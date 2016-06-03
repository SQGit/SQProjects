$(document).ready(function() {
    $('#AccessInquiry #Email').keyup(function(){
        var email = $(this).val(); 
        var emailResult = $('#emailResult'); 
        if(email.length < 1) {
			emailResult.html('');
        }
		if(email.length > 2) { 
            emailResult.html('Loading...');
		}
	});
	
    $('#AccessInquiry #Email').blur(function(){      
      $.post("process/checkEmailAvail.php", {
        email: $('#AccessInquiry #Email').val()
      }, function(response){
        $('#AccessInquiry #emailResult').fadeOut();
        setTimeout("finishAjax('emailResult', '"+escape(response)+"')", 400);
      });
        return false;
    });
	
	
});
function finishAjax(id, response) {  
  $('#'+id).html(unescape(response));
  $('#'+id).fadeIn();
} //finish Ajax email available check

function showpicenquiryform() {
    $("#loginModal").removeClass("fade").modal("hide");	
	$('#AccessInquiryModal #AccessInquiry')[0].reset();
	$("#AccessInquiryResult").empty();
	$("#emailResult").empty();
    $("#AccessInquiryModal").modal("show").addClass("fade");
}

function showpicenterform() {
    $("#loginModal").removeClass("fade").modal("hide");
	$('#picentermodal #picentermodalform')[0].reset();
	$("#picmsg").empty();
    $("#picentermodal").modal("show").addClass("fade");
}

function showloginform() {
    $("#pwdModal").removeClass("fade").modal("hide");
	$("#loginform")[0].reset();
	$("#loginmsg").empty();
    $("#loginModal").modal("show").addClass("fade");
}

function showforgotpassword() {
    $("#loginModal").removeClass("fade").modal("hide");
    $("#pwdModal").modal("show").addClass("fade");
}


//pic user//

function showforgotpassword() {
    $("#loginModal").removeClass("fade").modal("hide");
    $("#pwdModal").modal("show").addClass("fade");
}	

function showloginform() {
    $("#pwdModal").removeClass("fade").modal("hide");
    $("#loginModal").modal("show").addClass("fade");
}

function showregform() {
    $("#loginModal").removeClass("fade").modal("hide");
    $("#regformmodal").modal("show").addClass("fade");
}
	
function validate() {
$(".signup-error").html('');
if($("#user-field").css('display') != 'none') {
	
	if(!($("#rfname").val())) {		
		$("#rfname").focus();
		$("#fname-error").html("First Name required!");	
		return false;
	}else if(!$("#rfname").val().match(/^([A-z]{2,})?$/)){
		$("#rfname").focus();
		$("#fname-error").html("A to Z only allowed");
		return false;
	}else if(!($("#rlname").val())) {		
		$("#rlname").focus();
		$("#lname-error").html("Last Name required!");	
		return false;
	}else if(!$("#rlname").val().match(/^([A-z]{1,})?$/)){
		$("#rlname").focus();
		$("#lname-error").html("A to Z only allowed");
		return false;
	}else if(!($("#runame").val())){
		$("#runame").focus();
		$("#uname-error").html("User Name required");
		return false;
	}else if(($("#runame").val().length)< 5){
		$("#runame").focus();
		$("#uname-error").html("atleast five characters need");
		return false;
	}else if(!($("#remail").val())) {	
		$("#remail").focus();
		$("#email-error").html("Email required!");
		return false;
	}else if(!$("#remail").val().match(/^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/)) {
		$("#remail").focus();
		$("#email-error").html("Invalid Email!");
		return false;
	}else if(!($("#rpass").val())) {	
		$("#rpass").focus();
		$("#password-error").removeClass().addClass('signup-error').html("Password required!");
		return false;		
	}else if(!($("#rcpass").val())) {	
		$("#rcpass").focus();
		$("#confirm-password-error").html("Confirm password required!");
		return false;
	}else if($("#rpass").val() != $("#rcpass").val()) {	
		$("#rcpass").focus();
		$("#confirm-password-error").html("Password not matched!");
		return false;
	}else {
		return true;
	}
}

if($("#billingaddress-field").css('display') != 'none') {
	if(!($("#billing_address1").val())) {
		$("#billing_address1").focus();
		$("#billing_address1_error").html("billing address required!");
		return false;
	}else if(!($("#billing_address2").val())) {
		$("#billing_address2").focus();
		$("#billing_address2_error").html("billing address required!");
		return false;
	}else if(!($("#billing_city").val())) {
		$("#billing_city").focus();
		$("#billing_city-error").html("city required!");
		return false;
	}else if(!($("#billing_zip").val())) {
		$("#billing_zip").focus();
		$("#billing_zip-error").html("zip required!");
		return false;
	}else if(!($("#rcountry").val())) {
		$("#rcountry").focus();
		$("#country-error").html("Select your country");
		return false;
	}else if(!($("#rphone").val())) {
		$("#rphone").focus();
		$("#phone-error").html("Enter your Phone number!");
		return false;
	}else{
		return true;
	}
}

if($("#shippingaddress-field").css('display') != 'none') {
	if(!($("#shipping_address1").val())) {
		$("#shipping_address1").focus();
		$("#shipping_address1_error").html("billing address required!");
		return false;
	}else if(!($("#shipping_address2").val())) {
		$("#shipping_address2").focus();
		$("#shipping_address2_error").html("billing address required!");
		return false;
	}else if(!($("#shipping_city").val())) {
		$("#shipping_city").focus();
		$("#shipping_city_error").html("city required!");
		return false;
	}else if(!($("#shipping_zip").val())) {
		$("#shipping_zip").focus();
		$("#shipping_zip_error").html("zip required!");
		return false;
	}else{
		return true;
	}
}


}

function sameAsAbove(){
	if($('#sameas').is(":checked")) {
        $("#shipping_address1").val($("#billing_address1").val()); 
		$("#shipping_address2").val($("#billing_address2").val()); 
		$("#shipping_city").val($("#billing_city").val()); 
        $("#shipping_zip").val($("#billing_zip").val()); 
    }else{
        $("#shipping_address1").val('');
        $("#shipping_address2").val('');
        $("#shipping_city").val('');
		$("#shipping_zip").val('');
    }

}

$(document).ready(function() {

	var password1 		= $('#rpass'); //id of first password field
	var password2		= $('#rcpass'); //id of second password field
	var passwordsInfo 	= $('#password-error'); //id of indicator element
	var confirmpasswordsInfo 	= $('#confirm-password-error'); //id of indicator element
	
	passwordStrengthCheck(password1,password2,passwordsInfo,confirmpasswordsInfo); //call password check function
	
$('#regform #runame').keyup(function(){

var username = $("#runame").val();
var result = $("#uname-error");

if(username.length > 3)
{
result.html('Checking availability...');
var dataPass = 'action=availability&username='+username;

$.ajax({  
    type: "POST",  
    url: "process/checkuseravail.php",  
    data: dataPass,  
    success: function(response){  
			
    if(response == 0)
    {     
        result.html('<span class="success">User Name Available</span>');	
    }  
    else if(response > 0)
    {  
		result.html('<span class="error">Not Available</span>');
    }
	else
	{
		alert('Problem with sql query');
	}
	
	}
   });
   
  }else {
	result.html('Enter atleast 5 characters');
   } 
   if(username.length == 0) {
            result.html('');
   }
  }); 

	
		


function passwordStrengthCheck(password1, password2, passwordsInfo, confirmpasswordsInfo)
{
	//Must contain 5 characters or more
	var WeakPass = /(?=.{5,}).*/; 
	//Must contain lower case letters and at least one digit.
	var MediumPass = /^(?=\S*?[a-z])(?=\S*?[0-9])\S{5,}$/; 
	//Must contain at least one upper case letter, one lower case letter and one digit.
	var StrongPass = /^(?=\S*?[A-Z])(?=\S*?[a-z])(?=\S*?[0-9])\S{5,}$/; 
	//Must contain at least one upper case letter, one lower case letter and one digit.
	var VryStrongPass = /^(?=\S*?[A-Z])(?=\S*?[a-z])(?=\S*?[0-9])(?=\S*?[^\w\*])\S{5,}$/; 
	
	$(password1).on('keyup', function(e) {
		if(VryStrongPass.test(password1.val()))
		{
			passwordsInfo.removeClass().addClass('goodpass').html("Very Strong! (Awesome, please don't forget your pass now!)");
		}	
		else if(StrongPass.test(password1.val()))
		{
			passwordsInfo.removeClass().addClass('strongpass').html("Strong! (Enter special chars to make even stronger");
		}	
		else if(MediumPass.test(password1.val()))
		{
			passwordsInfo.removeClass().addClass('goodpass').html("Good! (Enter uppercase letter to make strong)");
		}
		else if(WeakPass.test(password1.val()))
    	{
			passwordsInfo.removeClass().addClass('stillweakpass').html("Still Weak! (Enter digits to make good password)");
    	}
		else if(password1.val()=='')
		{
			passwordsInfo.html("");
		}else
		{
			passwordsInfo.removeClass().addClass('weakpass').html("Very Weak! (Must be 5 or more chars)");
		}
	});   
	
	$(password2).on('keyup', function(e) {
		
		if(password2.val()=='')
		{
			confirmpasswordsInfo.html("");
		}
		else if(password1.val() !== password2.val())
		{
			confirmpasswordsInfo.removeClass().addClass('weakpass').html("Passwords do not match!");	
		}else{
			confirmpasswordsInfo.removeClass().addClass('goodpass').html("Passwords match!");	
		}
			
	});
}



	$("#next").click(function(){
		var output = validate();			
		if(output) {
			var current = $("#signup-step .active");			
			var next = $("#signup-step .active").next("#signup-step li");			
			if(next.length>0) {
				$("#"+current.attr("id")+"-field").hide();
				$("#"+next.attr("id")+"-field").show();
				$("#back").show();
				$("#finish").hide();
				$("#signup-step .active").removeClass("active");
				next.addClass("active");
				if($("#signup-step .active").attr("id") == $("#signup-step li").last().attr("id")) {
					$("#next").hide();
					$("#finish").show();				
				}
			}
		}
	});
	$("#back").click(function(){ 
		var current = $("#signup-step .active");
		var prev = $("#signup-step .active").prev("#signup-step li");
		if(prev.length>0) {
			$("#"+current.attr("id")+"-field").hide();
			$("#"+prev.attr("id")+"-field").show();
			$("#next").show();
			$("#finish").hide();
			$("#signup-step .active").removeClass("active");
			prev.addClass("active");
			if($("#signup-step .active").attr("id") == $("#signup-step li").first().attr("id")) {
				$("#back").hide();			
			}
		}
	});
	
	$("#regform").submit(function(event) {
        // cancels the form submission
        event.preventDefault();
		var output = validate();			
		if(output) {
			submitregForm(); //call function
		
		}
    });	
});
