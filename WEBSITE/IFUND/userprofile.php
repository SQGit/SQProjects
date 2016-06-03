<?php  
session_start();

if(!isset($_SESSION['username']))
{
	header("location: pic_user.php"); // Redirecting To Other Page
}

include 'includes/db_config.php';

$sess_name = $_SESSION['username'];
$sess_email = $_SESSION['inv_email'];
$pic = $_SESSION['inv_id'];
?>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>iFund Network | Connecting Investors To Great Startup</title>
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta charset="utf-8">
    <meta name="author">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <link rel="shortcut icon" href="assets/img/favicon.png">
    <link rel="stylesheet" href="assets/css/core/bootstrap.min.css">
	<link rel="stylesheet" href="assets/bootstrap3-editable-1.5.1/bootstrap3-editable/css/bootstrap-editable.css">	
    <link rel="stylesheet" href="assets/css/core/animate.min.css">
    <link rel="stylesheet" href="assets/css/core/jquery-ui.css">	
	<link rel="stylesheet" href="assets/css/core/prettify.css">
    <link rel="stylesheet" href="assets/css/main/main.min.css">
    <link rel="stylesheet" href="assets/css/magnific/magic.min.css">
    <link rel="stylesheet" href="assets/css/magnific/magnific-popup.css">
    <link rel="stylesheet" href="assets/css/magnific/magnific-popup-zoom-gallery.css">
    <link rel="stylesheet" href="assets/css/owl-carousel/owl.carousel.css">
    <link rel="stylesheet" href="assets/css/owl-carousel/owl.theme.css">
    <link rel="stylesheet" href="assets/css/owl-carousel/owl.transitions.css">
    <link rel="stylesheet" href="assets/css/color/pasific.css">
    <link rel="stylesheet" href="assets/css/icon/font-awesome.css">
    <link rel="stylesheet" href="assets/css/icon/et-line-font.css">
    <!--[if lt IE 9]> <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script> <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script> <![endif]-->
</head>

<body id="page-top" data-spy="scroll" data-target=".navbar" data-offset="100">
    <div id="pageloader">
        <div class="loader-item"> <img src="assets/img/other/puff.svg" alt="page loader">
        </div>
    </div>
    <nav class="navbar navbar-pasific navbar-mp navbar-standart megamenu navbar-fixed-top top-nav-collapse">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-main-collapse"> <i class="fa fa-bars"></i> </button>
                <a class="navbar-brand page-scroll" href="#page-top"> <img src="assets/img/logo/logo-default.png" alt="logo"> <span style="color:#8cc63f;">i</span>Fund <span style="color:#8cc63f;">Network</span> </a>
            </div>
            <div class="navbar-collapse collapse navbar-main-collapse">
<?php               
if(isset($_SESSION['username']))
{ 
	if($_SESSION['username']=="admin")
	{
?>
			   <ul class="nav navbar-nav">
					<li><a href="admin/admin.php">Investor management</a></li>
                    <li><a href="admin/home.php">Home </a></li>
					<li><a href="admin/about_us.php">About Us </a></li>
					<li><a href="admin/faq.php">FAQ </a></li>
					<li><a href="admin/partner.php">Our Partners </a></li>
                    <li><a href="admin/contact_us.php">Contact Us </a></li>                    
                </ul>
				<ul class="nav navbar-nav navbar-right text-capitalize">
                    <li class="dropdown text-capitalize"><a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-user"> </span>&nbsp;&nbsp;Hi&nbsp;&nbsp;<?php echo $_SESSION['username']; ?> <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li class="pt0">
                                <div class="navbar-content">
                                    <div class="row">
                                        <div class="col-md-5">
                                            <img src="http://placehold.it/120x120"
                                                alt="Alternate Text" class="img-responsive img-circle" />                                            
                                        </div>
                                        <div class="col-md-7">
                                            <span><?php echo $_SESSION['username']; ?></span>
                                            <p class="text-muted small mb10 text-lowercase"><?php echo $_SESSION['inv_email']; ?></p>
                                            <div class="divider">
                                            </div>
                                            <a href="userprofile.php" class="btn btn-primary btn-sm active" style="color:#fff!important;">View Profile</a>
                                        </div>
                                    </div>
                                </div>
                                <div class="navbar-footer mt15">
                                    <div class="navbar-footer-content">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <a href="userprofile.php" class="btn btn-default btn-sm font-black">Change Passowrd</a>
                                            </div>
                                            <div class="col-md-6">
                                                <a href="process/logout.php" class="btn btn-default btn-sm pull-right font-black">Sign Out</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </li>
                </ul>
<?php }
else
{?>
			   <ul class="nav navbar-nav">
                    <li><a href="reg_user.php">Home </a></li>
					<li><a href="about_us.php">About Us </a></li>
					<li><a href="faq.php">FAQ </a></li>
					<li><a href="partner.php">Our Partners </a></li>
                    <li><a href="contact_us.php">Contact Us </a></li>                    
                </ul>
				<ul class="nav navbar-nav navbar-right text-capitalize">
						<li class="dropdown text-capitalize"><a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-user"> </span>&nbsp;&nbsp;Hi&nbsp;&nbsp;<?php echo $_SESSION['username']; ?> <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li class="pt0">
                                <div class="navbar-content">
                                    <div class="row">
                                        <div class="col-md-5">
                                            <img src="http://placehold.it/120x120"
                                                alt="Alternate Text" class="img-responsive img-circle" />                                            
                                        </div>
                                        <div class="col-md-7">
                                            <span><?php echo $_SESSION['username']; ?></span>
                                            <p class="text-muted small mb10 text-lowercase"><?php echo $_SESSION['inv_email']; ?></p>
                                            <div class="divider">
                                            </div>
                                            <a href="userprofile.php" class="btn btn-primary btn-sm active" style="color:#fff!important;">View Profile</a>
                                        </div>
                                    </div>
                                </div>
                                <div class="navbar-footer mt15">
                                    <div class="navbar-footer-content">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <a href="userprofile.php" class="btn btn-default btn-sm font-black">Change Passowrd</a>
                                            </div>
                                            <div class="col-md-6">
                                                <a href="process/logout.php" class="btn btn-default btn-sm pull-right font-black">Sign Out</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </li>
                </ul>
<?php 
}

}
else
{ ?>
				   <ul class="nav navbar-nav">
                    <li><a href="pic_user.php">Home </a></li>
					<li><a href="about_us.php">About Us </a></li>
					<li><a href="faq.php">FAQ </a></li>
					<li><a href="partner.php">Our Partners </a></li>
                    <li><a href="contact_us.php">Contact Us </a></li>
                    <li><a href="#" data-toggle="modal" data-target="#loginModal"><i class="fa fa-power-off fa-fw" style="color:#8cc63e;"></i> Login / Sign Up</a></li>                    
                </ul>
<?php				
} ?>
            </div>
        </div>
    </nav>

	<?php
	$query = "select * from reg_user WHERE user_id='$sess_name' and email='$sess_email'";
	$result = mysqli_query($conn, $query); 
	$row = mysqli_fetch_array($result) or die(mysqli_error());
	?>
	
    <div id="welcome" class="pt100 pb25">
        <div class="container">
            <div class="row">
                <div class="col-md-12 text-center">
                    <div class="well">
					<ul class="nav nav-tabs">
						<li class="active"><a href="#home" data-toggle="tab">Profile</a></li>
						<li><a href="#profile" data-toggle="tab">Password</a></li>
					</ul>
					<div id="myTabContent" class="tab-content">
						<div class="tab-pane active in" id="home">
						
						

						<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xs-offset-0 col-sm-offset-0 col-md-offset-3 col-lg-offset-3 toppad" >
						<div class="panel panel-info">
						<div class="panel-heading">
							<h3 class="panel-title"><?php echo $row['name']?></h3>
						</div>
						<div class="panel-body">
						<div class="row">
						<div class="col-md-3 col-lg-3 " align="center"> <img alt="User Pic" src="http://babyinfoforyou.com/wp-content/uploads/2014/10/avatar-300x300.png" class="img-circle img-responsive"> </div>
			<div id="profile_view" class="col-md-9 col-lg-9 "> 
				<table class="table table-user-information">
                    <tbody>
                      <tr>
                        <td>Name:</td>
                        <td><?php echo $row['name']?></td>
                      </tr>
                      <tr>
                        <td>email</td>
                        <td><?php echo $row['email']?></td>
                      </tr>
                      <tr>
                        <td>Phone</td>
                        <td><?php echo $row['phone']?></td>
                      </tr>                   
                      <tr>
                        <td>Country</td>
                        <td><?php echo $row['country']?></td>
                      </tr>
                      <tr>
                        <td>Billing Address</td>
                        <td><?php echo $row['baddress']?><br/><?php echo $row['bcity']?> - <?php echo $row['bzipcode']?></td>
                      </tr>
                      <tr>
                        <td>Shipping Address</td>
                        <td><?php echo $row['saddress']?><br/><?php echo $row['scity']?> - <?php echo $row['szipcode']?></td>
                      </tr>                     
                    </tbody>
                </table>
			</div>
			<div id="profile_update" class="col-md-9 col-lg-9" style="display:none">
				<table class="table table-user-information">
                    <tbody>
                      <tr>
                        <td>Name:</td>
                        <td><a href="#" class="name" data-name="name" data-type="text" data-pk="<?php echo $row['pic'] ?>" data-url="process/profileupdate.php"><?php echo $row['name']?></a></td>
                      </tr>
                      <tr>
                        <td>email</td>
                        <td><a href="#" class="email" data-name="email" data-type="text" data-pk="<?php echo $row['pic'] ?>" data-url="process/profileupdate.php"><?php echo $row['email']?></a></td>
                      </tr>
                      <tr>
                        <td>Phone</td>
                        <td><a href="#" class="phone" data-name="phone" data-type="text" data-pk="<?php echo $row['pic'] ?>" data-url="process/profileupdate.php"><?php echo $row['phone']?></a></td>
                      </tr>                   
                      <tr>
                        <td>Country</td>
                        <td><a href="#" class="inlineedit" data-name="country" data-type="text" data-pk="<?php echo $row['pic'] ?>" data-url="process/profileupdate.php"><?php echo $row['country']?></a></td>
                      </tr>
                      <tr>
                        <td>Billing Address</td>
                        <td><a href="#" class="inlineedit" data-name="baddress" data-type="textarea" data-pk="<?php echo $row['pic'] ?>" data-url="process/profileupdate.php"><?php echo $row['baddress']?></a></td>
                      </tr>
					  <tr>
                        <td>City</td>
                        <td><a href="#" class="inlineedit" data-name="bcity" data-type="text" data-pk="<?php echo $row['pic'] ?>" data-url="process/profileupdate.php"><?php echo $row['bcity']?></a></td>
                      </tr>
					  <tr>
                        <td>Zipcode</td>
                        <td><a href="#" class="zipcode" data-name="bzipcode" data-type="text" data-pk="<?php echo $row['pic'] ?>" data-url="process/profileupdate.php"><?php echo $row['bzipcode']?></a></td>
                      </tr>
                      <tr>
                        <td>Shipping Address</td>
                        <td><a href="#" class="inlineedit" data-name="saddress" data-type="textarea" data-pk="<?php echo $row['pic'] ?>" data-url="process/profileupdate.php"><?php echo $row['saddress']?></a></td>
                      </tr>
					  <tr>
                        <td>City</td>
                        <td><a href="#" class="inlineedit" data-name="scity" data-type="text" data-pk="<?php echo $row['pic'] ?>" data-url="process/profileupdate.php"><?php echo $row['scity']?></a></td>
                      </tr>
					  <tr>
                        <td>Zipcode</td>
                        <td><a href="#" class="zipcode" data-name="szipcode" data-type="text" data-pk="<?php echo $row['pic'] ?>" data-url="process/profileupdate.php"><?php echo $row['szipcode']?></a></td>
                      </tr>					  
                    </tbody>
                </table>
            </div>
        </div>
            </div>
                 <div class="panel-footer">
                        
                        <span>
                            <a onclick="show_profile_edit();" data-original-title="Edit profile" data-toggle="tooltip" type="button" class="btn btn-sm btn-warning"><i class="glyphicon glyphicon-edit"></i></a>
                            <a href="reg_user.php" data-original-title="Home page" data-toggle="tooltip" type="button" class="btn btn-sm btn-danger"><i class="glyphicon glyphicon-remove"></i></a>
                        </span>
                    </div>
            
          </div>
        </div>
      
    
      </div>
	  
    <div class="tab-pane fade" id="profile">	  
	  
		
		<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xs-offset-0 col-sm-offset-0 col-md-offset-3 col-lg-offset-3 toppad" >
		<div class="panel panel-info">
		<div class="panel-heading">
			<h3 class="panel-title">Change Your Password</h3>
		</div>
		<div class="panel-body">
		<div class="row">
		<div class="col-md-offset-2 col-lg-offset-2 col-md-8 col-lg-8 " align="center"> 		
		<form id="changepassword" class="inline-horizontal" method="post" role="form">
				<input type="hidden" class="form-control" id="sess_name" value="<?php echo $sess_name ?>" placeholder="Current Password">
				<input type="hidden" class="form-control" id="sess_email" value="<?php echo $sess_email ?>" placeholder="Current Password">
			<div class="form-group">
				<!--<label class="control-label" for="email">Current Password: </label>-->
				<input type="password" class="form-control " id="oldpassword" placeholder="Current Password" required>
			</div>
			<div class="form-group">
				<!--<label class="control-label" for="email">New Password: </label>-->
				<input type="password" class="form-control " id="newpassword" placeholder="New Password" required>
				<span id="password-error" class="signup-error"></span>							
			</div>
			<div class="form-group">
				<!--<label class="control-label" for="email">Confirm New Password:</label>-->
				<input type="password" class="form-control " id="confirmpassword" placeholder="Confirm New Password" required>
				<span id="uconfirm-password-error" class="signup-error"></span>				
			</div>						
			<div id="changepassmsg" class="text-center"></div>
			
    	</form>
		</div>
		</div>
		</div>
		<div class="panel-footer col-md-offset-0 col-lg-offset-0">		
			<span>			
				<button id="changepasswordsubmit" onclick="changepasswordsubmit();" type="submit" class="btn btn-primary">Submit</button>			
			</span>
		</div>
        </div>
		</div>
	 
	
  </div>
                </div>

            </div>
        </div>
    </div>
    </div>
    <div id="footer" class="footer-two pt20">
        <div class="container-fluid">
            <div class="container">
                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12 text-center">
                        <p>Copyright &copy;2016 <a href="#">iFund Network</a>. All rights reserved.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="assets/js/core/jquery.min.js"></script>	
    <script src="assets/js/core/jquery-ui.js"></script>
    <script src="assets/js/core/bootstrap.min.js"></script>    
	<script src="formvalidator.js"></script>
	<script src="assets/bootstrap3-editable-1.5.1/bootstrap3-editable/js/bootstrap-editable.min.js"></script>	
    <script src="assets/js/magnific-popup/jquery.magnific-popup.min.js"></script>
    <script src="assets/js/magnific-popup/magnific-popup-zoom-gallery.js"></script>
    <script src="assets/js/progress-bar/bootstrap-progressbar.js"></script>
    <script src="assets/js/progress-bar/bootstrap-progressbar-main.js"></script>
    <script src="assets/js/main/jquery.appear.js"></script>
    <script src="assets/js/main/isotope.pkgd.min.js"></script>
    <script src="assets/js/main/parallax.min.js"></script>
    <script src="assets/js/main/jquery.countTo.js"></script>
    <script src="assets/js/main/owl.carousel.min.js"></script>
    <script src="assets/js/main/owl.carousel.min.js"></script>
    <script src="assets/js/main/ion.rangeSlider.min.js"></script>
    <script src="assets/js/main/main.js"></script>
	<script type="text/javascript">
	
	$.fn.editable.defaults.mode = "popup";                       
        $('.inlineedit').editable();
		
		$('.email').editable({
			validate:function(value){
				//var v=valib.String.isEmailLike(value);      
				if(!(value).match(/^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/))
				//if(v==false) 
					return 'Please insert valid mail';
			}	 
		});
		
		$('.name').editable({
			validate:function(value){
				//var v=valib.String.isEmailLike(value);      
				if(!(value).match(/^([A-z]{2,})?$/))
				//if(v==false) 
					return 'Enter valid Name (A-z only allowed)';
			}	 
		});
		
		$('.phone').editable({
			validate:function(value){
				//var v=valib.String.isEmailLike(value);      
				if(!(value).match(/^([0-9]{9,})?$/))
				//if(v==false) 
					return 'Enter valid Mobile(Minimum 9 numbers required)';
			}	 
		});
		
		$('.zipcode').editable({
			validate:function(value){
				//var v=valib.String.isEmailLike(value);      
				if(!(value).match(/^([0-9]{6,})?$/))
				//if(v==false) 
					return 'Enter valid zipcode(6 numbers required)';
			}	 
		});
		
		
	function show_profile_edit(){
		$("#profile_view").hide();
		$("#profile_update").show();
	}
	
	var password1 		= $('#newpassword'); //id of first password field
	var password2		= $('#confirmpassword'); //id of second password field
	var passwordsInfo 	= $('#password-error'); //id of indicator element
	var confirmpasswordsInfo 	= $('#uconfirm-password-error'); //id of indicator element
	
	passwordStrengthCheck(password1,password2,passwordsInfo,confirmpasswordsInfo); //call password check function
	
function passwordStrengthCheck(password1, password2, passwordsInfo, confirmpasswordsInfo)
{
	//Must contain 5 characters or more
	var WeakPass = /(?=.{5,}).*/; 
	//Must contain lower case letters and at least one digit.
	var MediumPass = /^(?=\S*?[a-z])(?=\S*?[0-9])\S{5,}$/; 
	//Must contain at least one upper case letter, one lower case letter and one digit.
	var StrongPass = /^(?=\S*?[A-Z])(?=\S*?[a-z])(?=\S*?[0-9])\S{5,}$/; 
	//Must contain at least one upper case letter, one lower case letter and one digit.
	var VryStrongPass = /^(?=\S*?[A-Z])(?=\S*?[a-z])(?=\S*?[0-9])(?=\S*?[^\w\*])\S{5,}$/; 
	
	$(password1).on('keyup', function(e) {
		if(VryStrongPass.test(password1.val()))
		{
			passwordsInfo.removeClass().addClass('goodpass').html("Very Strong! (Awesome, please don't forget your pass now!)");
		}	
		else if(StrongPass.test(password1.val()))
		{
			passwordsInfo.removeClass().addClass('strongpass').html("Strong! (Enter special chars to make even stronger");
		}	
		else if(MediumPass.test(password1.val()))
		{
			passwordsInfo.removeClass().addClass('goodpass').html("Good! (Enter uppercase letter to make strong)");
		}
		else if(WeakPass.test(password1.val()))
    	{
			passwordsInfo.removeClass().addClass('stillweakpass').html("Still Weak! (Enter digits to make good password)");
    	}
		else if(password1.val()=='')
		{
			passwordsInfo.html("");
		}else
		{
			passwordsInfo.removeClass().addClass('weakpass').html("Very Weak! (Must be 5 or more chars)");
		}
	}); 
	
	$(password2).on('keyup', function(e) {
		
		if(password2.val()=='')
		{
			confirmpasswordsInfo.html("");
		}
		else if(password1.val() !== password2.val())
		{
			confirmpasswordsInfo.removeClass().addClass('weakpass').html("Passwords do not match!");	
		}else{
			confirmpasswordsInfo.removeClass().addClass('goodpass').html("Passwords match!");	
		}
			
	});
}
	
	</script>
</body>

</html>