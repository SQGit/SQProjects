//when document is ready
$(document).ready(function() {
    //center the all popup modal
    function centerModal() {
            $(this).css('display', 'block');
            var $dialog = $(this).find(".modal-dialog");
            var offset = Math.max(0, ($(window).height() - $dialog.height()) / 2);
            // Applying the top margin on modal dialog to align it vertically center
            $dialog.css("margin-top", offset);
        }
        // Align modal when it is displayed
    $('.modal').on('show.bs.modal', centerModal);
    //Align modal when user resize the window
    $(window).on("resize", function() {
        $('.modal:visible').each(centerModal);
    });
    //center the all popup modal end	

//============clear form fields on close modal==============start//
$('.modal').on('hidden.bs.modal', function (e) {
  $(this)
    .find("input,textarea,select")
       .val('')	   
       .end()    
	.find("input[type=checkbox], input[type=radio]")
       .prop("checked", "")
       .end();	  
});
//============clear form fields on close modal==============end//	
	
	
    $("#loginform").submit(function(event) {
        // cancels the form submission
        event.preventDefault();
        submitloginForm(); //call function
    });

    $("#picentermodalform").submit(function(event) {
        // cancels the form submission
        event.preventDefault();
        submitpicForm();
    });
    
    $("#AccessInquiry").submit(function(event) {
        // cancels the form submission
        event.preventDefault();
        submitpicrequestForm(); //call function
    });
	
	$("#contactform").submit(function(event) {
        // cancels the form submission
        event.preventDefault();
        guestinquiry(); //call function
    });
	
	$("#contactform1").submit(function(event) {
        // cancels the form submission
        event.preventDefault();
        userinquiry(); //call function
    });	
	
	
	$("#changepasswordsubmit").submit(function(event) {
        // cancels the form submission
        event.preventDefault();
        changepasswordsubmit(); //call function
    });		
	
 //modal redirect
    	
}); //end document ready

function submitpicrequestForm() {
        //store form datas to variable
        var acountry = $("#acountry :selected").text();
        var afname = $("#aFirstName").val();
        var alname = $("#aLastname").val();
        var email = $("#Email").val();
        var aphone = $("#aphone").val();
        var amessage = $('#amessage').val();
        //check empty fields
        if (acountry == '' || afname == '' || alname == '' || email == '' || aphone == '' || amessage == '') {
            $("#AccessInquiryResult").html("<span class=\"error\">Please fill all fields</span>");
       		
       } else if(!($("#AccessInquiry #aFirstName").val())) {		
		$("#AccessInquiry #aFirstName").focus();
		$("#AccessInquiryResult").html("<span class=\"error\">First Name required!</span>");	
	   } else if(!$("#AccessInquiry #aFirstName").val().match(/^([A-z]{3,})?$/)){
		$("#AccessInquiry #aFirstName").focus();
		$("#AccessInquiryResult").html("<span class=\"error\">Enter valid First Name (A-z) only allowed..</span>");
		} else if(!($("#AccessInquiry #aLastname").val())) {		
		$("#AccessInquiry #aLastname").focus();
		$("#AccessInquiryResult").html("<span class=\"error\">Last Name required!</span>");	
	   } else if(!$("#AccessInquiry #aLastname").val().match(/^([A-z]{1,})?$/)){
		$("#AccessInquiry #aLastname").focus();
		$("#AccessInquiryResult").html("<span class=\"error\">Enter valid Last Name (A-z) only allowed..</span>");
	   } else if(!($("#AccessInquiry #Email").val())) {	
		$("#AccessInquiry #Email").focus();
		$("#AccessInquiryResult").html("<span class=\"error\">Email required!</span>");		
	   } else if(!$("#AccessInquiry #Email").val().match(/^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/)) {
		$("#AccessInquiry #Email").focus();
		$("#AccessInquiryResult").html("<span class=\"error\">Enter valid Email!</span>");
	   } else if(!($("#AccessInquiry #aphone").val())) {
		$("#AccessInquiry #aphone").focus();
		$("#AccessInquiryResult").html("<span class=\"error\">Enter your Phone number!</span>");
	   } else if(!$("#AccessInquiry #aphone").val().match(/^([0-9]{9,})?$/)) {
		$("#AccessInquiry #aphone").focus();
		$("#AccessInquiryResult").html("<span class=\"error\">Enter valid Mobile Number!</span>");
	   } else if(!($("#AccessInquiry #acountry :selected").text())) {
		$("#AccessInquiry #acountry").focus();
		$("#AccessInquiry #AccessInquiryResult").html("<span class=\"error\">Enter Your Country</span>");	   	
		}else if(!$("#terms").is(':checked')){
		$("#AccessInquiry #terms").focus();
		$("#AccessInquiry #AccessInquiryResult").html("<span class=\"error\">Please check terms and conditions box</span>");
		}
		else
		{
            var formdata = 'country=' + acountry + '&fname=' + afname + '&lname=' + alname + '&email=' + email + '&phone=' + aphone + '&message=' + amessage;
            // AJAX Code To Submit Form.
            $.ajax({
                type: "POST",
                url: "process/picrequest.php",
                data: formdata,
                cache: false,
                success: function(response) {
                        if (response == "success") {
                            //formSuccess();
                            $(".modal").removeClass("fade").modal("hide");
                            $('#confirm-modal').modal('toggle');                            
                            $('#AccessInquiry')[0].reset(); // To reset form fields		
                        } 
						else
						{
							$("#AccessInquiryResult").html(response);
						}

                    } //end success
            }); //end ajax
        }
    } //end picrequest function

function formSuccess() {
        $("#msgSubmit").removeClass("hidden");
    }
    //submitloginForm
function submitloginForm() {
        //store form datas to variable
        var uname = $("#username").val();
        var pass = $("#password").val();
        var lemail = $("#LEmail").val();
        //check empty fields
        if (uname == '' || pass == '' || lemail == '') {
            $("#loginform #loginmsg").html("<span class=\"error\">Please fill all fields</span>");
        } else {
            $.ajax({
                type: "POST",
                url: "process/login.php",
				dataType: 'json',
                data: "uname=" + uname + "&lemail=" + lemail + "&pass=" + pass,
                success: function(data) {
                    if (data.response==1) {
                        //alert("successlogin");
                        $("#loginform")[0].reset(); // To reset form fields	
                        $("#loginModal").removeClass("fade").modal("hide");
                        var go_to_url = data.redirecturl;
                        //this will redirect us in same window
                        document.location.href = go_to_url;
                    }
					else if(data.response==0){
						$("#loginmsg").html("<span class=\"error\">" + data.error + "</span>");
					}
                }
            });
        }
    } //end submitloginForm function

//submitpicenterForm
function submitpicForm() {
        // Initiate Variables With Form Content
        var pic = $("#pic").val();
        var email = $("#pEmail").val();

        if (pic == '' || email == '') {
            $("#picmsg").html("<span class=\"error\">Please Enter all fields</span>");
        } else {
            $.ajax({
                type: "POST",
                url: "process/piclogin.php",
                data: "pic=" + pic + "&email=" + email,
                success: function(response) {
                   if (response == "success") {
                        $('#picentermodalform')[0].reset(); // To reset form fields	
                        $("#picentermodal").removeClass("fade").modal("hide");
                        var go_to_url = "pic_user.php";
                        //this will redirect us in same window
                        document.location.href = go_to_url;
                    }
					else 
					{
						$("#picmsg").html(response);
					}
                }
				
            });
        }
    } //end submitpicenterForm function

function submitregForm() {
		var pic = $("#pic").val();
        var rfname = $("#rfname").val();
        var rlname = $("#rlname").val();
        var runame = $("#runame").val();
        var rpass = $("#rpass").val();
        var rcpass = $('#rcpass').val();
        var remail = $("#remail").val();
        var billing_address1 = $("#billing_address1").val();
        var billing_address2 = $("#billing_address2").val();
        var billing_city = $("#billing_city").val();
        var billing_zip = $('#billing_zip').val();
        var rcountry = $("#rcountry :selected").text();
        var rphone = $('#rphone').val();
		var shipping_address1 = $("#shipping_address1").val();
        var shipping_address2 = $("#shipping_address2").val();
        var shipping_city = $("#shipping_city").val();
        var shipping_zip = $('#shipping_zip').val();
		
        //check empty fields
        if (pic == '' || rfname == '' || rlname == '' || runame == '' || rpass == '' || rcpass == '' || remail == '' || billing_address1 == '' || billing_address2 == '' || billing_city == '' || billing_zip == '' || rcountry == '' || rphone == '' || shipping_address1 == '' || shipping_address2 == '' || shipping_city == '' || shipping_zip == '') {
            alert("Plese fill all required fields");
        } else {

            var formdata = 'pic=' + pic + '&rfname=' + rfname + '&rlname=' + rlname + '&runame=' + runame + '&rpass=' + rpass + '&rcpass=' + rcpass + '&remail=' + remail + '&billing_address1=' + billing_address1 + '&billing_address2=' + billing_address2 + '&billing_city=' + billing_city + '&billing_zip=' + billing_zip + '&rcountry=' + rcountry + '&rphone=' + rphone + '&shipping_address1=' + shipping_address1 + '&shipping_address2=' + shipping_address2 + '&shipping_city=' + shipping_city + '&shipping_zip=' + shipping_zip ;
            // AJAX Code To Submit Form.
            //alert(formdata);
            $.ajax({
                type: "POST",
                url: "process/reg-process.php",
                data: formdata,
                cache: false,
                success: function(text) {
                        //$('#your-modal').modal('toggle');
                        if (text == "success") {
                            //alert("Thank you,successfully registered");
                            $('#regform')[0].reset(); // To reset form fields					
                            $("#regformmodal").removeClass("fade").modal("hide");
                            $('#reg-confirm-modal').modal('toggle');
                            //$("#loginModal").modal("show").addClass("fade");
                        }

                    } //end success
            }); //end ajax
        }
    } //end submitForm function
	
	
function forgotpassword(){
	var femail = $("#femail").val();
	if(femail == '') {	
		$("#femail").focus();		
		$("#femailresult").html("<span class=\"error\">Enter Your Email</span>");	
	}
	else
	{
		$.ajax({
                type: "POST",
                url: "process/forgot_password.php",
                data: 'action=forgotpassword&email='+femail,
                cache: false,
                success: function(response) {

					$('.error').remove();
					$('.success').remove();
					$('#femailresult').html(response);				
					 
				}
		});
		return false;
	}
}

	$( "#femail" ).keyup(function() {
	   var email = $('#femail').val();
	   if(email.length == 0)
	   {
		$('#femailresult').empty();
	   }
	});
	
	
function guestinquiry(){
		
       var name = $("#contactform #fullname").val();
       var email = $("#contactform #email").val();
       var mobile = $("#contactform #mobile").val();
       var country = $("#contactform #country").val();
	   var message = $("#contactform #message").val();
	
	   if (name == '' || email == '' || mobile == '' || country == '') {
           $("#conterr").html("<span class=\"error\">please fill all fields</span>");
       } else if(!($("#contactform #fullname").val())) {		
		$("#contactform #fullname").focus();
		$("#conterr").html("<span class=\"error\">First Name required!</span>");	
	   } else if(!$("#contactform #fullname").val().match(/^([A-z]{2,})?$/)){
		$("#contactform #fullname").focus();
		$("#conterr").html("<span class=\"error\">Enter valid Name (A-z) only allowed..</span>");
	   } else if(!($("#contactform #email").val())) {	
		$("#contactform #email").focus();
		$("#conterr").html("<span class=\"error\">Email required!</span>");		
	   } else if(!$("#contactform #email").val().match(/^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/)) {
		$("#contactform #email").focus();
		$("#conterr").html("<span class=\"error\">Enter valid Email!</span>");
	   } else if(!($("#contactform #mobile").val())) {
		$("#contactform #mobile").focus();
		$("#conterr").html("<span class=\"error\">Enter your Phone number!</span>");
	   } else if(!$("#contactform #mobile").val().match(/^([0-9]{6,})?$/)) {
		$("#contactform #mobile").focus();
		$("#conterr").html("<span class=\"error\">Enter valid Mobile Number!</span>");
	   } else if(!($("#contactform #country").val())) {
		$("#contactform #country").focus();
		$("#contactform #conterr").html("<span class=\"error\">Enter Your Country</span>");
	   }
	    else 
	   {
           $.ajax({
               type: "POST",
               url: "process/guestinquiry.php",
               data: 'action=guestinquiry&name='+name+ '&email=' +email+ '&mobile=' +mobile+ '&country=' +country+ '&message=' +message,
               cache: false,
               success: function(response) {
                   $('#contactform')[0].reset();    
                   $("#conterr").html(response);
               }

           }); //end success
       }		
}

function userinquiry(){
	
      var name = $("#contactform1 #fullname").val();
      var email = $("#contactform1 #email").val();
      var mobile = $("#contactform1 #mobile").val();
      var country = $("#contactform1 #country").val();
	  var message = $("#contactform1 #message").val();

	if (name == '' || email == '' || mobile == '' || country == '') {
       $("#conterr").html("<span class=\"error\">please fill all fields</span>");
       } else if(!($("#contactform1 #fullname").val())) {		
		$("#contactform #fullname").focus();
		$("#conterr").html("<span class=\"error\">First Name required!</span>");	
	   } else if(!$("#contactform1 #fullname").val().match(/^([A-z]{2,})?$/)){
		$("#contactform #fullname").focus();
		$("#conterr").html("<span class=\"error\">Enter valid Name (A-z) only allowed..</span>");
	   } else if(!($("#contactform1 #email").val())) {	
		$("#contactform #email").focus();
		$("#conterr").html("<span class=\"error\">Email required!</span>");		
	   } else if(!$("#contactform1 #email").val().match(/^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/)) {
		$("#contactform #email").focus();
		$("#conterr").html("<span class=\"error\">Enter valid Email!</span>");
	   } else if(!($("#contactform1 #mobile").val())) {
		$("#contactform #mobile").focus();
		$("#conterr").html("<span class=\"error\">Enter your Phone number!</span>");
	   } else if(!$("#contactform1 #mobile").val().match(/^([0-9]{6,})?$/)) {
		$("#contactform #mobile").focus();
		$("#conterr").html("<span class=\"error\">Enter valid Mobile Number!</span>");
	   } else if(!($("#contactform1 #country").val())) {
		$("#contactform #country").focus();
		$("#contactform1 #conterr").html("<span class=\"error\">Enter Your Country</span>");
	   }else {
          $.ajax({
              type: "POST",
              url: "process/guestinquiry.php",
              data: 'action=guestinquiry&name='+name+ '&email=' +email+ '&mobile=' +mobile+ '&country=' +country+ '&message=' +message,
              cache: false,
              success: function(response) {
                  $('#contactform1')[0].reset();    
                  $("#contactform1 #conterr").html(response);
              }

          }); //end success
      }		
}
	
	
	function changepasswordsubmit(){
		
		var curpass = $("#changepassword #oldpassword").val();
		var newpass = $("#changepassword #newpassword").val();
		var confirmpass = $("#changepassword #confirmpassword").val();
		var sess_name = $("#changepassword #sess_name").val();
		var sess_email = $("#changepassword #sess_email").val();
		
		if(curpass =='' || newpass == '' || confirmpass == ''){
			$("#changepassmsg").html("<span class=\"error\">Please fill all fields</span>");			
		}else if($("#newpass").val() != $("#confirmpass").val()) {
			$("#confirmpass").focus();
			$("#changepassmsg").html("<span class=\"error\">Password not match..</span>");
		}else{
			$.ajax({
                type: "POST",
                url: "process/changepassword.php",
                data: 'action=changepassword&curpass='+curpass+ '&newpass=' +newpass+ '&confirmpass=' +confirmpass+ '&sess_name='+sess_name+ '&sess_email='+sess_email,
                cache: false,
                success: function(response) {
                    $('#changepassword')[0].reset();    
                    $("#changepassword #changepassmsg").html(response);
                }

            });
		}
	}


