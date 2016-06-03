<?php
session_start();

include ("../includes/db_config.php");

$pic = test_input($_POST["pic"]);
$email = test_input($_POST["email"]);

function test_input($data)
{
    $data = trim($data);
    $data = stripslashes($data);
    $data = htmlspecialchars($data);
    return $data;
} 
 
$query = "SELECT * FROM pic_table WHERE email ='$email' and pic ='$pic' and status=1";

$result = mysqli_query($conn, $query);

$num_rows = mysqli_num_rows($result);

if($num_rows >0 ){
	
	$_SESSION["inv_id"] = $pic; //Initializing Session
	$_SESSION["inv_email"] = $email; //Initializing Session

	//header("location: pic_user.php"); // Redirecting To Other Page
	echo "success";	
}
else
{
	echo "<span class=\"error\">Please Enter Correct Details</span>";
}
mysqli_close($conn); // Closing Connection
 
?>