<?php

include ("../../includes/db_config.php");

$message = '';

	if (empty($_POST["name"])) {
        $nameErr = "Please Enter your First Name";
    } else {
        $name = test_input($_POST["name"]);
        // check if name only contains letters and whitespace
        if (!preg_match("/^[a-zA-Z ]*$/", $name)) {
            $nameErr = "Only letters and white space allowed";
        }
    }

    if (empty($_POST["email"])) {
        $emailErr = "Email is required";
    } else {
        $email = test_input($_POST["email"]);
        // check if e-mail address is well-formed
        if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
            $emailErr = "Invalid email format";
        }
    }
    if (empty($_POST["country"])) {
        $countryErr = "Please Select your Country";
    } else {
        $country = test_input($_POST["country"]);
    }
	
    if (empty($_POST["mobile"])) {
        $mobileErr = "Please Enter your Phone Number";
    } else {
        $mobile = test_input($_POST["mobile"]);
        // check if name only contains letters and whitespace
        if (!preg_match("/^[0-9]*$/", $mobile)) {
            $mobileErr = "Only letters and -,+ allowed";
        }
    }
    if (empty($_POST["message"])) {
        $messageErr = "";
    } else {
        $message = test_input($_POST["message"]);
    }
/*
$fname = test_input($_POST["afname"]);
$lname = test_input($_POST["alname"]);
$email = test_input($_POST["aemail"]);
$country = test_input($_POST["acountry"]);
$phone = test_input($_POST["aphone"]);
$message = test_input($_POST["amessage"]);
*/


function test_input($data)
{
    $data = trim($data);
    $data = stripslashes($data);
    $data = htmlspecialchars($data);
    return $data;
}

$EmailTo = "investors@ifindcard.com";
$Subject = "Inquiry from your ifund Guest";
$email =  $email;
$Body = 'Name : '.$name."\r\n".'Email :'.$email."\r\n".'Mobile Number :'.$mobile."\r\n". 'Country :'.$country."\r\n".'Message :'.$message;


if (!empty($name) || !empty($email) || !empty($mobile) || !empty($country)) 
{
 	if($success = @mail($EmailTo, $Subject, $Body, "From:".$email))
	{
		echo "<span class=\"success\">Thank You!.. Your Query Received<br/>We will contact you soon..</span>";
	}
	else 
	{
		echo "<span class=\"error\">Sorry!.. Something went wrong<br/>Try after sometime..</span>";
	}
}
else
{
	echo "<span class=\"error\">please fill all fields</span>";
}	

mysqli_close($conn);
?>