<?php 
session_start();
session_destroy();
error_reporting(E_ALL);
ini_set('display_errors', 'on');
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
	<link rel="stylesheet" href="assets/css/core/bootstrap-select.css">
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
                <ul class="nav navbar-nav">                    
                    <li><a href="#contact">Contact Us </a></li>
                    <li><a href="#" data-toggle="modal" data-target="#loginModal"><i class="fa fa-power-off fa-fw" style="color:#8cc63e;"></i> Login / Sign Up</a></li>
                </ul>				
            </div>
        </div>
    </nav>
    <!-- confirm modal -->
    <div class="modal fade" id="confirm-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="z-index:99999;">
        <div class="modal-dialog m-width" role="document">
            <div class="modal-content brdr_blue">
                <div class="modal-body text-center">
                    <h4 class="sm_fnt_1"><span style="color:#f60; font-size:18px;"> Thank you for your Interest!!</span> <hr style="margin:10px 0;"> We will be contacting soon.</h4>
                    <h4 class="sm_fnt">The funding period is open until May 31st 2016</h4>
                </div>
            </div>
        </div>
    </div>
    <!-- Login Modal -->
    <div id="loginModal" class="modal fade" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content pl25 pr25">
				<div class="modal-header brdr-none">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <div class="text-center text-uppercase m15 letter-space-05" style="font-size:18px; color:#3a403e;">Investor Login</div>
                </div>
				<div class="modal-body">
                    <form id="loginform" class="inline-form" data-toggle="validator" role="form">
						<div id="loginmsg" class="text-center pb10"></div>
                        <div class="form-group">                            
                            <input type="text" class="form-control" id="username" placeholder="Username" required>
                        </div>
                        <div class="form-group">                           
                            <input type="email" class="form-control" id="LEmail" placeholder="Email" required>
                        </div>
                        <div class="form-group">                            
                            <input type="password" class="form-control" id="password" placeholder="Password" required>
                        </div>
						<div class="form-group text-right">
							 <a href="#" id="showforgotpassword" onclick="showforgotpassword();" class="rdirect_link">Forgot password</a>
						</div>
                        <!--<div class="checkbox">
                            <label><input type="checkbox" id="remember_me" name="remember_me" Remember me</label>
                        </div>-->
                        <div class="modal-footer brdr-none pl0 pr0">							 
                            <button type="submit" id="userrt" class="button btn_1 button-md text-center btn-100 bg_grn">Login</button>
							<div class="mt10">
								<a id="showpicenquiryform" onclick="showpicenquiryform();" class="text-center button btn_2 button-md text-center btn-100 bg_blue">Don't have a Account ?&nbsp;&nbsp;<span class="col-grn">Apply here</span></a>
							</div>
							<div class="pt35">
								<a id="showpicenterform" onclick="showpicenterform();" class="text-center" style="color:#333; background-color:#fff; padding:10px 20px; border:1px solid #949490; font-size:14px; border-radius:25px; font-weight:normal; cursor:pointer;">You have a PIC... &nbsp;&nbsp;<span class="col_blue">Submit</span></a>
							</div>
                        </div>
                    </form>                                                         
                </div>
            </div>
        </div>
    </div>
    <!--pic enter form -->
    <div id="picentermodal" class="modal fade" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content pl25 pr25">
                <div class="modal-header brdr-none">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <div class="text-center text-uppercase m15 letter-space-05" style="font-size:18px; color:#3a403e;">Enter Your Private Investors Code</div>
                </div>
                <div class="modal-body">
                    <form id="picentermodalform" class="inline-form" data-toggle="validator" role="form">
						<div class="form-group">							
							<input type="email" class="form-control" id="pEmail" placeholder="Email" required>
						</div>
						<div class="form-group">							
							<input type="text" class="form-control" id="pic" placeholder="Enter your PIC here" required>
						</div>
						<div id="picmsg" class="text-center"></div>
						<div class="modal-footer brdr-none pl0 pr0">							
							<button type="submit" id="btnpicrequest" class="button btn_1 button-md text-center btn-100 bg_grn">Submit</button>
						</div>
					</form>
                </div>
            </div>
        </div>
    </div>
    
    <!--PIC Inquiry modal -->
    <div id="AccessInquiryModal" class="modal fade" role="dialog" data-toggle="validator">
        <div class="modal-dialog">
            <div class="modal-content pl25 pr25">
				<div class="modal-header brdr-none">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<div class="text-center text-uppercase m15 letter-space-05" style="font-size:18px; color:#3a403e;">Investment Code Access Inquiry</div>
				</div>
                <div class="modal-body">
                    <form id="AccessInquiry" class="inline-form" data-toggle="validator" role="form">
						<div id="AccessInquiryResult" class="pb10 text-center"></div>
                        <div class="form-group">
							<div class="row">
								<div class="col-md-6">
									<input type="text" class="form-control" id="aFirstName" placeholder="First Name (min 3 letters)" required>
								</div>
								<div class="col-md-6">
									<input type="text" class="form-control" id="aLastname" placeholder="Last Name (min 1 letter)" required>
								</div>
							</div>
						</div>
						<div class="form-group">
							<input type="email" name="Email" class="form-control" pattern="^[\w\-\.\+]+\@[a-zA-Z0-9\.\-]+\.[a-zA-z0-9]{2,4}$" id="Email" placeholder="Email" autocomplete="off" required/>
							<span id="emailResult"></span>
						</div>
                        <div class="form-group">
                            <input type="text" class="form-control" id="aphone" placeholder="Enter Your Mobile Number" required>
                        </div>

						<div class="form-group">
                            <select id="acountry" name="acountry" class="selectpicker" data-width="100%" title="Select Your Country" required>
								<?php 
								$query1 = "select * from country";
								$result1 = mysqli_query($conn, $query1);
								while($record1 = mysqli_fetch_assoc($result1))
								{								
								?>
								<option value="<?php echo $record1['country'] ?>"><?php echo $record1['country'] ?></option>
                                <?php
								}
								mysqli_free_result($result1);
								?>
                            </select>
                        </div>
                        <div class="form-group">
                            <textarea id="amessage" class="form-control" row="5" placeholder="Comments"></textarea>
                        </div>
                        <div class="form-group">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" id="terms" class="" data-error="" required>By Joining ifundnetwork, you agree to the <a href="#">Terms, Conditions & Privacy Policy,</a> and you will keep all information you recieved from this company confidential.</label>
                            </div>
                        </div>
                        <div class="modal-footer brdr-none pr0 pl0">                            
							<button type="submit" id="AccessInquirysubmit" class="button btn_1 button-md text-center btn-100 bg_grn">Submit</button>
                        </div>						
                    </form>
                </div>
            </div>
        </div>
    </div>
    <!-- forgotton password modal-->
    <div id="pwdModal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content pl25 pr25">
                <div class="modal-header brdr-none">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <div class="text-center text-uppercase m15 letter-space-05" style="font-size:18px; color:#3a403e;">Password Reset</div>
                </div>
				<span id="forgotresult"></span>
				<div class="modal-body pb0">
					<div class="text-center">
						<!--<p style="font-size:15px;">Please Enter the Email ID to Reset Your Password</p>-->
						<div class="form-group">
							<input class="form-control input-lg" placeholder="E-mail Address" id="femail" name="femail" type="email" required>					
						</div>
						<div id="femailresult"></div>
                    </div>
                </div>
                <div class="modal-footer brdr-none">
					<button type="submit" id="forgotpassword" onclick="forgotpassword();" class="button btn_1 button-md text-center btn-100 bg_grn">Submit</button>
					<div class="pt25 pb25">
						<a id="showloginform" onclick="showloginform();" class="text-center" style="color:#333; background-color:#fff; padding:10px 20px; border:1px solid #949490; font-size:14px; border-radius:25px; font-weight:normal; cursor:pointer;">You have Password?&nbsp;&nbsp;<span class="col_blue">Login</span></a>
					</div>
                </div>
            </div>
        </div>
    </div>
    <!-- carousel -->
    <?php
	include 'includes/header-carousel.php';
	?>
    <!-- welcome start -->

<div id="welcome" class="pt75">
  <div class="container">
    <div class="row">
      <div class="col-md-12 text-center">
        <h1 class="font-size-normal"> <small>Welcome to iFund Network</small> Connecting Investors for Investing <small class="heading heading-solid center-block"></small></h1>
      </div>
      <div class="col-md-8 col-md-offset-2 text-center">
        <p> <span class="lead"><strong>Ifundnetwork is a seed and early stage invite only private investor firm, currently we are focused on investing in startup with Novel ideas and outstanding technology innovations. Presently operating in the following locations, Houston, Texas, Lagos Nigeria, and Chennai India </strong></span><br>
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
		'<div class="ml15 mr15 portfolio_border" data-toggle="modal" data-target="#loginModal">
        <div class="animated pt15" data-animation="fadeInLeft" data-animation-delay="100"> <img src="assets/img/other/'.$record['project_logo'].'" alt="website service" class="img-responsive"></div>
        <div class="animated" data-animation="fadeIn" data-animation-delay="100">
		<h3 class="font-size-normal text-center pt5 pb5 ml0 mr0 portfolio_bg2"> <small class="color-primary pb10">Category</small> '.$record['project_category'].'</h3>
		<p class="mt20 pl10 pr10">'.$record['project_desc_short'].'</p>
		<h3 class="font-size-normal text-center mb0 pb10 pt10 portfolio_bg3"> <small class="color-primary pb15">Seed Stage Funding Goal</small>$ '.$record['project_fund_goal'].'</h3>									
		</div>
		</div>';		
}
mysqli_free_result($result);
?>
					</div>
                </div>
            </div>
        </div>
    </div>
</div>




    <!-- start-->
    <div id="info-1" class="pt50 pb50 mt75 parallax-window" data-parallax="scroll" data-speed="0.5" data-image-src="assets/img/bg/img-bg-2.jpg">
        <div class="container">
            <div class="row pt75">
                <div class="col-md-12 text-center">
                    <h1 class="color-light"> <small class="color-light">The best way to be success</small> Are you ready to be join as Investor?</h1>
                    <a href="#" data-toggle="modal" data-target="#AccessInquiryModal" class="button button-md button-pasific hover-ripple-out mt25">Join now</a> <a href="#contact" class="button-o button-md button-green hover-fade mt25"><span class="color-light">Contact Us</span></a>
                </div>
            </div>
        </div>
    </div>
    <!--end -->

    <!-- testimonial end -->
    <!--contactus start -->
    <div id="contact" class="pt100 pb100 bg-grad-stellar">
        <div class="container">
            <div class="row">
                <div class="col-md-6">
                    <div class="row">
                        <div class="col-md-12 mb50">
                            <h1 class="font-size-normal color-light"> <small class="color-light">Contact Us</small> Drop Us a Message</h1>
                            <h5 class="color-light">Please feel free to say anything to us. Our staff will reply any message<br>as soon as possible.</h5>
                        </div>
                        <div class="col-md-4 col-sm-4 col-xs-12"> <span class="icon-map color-light el-icon2x"></span>
                            <h5 class="color-light"><strong>Address</strong></h5>
                            <p class="color-light">Address has to be updated</p>
                        </div>
                        <div class="col-md-3 col-sm-3 col-xs-6"> <span class="icon-megaphone color-light el-icon2x"></span>
                            <h5 class="color-light"><strong>Phone</strong></h5>
                            <p class="color-light">123-456-789</p>
                        </div>
                        <div class="col-md-4 col-sm-4 col-xs-6"> <span class="icon-envelope color-light el-icon2x"></span>
                            <h5 class="color-light"><strong>Email</strong></h5>
                            <p class="color-light">email@domain.com</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="contact contact-us-one">
                        <div class="col-sm-12 col-xs-12 text-center mb20">
                            <h4 class="pb25 bb-solid-1 text-uppercase"> Get in Touch <small class="text-lowercase">Please complete the form and we will get back to you.</small></h4>
                        </div>
                        <form id="contactform" data-toggle="validator" role="form">
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <div class="form-group">
                                    <input type="text" id="fullname" name="fullname" class="input-md input-rounded form-control" placeholder="full Name" required>
									<span id="name-error"></span>
                                </div>
                            </div>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <div class="form-group">
                                    <input type="email" id="email" name="email" class="input-md input-rounded form-control" placeholder="email address" required>
									<span id="email1-error"></span>
                                </div>
                            </div>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <div class="form-group">
                                    <input type="tel" id="mobile" name="mobile" class="input-md input-rounded form-control" placeholder="mobile number" required>
									<span id="mobile-error"></span>
                                </div>
                            </div>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <div class="form-group">
                                    <input type="text" id="country" name="country" class="input-md input-rounded form-control" placeholder="country" required>
									<span id="country1-error"></span>
                                </div>
                            </div>
                            <div class="col-sm-12">
                                <textarea id="message" name="message" class="form-control" rows="7" placeholder="Your comments please.."></textarea>
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
    <!--contactus end -->
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