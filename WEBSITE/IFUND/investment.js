
$(function() {
    $('#single_portfolio input[type="checkbox"]').click(function(){
        // Caching all the checkboxes into a variable
        var checkboxes =  $('#single_portfolio input[type="checkbox"]');
        // If one item is checked.. Uncheck all and
        // check current item..
        if($(this).is(':checked')){
            checkboxes.attr('checked', false);
            $(this).prop('checked', 'checked');
        }
    });    
});


$('#double_portfolio input[type="checkbox"]').click(function(e) {
    if ($('#double_portfolio').children().find('input[type="checkbox"]:checked').length > 2) {
        e.preventDefault();
    }
});

function single_portfolio_validate(){
	
	var cheked_len = $("#single_portfolio [name='sp[]']:checked").length;
	if(cheked_len < 1){
		$("#single_portfolio_msg").text("Please Select Project");
		return false;
	}	
	
	var amt = $("#single_portfolio [name=pay_amount]").val();
	if(amt == ''){
		$("#single_portfolio_msg").text("Please Select Amount");		
		return false;
	}
	return true;	
}

function double_portfolio_validate(){
	
	var cheked_len = $("#double_portfolio [name='sp[]']:checked").length;
	if(cheked_len < 1){
		$("#double_portfolio_msg").text("Please Select Project");
		return false;
	}	
	if(cheked_len < 2){
		$("#double_portfolio_msg").text("Atleast Select Max 2 projects");
		return false;
	}	
	var amt = $("#double_portfolio [name=pay_amount]").val();
	if(amt == ''){
		$("#double_portfolio_msg").text("Please Enter Amount");		
		return false;
	}
	
	var dou_min_amt = $('#double_min').val();
	var dou_max_amt = $('#double_max').val();
	if(amt < dou_min_amt || amt > dou_max_amt){
		$("#double_portfolio_msg").text("Amount in between " + dou_min_amt + " to " + dou_max_amt);		
		return false;
	}	
	return true;
}

function full_portfolio_validate(){
	
	var cheked_len = $("#full_portfolio [name='sp[]']:checked").length;
	if(cheked_len < 1){
		$("#full_portfolio_msg").text("Please Select Project");
		return false;
	}	
	if(cheked_len < 2){
		$("#full_portfolio_msg").text("Atleast Select Max 3 projects");
		return false;
	}
	
	var amt = $("#full_portfolio [name=pay_amount]").val();
	if(amt == ''){
		$("#full_portfolio_msg").text("Please Enter Amount");		
		return false;
	}
	
	var full_min_amt = $('#full_min').val();
	var full_max_amt = $('#full_max').val()
	if(amt < full_min_amt || amt > full_max_amt){
		$("#full_portfolio_msg").text("Amount in between " + full_min_amt + " to " + full_max_amt);		
		return false;
	}
	return true;
}

$(document).ready(function() {
    $(".tabs-menu a").click(function(event) {
        event.preventDefault();
      
        var tab = $(this).attr("href");
        $(".tab-content").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
});
