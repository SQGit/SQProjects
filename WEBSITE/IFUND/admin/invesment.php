<?php 
session_start();
if(!(isset($_SESSION['username']) || isset($_SESSION['inv_id']) || isset($_SESSION['inv_email'])))
{
	header("location: pic_user.php"); // Redirecting To Other Page
}
include '../includes/db_config.php';
$inv_id = $_SESSION['inv_id'];

$msg='';
if(isset($_GET['msg'])){
$msg=$_GET['msg'];
}

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
    <link rel="stylesheet" href="../assets/css/core/jquery-ui.css">
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
    <!--[if lt IE 9]> <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script> <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script> <![endif]-->
</head>

<body id="page-top" data-spy="scroll" data-target=".navbar" data-offset="100">
    <div id="pageloader">
        <div class="loader-item"> <img src="../assets/img/other/puff.svg" alt="page loader">
        </div>
    </div>
    <nav class="navbar navbar-pasific navbar-mp megamenu navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-main-collapse"> <i class="fa fa-bars"></i> </button>
                <a class="navbar-brand page-scroll" href="#page-top"> <img src="../assets/img/logo/logo-default.png" alt="logo"> <span style="color:#8cc63f;">i</span>Fund <span style="color:#8cc63f;">Network</span> </a>
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

	<header class="pt100 pb100 parallax-window-2" data-parallax="scroll" data-speed="0.5" data-image-src="../assets/img/bg/investment.jpg" data-positionY="1000">
        <div class="intro-body text-center">
            <div class="container">
                <div class="row">
                    <div class="col-md-12 pt50">
                       <h2 class="text-center color-white"></h2>
						<h1 class="brand-heading font-montserrat text-uppercase color-light" data-in-effect="fadeInDown">Investment Criteria</h1>
                    </div>
                </div>
            </div>
        </div>
    </header>
	
    <div id="welcome" class="pt25 pb25"></div>
	<div id="paypal_msg" class="text-center"><?php echo $msg; ?></div>
	<div id="investment Criteria" class="container">
	
	
	<?php
	$query7 = "SELECT * from investment_criteria";
	$result7 = mysqli_query($conn, $query7);
	while($row7 = mysqli_fetch_assoc($result7))
	{
	echo '<div class="row bg-gray border-black brdr-full_1 ">
	<div class="col-md-12">
	<h4 style="font-size:20px; font-weight:800;">SELECT YOUR INVESTMENT PORTFOLIO</h4>	
	<ol>'.$row7['points'].'</ol>	
	<div id="note" class="pt25 pb10">
	<h5>Note</h5>
	<ul class="no-icon-list pt5 pb20 pl15 mb0">'.$row7['note'].'</ul>
	</div>
	</div>
	</div>';
	}?>
	
	<!-- Portfolio group -->
	<div class="row row-centered bg-gray mt20 p20">
	<div class="row-height">
	<!-- Portfolio 1 -->
	<div class="col-md-4 col-sm-4">
	<form id="single_portfolio" method="POST" action="../payment_process.php" onSubmit="return single_portfolio_validate();">
	<div class="border-black brdr-full_1  pr0 pl0 col-centered col-xs-12">
	<div class="row bg-violet m0">
		<div class="col-md-12 col-sm-12">
			<h4 class="text-center">Single Portfolio</h4>
		</div>
	</div>
	
	<?php
	$query1 = "SELECT * from project_list WHERE funding_required='1'";
	$result1 = mysqli_query($conn, $query1);
	while($row1 = mysqli_fetch_assoc($result1))
	{	
	
	echo '<div class="row ml0 mr0 mt20 mb20 pt10 pb10">	
	<div class="col-md-2 col-sm-2 pull-center">
		<input type="checkbox" value="'.$row1['project_name'].'" name="sp[]" class="center"/>
	</div>
	<div class="col-md-9 col-sm-9 pull-center">
		<img class="center"  height="50" width="80%" src="../assets/img/'.$row1['pjt_payment_logo'].'"  width="" alt=""/>
	</div>
	</div>';
	}
	?>
	
	<div class="row bg-violet m0">	
	<div class="col-md-12 col-sm-12 text-center">
		<h4 class="">Investment Amount</h4>
	</div>
	<?php
	$query3 = "SELECT MIN(amount) FROM single_port";
	$query4 = "SELECT MAX(amount) FROM single_port";
	$result3 = mysqli_query($conn, $query3);
	$result4 = mysqli_query($conn, $query4);
	$row3=mysqli_fetch_row($result3);
	$row4=mysqli_fetch_row($result4);
	?>
	
	<div class="col-md-12 col-sm-12 text-center">
		<?php
		echo '<p class="fs-14">$'.$row3[0].' - $'.$row4[0].'</p>';
		mysqli_free_result($result3);
		mysqli_free_result($result4);
		?>
	</div>
	<div class="col-md-12 col-sm-12 text-center">
		<span><strong>$ </strong></span>
		<select name="pay_amount" id="pay_amount" style="width:60%; padding:4px;"> 
		<option value="">Select Amount</option>
		<?php
		$query2 = "SELECT * from single_port";
		$result2 = mysqli_query($conn, $query2);
		while($row2 = mysqli_fetch_assoc($result2))
		{		
		echo '<option value="'.$row2['amount'].'">'.$row2['amount'].'</option>';
		}
		mysqli_free_result($result2);
		?>
		</select>
	</div>
	<input type="hidden" id="hv" value="ok"/>
	<div class="col-md-12 col-sm-12 text-center pt25 pb25">                            
		<!--<button type="submit" id="" class="btn btn-info">Submit</button>-->
		<input type="submit" class="btn btn-info" name="submit" id="submit_single_portfolio" value="Submit"/>
    </div>
	
	</div>
	</div>
	</form>
	<div id="single_portfolio_msg" class="text-center pt10 error"></div>	
	</div>
	
	
	<!-- Portfolio 2 -->
	
	<div class="col-md-4 col-sm-4"> 
	<form id="double_portfolio" method="POST" action="../payment_process1.php" onSubmit="return double_portfolio_validate();">
	<div class="border-black brdr-full_1  pr0 pl0 col-centered col-xs-12">
	<div class="row bg-violet m0">
		<div class="col-md-12">
			<h4 class="text-center">Portfolio Bundle 2</h4>
		</div>
	</div>
	
	<?php
	mysqli_data_seek($result1, 0);
	while($row1 = mysqli_fetch_assoc($result1))
	{
	echo '<div class="row ml0 mr0 mt20 mb20 pt10 pb10">	
	<div class="col-md-2 col-sm-2 pull-center" >
		<input type="checkbox" value="'.$row1['project_name'].'" name="sp[]" class="center"/>		
	</div>
	<div class="col-md-9 col-sm-9 pull-center">				
		<img class="center"  height="50" width="80%" src="../assets/img/'.$row1['pjt_payment_logo'].'"  width="" alt=""/>
	</div>
	</div>';
	}?>
	

	<div class="row bg-violet m0">	
	<div class="col-md-12 col-sm-12 text-center">
		<h4 class="">Investment Amount</h4>
	</div>
	
	<div class="col-md-12 col-sm-12 text-center">	
	<?php
	$query5 = "SELECT * FROM double_port";
	$result5 = mysqli_query($conn, $query5);
	$row5=mysqli_fetch_assoc($result5);
		echo '<input type="hidden" id="double_min" name="double_min" value="'.$row5['min_amt'].'"/>';
		echo '<input type="hidden" id="double_max" name="double_max" value="'.$row5['max_amt'].'"/>';
		echo '<p class="fs-14">$'.$row5['min_amt'].' - $'.$row5['max_amt'].'</p>';
	mysqli_free_result($result5);
	?>
	</div>

	<div class="col-md-12 col-sm-12 text-center">		
		<strong>$ </strong><input type="text" name="pay_amount" style="width:60%;" placeholder="Enter Amount">
	</div>
	<div class="col-md-12 col-sm-12 text-center pt25 pb25">                            
		<!--<button type="submit" id="" class="btn btn-info">Submit</button>-->
		<input type="submit" class="btn btn-info" name="submit" id="submit_double_portfolio" value="Submit"/>
    </div>	
	</div>
	</div>
	</form>
	<div id="double_portfolio_msg" class="text-center pt10 error"></div>
	</div>
	
	<!-- Portfolio 3 -->
	<div class="col-md-4 col-sm-4"> 
	<form id="full_portfolio" method="POST" action="../payment_process1.php" onSubmit="return full_portfolio_validate();">
	<div class="border-black brdr-full_1  pr0 pl0 col-centered col-xs-12">
	<div class="row bg-violet m0">
		<div class="col-md-12">
			<h4 class="text-center">Portfolio Bundle 3</h4>
		</div>
	</div>
	
	<?php
	mysqli_data_seek($result1, 0);
	while($row1 = mysqli_fetch_assoc($result1))
	{
	echo '<div class="row ml0 mr0 mt20 mb20 pt10 pb10">	
	<div class="col-md-11 col-sm-11">
		<input type="checkbox" value="'.$row1['project_name'].'" name="sp[]" checked="true" style="display:none;" />
		<img class="center"  height="50" width="55%" src="../assets/img/'.$row1['pjt_payment_logo'].'"  width="" alt=""/>
	</div>
	</div>';
	}
	?>
	<div class="row bg-violet m0">	
	<div class="col-md-12 col-sm-12 text-center">
		<h4 class="">Investment Amount</h4>
	</div>
	<div class="col-md-12 col-sm-12 text-center">	
	<?php
	$query6 = "SELECT * FROM full_port";
	$result6 = mysqli_query($conn, $query6);
	$row6=mysqli_fetch_assoc($result6);
		echo '<input type="hidden" id="full_min" name="full_min" value="'.$row6['min_amt'].'"/>';
		echo '<input type="hidden" id="full_max" name="full_max" value="'.$row6['max_amt'].'"/>';
		echo '<p class="fs-14">$'.$row6['min_amt'].' - $'.$row6['max_amt'].'</p>';
	mysqli_free_result($result6);
	?>
	</div>
	<div class="col-md-12 col-sm-12 text-center">		
		<strong>$ </strong><input type="text" name="pay_amount" style="width:60%;" placeholder="Enter Amount">
	</div>
	<div class="col-md-12 col-sm-12 text-center pt25 pb25">                            
		<!--<button type="submit" id="" class="btn btn-info">Submit</button>-->
		<input type="submit" class="btn btn-info" name="submit" id="submit_full_portfolio" value="Submit"/>
    </div>	
	</div>
	</div>
	</form>
	<div id="full_portfolio_msg" class="text-center pt10 error"></div>
	</div>	
	</div>
	</div> <!-- Portfolio group end -->
	
	<div class="row mt25">
	<div class="tabs-menu col-md-4 col-sm-4 p20">

	<?php
	mysqli_data_seek($result1, 0);
	while($row1 = mysqli_fetch_assoc($result1))
	{
	echo '<div><a href="#tab-'.$row1['project_id'].'"><div class="col-md-11 col-sm-11 bg-violet border-black brdr-full_1 m15 pt20 pb20">
	<img class="center" src="../assets/img/'.$row1['pjt_payment_logo'].'"  width="65%" alt=""/>
	<h5 class="text-center">'.$row1['project_category'].'</h5>	
	</div></a></div>';
	}?>

	</div>
	<div class="col-md-8 col-sm-8 p20 mt15">
	<div class="tab">
	<?php
	mysqli_data_seek($result1, 0);
	$i=0;
	while($row1 = mysqli_fetch_assoc($result1))
	
	{	
	echo'<div id="tab-'.$row1['project_id'].'" class="tab-content1"><div class="col-md-12 col-sm-12 pt20 pb20">
	<img class="center" src="../assets/img/'.$row1['pjt_payment_logo'].'"  width="40%" alt=""/>
	</div>
	<div class="col-md-12 col-sm-12 text-center">
	<h4 style="font-size:20px; font-weight:800;">Investment Fund Pool</h4>
	<div class="">'.$row1['investment_fund_pool'].'
	<h4>You Will Recieve The Following Document After Completing The Process</h4>'.$row1['doc_receive'].'	
	</div>
	</div></div>';
	}

	?>
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
    <script src="../assets/js/core/jquery.min.js"></script>
    <script src="../assets/js/core/jquery-ui.js"></script>
    <script src="../assets/js/core/bootstrap.min.js"></script>
    <script src="../investment.js"></script>
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

</body>

</html>