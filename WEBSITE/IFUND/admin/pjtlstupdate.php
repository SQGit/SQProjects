<?php

include '../includes/db_config.php';

if(($_POST['name']=='project_category') Or ($_POST['name']=='project_fund_goal') Or ($_POST['name']=='project_desc_short') Or ($_POST['name']=='project_problem') Or ($_POST['name']=='project_vision') Or ($_POST['name']=='project_angels') Or ($_POST['name']=='	project_fund_pool') Or ($_POST['name']=='project_amt_invested') Or ($_POST['name']=='project_name') Or ($_POST['name']=='project_other_details') Or ($_POST['name']=='project_advantage') Or ($_POST['name']=='project_fund_period_from') Or ($_POST['name']=='project_fund_period_to')){
	$name = $_POST['name'];
	$id=$_POST['pk'];
    $value=$_POST['value'];
    $result=mysqli_query($conn, "SELECT COUNT(*) as count FROM project_list WHERE project_id=$id") or die(mysqli_error($conn));
    $count= mysqli_fetch_row($result);
    if($count[0]==0){
       mysqli_query($conn, "INSERT INTO project_list($name) VALUES('$value')") or die(mysqli_error($conn)); 	   
    }else{		
       mysqli_query($conn, "UPDATE project_list SET ".$name." = '".$value."' WHERE project_id=$id") or die(mysqli_error($conn)); 
    }   
}

if(($_POST['name']=='about_section') Or ($_POST['name']=='about_section_desc') Or ($_POST['name']=='about_main_title') Or ($_POST['name']=='about_sub_title')){
	$name = $_POST['name'];
	$id=$_POST['pk'];
    $value=$_POST['value'];
    $result=mysqli_query($conn, "SELECT COUNT(*) as count FROM about_us WHERE about_id=$id") or die(mysqli_error($conn));
    $count= mysqli_fetch_row($result);
    if($count[0]==0){
       mysqli_query($conn, "INSERT INTO about_us($name) VALUES('$value')") or die(mysqli_error($conn)); 
    }else{		
       mysqli_query($conn, "UPDATE about_us SET ".$name." = '".$value."' WHERE 	about_id=$id") or die(mysqli_error($conn)); 
    }   
}

if(($_POST['name']=='contact_address') Or ($_POST['name']=='contact_phone') Or ($_POST['name']=='contact_mail') Or ($_POST['name']=='	contact_main_title')){
	$name = $_POST['name'];
	$id=$_POST['pk'];
    $value=$_POST['value'];
    $result=mysqli_query($conn, "SELECT COUNT(*) as count FROM contact_us WHERE contact_id=$id") or die(mysqli_error($conn));
    $count= mysqli_fetch_row($result);
    if($count[0]==0){
       mysqli_query($conn, "INSERT INTO contact_us($name) VALUES('$value')") or die(mysqli_error($conn)); 
    }else{		
       mysqli_query($conn, "UPDATE contact_us SET ".$name." = '".$value."' WHERE contact_id=$id") or die(mysqli_error($conn)); 
    }   
}

if(($_POST['name']=='faq_main_title') Or ($_POST['name']=='faq_sub_title') Or ($_POST['name']=='faq_question') Or ($_POST['name']=='		faq_ans')){
	$name = $_POST['name'];
	$id=$_POST['pk'];
    $value=$_POST['value'];
    $result=mysqli_query($conn, "SELECT COUNT(*) as count FROM faq WHERE faq_id=$id") or die(mysqli_error($conn));
    $count= mysqli_fetch_row($result);
    if($count[0]==0){
       mysqli_query($conn, "INSERT INTO faq($name) VALUES('$value')") or die(mysqli_error($conn)); 
    }else{		
       mysqli_query($conn, "UPDATE faq SET ".$name." = '".$value."' WHERE faq_id=$id") or die(mysqli_error($conn)); 
    }   
}

if(($_POST['name']=='competitor_name')){
	$name = $_POST['name'];
	$id=$_POST['pk'];
    $value=$_POST['value'];
    $result=mysqli_query($conn, "SELECT COUNT(*) as count FROM competitors WHERE competitor_id=$id") or die(mysqli_error($conn));
    $count= mysqli_fetch_row($result);
    if($count[0]==0){
       mysqli_query($conn, "INSERT INTO competitors($name) VALUES('$value')") or die(mysqli_error($conn)); 
    }else{		
       mysqli_query($conn, "UPDATE competitors SET ".$name." = '".$value."' WHERE competitor_id=$id") or die(mysqli_error($conn)); 
    }   
}

if(($_POST['name']=='team_short_intro') Or ($_POST['name']=='team_member_name') Or ($_POST['name']=='team_member_profession') Or ($_POST['name']=='team_member_intro')){
	$name = $_POST['name'];
	$id=$_POST['pk'];
    $value=$_POST['value'];
    $result=mysqli_query($conn, "SELECT COUNT(*) as count FROM team WHERE team_id=$id") or die(mysqli_error($conn));
    $count= mysqli_fetch_row($result);
    if($count[0]==0){
       mysqli_query($conn, "INSERT INTO team($name) VALUES('$value')") or die(mysqli_error($conn)); 
    }else{		
       mysqli_query($conn, "UPDATE team SET ".$name." = '".$value."' WHERE team_id=$id") or die(mysqli_error($conn)); 
    }   
}

if(($_POST['name']=='funding_name') Or ($_POST['name']=='funding_document_path')){
	$name = $_POST['name'];
	$id=$_POST['pk'];
    $value=$_POST['value'];
    $result=mysqli_query($conn, "SELECT COUNT(*) as count FROM funding WHERE fund_id=$id") or die(mysqli_error($conn));
    $count= mysqli_fetch_row($result);
    if($count[0]==0){
       mysqli_query($conn, "INSERT INTO funding($name) VALUES('$value')") or die(mysqli_error($conn)); 
    }else{		
       mysqli_query($conn, "UPDATE funding SET ".$name." = '".$value."' WHERE fund_id=$id") or die(mysqli_error($conn)); 
    }   
}

if(($_POST['name']=='document_group_name') Or ($_POST['name']=='document_name') Or ($_POST['name']=='document_path')){
	$name = $_POST['name'];
	$id=$_POST['pk'];
    $value=$_POST['value'];
    $result=mysqli_query($conn, "SELECT COUNT(*) as count FROM documents WHERE document_id=$id") or die(mysqli_error($conn));
    $count= mysqli_fetch_row($result);
    if($count[0]==0){
       mysqli_query($conn, "INSERT INTO documents($name) VALUES('$value')") or die(mysqli_error($conn)); 
    }else{		
       mysqli_query($conn, "UPDATE documents SET ".$name." = '".$value."' WHERE document_id=$id") or die(mysqli_error($conn)); 
    }   
}

if(($_POST['name']=='partner_country') Or ($_POST['name']=='partner_profession') Or ($_POST['name']=='partner_desc') Or ($_POST['name']=='partner_main_title')){
	$name = $_POST['name'];
	$id=$_POST['pk'];
    $value=$_POST['value'];
    $result=mysqli_query($conn, "SELECT COUNT(*) as count FROM partners WHERE partners_id=$id") or die(mysqli_error($conn));
    $count= mysqli_fetch_row($result);
    if($count[0]==0){
       mysqli_query($conn, "INSERT INTO partners($name) VALUES('$value')") or die(mysqli_error($conn)); 
    }else{		
       mysqli_query($conn, "UPDATE partners SET ".$name." = '".$value."' WHERE partners_id=$id") or die(mysqli_error($conn)); 
    }   
}

if(($_POST['name']=='traction_name') Or ($_POST['name']=='traction_name_desc') Or ($_POST['name']=='traction_status')){
	$name = $_POST['name'];
	$id=$_POST['pk'];
    $value=$_POST['value'];
    $result=mysqli_query($conn, "SELECT COUNT(*) as count FROM traction WHERE traction_id=$id") or die(mysqli_error($conn));
    $count= mysqli_fetch_row($result);
    if($count[0]==0){
       mysqli_query($conn, "INSERT INTO traction($name) VALUES('$value')") or die(mysqli_error($conn)); 
    }else{		
       mysqli_query($conn, "UPDATE traction SET ".$name." = '".$value."' WHERE 	traction_id=$id") or die(mysqli_error($conn)); 
    }   
}

if(($_POST['name']=='testing_name') Or ($_POST['name']=='testing_name_desc') Or ($_POST['name']=='testing_status')){
	$name = $_POST['name'];
	$id=$_POST['pk'];
    $value=$_POST['value'];
    $result=mysqli_query($conn, "SELECT COUNT(*) as count FROM testing WHERE testing_id=$id") or die(mysqli_error($conn));
    $count= mysqli_fetch_row($result);
    if($count[0]==0){
       mysqli_query($conn, "INSERT INTO testing($name) VALUES('$value')") or die(mysqli_error($conn));
    }else{		
       mysqli_query($conn, "UPDATE testing SET ".$name." = '".$value."' WHERE testing_id=$id") or die(mysqli_error($conn)); 
    }   
}

//add country//
if($_POST['name']=='country'){
	$name = $_POST['name'];	
	//$id=$_POST['pk'];
    $value=$_POST['value'];   
	$result=mysqli_query($conn, "SELECT COUNT(*) as count FROM country WHERE country='$value'") or die(mysqli_error($conn));
	$count= mysqli_fetch_row($result);
    if($count[0]==0){
    mysqli_query($conn, "INSERT INTO country(country) VALUES('$value')") or die(mysqli_error($conn));
}
}

//transaction note//
if($_POST['name'] == 'note') {
	$name = $_POST['name'];
	$id=$_POST['pk'];
    $value=$_POST['value'];
    $result=mysqli_query($conn, "UPDATE transection_tbl SET ".$name." = '".$value."' WHERE txn_id='$id'") or die(mysqli_error($conn)); 
    if($result){
		echo "success";
	}else{
		echo mysqli_error($conn);
	}
}


?>