$(document).ready(function(){

	$('#questionSeq').change(function () {
		 $("#questionSeq").valid();
	   }
    );
	
	$('#sysDate').change(function () {
		 $("#sysDate").valid();
	   }
	);
    
	$.validator.addMethod("quesSeqCheck", quesSeqCheck, "");
	$.validator.addMethod("quesSeqValidation", quesSeqValidation, "");
	$.validator.addMethod("CurrentDateCheck", CurrentDateCheck, "");

    // validation implementation will go here.
	$("#dailyReportForm").validate({
		rules: {
			"dailyReportsInfo.questionSequences" : {
					quesSeqCheck:true,
					quesSeqValidation:true
    		},

			"dailyReportsInfo.currentDate" : {
				    CurrentDateCheck:true
			}
	    },
	    messages: {
	    	"dailyReportsInfo.questionSequences" : {
	            	quesSeqCheck:QUES_SEQ_ARE_MANDATORY,
	            	quesSeqValidation:ENTER_VALID_INPUT
	        },

	        "dailyReportsInfo.currentDate" : {
	        		CurrentDateCheck:CURRENT_DATE_IS_REQUIRED
	        }
	    }
	});
});

function quesSeqCheck(){
	var ques_seq = document.getElementById('questionSeq').value;
	var specQuesCount = document.getElementById('specifiedStudQuesCnt');

	if(specQuesCount.checked==true){
		if(ques_seq=='' || ques_seq.length==0)
			return false;
		else
			return true;
	}
}

function quesSeqValidation(){
	var ques_seq = document.getElementById('questionSeq').value;
	var regexString = /^(?:\s*\d{5,6}\s*(?:,|$))+$/;
	if(!ques_seq.match(regexString)){
		return false;
	}else{
		return true;
	}
}

function allQuesCount(){
	document.getElementById("questionSeq").disabled = true;
	document.getElementById("questionSeq").value = "";
	document.getElementById("sysDate").disabled = true;
	document.getElementById("sysDate").value="";
}

function specQuesCount(){
	document.getElementById("questionSeq").disabled = false;
	document.getElementById("questionSeq").value = "";
	document.getElementById("sysDate").disabled = true;
	document.getElementById("sysDate").value="";
}

function confAndWait(){
	document.getElementById("questionSeq").disabled = true;
	document.getElementById("questionSeq").value = "";
	document.getElementById("sysDate").disabled = true;
	document.getElementById("sysDate").value="";
}

function notConfAndWait(){
	document.getElementById("questionSeq").disabled = true;
	document.getElementById("questionSeq").value = "";
	document.getElementById("sysDate").disabled = true;
	document.getElementById("sysDate").value="";
}

function enableDisableAndClearFields(){
	document.getElementById("sysDate").disabled = false;
	document.getElementById("questionSeq").disabled = true;
	document.getElementById("questionSeq").value = "";
	document.getElementById("sysDate").value="";
}

function CurrentDateCheck() {
	var currDate = document.getElementById('sysDate').value;
	if (currDate == '') {
		return false;
	} else {
		return true;
	}
}

function clearValidationMsg(){
	if( $('#dataNotFound').is(':empty') ) { 
		$('#dataNotFound').empty();
	}else{
		$('#dataNotFound').empty();
	}
}