<?php

$session= "localhost";
$user_name= "root";
$password = "";
$db_name = "tvitr_db";

$con = mysqli_connect($session, $user_name, $password, $db_name);

if (!$con) {
	echo "Connection failed!";
}