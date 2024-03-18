<?php

session_start(); 
include "connection.php";
include "functions.php";

if (isset($_POST['user_name']) && isset($_POST['password']) && isset($_POST['name'])) {

    $name = validate($_POST['name']);
	$user_name = validate($_POST['user_name']);
	$password = validate($_POST['password']);

	if (empty($user_name)) {
        header("Location: signup.php?error=User name is required!");
	    exit();
    }

    else if (empty($password)) {
        header("Location: signup.php?error=Password is required!");
	    exit();
    }

    else if (empty($name)) {
        header("Location: signup.php?error=Name is required!");
	    exit();

	} else {
		$password = md5($password);

		// Check if username is taken
		$query = "SELECT * FROM users WHERE user_name='$user_name'";

		$result = mysqli_query($con, $query);

		if (mysqli_num_rows($result) > 0) {
			header("Location: signup.php?error=User name already exists!");
			exit();

		} else {
			$query2 = "INSERT INTO users(user_name, password, name) VALUES('$user_name','$password','$name')";
			$result2 = mysqli_query($con, $query2);

			if ($result2) {
				header("Location: index.php?success=You have created an account!");
				exit();

			} else {
				header("Location: signup.php?error=error!");
				exit();
			}
		}
	}
	
} else {
	header("Location: signup.php");
	exit();
}