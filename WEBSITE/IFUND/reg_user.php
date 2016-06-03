<?php 
session_start();

if(!isset($_SESSION['username']))
{
	header("location: pic_user.php"); // Redirecting To Other Page
}

include 'includes/db_config.php';
$query = "select * from project_list WHERE status='active'";
$result = mysqli_query($conn, $query); 
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
    <link rel="stylesheet" href="assets/css/core/bootstrap-formhelpers.css">
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
			   <ul class="nav navbar-nav navbar-left">
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
                                        <div class="col-md-5 col-sm-5 hidden-xs">
                                            <img src="http://placehold.it/120x120"
                                                alt="Alternate Text" class="img-responsive img-circle" />                                            
                                        </div>
                                        <div class="col-md-7 col-sm-7">
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
                                            <div class="col-md-6 col-sm-6 col-xs-6">
                                                <a href="userprofile.php" class="btn btn-default btn-sm font-black">Change Passowrd</a>
                                            </div>
                                            <div class="col-md-6 col-sm-6 col-xs-6">
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
    <!-- confirm modal -->
    <div class="modal fade" id="confirm-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="z-index:99999;">
        <div class="modal-dialog m-width" role="document">
            <div class="modal-content brdr_blue">
                <div class="modal-body text-center">
                    <h4 class="sm_fnt_1"><span style="color:#f60; font-size:18px;"> Thank you for your Interest!!</span> <hr style="margin:10px 0;"> We will be contacting soon.</h4>
                    <h4 class="sm_fnt">The funding period is open until April 30th 2016</h4>
                </div>
            </div>
        </div>
    </div>
    <!-- Login Modal -->
    <div id="loginModal" class="modal fade" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content bg-gray">
                <div class="text-center text-uppercase m15" style="font-size:18px;">Investor Login</div>
                <div class="modal-body">
                    <form id="loginform" class="inline-form" data-toggle="validator" role="form">
                        <div class="form-group">
                            <label class="control-label" for="User Name">User Name</label>
                            <input type="text" class="form-control" pattern="^[A-z]{1,}$" id="username" placeholder="Username" required>
                        </div>
                        <div class="form-group">
                            <label class="control-label" for="email">Email</label>
                            <input type="email" class="form-control" id="LEmail" placeholder="Email" data-error="email address is invalid" required>
                        </div>
                        <div class="form-group">
                            <label class="control-label" for="Password">Password</label>
                            <input type="password" class="form-control" id="password" placeholder="Password" required>
                        </div>
                        <div class="checkbox">
                            <label>
                                <input type="checkbox">Remember me</label>
                        </div>
                        <div class="modal-footer bg-gray">
                            <button type="submit" id="userrt" class="button button-pasific hover-ripple-out button-md mt30 text-center">Login</button>
                        </div>
                    </form>
                    <a href="#" id="forgot_password" class="rdirect_link">Forgot the password?</a>
                    <br/>
                    <a href="#" id="register" class="rdirect_link">Register</a>
                </div>
            </div>
        </div>
    </div>
    

    <!-- forgotton password modal-->
    <div id="pwdModal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h1 class="text-center">What's My Password?</h1>
                </div>
                <div class="modal-body">
                    <div class="col-md-12">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class="text-center">
                                    <p>If you have forgotten your password you can reset it here.</p>
                                    <div class="panel-body">
                                        <fieldset>
                                            <div class="form-group">
                                                <input class="form-control input-lg" placeholder="E-mail Address" name="email" type="email">
                                            </div>
                                            <input class="btn btn-lg btn-primary btn-block" value="Send My Password" type="submit">
                                        </fieldset>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <div class="col-md-12">
                        <button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <?php
	include 'includes/header-carousel.php';
	?>
    <div id="welcome" class="pt75">
        <div class="container">
            <div class="row">
                <div class="col-md-12 text-center">
                    <h1 class="font-size-normal"> <small>Welcome to iFund Network</small> Connecting Investors for Investing <small class="heading heading-solid center-block"></small></h1>
                </div>
                <div class="col-md-8 col-md-offset-2 text-center">
                    <p> <span class="lead"><strong>Ifundnetwork is a seed and early stage invite only private investor firm, currently we are focused on investing in startup with Novel ideas and outstanding technology innovations. Presently operating in the following locations, Houston, Texas, Lagos Nigeria, and Chennai India </strong></span>
                        <br>
                    </p>
                </div>
            </div>
        </div>
    </div>
	
<div id="testimonial" class="pt75 pb25">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <div class="row">
                        <div id="owlSectionThreeItem" class="owl-carousel">
<?php
while($record = mysqli_fetch_assoc($result))
{
	echo
		'<a href="ifindcard.php?id='.$record['project_id'].'"><div class="ml15 mr15 portfolio_border">
        <div class="animated pt15" data-animation="fadeInLeft" data-animation-delay="100"> <img src="assets/img/other/'.$record['project_logo'].'" alt="website service" class="img-responsive"></div>
        <div class="animated" data-animation="fadeIn" data-animation-delay="100">
		<h3 class="font-size-normal text-center pt5 pb5 ml0 mr0 portfolio_bg2"> <small class="color-primary pb10">Category</small> '.$record['project_category'].'</h3>
		<p class="mt20 pl10 pr10">'.$record['project_desc_short'].'</p>
		<h3 class="font-size-normal text-center mb0 pb10 pt10 portfolio_bg3"> <small class="color-primary pb15">Seed Stage Funding Goal</small>$ '.$record['project_fund_goal'].'</h3>									
		</div>
		</div></a>';		
}
mysqli_free_result($result);
?>
					</div>
                </div>
            </div>
        </div>
    </div>
</div>
    
    <div id="info-1" class="pt50 pb50 mt75 parallax-window" data-parallax="scroll" data-speed="0.5" data-image-src="assets/img/bg/img-bg-2.jpg">
        <div class="container">
            <div class="row pt75">
                <div class="col-md-12 text-center">
                    <h1 class="color-light"> <small class="color-light">The best way to be success</small> Are you ready to be Invest?</h1>                    
                </div>
            </div>
        </div>
    </div>
   
<!---<div id="testimonial" class="pt75 pb75">
        <div class="container">
            <div class="row text-center mb25">
                <h1 class="font-size-normal"> <small>Testimonials</small> What Our Partners Say About Us <small class="heading heading-solid center-block"></small></h1>
            </div>
            <div class="row">
                <div class="col-sm-12">
                    <div class="row">
                        <div id="owlSectionThreeItem" class="owl-carousel">
                            <div class="testimonial testimonial-triangle-isosceles bottom-left">
                                <div class="testimonial-body">
                                    <p> Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s</p>
                                </div>
                                <div class="testimonial-footer"> <img src="assets/img/other/photo-1.jpg" alt="testimonial author" class="img-responsive img-circle"> <i class="fa fa-quote-left"></i> Martin Smith </div>
                            </div>
                            <div class="testimonial testimonial-triangle-isosceles bottom-left">
                                <div class="testimonial-body">
                                    <p> Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s</p>
                                </div>
                                <div class="testimonial-footer"> <img src="assets/img/other/photo-2.jpg" alt="testimonial author" class="img-responsive img-circle"> <i class="fa fa-quote-left"></i> Steve Austin </div>
                            </div>
                            <div class="testimonial testimonial-triangle-isosceles bottom-left">
                                <div class="testimonial-body">
                                    <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s</p>
                                </div>
                                <div class="testimonial-footer"> <img src="assets/img/other/photo-4.jpg" alt="testimonial author" class="img-responsive img-circle"> <i class="fa fa-quote-left"></i> Gisselse </div>
                            </div>
                            <div class="testimonial testimonial-triangle-isosceles bottom-left">
                                <div class="testimonial-body">
                                    <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s</p>
                                </div>
                                <div class="testimonial-footer"> <img src="assets/img/other/photo-1.jpg" alt="testimonial author" class="img-responsive img-circle"> <i class="fa fa-quote-left"></i> Martin Smith </div>
                            </div>
                            <div class="testimonial testimonial-triangle-isosceles bottom-left">
                                <div class="testimonial-body">
                                    <p> Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s</p>
                                </div>
                                <div class="testimonial-footer"> <img src="assets/img/other/photo-2.jpg" alt="testimonial author" class="img-responsive img-circle"> <i class="fa fa-quote-left"></i> Steve Austin </div>
                            </div>
                            <div class="testimonial testimonial-triangle-isosceles bottom-left">
                                <div class="testimonial-body">
                                    <p> Fusce quam augue, gravida tincidunt dui nec, tempor iaculis justo.</p>
                                </div>
                                <div class="testimonial-footer"> <img src="assets/img/other/photo-4.jpg" alt="testimonial author" class="img-responsive img-circle"> <i class="fa fa-quote-left"></i> Goselle </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
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
    <script src="assets/js/core/bootstrap-formhelpers.js"></script>
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