<?php 
session_start();
if(!(isset($_SESSION['username']) || isset($_SESSION['inv_id']) || isset($_SESSION['inv_email'])))
{
	header("location: pic_user.php"); // Redirecting To Other Page
}
include 'includes/db_config.php';
$query = "select * from contact_us";
$result = mysqli_query($conn, $query); 
$row = mysqli_fetch_array($result) or die(mysqli_error());
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
    <link rel="stylesheet" href="assets/css/core/animate.min.css">
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
</head>

<body id="topPage" data-spy="scroll" data-target=".navbar" data-offset="100">
    <div id="pageloader">
        <div class="loader-item"> <img src="assets/img/other/puff.svg" alt="page loader">
        </div>
    </div>
    <nav class="navbar navbar-pasific navbar-mp megamenu navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-main-collapse"> <i class="fa fa-bars"></i> </button>
                <a class="navbar-brand page-scroll" href="#page-top"> <img src="assets/img/logo/logo-default.png" alt="logo"> <span style="color:#8cc63f;">i</span>Fund <span style="color:#8cc63f;">Network</span> </a>
            </div>
            <div class="navbar-collapse collapse navbar-main-collapse">
<?php               
if(isset($_SESSION['username']))
{ ?>
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
                                            <a href="userprofile.php" class="btn btn-primary btn-sm active">View Profile</a>
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
    
    <header class="pt100 pb100 parallax-window-2" data-parallax="scroll" data-speed="0.5" data-image-src="assets/img/bg/<?php echo $row['contact_bg_image']?>" data-positionY="1000">
        <div class="intro-body text-center">
            <div class="container">
                <div class="row">
                    <div class="col-md-12 pt50">
                        <h1 class="brand-heading font-montserrat text-uppercase color-light" data-in-effect="fadeInDown"><?php echo $row['contact_main_title']?></h1>                       
                    </div>
                </div>
            </div>
        </div>
    </header>
	
	<?php
	include 'includes/modals.php';
	?>
	
        <div id="contact" class="pt100 pb100 bg-grad-stellar">
        <div class="container">
            <div class="row">
                <div class="col-md-6">
                    <div class="row">
                        <div class="col-md-12 mb50">
                            <h1 class="font-size-normal color-light"> <small class="color-light">Contact Us</small> Drop Us a Message</h1>
                            <h5 class="color-light">Please feel free to say anything to us. Our staff will reply any message<br>
              as soon as possible.</h5>
                        </div>
                        <div class="col-md-4 col-sm-4 col-xs-12"> <span class="icon-map color-light el-icon2x"></span>
                            <h5 class="color-light"><strong>Address</strong></h5>
                            <p class="color-light"><?php echo $row['contact_address']?></p>
                        </div>
                        <div class="col-md-3 col-sm-3 col-xs-6"> <span class="icon-megaphone color-light el-icon2x"></span>
                            <h5 class="color-light"><strong>Phone</strong></h5>
                            <p class="color-light"><?php echo $row['contact_phone']?></p>
                        </div>
                        <div class="col-md-4 col-sm-4 col-xs-6"> <span class="icon-envelope color-light el-icon2x"></span>
                            <h5 class="color-light"><strong>Email</strong></h5>
                            <p class="color-light"><?php echo $row['contact_mail']?></p>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="contact contact-us-one">
                        <div class="col-sm-12 col-xs-12 text-center mb20">
                            <h4 class="pb25 bb-solid-1 text-uppercase"> Get in Touch <small class="text-lowercase">Please complete the form and we will get back to you.</small></h4>
                        </div>
                        <form id="contactform1" data-toggle="validator" role="form">
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <div class="form-group">
                                    <input type="text" id="fullname" name="fullname" class="input-md input-rounded form-control" placeholder="fullname" required>
                                </div>
                            </div>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <div class="form-group">
                                    <input type="email" id="email" name="email" class="input-md input-rounded form-control" placeholder="email address" required>
                                </div>
                            </div>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <div class="form-group">
                                    <input type="tel" id="mobile" name="mobile" class="input-md input-rounded form-control" placeholder="mobile number" required>
                                </div>
                            </div>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <div class="form-group">
                                    <input type="text" id="country" name="country" class="input-md input-rounded form-control" placeholder="country" required>
                                </div>
                            </div>
                            <div class="col-sm-12">
                                <textarea id="message" name="message" class="form-control" rows="7"></textarea>
                            </div>
                            <div class="col-sm-12">
                                <div id="conterr" class="text-center pt10"></div>
                            </div>							
                            <div class="col-sm-12 mt10 text-center">
                                <button type="submit" class="button-3d button-md button-block button-pasific hover-ripple-out">Send Message</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
	
<!--	<div class="pt75 pb75" style="background:url(assets/img/bg/img-bg-13.jpg) 50% 0 no-repeat;">
        <h1 class="font-pacifico text-center color-light"> Your Question Unlisted?</h1>
        <h5 class="text-center color-light"> Lorem ipsum dolor sit amet, consectetur adipisicing elit. Porro, libero, temporibus quam eaque<br>numquam debitis excepturi assumenda necessitatibus dolore dolorum vero enim distinctio ipsa.</h5>
        <p class="text-center"> <a class="button-3d button-md button-pasific">Submit Ticket Now</a>
        </p>
    </div> -->
	
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
    <script src="assets/js/core/bootstrap.min.js"></script>
    <script src="formvalidator.js"></script>		
	<script src="script1.js"></script>
    <script src="assets/js/core/bootstrap-formhelpers.js"></script>
	<script src="assets/js/core/bootstrap-select.js"></script>
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
</body>
</html>