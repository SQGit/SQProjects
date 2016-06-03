 <?php
 
session_start();

//include db config file
include '../includes/db_config.php';

if(isset($_POST['action']) && $_POST['action'] == 'forgotpassword')
    {
		$email = mysqli_real_escape_string($conn,$_POST['email']); 
          $query  = "select * from reg_user where email='".$email."'";
          $result1    = mysqli_query($conn,$query);
          if(mysqli_num_rows($result1))
		{
			$chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%&*_";
			$password = substr( str_shuffle( $chars ), 0, 8 );
			$query1 = "UPDATE reg_user SET password = '$password' WHERE email = '$email'";
			$result = mysqli_query($conn, $query1);
	
				$to = $email;
				$subject = 'Your New Password...';
				$message = 'Hello User Your new password is : '.$password. "\r\n". 'E-mail: '.$email."\r\n". 'Now you can login with this email and password.';
				/* Send the message using mail() function */
				if(@mail($to, $subject, $message ))
				{
				echo "<span class='success'>Your Password changed.. Check your mail for new password.</span>";
				}
				else
				{
					echo "<span class='error'>Your Password changed..There is an error to send mail.</span>";
				}
		}
		else
		{			
			echo "<span class='error'>There is no user with this email address.</span>";
		}
          
    }

mysqli_close($conn);
?> 

