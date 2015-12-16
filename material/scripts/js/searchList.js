jQuery.fn.exists = function(){return this.length>0;}
String.prototype.trim = function(){ return this.replace(/^\s+|\s+$/g, '') }

$(document).ready(function(){
	
	
//validation implementation will go here.
$("#update").validate({
    
	rules: {	
	
		 "selectCheckBoxToUpdate" : {
  				required: function (element) {
  					var allVals = [];
  					$('#c_b :checked').each(function() {
  				       allVals.push($(this).val());
  				     });
  					if(allVals.length<=0){
  						return true;
  					}else{
  						return false;
  					}
  				}      			
			}			
    },
    messages: {
		"selectCheckBoxToUpdate" : {
            required: SELECT_ATLEAST_ONE_RECORD
        }
    }	    
});
});

//Our validation script will go here.
$(function(){
	 
	 // add multiple select / deselect functionality
    $("#pageselectall").change(function () {
        $('.case').attr('checked', this.checked);
        if(!($("#pageselectall").is(":checked"))){
			$("#selectall").removeAttr("checked");
		}
        setCheckboxState();
    });
 
    // if all checkbox are selected, check the selectall checkbox
    // and viceversa
    $(".case").click(function(){
    	if($(".case").length == $(".case:checked").length) {
            $("#pageselectall").attr("checked", "checked");
        } else {
            $("#pageselectall").removeAttr("checked");
            $("#selectall").removeAttr("checked");
        }
        setSelectFlag();
        setCheckboxState();
 
    });
    $("#selectall").click(function (){
		$('.case').attr('checked', this.checked);
		$("#pageselectall").attr("checked", this.checked);
		setCheckboxState();
	});
   
});

function setCheckboxState(){
	var requestData = {};   
	requestData["selectAllFlag"] =  $("#selectall").is(":checked") ? true : false;
	var isSuccess;
	
	$.ajax({
		type: "POST",
		url: './selectAll.action',
		async: false,
		dataType:"json",
		cache: false,
		data: $.param(requestData),
		error: function(request, status, error){
			alert(INTERNAL_SERVER_ERROR);
			isSuccess = false;
		}
	});
	
	return isSuccess;
}

function setSelectFlag(){
	var selectFlag = $('#selectall').is(':checked');
	
	if(selectFlag == true){
		$('#selectAllFlag').val('true');
	}else {
		$('#selectAllFlag').val('false');
	}
		
}