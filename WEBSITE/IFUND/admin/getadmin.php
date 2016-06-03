
<?php
// This file makes a connection with mysql database. 
include ("../includes/db_config.php");
echo '<script src="admin_page.js"></script>';
// get pic requested users list start//
if($_POST['action'] && $_POST['action']=="get_pic_req_list")
{
$sql = "SELECT * FROM pic_table WHERE is_active = 0";

$result = mysqli_query($conn, $sql);

echo '<div class="page-title">
		<div class="title_left"> 
			<h3>Investor Management</h3>
		</div>
	  </div>
	  <div class="clearfix"></div>
	  <div class="row">
	  	<div class="col-md-12">
	  		<div class="x_panel">
	  			<div class="table-responsive">
	  				<table class="table table-bordered table-striped table-hover text-center">
	  					<thead align="center">
	  						<tr class="info">
	  							<th>Request ID</th>
	  							<th>Name</th>
	  							<th>Email</th>
	  							<th>phone</th>
	  							<th>Country</th>
	  							<th>User Status</th>
	  						</tr>
	  					</thead>
	  					<tbody>';

if(mysqli_num_rows($result) >0){

	while ($row = mysqli_fetch_assoc($result)) {
	
		echo '<tr>
				<td id="req_id">'.$row['req_id'].'</td>
				<td id="name">'.$row['name'].'</td>
				<td id="email">'.$row['email'].'</td>
				<td id="phone">'.$row['phone'] .'</td>
				<td id="country">'.$row['country'].'</td>
				<td><button id="activate" onclick="pic_activate();" class="btn btn-primary btn-sm">Activate</button></td>
			  </tr>';
	}
}else{
	echo '<tr><td colspan=6> No New Investors.. </td></tr>';
}
echo '</tbody></table></div> </div> </div> </div>';
}
// get pic requested users list end//

//activate pic start//
if($_POST['action'] && $_POST['action']=="pic_activate"){

$req_id = $_POST['req_id'];
$name = ($_POST["name"]);
$email = ($_POST["email"]);
$country = ($_POST["country"]);
$phone = ($_POST["phone"]);

function test_input($data)
{
    $data = trim($data);
    $data = stripslashes($data);
    $data = htmlspecialchars($data);
    return $data;
}

$EmailTo = "$email";
$Subject = "Your Investor Code";
$pic = uniqid();
$femail = "investors@ifindcard.com";

$Body = "From: 'iFund Network' \n To: $email \n Message: Your account activated \n Your PIC is: $pic";

$query = "SELECT * FROM pic_table WHERE req_id=$req_id";

$result = mysqli_query($conn, $query);

if (mysqli_num_rows($result) > 0) {
    $update = mysqli_query($conn, "UPDATE pic_table SET is_active = 1, pic = '$pic', activation_date = now(), expiry_date = CURDATE() + INTERVAL 2 DAY WHERE req_id=$req_id");
    if ($update) {
        if ($success = mail($EmailTo, $Subject, $Body, "From:" . $femail)) {
            echo "success";
        } else {
            echo "Error";
        }
    } else {
        echo "account not activated..";
    }
} else {
    echo "data not available";
}
}
//activate pic end//





//get pic activated users start//
if($_POST['action'] && $_POST['action']=="get_activated_pic")
{
$sql = "SELECT * FROM pic_table WHERE is_active = 1 and expiry_date >= DATE(now())";

$result = mysqli_query($conn, $sql);

echo '<div class="page-title"> 
		<div class="title_left"> 
			<h3>Investor Management<small>Activated Users</small></h3> 
		</div>
 	  </div> 
 	  <div class="clearfix"></div> 
 		<div class="row"> 
 			<div class="col-md-12"> 
 				<div class="x_panel"> 
 					<div class="table-responsive">
 						<table class="table table-bordered table-striped table-hover text-center">
 							<thead align="center">
 								<tr class="info">
 									<th>Request ID</th>
 									<th>Name</th><th>Email</th>
 									<th>phone</th><th>Country</th>
 									<th>Activation Date</th>
 								</tr>
 							</thead>
 							<tbody>';

if(mysqli_num_rows($result) >0){

	while ($row = mysqli_fetch_assoc($result)) {	
		echo '<tr>
				<td id="req_id">'.$row['req_id'].'</td>
				<td id="name">'.$row['name'].'</td>
				<td id="email">'.$row['email'] .'</td>
				<td id="phone">'.$row['phone'].'</td>
				<td id="country">'.$row['country'].'</td>
				<td id="activation_date">'.$row['activation_date'].'</td>
			  </tr>';
	}
}else{
	echo '<tr><td colspan=6> No More activated PIC users.. </td></tr>';
}
echo '</tbody></table></div> </div> </div> </div> </div>';
}
//get pic activated users end//


//get pic expired users START//
if($_POST['action'] && $_POST['action']=="get_expired_pic")
{
$sql = "SELECT * FROM pic_table WHERE is_active = 1 and expiry_date < DATE(now())";

$result = mysqli_query($conn, $sql);

echo '<div class="page-title">
		<div class="title_left">
			<h3>Investor Management<small>Expired PIC</small></h3>
		</div>';
if(mysqli_num_rows($result) >0){		
		echo '<div class="title_right">
				<div class="col-md-6 col-sm-6 col-xs-12 form-group pull-right"> 
					<button onclick="remove_expired_pic();" type="button" class="btn btn-danger">Remove All Expired PIC from Database</button> 
				</div> 
			  </div>'; 
}			  

echo '</div> 
		<div class="clearfix"></div> 
		<div class="row">
			<div class="col-md-12"> 
				<div class="x_panel"> 
					<div class="table-responsive">
						<table class="table table-bordered table-striped table-hover text-center">
							<thead align="center">
								<tr class="info">
									<th>Request ID</th>
									<th>Name</th>
									<th>Email</th>
									<th>phone</th>
									<th>Country</th>
									<th>Activation Date</th>
									<th>Expired Date</th>
								</tr>
							</thead>
							<tbody>';

if(mysqli_num_rows($result) >0){

	while ($row = mysqli_fetch_assoc($result)) {	
		echo '<tr>
				<td id="req_id">'.$row['req_id'].'</td>
				<td id="name">'.$row['name'].'</td>
				<td id="email">'.$row['email'].'</td>
				<td id="phone">'.$row['phone'].'</td>
				<td id="country">'.$row['country'].'</td>
				<td id="activation_date">'.$row['activation_date'].'</td>
				<td id="expiry_date">'.$row['expiry_date'].'</td>
			</tr>';
		
	}
}else{
	echo '<tr><td colspan=7> No More Expired PIC.. </td></tr>';
}
echo '</tbody></table></div> </div> </div> </div> </div>';
}
//get pic expired users END//


//remove expired pic users start//
if($_POST['action'] && $_POST['action']=="remove_expired_pic")
{
$sql = "DELETE FROM pic_table WHERE is_active = 1 and expiry_date < DATE(now())";

$result = mysqli_query($conn, $sql);

if($result){
	echo '<script> get_expired_pic(); </script>';
}
}
//remove expired pic users end//

//pic generate start//
if($_POST['action'] && $_POST['action']=="pic_generate"){
	
	$no_of_pic = $_POST['no_of_pic'];
	$valid_days = $_POST['valid_days'];
	
	$expiry_date = date('Y-m-d', strtotime("+$valid_days days"));
	$result = '';
	$result .= '<table class="table table-bordered table-striped table-hover text-center"><thead align="center"><tr class="info"><th>PIC</th><th>Expiry Date</th></tr></thead><tbody>';
	$sql = "INSERT INTO pic_table (pic, is_active, activation_date, expiry_date) VALUES ";
	
	for($i=0; $i<$no_of_pic; $i++){
		$pic = uniqid();
		if ($i > 0) $sql .= ", ";
		$sql .= "('$pic', 1, now(), CURDATE() + INTERVAL $valid_days DAY)";
		$result .=  "<tr><td>".$pic."</td> <td>".$expiry_date."</td></tr>";	
	}
	$result .= '</tbody></table>';
	if(mysqli_query($conn, $sql)){
		echo $result;
	}else{
		echo mysqli_error($conn);
	}
}
//pic generate end//

// get projects list start//
if($_POST['action'] && $_POST['action']=="projects")
{
	$sql = "SELECT project_id, project_name, status, funding_required from project_list";
	
	$result = mysqli_query($conn, $sql);
	
	echo '<div class="page-title">
			<div class="title_left">
				<h3>Project Management Panel</h3>
			</div>
		</div> 
		<div class="clearfix"></div> 
		<div class="row"> 
			<div class="col-md-12"> 
				<div class="x_panel">
					<div class="x_title">
						<h2>Projects</h2>
						<ul class="nav navbar-right mt10">
							<a href="#" onclick="add_edit_project_UI();" class="text-capitalize btn btn-info"><i class="fa fa-plus"></i> Add New Project </a>
						</ul>
						<div class="clearfix"></div>
					</div>
					<table class="table table-striped projects" id="projects">
						<thead>
							<tr>
								<th style="width: auto">ID</th>
								<th style="width: auto">Project Name</th>                       
								<th style="width: auto">Status</th>
								<th style="width: auto">Fund Status</th>
								<th style="width: auto">Action</th>
							</tr>
						</thead>
						<tbody>';

	while ($row = mysqli_fetch_assoc($result)) {
		
		echo '<tr><td>#</td>
			<td>'.$row['project_name'].'</td>
			<td> <div class="btn-group">';
			if($row['status'] == 'active'){
				echo '<a href="#" class="btn btn-success btn-xs text-capitalize m2">'.$row['status'].'</a>';
			}else if($row['status'] == 'disabled'){
				echo '<a href="#" class="btn btn-danger btn-xs text-capitalize m2">'.$row['status'].'</a>';
			}else if($row['status'] == 'draft'){
				echo '<a href="#" class="btn btn-info btn-xs text-capitalize m2">'.$row['status'].'</a>';
			}
			if($row['funding_required'] == 0){
				echo '<a href="#" class="btn btn-danger btn-xs text-capitalize m2">Fund Disabled</a>';
			}else if($row['funding_required'] == 1){
				echo '<a href="#" class="btn btn-success btn-xs text-capitalize m2">Fund Enabled</a>';
			}
			echo '</div></td><td></td>
				<td>
				<a href="#" onclick="add_edit_project_UI('.$row['project_id'].')" class="btn btn-info btn-xs m2"><i class="fa fa-pencil"></i> Edit </a> ';
			if($row['status']!=='active'){					
				echo '<a href="#" onclick="enable_project('.$row['project_id'].')" class="btn btn-success btn-xs m2"><i class="fa fa-paper-plane"></i> Activate </a> ';
			}else{
				echo '<a href="#" onclick="disable_project('.$row['project_id'].')" class="btn btn-danger btn-xs m2"><i class="fa fa-trash-o"></i> Disable </a> ';
				if($row['funding_required'] == 1){
					echo '<input class="funding" data-name="funding_required" data-pk="'.$row['project_id'].'" type="checkbox" checked>';
				}else if($row['funding_required'] == 0){
					echo '<input class="funding" data-name="funding_required" data-pk="'.$row['project_id'].'" type="checkbox">';
				}
			}
		echo '</td></tr>';
	}
	
	echo '</tbody></table></div></div></div>';	
}	
// get projects list end//

//funding required//
if($_POST['action'] && $_POST['action'] == "funding_required") {
	$name = $_POST['name'];
	$id=$_POST['pk'];
    $value=$_POST['value'];
    $result=mysqli_query($conn, "UPDATE project_list SET ".$name." = '".$value."' WHERE project_id=$id") or die(mysqli_error($conn)); 
    if($result){
		echo "success";
	}else{
		echo mysqli_error($conn);
	}
}


//add_edit_project_UI start//

if ($_POST['action'] && $_POST['action'] == "add_edit_project_UI") {
    ?>
    <div class="page-title">
        <div class="title_left">
            <h3>Project Management Panel</h3>
        </div>
    </div>
    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2></h2>
                    <h2><?php if ($_POST['id'] !== '') {
                            echo 'Edit Project Details';
                        } else {
                            echo 'Add New Project';
                        } ?></h2>
                    <div class="clearfix"></div>
                </div>
                <div id="<?php if ($_POST['id'] !== '') {
                    echo 'edit_project_msg';
                } else {
                    echo 'add_project_msg';
                } ?>" class="text-center mt15 mb15"></div>
                <form id="<?php if ($_POST['id'] !== '') {
                    echo 'edit_project';
                } else {
                    echo 'add_project';
                } ?>" enctype="multipart/form-data" class="form-horizontal add_edit_project" role="form">

                    <?php

                    if ($_POST['id'] !== '') {
                        $id = $_POST['id'];
                        $sql = "select * from project_list where project_id=$id";
                        $project = mysqli_query($conn, $sql);
                        $row = mysqli_fetch_assoc($project);
                    }
                    ?>
                    <input type="hidden" id="project_id" name="project_id" value="<?php if ($_POST['id'] !== '') {
                        echo $row['project_id'];
                    } ?>"/>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="project_name">Project Name</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="project_name" name="project_name"
                                   value="<?php if ($_POST['id'] !== '') {
                                       echo $row['project_name'];
                                   } ?>" placeholder="Enter your Project name here..">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="project_logo">Project Logo</label>
                        <!--<div class="col-sm-10">
                           <input type="file" id="project_logo" name="image" placeholder="Select Your Project Logo here..">             
                        </div>-->
                        <div class="col-lg-8">
                            <div class="fileupload fileupload-new" name="image" id="image" data-provides="fileupload">
                    <span class="btn btn-file btn-default">
                        <span class="fileupload-new">Select file</span>
                        <span class="fileupload-exists">Change</span>
                        <input type="file" name="image" id="image"/>
                    </span>
                                <span class="fileupload-preview"></span>
                                <a href="#" class="close fileupload-exists" data-dismiss="fileupload"
                                   style="float: none">×</a>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="project_Category">Project Category</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="project_Category" name="project_Category"
                                   value="<?php if ($_POST['id'] !== '') {
                                       echo $row['project_category'];
                                   } ?>" placeholder="Enter Your Project Category here..">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="project_fund_goal">Project Fund Goal</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="project_fund_goal" name="project_fund_goal"
                                   value="<?php if ($_POST['id'] !== '') {
                                       echo $row['project_fund_goal'];
                                   } ?>" placeholder="Enter Your Project Fund Goal here..">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="project_amt_invested">Amount Invested</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="project_amt_invested"
                                   name="project_amt_invested" value="<?php if ($_POST['id'] !== '') {
                                echo $row['project_amt_invested'];
                            } ?>" placeholder="Enter Invested Amount For this project here..">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="project_fund_pool">Project Fund Pool</label>
                        </td>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="project_fund_pool" name="project_fund_pool"
                                   value="<?php if ($_POST['id'] !== '') {
                                       echo $row['project_fund_pool'];
                                   } ?>" placeholder="Enter Fund Pool For this project here..">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="project_angels">Project Angels</label>
                        </td>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="project_angels" name="project_angels"
                                   value="<?php if ($_POST['id'] !== '') {
                                       echo $row['project_angels'];
                                   } ?>" placeholder="Enter Angels For this project here..">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="project_fund_period_from">Starting Fund Date</label>
                        <div class="col-sm-10">
                            <input type="date" class="form-control" id="project_fund_period_from"
                                   name="project_fund_period_from" value="<?php if ($_POST['id'] !== '') {
                                echo $row['project_fund_period_from'];
                            } ?>" placeholder="Starting Fund Date..">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="project_fund_period_to">Ending Fund Date</label>
                        <div class="col-sm-10">
                            <input type="date" class="form-control" id="project_fund_period_to"
                                   name="project_fund_period_to" value="<?php if ($_POST['id'] !== '') {
                                echo $row['project_fund_period_to'];
                            } ?>" placeholder="Ending Fund Date..">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="project_video">Project Video url</label>
                        </td>
                        <div class="col-sm-10">
                            <input type="url" class="form-control" id="project_video" name="project_video"
                                   value="<?php if ($_POST['id'] !== '') {
                                       echo $row['project_video'];
                                   } ?>" placeholder="Project Video url..">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="project_short_desc">Project Short
                            Description </label>
                        <div class="col-sm-10">
                            <textarea class="form-control" id="project_short_desc" name="project_short_desc" rows="3"
                                      placeholder="Enter your Project Short Description here.."><?php if ($_POST['id'] !== '') {
                                    echo $row['project_desc_short'];
                                } ?></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="project_vision">Project Vision</label>
                        <div class="col-sm-10">
                            <textarea type="text" class="form-control" id="project_vision" name="project_vision"
                                      rows="3" placeholder="Project Vision.."><?php if ($_POST['id'] !== '') {
                                    echo $row['project_vision'];
                                } ?></textarea>

                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="project_problem">Project Problem</label>
                        <div class="col-sm-10">
                            <textarea type="text" class="form-control" id="project_problem" name="project_problem"
                                      rows="3" placeholder="Project Problem.."><?php if ($_POST['id'] !== '') {
                                    echo $row['project_problem'];
                                } ?></textarea>

                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="project_solution_video">Project Solution Video
                            url</label>
                        <div class="col-sm-10">
                            <input type="url" class="form-control" id="project_solution_video"
                                   name="project_solution_video" value="<?php if ($_POST['id'] !== '') {
                                echo $row['project_solution_video'];
                            } ?>" placeholder="Project Solution Video url..">

                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="project_advantage">Project Advantage</label>
                        <div class="col-sm-10">
                            <textarea type="text" class="form-control" id="project_advantage" name="project_advantage"
                                      rows="3" placeholder="Project Advantage.."><?php if ($_POST['id'] !== '') {
                                    echo $row['project_advantage'];
                                } ?></textarea>

                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="project_other_details">Project Other Details</label>
                        <div class="col-sm-10">
                            <textarea type="text" class="form-control" id="project_other_details"
                                      name="project_other_details" rows="3"
                                      placeholder="Project Other Detals like End User Engagement, Marketing Strategy, Merchants/ Business Professional Engagments, ifindcard Business Manager, ifindcard 2.0 .. "><?php if ($_POST['id'] !== '') {
                                    echo $row['project_other_details'];
                                } ?></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-default"><?php if ($_POST['id'] !== '') {
                                    echo 'Update';
                                } else {
                                    echo 'Submit';
                                } ?></button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
<?php
}
//add_edit_project_UI end//

//add and update or edit project start//
if($_POST['action'] && ($_POST['action']=="add_project") || ($_POST['action']=="edit_project")){

$logo = '';
$project_name = ($_POST["project_name"]);
$project_Category = ($_POST["project_Category"]);
$project_fund_goal = ($_POST["project_fund_goal"]);
$project_amt_invested = ($_POST["project_amt_invested"]);
$project_fund_pool = ($_POST["project_fund_pool"]);
$project_angels = ($_POST["project_angels"]);
$project_fund_period_from = ($_POST["project_fund_period_from"]);
$project_fund_period_to = ($_POST["project_fund_period_to"]);
$project_video = ($_POST["project_video"]);
$project_short_desc = ($_POST['project_short_desc']);
$project_vision = ($_POST["project_vision"]);
$project_problem = ($_POST['project_problem']);
$project_solution_video = ($_POST["project_solution_video"]);
$project_advantage = ($_POST["project_advantage"]);
$project_other_details = ($_POST["project_other_details"]);
$logofile = $_FILES['image']['name'];
$tmp_dir = $_FILES['image']['tmp_name'];
$logosize = $_FILES['image']['size'];


if (!empty($logofile)) {
    $upload_dir = '../assets/img/';
    $imgExt = strtolower(pathinfo($logofile, PATHINFO_EXTENSION));
    $valid_extensions = array('jpeg', 'jpg', 'png', 'gif');
    $logo = rand(1000, 1000000) . "." . $imgExt;

    if (in_array($imgExt, $valid_extensions)) {

        if ($logosize < 5000000) {
            move_uploaded_file($tmp_dir, $upload_dir . $logo);
        } else {
            $errMSG = "Sorry, your file is too large.";
        }
    } else {
        $errMSG = "Sorry, only JPG, JPEG, PNG & GIF files are allowed.";
    }
}

if (!isset($errMSG)) {

    if ($_POST['action'] == 'add_project') {

        $query = "INSERT INTO project_list (project_name, project_Category, project_fund_goal, project_amt_invested, project_fund_pool, project_angels, project_fund_period_from, project_fund_period_to, project_video, project_desc_short, project_vision, project_problem, project_solution_video, project_advantage, project_other_details, project_logo) VALUES ('$project_name','$project_Category','$project_fund_goal','$project_amt_invested','$project_fund_pool','$project_angels','$project_fund_period_from','$project_fund_period_to','$project_video','$project_short_desc','$project_vision','$project_problem','$project_solution_video', '$project_advantage', '$project_other_details','$logo')";

    } else if ($_POST['action'] == 'edit_project') {
        $project_id = ($_POST["project_id"]);
        $query = "UPDATE  project_list SET project_name = '$project_name', project_Category = '$project_Category', project_fund_goal = '$project_fund_goal', project_amt_invested = '$project_amt_invested', project_fund_pool = '$project_fund_pool', project_angels = '$project_angels', project_fund_period_from = '$project_fund_period_from', project_fund_period_to = '$project_fund_period_to', project_video = '$project_video', project_desc_short = '$project_short_desc', project_vision = '$project_vision', project_problem = '$project_problem', project_solution_video = '$project_solution_video', project_advantage = '$project_advantage', project_other_details = '$project_other_details', project_logo = '$logo' WHERE project_id=$project_id";
    }

    if (mysqli_query($conn, $query)) {
        if ($_POST['action'] == 'add_project') {
            echo "Your Project Added Successfully..";
        } else if ($_POST['action'] == 'edit_project') {
            echo "Your Project Updated Successfully..";
        }
    } else {
        //echo "Error While Inserting Data..";
        echo "Error: " . $query . "<br>" . mysqli_error($conn);
    }
    mysqli_close($conn);
}
}
//add and update or edit project end//

//disable project start//
if($_POST['action'] && $_POST['action']=="disable_project"){
	
	$project_id = $_POST['id'];
	
	$sql = "UPDATE project_list SET status = 'disabled' WHERE project_id = $project_id";
	
	if(mysqli_query($conn, $sql)){
		return;
	}
}
//disable project end//

//activate project start//
if($_POST['action'] && $_POST['action']=="enable_project"){
	
	$project_id = $_POST['id'];
	
	$sql = "UPDATE project_list SET status = 'active' WHERE project_id = $project_id";
	
	if(mysqli_query($conn, $sql)){
		return;
	}
}
//activate project end//

//add country UI start//  
if ($_POST['action'] && $_POST['action'] == "add_country_UI") { ?>
    <div class="page-title">
        <div class="title_left">
            <h3>Project Management Panel</h3>
        </div>
    </div>
    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-6">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Country</h2>
                    <div class="clearfix"></div>
                </div>
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th style="width: 70%">Country</th>
                        <th style="width: 30%">Action</th>
                    </tr>
                    </thead>
                    <tbody>

                    <?php

                    $sql = "SELECT * from country";

                    $result = mysqli_query($conn, $sql);

                    if (mysqli_num_rows($result) > 0) {
                        while ($row = mysqli_fetch_assoc($result)) {

                            echo '<tr><td>' . $row['country'] . '</td><td><a href="#" onclick="remove_country(' . $row['country_id'] . ')" class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i> Remove </a></td></tr>';
                        }
                    } else {
                        echo '<tr>No Countries here.. Please Add..</tr>';
                    }
                    ?>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="col-md-6">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Add Country</h2>
                    <div class="clearfix"></div>
                </div>
                <div id="add_country_msg" class="text-center mt15 mb15"></div>
                <form id="add_country" enctype="multipart/form-data" class="form-horizontal" role="form">
                    <div class="form-group">
                        <label class="control-label col-sm-3" for="country_name">Country Name</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="country_name" name="country_name"
                                   placeholder="Enter Country Name" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-6 col-sm-6 mt20">
                            <button type="submit" class="btn btn-primary">Add</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <script>


    </script>
<?php
}
//add country UI end//

//add country start//
if ($_POST['action'] && $_POST['action'] == "add_country") {

    $country = ($_POST["country"]);

    $query = "INSERT INTO country (country) VALUES ('$country')";

    if (@mysqli_query($conn, $query)) {
        echo "Country Added Successfully..";
    } else {
        //echo "Error While Inserting Data..";
        echo "Error: " . $query . "<br>" . mysqli_error($conn);
    }
    mysqli_close($conn);
}
//add country start//

//remove country start//
if ($_POST['action'] && $_POST['action'] == "remove_country") {

    $id = ($_POST["id"]);

    $query = "DELETE FROM country WHERE country_id = $id";

    if (@mysqli_query($conn, $query)) {
        echo "Country Removed Successfully..";
    } else {
        //echo "Error While Inserting Data..";
        echo "Error: " . $query . "<br>" . mysqli_error($conn);
    }
    mysqli_close($conn);
}
//remove country end//

//team members list start//
if($_POST['action'] && $_POST['action']=="team_members_UI")
{
	$sql = "SELECT * from team_members";	
	$result = mysqli_query($conn, $sql);
	
	echo '<div class="page-title">
			<div class="title_left">
				<h3>Project Management Panel</h3>
			</div>
		  </div> 
		  <div class="clearfix"></div> 
		  <div class="row"> 
		  <div class="col-md-12"> 
		  <div class="x_panel">
		  
		    <div class="x_title">
			  <h2>Team Members</h2>
			  <ul class="nav navbar-right mt15">
			  	<a href="#" onclick="add_edit_member_UI();" class="text-capitalize btn btn-info"><i class="fa fa-plus"></i> Add New Member </a>
			  </ul>
		    <div class="clearfix"></div>
		    </div>
		  
			<table class="table table-striped projects">
			<thead>
				<tr>					
					<th style="width:20%">Member Name</th>                       
					<th style="width:20%">Member Profession</th>
					<th style="width:20%">Project</th>
					<th style="width:20%">Action</th>
				</tr>
			</thead>
            <tbody>';

	while ($row = mysqli_fetch_assoc($result)){
		
		echo '<tr>
			<td><img src="../assets/img/'.$row['team_member_photo'].'" class="avatar" alt="Avatar"> &nbsp;&nbsp;'.$row['team_member_name'].'</td>
			<td>'.$row['team_member_profession'].'</td>';
			
			$pjt_id = $row['project_id'];
			$pjt_sql = "SELECT project_name from project_list WHERE project_id=$pjt_id";
			$pjtrow = mysqli_fetch_assoc(mysqli_query($conn, $pjt_sql));
			
			echo '<td>'.$pjtrow['project_name'].'</td>
				<td>
				<a href="#" onclick="add_edit_member_UI('.$row['team_member_id'].')" class="btn btn-info btn-xs"><i class="fa fa-pencil"></i> Edit </a>
				<a href="#" onclick="remove_member('.$row['team_member_id'].')" class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i> Remove </a>
				</td>
				</tr>';			
	}
	
	echo '</tbody></table></div></div></div>';	
	
}
//team members list end//

//add edit team member UI start//
if($_POST['action'] && $_POST['action']=="add_edit_member_UI")
{
?>
<div class="page-title mb25">
<div class="title_left">
<h3>Project Management Panel</h3>
</div>
</div> 
<div class="clearfix"></div> 
<div class="row"> 
<div class="col-md-offset-2 col-md-8">
 <div class="x_panel">
 <div class="x_title">  
  <h2><?php if($_POST['id']!==''){echo 'Edit Team Member'; } else {echo 'Add New Team Member';}?></h2> 
 <div class="clearfix"></div>
 </div>
 <div id="<?php if($_POST['id']!==''){echo 'edit_member_msg'; } else {echo 'add_member_msg';}?>" class="text-center mt15 mb15"></div>
    <form id="<?php if($_POST['id']!==''){echo 'edit_member'; } else {echo 'add_member';}?>" enctype="multipart/form-data" class="form-horizontal" role="form">
        <?php if($_POST['id']!==''){echo '<input type="hidden" id="team_member_id" name="team_member_id" value="'.$_POST['id'].'"></input>';}?>
        <div class="form-group">
            <label class="control-label col-sm-3" for="project_name">Project Name</label>
            <div class="col-sm-9 col-md-9">
                <select class="form-control placeholder" id="project_name" name="project_name" required>
                <option value="" selected disabled>Please Select Project</option>
                <?php
                $query = "select project_id, project_name from project_list";
                $result = mysqli_query($conn, $query);
                while($record = mysqli_fetch_assoc($result))
                {                               
                ?>
                <option value="<?php echo $record['project_id'] ?>" <?php

                if($_POST['id']!==''){
                  $id=$_POST['id'];
                  $sql= "select * from team_members where team_member_id=$id";
                  $team = mysqli_query($conn, $sql);
                  $row=mysqli_fetch_assoc($team);
                
                if ($row['project_id'] === $record['project_id']){ echo 'selected="selected"';}}?>><?php echo $record['project_name'] ?></option>
                <?php
                }
                mysqli_free_result($result);
                ?>
              </select>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-3" for="team_member_name">Member Name</label>
            <div class="col-sm-9">
                <input type="text" class="form-control" id="team_member_name" name="team_member_name" value="<?php if($_POST['id']!==''){echo $row['team_member_name']; }?>" placeholder="Enter Team Member name here..">
            </div>
        </div>
		<div class="form-group">
            <label class="control-label col-sm-3" for="member_photo">Project Logo</label>
            <!--<div class="col-sm-10">
               <input type="file" id="project_logo" name="image" placeholder="Select Your Project Logo here..">				
            </div>-->
			<div class="col-lg-9">
                <div class="fileupload fileupload-new" name="image" id="image" data-provides="fileupload">
                    <span class="btn btn-file btn-default">
                        <span class="fileupload-new">Select file</span>
                        <span class="fileupload-exists">Change</span>
                        <input type="file" name="image" id="image"/>
                    </span>
                    <span class="fileupload-preview"></span>
                    <a href="#" class="close fileupload-exists" data-dismiss="fileupload" style="float: none">×</a>
                </div>
            </div>
        </div>
		<div class="form-group">
            <label class="control-label col-sm-3" for="team_member_profession">Member Profession</label>
            <div class="col-sm-9">
                <input type="text" class="form-control" id="team_member_profession" name="team_member_profession" value="<?php if($_POST['id']!==''){echo $row['team_member_profession']; }?>" placeholder="Enter member profession here..">
            </div>
        </div>        
        <div class="form-group">
            <label class="control-label col-sm-3" for="team_member_intro">Project Fund Goal</label>
            <div class="col-sm-9">
                <textarea class="form-control" id="team_member_intro" name="team_member_intro" placeholder="Team Member Intro.."><?php if($_POST['id']!==''){echo $row['team_member_intro']; }?></textarea>
            </div>
        </div>        
        <div class="form-group">
            <div class="col-sm-offset-6 col-sm-6 mt20">
                <button type="submit" class="btn btn-primary">Submit</button>				
            </div>
        </div>
    </form>
</div>
</div>
</div>
<?php
}
//add edit team member UI end//

//add edit team member start//
if($_POST['action'] && ($_POST['action']=="add_member") || ($_POST['action']=="edit_member")){

$image = 'dummy.jpg';
$member_name = ($_POST["team_member_name"]);
$member_profession = ($_POST["team_member_profession"]);
$member_intro = ($_POST["team_member_intro"]);
$project_id = ($_POST["project_name"]);
$imagefile = $_FILES['image']['name'];
$tmp_dir = $_FILES['image']['tmp_name'];
$imagesize = $_FILES['image']['size'];


if (!empty($imagefile)) {
    $upload_dir = '../assets/img/';
    $imgExt = strtolower(pathinfo($imagefile, PATHINFO_EXTENSION));
    $valid_extensions = array('jpeg', 'jpg', 'png', 'gif');
    $image = rand(1000, 1000000) . "." . $imgExt;

    if (in_array($imgExt, $valid_extensions)) {

        if ($imagesize < 5000000) {
            move_uploaded_file($tmp_dir, $upload_dir . $image);
        } else {
            $errMSG = "Sorry, your file is too large.";
        }
    } else {
        $errMSG = "Sorry, only JPG, JPEG, PNG & GIF files are allowed.";
    }
}

if (!isset($errMSG)) {

    if ($_POST['action'] == 'add_member') {
        $query = "INSERT INTO team_members (project_id, team_member_name, team_member_profession, team_member_intro, team_member_photo) VALUES ('$project_id','$member_name','$member_profession','$member_intro','$image')";

    } else if ($_POST['action'] == 'edit_member') {
        $team_member_id = ($_POST["team_member_id"]);
        $query = "UPDATE team_members SET project_id = '$project_id', team_member_name = '$member_name', team_member_profession = '$member_profession', team_member_intro = '$member_intro', team_member_photo = '$image' WHERE team_member_id=$team_member_id";
    }

    if (@mysqli_query($conn, $query)) {
        echo '<span class="success">Member Added / Updated Successfully..</span>';
    } else {
        echo '<span class="error">Error While Inserting Data..</span>';       
    }
    mysqli_close($conn);
}
}
//add edit team member end//

//remove team members start//
if($_POST['action'] && $_POST['action']=="remove_member"){
	
	$team_member_id = $_POST['id'];
	
	$sql = "DELETE FROM team_members WHERE team_member_id = $team_member_id";
	
	if(mysqli_query($conn, $sql)){
		return;
	}
}
//remove team members end//

//partners list start//
if($_POST['action'] && $_POST['action']=="partners_UI")
{
	$sql = "SELECT * from partners";
	
	$result = mysqli_query($conn, $sql);

	
	echo '<div class="page-title">
			<div class="title_left">
				<h3>Project Management Panel</h3>
			</div>
		  </div> 
		  <div class="clearfix"></div> 
		  <div class="row"> 
		  <div class="col-md-12"> 
		  <div class="x_panel">
		  
		    <div class="x_title">
			  <h2>Partners</h2>
			  <ul class="nav navbar-right mt10">
			  	<a href="#" onclick="add_edit_partner_UI();" class="text-capitalize btn btn-info"><i class="fa fa-plus"></i> Add New Partner </a>
			  </ul>
		    <div class="clearfix"></div>
		    </div>
		  
			<table class="table table-striped projects">
			<thead>
				<tr>					
					<th style="width: 20%">Partner Name</th>                       
					<th style="width: 20%">Partner Profession</th>					
					<th style="width: 20%">Partner Country</th>
					<th style="width: 20%">Action</th>
				</tr>
			</thead>
            <tbody>';

	while ($row = mysqli_fetch_assoc($result)) {
		
		echo '<tr>
			<td>'.$row['partner_name'].'</td>
			<td>'.$row['partner_profession'].'</td>
			<td>'.$row['partner_country'].'</td>
			<td>
				<a href="#" onclick="add_edit_partner_UI('.$row['partners_id'].')" class="btn btn-info btn-xs"><i class="fa fa-pencil"></i> Edit </a> 
				<a href="#" onclick="remove_partner('.$row['partners_id'].')" class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i> Remove </a> 
				</td>
				</tr>';				
	}
	echo '</tbody></table></div></div></div>';		
}
//partners list end//

//add edit partners UI start//
if($_POST['action'] && $_POST['action']=="add_edit_partner_UI")
{
?>
<div class="page-title mb25">
<div class="title_left">
<h3>Project Management Panel</h3>
</div>
</div> 
<div class="clearfix"></div> 
<div class="row"> 
<div class="col-md-offset-2 col-md-8"> 
 <div class="x_panel">
 <div class="x_title">  
  <h2><?php if($_POST['id']!==''){echo 'Edit Partner'; } else {echo 'Add New Partner';}?></h2>
 <div class="clearfix"></div>
 </div>
 <div id="<?php if($_POST['id']!==''){echo 'edit_partner_msg'; } else {echo 'add_partner_msg';}?>" class="text-center mt15 mb15"></div>
    <form id="<?php if($_POST['id']!==''){echo 'edit_partner'; } else {echo 'add_partner';}?>" enctype="multipart/form-data" class="form-horizontal" role="form">   
    <?php if($_POST['id']!==''){echo '<input type="hidden" id="partner_id" name="partner_id" value="'.$_POST['id'].'"></input>';}?>

   <?php
   if($_POST['id']!==''){
        $id=$_POST['id'];
        $sql= "select * from partners where partners_id=$id";
        $partner = mysqli_query($conn, $sql);
        $row=mysqli_fetch_assoc($partner);
    }
    ?>

        <div class="form-group">
            <label class="control-label col-sm-3" for="partner_name">Partner Name</label>
            <div class="col-sm-9">
                <input type="text" class="form-control" id="partner_name" name="partner_name" value="<?php if($_POST['id']!==''){echo $row['partner_name']; }?>" placeholder="Enter Partner name here..">
            </div>
        </div>
		<div class="form-group">
            <label class="control-label col-sm-3" for="partner_logo">Partner Logo</label>
            <!--<div class="col-sm-10">
               <input type="file" id="project_logo" name="image" placeholder="Select Your Project Logo here..">				
            </div>-->
			<div class="col-sm-9">
                <div class="fileupload fileupload-new" name="image" id="image" data-provides="fileupload">
                    <span class="btn btn-file btn-default">
                        <span class="fileupload-new">Select file</span>
                        <span class="fileupload-exists">Change</span>
                        <input type="file" name="image" id="image"/>
                    </span>
                    <span class="fileupload-preview"></span>
                    <a href="#" class="close fileupload-exists" data-dismiss="fileupload" style="float: none">×</a>
                </div>
            </div>
        </div>
		<div class="form-group">
            <label class="control-label col-sm-3" for="partner_profession">Partner Profession</label>
            <div class="col-sm-9">
                <input type="text" class="form-control" id="partner_profession" name="partner_profession" value="<?php if($_POST['id']!==''){echo $row['partner_profession']; }?>" placeholder="Enter member profession here..">
            </div>
        </div>  
        <div class="form-group">
            <label class="control-label col-sm-3" for="partner_country">Partner Country</label>
            <div class="col-sm-9">
                <select class="form-control placeholder" id="project_name" name="partner_country" required>
                <option value="" selected disabled>Please Select Country</option>
                <?php
                $query = "select * from country";
                $result = mysqli_query($conn, $query);
                while($record = mysqli_fetch_assoc($result))
                {                               
                ?>
                <option value="<?php echo $record['country'] ?>" <?php
                if($_POST['id']!==''){
                                 
                 if ($row['partner_country'] === $record['country']){ echo 'selected="selected"';}}?>><?php echo $record['country'] ?></option>
                <?php
                }
                
                ?>
              </select>
            </div>
        </div>      
        <div class="form-group">
            <label class="control-label col-sm-3" for="partner_intro">Partner Intro</label>
            <div class="col-sm-9">
                <textarea class="form-control" id="partner_intro" name="partner_intro" placeholder="Partner Intro.."><?php if($_POST['id']!==''){echo $row['partner_desc']; }?></textarea>
            </div>
        </div>        
        <div class="form-group">
            <div class="col-sm-offset-6 col-sm-6 mt20">
                <button type="submit" class="btn btn-primary">Submit</button>				
            </div>
        </div>
    </form>
</div>
</div>
</div>
<?php
}
//add edit partners UI end//

//add edit partners start//
if($_POST['action'] && ($_POST['action']=="add_partner") || ($_POST['action']=="edit_partner")){
$image = '';
$partner_name = ($_POST["partner_name"]);
$partner_profession = ($_POST["partner_profession"]);
$partner_country = ($_POST["partner_country"]);
$partner_intro = ($_POST["partner_intro"]);
$imagefile = $_FILES['image']['name'];
$tmp_dir = $_FILES['image']['tmp_name'];
$imagesize = $_FILES['image']['size'];


if (!empty($imagefile)) {
    $upload_dir = '../assets/img/';
    $imgExt = strtolower(pathinfo($imagefile, PATHINFO_EXTENSION));
    $valid_extensions = array('jpeg', 'jpg', 'png', 'gif');
    $image = rand(1000, 1000000) . "." . $imgExt;

    if (in_array($imgExt, $valid_extensions)) {

        if ($imagesize < 5000000) {
            move_uploaded_file($tmp_dir, $upload_dir . $image);
        } else {
            $errMSG = "Sorry, your file is too large.";
        }
    } else {
        $errMSG = "Sorry, only JPG, JPEG, PNG & GIF files are allowed.";
    }
}

if (!isset($errMSG)) {

    if ($_POST['action'] == 'add_partner') {

        $query = "INSERT INTO partners (partner_name, partner_country, partner_profession, partner_desc, partner_logo) VALUES ('$partner_name','$partner_country','$partner_profession','$partner_intro','$image')";

    } else if ($_POST['action'] == 'edit_partner') {
        $partner_id = ($_POST["partner_id"]);
        $query = "UPDATE  partners SET partner_name = '$partner_name', partner_country = '$partner_country', partner_profession = '$partner_profession', partner_desc = '$partner_intro', partner_logo = '$image' WHERE partners_id=$partner_id";
    }

    if (@mysqli_query($conn, $query)) {
        echo "Partner Added Successfully..";
    } else {
        //echo "Error While Inserting Data..";
        echo "Error: " . $query . "<br>" . mysqli_error($conn);
    }
    mysqli_close($conn);
}
}
//add edit partners end//

//remove partners start//
if($_POST['action'] && $_POST['action']=="remove_partner"){
	
	$partners_id = $_POST['id'];
	
	$sql = "DELETE FROM partners WHERE partners_id = $partners_id";
	
	if(mysqli_query($conn, $sql)){
		return;
	}
}
//remove partners end//

//compititors list start//
if ($_POST['action'] && $_POST['action'] == "competitors_UI") {
    $sql = "SELECT * from competitors";

    $result = mysqli_query($conn, $sql);


    echo '<div class="page-title">
			<div class="title_left">
				<h3>Project Management Panel</h3>
			</div>
		  </div> 
		  <div class="clearfix"></div> 
		  <div class="row"> 
		  <div class="col-md-12"> 
		  <div class="x_panel">
		  
		    <div class="x_title">
			  <h2>Competitors</h2>
			  <ul class="nav navbar-right mt10">
			  	<a href="#" onclick="add_edit_competitors_UI();" class="text-capitalize btn btn-info"><i class="fa fa-plus"></i> Add New Competitor </a>
			  </ul>
		    <div class="clearfix"></div>
		    </div>
		  
			<table class="table table-striped projects">
			<thead>
				<tr>					
					<th style="width: 20%">Competitor Name	</th>                       
					<th style="width: 20%">About Competitor</th>					
					<th style="width: 20%">Action</th>
				</tr>
			</thead>
            <tbody>';

    while ($row = mysqli_fetch_assoc($result)) {

        echo '<tr>			
			<td>' . $row['competitor_name'] . '</td>	
			<td>' . $row['competitor_intro'] . '</td>	
			<td>
				<a href="#" onclick="add_edit_competitors_UI(' . $row['competitor_id'] . ')" class="btn btn-info btn-xs"><i class="fa fa-pencil"></i> Edit </a>
				<a href="#" onclick="remove_competitors(' . $row['competitor_id'] . ')" class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i> Remove </a> </td>
				</tr>';
    }

    echo '</tbody></table></div></div></div>';

}
//compititors list end//

//add edit compititors UI start//
if ($_POST['action'] && $_POST['action'] == "add_edit_competitors_UI") {
    ?>
    <div class="page-title mb25">
        <div class="title_left">
            <h3>Project Management Panel</h3>
        </div>
    </div>
    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-offset-2 col-md-8">
            <div class="x_panel">
                <div class="x_title">
                    <h2><?php if ($_POST['id'] !== '') {
                            echo 'Edit Competitors';
                        } else {
                            echo 'Add New Competitors';
                        } ?></h2>
                    <div class="clearfix"></div>
                </div>
                <div id="<?php if ($_POST['id'] !== '') {
                    echo 'edit_competitor_msg';
                } else {
                    echo 'add_competitor_msg';
                } ?>" class="text-center mt15 mb15"></div>
                <form id="<?php if ($_POST['id'] !== '') {
                    echo 'edit_competitor';
                } else {
                    echo 'add_competitor';
                } ?>" enctype="multipart/form-data" class="form-horizontal" role="form">
                    <?php if ($_POST['id'] !== '') {
                        echo '<input type="hidden" id="competitor_id" name="competitor_id" value="' . $_POST['id'] . '"></input>';
                    } ?>

                    <div class="form-group">
                        <label class="control-label col-sm-3" for="project_name">Project Name</label>
                        <div class="col-sm-9 col-md-9">
                            <select class="form-control placeholder" id="project_name" name="project_name" required>
                                <option value="" selected disabled>Please Select Project</option>
                                <?php
                                $query = "select project_id, project_name from project_list";
                                $result = mysqli_query($conn, $query);
                                while ($record = mysqli_fetch_assoc($result)) {
                                    ?>
                                    <option value="<?php echo $record['project_id'] ?>" <?php

                                    if ($_POST['id'] !== '') {
                                        $id = $_POST['id'];
                                        $sql = "select * from competitors where competitor_id=$id";
                                        $competitor = mysqli_query($conn, $sql);
                                        $row = mysqli_fetch_assoc($competitor);

                                        if ($row['project_id'] === $record['project_id']) {
                                            echo 'selected="selected"';
                                        }
                                    } ?>><?php echo $record['project_name'] ?></option>
                                    <?php
                                }
                                mysqli_free_result($result);
                                ?>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3" for="competitor_name">Competitor Name</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="competitor_name" name="competitor_name"
                                   value="<?php if ($_POST['id'] !== '') {
                                       echo $row['competitor_name'];
                                   } ?>" placeholder="Enter Competitor Name here..">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3" for="member_photo">Competitor Logo</label>
                        <!--<div class="col-sm-10">
                           <input type="file" id="project_logo" name="image" placeholder="Select Your Competitor Logo here..">              
                        </div>-->
                        <div class="col-lg-9">
                            <div class="fileupload fileupload-new" data-provides="fileupload">
                    <span class="btn btn-file btn-default">
                        <span class="fileupload-new">Select file</span>
                        <span class="fileupload-exists">Change</span>
                        <input type="file" name="image" id="image"/>
                    </span>
                                <span class="fileupload-preview"></span>
                                <a href="#" class="close fileupload-exists" data-dismiss="fileupload"
                                   style="float: none">×</a>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3" for="competitor_intro">Competitor Intro</label>
                        <div class="col-sm-9">
                            <textarea class="form-control" id="competitor_intro" name="competitor_intro"
                                      placeholder="Enter Competitor Intro here.."><?php if ($_POST['id'] !== '') {
                                    echo $row['competitor_intro'];
                                } ?></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-5 col-sm-6 mt20">
                            <button type="submit" class="btn btn-primary"><?php if ($_POST['id'] !== '') {
                                    echo 'Update';
                                } else {
                                    echo 'Submit';
                                } ?></button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
<?php
}
//add edit compititors UI end//

//add edit compititors srart//
if($_POST['action'] && ($_POST['action']=="add_competitor") || ($_POST['action']=="edit_competitor")){
$image = 'placeholder.jpg';
$competitor_name = ($_POST["competitor_name"]);
$competitor_intro = ($_POST["competitor_intro"]);
$project_id = ($_POST["project_name"]);
$imagefile = $_FILES['image']['name'];
$tmp_dir = $_FILES['image']['tmp_name'];
$imagesize = $_FILES['image']['size'];


if (!empty($imagefile)) {
    $upload_dir = '../assets/img/';
    $imgExt = strtolower(pathinfo($imagefile, PATHINFO_EXTENSION));
    $valid_extensions = array('jpeg', 'jpg', 'png', 'gif');
    $image = rand(1000, 1000000) . "." . $imgExt;

    if (in_array($imgExt, $valid_extensions)) {

        if ($imagesize < 5000000) {
            move_uploaded_file($tmp_dir, $upload_dir . $image);
        } else {
            $errMSG = "Sorry, your file is too large.";
        }
    } else {
        $errMSG = "Sorry, only JPG, JPEG, PNG & GIF files are allowed.";
    }
}

if (!isset($errMSG)) {

    if ($_POST['action'] == 'add_competitor') {

        $query = "INSERT INTO competitors (project_id, competitor_name, competitor_intro, competitor_logo) VALUES ('$project_id','$competitor_name','$competitor_intro','$image')";

    } else if ($_POST['action'] == 'edit_competitor') {
        $competitor_id = ($_POST["competitor_id"]);
        $query = "UPDATE  competitors SET project_id = '$project_id', competitor_name = '$competitor_name', competitor_intro = '$competitor_intro', competitor_logo = '$image' WHERE competitor_id=$competitor_id";
    }

    if (@mysqli_query($conn, $query)) {
        if ($_POST['action'] == 'edit_competitor') {
            echo "Competitor Updated Successfully..";
        } else {
            echo "Competitor Added Successfully..";
        }
    } else {
        //echo "Error While Inserting Data..";
        echo "Error: " . $query . "<br>" . mysqli_error($conn);
    }

}
mysqli_close($conn);
}
//add edit compititors end//

//remove competitors start//
if($_POST['action'] && $_POST['action']=="remove_competitors"){
	
	$competitor_id = $_POST['id'];
	
	$sql = "DELETE FROM competitors WHERE competitor_id = $competitor_id";
	
	if(mysqli_query($conn, $sql)){
		return;
	}
}
//remove competitors end//

//traction testing list start//
if($_POST['action'] && $_POST['action']=="traction_testing_UI")
{
	echo '<div class="page-title">
			<div class="title_left">
				<h3>Project Management Panel</h3>
			</div>
		  </div>
		  <div class="clearfix"></div> 
		  <div class="row">
		  <div class="col-md-6"> 
		  <div class="x_panel">
		    <div class="x_title">
			  <h2>Traction</h2>
			  <ul class="nav navbar-right mt10">
			  	<a href="#" onclick="add_edit_traction_UI();" class="text-capitalize btn btn-info"><i class="fa fa-plus"> </i> Add New Traction </a>
			  </ul>
		    <div class="clearfix"></div>
		    </div>
			<table class="table table-striped projects">
			<thead>
				<tr>					
					<th style="width: 55%">Traction Name</th>
					<th style="width: 10%">Status</th>					                       					
					<th style="width: 35%">Action</th>
				</tr>
			</thead>
            <tbody>';

    $sql = "SELECT * from traction";
	$result = mysqli_query($conn, $sql);

	while ($row = mysqli_fetch_assoc($result)) {
		
		echo '<tr>			
			<td>'.$row['traction_name'].'<br/>'.$row['traction_name_desc'].'</td>';
			if($row['traction_status']==0){
				echo '<td>Disabled</td>';
			}else if($row['traction_status']==1){
				echo '<td>Active</td>';
			}
			echo '<td onclick="add_edit_traction_UI('.$row['traction_id'].')">
				<a href="#" class="btn btn-info btn-xs"><i class="fa fa-pencil"></i> Edit </a> ';
			if($row['traction_status']==0){
				echo '<a href="#" onclick="enable_traction('.$row['traction_id'].')" class="btn btn-success btn-xs"><i class="fa fa-pencil"></i> Enable </a></td></tr>';
			}else if($row['traction_status']==1){
				echo '<a href="#" onclick="disable_traction('.$row['traction_id'].')" class="btn btn-danger btn-xs"><i class="fa fa-times"></i> Disable </a></td></tr>';
			}
	}	
	echo '</tbody></table></div></div>	
		<div class="col-md-6"> 
		  <div class="x_panel">
		    <div class="x_title">
			  <h2>Testing</h2>
			  <ul class="nav navbar-right mt10">
			  	<a href="#" onclick="add_edit_testing_UI();" class="text-capitalize btn btn-info"><i class="fa fa-plus"></i> Add New Testing </a>
			  </ul>
		    <div class="clearfix"></div>
		    </div>
			<table class="table table-striped projects">
			<thead>
				<tr>					
					<th style="width: 55%">Testing Name</th>
					<th style="width: 10%">Status</th>					                       					
					<th style="width: 35%">Action</th>
				</tr>
			</thead>
            <tbody>';

    $sql = "SELECT * from testing";
	$result = mysqli_query($conn, $sql);

	while ($row = mysqli_fetch_assoc($result)) {
		
		echo '<tr>			
			<td>'.$row['testing_name'].'<br/>'.$row['testing_name_desc'].'</td>';
			if($row['testing_status']==0){
				echo '<td>Disabled</td>';
			}else if($row['testing_status']==1){
				echo '<td>Active</td>';
			}
			echo '<td onclick="add_edit_testing_UI('.$row['testing_id'].')">
				<a href="#" class="btn btn-info btn-xs"><i class="fa fa-pencil"></i> Edit </a> '; 

			if($row['testing_status']==0){
			echo '<a href="#" onclick="enable_testing('.$row['testing_id'].')" class="btn btn-success btn-xs"><i class="fa fa-pencil"></i> Enable </a></td></tr>';
			}else if($row['testing_status']==1){
			echo '<a href="#" onclick="disable_testing('.$row['testing_id'].')" class="btn btn-danger btn-xs"><i class="fa fa-times"></i> Disable </a></td></tr>';
			}	
	}	
	echo '</tbody></table></div></div></div>';	
}
//traction testing list end//

//traction testing add edit UI start//

if($_POST['action'] && $_POST['action']=="add_edit_traction_UI")
{   
?>
<div class="page-title mb25">
<div class="title_left">
<h3>Project Management Panel</h3>
</div>
</div> 
<div class="clearfix"></div> 
<div class="row"> 
<div class="col-md-offset-3 col-md-6"> 
 <div class="x_panel">
 <div class="x_title">
  <h2><?php if($_POST['id']!==''){echo 'Edit Traction'; } else {echo 'Add New Traction';}?></h2>  
 <div class="clearfix"></div>
 </div>
 <div id="<?php if($_POST['id']!==''){echo 'edit_traction_msg'; } else {echo 'add_traction_msg';}?>" class="text-center mt15 mb15"></div>
    <form id="<?php if($_POST['id']!==''){echo 'edit_traction'; } else {echo 'add_traction';}?>" enctype="multipart/form-data" class="form-horizontal" role="form">
    <?php if($_POST['id']!==''){echo '<input type="hidden" id="traction_id" name="traction_id" value="'.$_POST['id'].'"></input>';}?>
        
        <div class="form-group">
            <label class="control-label col-sm-3" for="project_name">Project Name</label>
            <div class="col-sm-9 col-md-9">
                <select class="form-control placeholder" id="project_name" name="project_name" required>           
                <option value="" selected disabled>Please Select Project</option>
                <?php
                $query = "select project_id, project_name from project_list";
                $result = mysqli_query($conn, $query);
                while($record = mysqli_fetch_assoc($result))
                {                               
                ?>
                <option value="<?php echo $record['project_id'] ?>" <?php

                if($_POST['id']!==''){
                  $id=$_POST['id'];
                  $sql= "select * from traction where traction_id=$id";
                  $traction = mysqli_query($conn, $sql);
                  $row=mysqli_fetch_assoc($traction);
                
                 if ($row['project_id'] === $record['project_id']){ echo 'selected="selected"';}}?>><?php echo $record['project_name'] ?></option>
                <?php
                }
                mysqli_free_result($result);
                ?>
              </select>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-3" for="traction_name">Traction Name</label>
            <div class="col-sm-9">
                <input type="text" class="form-control" id="traction_name" name="traction_name" value="<?php if($_POST['id']!==''){echo $row['traction_name']; }?>" placeholder="Enter traction name here.." required>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-3" for="traction_requirement">Traction Requirement</label>
            <div class="col-sm-9">
                <input type="text" class="form-control" id="traction_requirement" name="traction_requirement" value="<?php if($_POST['id']!==''){echo $row['traction_name_desc']; }?>" placeholder="Enter traction requirement here.." required>
            </div>
        </div>        
        <div class="form-group">
            <div class="col-sm-offset-5 col-sm-6 mt20">
                <button type="submit" class="btn btn-primary">Submit</button>               
            </div>
        </div>
    </form>
</div>
</div>
</div>
<?php
}

if($_POST['action'] && $_POST['action']=="add_edit_testing_UI")
{   ?>
<div class="page-title mb25">
<div class="title_left">
<h3>Project Management Panel</h3>
</div>
</div> 
<div class="clearfix"></div> 
<div class="row"> 
<div class="col-md-offset-3 col-md-6"> 
 <div class="x_panel">
 <div class="x_title">
  <h2><?php if($_POST['id']!==''){echo 'Edit Testing'; } else {echo 'Add New Testing';}?></h2>
 <div class="clearfix"></div>
 </div>
 <div id="<?php if($_POST['id']!==''){echo 'edit_testing_msg'; } else {echo 'add_testing_msg';}?>" class="text-center mt15 mb15"></div>
    <form id="<?php if($_POST['id']!==''){echo 'edit_testing'; } else {echo 'add_testing';}?>" enctype="multipart/form-data" class="form-horizontal" role="form">
        <div class="form-group">
        <?php if($_POST['id']!==''){echo '<input type="hidden" id="testing_id" name="testing_id" value="'.$_POST['id'].'"></input>';}?>
            <label class="control-label col-sm-3" for="project_name">Project Name</label>
            <div class="col-sm-9">
                <select class="form-control placeholder" id="project_name" name="project_name" required>
                <option value="" selected disabled>Please Select Project</option>
                <?php
                $query = "select project_id, project_name from project_list";
                $result = mysqli_query($conn, $query);
                while($record = mysqli_fetch_assoc($result))
                {                               
                ?>
                <option value="<?php echo $record['project_id'] ?>" <?php

                if($_POST['id']!==''){
                  $id=$_POST['id'];
                  $sql= "select * from testing where testing_id=$id";
                  $traction = mysqli_query($conn, $sql);
                  $row=mysqli_fetch_assoc($traction);
                
                 if ($row['project_id'] === $record['project_id']){ echo 'selected="selected"';}}?>><?php echo $record['project_name'] ?></option>
                <?php
                }
                mysqli_free_result($result);
                ?>
              </select>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-3" for="testing_name">Testing Name</label>
            <div class="col-sm-9">
                <input type="text" class="form-control" id="testing_name" name="testing_name" value="<?php if($_POST['id']!==''){echo $row['testing_name']; }?>" placeholder="Enter testing name here.." required>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-3" for="testing_requirement">Testing Requirement</label>
            <div class="col-sm-9">
                <input type="text" class="form-control" id="testing_requirement" name="testing_requirement" value="<?php if($_POST['id']!==''){echo $row['testing_name_desc']; }?>" placeholder="Enter traction requirement here.." required>
            </div>
        </div>        
        <div class="form-group">
            <div class="col-sm-offset-6 col-sm-4 mt20">
                <button type="submit" class="btn btn-primary">Submit</button>               
            </div>
        </div>
    </form>
</div>
</div>
</div>

<?php
}
//traction testing add edit UI end//

//add testing start//
if($_POST['action']=='add_testing'){

$testing_name = ($_POST["testing_name"]);
$testing_requirement = ($_POST["testing_requirement"]);
$project_id = ($_POST["project_name"]);
       
	$query = "INSERT INTO testing (project_id, testing_name, testing_name_desc) VALUES ('$project_id','$testing_name','$testing_requirement')";
		
		if (@mysqli_query($conn, $query)) 
		{
			echo "Testing Added Successfully..";
		}
		else
		{
			//echo "Error While Inserting Data..";
			echo "Error: " . $query . "<br>" . mysqli_error($conn);
		}    
	mysqli_close($conn);
}
//add testing end//

//edit testing start//
if($_POST['action']=='edit_testing'){
$testing_id = ($_POST["testing_id"]);
$testing_name = ($_POST["testing_name"]);
$testing_requirement = ($_POST["testing_requirement"]);
$project_id = ($_POST["project_name"]);
       
	$query = "UPDATE testing SET project_id = '$project_id', testing_name = '$testing_name', testing_name_desc = '$testing_requirement' WHERE testing_id=$testing_id";
		
		if (@mysqli_query($conn, $query)) 
		{
			echo "Testing Updated Successfully..";
		}
		else
		{
			//echo "Error While Inserting Data..";
			echo "Error: " . $query . "<br>" . mysqli_error($conn);
		}    
	mysqli_close($conn);
}
//edit testing end//

//add traction start//
if($_POST['action']=='add_traction'){

$traction_name = ($_POST["traction_name"]);
$traction_requirement = ($_POST["traction_requirement"]);
$project_id = ($_POST["project_name"]);
       
	$query = "INSERT INTO traction (project_id, traction_name, traction_name_desc) VALUES ('$project_id','$traction_name','$traction_requirement')";
		
		if (@mysqli_query($conn, $query)) 
		{
			echo "Traction Added Successfully..";
		}
		else
		{
			//echo "Error While Inserting Data..";
			echo "Error: " . $query . "<br>" . mysqli_error($conn);
		}    
	mysqli_close($conn);
}
//add traction end//

//edit traction start//
if($_POST['action']=='edit_traction'){
$traction_id = ($_POST["traction_id"]);
$traction_name = ($_POST["traction_name"]);
$traction_requirement = ($_POST["traction_requirement"]);
$project_id = ($_POST["project_name"]);
       
	$query = "UPDATE traction SET project_id = '$project_id', traction_name = '$traction_name', traction_name_desc = '$traction_requirement' WHERE traction_id=$traction_id";
		
		if (@mysqli_query($conn, $query)) 
		{
			echo "Traction Updated Successfully..";
		}
		else
		{
			//echo "Error While Inserting Data..";
			echo "Error: " . $query . "<br>" . mysqli_error($conn);
		}    
	mysqli_close($conn);
}
//edit traction end//

//enable testing
if($_POST['action'] && $_POST['action']=="enable_testing"){
	
	$testing_id = $_POST['id'];
	
	$sql = "UPDATE testing SET testing_status = 1 WHERE testing_id = $testing_id";
	
	if(mysqli_query($conn, $sql)){
		return;
	}
}

//disable testing
if($_POST['action'] && $_POST['action']=="disable_testing"){
	
	$testing_id = $_POST['id'];
	
	$sql = "UPDATE testing SET testing_status = 0 WHERE testing_id = $testing_id";
	
	if(mysqli_query($conn, $sql)){
		return;
	}
}

//enable traction

if($_POST['action'] && $_POST['action']=="enable_traction"){
	
	$traction_id = $_POST['id'];
	
	$sql = "UPDATE traction SET traction_status = 1 WHERE traction_id = $traction_id";
	
	if(mysqli_query($conn, $sql)){
		return;
	}
}

//disable traction
if($_POST['action'] && $_POST['action']=="disable_traction"){
	
	$traction_id = $_POST['id'];
	
	$sql = "UPDATE traction SET traction_status = 0 WHERE traction_id = $traction_id";
	
	if(mysqli_query($conn, $sql)){
		return;
	}
}

//get transaction details//
if($_POST['action'] && $_POST['action']=="transection_details")
{
	$sql = "SELECT * from transection_tbl";
	
	$result = mysqli_query($conn, $sql);
	$count = mysqli_num_rows($result);
	
	echo '<div class="page-title">
			<div class="title_left">
				<h3>Transaction Details</h3>
			</div>
		  </div> 
		<div class="clearfix"></div> 
		<div class="row"> 
			<div class="col-md-12"> 
				<div class="x_panel">
					<table class="table table-striped table-bordered transaction" id="transaction">
						<thead>
							<tr>
								<th width="auto" class="text-center">Investor<br/>ID</th>
								<th width="auto" class="text-center">Investor<br/>Name</th>                       
								<th width="auto" class="text-center">Product<br/>Name</th>
								<th width="auto" class="text-center">Amount</th>
								<th width="auto" class="text-center">Txn<br/>ID</th>
								<th width="auto" class="text-center">Txn<br/>Type</th>
								<th width="auto" class="text-center">Payment<br/>Type</th>
								<th width="auto" class="text-center">Payment<br/>Status</th>
								<th width="auto" class="text-center">Payment<br/> Date</th>
								<th width="auto" class="text-center">Message</th>
							</tr>
						</thead>
						<tbody>';

	if($count){

		while ($row = mysqli_fetch_assoc($result)) {
			
			echo '<tr>
					<td>'.$row['investor_id'].'</td>
					<td>'.$row['first_name'].' '.$row['last_name'].'</td>
					<td>'.$row['item_name'].'</td>
					<td>'.$row['currency'].' '.$row['amount'].'</td>
					<td>'.$row['txn_id'].'</td>
					<td>'.$row['txn_type'].'</td>
					<td>'.$row['payment_type'].'</td>
					<td>'.$row['payment_status'].'</td>
					<td>'.$row['payment_date'].'</td>					
					<td><span href="#" class="inlineedit" data-name="note" data-type="text" data-pk="'.$row['txn_id'].'" data-url="getadmin.php">'.$row['note'].'</span></td>
				</tr>';
		}
	}
	else
	{
		echo '<tr><td colspan="10" class="text-center">No Transactions..</td></tr>';
	}
	
	echo '</tbody></table></div></div></div>';	
}


?>