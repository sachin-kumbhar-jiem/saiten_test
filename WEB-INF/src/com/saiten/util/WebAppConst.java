package com.saiten.util;


/**
 * @author sachin
 * @version 1.0
 * @created 06-Dec-2012 2:32:49 PM
 */
public interface WebAppConst {

	public static final String SAITEN_MASTERDB_URL = "saiten.masterdb.url";

	public static final String SAITEN_COMMONDB_URL = "saiten.commondb.url";

	public static final Character DELETE_FLAG = 'F';

	public static final Character VALID_FLAG = 'T';

	public static final Character ENABLE = 'T';

	public static final Character F = 'F';

	public static final Character D = 'D';

	public static final Character S = 'S';

	public static final Character HYPHEN = '-';

	public static final Character FAIL = '×';

	public static final Character DOUBLE_CIRCLE = '◎';

	public static final Character SINGLE_CIRCLE = '○';

	public static final int ANSWER_RECORD_FETCH_SIZE = 1;

	public static final Character LOCK = 'T';

	public static final Character UNLOCK = 'F';

	public static final Character DOT = '.';

	public static final String COMMA = ",";

	public static final String COLON = ":";

	public static final Character NO_DB_UPDATE_FALSE = 'F';

	public static final Character NO_DB_UPDATE_TRUE = 'T';

	public static final Character BOOKMARK_FLAG_TRUE = 'T';

	public static final Character BOOKMARK_FLAG_FALSE = 'F';

	public static final Character[] SCORE_TYPE = { '0', '1', '2', '4' };

	public static final Character[] QUESTION_TYPE = { '1', '2', '3', '4' };

	public static final Character SCORE_FLAG = 'T';

	public static final int LOGIN_ATTEMPT = 1;

	public static final String SCORER_ROLE = "採点者";

	public static final String SV_ROLE = "監督者";

	public static final String WG_ROLE = "WG";

	public static final String ADMIN_ROLE = "管理者";

	public static final String FIRST_SCORING_MENU_ID = "FIRST_SCORING_MENU_ID";

	public static final String FIRST_SCORING_QUALITY_CHECK_MENU_ID = "FIRST_SCORING_QUALITY_CHECK_MENU_ID";

	public static final String SECOND_SCORING_MENU_ID = "SECOND_SCORING_MENU_ID";

	public static final String CHECKING_MENU_ID = "CHECKING_MENU_ID";

	public static final String DENY_MENU_ID = "DENY_MENU_ID";

	public static final String PENDING_MENU_ID = "PENDING_MENU_ID";

	public static final String INSPECTION_MENU_ID = "INSPECTION_MENU_ID";

	public static final String MISMATCH_MENU_ID = "MISMATCH_MENU_ID";

	public static final String NO_GRADE_MENU_ID = "NO_GRADE_MENU_ID";

	public static final String OUT_BOUNDARY_MENU_ID = "OUT_BOUNDARY_MENU_ID";

	public static final String REFERENCE_SAMP_MENU_ID = "REFERENCE_SAMP_MENU_ID";

	public static final String SCORE_SAMP_MENU_ID = "SCORE_SAMP_MENU_ID";

	public static final String SPECIAL_SAMP_INSPECTION_MENU_ID = "SPECIAL_SAMP_INSPECTION_MENU_ID";

	public static final String FORCED_MENU_ID = "FORCED_MENU_ID";

	public static final String STATE_TRAN_MENU_ID = "STATE_TRAN_MENU_ID";

	public static final String SPECIAL_SCORING_BLIND_TYPE_MENU_ID = "SPECIAL_SCORING_BLIND_TYPE_MENU_ID";

	public static final String SPECIAL_SCORING_LANGUAGE_SUPPORT_MENU_ID = "SPECIAL_SCORING_LANGUAGE_SUPPORT_MENU_ID";

	public static final String SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID = "SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID";

	public static final String SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID = "SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID";

	public static final String DAILY_SCORING_REPORT_MENU_ID = "DAILY_SCORING_REPORT_MENU_ID";

	public static final int SCORER_ROLE_ID = 1;

	public static final int SV_ROLE_ID = 2;

	public static final int WG_ROLE_ID = 3;

	public static final int ADMIN_ROLE_ID = 4;

	public static final int ZERO = 0;

	public static final int ONE = 1;

	public static final String SIDE_NINETY_EIGHT = "98";

	public static final String SIDE_NINETY_NINE = "99";

	public static final Short MINUS_ONE = -1;

	public static final String MENUID_SCORING_STATE_DATA = "menuid.scoringstate.data";

	public static final String MENUID_SCORING_EVENT_DATA = "menuid.scoringevent.data";

	public static final String SAITEN_SCREEN_ID_DATA = "saiten.screenid.data";

	public static final String SHORTCUTKEYS_DATA = "checkpoints.shortcutkeys.data";

	public static final String HISTORY_SCORINGSTATE_MAP_DATA = "history.scoringstate.map.data";

	public static final int AUTHENTICATION_ERROR_CODE = 401;

	public static final int AUTHENTICATION_SUCCESS_CODE = 200;

	public static final String QUESTION_NUM_VALID = "valid";

	public static final String QUESTION_NUM_INVALID = "invalid";

	public static final boolean TRUE = true;

	public static final boolean FALSE = false;

	public static final String BOOKMARK_PAGESIZE = "bookmark.pagesize";

	public static final String HISTORY_PAGESIZE = "history.pagesize";

	public static final String DAILY_STATUS_REPORT_PAGESIZE = "dailyStatusSearch.report.pagesize";

	public static final String FORCED_AND_STATETRANSITION_LIST_PAGESIZE = "forcedAndStateTransition.listpage.pagesize";

	public static final String INCORRECT_USERID_PASSWORD = "error.incorrect.userId.or.password";

	public static final String GRADE_NUM_TEXT = "label.gradeNum";

	public static final String REFERENCE_SAMPLING_SCREEN_ID = "REFERENCE_SAMPLING_SCREEN_ID";

	public static final String SCORE_SAMPLING_SCREEN_ID = "SCORE_SAMPLING_SCREEN_ID";

	public static final String FORCE_SAMPLING_SCREEN_ID = "FORCE_SAMPLING_SCREEN_ID";

	public static final String STATE_TRANSITION_SCREEN_ID = "STATE_TRANSITION_SCREEN_ID";

	public static final int CALENDAR_FIRST_MONTH = 1;

	public static final int CALENDAR_LAST_MONTH = 12;

	public static final String APPROVE = "approve";

	public static final String DENY = "deny";

	public static final int CALENDAR_FIRST_DAY = 1;

	public static final int CALENDAR_LAST_DAY = 31;

	public static final int CLOCK_FIRST_HOUR = 0;

	public static final int CLOCK_LAST_HOUR = 23;

	public static final int CLOCK_FIRST_MINUTE = 0;

	public static final int CLOCK_LAST_MINUTE = 59;

	public static final Short[] MISMATCH_STATES = { 171, 172, 173, 372, 373 };

	public static final Short[] APROVE_STATES = { 144, 344, 154, 354 };

	public static final Short[] DENY_STATES = { 145, 345, 155, 355 };

	public static final Short[] ANSWER_PAPER_LINK_ENABLE_STATES = { 192, 193,
			392, 393, 182, 183, 382, 383, 252, 254, 256, 258, 452, 454, 456,
			458 };

	public static final Short[] SPECIAL_SCORING_STATES = { 251, 252, 253, 254,
			255, 256, 257, 258, 452, 454, 456, 458 };

	public static final Short[] SPECIAL_SCORING_AND_BATCH_STATES = { 31, 51,
			52, 53, 59, 61, 101 };

	public static final Short[] DUMMY_SCORING_STATES = { 322, 323, 332, 333,
			344, 345, 354, 355, 362, 363, 392, 393, 372, 373, 312, 313, 382,
			383, 421, 422, 441, 442, 452, 454, 456, 458 };

	public static final Short NO_GRADE_SCORING_STATE = 211;

	public static final Short[] SCORE_SAMPLING_CURRENT_STATES = { 131, 141,
			151, 500, 501, 161 };

	public static final Short[] STATE_TRANSITION_CURRENT_STATES = { 141,
			500, 501 };

	public static final Short[] FORCED_SCORING_CURRENT_STATES = { 21, 121, 131,
			141, 151, 161, 171, 181, 191, 211, 251, 253, 255, 257, 500, 501 };

	public static final Character[] SCORE_SAMPLING_FLAG_LIST = { 'F', 'T' };

	public static final String FORCED_SCORING_NOT_SCORED_STATES = "21,121,161,171,181,251,253,255,257";

	public static final Short[] NOT_SCORED_STATES_ARRAY = { 21, 121, 161, 171,
			181, 251, 253, 255, 257 };

	public static final String RANDOM_NUMBER = "label.randomNumber";

	public static final String ORDER_BY_ATTEMPT1 = "attempt1";

	public static final String ORDER_BY_ATTEMPT2 = "attempt2";

	public static final String ORDER_BY_ATTEMPT3 = "attempt3";

	public static final int FIRSTTIME_SCORED_STATE = 122;

	public static final int SECONDTIME_SCORED_STATE = 132;

	public static final String SUBJECT_LIST = "subjectList";

	public static final String SUBJECT_WISE_QUESTION_LIST = "subjectWiseQuestionList";

	public static final String WG_REGISTRATION = "WGRegistration";

	public static final String MST_SCORER_QUESTION_MAP = "mstScorerQuestionMap";

	public static final String ERROR_SERVICE_ID_INVALID = "error.serviceIdInvalid";

	public static final String ERROR_MASTERDATA_ID_INVALID = "error.masterDataIdInvalid";

	public static final String ERROR_WG_REGISTRATION = "error.wg.registration";

	public static final String ERROR_SAITENSHA_KANTOKUSHA_REGISTRATION = "error.saitensha.kantokusha.registration";

	public static final String ERROR_GET_SUBJECTLIST = "error.get.subjectlist";

	public static final String ERROR_GET_SUBJECTWISE_QUESTIONLIST = "error.get.subjectwise.questionlist";

	public static final String ERROR_ADMIN_REGISTRATION = "error.admin.registration";

	public static final String ERROR_KENKYUSHA_REGISTRATION = "error.kenkyusha.registration";

	public static final String SCORER_WISE_QUESTIONSMAP = "scorerWiseQuestionsMap";

	public static final Character SHORT_TYPE = '1';

	public static final Character LONG_TYPE = '2';

	public static final Character SPEAKING_TYPE = '3';

	public static final Character WRITING_TYPE = '4';

	public static final Character SHORT_SCREEN_OBJECTIVE_TYPE = '5';

	public static final Character LONG_SCREEN_OBJECITVE_TYPE = '6';

	public static final Character BATCH_SCORING_TYPE = '7';

	public static final Integer SHORT_TYPE_EVAL_SEQ = 2;

	public static final Integer LONG_TYPE_EVAL_SEQ = 3;

	public static final Integer SHORT_SCREEN_OBJECTIVE_EVAL_SEQ = 4;

	public static final Integer LONG_SCREEN_OBJECTIVE_EVAL_SEQ = 5;

	public static final Integer BATCH_SCORING_EVAL_SEQ = 6;

	public static final Integer SPEAKING_TYPE_EVAL_SEQ = 1;

	public static final Integer WRITTING_TYPE_EVAL_SEQ = 2;

	public static final String ERROR_WG_USER_WITH_DUMMY_ROLE = "error.wgUserWithDummyRole";

	public static final Character QUALITY_MARK_FLAG_TRUE = 'T';

	public static final Character QUALITY_MARK_FLAG_FALSE = 'F';

	public static final String ROLL_ID_FOR_DAILY_STATUS_SEARCH_SCREEN = "daily.status.search.roll.id";

	public static final String ROLL_ID_FOR_DAILY_STATUS_SEARCH_SCREEN_DEFAULT_SELECTED = "daily.status.search.default.selected.roll";

	public static final String QUESTIONWISE_SEARCH = "QUESTIONWISE_SEARCH";

	public static final String SCORERWISE_SEARCH = "SCORERWISE_SEARCH";

	public static final String DAILY_STATUS_REPORT_MENU_ID = "DAILY_STATUS_REPORT";

	public static final String SAITENSHA_REGISTRATION = "SaitenshaRegistration";

	public static final String ADMIN_REGISTRATION = "AdminRegistration";

	public static final String KENKYUSHA_REGISTRATION = "KenkyushaRegistration";

	public static final Integer IS_KANTOKUSHA = 1;

	public static final String ADMINISTRATOR = "administrator";

	public static final String USER_REGISTRATION_UPDATE_PERSON_ID = "SaitLMS";

	public static final String SAITEN_LMS_INDEX_PAGE_URL = "saitenlms.indexpage.url";

	public static final String GLOBAL_PROPERTIES = "global";

	public static final Character[] QUALITY_MARK_FLAG_LIST = { 'T', 'F' };

	public static final Short BATCH_EVENT_ID = 80;

	public static final String ERROR_NO_QUESTIONS_ALLOCATED = "error.no.question.allocated";

	public static final String ERROR_NO_GRADE_AVAILABLE_FOR_SELECTED_QUESTION = "error.no.grade.available.for.selected.question";

	public static final String ERROR_NO_INSPECTION_GROUP_SEQ_AVAILABLE_FOR_SELECTED_QUESTION = "error.no.inspection.group.seq.available.for.selected.question";

	public static final String ERROR_NO_PENDING_CATEGORY_AVAILABLE_FOR_SELECTED_QUESTION = "error.no.pending.category.available.for.selected.question";

	public static final String LOGIN_PAGE_URL = "loginpage.url";

	public static final String IS_SAITEN_LOGIN_FUNCTINALITY_ENABLED = "isEnabledSaitenLoginFunctinality";

	public static final Short[] MISMATCH_PREVIOUS_SCORING_STATES = { 122, 132,
			162, 212 };

	public static final Short[] FIRST_AND_SECOND_TIME_SCORING_STATES = { 122,
			132 };

	public static final String[] SCORER_LOGGING_STATUS = { "LOGGED_IN",
			"SCREEN_LOGOUT", "TIMEOUT_LOGOUT", "DUPLICATE_LOGOUT" };

	public static final String ERROR_NO_MARKVALUES_AVAILABLE = "error.no.markvalue.available";

	public static final String OTHERS = "markvalue.noselection.or.othersselection";

	public static final Short[] MV_SELECTION_STATES = { 121, 131, 161, 171,
			181, 211, 251, 253, 255, 257 };

	public static final Short[] DENY_PREVIOUS_SCORING_STATES = { 122, 132, 162,
			172, 241, 144, 154, 192, 212, 221 };

	public static final int TWO = 2;

	public static final Short[] DENY_SCORING_STATES = { 191, 192, 193, 392, 393 };

	public static final Character[] ANSWER_PAPER_TYPES = { '1', '2', '3' };

	public static final Short[] PENDING_CATEGORIES = { 97, 98 };

	public static final String QUESTION_TYPE_BATCH_SCORING = "label.dailyStatusReport.questionType.batch";

	public static final String QUESTION_TYPE_SHORT_SCREEN = "label.dailyStatusReport.questionType.short";

	public static final String QUESTION_TYPE_LONG_SCREEN = "label.dailyStatusReport.questionType.long";

	public static final String QUESTION_TYPE_SHORT_SCREEN_OBJECTIVE = "label.dailyStatusReport.questionType.shortscreenobjective";

	public static final String QUESTION_TYPE_LONG_SCREEN_OBJECTIVE = "label.dailyStatusReport.questionType.longscreenobjective";

	public static final String QUESTION_TYPE_WRITTING = "label.dailyStatusReport.questionType.writing";

	public static final String QUESTION_TYPE_SPEAKING = "label.dailyStatusReport.questionType.speaking";

	public static final String ANSWER_IMAGE_URL = "saiten.answerimage.url";

	public static final String SHIFT_JIS = "Shift_JIS";

	public static final String SAITEN_RELEASE = "saiten.release";

	public static final String SHINEIGO_RELEASE = "shineigo.release";

	public static final String APPLICATION_PROPERTIES_FILE = "application";

	public static final String CRLF = "\r\n";

	public static final String CSV_FILE_EXTENSION = ".csv";

	public static final String ZIP_FILE_EXTENSION = ".zip";

	public static final String FILE_ENCODING = "UTF-8";

	public static final String SINGLE_SPACE = " ";

	public static final String TILDE = "~";

	public static final String UNDERSCORE = "_";

	public static final String EMPTY_STRING = "";

	public static final String HYPHEN_STRING = "-";

	public static final String PERCENTAGE_FORMAT = "%1$.2f";

	public static final Character QUES_TYPE_SPEAKING = 'S';

	public static final Character QUES_TYPE_WRITING = 'W';

	public static final String MP3_FORMAT = "%3";

	public static final String TXT_FORMAT = "%txt";

	public static final String BATCH_SCORER_ID = "BAT001";

	public static final Short[] SUMMARY_DISCR_REPORT_STATES = { 122, 132, 162,
			212 };

	public static final String FIRST_RTG_SECOND_WAIT = "%122,131%";

	public static final String FIRST_RTG_PENDING = "%123,161%";

	public static final String SECOND_RTG_COMPLETE = "%132,500%";

	public static final String SECOND_RTG_PENDING = "%133,161%";

	public static final String SECOND_RTG_MISMATCH = "%132,171%";

	public static final String MISMATCH_RTG_PENDING = "%173,161%";

	public static final String MISMATCH_RTG_COMPLETE = "%172,500%";

	public static final String PENDING_RTG_SECOND_WAIT = "%162,131%";

	public static final String PENDING_RTG_PENDING = "%163,161%";

	public static final String PENDING_RTG_COMPLETE = "%162,500%";

	public static final String PENDING_RTG_MISMATCH = "%162,171%";

	public static final String PEND_CATE_01 = "%01";

	public static final String PEND_CATE_02 = "%02";

	public static final String PEND_CATE_03 = "%03";

	public static final String PEND_CATE_04 = "%04";

	public static final String PEND_CATE_05 = "%05";

	public static final String FIRST_RTG_SECOND_WAIT_KEY = "dailyScoring.report.title.first.rating.second.wait";

	public static final String FIRST_RTG_PENDING_KEY = "dailyScoring.report.title.first.rating.pending";

	public static final String SECOND_RTG_COMPLETE_KEY = "dailyScoring.report.title.second.rating.complete";

	public static final String SECOND_RTG_PENDING_KEY = "dailyScoring.report.title.second.rating.pending";

	public static final String SECOND_RTG_MISMATCH_KEY = "dailyScoring.report.title.second.rating.discrepancy";

	public static final String PENDING01_RTG_SECOND_WAIT_KEY = "dailyScoring.report.title.pending01.rating.second.wait";

	public static final String PENDING01_RTG_PENDING_KEY = "dailyScoring.report.title.pending01.rating.pending";

	public static final String PENDING01_RTG_COMPLETE_KEY = "dailyScoring.report.title.pending01.rating.complete";

	public static final String PENDING01_RTG_MISMATCH_KEY = "dailyScoring.report.title.pending01.rating.discrepancy";

	public static final String PENDING02_RTG_SECOND_WAIT_KEY = "dailyScoring.report.title.pending02.rating.second.wait";

	public static final String PENDING02_RTG_PENDING_KEY = "dailyScoring.report.title.pending02.rating.pending";

	public static final String PENDING02_RTG_COMPLETE_KEY = "dailyScoring.report.title.pending02.rating.complete";

	public static final String PENDING02_RTG_MISMATCH_KEY = "dailyScoring.report.title.pending02.rating.discrepancy";

	public static final String PENDING03_RTG_SECOND_WAIT_KEY = "dailyScoring.report.title.pending03.rating.second.wait";

	public static final String PENDING03_RTG_PENDING_KEY = "dailyScoring.report.title.pending03.rating.pending";

	public static final String PENDING03_RTG_COMPLETE_KEY = "dailyScoring.report.title.pending03.rating.complete";

	public static final String PENDING03_RTG_MISMATCH_KEY = "dailyScoring.report.title.pending03.rating.discrepancy";

	public static final String PENDING04_RTG_SECOND_WAIT_KEY = "dailyScoring.report.title.pending04.rating.second.wait";

	public static final String PENDING04_RTG_PENDING_KEY = "dailyScoring.report.title.pending04.rating.pending";

	public static final String PENDING04_RTG_COMPLETE_KEY = "dailyScoring.report.title.pending04.rating.complete";

	public static final String PENDING04_RTG_MISMATCH_KEY = "dailyScoring.report.title.pending04.rating.discrepancy";

	public static final String DEFECTIVE_SCRIPT_COUNT_KEY = "dailyScoring.report.title.pending04.rating.defective.script.count";

	public static final String PENDING05_RTG_SECOND_WAIT_KEY = "dailyScoring.report.title.pending05.rating.second.wait";

	public static final String PENDING05_RTG_PENDING_KEY = "dailyScoring.report.title.pending05.rating.pending";

	public static final String PENDING05_RTG_COMPLETE_KEY = "dailyScoring.report.title.pending05.rating.complete";

	public static final String PENDING05_RTG_MISMATCH_KEY = "dailyScoring.report.title.pending05.rating.discrepancy";

	public static final String MISMATCH_RTG_PENDING_KEY = "dailyScoring.report.title.mismatch.rating.pending";

	public static final String MISMATCH_RTG_COMPLETE_KEY = "dailyScoring.report.title.mismatch.rating.complete";

	public static final String TOTAL_RESULT_COUNT_DEFAULT = "1000";

	public static final String SINGLE_DIGIT_RANDOM_NUMBER = "label.singledigit.randomNumber";

	public static final String PUNCH_TEXT_CONDITION_EXACT_MATCH = "punch.text.condition.exact.match";

	public static final String PUNCH_TEXT_CONDITION_FORWORD_MATCH = "punch.text.condition.forword.match";

	public static final String PUNCH_TEXT_CONDITION_BACKWORD_MATCH = "punch.text.condition.backword.match";

	public static final String PUNCH_TEXT_CONDITION_PARTIAL_MATCH = "punch.text.condition.partial.match";

	public static final char PERCENTAGE_CHARACTER = '%';

	public static final char ESCAPE_CHARACTER = '!';

	public static final String ERROR_NO_DENY_CATEGORY_AVAILABLE_FOR_SELECTED_QUESTION = "error.no.deny.category.available.for.selected.question";

	public static final int KENSHU_ROLE_ID = 6;

	public static final String KENSHU_SAMPLING_MENU_ID = "KENSHU_SAMPLING_MENU_ID";

	public static final String OPENING_BRACKET = "(";

	public static final String CLOSING_BRACKET = ")";

	public static final int INSPECTION_STATE = 151;

	public static final String ACCEPTANCE_DISPLAY = "ACCEPTANCE DISPLAY";

	public static final String KENSHU_SAMPLING_SEARCH = "KENSHU_SAMPLING_SEARCH";

	public static final String KENSHU_SEARCH_ALL = "label.kenshu.search.all";

	public static final String KENSHU_SEARCH_EXPLAINED = "label.kenshu.explain.flag";

	public static final String KENSHU_SEARCH_UNEXPLAINED = "label.kenshu.search.unexplained";

	public static final Short[] KENSU_RECORDS_STATES = { 141, 151, 500, 501 };

	public static final Character UNDER_SCORE = '_';

	public static final String KENSHU_SEARCH_EXPLAINED_STRING = "EXPLAINED";

	public static final String KENSHU_SEARCH_UNEXPLAINED_STRING = "UNEXPLAINED";

	public static final Short[] CONFIRM_AND_INSPECTION_WAIT_STATES = { 500, 151 };

	public static final String DAILY_REPORT_MENU_ID = "DAILY_REPORT_MENU_ID";

	public static final String SPECIFIC_STUD_QUES_COUNT = "specifiedStudQuesCnt";

	public static final String STUD_COUNT_FOR_ALL_QUES = "studCountForAllQues";

	public static final String CONFIRM_AND_WAIT_STATE = "confirmAndWait";

	public static final String NOT_CONFIRM_AND_WAIT_STATE = "notConfirmAndWait";

	public static final String LOGIN_LOGOUT_REPORT = "loginLogoutReport";

	public static final String TOTAL_RESULT_COUNT_SET_DEFAULT = "100";

	public static final String SKP_AND_CONDITION = "skpAndCondition";

	public static final String SKP_OR_CONDITION = "skpORCondition";

	public static final String TXT_FILE_EXTENSION = ".txt";
	
	public static final String GRADE_WISE_DETAILS_REPORT="gradeWiseReport";
	
	public static final String MARK_VALUE_WISE_DETAILS_REPORT="markValueWiseReport";
	
	public static final String PENDING_CATEGORY_WISE_DETAILS_REPORT="pendingCategoryWiseReport";
	
	public static final Character TAB_CHARACTER = '\t';
	
	public static final String STUD_COUNT_FOR_ALL_QUES_FOR_WG_ONLY = "studCountForAllQuesForWgOnly";
	
	public static final String REPLICATION_ON = "replication.on";
	
    public static final String GRADEWISE_COUNT_WHERE_GRADE_IS_AVAILABLE = "gradeWiseCountGradeIsAvailable";

	public static final String QUES_SEQ_WISE_COUNT_WHERE_PENDING_CATEGORY_IS_SET = "pendingCategoryIsSet";
	
	public static final String QUESTION_WISE_COUNT_FOR_HISTORY_RECORDS = "historyRecordsCount";
}
