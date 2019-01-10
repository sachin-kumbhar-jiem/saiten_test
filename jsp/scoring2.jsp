<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
<head>
<meta http-equiv="Content-Language" content="ja">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="Content-Script-Type" content="text/javascript">
	<s:if test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FIRST_SCORING_MENU_ID">
		<title><s:text name="firstTime.scoring.title" /></title>
	</s:if><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@SECOND_SCORING_MENU_ID">
		<title><s:text name="secondTime.scoring.title" /></title>
	</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@CHECKING_MENU_ID">
		<title><s:text name="checking.scoring.title" /></title>
	</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@DENY_MENU_ID">
		<title><s:text name="deny.scoring.title" /></title>
	</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@PENDING_MENU_ID">
		<title><s:text name="pending.scoring.title" /></title>
	</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@INSPECTION_MENU_ID">
		<title><s:text name="inspection.scoring.title" /></title>
	</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@MISMATCH_MENU_ID">
		<title><s:text name="mismatch.scoring.title" /></title>
	</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID">
		<title><s:text name="scoresearch.scoring.title" /></title>
	</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@NO_GRADE_MENU_ID">
		<title><s:text name="nograde.scoring.title" /></title>
	</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@OUT_BOUNDARY_MENU_ID">
		<title><s:text name="outofboundary.scoring.title" /></title>
	</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@SCORE_SAMP_MENU_ID">
		<title><s:text name="scoresampling.scoring.title"/></title>
	</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_BLIND_TYPE_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_LANGUAGE_SUPPORT_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID">
		<title><s:text name="specialscoresearch.scoring.title" /></title>
	</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID">
		<title><s:text name="label.forcedscoring.title" /></title>
	</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FIRST_SCORING_QUALITY_CHECK_MENU_ID">
		<title><s:text name="label.firsttimequalitycheck.title" /></title>
	</s:elseif>
<script>
var questionType = '<s:property value="#session.questionInfo.questionType"/>';
</script>

<script type="text/javascript" src="./material/scripts/js/enlarge_image.js"></script>
<script type="text/javascript" src="./material/scripts/jQuery/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="./material/css/jquery.highlight-within-textarea.css">
<script type="text/javascript" src="./material/scripts/js/forward_dialog.js"></script>
<script type="text/javascript" src="./material/scripts/js/list_action.js"></script>
<script type="text/javascript" src="./material/scripts/js/chbox_action.js"></script>
<script type="text/javascript" src="./material/scripts/js/default.js"></script>
<script type="text/javascript" src="./material/scripts/jQuery/jquery.validate.js" ></script>
<script type="text/javascript" src="./material/scripts/js/script.js"></script>
<script type="text/javascript" src="./material/scripts/js/scoring.js"></script>
<script type="text/javascript" src="./material/scripts/js/modalPopLite.js"></script>
<script type="text/javascript" src="./material/scripts/js/jquery3_2_1.min.js"></script>
<script type="text/javascript">var jQuery_3_2_1 = $.noConflict(true);</script>
<script type="text/javascript" src="./material/scripts/js/jquery.highlight-within-textarea.js"></script>

<jsp:include page="include/jsMessages.jsp"></jsp:include>

<script type="text/javascript" src="./material/scripts/js/answerImagePopup.js"></script>
<script type="text/javascript" src="./material/scripts/js/imagePopUp.js"></script>
<script type="text/javascript">
		
		var shortcutkeysmap = '<s:property value="%{@com.saiten.util.SaitenUtil@getConfigMap().get('checkpoints.numlockshortcutkeys.data')}"/>';
		var shortcutkeys = shortcutkeysmap.split(",");
		var arr = [];
			for (var i = 0; i < shortcutkeys.length; i++)
		{
			shortcut = shortcutkeys[i].split(":");
			var key = shortcut[0];
			var value = shortcut[1];
	 		arr[key] = value;
		} 

        function openHelpDocWindow()
        {
        	var pdfWindow = window.open("showPdfDoc.action?docType=<s:property value='@com.saiten.util.WebAppConst@HELP_DOC' />","<s:text name="scoring.title" />","status = 1, height = 500, width = 500, location=no, scrollbars=no");
        	
        	//var pdfWindow = window.open("material/documents/manual/<s:property value="#session.questionInfo.manualDocument" />","<s:text name="scoring.title" />","status = 1, height = 500, width = 500, location=no, scrollbars=no");
            /* var pdfWindow = window.open("./material/documents/manual/<s:property value="#session.questionInfo.manualDocument" />","<s:text name="scoring.title" />","status = 1, height = 500, width = 500, location=no, scrollbars=no");
            pdfWindow.document.write('<html><head><title><s:text name="label.scoring.helpdocument.title" /></title></head>');
            pdfWindow.document.write('</html>');
             pdfWindow.document.close(); */
        }
        function openManualDocWindow()
        {
        	var pdfWindow = window.open("showPdfDoc.action?docType=<s:property value='@com.saiten.util.WebAppConst@MANUAL_DOC' />","<s:text name="scoring.title" />","status = 1, height = 500, width = 500, location=no, scrollbars=no");
           /*  var pdfWindow = window.open("./material/documents/manual/<s:property value="#session.questionInfo.manualDocument" />","<s:text name="scoring.title" />","status = 1, height = 500, width = 500, location=no, scrollbars=no");
            pdfWindow.document.write('<html><head><title><s:text name="label.scoring.manualdocument.title" /></title></head>');
            pdfWindow.document.write('</html>');
             pdfWindow.document.close(); */
        }
        /* document.getElementById('playButton').onclick = function() { document.getElementById('myTuneObj').play() };
		document.getElementById('pauseButton').onclick = function() { document.getElementById('myTuneObj').pause() };
		document.getElementById('stopButton').onclick = function() { document.getElementById('myTuneObj').stop() }; */
    </script>   
    <script language="JavaScript">
   		javascript:window.history.forward(1);
	</script> 
	<script type="text/javascript" language="javascript">
		jQuery_3_2_1(document).ready(function($){					
				var duplicateWords = '<s:property value="#session.tranDescScoreInfo.duplicateWords"/>';				
			    $('.HighLightText').highlightWithinTextarea({
				    highlight: [
				    	duplicateWords.split(", ")
				    ]
				});
			});
		</script>
<link href="./material/css/modalPopLite.css" rel="stylesheet" type="text/css" />
<link href="./material/css/modelPopUp.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="./material/css/import.css" media="all">
		
<style type="text/css">
 label { width: auto; float: none; color: red;  display: block;   }
 td{border: 1px solid #000;}
 
}
</style>
</head>

<s:if test="#session.scorerInfo.roleId == @com.saiten.util.WebAppConst@SCORER_ROLE_ID">
	<s:set id="bodyColor" name="bodyColor" value="%{'scorer-body-color'}" />
</s:if>
<s:elseif test="#session.scorerInfo.roleId == @com.saiten.util.WebAppConst@SV_ROLE_ID">
	<s:set id="bodyColor" name="bodyColor" value="%{'supervisor-body-color'}" />
</s:elseif>
<s:elseif test="#session.scorerInfo.roleId == @com.saiten.util.WebAppConst@WG_ROLE_ID">
	<s:set id="bodyColor" name="bodyColor" value="%{'wg-body-color'}" />
</s:elseif>
<s:else>
	<s:set id="bodyColor" name="bodyColor" value="%{'admin-body-color'}" />
</s:else>

<body class="<s:property value="%{#bodyColor}"/>" id="body"  onload="onloadScoringScreen();">
<jsp:include page="include/jsMessages.jsp"></jsp:include>
<div>
	<div id="wrapper">
		<div id="contents" class="text14">
		<div></div>
			<form  name="scoreActionForm" id="scoreActionForm" method="post">
					
	<!-- updated table  -->				
	           <table  style="background-color: white;height: 100%;width: 100%;border: 1px solid #000;">
			<tr>
				<td rowspan=3 class="table-side-menu-td" width="6%" style="text-align: center;">
					 <div id="side_menu"> <!-- 1st td --> 
				     <div class="side_menu_top_margin">
					  <p class="side_menu_heading_color">
							<s:property value="#session.scorerInfo.roleDescription" /><br>
							<s:property value="#session.scorerInfo.scorerId" /><br/>
					  <p class="side_menu_alignment"><s:text name="label.menu" />
					   <div class="green_menu"><!-- 3rd td --> 
							
							<s:if  test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID">
								<input type="button" name="submit" value="<s:text name="label.backtosaitenmenu" />" class="menu_button"
								 onclick="javascript:location.href='userMenu.action';" />
							</s:if>
							<s:else>
								<input type="button" name="submit" value="<s:text name="label.backtosaitenmenu" />" class="menu_button"
								 onclick="javascript:location.href='showSaitenMenu.action';" />
							</s:else>
							
							<s:if  test="!(#session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@KENSHU_SAMPLING_MENU_ID)">
								<s:if test="(bookmarkScreenFlag == true || #session.historyScreenFlag == true)&&(#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID)">
									<a href="historyListing.action?forcedScoringFlag=true" class="menu_button"><s:text name="label.historylist" /></a><p>
									<a href="bookmarkListing.action?forcedScoringFlag=true" class="menu_button"><s:text name="label.bookmarklist" /></a><p>	
								</s:if><s:else>
									<a href="<s:url action="historyListing" includeParams="none" />" class="menu_button"><s:text name="label.historylist" /></a><p>
									<a href="<s:url action="bookmarkListing" includeParams="none" />" class="menu_button"><s:text name="label.bookmarklist" /></a><p>
								</s:else>
								
							</s:if>
							
							<input type="button" name="submit" value="<s:text name="label.logout" />" class="menu_button" 
							onclick="javascript:location.href='logOut.action';" />
						   
						</div>
						<s:if test="#session.saitenLoginEnabled != true">
							<br>
							<div class="green_menu">
								<input type="button" name="submit" value="<s:text name="label.backToSaitenLMS" />" class="menu_button" onclick="javascript:location.href='backToSaitenLms.action';" />
							</div>						
						</s:if>
					</div><br/>
					<div class="green_menu" >
					<p class="menu_button_without_cursor" ><s:text name="label.scoring.questionnumber" /></p>
					<p class="menu_button_without_cursor" style="width: 80px; word-break: break-all;" >
						<span class="menu_button_without_cursor"><s:property value="#session.questionInfo.subjectShortName" /></span>
					</p>
				</div>
				<br/>
				<div class="green_menu">
					<s:if test="!(#session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@KENSHU_SAMPLING_MENU_ID)">
						<p class="menu_button_without_cursor"><s:text name="label.scoring.pregrading" /></p>
						<p class="menu_button_without_cursor">
							<s:if test="#session.historyRecordCount != null">
								<s:property value="#session.historyRecordCount" /><s:text name="label.scoring.pregradingcount" />
							</s:if>
							<s:else>
								<s:property value="#session.questionInfo.historyRecordCount" /><s:text name="label.scoring.pregradingcount" />
							</s:else>
						</p>
					</s:if>
					<s:if test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SCORE_SAMP_MENU_ID 
					|| #session.questionInfo.menuId == @com.saiten.util.WebAppConst@KENSHU_SAMPLING_MENU_ID">
						<p class="menu_button_without_cursor"><s:text name="label.scoresearch.referencesamplingrecordcount" /></p>
						<p class="menu_button_without_cursor">
							<s:if test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID">
								<s:property value="#session.questionInfo.prevRecordCount + 1" /><s:text name="label.forwardslash" /><s:property value="#session.questionInfo.historyRecordCount" />
								<script type="text/javascript">
									var id2='<s:property value="#session.questionInfo.prevRecordCount + 1" />';
									var id1='<s:property value="#session.questionInfo.prevRecordCount + 1" />';
								</script>
							</s:if> <s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@KENSHU_SAMPLING_MENU_ID">
								<s:property value="#session.questionInfo.prevRecordCount + 1" /><s:text name="label.forwardslash" /><s:property value="#session.tranDescScoreInfoList.size()" />
							</s:elseif><s:else>
								<s:property value="#session.recordNumber"/><s:text name="label.forwardslash" /><s:property value="#session.answerRecordsList.size()"/>
							</s:else>
							
						</p>
					</s:if>
					<s:else>
						
					</s:else>
				</div>
			   </div>
		    
					     </td>
							<td style="background-color: #4a6c9a; height: 25px;"></td>
							<td style="background-color: #4a6c9a; height: 25px;">
							<div class="div_center_heading">
							<span class="box_content_right_pane_links"> <span
									class="partition">|</span> <a
									href="javascript:openHelpDocWindow();">Help Manual</a> <span
									class="partition">|</span> <a
									href="javascript:openManualDocWindow();">Detail Manual</a> <span
									class="partition">|</span>
							</span>
							</div>
						   </td>
						</tr>
						<tr>

							<s:set var="columnSize" value="imageDisplayProperies.columnSize" />
							<s:set var="tdWidth" value="imageDisplayProperies.tdWidth"></s:set>
							<s:set var="tdHeigth" value="imageDisplayProperies.tdHeigth"></s:set>
							<s:set var="totalRecords" value="tranDescInfoList.size()" />
							<s:set var="startValue" value="0" />
							<td colspan=2 style="text-align:left;vertical-align:top;padding:0">
								<table style="margin-left:5px;" id="imageDisplay">
									<tbody>																			
									  <s:iterator begin="1" end="%{@com.saiten.util.SaitenUtil@getRowSize(#totalRecords,#columnSize)}" status="rowStatus">
										<tr>
									
											<s:set var="startValue"
													value="%{ #rowStatus.index==0 ? #rowStatus.index:#rowStatus.index * #columnSize }" />
												<s:subset source="tranDescInfoList" start="%{#startValue}"
													count="%{@com.saiten.util.SaitenUtil@findRecordCount(#totalRecords,#columnSize,#rowStatus.index)}">
													<s:iterator var="tranDescScoreInfo">
													    
													    <s:hidden id="answerSeq" name="" value="%{tranDescScoreInfo.answerInfo.answerSeq}"></s:hidden>
														<td
															style="border-color: green; width:<s:property value="#tdWidth"/>px;height:<s:property value="#tdHeigth"/>px;"
															align="center">
															<a href='<s:i18n name="application"><s:text name="saiten.answerimage.url" /></s:i18n>/<s:property value="%{#session.questionInfo.questionSeq}" /><s:text name="label.hyphen"/><s:property value="%{#session.questionInfo.subjectCode}" /><s:text name="label.hyphen"/><s:property value="%{#session.questionInfo.questionNum}" />/<s:property value="%{#tranDescScoreInfo.imageFileName}" />' data-lightbox="roadtrip"><img id="answerImage1" name="answerImage1"
																	src='<s:i18n name="application"><s:text name="saiten.answerimage.url" /></s:i18n>/<s:property value="%{#session.questionInfo.questionSeq}" /><s:text name="label.hyphen"/><s:property value="%{#session.questionInfo.subjectCode}" /><s:text name="label.hyphen"/><s:property value="%{#session.questionInfo.questionNum}" />/<s:property value="%{#tranDescScoreInfo.imageFileName}" />'
																	alt="<s:text name="btn.scoring.alt.scaling" />" style="vertical-align: bottom;" width="100%" height="100%" ></a>
														    <span style="float:left;"><input type="checkbox" id="historyGradeNum" name="scoreInputInfo.scoreHistoryInfo.historyGradeNum" value="0" ></span> 
														</td>

													</s:iterator>
												</s:subset> 
											</tr>	
										 </s:iterator>									
									</tbody>
								</table>
							</td>
						</tr>
						<tr>
							<td class="big_result_blue"><s:set id="backToScoringFlag" value="#parameters['backToScoringFlag'][0]"></s:set>
					<s:text name="label.header.grade"></s:text><s:text name="label.colon"></s:text>
					<s:if test="((#session.questionInfo.menuId == @com.saiten.util.WebAppConst@KENSHU_SAMPLING_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID) || ((#session.questionInfo.menuId == @com.saiten.util.WebAppConst@SCORE_SAMP_MENU_ID) && (#backToScoringFlag != @java.lang.String@valueOf(@com.saiten.util.WebAppConst@TRUE) && (bookmarkScreenFlag != true) && (#session.historyScreenFlag != true)))) 
								|| ((#parameters['prevOrNextFlag'][0] == @java.lang.String@valueOf(@com.saiten.util.WebAppConst@TRUE) || #parameters['prevOrNextFlag'][0] == @java.lang.String@valueOf(@com.saiten.util.WebAppConst@FALSE)) 
									&& (#session.questionInfo.menuId == @com.saiten.util.WebAppConst@CHECKING_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@INSPECTION_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@DENY_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SCORE_SAMP_MENU_ID || #session.historyScreenFlag == true))">
						<s:if test="#session.tranDescScoreInfo.answerInfo.pendingCategorySeq == null && #parameters['gradeNum'][0] == '' && !(@org.apache.commons.lang.ArrayUtils@contains(@com.saiten.util.WebAppConst@NOT_SCORED_STATES_ARRAY, #session.tranDescScoreInfo.scoringState))">
							<s:text name="label.hyphen"></s:text>
						</s:if><s:elseif test="!(@org.apache.commons.lang.ArrayUtils@contains(@com.saiten.util.WebAppConst@NOT_SCORED_STATES_ARRAY, #session.tranDescScoreInfo.scoringState))">
							<s:property value="#parameters.gradeNum"/>
						</s:elseif><s:elseif test="(@org.apache.commons.lang.ArrayUtils@contains(@com.saiten.util.WebAppConst@NOT_SCORED_STATES_ARRAY, #session.tranDescScoreInfo.scoringState)">
						&nbsp;
						</s:elseif>
						&nbsp;
						<s:text name="label.header.result"></s:text><s:text name="label.colon"></s:text>
						<s:if test="#session.tranDescScoreInfo.answerInfo.pendingCategorySeq == null && #parameters['result'][0] == '' && !(@org.apache.commons.lang.ArrayUtils@contains(@com.saiten.util.WebAppConst@NOT_SCORED_STATES_ARRAY, #session.tranDescScoreInfo.scoringState))">
							<s:text name="label.hyphen"></s:text>
						</s:if><s:elseif test="!(@org.apache.commons.lang.ArrayUtils@contains(@com.saiten.util.WebAppConst@NOT_SCORED_STATES_ARRAY, #session.tranDescScoreInfo.scoringState))">
							<s:if test="#parameters['result'][0] == @java.lang.String@valueOf(@com.saiten.util.WebAppConst@F)" >
								<s:text name="label.result.fail"></s:text>	
							</s:if><s:elseif test="#parameters['result'][0] == @java.lang.String@valueOf(@com.saiten.util.WebAppConst@D)" >
								<s:text name="label.result.dubleCircle"></s:text>
							</s:elseif><s:elseif test="#parameters['result'][0] == @java.lang.String@valueOf(@com.saiten.util.WebAppConst@S)" >
								<s:text name="label.result.SingleCircle"></s:text>
							</s:elseif>	
						</s:elseif><s:elseif test="(@org.apache.commons.lang.ArrayUtils@contains(@com.saiten.util.WebAppConst@NOT_SCORED_STATES_ARRAY, #session.tranDescScoreInfo.scoringState))">
						&nbsp;
						</s:elseif>
					</s:if>
					<s:else>
						<s:if test="#session.tranDescScoreInfo.answerInfo.pendingCategorySeq == null && gradeInfo == null">
							<s:if test="@org.apache.commons.lang.ArrayUtils@contains(@com.saiten.util.WebAppConst@NOT_SCORED_STATES_ARRAY, #session.tranDescScoreInfo.scoringState)">
								&nbsp;
						</s:if><s:else>
							<s:text name="label.hyphen"></s:text>
						</s:else></s:if>
						<s:elseif test="#session.tranDescScoreInfo.answerInfo.pendingCategorySeq != null">
							&nbsp;
						</s:elseif><s:elseif test="gradeInfo != null">
							<s:property value="gradeInfo.gradeNum"/>	
						</s:elseif>&nbsp;
						<s:text name="label.header.result"></s:text><s:text name="label.colon"></s:text>
						<s:if test="#session.tranDescScoreInfo.answerInfo.pendingCategorySeq == null && gradeInfo == null">
							<s:if test="@org.apache.commons.lang.ArrayUtils@contains(@com.saiten.util.WebAppConst@NOT_SCORED_STATES_ARRAY, #session.tranDescScoreInfo.scoringState)">
								&nbsp;
						</s:if><s:else>
							<s:text name="label.hyphen"></s:text>
						</s:else>
						</s:if><s:elseif test="#session.tranDescScoreInfo.answerInfo.pendingCategorySeq != null">
							&nbsp;
						</s:elseif><s:elseif test="gradeInfo != null">
							<s:if test="gradeInfo.result == @com.saiten.util.WebAppConst@F" >
								<s:text name="label.result.fail"></s:text>	
							</s:if><s:elseif test="gradeInfo.result == @com.saiten.util.WebAppConst@D" >
								<s:text name="label.result.dubleCircle"></s:text>
							</s:elseif><s:elseif test="gradeInfo.result == @com.saiten.util.WebAppConst@S" >
								<s:text name="label.result.SingleCircle"></s:text>
							</s:elseif>	
						</s:elseif>
						
					</s:else></td>
							<td class="big_result_blue">
				              <s:submit accesskey="s" type="button" cssStyle="width:99px; height:39px;" cssClass="btn btn-primary btn-scoring-sm" label="%{getText('label.approve')}" name="approve" id="approve" />
							</td>
						</tr>
					</table>
				<s:token />
		</form>
		</div>
		</div>
	</div>
</body>
</html>