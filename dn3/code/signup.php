<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="style.css">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign up</title>
</head>

<body>
    <form action="signup-check.php" method="post">
        <h1>Sign up</h1>

        <?php 
        if (isset($_GET['error'])) { ?>
     		<p class="error"><?php echo $_GET['error']; ?></p>
     	<?php }
        ?>

        <label>Name</label>
        <input type="text" name="name"> <br>

        <label>User name</label>
        <input type="text" name="user_name"> <br>

        <label>Password</label>
        <input type="password" name="password">

        <button type="submit">Sign up</button>
        
        <a href="index.php" class="sign_up">Log into an existing account</a>
    </form>
</body>

</html>