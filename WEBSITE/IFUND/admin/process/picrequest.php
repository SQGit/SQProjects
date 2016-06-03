<?php

include ("../includes/db_config.php");

	if (empty($_POST["fname"])) {
        $fnameErr = "Please Enter your First Name";
    } else {
        $fname = test_input($_POST["fname"]);
        // check if name only contains letters and whitespace
        if (!preg_match("/^[a-zA-Z ]*$/", $fname)) {
            $fnameErr = "Only letters and white space allowed";
        }
    }
    if (empty($_POST["lname"])) {
        $lnameErr = "Please Enter your Last Name";
    } else {
        $lname = test_input($_POST["lname"]);
        // check if name only contains letters and whitespace
        if (!preg_match("/^[a-zA-Z ]*$/", $lname)) {
            $lnameErr = "Only letters and white space allowed";
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
    if (empty($_POST["phone"])) {
        $phoneErr = "Please Enter your Phone Number";
    } else {
        $phone = test_input($_POST["phone"]);
        // check if name only contains letters and whitespace
        if (!preg_match("/^[0-9]*$/", $phone)) {
            $phoneErr = "Only letters and -,+ allowed";
        }
    }
    if (empty($_POST["message"])) {
        $messageErr = "";
    } else {
        $message = test_input($_POST["message"]);
    }


function test_input($data)
{
    $data = trim($data);
    $data = stripslashes($data);
    $data = htmlspecialchars($data);
    return $data;
}

$name = $fname.' '.$lname;

if (!empty($name || $email || $phone || $country)) {
 
		$query = "INSERT INTO pic_table (name, email, phone, country, status)
		VALUES ('$name', '$email', '$phone', '$country', 0)";
		
		if (mysqli_query($conn, $query)) 
		{			
			echo "success";	
		}
		else
		{
			echo "<span class=\"error\">Sorry.. Error occured.. Please try again.</span>";
		}
    
	mysqli_close($conn);
}
else
{
	echo "<span class=\"error\">Please fill all fields</span>";
}	

?>