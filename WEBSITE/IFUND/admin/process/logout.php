<?php
session_start();


unset($_SESSION['inv_id']);

unset($_SESSION['inv_email']);

unset($_SESSION['username']);

if(session_destroy())
{
	header('location:../index.php'); 
}

?>
