"""An example of a simple HTTP server."""
import json
import mimetypes
import pickle
import socket
from os import listdir
from os.path import isdir, isfile, join
from urllib.parse import unquote_plus

# Pickle file for storing data
PICKLE_DB = "db.pkl"

# Directory containing www data
WWW_DATA = "www-data"

# Header template for a successful HTTP request
HEADER_RESPONSE_200 = """HTTP/1.1 200 OK\r
content-type: %s\r
content-length: %d\r
connection: Close\r
\r
"""

# Represents a table row that holds user data
TABLE_ROW = """
<tr>
    <td>%d</td>
    <td>%s</td>
    <td>%s</td>
</tr>
"""

RESPONSE_301 = """HTTP/1.1 301 Moved Permanently
Location: %s
"""

RESPONSE_400 = """HTTP/1.1 400 Bad request\r
content-type: text/html\r
connection: Close\r
\r
<!doctype html>
<h1>400 Bad request</h1>
"""

# Template for a 404 (Not found) error
RESPONSE_404 = """HTTP/1.1 404 Not found\r
content-type: text/html\r
connection: Close\r
\r
<!doctype html>
<h1>404 Page not found</h1>
<p>Page cannot be found.</p>
"""

RESPONSE_405 = """HTTP/1.1 405 Method not allowed\r
content-type: text/html\r
connection: Close\r
\r
<!doctype html>
<h1>405 Method not allowed</h1>
"""

DIRECTORY_LISTING = """<!DOCTYPE html>
<html lang="en">
<meta charset="UTF-8">
<title>Directory listing: %s</title>

<h1>Contents of %s:</h1>

<ul>
{{CONTENTS}}
</ul> 
"""

FILE_TEMPLATE = "  <li><a href='%s'>%s</li>"


def save_to_db(first, last):
    """Create a new user with given first and last name and store it into
    file-based database.

    For instance, save_to_db("Mick", "Jagger"), will create a new user
    "Mick Jagger" and also assign him a unique number.

    Do not modify this method."""

    existing = read_from_db()
    existing.append({
        "number": 1 if len(existing) == 0 else existing[-1]["number"] + 1,
        "first": first,
        "last": last
    })
    with open(PICKLE_DB, "wb") as handle:
        pickle.dump(existing, handle)


def read_from_db(criteria=None):
    """Read entries from the file-based DB subject to provided criteria

    Use this method to get users from the DB. The criteria parameters should
    either be omitted (returns all users) or be a dict that represents a query
    filter. For instance:
    - read_from_db({"number": 1}) will return a list of users with number 1
    - read_from_db({"first": "bob"}) will return a list of users whose first
    name is "bob".

    Do not modify this method."""
    if criteria is None:
        criteria = {}
    else:
        # remove empty criteria values
        for key in ("number", "first", "last"):
            if key in criteria and criteria[key] == "":
                del criteria[key]

        # cast number to int
        if "number" in criteria:
            criteria["number"] = int(criteria["number"])

    try:
        with open(PICKLE_DB, "rb") as handle:
            data = pickle.load(handle)

        filtered = []
        for entry in data:
            predicate = True

            for key, val in criteria.items():
                if val != entry[key]:
                    predicate = False

            if predicate:
                filtered.append(entry)

        return filtered
    except (IOError, EOFError):
        return []


def error_response(client, e):
    if e.args[0].startswith('404'):
        client.write(RESPONSE_404.encode("utf-8"))

    if e.args[0].startswith('405'):
        client.write(RESPONSE_405.encode("utf-8"))

    else:
        client.write(RESPONSE_400.encode("utf-8"))


# Read and parse headers
def parse_headers(client):
    headers = dict()

    while True:
        line = client.readline().decode("utf-8").strip()

        if not line:
            return headers

        key, value = line.split(":", 1)
        key = key.strip().lower()
        headers[key] = value.strip()


def parse_post_request(data_db):
    student_dict = dict()
    data_db = data_db.strip().split("&")

    if len(data_db) != 2:
        return False

    for part in data_db:
        key, value = unquote_plus(part).strip().split("=", 1)

        if value:
            if key == "first" or key == "last":
                student_dict[key] = value

    save_to_db(student_dict["first"], student_dict["last"])
    return True


def parse_get_request(data_db):
    person = {}

    try:
        data = data_db.strip().split("?", 1)[-1].split("&")

        if len(data) > 0 and data[0]:
            for attribute in data:
                key, value = unquote_plus(attribute).strip().split("=", 1)

                if value:
                    if key == "first" or key == "last" or key == "number":
                        person[key] = value

            return person

    except Exception as e:
        return None


def json_response(uri):
    read_db = read_from_db()

    if "?" in uri:
        read_db = read_from_db(parse_get_request(uri))

    return json.dumps(read_db).encode("utf-8")


def table_response(uri):
    try:
        if "?" in uri:
            search_db = read_from_db(parse_get_request(uri))

        for person in search_db:
            response = TABLE_ROW % (person["number"], person["first"], person["last"])

        with open(WWW_DATA + "/app_list.html", "rt") as data:
            data = data.read()
            new_page = data.replace("{students}", response)
            data.close

        return new_page.encode("utf-8")

    except Exception as e:
        return None


def handle_mimetype(uri):
    mimetype, encoding = mimetypes.guess_type(uri[1:])

    if mimetype is None:
        mimetype = "application/octet-stream"

    return mimetype


def process_request(connection, address, port):
    """Process an incoming socket request.

    :param connection is a socket of the client
    :param address is a 2-tuple (address(str), port(int)) of the client
    """

    client = connection.makefile("wrb")

    try:
        line = client.readline().decode("utf-8").strip()
        method, uri, version = line.split()

        # Possible uri combinations
        assert method == "GET" or method == "POST", "405 Invalid method: %s" % method

        assert len(uri) > 0 and uri[0] == "/", RESPONSE_404

        if uri.startswith("/app-json") or uri.startswith("/app-index"):
            assert method == "GET", "405 Invalid method: %s" % method

        if uri.startswith("/app-add"):
            assert method == "POST", "405 Invalid method: %s" % method

        # Version check
        assert version == "HTTP/1.1", "Invalid HTTP version"

        # Headers
        headers = parse_headers(client)
        print(method, uri, version, headers)

        # Response
        if uri.endswith("/"):
            uri = uri + "index.html"

            redirect = "http://" + headers["host"] + uri

            head = (RESPONSE_301 % (
                redirect
            ))
            client.write(head.encode("utf-8"))
            return

        if uri == "/app-add":
            data_db = client.read(int(headers["content-length"])).decode("utf-8")

            assert parse_post_request(data_db), RESPONSE_400

            uri = "/app_add.html"

        if uri.startswith("/app-json?") or uri == "/app-json":
            data_db = json_response(uri)

            head = HEADER_RESPONSE_200 % (
                "application/json",
                len(data_db)
            )

            client.write(head.encode("utf-8"))
            client.write(data_db)
            return

        if uri.startswith("/app-index?") or uri == "/app-index":
            data_db = table_response(uri)
            header = HEADER_RESPONSE_200 % (
                "text/html",
                len(data_db)
            )

            client.write(header.encode("utf-8"))
            client.write(data_db)
            return

        if "?" in uri:
            uri = uri[:-1]

        if isdir(WWW_DATA + uri):
            uri = uri + "/index.html"

            redirect = "http://" + headers["host"] + uri

            head = (RESPONSE_301 % (
                redirect
            ))

            client.write(head.encode("utf-8"))
            return

        ##################################################
        with open(WWW_DATA + uri, "rb") as txt:
            body = txt.read()
            txt.close()

        mimetype = handle_mimetype(uri)

        head = HEADER_RESPONSE_200 % (
            mimetype,
            len(body)
        )

        client.write(head.encode("utf-8"))
        client.write(body)

    except (ValueError, AssertionError) as e:
        print("[%s:%d] ERROR: %s" % (address[0], port, e))
        error_response(client, e)

    except IOError:
        client.write(RESPONSE_404.encode("utf-8"))

    except Exception as e:
        client.write(RESPONSE_400.encode("utf-8"))

    finally:
        client.close()


def main(port):
    """Starts the server and waits for connections."""

    server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    server.bind(("", port))
    server.listen(1)  # Max 1 at the time

    print("Listening on %d" % port)

    while True:
        connection, address = server.accept()
        print("[%s:%d] CONNECTED" % address)
        process_request(connection, address, port)
        connection.close()
        print("[%s:%d] DISCONNECTED" % address)


if __name__ == "__main__":
    main(8080)
