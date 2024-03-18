<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="style.css">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
</head>

<body>
    <form action="login.php" method="post">
        <h1>Login</h1>

        <?php 
        if (isset($_GET['error'])) { ?>
     		<p class="error"><?php echo $_GET['error']; ?></p>
     	<?php }
        ?>

        <?php 
        if (isset($_GET['success'])) { ?>
     		<p class="success"><?php echo $_GET['success']; ?></p>
     	<?php }
        ?>

        <label>User name</label>
        <input type="text" name="user_name"> <br>

        <label>Password</label>
        <input type="password" name="password">

        <button type="submit">Login</button>
        
        <a href="signup.php" class="sign_up">Sign up</a>
        <a href="home_guest.php" class="sign_up">Log in as Guest</a>
    </form>
</body>

</html>