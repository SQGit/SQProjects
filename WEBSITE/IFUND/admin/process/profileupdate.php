<?php

include '../includes/db_config.php';

if(($_POST['name']=='name') Or ($_POST['name']=='email') Or ($_POST['name']=='baddress') Or ($_POST['name']=='saddress') Or ($_POST['name']=='bcity') Or ($_POST['name']=='scity') Or ($_POST['name']=='bzipcode') Or ($_POST['name']=='szipcode') Or ($_POST['name']=='country') Or ($_POST['name']=='phone')){
	$name = $_POST['name'];
	$id=$_POST['pk'];
    $value=$_POST['value'];
    $result=mysqli_query($conn, "SELECT COUNT(*) as count FROM reg_user WHERE pic='$id'") or die(mysqli_error($conn));
    $count= mysqli_fetch_row($result);
    if($count[0]==0){
       mysqli_query($conn, "INSERT INTO reg_user(project_id,project_category) VALUES(".$id.",".$project_category.")") or die(mysqli_error($conn)); 
    }else{		
       mysqli_query($conn, "UPDATE reg_user SET ".$name." = '".$value."' WHERE pic='$id'") or die(mysqli_error($conn));
	   
    }   
}

?>