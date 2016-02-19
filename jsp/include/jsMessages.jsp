<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<script>
LOGIN_REQUIRED= '<s:text name="error.loginid.required"/>';
LOGIN_ID_LENGTH_7= '<s:text name="error.loginid.length"/>';
LOGIN_ID_ALPHA_NUMERIC_AND_SYMBOLS= '<s:text name="error.loginid.alpha.numeric.symbols"/>';
PASSWORD_REQUIRED= '<s:text name="error.password.required"/>';
PASSWORD_LENGTH_4_256= '<s:text name="error.password.length"/>';
PASSWORD_ALPHA_NUMERIC_AND_SYMBOLS= '<s:text name="error.password.alpha.numeric.symbols"/>';
SELECT_RECORD_TO_DELETE= '<s:text name="error.select.record.to.delete"/>';
INTERNAL_SERVER_ERROR = '<s:text name="error.internal.server.error"/>';
SELECT_ATLEAST_ONE_RECORD = '<s:text name="error.select.atleast.one.record"/>';
	
//reference Sampling Search Screen
REQUIRED= '<s:text name="error.required"/>';
NUMERIC_ONLY= '<s:text name="error.numeric.only"/>';
ALPHA_NUMERIC_ONLY= '<s:text name="error.alpha.numeric.only"/>';
MAX_LENGTH_11= '<s:text name="error.max.length.11"/>';
MAX_LENGTH_6= '<s:text name="error.max.length.6"/>';
MAX_LENGTH_7= '<s:text name="error.max.length.7"/>';
MAX_LENGTH_CHECKPONTS_5 = '<s:text name="error.max.length.5"/>';
MAX_LENGTH_PENDING_CATEGORY_6 = '<s:text name="error.max.length.pending.category.6"/>';
MAX_LENGTH_DENY_CATEGORY_5 = '<s:text name="error.max.length.deny.category.5"/>';
MAX_LENGTH_13= '<s:text name="error.max.length.13"/>';
MIN_LENGTH_7= '<s:text name="error.min.length.7"/>';
SELECT_GRADE= '<s:text name="error.select.grade"/>';
SELECT_EVENT= '<s:text name="error.select.event"/>';
SELECT_STAE= '<s:text name="error.select.state"/>';
PENDING_CATEGORY_NUMERIC_AND_COMMA_ALLOWED= '<s:text name="error.pending.category.numeric.and.comma.allowed"/>';
INCLUDE_CHECKPOINT_NUMERIC_AND_COMMA_ALLOWED= '<s:text name="error.include.checkpoint.numeric.and.comma.allowed"/>';
EXCLUDE_CHECKPOINT_NUMERIC_AND_COMMA_ALLOWED= '<s:text name="error.exclude.checkpoint.numeric.and.comma.allowed"/>';
RESULT_COUNT_MINIMUM_RANGE= '<s:text name="error.result.count.min.range"/>';
RESULT_COUNT_MAXIMUM_RANGE= '<s:text name="error.result.count.max.range"/>';
QUESTION_NUM_MAXIMUM_RANGE= '<s:text name="error.question.num.max.range"/>';
INVALID_CHECK_POINTS= '<s:text name="error.invalid.check.points"/>';
PENDING_CATEGORY_REQUIRED= '<s:text name="error.pending.category.required"/>';
DURATION_CHECK_LEAPYEAR_FROM_TO_DATE= '<s:text name="error.duration.check.leapyear.from.to.date"/>';
DURATION_CHECK_LEAPYEAR_FROM_DATE= '<s:text name="error.duration.check.leapyear.from.date"/>';
DURATION_CHECK_LEAPYEAR_TO_DATE= '<s:text name="error.duration.check.leapyear.to.date"/>';
DURATION_CHECK_DATE= '<s:text name="error.duration.check.date"/>';
DURATION_CHECK_FEBRUARY_FROM_TO_DATE= '<s:text name="error.duration.check.feb.from.to.date"/>';
DURATION_CHECK_FEBRUARY_FROM_DATE= '<s:text name="error.duration.check.feb.from.date"/>';
DURATION_CHECK_FEBRUARY_TO_DATE= '<s:text name="error.duration.check.feb.to.date"/>';
DENY_CATEGORY_REQUIRED= '<s:text name="error.deny.category.required"/>';
DENY_CATEGORY_NUMERIC_AND_COMMA_ALLOWED= '<s:text name="error.deny.category.numeric.and.comma.allowed"/>';
	
//special Scoring	
QUESTION_NO_REQUIRED= '<s:text name="error.question.num.required"/>';
ANSWER_FORM_NO_REQUIRED= '<s:text name="error.answer.form.num.required"/>';
QUESTION_NO_INVALID= '<s:text name="error.question.num.invalid"/>';
	
//check scroring validation
TYPE_4_SELECTED_THEN_OTHER_TYPE_NOT_SELECTED = '<s:text name="error.type.4.selected.then.other.type.not.selected"/>';
SELECT_AT_LIST_ONE_FROM = '<s:text name="error.select.atleast.one.from"/>';
SELECT_MAX_ONE_FROM = '<s:text name="error.select.max.one.from"/>';

//modalPopLite js messages
APPROVE = '<s:text name="error.approve"/>';
DENY = 	'<s:text name="error.deny"/>';
APPROVE_HEADING = '<s:text name="error.approve.heading"/>';
DENY_HEADING = '<s:text name="error.deny.heading"/>';

//scoring.js
EACH='<s:text name="scoring.js.each"/>';
OR_ONE_OUT_OF='<s:text name="scoring.js.or.one.out.of"/>';
SELECT_SINGLE_RECORD_FROM_THEM='<s:text name="scoring.js.select.single.record.from.them"/>';
CHECKBOX_NOT_SELECT_FOR_PENDING='<s:text name="scoring.js.checkbox.can.not.select.for.pending"/>';

var WritingQuestionWise= '<s:text name="label.dailyStatusReport.searchButton.writing.questionWise" />';
var WritingScorerWise= '<s:text name="label.dailyStatusReport.searchButton.writing.scorerWise" />';
var SpeakingQuestionWise= '<s:text name="label.dailyStatusReport.searchButton.speaking.questionWise" />';
var SpeakingScorerWise= '<s:text name="label.dailyStatusReport.searchButton.speaking.scorerWise" />';

var REQUIRED= '<s:text name="common.property.error.required" />';
var NUMERIC_ONLY= '<s:text name="common.property.error.numeric.only" />';
var MAX_LENGTH_11= '<s:text name="common.property.error.max.length.11" />';
var QUESTION_NUM_MAXIMUM_RANGE= '<s:text name="common.property.error.question.num.max.range" />';
var QUESTION_NO_INVALID=  '<s:text name="common.property.error.question.num.invalid" />';
var DURATION_CHECK_LEAPYEAR_FROM_TO_DATE= '<s:text name="common.property.error.duration.check.leapyear.from.to.date" />';
var	DURATION_CHECK_LEAPYEAR_FROM_DATE= '<s:text name="common.property.error.duration.check.leapyear.from.date" />';
var	DURATION_CHECK_LEAPYEAR_TO_DATE='<s:text name="common.property.error.duration.check.leapyear.to.date" />'; 
var	DURATION_CHECK_DATE= '<s:text name="common.property.error.duration.check.date" />'; 
var	DURATION_CHECK_FEBRUARY_FROM_TO_DATE='<s:text name="common.property.error.duration.check.feb.from.to.date" />'; 
var	DURATION_CHECK_FEBRUARY_FROM_DATE='<s:text name="common.property.error.duration.check.feb.from.date" />'; 
var	DURATION_CHECK_FEBRUARY_TO_DATE='<s:text name="common.property.error.duration.check.feb.to.date" />';  
var ENTER_FROM_DATE_BEFORE_TODAY = '<s:text name="common.property.error.from.date.must.less.than.today" />'; 
var ENTER_TO_DATE_BEFORE_TODAY = '<s:text name="common.property.error.to.date.must.less.than.today" />';   

//Daily Status Report
var DAILY_STATUS_REPORT_COMMON_TITLE_FIRST_TIME_SCORING='<s:text name="dailyStatusSearch.report.scorerwise.common.title.first.time.scoring.total" />';
var DAILY_STATUS_REPORT_COMMON_TITLE_SECOND_TIME_SCORING='<s:text name="dailyStatusSearch.report.scorerwise.common.title.second.time.scoring.total" />';
var DAILY_STATUS_REPORT_COMMON_TITLE_PENDING_SCORING='<s:text name="dailyStatusSearch.report.scorerwise.common.title.pending" />';
var DAILY_STATUS_REPORT_COMMON_TITLE_CHEKING_SCORING = '<s:text name="dailyStatusSearch.report.scorerwise.common.title.cheking" />';
var DAILY_STATUS_REPORT_COMMON_TITLE_OUTOFBOUNDARY = '<s:text name="dailyStatusSearch.report.scorerwise.common.title.outofboundary" />';
var DAILY_STATUS_REPORT_COMMON_TITLE_NOGRADE = '<s:text name="dailyStatusSearch.report.scorerwise.common.title.nograde" />';
var DAILY_STATUS_REPORT_COMMON_TITLE_MISMATCH_SCORING='<s:text name="dailyStatusSearch.report.scorerwise.common.title.mismatch.scoring.total" />';
var DAILY_STATUS_REPORT_COMMON_TITLE_INSPECTION_SCORING='<s:text name="dailyStatusSearch.report.scorerwise.common.title.inespection.menu" />';
var DAILY_STATUS_REPORT_COMMON_TITLE_DENY_SCORING='<s:text name="dailyStatusSearch.report.scorerwise.common.title.deny.scoring.total" />';
var DAILY_STATUS_REPORT_COMMON_TITLE_SAMPLING_SCORING='<s:text name="dailyStatusSearch.report.scorerwise.common.title.scoring.sampling.total" />';
var DAILY_STATUS_REPORT_COMMON_TITLE_FORCED_SCORING='<s:text name="dailyStatusSearch.report.scorerwise.common.title.forced.scoring" />';
//var DAILY_STATUS_REPORT_COMMON_TITLE_BATCH_SCORING='<s:text name="dailyStatusSearch.report.scorerwise.common.title.batch.scoring" />'; 
var DAILY_STATUS_REPORT_COMMON_TITLE='<s:text name="dailyStatusSearch.report.scorerwise.common.title" />';

//look afterwards.
var MARK_lOOK_AFTERWARDS ='<s:text name="label.mark.look_afterwoards" />';
var UNMARK_LOOK_AFTERWARDS ='<s:text name="label.unmark.look_afterwoards" />'; 

// Daily Score Report 
var START_DATE_REQUIRED='<s:text name="error.daily.score.report.start.date"/>';
var END_DATE_REQUIRED='<s:text name="error.daily.score.report.end.date"/>';
var	START_AND_END_DATE_COMPARISON='<s:text name="error.daily.score.report.start.and.end.date.comparison"/>';
var TIME_COMPARISON='<s:text name="error.daily.score.report.start.and.end.time.comparison"/>';
var START_DATE_IS_GREATER_THAN_CURRENT_DATE ='<s:text name="error.start.date.is.greater.than.current.date"/>';
var END_DATE_IS_GREATER_THAN_CURRENT_DATE='<s:text name="error.end.date.is.greater.than.current.date"/>';
var REPORT_CANNOT_BE_DOWNLOADED='<s:text name="error.report.cannot.downloaded"/>';

//Daily report download
var QUES_SEQ_ARE_MANDATORY='<s:text name="error.question.seq.are.mandatory"/>';
var ENTER_VALID_INPUT='<s:text name="error.enter.valid.input"/>';
var CURRENT_DATE_IS_REQUIRED='<s:text name="error.select.the.date"/>';

</script>		
</html>