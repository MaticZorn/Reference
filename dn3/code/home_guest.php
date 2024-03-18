<?php 
session_start();
require "connection.php";

 ?>
<!DOCTYPE html>
<html>
<head>
	<title>To-do list</title>
	<link rel="stylesheet" type="text/css" href="style2.css">
</head>

<body>
    <div class="main-section">
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
                <p id="<?php echo $todo['todo_id']; ?>"
                    class="remove"></p>
                <?php if ($todo['checked']) { ?>
                    <h2 class="checked"><?php echo $todo['todo_title'] ?></h2><br>
                <?php } else { ?>
                    <h2><?php echo $todo['todo_title'] ?></h2><br>
                    <?php } ?>

                <small>date posted: <?php echo $todo['todo_date'] ?></small>
            </div>
        <?php } ?>

    </div>
    <a href="index.php">Log in</a>
</body>

</html>

<?php 
?>