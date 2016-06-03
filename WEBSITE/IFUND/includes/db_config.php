<?php

$servername = "50.62.209.119:3306";
$username = "ifunduser";
$password = "Root@123";
$db = "ifund_data";


// Create connection
$conn = mysqli_connect($servername, $username, $password, $db);

// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

?>