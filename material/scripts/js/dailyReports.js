$(document)
		.ready(
				function() {

					$.validator.addMethod("quesSeqCheck", quesSeqCheck, "");
					$.validator.addMethod("quesSeqValidation",
							quesSeqValidation, "");
					$.validator.addMethod("CurrentDateCheck", CurrentDateCheck,
							"");
					$.validator.addMethod("startDateCheck", startDateCheck, "");
					$.validator.addMethod("endDateCheck", endDateCheck, "");
					$.validator.addMethod("datesChecking", datesChecking, "");
					$.validator.addMethod("dateComparison", dateComparison, "");
					$.validator.addMethod("dateAndTimeEquityChecking", dateAndTimeEquityChecking, "");
					
					// validation implementation will go here.
					$("#dailyReportForm")
							.validate(
									{
										rules : {
											"dailyReportsInfo.questionSequences" : {
												quesSeqCheck : true,
												quesSeqValidation : true
											},

											"dailyReportsInfo.currentDate" : {
												CurrentDateCheck : true
											},

											"dailyReportsInfo.fromDate" : {
												startDateCheck : true
											},

											"dailyReportsInfo.toDate" : {
												endDateCheck : true,
												datesChecking : true,
												dateComparison : true
											},
											
											"dailyReportsInfo.endSeconds" : {
												dateAndTimeEquityChecking:true
											}
										},
										messages : {
											"dailyReportsInfo.questionSequences" : {
												quesSeqCheck : QUES_SEQ_ARE_MANDATORY,
												quesSeqValidation : ENTER_VALID_INPUT
											},

											"dailyReportsInfo.currentDate" : {
												CurrentDateCheck : CURRENT_DATE_IS_REQUIRED
											},

											"dailyReportsInfo.fromDate" : {
												startDateCheck : START_DATE_IS_REQUIRED
											},

											"dailyReportsInfo.toDate" : {
												endDateCheck : END_DATE_IS_REQUIRED,
												datesChecking : START_DATE_CANT_GREATER_THAN_END_DATE,
												dateComparison : START_DATE_AND_END_DATE_CANT_GREATER_THAN_CURR_DATE
											},
											
											"dailyReportsInfo.endSeconds" : {
												dateAndTimeEquityChecking:DIFF_COMPARISON_BETWEEN_START_AND_END_TIME
											}

										},
										submitHandler : function(form) {
											form.dailyReportDownload.disabled = true;
											$('#dailyReportDownload').addClass(
													'btn-disabled');
											form.submit();
										}
									});
				});

function quesSeqCheck() {
	var ques_seq = document.getElementById('questionSeq').value;
	var specQuesCount = document.getElementById('specifiedStudQuesCnt');

	if (specQuesCount.checked == true) {
		if (ques_seq == '' || ques_seq.length == 0)
			return false;
		else
			return true;
	}
}

function quesSeqValidation() {
	var ques_seq = document.getElementById('questionSeq').value;
	var regexString = /^(?:\s*\d{5,6}\s*(?:,|$))+$/;
	if (!ques_seq.match(regexString)) {
		return false;
	} else {
		return true;
	}
}

function allQuesCount() {
	document.getElementById("questionSeq").disabled = true;
	document.getElementById("questionSeq").value = "";
	document.getElementById("sysDate").disabled = true;
	document.getElementById("sysDate").value = "";

	$("#dailyReportDownload").prop('disabled', false);
	$("#dailyReportDownload").removeClass("btn-disabled");

	disableFieldsAndRemoveErrorMsg();
}

function specQuesCount() {
	document.getElementById("questionSeq").disabled = false;
	document.getElementById("questionSeq").value = "";
	document.getElementById("sysDate").disabled = true;
	document.getElementById("sysDate").value = "";

	$("#dailyReportDownload").prop('disabled', false);
	$("#dailyReportDownload").removeClass("btn-disabled");

	disableFieldsAndRemoveErrorMsg();

}

function confAndWait() {
	document.getElementById("questionSeq").disabled = true;
	document.getElementById("questionSeq").value = "";
	document.getElementById("sysDate").disabled = true;
	document.getElementById("sysDate").value = "";

	$("#dailyReportDownload").prop('disabled', false);
	$("#dailyReportDownload").removeClass("btn-disabled");

	disableFieldsAndRemoveErrorMsg();
}

function notConfAndWait() {
	document.getElementById("questionSeq").disabled = true;
	document.getElementById("questionSeq").value = "";
	document.getElementById("sysDate").disabled = true;
	document.getElementById("sysDate").value = "";

	$("#dailyReportDownload").prop('disabled', false);
	$("#dailyReportDownload").removeClass("btn-disabled");

	disableFieldsAndRemoveErrorMsg();
}

function enableDisableAndClearFields() {
	document.getElementById("sysDate").disabled = false;
	document.getElementById("questionSeq").disabled = true;
	document.getElementById("questionSeq").value = "";
	document.getElementById("sysDate").value = "";

	$("#dailyReportDownload").prop('disabled', false);
	$("#dailyReportDownload").removeClass("btn-disabled");

	disableFieldsAndRemoveErrorMsg();
}

function CurrentDateCheck() {
	var currDate = document.getElementById('sysDate').value;
	if (currDate == '') {
		return false;
	} else {
		return true;
	}
}

function clearValidationMsg() {
	if ($('#dataNotFound').is(':empty')) {
		$('#dataNotFound').empty();
	} else {
		$('#dataNotFound').empty();
	}
}

function historyRecordCount() {
	var array = [ "startDate", "startHours", "startMinutes", "startSeconds",
			"endDate", "endHours", "endMinutes", "endSeconds", "sysDate",
			"questionSeq" ];
	
	$("#dailyReportDownload").prop('disabled', false);
	$("#dailyReportDownload").removeClass("btn-disabled");
	
	for ( var i = 0; i < array.length; i++) {
		document.getElementById(array[i]).disabled = false;
	}
	
	document.getElementById(array[0]).value = "";
	document.getElementById(array[4]).value = "";
	document.getElementById(array[8]).value = "";
	document.getElementById(array[9]).value = "";
	document.getElementById(array[8]).disabled = true;
	document.getElementById(array[9]).disabled = true;
	
	removeErrorMsgsAndSettingTime();
	clearValidationMsg();
}

function disableFieldsAndRemoveErrorMsg() {
	var array = [ "startDate", "startHours", "startMinutes", "startSeconds",
	  			"endDate", "endHours", "endMinutes", "endSeconds"];
	
	document.getElementById(array[0]).value = "";
	document.getElementById(array[4]).value = "";
	removeErrorMsgsAndSettingTime();
	clearValidationMsg();
	
	for ( var i = 0; i <= 8; i++) {
		document.getElementById(array[i]).disabled = true;
	}
}

function removeErrorMsgsAndSettingTime(){
	$('label[for="startDate"]').remove();
	$('label[for="endDate"]').remove();
	$('label[for="questionSeq"]').remove();
	$('label[for="sysDate"]').remove();
	$('label[for="endSeconds"]').remove();
	
	$('#startHours').val('00');
	$('#startMinutes').val('00');
	$('#startSeconds').val('00');
	$('#endHours').val('00');
	$('#endMinutes').val('00');
	$('#endSeconds').val('00');
}

function startDateCheck() {
	var fromDate = document.getElementById('startDate').value;
	if (fromDate == '') {
		return false;
	} else {
		return true;
	}
}

function endDateCheck() {
	var toDate = document.getElementById('endDate').value;
	if (toDate == '') {
		return false;
	} else {
		return true;
	}
}

function datesChecking() {
	var endDate = document.getElementById('endDate').value;
	var startDate = document.getElementById('startDate').value;
	var sDate = new Date(startDate);
	var eDate = new Date(endDate);

	if (sDate > eDate) {
		return false;
	} else {
		return true;
	}
}

function dateComparison() {
	var fromDate = document.getElementById('startDate').value;
	var toDate = document.getElementById('endDate').value;
	var sDate = new Date(fromDate);
	var eDate = new Date(toDate);
	var currentDate = new Date();

	if (sDate > currentDate || eDate > currentDate) {
		return false;
	} else {
		return true;
	}
}

function dateAndTimeEquityChecking() {
	var fromDate = document.getElementById('startDate').value;
	var toDate = document.getElementById('endDate').value;
	
	var sDate = new Date(fromDate);
	var eDate = new Date(toDate);
	
	if(fromDate!='' && toDate!=''){
		if (sDate.getTime() == eDate.getTime() || eDate.getTime() > sDate.getTime()) {
			return timechecking();
		}
    }
}

function timechecking(){
	var fromDate = document.getElementById('startDate').value;
	var toDate = document.getElementById('endDate').value;
	
	var fromHours = document.getElementById("startHours").value;
	var fromMinutes = document.getElementById("startMinutes").value;
	var fromSeconds=document.getElementById("startSeconds").value;
	
	var toHours = document.getElementById("endHours").value;
	var toMinutes = document.getElementById("endMinutes").value;
	var toSeconds=document.getElementById("endSeconds").value;
		
	var date1 = fromDate + " " + fromHours + ":" + fromMinutes + ":" + fromSeconds;
	var date2 = toDate + " " + toHours + ":" + toMinutes + ":" + toSeconds;
	
	var fromDateObj = new Date(date1);
	var toDateObj = new Date(date2);
	var timeDifference = toDateObj - fromDateObj;

	if (timeDifference <= 86400000) {
		return true;
	}else{
		return false;
	}
}
