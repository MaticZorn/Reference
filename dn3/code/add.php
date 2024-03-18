<?php

if (isset($_POST['title'])) {
    require 'connection.php';

    $title = $_POST['title'];

    if (empty($title)) {
        header("Location: home.php?error");
    } else {
        $query = $con->prepare("INSERT INTO todo(todo_title) VALUE(?)");
        $result = $query->execute([$title]);

        if ($result) {
            header("Location: home.php?success");
        } else {
            header("Location: home.php");
        }
        exit();
    }
} else {
    header("Location: home.php?error");
}