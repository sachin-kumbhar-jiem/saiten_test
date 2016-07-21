
$(function () {
	toggleStateSearch();
	toggleProcessSearch();
	
	if($("input:radio[id=subjectCode]:checked").length == 0){
		
		var redioBtnNum = $("input:radio[id=subjectCode]:unchecked").length;
		var i;
		for (i=0;i<redioBtnNum;i++) {
			if (!$('input:radio[id=subjectCode]')[i].disabled) {
				$('input:radio[id=subjectCode]')[i].checked = true;
				break;
			}
		}
	}
	//setDefaultCB(); 
});



//Our validation script will go here.
$(document).ready(function(){

	    $("#scoreSearchForm").keydown(function (e) {
	        if ((e.which && e.which == 13) || (e.keyCode && e.keyCode == 13)) {
	        	$('#scoreSearchForm').focus();
	            $("#search").click();
	            return false;
	        } else {
	            return true;
	        }
	    });
	
	$("#search").click(function()
		{
			setHistoryDateValues();
			setCurrentDateValues();	
			isDateError();
		});
	
	$("#topSearch").click(function()
			{
				setHistoryDateValues();
				setCurrentDateValues();
				isDateError();
			});
	
	//custom validation for Alphanumeric when value is not blank
	$.validator.addMethod("alphaNumericIfNotBlank", alphaNumericValidationIfNotBlank, "");
	
	//custom validation for Numeric Only
	$.validator.addMethod("numericOnly", numericOnlyValidation, "");
	
	//custom validation for history grade selection
	$.validator.addMethod("checkHistoryGradeSelected", historyGradeSelectionValidation, "");
    
	//custom validation for current grade selection
	$.validator.addMethod("checkCurrentGradeSelected", currentGradeSelectionValidation, "");
    
	//custom validation for Numeric and Space.
	$.validator.addMethod("numericComma", numericCommaValidation, "");
	
	//resultCount minimum range validation.
	$.validator.addMethod("resultCountMinValidRange", resultCountMinRangeValidation, "");
	
	//questionNum maximum range validation
	$.validator.addMethod("questionNumMaxValidRange", questionNumMaxRangeValidation, "");
	
	//resultCount maximum range validation.
	$.validator.addMethod("resultCountMaxValidRange", resultCountMaxRangeValidation, "");
	
	$.validator.addMethod("historyCheckPointValidation", historyCheckPointValidation, "");
	
	$.validator.addMethod("currentCheckPointValidation", currentCheckPointValidation, "");
	
	//custom validation for history Event selection
	$.validator.addMethod("checkHistoryEventSelected", historyEventSelectionValidation, "");
	
	//custom validation for current State selection
	$.validator.addMethod("checkCurrentStateSelected", currentStateSelectionValidation, "");
	
	//custom validation for checking length of history check point
	$.validator.addMethod("historyCheckLengthForCheckpoints", historyCheckLengthForCheckpoints, "");
	
	//custom validation for checking length of current check point
	$.validator.addMethod("currentCheckLengthForCheckpoints", currentCheckLengthForCheckpoints, "");
	
	//custom validation for checking length of pending categories
	$.validator.addMethod("checkHistoryPendingCategoryLength", checkHistoryPendingCategoryLength, "");
	
	// custom validation for checking length of current pending category
	$.validator.addMethod("checkCurrentPendingCategoryLength", checkCurrentPendingCategoryLength, "");
	
	// custom validation for checking length of history deny category
	$.validator.addMethod("checkHistoryDenyCategoryLength", checkHistoryDenyCategoryLength, "");
	
	// custom validation for checking length of current deny category
	$.validator.addMethod("checkCurrentDenyCategoryLength", checkCurrentDenyCategoryLength, "");
	
	//date validations
	$.validator.addMethod("checkForFromValidDayVal", checkForFromValidDay, DURATION_CHECK_LEAPYEAR_FROM_DATE); 
	$.validator.addMethod("checkForToValidDayVal", checkForToValidDay, DURATION_CHECK_LEAPYEAR_TO_DATE); 
	$.validator.addMethod("checkDateVal", checkdate, DURATION_CHECK_DATE);
	$.validator.addMethod("checkLeapYearFromDateToDateVal", checkLeapYearFromDateToDate, DURATION_CHECK_LEAPYEAR_FROM_TO_DATE);  
	$.validator.addMethod("checkLeapYearFromDateVal", checkLeapYearFromDate, DURATION_CHECK_LEAPYEAR_FROM_DATE);  
	$.validator.addMethod("checkLeapYearToDateVal", checkLeapYearToDate, DURATION_CHECK_LEAPYEAR_TO_DATE);
	$.validator.addMethod("checkFebruaryMonthFromDateToDateval", checkFebruaryMonthFromDateToDate, DURATION_CHECK_FEBRUARY_FROM_TO_DATE);  
	$.validator.addMethod("checkFromDateFebruaryMonthVal", checkFromDateFebruaryMonth, DURATION_CHECK_FEBRUARY_FROM_DATE);  
	$.validator.addMethod("checkToDateFebruaryMonthVal", checkToDateFebruaryMonth, DURATION_CHECK_FEBRUARY_TO_DATE);
	$.validator.addMethod("questionNumValidaton", isQuestionNumValid, "");
	
	
	//$.validator.addMethod("imeModeDisabled", checkImeModeDisabled , "");
	
	//validation implementation will go here.
	$("#scoreSearchForm").validate({
		rules: {	
			"scoreInputInfo.questionNum" :{
				required: true,
				numericOnly: true,
				maxlength: 5,
				questionNumMaxValidRange: true,
				questionNumValidaton: true
			},
			"scoreInputInfo.answerFormNum" :{
				alphaNumericIfNotBlank: true,
				maxlength: answerFormNumLength
			},
			"scoreInputInfo.resultCount" :{
				required: true,
				numericOnly: true,
				maxlength: 6,
				resultCountMinValidRange: true,
				resultCountMaxValidRange: true
			},
			"scoreInputInfo.scoreHistoryInfo.historyScorerId1" :{
				alphaNumericIfNotBlank: true,
				maxlength: 7
			},
			"scoreInputInfo.scoreHistoryInfo.historyScorerId2" :{
				alphaNumericIfNotBlank: true,
				maxlength: 7
			},
			"scoreInputInfo.scoreHistoryInfo.historyScorerId3" :{
				alphaNumericIfNotBlank: true,
				maxlength: 7
			},
			"scoreInputInfo.scoreHistoryInfo.historyScorerId4" :{
				alphaNumericIfNotBlank: true,
				maxlength: 7
			},
			"scoreInputInfo.scoreHistoryInfo.historyScorerId5" :{
				alphaNumericIfNotBlank: true,
				maxlength: 7
			},
			"scoreInputInfo.scoreCurrentInfo.currentScorerId1" :{
				alphaNumericIfNotBlank: true,
				maxlength: 7
			},
			"scoreInputInfo.scoreCurrentInfo.currentScorerId2" :{
				alphaNumericIfNotBlank: true,
				maxlength: 7
			},
			"scoreInputInfo.scoreCurrentInfo.currentScorerId3" :{
				alphaNumericIfNotBlank: true,
				maxlength: 7
			},
			"scoreInputInfo.scoreCurrentInfo.currentScorerId4" :{
				alphaNumericIfNotBlank: true,
				maxlength: 7
			},
			"scoreInputInfo.scoreCurrentInfo.currentScorerId5" :{
				alphaNumericIfNotBlank: true,
				maxlength: 7
			},
			"selectHistoryGrade" :{
				checkHistoryGradeSelected: true
			},
			"selectCurrentGrade" :{
				checkCurrentGradeSelected: true
			},
			"scoreInputInfo.scoreHistoryInfo.historyPendingCategory" :{
				required: true,
				numericComma: true
			},
			"scoreInputInfo.scoreCurrentInfo.currentPendingCategory" :{
				required: true,
				numericComma: true
			},
			"scoreInputInfo.scoreHistoryInfo.historyDenyCategory" :{
				required: true,
				numericComma: true
			},
			"scoreInputInfo.scoreCurrentInfo.currentDenyCategory" :{
				required: true,
				numericComma: true
			},
			"scoreInputInfo.scoreHistoryInfo.historyIncludeCheckPoints" :{
				numericComma: true
			},
			"scoreInputInfo.scoreHistoryInfo.historyExcludeCheckPoints" :{
				numericComma: true
			},
			"scoreInputInfo.scoreCurrentInfo.currentIncludeCheckPoints" :{
				numericComma: true
			},
			"scoreInputInfo.scoreCurrentInfo.currentExcludeCheckPoints" :{
				numericComma: true
			},
			"historyCheckPointHidden" :{
				historyCheckLengthForCheckpoints: true,
				historyCheckPointValidation: true
			},
			"currentCheckPointHidden" :{
				currentCheckLengthForCheckpoints: true,
				currentCheckPointValidation: true
			},
			"selectHistoryEvent" :{
				checkHistoryEventSelected: true
			},
			"selectCurrentState" :{
				checkCurrentStateSelected : true
			},
	     	"historyToValidDate" : {
	     		checkForToValidDayVal: true
	     	},
	     	"historyFromValidDate" : {
	     		checkForFromValidDayVal: true
	     	},
	     	"historyValidDate" : {
	     		checkDateVal: true,
	     		checkLeapYearFromDateToDateVal: true,
	     		checkLeapYearFromDateVal: true,
	     		checkLeapYearToDateVal: true,
	     		checkFebruaryMonthFromDateToDateval: true,
	     		checkFromDateFebruaryMonthVal: true,
	     		checkToDateFebruaryMonthVal: true
	     	},
	     	"currentToValidDate" : {
	     		checkForToValidDayVal: true
	     	},
	     	"currentFromValidDate" : {
	     		checkForFromValidDayVal: true
	     	},
	     	"currentValidDate" : {
	     		checkDateVal: true,
	     		checkLeapYearFromDateToDateVal: true,
	     		checkLeapYearFromDateVal: true,
	     		checkLeapYearToDateVal: true,
	     		checkFebruaryMonthFromDateToDateval: true,
	     		checkFromDateFebruaryMonthVal: true,
	     		checkToDateFebruaryMonthVal: true
	     	},
	     	"historyPendingCategoryHidden" : {
	     		checkHistoryPendingCategoryLength: true
	     	},
			"currentPendingCategoryHidden": {
				checkCurrentPendingCategoryLength: true
			},
			"historyDenyCategoryHidden" :{
				checkHistoryDenyCategoryLength: true
			},
			"currentDenyCategoryHidden" :{
				checkCurrentDenyCategoryLength: true
			}
	    },
	    messages: {
			
			"scoreInputInfo.questionNum" : {
				required: REQUIRED,
				numericOnly: NUMERIC_ONLY,
				maxlength: MAX_LENGTH_11,
				questionNumMaxValidRange: QUESTION_NUM_MAXIMUM_RANGE,
				questionNumValidaton: QUESTION_NO_INVALID
	        },
	        "scoreInputInfo.answerFormNum" :{
	        	alphaNumericIfNotBlank: ALPHA_NUMERIC_ONLY,
				maxlength: function() {
					if(answerFormNumLength==13){
						return MAX_LENGTH_13;
					}else if (answerFormNumLength==7) {
						return MAX_LENGTH_7;
					}
				}
			},
			"scoreInputInfo.resultCount" : {
				required: REQUIRED,
				numericOnly: NUMERIC_ONLY,
				maxlength: MAX_LENGTH_6,
				resultCountMinValidRange: RESULT_COUNT_MINIMUM_RANGE,
				resultCountMaxValidRange: RESULT_COUNT_MAXIMUM_RANGE
	        },
	        "scoreInputInfo.scoreHistoryInfo.historyScorerId1" :{
	        	alphaNumericIfNotBlank: ALPHA_NUMERIC_ONLY,
	        	maxlength: MAX_LENGTH_7
			},
			"scoreInputInfo.scoreHistoryInfo.historyScorerId2" :{
	        	alphaNumericIfNotBlank: ALPHA_NUMERIC_ONLY,
	        	maxlength: MAX_LENGTH_7
			},
			"scoreInputInfo.scoreHistoryInfo.historyScorerId3" :{
	        	alphaNumericIfNotBlank: ALPHA_NUMERIC_ONLY,
	        	maxlength: MAX_LENGTH_7
			},
			"scoreInputInfo.scoreHistoryInfo.historyScorerId4" :{
	        	alphaNumericIfNotBlank: ALPHA_NUMERIC_ONLY,
	        	maxlength: MAX_LENGTH_7
			},
			"scoreInputInfo.scoreHistoryInfo.historyScorerId5" :{
	        	alphaNumericIfNotBlank: ALPHA_NUMERIC_ONLY,
	        	maxlength: MAX_LENGTH_7
			},
	        "scoreInputInfo.scoreCurrentInfo.currentScorerId1" :{
	        	alphaNumericIfNotBlank: ALPHA_NUMERIC_ONLY,
	        	maxlength: MAX_LENGTH_7
			},
			"scoreInputInfo.scoreCurrentInfo.currentScorerId2" :{
	        	alphaNumericIfNotBlank: ALPHA_NUMERIC_ONLY,
	        	maxlength: MAX_LENGTH_7
			},
			"scoreInputInfo.scoreCurrentInfo.currentScorerId3" :{
	        	alphaNumericIfNotBlank: ALPHA_NUMERIC_ONLY,
	        	maxlength: MAX_LENGTH_7
			},
			"scoreInputInfo.scoreCurrentInfo.currentScorerId4" :{
	        	alphaNumericIfNotBlank: ALPHA_NUMERIC_ONLY,
	        	maxlength: MAX_LENGTH_7
			},
			"scoreInputInfo.scoreCurrentInfo.currentScorerId5" :{
	        	alphaNumericIfNotBlank: ALPHA_NUMERIC_ONLY,
	        	maxlength: MAX_LENGTH_7
			},
			"scoreInputInfo.scoreHistoryInfo.historyPendingCategory" :{
				required: PENDING_CATEGORY_REQUIRED,
				numericComma: PENDING_CATEGORY_NUMERIC_AND_COMMA_ALLOWED
			},
			"scoreInputInfo.scoreCurrentInfo.currentPendingCategory" :{
				required: PENDING_CATEGORY_REQUIRED,
				numericComma: PENDING_CATEGORY_NUMERIC_AND_COMMA_ALLOWED
			},
			"scoreInputInfo.scoreHistoryInfo.historyDenyCategory" :{
				required: DENY_CATEGORY_REQUIRED,
				numericComma: DENY_CATEGORY_NUMERIC_AND_COMMA_ALLOWED
			},
			"scoreInputInfo.scoreCurrentInfo.currentDenyCategory" :{
				required: DENY_CATEGORY_REQUIRED,
				numericComma: DENY_CATEGORY_NUMERIC_AND_COMMA_ALLOWED
			},
			"scoreInputInfo.scoreHistoryInfo.historyIncludeCheckPoints" :{
				numericComma: INCLUDE_CHECKPOINT_NUMERIC_AND_COMMA_ALLOWED
			},
			"scoreInputInfo.scoreHistoryInfo.historyExcludeCheckPoints" :{
				numericComma: EXCLUDE_CHECKPOINT_NUMERIC_AND_COMMA_ALLOWED
			},
			"scoreInputInfo.scoreCurrentInfo.currentIncludeCheckPoints" :{
				numericComma: INCLUDE_CHECKPOINT_NUMERIC_AND_COMMA_ALLOWED
			},
			"scoreInputInfo.scoreCurrentInfo.currentExcludeCheckPoints" :{
				numericComma: EXCLUDE_CHECKPOINT_NUMERIC_AND_COMMA_ALLOWED
			},
			"historyCheckPointHidden" :{
				historyCheckLengthForCheckpoints : MAX_LENGTH_CHECKPONTS_5,
				historyCheckPointValidation: INVALID_CHECK_POINTS
			},
			"currentCheckPointHidden" :{
				currentCheckLengthForCheckpoints : MAX_LENGTH_CHECKPONTS_5,
				currentCheckPointValidation: INVALID_CHECK_POINTS
			},
			"historyPendingCategoryHidden": {
				checkHistoryPendingCategoryLength: MAX_LENGTH_PENDING_CATEGORY_6
			},
			"currentPendingCategoryHidden": {
				checkCurrentPendingCategoryLength: MAX_LENGTH_PENDING_CATEGORY_6
			},
			"historyDenyCategoryHidden" :{
				checkHistoryDenyCategoryLength: MAX_LENGTH_DENY_CATEGORY_5
			},
			"currentDenyCategoryHidden" :{
				checkCurrentDenyCategoryLength: MAX_LENGTH_DENY_CATEGORY_5
			}
	    },
	    submitHandler: function(form){
	    	form.search.disabled = true;
		    $('#search').addClass('btn-disabled');
		    form.submit();
        }
	
	});
	
	$('#questionNum').bind('input', function () {
	     $(this).val($(this).val().replace(/[^0-9]/g, ''));
	});

	$('#answerFormNum').bind('input', function () {
		 $(this).val($(this).val().replace(/[^A-Za-z0-9]/g, ''));
	});

	$('#resultCount').bind('input', function () {
	     $(this).val($(this).val().replace(/[^0-9]/g, ''));
	});

	$('#historyScorerId1').bind('input', function () {
	     $(this).val($(this).val().replace(/[^A-Za-z0-9]/g, ''));
	});
	
	$('#historyScorerId2').bind('input', function () {
	     $(this).val($(this).val().replace(/[^A-Za-z0-9]/g, ''));
	});
	
	$('#historyScorerId3').bind('input', function () {
	     $(this).val($(this).val().replace(/[^A-Za-z0-9]/g, ''));
	});
	
	$('#historyScorerId4').bind('input', function () {
		$(this).val($(this).val().replace(/[^A-Za-z0-9]/g, ''));
	});
	
	$('#historyScorerId5').bind('input', function () {
		$(this).val($(this).val().replace(/[^A-Za-z0-9]/g, ''));
	});
	
	$('#currentScorerId1').bind('input', function () {
		$(this).val($(this).val().replace(/[^A-Za-z0-9]/g, ''));
	});
	
	$('#currentScorerId2').bind('input', function () {
		$(this).val($(this).val().replace(/[^A-Za-z0-9]/g, ''));
	});
	
	$('#currentScorerId3').bind('input', function () {
		$(this).val($(this).val().replace(/[^A-Za-z0-9]/g, ''));
	});
	
	$('#currentScorerId4').bind('input', function () {
		$(this).val($(this).val().replace(/[^A-Za-z0-9]/g, ''));
	});
	
	$('#currentScorerId5').bind('input', function () {
		$(this).val($(this).val().replace(/[^A-Za-z0-9]/g, ''));
	});

	/*$('#historyPendingCategory').bind('input', function () {
		$(this).val($(this).val().replace(/[^0-9,]/g, ''));
	});*/
	
	$('#historyPendingCategory').bind('keypress', function (e) {
		var regex = new RegExp("^[0-9,]+$");
	    var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
	    if (regex.test(str)) {
	        return true;
	    }
	    return false;
	});

	/*$('#currentPendingCategory').bind('input', function () {
		$(this).val($(this).val().replace(/[^0-9,]/g, ''));
	});*/
	
	$('#currentPendingCategory').bind('keypress', function (e) {
		var regex = new RegExp("^[0-9,]+$");
	    var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
	    if (regex.test(str)) {
	        return true;
	    }
	    return false;
	});
	
	$('#historyDenyCategory').bind('keypress', function (e) {
		var regex = new RegExp("^[0-9,]+$");
	    var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
	    if (regex.test(str)) {
	        return true;
	    }
	    return false;
	});
	
	$('#currentDenyCategory').bind('keypress', function (e) {
		var regex = new RegExp("^[0-9,]+$");
	    var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
	    if (regex.test(str)) {
	        return true;
	    }
	    return false;
	});

	/*$('#historyIncludeCheckPoints').bind('input', function () {
		$(this).val($(this).val().replace(/[^0-9,]/g, ''));
	});*/
	
	$('#historyIncludeCheckPoints').bind('keypress', function (e) {
		var regex = new RegExp("^[0-9,]+$");
	    var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
	    if (regex.test(str)) {
	        return true;
	    }
	    return false;
	});

	/*$('#historyExcludeCheckPoints').bind('input', function () {
		$(this).val($(this).val().replace(/[^0-9,]/g, ''));
	});*/
	
	$('#historyExcludeCheckPoints').bind('keypress', function (e) {
		var regex = new RegExp("^[0-9,]+$");
	    var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
	    if (regex.test(str)) {
	        return true;
	    }
	    return false;
	});
	
	/*$('#currentIncludeCheckPoints').bind('input', function () {
		$(this).val($(this).val().replace(/[^0-9,]/g, ''));
	});*/

	$('#currentIncludeCheckPoints').bind('keypress', function (e) {
		var regex = new RegExp("^[0-9,]+$");
	    var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
	    if (regex.test(str)) {
	        return true;
	    }
	    return false;
	});
	
	/*$('#currentExcludeCheckPoints').bind('input', function () {
		$(this).val($(this).val().replace(/[^0-9,]/g, ''));
	});*/
	
	$('#currentExcludeCheckPoints').bind('keypress', function (e) {
		var regex = new RegExp("^[0-9,]+$");
	    var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
	    if (regex.test(str)) {
	        return true;
	    }
	    return false;
	});

});

/*function checkImeModeDisabled(){
	return true;
}*/

//this method call for textfield's for which value is not mandatory.
//and if value entered then it should be alphanumeric.
function alphaNumericValidationIfNotBlank(value, element) {
	
	if(value!=""){

		var reg=/^[0-9A-Za-z]+$/;

		if(reg.test(value) == false) 

	  			return false;

		else
				return true;
	}else {
		return true;
	}
}

//call this method onchange of historyGrade checkboxes.
//and validate at least one grade should selected.
function checkAtLeastOneHistoryGradeSelected(){
	var isDisable = !document.getElementById("historyBlock").checked;
	if(isDisable){
		return true;
	}
	var form = document.getElementById("scoreSearchForm");
	if(!(form.historyGradeNum[0].checked 
			|| form.historyGradeNum[1].checked
			|| form.historyGradeNum[2].checked
			|| form.historyGradeNum[3].checked
			|| form.historyGradeNum[4].checked
			|| form.historyGradeNum[5].checked
			|| form.historyGradeNum[6].checked
			|| form.historyGradeNum[7].checked
			|| form.historyGradeNum[8].checked
			|| form.historyGradeNum[9].checked
			|| form.historyGradeNum[10].checked)){
		if($("#selectHistoryGrade").val()==""){
			$("#selectHistoryGrade").val(SELECT_GRADE);
		}
		return false;
	}else {
		$("#selectHistoryGrade").val(""); 
		return true;
	}
}

//call this method onchange of currentGrade checkboxes.
//and validate at least one grade should selected.
function checkAtLeastOneCurrentGradeSelected(){
	var isDisable = !document.getElementById("currentBlock").checked;
	if(isDisable){
		return true;
	}
	var form = document.getElementById("scoreSearchForm");
	if(!(form.currentGradeNum[0].checked 
			|| form.currentGradeNum[1].checked
			|| form.currentGradeNum[2].checked
			|| form.currentGradeNum[3].checked
			|| form.currentGradeNum[4].checked
			|| form.currentGradeNum[5].checked
			|| form.currentGradeNum[6].checked
			|| form.currentGradeNum[7].checked
			|| form.currentGradeNum[8].checked
			|| form.currentGradeNum[9].checked
			|| form.currentGradeNum[10].checked)){
		if($("#selectCurrentGrade").val()==""){
			$("#selectCurrentGrade").val(SELECT_GRADE);
		}
		return false;
	}else {
		$("#selectCurrentGrade").val(""); 
		return true;
	}
}
//on form submission check at least one History Grade Selected.
function historyGradeSelectionValidation(value, element){
	var radio = document.getElementsByName('scoreInputInfo.scoreHistoryInfo.historyCategoryType');
	if(radio[2].checked){
		return checkAtLeastOneHistoryGradeSelected();
	}else {
		return true;
	}
}

//on form submission check at least one Current Grade Selected.
function currentGradeSelectionValidation(value, element){
	var radio = document.getElementsByName('scoreInputInfo.scoreCurrentInfo.currentCategoryType');
	if(radio[2].checked){
		return checkAtLeastOneCurrentGradeSelected();
	}else {
		return true;
	}
}

function checkAtLeastOneHistoryEventSelected(){
	var isDisable = !document.getElementById("historyBlock").checked;
	if(isDisable){
		return true;
	}
	var checkValues = $('input[id=historyEventList]:checked').map(function() {
        			return $(this).val();
    			}).get();
	if(checkValues.length>0){
		$("#selectHistoryEvent").val("");
		return true;
	}else{
		if($("#selectHistoryEvent").val()==""){
			$("#selectHistoryEvent").val(SELECT_EVENT);
		}
		return false;
	}
	
}

function checkAtLeastOneCurrentStateSelected(){
	var isDisable = !document.getElementById("currentBlock").checked;
	if(isDisable){
		return true;
	}
	var checkValues = $('input[id=currentStateList]:checked').map(function() {
        			return $(this).val();
    			}).get();
	if(checkValues.length>0){
		$("#selectCurrentState").val("");
		return true;
	}else{
		if($("#selectCurrentState").val()==""){
			$("#selectCurrentState").val(SELECT_STAE);
		}
		return false;
	}
	
}

function historyEventSelectionValidation(value, element){
	return checkAtLeastOneHistoryEventSelected();
}

function currentStateSelectionValidation(value, element){
	return checkAtLeastOneCurrentStateSelected();
}

function resultCountMinRangeValidation(value, element){
	if(value>0){
		return true;
	}else{
		return false;
	}
}
function resultCountMaxRangeValidation(value, element){
	if(value<=100000){
		return true;
	}else{
		return false;
	}
}

function questionNumMaxRangeValidation(value, element){
	if(value<=32767){
		return true;
	}else{
		return false;
	}
}

function checkHistoryPendingCategoryLength (value, element) {
	var historyPendingCategoryString= $('#historyPendingCategory').val();
	
	var historyPendingCategoryArray;
	var i;
	if(historyPendingCategoryString!='') {
		historyPendingCategoryArray= splitStringByComma(historyPendingCategoryString);
		for (i=0;i<historyPendingCategoryArray.length;i++) {
			if(historyPendingCategoryArray[i].length>5) {
				return false;
			}
		}
	}
	return true;
}

function checkCurrentPendingCategoryLength(value, element) {

	var currentPendingCategoryString= $('#currentPendingCategory').val();

	var currentPendingCategoryArray;
	var i;

	if(currentPendingCategoryString!='') {
		currentPendingCategoryArray= splitStringByComma(currentPendingCategoryString);

		for (i=0;i<currentPendingCategoryArray.length;i++) {
			if(currentPendingCategoryArray[i].length>5) {
				return false;
			}
		}
	}
	return true;
}

function checkHistoryDenyCategoryLength (value, element) {
	var historyDenyCategoryString= $('#historyDenyCategory').val();
	
	var historyDenyCategoryArray;
	var i;
	if(historyDenyCategoryString!='') {
		historyDenyCategoryArray= splitStringByComma(historyDenyCategoryString);
		for (i=0;i<historyDenyCategoryArray.length;i++) {
			if(historyDenyCategoryArray[i].length>5) {
				return false;
			}
		}
	}
	return true;
}

function checkCurrentDenyCategoryLength(value, element) {

	var currentDenyCategoryString= $('#currentDenyCategory').val();

	var currentDenyCategoryArray;
	var i;

	if(currentDenyCategoryString!='') {
		currentDenyCategoryArray= splitStringByComma(currentDenyCategoryString);

		for (i=0;i<currentDenyCategoryArray.length;i++) {
			if(currentDenyCategoryArray[i].length>5) {
				return false;
			}
		}
	}
	return true;
}

function historyCheckPointValidation(value, element){
	var includeCheckPoints= $('#historyIncludeCheckPoints').val();
	var excludeCheckPoints= $('#historyExcludeCheckPoints').val();
	return checkPointValidation(includeCheckPoints, excludeCheckPoints);
}

function currentCheckPointValidation(value, element){
	var includeCheckPoints= $('#currentIncludeCheckPoints').val();
	var excludeCheckPoints= $('#currentExcludeCheckPoints').val();
	return checkPointValidation(includeCheckPoints, excludeCheckPoints);
}

function checkPointValidation(includeCheckPoints, excludeCheckPoints){
	
	var includeCheckPointArray= [];
	var excludeCheckPointArray= [];
	var includeBitValue=0;
	var excludeBitValue=0;
	
	if((includeCheckPoints!='')&&(excludeCheckPoints!='')){
		includeCheckPointArray = splitStringByComma(includeCheckPoints);
		excludeCheckPointArray = splitStringByComma(excludeCheckPoints);
		
		if(includeCheckPointArray.length>0){
			includeBitValue = calculateBitValue(includeCheckPointArray);
		}
		if(excludeCheckPointArray.length>0){
			excludeBitValue = calculateBitValue(excludeCheckPointArray);
		}
		if((includeBitValue!=0)&&(excludeBitValue!=0)){
			if((includeBitValue & excludeBitValue)!=0){
				return false;
			}
		}
		return true;
	}else{
		return true;
	}
	
}
function historyCheckLengthForCheckpoints (value, element) {
	var includeCheckPoints= $('#historyIncludeCheckPoints').val();
	var excludeCheckPoints= $('#historyExcludeCheckPoints').val();
	
	
	var includeCheckPointArray= [];
	var excludeCheckPointArray= [];

	
	var i;
	
	if(includeCheckPoints!=''){
		includeCheckPointArray = splitStringByComma(includeCheckPoints);
		
		for (i=0; i<includeCheckPointArray.length; i++) {
			if (includeCheckPointArray[i].length>5) {
				return false;
			}
		}
	}
	if (excludeCheckPoints!='') {
		excludeCheckPointArray = splitStringByComma(excludeCheckPoints);
		
		for (i=0; i<excludeCheckPointArray.length; i++) {
			if (excludeCheckPointArray[i].length>5) {
				return false;
			}
		}
	}
	
	return true;
}

function currentCheckLengthForCheckpoints(value, element) {
	var includeCheckPoints= $('#currentIncludeCheckPoints').val();
	var excludeCheckPoints= $('#currentExcludeCheckPoints').val();
	
	var includeCheckPointArray= [];
	var excludeCheckPointArray= [];
	
	var i;
	
	if(includeCheckPoints!=''){
		includeCheckPointArray = splitStringByComma(includeCheckPoints);
		
		for (i=0; i<includeCheckPointArray.length; i++) {
			if (includeCheckPointArray[i].length>5) {
				return false;
			}
		}
	}
	if (excludeCheckPoints!='') {
		excludeCheckPointArray = splitStringByComma(excludeCheckPoints);
		
		for (i=0; i<excludeCheckPointArray.length; i++) {
			if (excludeCheckPointArray[i].length>5) {
				return false;
			}
		}
	}
return true;
}

function splitStringByComma(str){
	return str.split(",");
}

function calculateBitValue(checkPointArray){
	var bitValue = 0;
	for (var i in checkPointArray) {
		bitValue+=Math.pow(2,checkPointArray[i]);
		}
	return bitValue;
}

function removeHistoryBlockErrorMessages(){
	var isDisable = !document.getElementById("historyBlock").checked;
	if(isDisable){
		$('#selectHistoryGrade').val("");
		$("#selectHistoryEvent").val("");
		var history_div = $("#history_div");
		var errorMeassages = history_div.find('label'); //.find($(".error"))
		errorMeassages.remove();
	}
}

function removeCurrentBlockErrorMessages(){
	var isDisable = !document.getElementById("currentBlock").checked;
	if(isDisable){
		$('#selectCurrentGrade').val("");
		$("#selectCurrentState").val("");
		var current_div = $("#current_div");
		var errorMeassages = current_div.find('label'); //find($(".error"))
		errorMeassages.remove();
	}
}

function isDateError (value, element) {
	 // $('#dateError').focus();
	var cDate1 = $( "#currentValidDate" ).valid();
	var cDate2 = $( "#currentFromValidDate" ).valid();
	var cDate3 = $( "#currentToValidDate" ).valid();
	
	var hDate1 = $( "#historyFromValidDate" ).valid();
	var hDate2 = $( "#historyToValidDate" ).valid();
	var hDate3 = $( "#historyValidDate" ).valid();
	
	if (cDate1==0 || cDate2==0 || cDate3==0) {
		$('#currentDateError').focus();
	}
	if (hDate1 ==0 || hDate2 == 0|| hDate3 == 0) {
		$('#historyDateError').focus();
	}
	return false;
}

//date validation functions
this.setHistoryDateValues = function(){
	$("#historyFromValidDate").val($("select#historyUpdateDateStartYear").val()+ "#" +$("select#historyUpdateDateStartMonth").val()+ "#" +$("select#historyUpdateDateStartDay").val()+ "#" +$("select#historyUpdateDateStartHours").val()+ "#" +$("select#historyUpdateDateStartMin").val());
	$("#historyToValidDate").val($("select#historyUpdateDateEndYear").val()+ "#" +$("select#historyUpdateDateEndMonth").val()+ "#" +$("select#historyUpdateDateEndDay").val()+ "#" +$("select#historyUpdateDateEndHours").val()+ "#" +$("select#historyUpdateDateEndMin").val());
	$("#historyValidDate").val($("#historyFromValidDate").val()+ "/" +$("#historyToValidDate").val());
};
this.setCurrentDateValues = function(){
	$("#currentFromValidDate").val($("select#currentUpdateDateStartYear").val()+ "#" +$("select#currentUpdateDateStartMonth").val()+ "#" +$("select#currentUpdateDateStartDay").val()+ "#" +$("select#currentUpdateDateStartHours").val()+ "#" +$("select#currentUpdateDateStartMin").val());
	$("#currentToValidDate").val($("select#currentUpdateDateEndYear").val()+ "#" +$("select#currentUpdateDateEndMonth").val()+ "#" +$("select#currentUpdateDateEndDay").val()+ "#" +$("select#currentUpdateDateEndHours").val()+ "#" +$("select#currentUpdateDateEndMin").val());
	$("#currentValidDate").val($("#currentFromValidDate").val()+ "/" +$("#currentToValidDate").val());
};

