<?php 
session_start();
if(!isset($_SESSION['username'])=="admin")
{
	header("location: ../pic_user.php"); // Redirecting To Other Page
}
include '../includes/db_config.php';
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
    <?php
	$query = "select * from faq";
	$result = mysqli_query($conn, $query); 
	$record = mysqli_fetch_array($result);
	?>
    <header class="pt100 pb100 parallax-window-2" data-parallax="scroll" data-speed="0.5" data-image-src="../assets/img/bg/<?php echo $record['faq_bg_image']?>" data-positionY="1000">
        <div class="intro-body text-center">
            <div class="container">
                <div class="row">
                    <div class="col-md-12 pt50">
                        <h1 class="brand-heading font-montserrat text-uppercase color-light" data-in-effect="fadeInDown"><a href="#" class="inlineedit color-white" data-name="faq_main_title" data-type="text" data-pk="<?php echo $record['faq_id'] ?>" data-url="pjtlstupdate.php"><?php echo $record['faq_main_title']?></a></h1>
                        <p class="intro-text color-light text-open-sans text-uppercase" data-in-effect="swing"><a href="#" class="inlineedit color-white" data-name="faq_sub_title" data-type="text" data-pk="<?php echo $record['faq_id'] ?>" data-url="pjtlstupdate.php"><?php echo $record['faq_sub_title']?></a></p><?php mysqli_free_result($result); ?>  <!--<a class="button button-pasific button-lg hover-ripple-out animated" data-animation="fadeInUp" data-animation-delay="1200">Submit Ticket</a>-->
                    </div>
                </div>
            </div>
        </div>
    </header>
	
    <div id="faqs" class="bg-gray pt50 pb20 bt-solid-1">
        <div class="container">
            <div class="row">
            <div class="row">
                <div class="container">
                    <div class="row">
                        <h3 class="text-center mb20">How can we help you?</h3>
							
							<div class="col-md-7 col-md-offset-2 col">
							<input type="text" class="form-control" id="searchquery" name="search" placeholder="Search FAQs" value="">
							
							</div>	
							<div class="col-md-1">
							<input type="submit" class="btn btn-lg btn-success" value="Search" id="faqsearch">
							</div>
                    </div>
                </div>
            </div>
            <div class="row mt35">
                <div class="col-md-8 col-md-offset-2 col-sm-8 col-sm-offset-2 col-xs-8 col-xs-offset-2 mb30">
                    <div class="panel-group" id="accordion5">
					<?php					
						$query = "select * from faq";
						$result = mysqli_query($conn, $query); 
						$i=0;
							while($row = mysqli_fetch_array($result))
							{ 
								if($i==0)
								{
								?>
						
					
                        <div class="panel">
                            <div class="panel-heading"> <a href="#<?php echo $row['faq_id']?>" class="font-black collapsed accordian-toggle-chevron-left" data-toggle="collapse" data-parent="#accordion5"><span class="inlineedit" data-name="faq_question" data-type="text" data-pk="<?php echo $record['faq_id'] ?>" data-url="pjtlstupdate.php"><?php echo $row['faq_question']?></span></a>
                            </div>
                            <div id="<?php echo $row['faq_id']?>" class="panel-collapse collapse in active">
                                <div class="panel-body">
								<a href="#" class="inlineedit" data-name="faq_ans" data-type="wysihtml5" data-pk="<?php echo $record['faq_id'] ?>" data-url="pjtlstupdate.php"><?php echo $row['faq_ans']?></a>
								</div>
                            </div>
                        </div>
						<?php 
						}
						else
						{
						?>		
                        <div class="panel">
                            <div class="panel-heading"> <a href="#<?php echo $row['faq_id']?>" class="font-black collapsed accordian-toggle-chevron-left" data-toggle="collapse" data-parent="#accordion5"><span class="inlineedit" data-name="faq_question" data-type="text" data-pk="<?php echo $record['faq_id'] ?>" data-url="pjtlstupdate.php"><?php echo $row['faq_question']?></span></a>
                            </div>
                            <div id="<?php echo $row['faq_id']?>" class="panel-collapse collapse">
                                <div class="panel-body">
								<a href="#" class="inlineedit" data-name="faq_ans" data-type="wysihtml5" data-pk="<?php echo $record['faq_id'] ?>" data-url="pjtlstupdate.php"><?php echo $row['faq_ans']?></a>
								</div>
                            </div>
                        </div>
						<?php
						}
						$i++;
						}
						?>                        
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
	<script>
		$(document).ready(function(){
		
		$("#faqsearch").click(function() {
			
			var query = $('#searchquery').val();			
			if(query.length >0){
				$.ajax({
					type: "POST",
					url: "process/faqsearch.php",
					data: 'query='+query,					
					cache: false,
					success: function(response)
					{
						$("#accordion5").html(response).show();
					}
				});
			}return false;
		});
		
	});
	</script>
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
		//$('.inlineedit').editable('option', 'disabled', true);
        $.fn.editable.defaults.mode = "popup";                       
        $('.inlineedit').editable();          
    </script>
</body>
</html>