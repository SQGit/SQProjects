<?php

include ("../includes/db_config.php");
$pic = $_POST["pic"];
$fname = test_input($_POST["rfname"]);
$lname = test_input($_POST["rlname"]);
$uname = test_input($_POST["runame"]);
$pass = test_input($_POST["rpass"]);
$cpass = test_input($_POST["rcpass"]);
$email = test_input($_POST["remail"]);
$baddress1 = test_input($_POST["billing_address1"]);
$baddress2 = test_input($_POST["billing_address2"]);
$bcity = test_input($_POST["billing_city"]);
$bzipcode = test_input($_POST['billing_zip']);
$country = test_input($_POST["rcountry"]);
$phone = test_input($_POST['rphone']);
$saddress1 = test_input($_POST["shipping_address1"]);
$saddress2 = test_input($_POST["shipping_address2"]);
$scity = test_input($_POST["shipping_city"]);
$szipcode = test_input($_POST['shipping_zip']);
function test_input($data)
{
    $data = trim($data);
    $data = stripslashes($data);
    $data = htmlspecialchars($data);
    return $data;
} 

 
$EmailTo = "$email";
$Subject = "New Message Received";
$name    = $fname . ' ' . $lname; 
$baddress = $baddress1. ',' .$baddress2;
$saddress = $saddress1. ',' .$saddress2;
$femail =  "investors@ifindcard.com";
// prepare email body text

 $Body = "From: 'iFund Network' \n Your User Name: $uname \n Registered E-mail id: $email \n Your Password : $pass";
        
		$query = "INSERT INTO reg_user (pic, name, user_id, password, email, baddress, saddress, bcity, scity, bzipcode, szipcode, country, phone)
		VALUES ('$pic','$name','$uname','$pass','$email','$baddress','$saddress','$bcity','scity','$bzipcode','$szipcode','$country','$phone')";
		
		if (mysqli_query($conn, $query)) 
		{
			
			if($success = @mail($EmailTo, $Subject, $Body, "From:".$femail))
			{
				echo "success";
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