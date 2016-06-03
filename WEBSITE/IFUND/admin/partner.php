<?php 
session_start();
if(!isset($_SESSION['username'])=="admin")
{
	header("location: ../pic_user.php"); // Redirecting To Other Page
}
include '../includes/db_config.php';
$query = "select * from partners";
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

<body id="topPage" data-spy="scroll" data-target=".navbar" data-offset="100" class="bglg">
    <?php    
    include '../includes/pageloader.php';
    include '../includes/navbar.php';
    ?>
    <?php 
	$result = mysqli_query($conn, $query); 
	$record = mysqli_fetch_array($result);
	?>
    <header class="pt100 pb100 parallax-window-2" data-parallax="scroll" data-speed="0.5" data-image-src="../assets/img/bg/<?php echo $record['partner_bg_image']?>" data-positionY="1000">
        <div class="intro-body text-center">
            <div class="container">
                <div class="row">
                    <div class="col-md-12 pt50">
                        <h1 class="brand-heading font-montserrat text-uppercase color-light" data-in-effect="fadeInDown"><a href="#" class="inlineedit color-white" data-name="partner_main_title" data-type="text" data-pk="<?php echo $record['partners_id'] ?>" data-url="pjtlstupdate.php"><?php echo $record['partner_main_title']?></a></h1>
                    </div>
                </div>
            </div>
        </div>
    </header>
	<?php
	mysqli_free_result($result);
	?>
	
<div class="container mt50">
  <div class="row row-eq-height">
    <div class="col-md-4 col-sm-4 mt25 ml10 mr10 pt15 pb15 bg-white">
        <ul class="nav nav-pills nav-stacked partner">
		
		<?php
		$result = mysqli_query($conn, $query); 
		$i=0;
		while($record = mysqli_fetch_array($result))
		{
			if ($i==0)
			{ ?>
				<li class="active"><a href="#<?php echo $record['partners_id']?>" data-toggle="tab">
					<h4><span href="#" class="inlineedit" data-name="partner_profession" data-type="text" data-pk="<?php echo $record['partners_id'] ?>" data-url="pjtlstupdate.php"><?php echo $record['partner_profession']?></span><br/><small><span href="#" class="inlineedit" data-name="partner_country" data-type="text" data-pk="<?php echo $record['partners_id'] ?>" data-url="pjtlstupdate.php"><?php echo $record['partner_country']?></span></small></h4>		  
					<img class="img-responsive" src="../assets/img/<?php echo $record['partner_logo']?>" width="50%" height="50%" alt=""></img></a>
				</li>
			<?php 
			}
			else
			{ ?>
				<li><a href="#<?php echo $record['partners_id']?>" data-toggle="tab">
					<h4><span href="#" class="inlineedit" data-name="partner_profession" data-type="text" data-pk="<?php echo $record['partners_id'] ?>" data-url="pjtlstupdate.php"><?php echo $record['partner_profession']?></span><br/><small><span href="#" class="inlineedit" data-name="partner_country" data-type="text" data-pk="<?php echo $record['partners_id'] ?>" data-url="pjtlstupdate.php"><?php echo $record['partner_country']?></span></small></h4>
					<img class="img-responsive" src="../assets/img/<?php echo $record['partner_logo']?>" width="50%" height="50%" alt=""></img></a>
				</li>
			<?php } 
			$i++;
		} 
		mysqli_free_result($result);
		?>
		 
        </ul>
	</div>
	<div class="col-md-8 col-sm-8 mt25 pt15 pb15 bg-white">
        <div class="tab-content pl50 pr50">
		<?php
		$result = mysqli_query($conn, $query); 		
		$i=0;
		while($record = mysqli_fetch_array($result))
		{
			if($i==0)			
				{?>
					<div class="tab-pane active" id="<?php echo $record['partners_id']?>">
						<img class="img-responsive" src="../assets/img/<?php echo $record['partner_logo']?>" alt="oonsoft"></img>
						<p><a href="#" class="inlineedit" data-name="partner_desc" data-type="wysihtml5" data-pk="<?php echo $record['partners_id'] ?>" data-url="pjtlstupdate.php"><?php echo $record['partner_desc']?></a></p>				
					</div>
				<?php 
				}
				else
				{ ?>
					<div class="tab-pane" id="<?php echo $record['partners_id']?>">
						<img class="img-responsive" src="../assets/img/<?php echo $record['partner_logo']?>" alt="oonsoft"></img>
						<p><a href="#" class="inlineedit" data-name="partner_desc" data-type="wysihtml5" data-pk="<?php echo $record['partners_id'] ?>" data-url="pjtlstupdate.php"><?php echo $record['partner_desc']?></a></p>
					</div>
				<?php
				}
			$i++;		
		} 
		mysqli_free_result($result);
		?>
          
        </div><!-- /tab-content -->
    </div><!-- /col -->
  </div><!-- /row -->
</div><!-- /container -->
	
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