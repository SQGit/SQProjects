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

	$("#searchquery").keyup(function(){
		var len = $("#searchquery").val().length;
		var clear = $(".clear");
		var visible = $(".clear").is(':visible');
		
		//alert(visible);
		if(len && !visible) {
			clear.show();
		}
		
		if(!len && visible) {
            clear.hide();
			getfaq();
        }
	});
	
	$(".clear").click(function(){
		var clear = $(".clear");
		$("#searchquery").val('');
		clear.hide();
		getfaq();
	});
	
});

function getfaq(){

    $.ajax({
      type: "POST",
      dataType: "html",
      url: "process/faqsearch.php",
	  data: 'action=randomfaq',
      cache: false,
      success: function(response) {
		$("#accordion5").html(response).show();
			}
		});
	}