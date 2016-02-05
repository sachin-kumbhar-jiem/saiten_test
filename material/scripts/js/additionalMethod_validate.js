
	function emailValidation (value, element) {	
		
		var reg = /^([A-Za-z0-9_\-\.\'!#$%*+-/=?^`~{(}|])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

			if(reg.test(value) == false) 
	 
					return false;
			else
					return true;
				
	}
	
	
 	function alphanumericSymbolsValidation (value, element) {
		 	
		var reg=/^[0-9A-Za-z_\-\.\'!#$%*+-/=?@^`~{(}|]+$/;

		if(reg.test(value) == false) 
 
      			return false;

		else
				return true;		
	 }
	 
	 
	 function alphanumericSymbolswithSpaceValidation (value, element) {
		 	
		var reg=/^[0-9A-Za-z_\-\.\'!#$%*+-/=?@^`~{(}| ]+$/;

		if(reg.test(value) == false) 
 
      			return false;

		else
				return true;		
	 }
	
	
	 function doubleByteValidation(value, element) {
		 
		 
	 
 
	
		for (var i = 0, n = value.length; i < n; i++)
		 {
		 	 if (value.charCodeAt(i) < 255) 				 
				{
   					return false;

		  		}
    	}
		return true;
		
		
	
	
	
	
	
	}
	
	
	 function doubleByte_AlphanumericValidation(value, element) {
		 
		 
	
		
		for (var i = 0, n = value.length; i < n; i++)
		 {
		
		 	 if (value.charCodeAt(i) < 255)	
			 	 				 
				{
					if( 	!( (value.charCodeAt(i)>=65 && value.charCodeAt(i)<=90)
						  	|| (value.charCodeAt(i)>=97 && value.charCodeAt(i)<=122)
					   	  	|| (value.charCodeAt(i)>=48 && value.charCodeAt(i)<=57)
						  )					   		
					   )
					   	{	
							
   							return false;
						}	
						
		  		}
    	}
		return true;
		
		
	
	
	
	
	
	}
	
	
 	function alphanumericValidation(value, element) {
		
		
	
		var reg=/^[0-9A-Za-z]+$/;

		if(reg.test(value) == false) 
 
      			return false;

		else
				return true;
		
		
	
	}
	
	
	
	function KatakanaValidation(value, element) {
		
		
	
	var re5digit = /^[\u30A1-\u30FA]+$/;

		if (value.search(re5digit)==-1) //if match failed
		{
			return false;
		}
		else
		{
			return true;
		}

	
	
	
	}
	
	function dropDown(value, element){
   if(value =="-1")
     return false;
    else
    return true;
  }
	
function numericOnlyValidation(value, element){

	var reg=/^[0-9]+$/;

	if(reg.test(value) == false) 

  			return false;

	else
			return true;
	
}

function numericCommaValidation(value, element){

	//regex for value begins with integer, may followed by comma and ended with integer.
	var reg= /^\d{1,}([,]\d{1,})*$/;
	
	//if: if any value enter into textfield.
	//else: return true because value is not mandatory. 
	if(value!=""){
		if(reg.test(value) == false){
	  		return false;
		} 
		else
			return true;
	}else {
		return true;
	}
}

function isQuestionNumValid(value, element){
	var isSuccess;
	var requestData = {};   
	if ($('#acceptanceDisplayRadio').is(':checked')) {
		requestData["questionNum"] = $("#questionNumA").val();
		requestData["subjectCode"] = $("input:radio[id='subjectCodeA']:checked").val();
	}else {
		requestData["questionNum"] = $("#questionNum").val();
		requestData["subjectCode"] = $("input:radio[id='subjectCode']:checked").val();
	}
	$.ajax({
		type: "POST",
		url: './validateQuestionNum.action',
		data: $.param(requestData),
		async: false,
		dataType:"json",
		cache: false,
		success: function(json, status, request){
			if(json.result.isQuestionNumValid == "invalid"){
				isSuccess = false;
			} else {
				isSuccess = true;
			}
		}
	});
	
	return isSuccess;
}	
	
	

  
	