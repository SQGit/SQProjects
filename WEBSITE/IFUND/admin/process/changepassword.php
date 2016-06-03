<?php

include ("../includes/db_config.php");

$oldpassword = test_input($_POST["curpass"]);
$newpassword = test_input($_POST["newpass"]);
$confirmpassword = test_input($_POST["confirmpass"]);
$sess_name = test_input($_POST["sess_name"]);
$sess_email = test_input($_POST["sess_email"]);

function test_input($data)
{
    $data = trim($data);
    $data = stripslashes($data);
    $data = htmlspecialchars($data);
    return $data;
} 

 
$EmailTo = "$sess_email";
$Subject = "Your password changed";
$femail =  "investors@ifindcard.com";
// prepare email body text

 $Body = "From: 'iFund Network' \n Hi $sess_name \n Your Ifund password changed \n Your New Password : $newpassword";
        
		$query = "UPDATE reg_user SET password = '$newpassword' WHERE email='$sess_email'";
		
		if (mysqli_query($conn, $query)) 
		{
			
			if($success = mail($EmailTo, $Subject, $Body, "From:".$femail))
			{
				echo "<span class=\"success\">Your password successfully changed</span>";
			}
			else 
			{
				echo "Error";
			}	
		}
		else
		{
			echo "Error: " . $query . "<br>" . mysqli_error($conn);
		}    
	mysqli_close($conn);
 
?>