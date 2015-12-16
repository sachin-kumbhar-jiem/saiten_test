$(document).ready(function(){
	
		  if(backButtonSession!=null && (''+backButtonSession).trim()=='true'){
			  if($("#scorewiseSearchRadio").is(':checked')){
				   $('#scorewiseDiv :input').removeAttr('disabled');
				   $('#questionwiseDiv :input').attr('disabled', true);
				}
			  else{
				   $('#questionwiseDiv :input').removeAttr('disabled');
				   subjectEnableDisable();
				   $('#scorewiseDiv :input').attr('disabled', true);
			  }
		  }
		  else{
	      resetDate();
		 
		  
			$('#scorewiseDiv :input').attr('disabled', true);
		  }
			if($("input:radio[id=subjectCode]:checked").length == 0){
				$("input:radio[id=subjectCode]:first").attr('checked', true);
			}
			
		  
			$("#scorewiseSearchRadio").click(function(){
				if($("#scorewiseSearchRadio").is(':checked')){
				   $('#scorewiseDiv :input').removeAttr('disabled');
				   $('#questionwiseDiv :input').attr('disabled', true);
				   resetDate();
				   
				}
			});
			
		
			
			$("#questionwiseSearchRadio").click(function(){
				if($("#questionwiseSearchRadio").is(':checked')){
				   $('#questionwiseDiv :input').removeAttr('disabled');
				   $('#scorewiseDiv :input').attr('disabled', true);
				   resetDate();
				   subjectEnableDisable();
				}
			});
        
			setSubmitButtonValue();
			
			$(".searchType").click(function(){
			    	setSubmitButtonValue();
			    	
           });
			
		    $(".questionType").click(function(){
		    	 $("#dailyStatusSearchForm").removeData('validator');
		    	 $(".error").val('');
		    	setSubmitButtonValue();
		    	
		    });
		    
		    function setSubmitButtonValue(){
		    	/*if($("#writingRadio").is(':checked') && $("#scorewiseSearchRadio").is(':checked')){
		    		$("#search").attr('value',WritingScorerWise);
					}
		    	
		    	if($("#writingRadio").is(':checked') && $("#questionwiseSearchRadio").is(':checked')){
		    		$("#search").attr('value',WritingQuestionWise);
		    	}
		    	
		    	if($("#speakingRadio").is(':checked') && $("#scorewiseSearchRadio").is(':checked')){
		    		$("#search").attr('value',SpeakingScorerWise);
					}
		    	
		    	
		    	if($("#speakingRadio").is(':checked') && $("#questionwiseSearchRadio").is(':checked')){
		    		$("#search").attr('value', SpeakingQuestionWise);
		    	}*/
		    }
		    
			function resetDate(){
				var now = new Date();
			    now.setDate(now.getDate() - 1);
			    $("#questionwiseUpdateDateStartYear").val(now.getFullYear());
			    $("#questionwiseUpdateDateStartMonth").val(now.getMonth() + 1);
				$("#questionwiseUpdateDateStartDay").val(now.getDate());
				$("#questionwiseUpdateDateStartHours").val(0);
				$("#questionwiseUpdateDateStartMin").val(0);
				
			    $("#questionwiseUpdateDateEndYear").val(now.getFullYear());
			    $("#questionwiseUpdateDateEndMonth").val(now.getMonth() + 1);
				$("#questionwiseUpdateDateEndDay").val(now.getDate());
				$("#questionwiseUpdateDateEndHours").val(23);
				$("#questionwiseUpdateDateEndMin").val(59);
				
				
				   $("#scorewiseUpdateDateStartYear").val(now.getFullYear());
				    $("#scorewiseUpdateDateStartMonth").val(now.getMonth() + 1);
					$("#scorewiseUpdateDateStartDay").val(now.getDate());
					$("#scorewiseUpdateDateStartHours").val(0);
					$("#scorewiseUpdateDateStartMin").val(0);
					
				    $("#scorewiseUpdateDateEndYear").val(now.getFullYear());
				    $("#scorewiseUpdateDateEndMonth").val(now.getMonth() + 1);
					$("#scorewiseUpdateDateEndDay").val(now.getDate());
					$("#scorewiseUpdateDateEndHours").val(23);
					$("#scorewiseUpdateDateEndMin").val(59);
			}
			
			
			
	       $("#search").click(function(){
	    	   
	    	   $("#dailyStatusSearchForm").removeData('validator');
	    	 //questionNum maximum range validation
	    		$.validator.addMethod("questionNumMaxValidRange", questionNumMaxRangeValidation, "");
	    		$.validator.addMethod("questionNumValidaton", isQuestionNumValid, "");
	    		//custom validation for Numeric Only
	    		$.validator.addMethod("numericOnly", numericOnlyValidation, "");
	    		$.validator.addMethod("checkForFromValidDayVal", checkForFromValidDay, ""); 
	    		$.validator.addMethod("checkForToValidDayVal", checkForToValidDay, ""); 
	    		$.validator.addMethod("checkDateVal", checkdate, DURATION_CHECK_DATE);
	    		$.validator.addMethod("checkLeapYearFromDateToDateVal", checkLeapYearFromDateToDate, DURATION_CHECK_LEAPYEAR_FROM_TO_DATE);  
	    		$.validator.addMethod("checkLeapYearFromDateVal", checkLeapYearFromDate, DURATION_CHECK_LEAPYEAR_FROM_DATE);  
	    		$.validator.addMethod("checkLeapYearToDateVal", checkLeapYearToDate, DURATION_CHECK_LEAPYEAR_TO_DATE);
	    		$.validator.addMethod("checkFebruaryMonthFromDateToDateval", checkFebruaryMonthFromDateToDate, DURATION_CHECK_FEBRUARY_FROM_TO_DATE);  
	    		$.validator.addMethod("checkFromDateFebruaryMonthVal", checkFromDateFebruaryMonth, DURATION_CHECK_FEBRUARY_FROM_DATE);  
	    		$.validator.addMethod("checkToDateFebruaryMonthVal", checkToDateFebruaryMonth, DURATION_CHECK_FEBRUARY_TO_DATE);
	    		$.validator.addMethod("todayAndFutureDateNotAllowFromDate", todayAndFutureDateNotAllowFromDate, ENTER_FROM_DATE_BEFORE_TODAY);
	    		$.validator.addMethod("todayAndFutureDateNotAllowToDate", todayAndFutureDateNotAllowToDate, ENTER_TO_DATE_BEFORE_TODAY);
              
	    		if($("#questionwiseSearchRadio").is(':checked')) {
	    			setQuestionwiseDateValues();
	    	       if($("#questionNum").val()!=''){
	    	             $("#dailyStatusSearchForm").validate({
	    	    		     rules: {	
				    	    			"dailyStatusSearchInfo.questionNum" :{
				    	    				numericOnly: true,
				    	    				maxlength: 5,
				    	    				questionNumMaxValidRange: true,
				    	    				questionNumValidaton: true
				    	    			},
				    	    			"questionwiseToValidDate" : {
				    	    	     		checkForToValidDayVal: true
				    	    	     	},
				    	    	     	"questionwiseFromValidDate" : {
				    	    	     		checkForFromValidDayVal: true
				    	    	     	},
				    	    	     	"questionwiseValidDate" : {
				    	    	     		checkDateVal: true,
				    	    	     		checkLeapYearFromDateToDateVal: true,
				    	    	     		checkLeapYearFromDateVal: true,
				    	    	     		checkLeapYearToDateVal: true,
				    	    	     		checkFebruaryMonthFromDateToDateval: true,
				    	    	     		checkFromDateFebruaryMonthVal: true,
				    	    	     		checkToDateFebruaryMonthVal: true,
				    	    	     		todayAndFutureDateNotAllowFromDate : true,
				    	    	     		todayAndFutureDateNotAllowToDate : true
				    	    	     	}
				    	    		},
				    	    		 messages: {
				    	    			 "dailyStatusSearchInfo.questionNum" : {
				    	    					numericOnly: NUMERIC_ONLY,
				    	    					maxlength: MAX_LENGTH_11,
				    	    					questionNumMaxValidRange: QUESTION_NUM_MAXIMUM_RANGE,
				    	    					questionNumValidaton: QUESTION_NO_INVALID
				    	    		        },
				    	    		        "questionwiseToValidDate" : {
					    	    	     		checkForToValidDayVal: DURATION_CHECK_LEAPYEAR_TO_DATE
					    	    	     	},
					    	    	     	"questionwiseFromValidDate" : {
					    	    	     		checkForFromValidDayVal: DURATION_CHECK_LEAPYEAR_FROM_DATE
					    	    	     	}
				    	    		}
			
	                });
	    	    } // check if question num not null
	    	       else{
	    	    	   $("#dailyStatusSearchForm").validate({
	    	    	   rules: {	
	    	    	    	"questionwiseToValidDate" : {
	    	    	     		checkForToValidDayVal: true
	    	    	     	},
	    	    	     	"questionwiseFromValidDate" : {
	    	    	     		checkForFromValidDayVal: true
	    	    	     	},
	    	    	     	"questionwiseValidDate" : {
	    	    	     		checkDateVal: true,
	    	    	     		checkLeapYearFromDateToDateVal: true,
	    	    	     		checkLeapYearFromDateVal: true,
	    	    	     		checkLeapYearToDateVal: true,
	    	    	     		checkFebruaryMonthFromDateToDateval: true,
	    	    	     		checkFromDateFebruaryMonthVal: true,
	    	    	     		checkToDateFebruaryMonthVal: true,
	    	    	     		todayAndFutureDateNotAllowFromDate : true,
	    	    	     		todayAndFutureDateNotAllowToDate : true
	    	    	     	}
	    	    		},
	    	    		 messages: {
	    	    			   "questionwiseToValidDate" : {
		    	    	     		checkForToValidDayVal: DURATION_CHECK_LEAPYEAR_TO_DATE
		    	    	     	},
		    	    	     	"questionwiseFromValidDate" : {
		    	    	     		checkForFromValidDayVal: DURATION_CHECK_LEAPYEAR_FROM_DATE
		    	    	     	}
	    	    		}
	    	    	   });
	    	    		
	    	       }   
	    	}//check search type
	    		else{ //scorewise search 
	    			setScorewiseDateValues();
	    			  $("#dailyStatusSearchForm").validate({
		    	    	   rules: {	
		    	    	    	"scorewiseToValidDate" : {
		    	    	     		checkForToValidDayVal: true
		    	    	     	},
		    	    	     	"scorewiseFromValidDate" : {
		    	    	     		checkForFromValidDayVal: true
		    	    	     	},
		    	    	     	"scorewiseValidDate" : {
		    	    	     		checkDateVal: true,
		    	    	     		checkLeapYearFromDateToDateVal: true,
		    	    	     		checkLeapYearFromDateVal: true,
		    	    	     		checkLeapYearToDateVal: true,
		    	    	     		checkFebruaryMonthFromDateToDateval: true,
		    	    	     		checkFromDateFebruaryMonthVal: true,
		    	    	     		checkToDateFebruaryMonthVal: true,
		    	    	     		todayAndFutureDateNotAllowFromDate : true,
		    	    	     		todayAndFutureDateNotAllowToDate : true
		    	    	     	}
		    	    		},
		    	    		 messages: {
		    	    			   "scorewiseToValidDate" : {
			    	    	     		checkForToValidDayVal: DURATION_CHECK_LEAPYEAR_TO_DATE
			    	    	     	},
			    	    	     	"scorewiseFromValidDate" : {
			    	    	     		checkForFromValidDayVal: DURATION_CHECK_LEAPYEAR_FROM_DATE
			    	    	     	}
		    	    		}
		    	    	   });
	    		}    
	    	   
	       });
	       
	     
		});
	
	

	function questionNumMaxRangeValidation(value, element){
		if(value<=32767){
			return true;
		}else{
			return false;
		}
	}
	
	//date validation functions
    this.setQuestionwiseDateValues = function(){
    	$("#questionwiseFromValidDate").val($("select#questionwiseUpdateDateStartYear").val()+ "#" +$("select#questionwiseUpdateDateStartMonth").val()+ "#" +$("select#questionwiseUpdateDateStartDay").val()+ "#" +$("select#questionwiseUpdateDateStartHours").val()+ "#" +$("select#questionwiseUpdateDateStartMin").val());
    	$("#questionwiseToValidDate").val($("select#questionwiseUpdateDateEndYear").val()+ "#" +$("select#questionwiseUpdateDateEndMonth").val()+ "#" +$("select#questionwiseUpdateDateEndDay").val()+ "#" +$("select#questionwiseUpdateDateEndHours").val()+ "#" +$("select#questionwiseUpdateDateEndMin").val());
    	$("#questionwiseValidDate").val($("#questionwiseFromValidDate").val()+ "/" +$("#questionwiseToValidDate").val());
    };
    
    this.setScorewiseDateValues = function(){
    	$("#scorewiseFromValidDate").val($("select#scorewiseUpdateDateStartYear").val()+ "#" +$("select#scorewiseUpdateDateStartMonth").val()+ "#" +$("select#scorewiseUpdateDateStartDay").val()+ "#" +$("select#scorewiseUpdateDateStartHours").val()+ "#" +$("select#scorewiseUpdateDateStartMin").val());
    	$("#scorewiseToValidDate").val($("select#scorewiseUpdateDateEndYear").val()+ "#" +$("select#scorewiseUpdateDateEndMonth").val()+ "#" +$("select#scorewiseUpdateDateEndDay").val()+ "#" +$("select#scorewiseUpdateDateEndHours").val()+ "#" +$("select#scorewiseUpdateDateEndMin").val());
    	$("#scorewiseValidDate").val($("#scorewiseFromValidDate").val()+ "/" +$("#scorewiseToValidDate").val());
    };

    function todayAndFutureDateNotAllowFromDate(value, element)
    {
    	
     	var fromDateStr=value.split("/")[0]
    	var toDateStr=value.split("/")[1]
    		
    	var fromValidYear=fromDateStr.split("#")[0]
    	var fromValidMonth=fromDateStr.split("#")[1]
    	var fromValidDay=fromDateStr.split("#")[2]
     	var fromValidHours=fromDateStr.split("#")[3]
    	var fromValidMin=fromDateStr.split("#")[4]
    	
    
    	var fromDate= new Date(fromValidYear, fromValidMonth-1, fromValidDay, 23, 59, 00, 00);
    
     	if(fromDate>=new Date()){
    		return false;
    	}
    	else{
    		return true;
    	}
    }
	
    function todayAndFutureDateNotAllowToDate(value, element)
    {
    	
     	var fromDateStr=value.split("/")[0]
    	var toDateStr=value.split("/")[1]
    		
     	var toValidYear=toDateStr.split("#")[0]
    	var toValidMonth=toDateStr.split("#")[1]
    	var toValidDay=toDateStr.split("#")[2]
  	    var toValidHours=toDateStr.split("#")[3]
  	    var toValidMin=toDateStr.split("#")[4]
    	
    	var toDate= new Date(toValidYear, toValidMonth-1, toValidDay, 23, 59, 00, 00); 
    	
     	if(toDate>=new Date()){
    		return false;
    	}
    	else{
    		return true;
    	}
    }
    
	function validateNUM(evt,field)
	{
		  var theEvent = evt || window.event;
		  var key = theEvent.keyCode || theEvent.which;
		  key = String.fromCharCode( key );
//	    var regex =/^-?[0-9]*\.?[0-9]*$/;
		  
		  if( isNaN(parseFloat(key)) && !isFinite(key)) {
		    theEvent.returnValue = false;
		    if(theEvent.preventDefault) theEvent.preventDefault();
		  }	  
	 }
	
	function validateAplhaNum(evt,field){
		  var theEvent = evt || window.event;
		  var key = theEvent.keyCode || theEvent.which;
		  key = String.fromCharCode( key );
	    var regex =/^[A-Za-z0-9]+$/;
		  if(!regex.test(key) ) {
		    theEvent.returnValue = false;
		    if(theEvent.preventDefault) theEvent.preventDefault();
		  }	 
	}
	
	function subjectEnableDisable(){
		
		var listObj=document.getElementById("loggedInScorerSubjectList").value;
		listObj=listObj.replace("[","");
		listObj=listObj.replace("]","");
		var listArray=listObj.split(",");
		
		
		var radios = document.getElementsByName("dailyStatusSearchInfo.subjectCode");
    	for(j=0;j<listArray.length;j++){
    		var countWrong=0;
	    for( i = 0; i < radios.length; i++ ) {
	    		if(parseInt(radios[i].value)!=parseInt(listArray[j])){
	    			countWrong++;
	    		}
	    	}
	    
	     if(countWrong>0){
	    	// radios[i].disabled=true;
	     }
	    }

	}
	