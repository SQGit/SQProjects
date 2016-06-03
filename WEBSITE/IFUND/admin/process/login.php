<?php
session_start();

include ("../includes/db_config.php");

$uname = test_input($_POST["uname"]);
$email = test_input($_POST["lemail"]);
$pass = test_input($_POST["pass"]);
 
function test_input($data)
{
    $data = trim($data);
    $data = stripslashes($data);
    $data = htmlspecialchars($data);
    return $data;
} 
//$pass = md5($pass);

$resultmsg=array();

if($uname == "admin"){
	$query1 = "SELECT * FROM reg_user WHERE email ='$email' and user_id ='$uname' and password = '$pass' and access_level=1";
	$result1 = mysqli_query($conn, $query1);
	$num_rows = mysqli_num_rows($result1);
	if($num_rows >0 ){
		
		$row = mysqli_fetch_assoc($result1);
		
		$_SESSION['username'] = $row['user_id']; //Initializing Session		
		$_SESSION['inv_id'] = $row['pic'];
		$_SESSION['inv_email'] = $row['email'];
		
		
		$resultmsg['response'] = 1;
		$resultmsg['redirecturl'] = 'admin/home.php';
		
		
	}
	else
	{
		
		$resultmsg['response'] = 0;
		$resultmsg['error'] = 'Sorry!! Username or Password Not Correct..';
		//echo "<span class=\"error\">Sorry!! Username or Password Not Correct..</span>";
	}
	
	
}
else
{

$query = "SELECT * FROM reg_user WHERE email ='$email' and user_id ='$uname' and password = '$pass'";

	$result = mysqli_query($conn, $query);
	
    $num_rows = mysqli_num_rows($result);
	
    if($num_rows >0 ){
		
		$row = mysqli_fetch_assoc($result);
		
		//session_regenerate_id();
		
		$_SESSION['username'] = $row['user_id']; //Initializing Session		
		$_SESSION['inv_id'] = $row['pic'];
		$_SESSION['inv_email'] = $row['email'];
		
		
		$resultmsg['response'] = 1;
		$resultmsg['redirecturl'] = 'reg_user.php';
		
	}
	else
	{
		$resultmsg['response'] = 0;
		$resultmsg['error'] = 'Sorry!! Username or Password Not Correct..';
		//echo "<span class=\"error\">Sorry!! Username or Password Not Correct..</span>";
	}
}
echo json_encode($resultmsg);
mysqli_close($conn); // Closing Connection
?>