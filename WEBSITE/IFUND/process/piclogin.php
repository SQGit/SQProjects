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

$query = "SELECT * FROM pic_table WHERE pic ='$pic' and is_active=1"; // and expiry_date <= DATE(now())//
$result = mysqli_query($conn, $query);
$num_rows = mysqli_num_rows($result);
if($num_rows >0 ){
	$row = mysqli_fetch_assoc($result);
	if(!empty($row['email']) && $row['email']=="$email" && $row['pic'] == "$pic")
	{
		if(strtotime($row['expiry_date']) >= strtotime(date('Y-m-d')))
		{
			$_SESSION["inv_id"] = $pic; //Initializing Session
			$_SESSION["inv_email"] = $email; //Initializing Session
			//header("location: pic_user.php"); // Redirecting To Other Page
			echo "success";	
		}
		else
		{
			echo "<span class=\"error\">Sorry! Your Account Expired!</span>";
		}	
		
	}
	else if(empty($row['email']) && $row['pic'] == "$pic")
	{
		if(strtotime($row['expiry_date']) >= strtotime(date('Y-m-d')))
		{
			$query2 = "UPDATE pic_table SET email= '$email' WHERE pic='$pic'";
			$result2 = mysqli_query($conn, $query2);
			$_SESSION["inv_id"] = $pic; //Initializing Session
			$_SESSION["inv_email"] = $email; //Initializing Session
			//header("location: pic_user.php"); // Redirecting To Other Page
			echo "success";	
		}
		else
		{
			echo "<span class=\"error\">Sorry! Your Account Expired!!</span>";
		}
	}	
}
else
{
	echo "<span class=\"error\">Please Enter Correct Details</span>";
}	
mysqli_close($conn); // Closing Connection
 
?>