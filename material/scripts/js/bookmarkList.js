jQuery.fn.exists = function(){return this.length>0;}
String.prototype.trim = function(){ return this.replace(/^\s+|\s+$/g, '') }  

//Our validation script will go here.
$(document).ready(function(){
	
		$("body").addClass("body-overflow-auto-history-bookmark");
		$("#resetHistory").focus();
    //validation implementation will go here.
	$("#deleteBookmarks").validate({
	    
		rules: {	
		
			 "selectCheckBoxToDelete" : {
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
			"selectCheckBoxToDelete" : {
	            required: SELECT_RECORD_TO_DELETE
	        }
	    }	    
	});
});