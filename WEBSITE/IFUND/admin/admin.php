<?php
session_start();
if (!isset($_SESSION['username']) == "admin") {
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
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <link rel="shortcut icon" href="../assets/img/favicon.png">
    <link rel="stylesheet" href="../assets/css/core/bootstrap.min.css">
    <link rel="stylesheet" href="../assets/css/core/animate.min.css">
    <link rel="stylesheet" href="../assets/css/core/bootstrap-fileupload.min.css">
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
    <link rel="stylesheet" href="custom.css">
    <link rel="stylesheet" href="../assets/bootstrap3-editable-1.5.1/wysihtml5/bootstrap-wysihtml5.css">
</head>
<body id="topPage" data-spy="scroll" data-target=".navbar" data-offset="100">
<?php
include '../includes/pageloader.php';
include '../includes/navbar.php';
?>

<div class="mt80 container body">
    <div class="col-md-3 left_col">
        <div class="left_col scroll-view">
            <!-- sidebar menu -->
            <div class="main_menu_side hidden-print main_menu" id="sidebar-menu">
                <div class="menu_section">                    
                    <ul class="nav side-menu">
                        <li><a><i class="fa fa-home"></i> Investor Management <span class="fa fa-chevron-down"></span></a>
                            <ul style="display: none;" class="nav child_menu">
                                <li><a href="#" onclick="get_pic_req_list()">Investor Activation</a></li>
                                <li><a href="#" onclick="get_activated_pic()">Activated PIC</a></li>
                                <li><a href="#" onclick="get_expired_pic()">Expired PIC</a></li>
                                <li><a href="#" onclick="PIC_Generate_form()">Generate PIC</a></li>
                            </ul>
                        </li>
                        <li><a><i class="fa fa-edit"></i> Project <span class="fa fa-chevron-down"></span></a>
                            <ul style="display: none;" class="nav child_menu">
                                <li><a href="#" onclick="projects()">Projects</a></li>
                                <li><a href="#" onclick="add_country_UI()">Country</a></li>
                                <li><a href="#" onclick="team_members_UI()">Team Members</a></li>
                                <li><a href="#" onclick="partners_UI()">Partners</a></li>
                                <li><a href="#" onclick="competitors_UI()">Competitors</a></li>
                                <li><a href="#" onclick="traction_testing_UI()">Traction / Testing</a></li>
                            </ul>
                        </li>
						<li><a><i class="fa fa-edit"></i> Investments <span class="fa fa-chevron-down"></span></a>
                            <ul style="display: none;" class="nav child_menu">                                
                                <li><a href="#" onclick="transection_details()">Transaction Details</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
            <!-- /sidebar menu -->
        </div>
    </div>
    <!-- ############## -->
    <div role="main" class="right_col">
        <div id="data_fetch">
            <!-- dynamic content to be fetch here -->
        </div>        
    </div>
</div>

<script src="../assets/js/core/jquery.min.js"></script>
<script src="../assets/js/core/bootstrap.min.js"></script>
<script src="../assets/js/core/bootstrap-fileupload.js"></script>
<script src="../assets/bootstrap3-editable-1.5.1/bootstrap3-editable/js/bootstrap-editable.min.js"></script>
<script src="../assets/bootstrap3-editable-1.5.1/wysihtml5/wysihtml5-0.3.0.min.js"></script>
<script src="../assets/bootstrap3-editable-1.5.1/wysihtml5/wysihtml5.js"></script>
<script src="../assets/bootstrap3-editable-1.5.1/wysihtml5/bootstrap-wysihtml5-0.0.2.js"></script>
<script src="../assets/js/progress-bar/bootstrap-progressbar.min.js"></script>
<script src="../assets/js/progress-bar/bootstrap-progressbar-main.js"></script>
<script src="../assets/js/core/moment.min.js"></script>
<script src="../assets/js/core/combodate.js"></script>
<script src="../assets/js/main/jquery.appear.js"></script>
<script src="../assets/js/main/isotope.pkgd.min.js"></script>
<script src="../assets/js/main/parallax.min.js"></script>
<script src="../assets/js/main/jquery.countTo.js"></script>
<script src="../assets/js/main/owl.carousel.min.js"></script>
<script src="../assets/js/main/jquery.sticky.js"></script>
<script src="../assets/js/main/ion.rangeSlider.min.js"></script>
<script src="../assets/js/main/main.js"></script>
<script src="custom.js"></script>
<script src="admin_page.js"></script>
</body>
</html>