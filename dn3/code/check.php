<?php

if(isset($_POST['id'])){
    require 'connection.php';

    $id = $_POST['id'];
    $checked = $_POST['checked'];

    if(empty($id)){
       echo 'error';
    }else {
        $query = ("UPDATE todo SET checked=$checked WHERE todo_id=$id");
        $res = mysqli_query($con, $query);

        $con = null;
        exit();
    }
}else {
    header("Location: home.php?error");
}