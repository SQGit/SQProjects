<?php 
session_start();
if(!isset($_SESSION['username'])=="admin")
{
	header("location: ../pic_user.php"); // Redirecting To Other Page
}
include '../includes/db_config.php';
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
    <link rel="shortcut icon" href="../assets/img/favicon.png">	
    <link rel="stylesheet" href="../assets/css/core/bootstrap.min.css">
    <link rel="stylesheet" href="../assets/css/core/animate.min.css">    
	<link rel="stylesheet" href="../assets/css/core/datetimepicker.css" media="screen">
	<link rel="stylesheet" href="../assets/bootstrap3-editable-1.5.1/bootstrap3-editable/css/bootstrap-editable.css">
	<link rel="stylesheet" href="../assets/bootstrap3-editable-1.5.1/wysihtml5/bootstrap-wysihtml5-0.0.2.css">	
	<link rel="stylesheet" href="../assets/css/core/prettify.css">
    <link rel="stylesheet" href="../assets/css/main/main.min.css">
    <link rel="stylesheet" href="../assets/css/magnific/magic.min.css">
    <link rel="stylesheet" href="../assets/css/magnific/magnific-popup.css">
    <link rel="stylesheet" href="../assets/css/magnific/magnific-popup-zoom-gallery.css">
    <link rel="stylesheet" href="../assets/css/owl-carousel/owl.carousel.css">
    <link rel="stylesheet" href="../assets/css/owl-carousel/owl.theme.css">
    <link rel="stylesheet" href="../assets/css/owl-carousel/owl.transitions.css">
    <link rel="stylesheet" href="../assets/css/color/pasific.css">
    <link rel="stylesheet" href="../assets/css/icon/font-awesome.css">
    <link rel="stylesheet" href="../assets/css/icon/et-line-font.css">    
	<link rel="stylesheet" href="../assets/bootstrap3-editable-1.5.1/wysihtml5/bootstrap-wysihtml5.css">	
</head>

<body id="topPage" data-spy="scroll" data-target=".navbar" data-offset="100">
    <?php    
    include '../includes/pageloader.php';
    include '../includes/navbar.php';
    ?>    
    <header class="pt100 pb100 parallax-window-2" data-parallax="scroll" data-speed="0.5" data-image-src="../assets/img/bg/<?php echo $row['contact_bg_image']?>" data-positionY="1000">
        <div class="intro-body text-center">
            <div class="container">
                <div class="row">
                    <div class="col-md-12 pt50">
                        <h1 class="brand-heading font-montserrat text-uppercase color-light" data-in-effect="fadeInDown"><a href="#" class="inlineedit color-white" data-name="contact_main_title" data-type="text" data-pk="<?php echo $row['contact_id'] ?>" data-url="pjtlstupdate.php"><?php echo $row['contact_main_title']?></a></h1>
                    </div>
                </div>
            </div>
        </div>
    </header>
	
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
                            <p class="color-light"><a href="#" class="inlineedit color-white" data-name="contact_address" data-type="text" data-pk="<?php echo $row['contact_id'] ?>" data-url="pjtlstupdate.php"><?php echo $row['contact_address']?></a></p>
                        </div>
                        <div class="col-md-3 col-sm-3 col-xs-6"> <span class="icon-megaphone color-light el-icon2x"></span>
                            <h5 class="color-light"><strong>Phone</strong></h5>
                            <p class="color-light"><a href="#" class="inlineedit color-white" data-name="contact_phone" data-type="text" data-pk="<?php echo $row['contact_id'] ?>" data-url="pjtlstupdate.php"><?php echo $row['contact_phone']?></a></p>
                        </div>
                        <div class="col-md-4 col-sm-4 col-xs-6"> <span class="icon-envelope color-light el-icon2x"></span>
                            <h5 class="color-light"><strong>Email</strong></h5>
                            <p class="color-light"><a href="#" class="inlineedit color-white" data-name="contact_mail" data-type="email" data-pk="<?php echo $row['contact_id'] ?>" data-url="pjtlstupdate.php"><?php echo $row['contact_mail']?></a></p>
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
	
	 <?php
    include '../includes/footer.php';
    ?>
	
    <script src="../assets/js/core/jquery.min.js"></script>
	<script src="../assets/js/core/jquery-ui.js"></script>
    <script src="../assets/js/core/bootstrap.min.js"></script>
    <script src="../formvalidator.js"></script>
    <script src="../assets/js/core/bootstrap-formhelpers.js"></script>
	<script src="../assets/js/core/bootstrap-datetimepicker.js"></script>
    <script src="../assets/bootstrap3-editable-1.5.1/bootstrap3-editable/js/bootstrap-editable.min.js"></script>
	<script src="../assets/bootstrap3-editable-1.5.1/wysihtml5/wysihtml5-0.3.0.min.js"></script>
	<script src="../assets/bootstrap3-editable-1.5.1/wysihtml5/wysihtml5.js"></script>
	<script src="../assets/bootstrap3-editable-1.5.1/wysihtml5/bootstrap-wysihtml5-0.0.2.js"></script>	
	<script src="../assets/js/core/moment.min.js"></script>
	<script src="../assets/js/core/combodate.js"></script>
    <script src="../assets/js/magnific-popup/jquery.magnific-popup.min.js"></script>
    <script src="../assets/js/magnific-popup/magnific-popup-zoom-gallery.js"></script>
    <script src="../assets/js/progress-bar/bootstrap-progressbar.js"></script>
    <script src="../assets/js/progress-bar/bootstrap-progressbar-main.js"></script>
    <script src="../assets/js/main/jquery.appear.js"></script>
    <script src="../assets/js/main/isotope.pkgd.min.js"></script>
    <script src="../assets/js/main/parallax.min.js"></script>
    <script src="../assets/js/main/jquery.countTo.js"></script>
    <script src="../assets/js/main/owl.carousel.min.js"></script>
    <script src="../assets/js/main/owl.carousel.min.js"></script>
    <script src="../assets/js/main/ion.rangeSlider.min.js"></script>
    <script src="../assets/js/main/main.js"></script>
	<script type="text/javascript">
		//$('.username').editable('option', 'disabled', true);
        $.fn.editable.defaults.mode = "popup";                       
        $('.inlineedit').editable();          
    </script>
</body>
</html>