
$.fn.editable.defaults.mode = "popup";                   
        $('.inlineedit').editable();

// call pic requested users list //
function get_pic_req_list() {
    $.ajax({
        type: "POST",
        dataType: "html",
        url: "getadmin.php",
        data: 'action=get_pic_req_list',
        cache: false,
        success: function (response) {

            $("#data_fetch").html(response);
        }
    });
}

//call activate pic//
function pic_activate() {
    $("table").on('click', 'tr', 'td#activate', function () {
        var req_id = ($(this).find("#req_id").text());
        var name = ($(this).find("#name").text());
        var email = ($(this).find("#email").text());
        var phone = ($(this).find("#phone").text());
        var country = ($(this).find("#country").text());        
        if (req_id != '') {
            $.ajax({
                type: "POST",
                dataType: "html",
                url: "getadmin.php",
                data: 'action=pic_activate&req_id=' + req_id + '&name=' + name + '&email=' + email + '&phone=' + phone + '&country=' + country,
                cache: false,
                success: function (response) {
                    
                    get_pic_req_list();
                }
            });
        }
    });
}

//call get pic activated users//
function get_activated_pic() {
    $.ajax({
        type: "POST",
        dataType: "html",
        url: "getadmin.php",
        data: 'action=get_activated_pic',
        cache: false,
        success: function (response) {                
            
            $("#data_fetch").html(response);
        }
    });
}

//call get pic expired users//
function get_expired_pic() {
    $.ajax({
        type: "POST",
        dataType: "html",
        url: "getadmin.php",
        data: 'action=get_expired_pic',
        cache: false,
        success: function (response) {
            
            $("#data_fetch").html(response);
        }
    });
}

//call remove expired pic//
function remove_expired_pic() {
    $.ajax({
        type: "POST",
        dataType: "html",
        url: "getadmin.php",
        data: 'action=remove_expired_pic',
        cache: false,
        success: function (response) {

            $("#data_fetch").html(response);
        }
    });
}

//Generate_pic UI pattern//
function PIC_Generate_form() {

    var result = '<div class="page-title"> <div class="title_left"> <h3>Generate PIC</h3></div></div> <div class="clearfix"></div> <div class="row"> <div class="col-md-4"> <div class="x_panel p30"> <form id="generate_pic" class="form-horizontal" role="form"> <div class="form-group"> <label for="no_of_pic">Number of PIC:</label> <input type="number" class="form-control ht" id="count"> </div> <div class="form-group"> <label for="expiry_date">Number of Days Validity:</label> <input type="number" class="form-control ht" id="days"> </div> <span id="resultpic" class="text-center error"></span> <button  onclick="generate_pic()" type="submit" class="btn btn-success mt15 btn-100">Submit</button> </form> </div></div> <div class="col-md-6"> <div id="gen_pic" class="table-responsive"></div> </div></div>';

    $("#data_fetch").html(result);
 }

//call generate PIC//
function generate_pic() {

    var no_of_pic = $("#generate_pic #count").val();
    var valid_days = $("#generate_pic #days").val();

    if ((no_of_pic == '') || (no_of_pic == 0)) {
        $("#resultpic").html("Please Enter No of Pic You want");
    } else if ((valid_days == '') || (valid_days == 0)) {
        $("#resultpic").html("Please Enter No of days pic to be valid");
    } else {

        $.ajax({
            type: "POST",
            dataType: "html",
            url: "getadmin.php",
            data: 'action=pic_generate&no_of_pic=' + no_of_pic + '&valid_days=' + valid_days,
            cache: false,
            success: function (response) {

                $("#gen_pic").html(response);                    
            }
        });
    }
}

//call Projects//
function projects() {
    $.ajax({
        type: "POST",
        dataType: "html",
        url: "getadmin.php",
        data: 'action=projects',
        cache: false,
        success: function (htmldata) {
            $("#data_fetch").html(htmldata);
        }
    });
}

//call add project UI//
function add_edit_project_UI(id='') {
    var id = id;
    $.ajax({
        type: "POST",
        dataType: "html",
        url: "getadmin.php",
        data: 'action=add_edit_project_UI&id=' + id,
        cache: false,
        success: function (htmldata) {
            $("#data_fetch").html(htmldata);
        }
    });
}

$(document).ready(function() {

//wysihtml5 textarea height adjustment//
$('.add_edit_project textarea').wysihtml5({

    "events": {
        "load": function () {
            console.log("Loaded!");
            var $iframe = $(this.composer.iframe);
            var $body = $(this.composer.element);
            $body.css({
                'min-height': 0,
                'line-height': '20px',
                'overflow': 'hidden',
            });
            var scrollHeightInit = $body[0].scrollHeight;
            console.log("scrollHeightInit", scrollHeightInit);
            var bodyHeightInit = $body.height();
            console.log("bodyHeightInit", bodyHeightInit);
            var heightInit = Math.min(scrollHeightInit, bodyHeightInit); // 3860
            $iframe.height(heightInit);
            $body.bind('keypress keyup keydown paste change focus blur', function (e) {
                var scrollHeight = $body[0].scrollHeight;        // 150
                var bodyHeight = $body.height();                 // 60
                var height = Math.min(scrollHeight, bodyHeight); // 60
                $iframe.height(height);
            });
        }
    }
});

//datapicker//
$('input[type=date]').datepicker({
    format: 'yyyy/mm/dd'
});

//call add project//
$("#add_project").on('submit', (function (e) {
    e.preventDefault();

    var formData = new FormData(this);
    formData.append('action', 'add_project');

    $.ajax({
        type: "POST",
        url: "getadmin.php",
        data: formData,
        contentType: false,
        cache: false,
        processData: false,
        success: function (response) {
            $("#add_project_msg").html(response);
            //alert(response);
        }
    });
}));

//call edit project//
$("#edit_project").on('submit', (function (e) {
    e.preventDefault();

    var formData = new FormData(this);
    formData.append('action', 'edit_project');

    $.ajax({
        type: "POST",
        url: "getadmin.php",
        data: formData,
        contentType: false,
        cache: false,
        processData: false,
        success: function (response) {
            $("#edit_project_msg").html(response);
            //alert(response);
        }
    });
}));
});

function disable_project(id) {
    var id = id;
    $.ajax({
        type: "POST",
        dataType: "html",
        url: "getadmin.php",
        data: 'action=disable_project&id=' + id,
        cache: false,
        success: function (htmldata) {

            projects();
        }
    });
}

function enable_project(id) {
    var id = id;
    $.ajax({
        type: "POST",
        dataType: "html",
        url: "getadmin.php",
        data: 'action=enable_project&id=' + id,
        cache: false,
        success: function (htmldata) {

            projects();
        }
    });
}

//enable disable fund requirement//
$('#projects input[type="checkbox"]').click(function(e) {
    if($('#projects').children().find('input[type="checkbox"]:checked').length <= 3) {
        
		var $check = $(this);
		var name = $(this).data("name");
		var pk = $(this).data("pk");	
		
		if($check.prop('checked')) {  
			var value = 1;
		} else {
			var value = 0;
		}
		
		$.ajax({
		type: "POST",
		url: "getadmin.php",
		data: 'action=funding_required&name='+name+'&pk='+pk+'&value='+value,
		cache: false,
		success: function(text) {                        
			projects();
			} //end success
		}); //end ajax
		
    }else{		
		e.preventDefault();	
	}
});


$('.tractionupdate').change(function() {

    var $check = $(this);
    var name = $(this).data("name");
    var pk = $(this).data("pk");

    if ($check.prop('checked')) {  
    	var value = 1;
    } else {
		var value = 0;
    }
	$.ajax({
                type: "POST",
                url: "pjtlstupdate.php",
                data: 'name='+name+'&pk='+pk+'&value='+value,
                cache: false,
                success: function(text) {                        
                    } //end success
            }); //end ajax
	
});



//add contry UI//
function add_country_UI() {

    $.ajax({
        type: "POST",
        dataType: "html",
        url: "getadmin.php",
        data: 'action=add_country_UI',
        cache: false,
        success: function (htmldata) {

            $("#data_fetch").html(htmldata);
        }
    });
}

//call remove country//
function remove_country(id) {
    var id = id;
    $.ajax({
        type: "POST",
        dataType: "html",
        url: "getadmin.php",
        data: 'action=remove_country&id='+ id,
        cache: false,
        success: function (htmldata) {

            add_country_UI();
        }
    });
}

$(document).ready(function() {
//call add country//
    $("#add_country").on("submit", (function (e) {
    e.preventDefault();
    var country = $("#country_name").val();
    $.ajax({
        type: "POST",
        url: "getadmin.php",
        data: 'action=add_country&country=' + country,
        success: function (response) {                    
            add_country_UI();
        }
    });

}));
    
});

//call team members UI 
function team_members_UI() {

    $.ajax({
        type: "POST",
        dataType: "html",
        url: "getadmin.php",
        data: 'action=team_members_UI',
        cache: false,
        success: function (htmldata) {

            $("#data_fetch").html(htmldata);
        }
    });
}

function add_edit_member_UI(id='') {
    var id = id;
    $.ajax({
        type: "POST",
        dataType: "html",
        url: "getadmin.php",
        data: 'action=add_edit_member_UI&id=' + id,
        cache: false,
        success: function (htmldata) {

            $("#data_fetch").html(htmldata);
        }
    });
}

$(document).ready(function() {
    
$('select').change(function() {
 if ($(this).children('option:first-child').is(':selected')) {
   $(this).addClass('placeholder');
 } else {
  $(this).removeClass('placeholder');
 }
});

//add member//
$("#add_member").on('submit',(function(e) {
    e.preventDefault();

    var formData = new FormData(this);
    formData.append('action', 'add_member');

    $.ajax({
        type: "POST",
        url: "getadmin.php",          
        data: formData,
        contentType: false,
        cache: false,
        processData:false,            
        success: function(response) {                    
            $("#add_member_msg").html(response);                    
            //alert(response);
        }
    });     
}));

//edit member//
$("#edit_member").on('submit',(function(e) {
    e.preventDefault();

    var formData = new FormData(this);
    formData.append('action', 'edit_member');

    $.ajax({
        type: "POST",
        url: "getadmin.php",          
        data:  formData,
        contentType: false,
        cache: false,
        processData:false,            
        success: function(response) {                    
            $("#edit_member_msg").html(response);                    
            //alert(response);
        }
    });         
}));

});

//remove team member//
function remove_member(id) {
    var id = id;
    $.ajax({
        type: "POST",
        dataType: "html",
        url: "getadmin.php",
        data: 'action=remove_member&id=' + id,
        cache: false,
        success: function (htmldata) {

            team_members_UI();
        }
    });
}

//partners list//
function partners_UI() {

    $.ajax({
        type: "POST",
        dataType: "html",
        url: "getadmin.php",
        data: 'action=partners_UI',
        cache: false,
        success: function (htmldata) {

            $("#data_fetch").html(htmldata);
        }
    });
}

//add partners UI//
function add_edit_partner_UI(id='') {
    var id = id;
    $.ajax({
        type: "POST",
        dataType: "html",
        url: "getadmin.php",
        data: 'action=add_edit_partner_UI&id=' + id,
        cache: false,
        success: function (htmldata) {

            $("#data_fetch").html(htmldata);
        }
    });
}

$(document).ready(function() {

//add partner            
$("#add_partner").on('submit',(function(e) {
    e.preventDefault();
    
    var formData = new FormData(this);
    formData.append('action', 'add_partner');
    
    $.ajax({
        type: "POST",
        url: "getadmin.php",         
        data:  formData,
        contentType: false,
        cache: false,
        processData:false,            
        success: function(response) {                    
            $("#add_partner_msg").html(response);                   
            //alert(response);
        }
    }); 
}));

//edit partner//
$("#edit_partner").on('submit',(function(e) {
    e.preventDefault();

    var formData = new FormData(this);
    formData.append('action', 'edit_partner');
    
    $.ajax({
        type: "POST",
        url: "getadmin.php",         
        data:  formData,
        contentType: false,
        cache: false,
        processData:false,            
        success: function(response) {                    
            $("#edit_partner_msg").html(response);                   
            //alert(response);
        }
    }); 
        
}));

}); 

function remove_partner(id) {
    var id = id;
    $.ajax({
        type: "POST",
        dataType: "html",
        url: "getadmin.php",
        data: 'action=remove_partner&id=' + id,
        cache: false,
        success: function (htmldata) {

            partners_UI();
        }
    });
}

//competitors list//
function competitors_UI() {

    $.ajax({
        type: "POST",
        dataType: "html",
        url: "getadmin.php",
        data: 'action=competitors_UI',
        cache: false,
        success: function (htmldata) {

            $("#data_fetch").html(htmldata);
        }
    });
}

function add_edit_competitors_UI(id='') {
    var id = id;
    $.ajax({
        type: "POST",
        dataType: "html",
        url: "getadmin.php",
        data: 'action=add_edit_competitors_UI&id=' + id,
        cache: false,
        success: function (htmldata) {

            $("#data_fetch").html(htmldata);
        }
    });
}

$(document).ready(function() {
    $("#add_competitor").on('submit', (function (e) {
        e.preventDefault();

        var formData = new FormData(this);
        formData.append('action', 'add_competitor');

        $.ajax({
            type: "POST",
            url: "getadmin.php",
            data: formData,
            contentType: false,
            cache: false,
            processData: false,
            success: function (response) {
                $("#add_competitor_msg").html(response);
                //alert(response);
            }
        });

    }));

    $("#edit_competitor").on('submit', (function (e) {
        e.preventDefault();

        var formData = new FormData(this);
        formData.append('action', 'edit_competitor');

        $.ajax({
            type: "POST",
            url: "getadmin.php",
            data: formData,
            contentType: false,
            cache: false,
            processData: false,
            success: function (response) {
                $("#edit_competitor_msg").html(response);
                //alert(response);
            }
        });

    }));
});

function remove_competitors(id) {
    var id = id;
    $.ajax({
        type: "POST",
        dataType: "html",
        url: "getadmin.php",
        data: 'action=remove_competitors&id=' + id,
        cache: false,
        success: function (htmldata) {

            competitors_UI();
        }
    });
}

function traction_testing_UI() {

    $.ajax({
        type: "POST",
        dataType: "html",
        url: "getadmin.php",
        data: 'action=traction_testing_UI',
        cache: false,
        success: function (htmldata) {

            $("#data_fetch").html(htmldata);
        }
    });
}

function add_edit_traction_UI(id='') {
    var id = id;
    $.ajax({
        type: "POST",
        dataType: "html",
        url: "getadmin.php",
        data: 'action=add_edit_traction_UI&id=' + id,
        cache: false,
        success: function (htmldata) {

            $("#data_fetch").html(htmldata);
        }
    });
}

function add_edit_testing_UI(id='') {
    var id = id;
    $.ajax({
        type: "POST",
        dataType: "html",
        url: "getadmin.php",
        data: 'action=add_edit_testing_UI&id=' + id,
        cache: false,
        success: function (htmldata) {

            $("#data_fetch").html(htmldata);
        }
    });
}

$(document).ready(function() {
    
$("#add_traction").on('submit',(function(e) {
    e.preventDefault();

    var formData = new FormData(this);
    formData.append('action', 'add_traction');

    $.ajax({
        type: "POST",
        url: "getadmin.php",            
        data: formData,
        contentType: false,
        cache: false,
        processData:false,            
        success: function(response) {                    
            $("#add_traction_msg").html(response);                  
            //alert(response);
        }
}); 
}));


$("#edit_traction").on('submit',(function(e) {
    e.preventDefault();
    
    var formData = new FormData(this);
    formData.append('action', 'edit_traction');
    
    $.ajax({
        type: "POST",
        url: "getadmin.php",            
        data: formData,
        contentType: false,
        cache: false,
        processData:false,            
        success: function(response) {                    
            $("#edit_traction_msg").html(response);                  
            //alert(response);
        }
    }); 
}));

$("#add_testing").on('submit',(function(e) {
    e.preventDefault();

    var formData = new FormData(this);
    formData.append('action', 'add_testing');

    $.ajax({
        type: "POST",
        url: "getadmin.php",            
        data:  formData,
        contentType: false,
        cache: false,
        processData:false,            
        success: function(response) {                    
            $("#add_testing_msg").html(response);                  
            //alert(response);
        }
    });    
}));

$("#edit_testing").on('submit',(function(e) {
    e.preventDefault();

    var formData = new FormData(this);
    formData.append('action', 'edit_testing');

    $.ajax({
        type: "POST",
        url: "getadmin.php",            
        data:  formData,
        contentType: false,
        cache: false,
        processData:false,            
        success: function(response) {                    
            $("#edit_testing_msg").html(response);                  
            //alert(response);
        }
    });  
}));

});
 
//enable_testing
function enable_testing(id) {
    var id = id;
    $.ajax({
        type: "POST",
        dataType: "html",
        url: "getadmin.php",
        data: 'action=enable_testing&id=' + id,
        cache: false,
        success: function (htmldata) {

            Traction_UI();
        }
    });
}

//disable_testing
function disable_testing(id) {
    var id = id;
    $.ajax({
        type: "POST",
        dataType: "html",
        url: "getadmin.php",
        data: 'action=disable_testing&id=' + id,
        cache: false,
        success: function (htmldata) {

            Traction_UI();
        }
    });
}

//enable_traction
function enable_traction(id) {
    var id = id;
    $.ajax({
        type: "POST",
        dataType: "html",
        url: "getadmin.php",
        data: 'action=enable_traction&id=' + id,
        cache: false,
        success: function (htmldata) {

            Traction_UI();
        }
    });
}

//disable_traction
function disable_traction(id) {
    var id = id;
    $.ajax({
        type: "POST",
        dataType: "html",
        url: "getadmin.php",
        data: 'action=disable_traction&id=' + id,
        cache: false,
        success: function (htmldata) {

            Traction_UI();
        }
    });
}

//transaction details
function transection_details() {
    
    $.ajax({
        type: "POST",
        dataType: "html",
        url: "getadmin.php",
        data: 'action=transection_details',
        cache: false,
        success: function (htmldata) {

			$("#data_fetch").html(htmldata);
        }
    });
}


