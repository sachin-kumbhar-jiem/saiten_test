jQuery.fn.exists = function(){return this.length>0;}
String.prototype.trim = function(){ return this.replace(/^\s+|\s+$/g, '') }  

//Our validation script will go here.
$(document).ready(function(){
	
	$("body").addClass("body-overflow-auto-login");
	
	$("#scorerId").focus();
	
	//custom validation for Alphanumeric Symbols
	$.validator.addMethod("alphanumericSymbols", alphanumericSymbolsValidation, "");

    //validation implementation will go here.
	$("#loginForm").validate({
	    
		rules: {	
		
			 "scorerInfo.scorerId" : {
      				required: true,      			
      				maxlength: 7,  				
      				alphanumericSymbols: true      				
    			},
		   	 "scorerInfo.password" : {
      				required: true,      			
      				minlength: 4,
      				maxlength: 256,      				
      				alphanumericSymbols: true      				
    			}			
		       
	    },
	    messages: {
			
			"scorerInfo.scorerId" : {
	            required: LOGIN_REQUIRED,
				maxlength: LOGIN_ID_LENGTH_7,
     			alphanumericSymbols: LOGIN_ID_ALPHA_NUMERIC_AND_SYMBOLS
				
	        },
	        "scorerInfo.password" : {
	            required: PASSWORD_REQUIRED,
				minlength: PASSWORD_LENGTH_4_256,
				maxlength: PASSWORD_LENGTH_4_256,
   				alphanumericSymbols: PASSWORD_ALPHA_NUMERIC_AND_SYMBOLS
	        }
	    }	    
	
	});
	
	$('#scorerId').bind('input', function () {
	     $(this).val($(this).val().replace(/[^0-9A-Za-z_\-\.\'!#$%*+-/=?@^`~{(}|]/g, ''));
	});
	
	$('#password').bind('input', function () {
	     $(this).val($(this).val().replace(/[^0-9A-Za-z_\-\.\'!#$%*+-/=?@^`~{(}|]/g, ''));
	});
	
});