<div id="regformmodal" class="modal fade" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content pl25 pr25">
				<div class="modal-header pb0">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>					
					<div class="text-center text-uppercase m15 letter-space-05" style="font-size:18px; color:#3a403e;">Investor Registration</div>
				</div>
                <div class="modal-body">
                    <div class="message"></div>
				<ul id="signup-step" class="mfprogress text-center">
					<li id="user" class="active">User Details</li>
					<li id="billingaddress" class="">Billing Address</li>
					<li id="shippingaddress" class="">Shipping Address</li>
				</ul>
				<form name="regform" class="inline-form" id="regform" method="post" role="form">
					<span id="emptydataalert" class="signup-error"></span>                          
					<div id="user-field">						
						<input type="hidden" id="pic" name="pic" value="<?php echo $_SESSION['inv_id']?>" />					
						<div class="form-group">                            
							<input type="text" class="form-control" name="rfname" id="rfname" placeholder="First Name">
							<span id="fname-error" class="signup-error"></span>
						</div>
						<div class="form-group">                            
							<input type="text" class="form-control" name="rlname" id="rlname" placeholder="Last Name">
							<span id="lname-error" class="signup-error"></span>
						</div>
						<div class="form-group">                            
							<input type="text" class="form-control" name="runame" id="runame" placeholder="User Name">
							<span id="uname-error" class="signup-error"></span>
						</div>		
						<div class="form-group">
							<input type="email" class="form-control" name="remail" id="remail" value="<?php echo $_SESSION['inv_email']?>" placeholder="Email" disabled />
							<span id="email-error" class="signup-error"></span>
						</div>
						<div class="form-group">                            
							<input type="password" class="form-control" name="rpass" id="rpass" placeholder="Password">
							<span id="password-error" class="signup-error"></span>							
						</div>	
						<div class="form-group">                            
							<input type="password" class="form-control" name="rcpass" id="rcpass" placeholder="Confirm Password">
							<span id="confirm-password-error" class="signup-error"></span>							
						</div>	
					</div>
					<div id="billingaddress-field" style="display:none;">
						<div class="form-group">                            
							<input type="text" class="form-control" id="billing_address1" name="billing_address1" placeholder="Billing Address"/>
							<span id="billing_address1_error" class="signup-error"></span>
						</div>
						<div class="form-group">                            
							<input type="text" class="form-control" id="billing_address2" name="billing_address2" placeholder="Billing Address"/>
							<span id="billing_address2_error" class="signup-error"></span>
						</div>
						<div class="form-group">                            
							<input type="text" class="form-control" id="billing_city" name="billing_city" placeholder="city"/>
							<span id="billing_city_error" class="signup-error"></span>
						</div>
						<div class="form-group">                            
							<input type="text" class="form-control" id="billing_zip" name="billing_zip" placeholder="zipcode"/>
							<span id="billing_zip_error" class="signup-error"></span>
						</div>
						<div class="form-group">
                            <select id="rcountry" name="rcountry" class="selectpicker" data-width="100%" title="Select Your Country">
								<?php 
								$query1 = "select * from country";
								$result1 = mysqli_query($conn, $query1);
								while($record1 = mysqli_fetch_assoc($result1))
								{								
								?>
								<option value="<?php echo $record1['country'] ?>"><?php echo $record1['country'] ?></option>
                                <?php
								}
								?>
                            </select>
							<span id="country-error" class="signup-error"></span>
                        </div>
						
						<div class="form-group">                            
							<input type="text" class="form-control" id="rphone" name="rphone" placeholder="phone"/>
							<span id="phone-error" class="signup-error"></span>
						</div>
					</div>
					<div id="shippingaddress-field" style="display:none;">
						<div class="form-group">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" id="sameas" value="1" onclick="sameAsAbove()"> Check here if Mailing address same as shiffing address</label>
                            </div>
                        </div>						
						<div class="form-group">                            
							<input type="text" class="form-control" id="shipping_address1" name="shipping_address1" placeholder="Shipping Address"/>
							<span id="shipping_address1_error" class="signup-error"></span>
						</div>
						<div class="form-group">                            
							<input type="text" class="form-control" id="shipping_address2" name="shipping_address2" placeholder="Shipping Address"/>
							<span id="shipping_address2_error" class="signup-error"></span>
						</div>
						<div class="form-group">                            
							<input type="text" class="form-control" id="shipping_city" name="shipping_city" placeholder="city"/>
							<span id="shipping_city_error" class="signup-error"></span>
						</div>
						<div class="form-group">                            
							<input type="text" class="form-control" id="shipping_zip" name="shipping_zip" placeholder="zipcode"/>
							<span id="shipping_zip_error" class="signup-error"></span>
						</div>
					</div>
					<div id="multiform" class="row mr0 ml0 pt20 pb25">
						
						<a id="back" name="back" class="text-center button button-md text-center btn-30 bglg_1 pull-left pl10 pr10 pt5 pb5 text-capitalize letter-space-0" style="display:none; border-radius:4px; font-size: 14px;"><i class="fa fa-chevron-left ml0 mr5" aria-hidden="true"></i>Back</a>
						
						<a id="next" name="next" class="text-center button button-md text-center btn-30 bglg_1 pull-right pl10 pr10 pt5 pb5 text-capitalize letter-space-0" style="border-radius:4px; font-size: 14px;">Next<i class="fa fa-chevron-right ml5 mr0" aria-hidden="true"></i></a>
						
						<button type="submit" id="finish" name="finish" class="button btn_1 button-md text-center btn-100 bg_grn mt25" style="display:none;">Submit</button>
					</div>
				</form>
                </div>
            </div>
        </div>
    </div>
	
	<!-- reg confirm modal -->
    <div class="modal fade" id="reg-confirm-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="z-index:99999;">
        <div class="modal-dialog m-width" role="document">
            <div class="modal-content brdr_blue">
                <div class="modal-body text-center">
                    <h4 class="sm_fnt_1"><span style="color:#f60; font-size:18px;">You are Successfully Registered!</span> <hr style="margin:10px 0;"> User Id and Password<br/>sent to your given mail id.</h4>
                    <span style="color:#0070bc; font-size:18px;"><a href="#" data-toggle="modal" data-dismiss="modal" aria-hidden="true" data-target="#loginModal">Please Login</a></span>
                </div>
            </div>
        </div>
    </div>
	
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
                            <input type="email" class="form-control" id="LEmail" pattern="^[\w\-\.\+]+\@[a-zA-Z0-9\.\-]+\.[a-zA-z0-9]{2,4}$"  placeholder="Email" required>
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
								<a id="showregform" onclick="showregform()" class="text-center button btn_2 button-md text-center btn-100 bg_blue">Don't have a Account ?&nbsp;&nbsp;<span class="col-grn">Apply here</span></a>
							</div>
                        </div>
                    </form>                                        
                </div>
            </div>
        </div>
    </div>