$(function () {
	if($("input:radio[id=subjectCode]:checked").length == 0){
		$("input:radio[id=subjectCode]:first").attr('checked', true);
		$("input:radio[name='specialScoreInputInfo.specialScoringType']:first").attr('checked', true);
	}
});	

$(document).ready(function(){
	
	$.validator.addMethod("validateQuestionNum", validateQuestionNum, "");
	$.validator.addMethod("questionNumValidaton", isQuestionNumValidSpecialScoring, "");
	$.validator.addMethod("validateAnswerFormNum", validateAnswerFormNum, "");
	$.validator.addMethod("questionNumMaxValidRange", questionNumMaxRangeValidation, "");
	
	$('#questionNum').bind('input', function () {
	     $(this).val($(this).val().replace(/[^0-9]/g, ''));
	 });
	
	$('#answerFormNum').bind('input', function () {
	     $(this).val($(this).val().replace(/[^A-Za-z0-9]/g, ''));
	 });
	
	//validation implementation will go here.
	$("#specialScoreSearchForm").validate({
		rules: {	
			 "specialScoreInputInfo.questionNum" : {
				 validateQuestionNum:true,
				 questionNumMaxValidRange: true,
				 questionNumValidaton: true
				 
   			},
   			"specialScoreInputInfo.answerFormNum" : {
				 validateAnswerFormNum:true				
  			}
		},
	    messages: {
			"specialScoreInputInfo.questionNum" : {
				validateQuestionNum: QUESTION_NO_REQUIRED,
				questionNumMaxValidRange: QUESTION_NUM_MAXIMUM_RANGE,
				questionNumValidaton: QUESTION_NO_INVALID
	        },
	        "specialScoreInputInfo.answerFormNum" : {
	        	validateAnswerFormNum: ANSWER_FORM_NO_REQUIRED
	        }
	    },
	    submitHandler : function(form) {
	    	form.search.disabled = true;
		    $('#search').addClass('btn-disabled');
		    
	    	var specialScoringType = $("input:radio[name='specialScoreInputInfo.specialScoringType']:checked").val();
	    	
	    	if($("#questionNum").val()!='') {
	    		document.forms[0].action = 'showSpecialScoreSearch.action';
	    	} else {
	    		document.forms[0].action = 'showOmrEnlargeScoreSearch.action';
	    	}
	    	
	    	document.forms[0].submit();
	    }	    
	
	});
	
	function validateQuestionNum(){
		var specialScoringType = $("input:radio[name='specialScoreInputInfo.specialScoringType']:checked").val();
		if(specialScoringType == 251 || specialScoringType == 257){
			if($("#questionNum").val()!='')
				return true;
			else
				return false;
		}
		return true;
	}
	
	function isQuestionNumValidSpecialScoring(value, element){
		
		if($("#questionNum").val()!=''){
			return isQuestionNumValid(value,element);
		}else{
			return true;
		}
		
	}
	
	function validateAnswerFormNum(){
		var specialScoringType = $("input:radio[name='specialScoreInputInfo.specialScoringType']:checked").val();
		if(specialScoringType == 253 || specialScoringType == 255){
			if($("#answerFormNum").val()!='')
				return true;
			else
				return false;
		}
		return true;
	}
	
	function questionNumMaxRangeValidation(value, element){
		if(value<=32767){
			return true;
		}else{
			return false;
		}
	}
});

		