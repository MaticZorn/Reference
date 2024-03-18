<?php

session_start(); 
include "connection.php";
include "functions.php";

if (isset($_POST['user_name']) && isset($_POST['password'])) {

	$user_name = validate($_POST['user_name']);
	$password = validate($_POST['password']);

	if (empty($user_name) || empty($password)) {
        header("Location: index.php?error=User name and password are required!");
	    exit();

	} else {
		$password = md5($password);

		$query = "SELECT * FROM users WHERE user_name='$user_name' AND password='$password'";

		$result = mysqli_query($con, $query);

		if (mysqli_num_rows($result) === 1) {

			$row = mysqli_fetch_assoc($result);

            if ($row['user_name'] === $user_name && $row['password'] === $password) {

            	$_SESSION['user_name'] = $row['user_name'];
            	$_SESSION['name'] = $row['name'];
            	$_SESSION['id'] = $row['id'];

            	header("Location: home.php");
		        exit();
                
            } else {
                header("Location: index.php?error=Incorect user name or password!");
		        exit();
			}

		} else {
            header("Location: index.php?error=Incorect user name or password!");
	        exit();
		}
	}
	
} else {
	header("Location: index.php");
	exit();
}