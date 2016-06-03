<?php
session_start();
if (!isset($_SESSION['username']) == "admin") {
    header("location: ../pic_user.php"); // Redirecting To Other Page
}
include '../includes/db_config.php';
$query = "select * from about_us";
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
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
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
<header class="pt100 pb100 parallax-window-2" data-parallax="scroll" data-speed="0.5"
        data-image-src="../assets/img/bg/<?php echo $row['about_bg_image'] ?>" data-positionY="1000">
    <div class="intro-body text-center">
        <div class="container">
            <div class="row">
                <div class="col-md-12 pt50">
                    <h1 class="brand-heading font-montserrat text-uppercase color-light" data-in-effect="fadeInDown"><a
                            href="#" class="inlineedit color-white" data-name="about_main_title" data-type="text"
                            data-pk="<?php echo $row['about_id'] ?>"
                            data-url="pjtlstupdate.php"><?php echo $row['about_main_title'] ?></a>
                        <small class="color-light alpha7"><a href="#" class="inlineedit color-white" data-name="about_sub_title" data-type="text" data-pk="<?php echo $row['about_id'] ?>" data-url="pjtlstupdate.php"><?php echo $row['about_sub_title'] ?></a>
                        </small>
                        </h1>
                </div>
            </div>
        </div>
    </div>
</header>

<div id="about" class="pt75 pb50">
    <div class="container">
        <div class="row">
            <div class="col-md-6 col-sm-6 col-xs-12">
                <img src="../assets/img/other/<?php echo $row['about_section_image'] ?>" alt="about us"
                     class="img-responsive center-block">
            </div>
            <div class="col-md-6 col-sm-6 col-xs-12">
                <h3 class="mt5"><a href="#" class="inlineedit" data-name="about_section" data-type="text"
                       data-pk="<?php echo $row['about_id'] ?>"
                       data-url="pjtlstupdate.php"><?php echo $row['about_section'] ?></a>
                    <small class="heading heading-solid"></small>
                </h3>
                <p><a href="#" class="inlineedit" data-name="about_section_desc" data-type="wysihtml5"
                      data-pk="<?php echo $row['about_id'] ?>"
                      data-url="pjtlstupdate.php"><?php echo $row['about_section_desc'] ?></a></p>
            </div>
        </div>

        <div class="row pt50">
            <div class="col-md-6 col-sm-6 col-xs-12">
                <h3><a href="#" class="inlineedit" data-name="about_section1" data-type="text"
                       data-pk="<?php echo $row['about_id'] ?>"
                       data-url="pjtlstupdate.php"><?php echo $row['about_section1'] ?></a>
                    <small class="heading heading-solid"></small>
                </h3>
                <p><a href="#" class="inlineedit" data-name="about_section_desc1" data-type="wysihtml5"
                      data-pk="<?php echo $row['about_id'] ?>"
                      data-url="pjtlstupdate.php"><?php echo $row['about_section_desc1'] ?></a></p>
            </div>
            <div class="col-md-6 col-sm-6 col-xs-12">
                <img src="../assets/img/other/<?php echo $row['about_section_image1'] ?>" alt="about us"
                     class="img-responsive center-block">
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