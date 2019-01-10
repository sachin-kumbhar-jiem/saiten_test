<html>
<head>
<meta http-equiv="Content-Language" content="ja">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="Content-Script-Type" content="text/javascript">

<title>Inspection Operation｜Rating System</title>
<style>
.box {
	width: 40%;
	margin: 0 auto;
	background: rgba(255, 255, 255, 0.2);
	padding: 35px;
	border: 2px solid #fff;
	border-radius: 20px/50px;
	background-clip: padding-box;
	text-align: center;
}

.button {
	font-size: 1em;
	padding: 10px;
	color: #fff;
	border: 2px solid #06D85F;
	border-radius: 20px/50px;
	text-decoration: none;
	cursor: pointer;
	transition: all 0.3s ease-out;
}
/*.button:hover {
  background: #06D85F;
}
*/
.overlay {
	position: fixed;
	top: 0;
	bottom: 0;
	left: 0;
	right: 0;
	background: rgba(0, 0, 0, 0.7);
	transition: opacity 500ms;
	visibility: hidden;
	opacity: 0;
}

.overlay:target {
	visibility: visible;
	opacity: 1;
}

.popup {
	margin: 30px auto;
	padding: 20px;
	background: #fff;
	border-radius: 5px;
	width: 34%;
	position: relative;
	transition: all 5s ease-in-out;
}

.popup h2 {
	margin-top: 0;
	color: #333;
	font-family: Tahoma, Arial, sans-serif;
}

.popup .close {
	position: absolute;
	top: 20px;
	right: 30px;
	transition: all 200ms;
	font-size: 30px;
	font-weight: bold;
	text-decoration: none;
	color: #333;
}

.popup .close:hover {
	color: #000;
}

.popup .content {
	max-height: 70%;
	overflow: auto;
}

@media screen and (max-width: 700px) {
	.box {
		width: 70%;
	}
	.popup {
		width: 70%;
	}
}
</style>
<script>
	var questionType = '1';
	function imageClicked(){
		
	document.getElementById("studentId").innerHTML="newtext";
	document.getElementById("answerImagePop").src="E://pp.jpg";
	
	}
	
	
	
</script>

<script type="text/javascript"
	src="./material/scripts/js/enlarge_image.js"></script>
<script type="text/javascript"
	src="./material/scripts/jQuery/jquery.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="./material/css/jquery.highlight-within-textarea.css">
<script type="text/javascript"
	src="./material/scripts/js/forward_dialog.js"></script>
<script type="text/javascript"
	src="./material/scripts/js/list_action.js"></script>
<script type="text/javascript"
	src="./material/scripts/js/chbox_action.js"></script>
<script type="text/javascript" src="./material/scripts/js/default.js"></script>
<script type="text/javascript"
	src="./material/scripts/jQuery/jquery.validate.js"></script>
<script type="text/javascript" src="./material/scripts/js/script.js"></script>
<script type="text/javascript" src="./material/scripts/js/scoring.js"></script>
<script type="text/javascript"
	src="./material/scripts/js/modalPopLite.js"></script>
<script type="text/javascript"
	src="./material/scripts/js/jquery3_2_1.min.js"></script>
<script type="text/javascript">
	var jQuery_3_2_1 = $.noConflict(true);
</script>
<script type="text/javascript"
	src="./material/scripts/js/jquery.highlight-within-textarea.js"></script>
<html>
<script>
	LOGIN_REQUIRED = 'Please enter Login ID';
	LOGIN_ID_LENGTH_7 = 'Valid length for Login ID is 7 characters';
	LOGIN_ID_ALPHA_NUMERIC_AND_SYMBOLS = 'Only Alphanumeric character and symbols can be used in Login ID.';
	PASSWORD_REQUIRED = 'Please specify password';
	PASSWORD_LENGTH_4_256 = 'Please enter password having minimum length 4 and maximum length 256';
	PASSWORD_ALPHA_NUMERIC_AND_SYMBOLS = 'Only Alphanumeric character and symbols can be used in Password.';
	SELECT_RECORD_TO_DELETE = 'Please select at least one record';
	INTERNAL_SERVER_ERROR = 'Internal application error';
	SELECT_ATLEAST_ONE_RECORD = 'Please select at least one record';

	//reference Sampling Search Screen
	REQUIRED = 'Mandetory';
	NUMERIC_ONLY = 'Numeric only';
	ALPHA_NUMERIC_ONLY = 'Alphanumeric only';
	SCORE_PERCENTAGE_RANGE_VALUES_REQUIRED = 'Please enter range values';
	SCORE_PERCENTAGE_RANGE_INVALID = 'Start range should not be greater than End range '
	SCORE_PERCENTAGE_MAX_RANGE_VALUE_EXCEEDED = 'Range Values must be between 0 to 100 '
	MAX_LENGTH_11 = 'Maximum allowed length is 11 characters';
	MAX_LENGTH_6 = 'Maximum allowed length is 6 characters';
	MAX_LENGTH_7 = 'Maximum allowed length is 7 characters';
	MAX_LENGTH_CHECKPONTS_5 = 'Maximum length for each SKP is 5 digits';
	MAX_LENGTH_PENDING_CATEGORY_6 = 'Maximum length for each pending category is 5 digits';
	MAX_LENGTH_DENY_CATEGORY_5 = 'Maximum length for each deny category is 5 digits';
	MAX_LENGTH_13 = 'Maximum allowed length is 13 characters';
	MIN_LENGTH_7 = 'Minimum allowed length is 11 characters';
	SELECT_GRADE = 'Select at least one Grade, if [Grade Available] is selected';
	SELECT_EVENT = 'Please select atleast one past rating operation';
	SELECT_STAE = 'Please select atleast one latest rating state';
	PENDING_CATEGORY_NUMERIC_AND_COMMA_ALLOWED = 'Only Numbers and commas are allowed in Pending Category';
	INCLUDE_CHECKPOINT_NUMERIC_AND_COMMA_ALLOWED = 'Only Numbers and commas are allowed in [SKP to include]';
	EXCLUDE_CHECKPOINT_NUMERIC_AND_COMMA_ALLOWED = 'Only Numbers and commas are allow in [SKP to exclude]';
	RESULT_COUNT_MINIMUM_RANGE = 'Minimum limit is 1';
	RESULT_COUNT_MAXIMUM_RANGE = 'Maximum limit is 100000';
	QUESTION_NUM_MAXIMUM_RANGE = 'Maximum limit is 32767';
	INVALID_CHECK_POINTS = 'Please do not enter the same SKP in [SKP to include] and [SKP to exclude]';
	PENDING_CATEGORY_REQUIRED = 'Please enter Pending category when  [Pending category] is selected';
	DURATION_CHECK_LEAPYEAR_FROM_TO_DATE = '[From date] and [To date] are invalid';
	DURATION_CHECK_LEAPYEAR_FROM_DATE = '[From date] is invalid';
	DURATION_CHECK_LEAPYEAR_TO_DATE = '[To date] is invalid';
	DURATION_CHECK_DATE = 'Please make sure that [To date] is greater than or equal to [From date]';
	DURATION_CHECK_FEBRUARY_FROM_TO_DATE = 'Both [From date] and [To date] are invalid';
	DURATION_CHECK_FEBRUARY_FROM_DATE = '[From date] is invalid';
	DURATION_CHECK_FEBRUARY_TO_DATE = '[To date] is invalid';
	DENY_CATEGORY_REQUIRED = 'Please enter Deny category when  [Deny category] is selected.';
	DENY_CATEGORY_NUMERIC_AND_COMMA_ALLOWED = 'Only Numbers and commas are allowed in Deny Category.';

	//special Scoring	
	QUESTION_NO_REQUIRED = 'Question Number is Required Field For [Blind] ,[Language Support] Type Of Answers';
	ANSWER_FORM_NO_REQUIRED = 'Student ID is required field for [Enlarged] and [OMR Read Fail] Type Of Answers';
	QUESTION_NO_INVALID = 'Invalid Question number';

	//check scroring validation
	TYPE_4_SELECTED_THEN_OTHER_TYPE_NOT_SELECTED = 'You cannot select check points of other group when following checkpoints are selected:';
	SELECT_AT_LIST_ONE_FROM = 'Please select atleast one checkpoint from following ';
	SELECT_MAX_ONE_FROM = 'Please select only one checkpoint from following:';

	//modalPopLite js messages
	APPROVE = 'Approve';
	DENY = 'Deny';
	APPROVE_HEADING = 'Do you want to approve?';
	DENY_HEADING = 'Do you want to deny?';

	//scoring.js
	EACH = 'Each';
	OR_ONE_OUT_OF = 'Or one out of following';
	SELECT_SINGLE_RECORD_FROM_THEM = 'Select single check point from the following set:';
	CHECKBOX_NOT_SELECT_FOR_PENDING = 'Check point can not be selected when pending button is clicked';

	var WritingQuestionWise = 'Writing-QuestionWise';
	var WritingScorerWise = 'Writing-ScorerWise';
	var SpeakingQuestionWise = 'Speaking-QuestionWise';
	var SpeakingScorerWise = 'Speaking-ScorerWise';

	var REQUIRED = 'Required';
	var NUMERIC_ONLY = 'Allowed Numeric Only';
	var MAX_LENGTH_11 = 'Valid lengths are 11 characters';
	var QUESTION_NUM_MAXIMUM_RANGE = 'The maximum limit is 32767';
	var QUESTION_NO_INVALID = 'Question Number is invalid';
	var DURATION_CHECK_LEAPYEAR_FROM_TO_DATE = 'Invaild Start And End Date';
	var DURATION_CHECK_LEAPYEAR_FROM_DATE = 'Start Date Is Invaild';
	var DURATION_CHECK_LEAPYEAR_TO_DATE = 'End Date Is Invalid';
	var DURATION_CHECK_DATE = 'Start Date Must Be Same Or Less Than End Date';
	var DURATION_CHECK_FEBRUARY_FROM_TO_DATE = 'Start And End Date is Invaild';
	var DURATION_CHECK_FEBRUARY_FROM_DATE = 'Start Date Is Invaild';
	var DURATION_CHECK_FEBRUARY_TO_DATE = 'End Date Is Invaild';
	var ENTER_FROM_DATE_BEFORE_TODAY = 'From Date must less than Current Date';
	var ENTER_TO_DATE_BEFORE_TODAY = 'To Date must less than Current Date';

	//Daily Status Report
	var DAILY_STATUS_REPORT_COMMON_TITLE_FIRST_TIME_SCORING = '1st Rating';
	var DAILY_STATUS_REPORT_COMMON_TITLE_SECOND_TIME_SCORING = '2nd Rating';
	var DAILY_STATUS_REPORT_COMMON_TITLE_PENDING_SCORING = 'Pending Script Rating';
	var DAILY_STATUS_REPORT_COMMON_TITLE_CHEKING_SCORING = 'Checking Operation';
	var DAILY_STATUS_REPORT_COMMON_TITLE_OUTOFBOUNDARY = 'Out Of Boundary Rating';
	var DAILY_STATUS_REPORT_COMMON_TITLE_NOGRADE = 'No Grade Rating';
	var DAILY_STATUS_REPORT_COMMON_TITLE_MISMATCH_SCORING = 'Discrepancy Rating ';
	var DAILY_STATUS_REPORT_COMMON_TITLE_INSPECTION_SCORING = 'Inspection Operation';
	var DAILY_STATUS_REPORT_COMMON_TITLE_DENY_SCORING = 'Deny Rating';
	var DAILY_STATUS_REPORT_COMMON_TITLE_SAMPLING_SCORING = 'Script Search';
	var DAILY_STATUS_REPORT_COMMON_TITLE_FORCED_SCORING = 'Forced Rating';
	//var DAILY_STATUS_REPORT_COMMON_TITLE_BATCH_SCORING='Batch Rating'; 
	var DAILY_STATUS_REPORT_COMMON_TITLE = 'EN';

	//look afterwards.
	var MARK_lOOK_AFTERWARDS = 'Mark';
	var UNMARK_LOOK_AFTERWARDS = 'Unmark';

	// Daily Score Report 
	var START_DATE_REQUIRED = 'Start Date is Required';
	var END_DATE_REQUIRED = 'End Date is Required';
	var START_AND_END_DATE_COMPARISON = 'End Date should be greater than Start Date';
	var TIME_COMPARISON = 'Ending Time should be greater than Starting Time';
	var START_DATE_IS_GREATER_THAN_CURRENT_DATE = 'Start Date should not be greater than Todays date';
	var END_DATE_IS_GREATER_THAN_CURRENT_DATE = 'End Date should not be greater than Todays date';
	var REPORT_CANNOT_BE_DOWNLOADED = 'Report cannot be downloaded. Please select atleast one option';

	//Daily report download
	var QUES_SEQ_ARE_MANDATORY = 'Question sequences are mandatory';
	var ENTER_VALID_INPUT = 'Please Enter valid inputs';
	var CURRENT_DATE_IS_REQUIRED = 'Please Select the Date';

	var START_DATE_IS_REQUIRED = 'start date is required';
	var END_DATE_IS_REQUIRED = 'end date is required';
	var START_DATE_CANT_GREATER_THAN_END_DATE = 'start date should not be greater than end date';
	var START_DATE_AND_END_DATE_CANT_GREATER_THAN_CURR_DATE = 'either start date or end date should not be greater than current date';
	var DIFF_COMPARISON_BETWEEN_START_AND_END_TIME = 'difference between start time and end time should be less than or equal to 24 hrs';
</script>
</html>

<script type="text/javascript"
	src="./material/scripts/js/answerImagePopup.js"></script>
<script type="text/javascript">
	var shortcutkeysmap = '96:0,97:1,98:2,99:3,100:4,101:5,102:6,103:7,104:8,107:21,109:22,110:23,105:24';
	var shortcutkeys = shortcutkeysmap.split(",");
	var arr = [];
	for (var i = 0; i < shortcutkeys.length; i++) {
		shortcut = shortcutkeys[i].split(":");
		var key = shortcut[0];
		var value = shortcut[1];
		arr[key] = value;
	}

	function openHelpDocWindow() {
		var pdfWindow = window
				.open("showPdfDoc.action?docType=helpdoc",
						"Rating｜Rating System",
						"status = 1, height = 500, width = 500, location=no, scrollbars=no");

		//var pdfWindow = window.open("material/documents/manual/skp30_k001_0419.pdf","Rating｜Rating System","status = 1, height = 500, width = 500, location=no, scrollbars=no");
		/* var pdfWindow = window.open("./material/documents/manual/skp30_k001_0419.pdf","Rating｜Rating System","status = 1, height = 500, width = 500, location=no, scrollbars=no");
		pdfWindow.document.write('<html><head><title>Help Manual｜Rating System</title></head>');
		pdfWindow.document.write('</html>');
		 pdfWindow.document.close(); */
	}
	function openManualDocWindow() {
		var pdfWindow = window
				.open("showPdfDoc.action?docType=manualdoc",
						"Rating｜Rating System",
						"status = 1, height = 500, width = 500, location=no, scrollbars=no");
		/*  var pdfWindow = window.open("./material/documents/manual/skp30_k001_0419.pdf","Rating｜Rating System","status = 1, height = 500, width = 500, location=no, scrollbars=no");
		 pdfWindow.document.write('<html><head><title>Detail Manual｜Rating System</title></head>');
		 pdfWindow.document.write('</html>');
		  pdfWindow.document.close(); */
	}
	/* document.getElementById('playButton').onclick = function() { document.getElementById('myTuneObj').play() };
	document.getElementById('pauseButton').onclick = function() { document.getElementById('myTuneObj').pause() };
	document.getElementById('stopButton').onclick = function() { document.getElementById('myTuneObj').stop() }; */
</script>
<script language="JavaScript">
	javascript: window.history.forward(1);
</script>
<script type="text/javascript" language="javascript">
	jQuery_3_2_1(document).ready(function($) {
		var duplicateWords = '';
		$('.HighLightText').highlightWithinTextarea({
			highlight : [ duplicateWords.split(", ") ]
		});
	});
</script>
<link href="./material/css/modalPopLite.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css" href="./material/css/import.css"
	media="all">

<style type="text/css">
label {
	width: auto;
	float: none;
	color: red;
	display: block;
}

td {
	border: 1px solid #000;
}
}
</style>
</head>








<body class="wg-body-color" id="body" onload="onloadScoringScreen();">
<html>
<script>
	LOGIN_REQUIRED = 'Please enter Login ID';
	LOGIN_ID_LENGTH_7 = 'Valid length for Login ID is 7 characters';
	LOGIN_ID_ALPHA_NUMERIC_AND_SYMBOLS = 'Only Alphanumeric character and symbols can be used in Login ID.';
	PASSWORD_REQUIRED = 'Please specify password';
	PASSWORD_LENGTH_4_256 = 'Please enter password having minimum length 4 and maximum length 256';
	PASSWORD_ALPHA_NUMERIC_AND_SYMBOLS = 'Only Alphanumeric character and symbols can be used in Password.';
	SELECT_RECORD_TO_DELETE = 'Please select at least one record';
	INTERNAL_SERVER_ERROR = 'Internal application error';
	SELECT_ATLEAST_ONE_RECORD = 'Please select at least one record';

	//reference Sampling Search Screen
	REQUIRED = 'Mandetory';
	NUMERIC_ONLY = 'Numeric only';
	ALPHA_NUMERIC_ONLY = 'Alphanumeric only';
	SCORE_PERCENTAGE_RANGE_VALUES_REQUIRED = 'Please enter range values';
	SCORE_PERCENTAGE_RANGE_INVALID = 'Start range should not be greater than End range '
	SCORE_PERCENTAGE_MAX_RANGE_VALUE_EXCEEDED = 'Range Values must be between 0 to 100 '
	MAX_LENGTH_11 = 'Maximum allowed length is 11 characters';
	MAX_LENGTH_6 = 'Maximum allowed length is 6 characters';
	MAX_LENGTH_7 = 'Maximum allowed length is 7 characters';
	MAX_LENGTH_CHECKPONTS_5 = 'Maximum length for each SKP is 5 digits';
	MAX_LENGTH_PENDING_CATEGORY_6 = 'Maximum length for each pending category is 5 digits';
	MAX_LENGTH_DENY_CATEGORY_5 = 'Maximum length for each deny category is 5 digits';
	MAX_LENGTH_13 = 'Maximum allowed length is 13 characters';
	MIN_LENGTH_7 = 'Minimum allowed length is 11 characters';
	SELECT_GRADE = 'Select at least one Grade, if [Grade Available] is selected';
	SELECT_EVENT = 'Please select atleast one past rating operation';
	SELECT_STAE = 'Please select atleast one latest rating state';
	PENDING_CATEGORY_NUMERIC_AND_COMMA_ALLOWED = 'Only Numbers and commas are allowed in Pending Category';
	INCLUDE_CHECKPOINT_NUMERIC_AND_COMMA_ALLOWED = 'Only Numbers and commas are allowed in [SKP to include]';
	EXCLUDE_CHECKPOINT_NUMERIC_AND_COMMA_ALLOWED = 'Only Numbers and commas are allow in [SKP to exclude]';
	RESULT_COUNT_MINIMUM_RANGE = 'Minimum limit is 1';
	RESULT_COUNT_MAXIMUM_RANGE = 'Maximum limit is 100000';
	QUESTION_NUM_MAXIMUM_RANGE = 'Maximum limit is 32767';
	INVALID_CHECK_POINTS = 'Please do not enter the same SKP in [SKP to include] and [SKP to exclude]';
	PENDING_CATEGORY_REQUIRED = 'Please enter Pending category when  [Pending category] is selected';
	DURATION_CHECK_LEAPYEAR_FROM_TO_DATE = '[From date] and [To date] are invalid';
	DURATION_CHECK_LEAPYEAR_FROM_DATE = '[From date] is invalid';
	DURATION_CHECK_LEAPYEAR_TO_DATE = '[To date] is invalid';
	DURATION_CHECK_DATE = 'Please make sure that [To date] is greater than or equal to [From date]';
	DURATION_CHECK_FEBRUARY_FROM_TO_DATE = 'Both [From date] and [To date] are invalid';
	DURATION_CHECK_FEBRUARY_FROM_DATE = '[From date] is invalid';
	DURATION_CHECK_FEBRUARY_TO_DATE = '[To date] is invalid';
	DENY_CATEGORY_REQUIRED = 'Please enter Deny category when  [Deny category] is selected.';
	DENY_CATEGORY_NUMERIC_AND_COMMA_ALLOWED = 'Only Numbers and commas are allowed in Deny Category.';

	//special Scoring	
	QUESTION_NO_REQUIRED = 'Question Number is Required Field For [Blind] ,[Language Support] Type Of Answers';
	ANSWER_FORM_NO_REQUIRED = 'Student ID is required field for [Enlarged] and [OMR Read Fail] Type Of Answers';
	QUESTION_NO_INVALID = 'Invalid Question number';

	//check scroring validation
	TYPE_4_SELECTED_THEN_OTHER_TYPE_NOT_SELECTED = 'You cannot select check points of other group when following checkpoints are selected:';
	SELECT_AT_LIST_ONE_FROM = 'Please select atleast one checkpoint from following ';
	SELECT_MAX_ONE_FROM = 'Please select only one checkpoint from following:';

	//modalPopLite js messages
	APPROVE = 'Approve';
	DENY = 'Deny';
	APPROVE_HEADING = 'Do you want to approve?';
	DENY_HEADING = 'Do you want to deny?';

	//scoring.js
	EACH = 'Each';
	OR_ONE_OUT_OF = 'Or one out of following';
	SELECT_SINGLE_RECORD_FROM_THEM = 'Select single check point from the following set:';
	CHECKBOX_NOT_SELECT_FOR_PENDING = 'Check point can not be selected when pending button is clicked';

	var WritingQuestionWise = 'Writing-QuestionWise';
	var WritingScorerWise = 'Writing-ScorerWise';
	var SpeakingQuestionWise = 'Speaking-QuestionWise';
	var SpeakingScorerWise = 'Speaking-ScorerWise';

	var REQUIRED = 'Required';
	var NUMERIC_ONLY = 'Allowed Numeric Only';
	var MAX_LENGTH_11 = 'Valid lengths are 11 characters';
	var QUESTION_NUM_MAXIMUM_RANGE = 'The maximum limit is 32767';
	var QUESTION_NO_INVALID = 'Question Number is invalid';
	var DURATION_CHECK_LEAPYEAR_FROM_TO_DATE = 'Invaild Start And End Date';
	var DURATION_CHECK_LEAPYEAR_FROM_DATE = 'Start Date Is Invaild';
	var DURATION_CHECK_LEAPYEAR_TO_DATE = 'End Date Is Invalid';
	var DURATION_CHECK_DATE = 'Start Date Must Be Same Or Less Than End Date';
	var DURATION_CHECK_FEBRUARY_FROM_TO_DATE = 'Start And End Date is Invaild';
	var DURATION_CHECK_FEBRUARY_FROM_DATE = 'Start Date Is Invaild';
	var DURATION_CHECK_FEBRUARY_TO_DATE = 'End Date Is Invaild';
	var ENTER_FROM_DATE_BEFORE_TODAY = 'From Date must less than Current Date';
	var ENTER_TO_DATE_BEFORE_TODAY = 'To Date must less than Current Date';

	//Daily Status Report
	var DAILY_STATUS_REPORT_COMMON_TITLE_FIRST_TIME_SCORING = '1st Rating';
	var DAILY_STATUS_REPORT_COMMON_TITLE_SECOND_TIME_SCORING = '2nd Rating';
	var DAILY_STATUS_REPORT_COMMON_TITLE_PENDING_SCORING = 'Pending Script Rating';
	var DAILY_STATUS_REPORT_COMMON_TITLE_CHEKING_SCORING = 'Checking Operation';
	var DAILY_STATUS_REPORT_COMMON_TITLE_OUTOFBOUNDARY = 'Out Of Boundary Rating';
	var DAILY_STATUS_REPORT_COMMON_TITLE_NOGRADE = 'No Grade Rating';
	var DAILY_STATUS_REPORT_COMMON_TITLE_MISMATCH_SCORING = 'Discrepancy Rating ';
	var DAILY_STATUS_REPORT_COMMON_TITLE_INSPECTION_SCORING = 'Inspection Operation';
	var DAILY_STATUS_REPORT_COMMON_TITLE_DENY_SCORING = 'Deny Rating';
	var DAILY_STATUS_REPORT_COMMON_TITLE_SAMPLING_SCORING = 'Script Search';
	var DAILY_STATUS_REPORT_COMMON_TITLE_FORCED_SCORING = 'Forced Rating';
	//var DAILY_STATUS_REPORT_COMMON_TITLE_BATCH_SCORING='Batch Rating'; 
	var DAILY_STATUS_REPORT_COMMON_TITLE = 'EN';

	//look afterwards.
	var MARK_lOOK_AFTERWARDS = 'Mark';
	var UNMARK_LOOK_AFTERWARDS = 'Unmark';

	// Daily Score Report 
	var START_DATE_REQUIRED = 'Start Date is Required';
	var END_DATE_REQUIRED = 'End Date is Required';
	var START_AND_END_DATE_COMPARISON = 'End Date should be greater than Start Date';
	var TIME_COMPARISON = 'Ending Time should be greater than Starting Time';
	var START_DATE_IS_GREATER_THAN_CURRENT_DATE = 'Start Date should not be greater than Todays date';
	var END_DATE_IS_GREATER_THAN_CURRENT_DATE = 'End Date should not be greater than Todays date';
	var REPORT_CANNOT_BE_DOWNLOADED = 'Report cannot be downloaded. Please select atleast one option';

	//Daily report download
	var QUES_SEQ_ARE_MANDATORY = 'Question sequences are mandatory';
	var ENTER_VALID_INPUT = 'Please Enter valid inputs';
	var CURRENT_DATE_IS_REQUIRED = 'Please Select the Date';

	var START_DATE_IS_REQUIRED = 'start date is required';
	var END_DATE_IS_REQUIRED = 'end date is required';
	var START_DATE_CANT_GREATER_THAN_END_DATE = 'start date should not be greater than end date';
	var START_DATE_AND_END_DATE_CANT_GREATER_THAN_CURR_DATE = 'either start date or end date should not be greater than current date';
	var DIFF_COMPARISON_BETWEEN_START_AND_END_TIME = 'difference between start time and end time should be less than or equal to 24 hrs';
</script>
</html>
<div>
	<div id="wrapper">
		<div id="contents" class="text14">
			<div></div>
			<form name="scoreActionForm" id="scoreActionForm" method="post">
				<input type="hidden" name="" value="" id="answerSeq" />




				<!-- updated table  -->
				<table
					style="background-color: white; height: 100%; width: 100%; border: 1px solid #000;">
					<tr>
						<td rowspan=3 class="table-side-menu-td" width="6%"
							style="text-align: center;">
							<div id="side_menu">
								<!-- 1st td -->
								<div class="side_menu_top_margin">
									<p class="side_menu_heading_color">
										WG<br> wg00001<br />
									<p class="side_menu_alignment">Menu
									<div class="green_menu">
										<!-- 3rd td -->



										<input type="button" name="submit" value="Main Menu"
											class="menu_button"
											onclick="javascript:location.href='showSaitenMenu.action';" />




										<a href="/saiten/historyListing.action" class="menu_button">History
											List</a>
										<p>
											<a href="/saiten/bookmarkListing.action" class="menu_button">Bookmark
												List</a>
										<p>




											<input type="button" name="submit" value="Logout"
												class="menu_button"
												onclick="javascript:location.href='logOut.action';" />
									</div>

								</div>
								<br />
								<div class="green_menu">
									<p class="menu_button_without_cursor">Question Number</p>
									<p class="menu_button_without_cursor"
										style="width: 80px; word-break: break-all;">
										<span class="menu_button_without_cursor">国-1</span>
									</p>
								</div>
								<br />
								<div class="green_menu">

									<p class="menu_button_without_cursor">Scored Answers</p>
									<p class="menu_button_without_cursor">0</p>
								</div>
							</div>

						</td>
						<td style="background-color: #4a6c9a; height: 25px;"></td>
						<td style="background-color: #4a6c9a; height: 25px;">41sth</td>
					</tr>
					<tr>




						<td colspan=2
							style="text-align: left; vertical-align: top; padding: 0">
							<table style="margin-left: 5px;" id="imageDisplay">
								<tbody>

									<tr>

										<td style="border-color: green; width: 150px; height: 300px;"
											align="center">
											<div class="box">
												<a class="button" href="#popup1"><img id="answerImage1"
													name="answerImage1"
													src='C:\Users\Shailendra\Downloads\665838-dawood970.jpg'
													alt="Zoom In・Zoom Out" style="vertical-align: bottom;"
													width="100%" height="100%"></a>
											</div>

										</td>

										<td style="border-color: green; width: 150px; height: 300px;"
											align="center">
											<div class="box">
												<a class="button" href="#popup1"><img id="answerImage1"
													name="answerImage1"
													src='http://52.194.111.254/saiten-images/processed-files/10010-A-1/A1011522619.jpeg'
													alt="Zoom In・Zoom Out" style="vertical-align: bottom;"
													width="100%" height="100%" onclick="imageClicked()"></a>
											</div>
										</td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
					<tr>
						<td class="big_result_blue">Grade： 1 &nbsp; Result： ◎</td>
						<td class="big_result_blue">
							<button type="submit" id="approve" name="approve" value="Submit"
								class="btn btn-primary btn-scoring-sm"
								style="width: 99px; height: 39px;" accesskey="s">
								Approve</button>

						</td>
					</tr>
				</table>
				<input type="hidden" name="struts.token.name" value="token" /> <input
					type="hidden" name="token" value="ALA2O8XJQQYRUTKS5H15JN7AEYK5ZBY2" />
			</form>

			<div id="popup1" class="overlay">
				<div class="popup">
					<div class="div_center_heading">

						<div class="box_content_left_pane_input_normal">

							<div class="div_center_heading">
								<span class="table_center_heading"> Student ID&nbsp;
									[<span id="studentId"></span>] &nbsp;&nbsp; | <a href="#"
									onclick="openPaperDialog()">Entire Image</a> |


								</span>


							</div>
						</div>

						<div id="magnifier1">
							<!-- <a href="#" id="close" onclick="applyOverflowAuto();"><img src="./material/img/close.png" align="right"></a> -->
							<a href="#" id="zoomOut" onclick="dropImage()"
								style="color: black;"><img
								src="./material/img/magnifier_zoom_out.png" class="border">&nbsp;Zoom
								Out</a> <a href="#" id="zoomIn" onclick="enlargeImage()"
								style="color: black;"><img
								src="./material/img/magnifier_zoom_in.png" class="border">&nbsp;Zoom
								In</a> <a href="#" id="original_size"
								onclick="setImageToDefaultSize()" style="color: black;"><img
								src="./material/img/default1.png" class="border">&nbsp;Default
								size</a>
						</div>
						<br> <a class="close" href="#">&times;</a>
						<div class="content">
							<img id="answerImagePop" name="answerImage1"
								src='http://52.194.111.254/saiten-images/processed-files/10010-A-1/A1011522619.jpeg'
								alt="Zoom In・Zoom Out" style="vertical-align: bottom;"
								width="100%" height="100%">
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>
</div>


</body>
</html>