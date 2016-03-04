$(document).ready(function(){
	
	if($("#kenshuSamplingSearch").is(':checked') ) {
		changeAcceptanceDisplayBlock ();
	}
	
	if($("#acceptanceDisplayRadio").is(':checked') ) {
		changeKenshuSamplingBlock ();
		if($("input:radio[id=subjectCodeA]:checked").length == 0){
			$("input:radio[id=subjectCodeA]:first").attr('checked', true);
		}
	   if($("input:radio[id=recordSearchCriteria]:checked").length == 0){
			$("input:radio[id=recordSearchCriteria]:first").attr('checked', true);
		}
	}
	
	if($("#kenshuSamplingSearch").is(':unchecked') && $("#acceptanceDisplayRadio").is(':unchecked')) {
		 $("input:radio[id=kenshuSamplingSearch]:first").attr('checked', true);
		 changeAcceptanceDisplayBlock ();
		 if($("input:radio[id=subjectCode]:checked").length == 0){
				$("input:radio[id=subjectCode]:first").attr('checked', true);
			}
	 }
	
	
	$('#kenshuSamplingSearch').click(function() {
		   if($('#kenshuSamplingSearch').is(':checked')) { 
			   changeAcceptanceDisplayBlock ();
			   if($("input:radio[id=subjectCode]:checked").length == 0){
					$("input:radio[id=subjectCode]:first").attr('checked', true);
				}
		   }
		});
	 
	 $('#acceptanceDisplayRadio').click(function() {
		   if($('#acceptanceDisplayRadio').is(':checked')) { 
			   changeKenshuSamplingBlock ();
			   if($("input:radio[id=subjectCodeA]:checked").length == 0){
					$("input:radio[id=subjectCodeA]:first").attr('checked', true);
				}
			   if($("input:radio[id=recordSearchCriteria]:checked").length == 0){
					$("input:radio[id=recordSearchCriteria]:first").attr('checked', true);
				}
		   }
		});
	 
	 
	$.validator.addMethod("questionNumValidaton", isQuestionNumValid, "");
	$.validator.addMethod("numericOnly", numericOnlyValidation, "");
	
	$("#kenshuSamplingForm").validate({
		rules: {	
			"kenshuSamplingInfo.questionNum" : {
				required: true,
				numericOnly: true,
				maxlength: 5,
				//questionNumMaxValidRange: true,
				questionNumValidaton: true
			},
			"kenshuSamplingInfo.resultCount" : {
				required: true,
				numericOnly: true
			},
			"acceptanceDisplayInfo.questionNum" : {
				required: true,
				questionNumValidaton: true
			},
			"acceptanceDisplayInfo.kenshuUserId" : {
				required: true
			}
		},
		 messages: {
				"kenshuSamplingInfo.questionNum" : {
					required: REQUIRED,
					numericOnly: NUMERIC_ONLY,
					maxlength: MAX_LENGTH_11,
					//questionNumMaxValidRange: QUESTION_NUM_MAXIMUM_RANGE,
					questionNumValidaton: QUESTION_NO_INVALID
		        },
		        "kenshuSamplingInfo.resultCount" : {
		        	required: REQUIRED,
		        	numericOnly: NUMERIC_ONLY
		        },
		        "acceptanceDisplayInfo.questionNum" : {
		        	required: REQUIRED,
		        	questionNumValidaton: QUESTION_NO_INVALID
		        },
		        "acceptanceDisplayInfo.kenshuUserId" : {
		        	required: REQUIRED
		        }
		 }
		
	});

	$('#resultCount').bind('input', function () {
	     $(this).val($(this).val().replace(/[^0-9]/g, ''));
	});
	
	$('#questionNum').bind('input', function () {
	     $(this).val($(this).val().replace(/[^0-9]/g, ''));
	});
	
	$('#questionNumA').bind('input', function () {
	     $(this).val($(this).val().replace(/[^0-9]/g, ''));
	});
	
	
	$('#questionNum').bind('keypress', function (e) {
		var regex = new RegExp("^[0-9]+$");
	    var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
	    if (regex.test(str)) {
	        return true;
	    }
	    return false;
	});
	
	$('#resultCount').bind('keypress', function (e) {
		var regex = new RegExp("^[0-9]+$");
	    var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
	    if (regex.test(str)) {
	        return true;
	    }
	    return false;
	});
	
	$('#questionNumA').bind('keypress', function (e) {
		var regex = new RegExp("^[0-9]+$");
	    var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
	    if (regex.test(str)) {
	        return true;
	    }
	    return false;
	});
	
});


function changeAcceptanceDisplayBlock () {
	var kenshuSampling = document.getElementsByName('kenshuSamplingSearch');
	var acceptanceSearch = document.getElementsByName('acceptanceDisplayRadio');
	var acceptanceSubjectCode = document.getElementsByName('acceptanceDisplayInfo.subjectCode');
	var acceptanceQuestionNum = document.getElementsByName('acceptanceDisplayInfo.questionNum');
	var acceptanceKenshuUserId = document.getElementsByName('acceptanceDisplayInfo.kenshuUserId');
	var acceptanceSearchCriteria = document.getElementsByName('acceptanceDisplayInfo.recordSearchCriteria');
	
	var kenshuSubjectCode = document.getElementsByName('kenshuSamplingInfo.subjectCode');
	var kenshuQuestionNum = document.getElementsByName('kenshuSamplingInfo.questionNum');
	var kenshuRecordCount = document.getElementsByName('kenshuSamplingInfo.resultCount');
	
	
	if (kenshuSampling[0].checked) {
		
		var i;
		for (i=0; i<kenshuSubjectCode.length; i++) {
			kenshuSubjectCode[i].disabled = false;
		}
		kenshuQuestionNum[0].disabled = false;
		kenshuRecordCount[0].disabled = false;
		
		
		acceptanceSearch[0].checked = false;

		for (i=0; i<acceptanceSubjectCode.length; i++) {
			acceptanceSubjectCode[i].checked = false;
			acceptanceSubjectCode[i].disabled = true;
		}
		
		acceptanceQuestionNum[0].disabled = true;
		acceptanceQuestionNum[0].value = "";
		acceptanceKenshuUserId[0].disabled = true;
		acceptanceKenshuUserId[0].value = "";
		
		for (i=0; i<acceptanceSearchCriteria.length; i++) {
			acceptanceSearchCriteria[i].checked = false;
			acceptanceSearchCriteria[i].disabled = true;
		}
	}
}

function changeKenshuSamplingBlock () {
	var kenshuSampling = document.getElementsByName('kenshuSamplingSearch');
	var acceptanceSearch = document.getElementsByName('acceptanceDisplayRadio');
	var kenshuSubjectCode = document.getElementsByName('kenshuSamplingInfo.subjectCode');
	var kenshuQuestionNum = document.getElementsByName('kenshuSamplingInfo.questionNum');
	var kenshuRecordCount = document.getElementsByName('kenshuSamplingInfo.resultCount');
	
	var acceptanceSubjectCode = document.getElementsByName('acceptanceDisplayInfo.subjectCode');
	var acceptanceQuestionNum = document.getElementsByName('acceptanceDisplayInfo.questionNum');
	var acceptanceKenshuUserId = document.getElementsByName('acceptanceDisplayInfo.kenshuUserId');
	var acceptanceSearchCriteria = document.getElementsByName('acceptanceDisplayInfo.recordSearchCriteria');
	
	var userID = document.getElementsByName('userID');
	
	
	
	if (acceptanceSearch[0].checked) {
		kenshuSampling[0].checked = false;
		var i;
		for (i=0; i<kenshuSubjectCode.length; i++) {
			kenshuSubjectCode[i].checked = false;
			kenshuSubjectCode[i].disabled = true;
		}
		kenshuQuestionNum[0].disabled = true;
		kenshuQuestionNum[0].value = "";
		kenshuRecordCount[0].disabled = true;
		kenshuRecordCount[0].value = "";
		
		for (i=0; i<acceptanceSubjectCode.length; i++) {
			//acceptanceSubjectCode[i].checked = false;
			acceptanceSubjectCode[i].disabled = false;
		}
		
		acceptanceQuestionNum[0].disabled = false;
		acceptanceKenshuUserId[0].disabled = false;
		
		if (acceptanceKenshuUserId[0].value == "") {
			acceptanceKenshuUserId[0].value = userID[0].value;
		}
		
		for (i=0; i<acceptanceSearchCriteria.length; i++) {
			//acceptanceSearchCriteria[i].checked = false;
			acceptanceSearchCriteria[i].disabled = false;
		}
	}
}