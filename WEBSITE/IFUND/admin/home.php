<?php 
session_start();
if(!isset($_SESSION['username'])=="admin")
{
	header("location: ../pic_user.php"); // Redirecting To Other Page
}

include '../includes/db_config.php';
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

<body id="page-top" data-spy="scroll" data-target=".navbar" data-offset="100">
    
    <?php    
    include '../includes/pageloader.php';
    include '../includes/navbar.php';
	include '../includes/header-carousel.php';	
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
?>
		<div class="ml15 mr15 portfolio_border">
        <div class="animated pt15" data-animation="fadeInLeft" data-animation-delay="100"> 
		<img src="../assets/img/other/<?php echo $record['project_logo']?>" alt="website service" class="img-responsive"></div>
        <div class="animated" data-animation="fadeIn" data-animation-delay="100">
		<h3 class="font-size-normal text-center pt5 pb5 ml0 mr0 portfolio_bg2"> <small class="color-primary pb10">Category</small> <span href="#" class="inlineedit" data-name="project_category" data-type="text" data-pk="<?php echo $record['project_id'] ?>" data-url="pjtlstupdate.php"><?php echo$record['project_category']?></span></h3>
		<p class="mt20 pl10 pr10"><span href="#" class="inlineedit" data-name="project_desc_short" data-type="textarea" data-pk="<?php echo $record['project_id'] ?>" data-url="pjtlstupdate.php"><?php echo $record['project_desc_short'] ?></span></p>
		<h3 class="font-size-normal text-center mb0 pb10 pt10 portfolio_bg3"> <small class="color-primary pb15">Seed Stage Funding Goal</small>$ <span href="#" class="inlineedit" data-name="project_fund_goal" data-type="text" data-pk="<?php echo $record['project_id'] ?>" data-url="pjtlstupdate.php"><?php echo$record['project_fund_goal']?></span></h3>
		<div class="text-center pt15 pb15"><a href="ifindcard.php?id=<?php echo $record['project_id']?>" class="button button-md button-pasific hover-ripple-out tex">View More</a></div>
		</div>
		</div>
<?php 
}
mysqli_free_result($result);
?>
					</div>
                </div>
            </div>
        </div>
    </div>
</div>
    
    <div id="info-1" class="pt50 pb50 mt75 parallax-window" data-parallax="scroll" data-speed="0.5" data-image-src="../assets/img/bg/img-bg-2.jpg">
        <div class="container">
            <div class="row pt75">
                <div class="col-md-12 text-center">
                    <h1 class="color-light"> <small class="color-light">The best way to be success</small> Are you ready to be join as Investor?</h1>
                    
                </div>
            </div>
        </div>
    </div>
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
    <script src="../assets/js/main/ion.rangeSlider.min.js"></script>
    <script src="../assets/js/main/main.js"></script>
	<script type="text/javascript">
		//$('.username').editable('option', 'disabled', true);
        $.fn.editable.defaults.mode = "popup";                       
        $('.inlineedit').editable();          
    </script>
	
</body>

</html>