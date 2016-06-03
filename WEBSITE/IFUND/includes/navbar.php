<nav class="navbar navbar-pasific navbar-mp megamenu navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-main-collapse"> <i class="fa fa-bars"></i> </button>
                <a class="navbar-brand page-scroll" href="#page-top"> <img src="../assets/img/logo/logo-default.png" alt="logo"> <span style="color:#8cc63f;">i</span>Fund <span style="color:#8cc63f;">Network</span> </a>
            </div>
            <div class="navbar-collapse collapse navbar-main-collapse">
                <ul class="nav navbar-nav">
					<li><a href="admin.php">Investor management</a></li>
                    <li><a href="home.php">Home </a></li>
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
                                            <a href="../userprofile.php" class="btn btn-primary btn-sm active">View Profile</a>
                                        </div>
                                    </div>
                                </div>
                                <div class="navbar-footer mt15">
                                    <div class="navbar-footer-content">
                                        <div class="row">
                                            <div class="col-md-6 col-sm-6 col-xs-6">
                                                <a href="../userprofile.php" class="btn btn-default btn-sm font-black">Change Passowrd</a>
                                            </div>
                                            <div class="col-md-6 col-sm-6 col-xs-6">
                                                <a href="../process/logout.php" class="btn btn-default btn-sm pull-right font-black">Sign Out</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
</nav>