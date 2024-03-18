<?php 
session_start();
require "connection.php";

if (isset($_SESSION['id']) && isset($_SESSION['user_name'])) {

    ?>
    <!DOCTYPE html>
    <html>
    <head>
        <title>To-do list</title>
        <link rel="stylesheet" type="text/css" href="style2.css">
    </head>

    <body>
        <div class="main-section">
            <div class="add-section">
                <form action="add.php" method="POST" autocomplete="off">
                    <?php if (isset($_GET['error'])) { ?>
                        <input type="text" name="title" placeholder="This field can't be empty!">
                        <button type="submit">Add</button>
                    <?php } else { ?>
                        <input type="text" name="title" placeholder="Write something">
                        <button type="submit">Add</button>
                    <?php } ?>
                </form>
            </div>

            <?php
                $query = "SELECT * FROM todo ORDER BY todo_id DESC";
                $result = mysqli_query($con, $query);
            ?>
            <!-- todo listki -->
            <div class="todo-section">
                <?php if (mysqli_num_rows($result) <= 0) { ?>
                    <div class="todo-item">
                        <div class="empty">
                            <h2>empty</h2>
                        </div>
                    </div>
            </div>
            <?php } ?>

            <?php while ($todo = mysqli_fetch_assoc($result)) { ?>
                <div class="todo-item">
                    <p id="<?php echo $todo['todo_id']; ?>"></p>

                    <?php if ($todo['checked']) { ?>
                        <input type="checkbox" class="check-box" data-todo-id="<?php echo $todo['todo_id']; ?>" checked > 
                        <h2 class="checked"><?php echo $todo['todo_title'] ?></h2><br>
                    <?php } else { ?>
                        <input type="checkbox" class="check-box" data-todo-id="<?php echo $todo['todo_id']; ?>" >           
                        <h2><?php echo $todo['todo_title'] ?></h2><br>
                    <?php } ?>

                    <small>date posted: <?php echo $todo['todo_date'] ?></small>
                </div>
                            
            <?php } ?>

        </div>
        <a href="logout.php">Logout</a>

    </body>

    </html>

<?php 
} else {
     header("Location: index.php");
     exit();
}
?>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script>
    $(document).ready(function() {
        $(".check-box").click(function(e){
            const id = $(this).attr('data-todo-id');

            if($(this).prop("checked") == true) {
                var checked = 1;
            } else {
                var checked = 0;
            }
            
            $.post('check.php', 
                    {
                        id: id,
                        checked: checked
                    },
                    (data) => {
                        if(data != 'error'){
                            const h2 = $(this).next();
                            if(data === '1'){
                                h2.removeClass('checked');
                            }else {
                                h2.addClass('checked');
                            }
                        }
                    }
            );
        });
    });
</script>