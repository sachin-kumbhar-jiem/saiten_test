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
<script type="text/javascript" src="./material/scripts/js/forward_dialog.js"></script>
<script type="text/javascript" src="./material/scripts/js/list_action.js"></script>
<script type="text/javascript" src="./material/scripts/js/chbox_action.js"></script>
<script type="text/javascript" src="./material/scripts/js/default.js"></script>
<script type="text/javascript" src="./material/scripts/jQuery/jquery.validate.js" ></script>
<script type="text/javascript" src="./material/scripts/js/script.js"></script>
<script type="text/javascript" src="./material/scripts/js/scoring.js"></script>
<script type="text/javascript" src="./material/scripts/js/modalPopLite.js"></script>
<jsp:include page="include/jsMessages.jsp"></jsp:include>

<script type="text/javascript" src="./material/scripts/js/answerImagePopup.js"></script>
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
        	var pdfWindow = window.open("<s:i18n name='application'><s:text name='saiten.manualfile.url' /></s:i18n>/<s:property value='#session.questionInfo.manualDocument' />","<s:text name="scoring.title" />","status = 1, height = 500, width = 500, location=no, scrollbars=no");
        	
        	//var pdfWindow = window.open("material/documents/manual/<s:property value="#session.questionInfo.manualDocument" />","<s:text name="scoring.title" />","status = 1, height = 500, width = 500, location=no, scrollbars=no");
            /* var pdfWindow = window.open("./material/documents/manual/<s:property value="#session.questionInfo.manualDocument" />","<s:text name="scoring.title" />","status = 1, height = 500, width = 500, location=no, scrollbars=no");
            pdfWindow.document.write('<html><head><title><s:text name="label.scoring.helpdocument.title" /></title></head>');
            pdfWindow.document.write('</html>');
             pdfWindow.document.close(); */
        }
        function openManualDocWindow()
        {
        	var pdfWindow = window.open("<s:i18n name='application'><s:text name='saiten.questionfile.url'/></s:i18n>/<s:property value='#session.questionInfo.questionFileName' />","<s:text name="scoring.title" />","status = 1, height = 500, width = 500, location=no, scrollbars=no");
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
<link href="./material/css/modalPopLite.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="./material/css/import.css" media="all">
		
<style type="text/css">
 label { width: auto; float: none; color: red;  display: block;   }
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

<body class="<s:property value="%{#bodyColor}"/>" id="body">
<jsp:include page="include/jsMessages.jsp"></jsp:include>
<div>
	<div id="wrapper">
		<div id="contents" class="text14">
		<div></div>
			<form  name="scoreActionForm" id="scoreActionForm" method="post">
			<s:hidden id="answerSeq" value="%{answerSequence}"></s:hidden>
			<table>
		     <tr>
			  <!-- 1st td --> 
		        <td colspan="2" class="table-side-menu-td" width="6%" style="text-align: center;" >
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
			   
			   <!-- 2nd td --> 
			   
			   <td class="table-center-menu-td-image">
			   <!-- Start left pane -->
		<div id="content_left_pane">
		  <div class="box_content_left_pane_input_normal">
			
			<div class="div_center_heading">
				<span class="table_center_heading">
					<s:text name="label.scoring.ansformnumber" />&nbsp;  
					<s:text name="label.scoring.openingbracket" /><s:property value="#session.tranDescScoreInfo.answerFormNumber" /><s:text name="label.scoring.closingbracket" /> 
					<s:if test="#session.questionInfo.questionType == @com.saiten.util.WebAppConst@SPEAKING_TYPE">
						&nbsp;<s:set var="facilitatorId" value="%{@com.saiten.util.SaitenUtil@getFacilitatorIdByImageFileName(#session.tranDescScoreInfo.imageFileName)}"></s:set>
						<s:text name="label.scoring.facilitator.id" />&nbsp;
						<s:text name="label.scoring.openingbracket" /><s:property value="%{#facilitatorId}"/><s:text name="label.scoring.closingbracket" />  
					</s:if>
					<s:elseif test="#session.questionInfo.questionType == @com.saiten.util.WebAppConst@WRITING_TYPE">
					   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   
					</s:elseif>  
				
					<s:if test="(#session.questionInfo.menuId == @com.saiten.util.WebAppConst@KENSHU_SAMPLING_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SCORE_SAMP_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID) || ((#session.questionInfo.menuId == @com.saiten.util.WebAppConst@DENY_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@OUT_BOUNDARY_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_BLIND_TYPE_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_LANGUAGE_SUPPORT_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID) && (#session.tranDescScoreInfo.answerInfo.historySeq == null)) || (@org.apache.commons.lang.ArrayUtils@contains(@com.saiten.util.WebAppConst@ANSWER_PAPER_LINK_ENABLE_STATES, #session.tranDescScoreInfo.scoringState))">
						&nbsp;&nbsp;
					<s:if test="!(#session.questionInfo.questionType == @com.saiten.util.WebAppConst@WRITING_TYPE) && !(#session.questionInfo.questionType == @com.saiten.util.WebAppConst@SPEAKING_TYPE)">	
						<s:text name="label.partition" />
							<s:if test="#session.questionInfo.side == @com.saiten.util.WebAppConst@SIDE_NINETY_EIGHT || #session.questionInfo.side == @com.saiten.util.WebAppConst@SIDE_NINETY_NINE">
								<a href="#" onclick="openPaperDialog()"><s:text name="label.answerpaper.popup.link"/></a>
							</s:if><s:elseif test="#session.questionInfo.side == @java.lang.String@valueOf(@com.saiten.util.WebAppConst@HYPHEN)">
								<s:text name="label.answerpaper.popup.link"/>
							</s:elseif>
						<s:text name="label.partition" />
					</s:if>		
					</s:if><s:else>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
					</s:else>
				</span>
				
				<s:if test="bookmarkScreenFlag != true && !(#session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@KENSHU_SAMPLING_MENU_ID)">
					<span style="cursor:pointer;padding-right:8px;float:right">
						<s:if test="#session.questionInfo.prevRecordCount > @com.saiten.util.WebAppConst@ZERO || #session.prevRecordCount > @com.saiten.util.WebAppConst@ZERO">
							<img style="padding-right:6px" id="prev" name="prev" src="./material/img/action_back.gif" alt="<s:text name="label.scoring.alt.previousgrading"/>"
							onclick="javascript:location.href='showPrevOrNextHistory.action?prevOrNextFlag=<s:property value="%{@com.saiten.util.WebAppConst@TRUE}" />';">
						</s:if>
						<s:else>
							<img />
						</s:else>
					<%-- </span>	
					
					<span class="box_content_left_pane_image_forward arrow_image_align_left_pane1"> 
                    <span style="cursor:pointer"> --%>
						<s:if test="#session.questionInfo.nextRecordCount > @com.saiten.util.WebAppConst@ZERO || #session.nextRecordCount > @com.saiten.util.WebAppConst@ZERO">
							<img style="float:right" id="next" name="next" src="./material/img/action_forward.gif" alt="<s:text name="label.scoring.alt.nextgrading" />"
							onclick="javascript:location.href='showPrevOrNextHistory.action?prevOrNextFlag=<s:property value="%{@com.saiten.util.WebAppConst@FALSE}" />';">
						</s:if>
					</span>	
				</s:if>
		 	</div>
		<br>
		<s:if test="(#session.questionInfo.questionType == @com.saiten.util.WebAppConst@SHORT_TYPE) || (#session.questionInfo.questionType == @com.saiten.util.WebAppConst@LONG_TYPE)">
		<div id="magnifier1">
		<!-- <a href="#" id="close" onclick="applyOverflowAuto();"><img src="./material/img/close.png" align="right"></a> -->
		<a href="#" id="zoomOut" onclick="dropImage()" style="color: black;"><img src="./material/img/magnifier_zoom_out.png" class="border">&nbsp;<s:text name="label.button.zoomOut"></s:text></a> 
		<a href="#" id="zoomIn" onclick="enlargeImage()" style="color: black;"><img src="./material/img/magnifier_zoom_in.png" class="border">&nbsp;<s:text name="label.button.zoomIn"></s:text></a>
		<a href="#" id="original_size" onclick="setImageToDefaultSize()" style="color: black;">&nbsp;<s:text name="label.button.dafault.image.size"></s:text></a>
	</div>	
	<div >
				<p class="score_img_top" style="overflow-x: auto;overflow-y: auto;width: 465px;height: 530px;vertical-align: top;">
					<span class="score_img">
						<%-- <img id="answerImage" name="answerImage" src='<s:i18n name="application"><s:text name="saiten.answerimage.url" /></s:i18n>/<s:property value="%{#session.questionInfo.questionNum}" />/<s:property value="%{#session.tranDescScoreInfo.imageFileName}" />' alt="<s:text name="btn.scoring.alt.scaling" />"> --%>
						<img id="answerImage" name="answerImage" src='<s:i18n name="application"><s:text name="saiten.answerimage.url" /></s:i18n>/<s:property value="%{#session.questionInfo.questionSeq}" /><s:text name="label.hyphen"/><s:property value="%{#session.questionInfo.subjectCode}" /><s:text name="label.hyphen"/><s:property value="%{#session.questionInfo.questionNum}" />/<s:property value="%{#session.tranDescScoreInfo.imageFileName}" />' alt="<s:text name="btn.scoring.alt.scaling" />">
					</span> 
				</p>
				<s:if  test="(#session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID) || (#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID && #session.tranDescScoreInfo.lookAfterwardsCount>0)">
		<table style="width: 465px;">
		<tr><td>&nbsp;</td></tr>
		<tr class="box_less_width_input" >
			<td colspan="5">
				<span class="table_center_heading"><s:text name="label.look_afterwards" /></span>
			</td>
		</tr>
		
		<tr >
		<td >
			<!-- <p class="comment_area1" > -->
					<s:if  test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID">
						<s:textarea  name="markComment" id="markComment" value="" maxlength="512" cssStyle="width: 350px; height: 50px;"></s:textarea>
					</s:if>
					<s:else>
							<s:textarea  name="markComment" id="markComment" value="%{#session.tranDescScoreInfo.lookAfterwardsComments}"  cssStyle="width: 350px; height: 50px;" disabled="true" ></s:textarea>
					</s:else>
			<!-- </p> -->
		</td>
		<td>
			<s:if  test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID">
				<a  onclick="doMarkUnmark();" id="markUnamrk" class="btn btn-primary btn-scoring-sm" style="width:60px; height:20px; text-align: center;"><s:text name="label.mark.look_afterwoards"></s:text></a>
			</s:if><s:else>
				<a onclick="unmarkAll();" id="unmarkAll" class="btn btn-primary btn-scoring-sm" style="width:60px; height:20px; text-align: center;"><s:text name="label.unmark.look_afterwoards"></s:text></a>
			</s:else>

		</td>
		</tr>
		</table>
		</s:if>
		<s:if  test="(#session.questionInfo.menuId == @com.saiten.util.WebAppConst@KENSHU_SAMPLING_MENU_ID)">
		<table style="width: 465px;">
		<tr><td>&nbsp;</td></tr>
		<tr class="box_less_width_input" >
			<td colspan="5">
				<span class="table_center_heading"><s:text name="label.kenshu_comment" /></span>
			</td>
		</tr>
		
		<tr >
		<td >
			<!-- <p class="comment_area1" > -->
					<%-- <s:if  test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID"> --%>
						
					<%-- </s:if>
					<s:else>
							<s:textarea  name="kenshuComment" id="kenshuComment" value=""  cssStyle="width: 350px; height: 50px;" disabled="true" ></s:textarea>
					</s:else> --%>
			<!-- </p> -->
			<s:if test="#session.samplingSearch == @com.saiten.util.WebAppConst@ACCEPTANCE_DISPLAY">
				<s:textarea  name="kenshuComment" id="kenshuComment" value="%{#session.tranAcceptance.comment}" maxlength="512" cssStyle="width: 350px; height: 50px;" disabled="true"></s:textarea>
			</s:if> <s:else>
				<s:if test="#session.kenshuRecordInfo == null || #session.kenshuRecordInfo.comment == '' || #session.kenshuRecordInfo.comment == null" >
					<s:textarea  name="kenshuComment" id="kenshuComment" value="%{#session.kenshuRecordInfo.comment}" maxlength="512" cssStyle="width: 350px; height: 50px;"></s:textarea>
				</s:if><s:else>
					<s:textarea  name="kenshuComment" id="kenshuComment" value="%{#session.kenshuRecordInfo.comment}" maxlength="512" cssStyle="width: 350px; height: 50px;" disabled="true"></s:textarea>
				</s:else>
			</s:else>
			
		</td>
		<td>
			
			<s:if test="#session.samplingSearch == @com.saiten.util.WebAppConst@ACCEPTANCE_DISPLAY">
				<s:if test="#session.tranAcceptance.explainFlag == @com.saiten.util.WebAppConst@VALID_FLAG">
					<p class="btn btn-disabled" style="width:60px; height:20px; text-align: center;"><s:text name="label.unmark.look_afterwoards"></s:text></p>	
				</s:if> <s:else>
					<a  onclick="doKenshuMarkUnmark();" id="kenshumarkUnamrk" class="btn btn-primary btn-scoring-sm" style="width:60px; height:20px; text-align: center;"><s:text name="label.unmark.look_afterwoards"></s:text></a>	
				</s:else>
			</s:if> <s:else>
				<s:if test="#session.kenshuRecordInfo == null || #session.kenshuRecordInfo.comment == '' || #session.kenshuRecordInfo.comment == null" >
					<a  onclick="doKenshuMarkUnmark();" id="kenshumarkUnamrk" class="btn btn-primary btn-scoring-sm" style="width:60px; height:20px; text-align: center;"><s:text name="label.mark.look_afterwoards"></s:text></a>	
				</s:if> <s:else>
					<a  onclick="doKenshuMarkUnmark();" id="kenshumarkUnamrk" class="btn btn-primary btn-scoring-sm" style="width:60px; height:20px; text-align: center;"><s:text name="label.unmark.look_afterwoards"></s:text></a>	
				</s:else>
			</s:else>
			
			<%-- <s:if  test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@KENSHU_SAMPLING_MENU_ID">
				<a  onclick="doKenshuMarkUnmark();" id="kenshumarkUnamrk" class="btn btn-primary btn-scoring-sm" style="width:60px; height:20px; text-align: center;"><s:text name="label.mark.look_afterwoards"></s:text></a>
			</s:if><s:else>
				<a onclick="unmarkAll();" id="unmarkAll" class="btn btn-primary btn-scoring-sm" style="width:60px; height:20px; text-align: center;"><s:text name="label.unmark.look_afterwoards"></s:text></a>
			</s:else> --%>

		</td>
		</tr>
		</table>
		</s:if>
			</div>			
		</s:if>
		<s:elseif test="#session.questionInfo.questionType == @com.saiten.util.WebAppConst@SPEAKING_TYPE">
			<div style="overflow-x: auto;overflow-y: auto;width: 465px;height: 555px;vertical-align: top;" align="center">
				<p class="score_speaking_top">
					<span class="score_speaking">
						<%-- <img id="answerImage" name="answerImage" src='<s:i18n name="application"><s:text name="saiten.answerimage.url" /></s:i18n>/<s:property value="%{#session.questionInfo.questionNum}" />/<s:property value="%{#session.tranDescScoreInfo.imageFileName}" />' alt="<s:text name="btn.scoring.alt.scaling" />"> --%>
					    
						 <br /><br /><br />
						 
						 <div style="margin-bottom: 20px;">
	
    						<img id="pauseButton"  alt="" src="./material/img/pause.jpg" onclick="document.getElementById('myTune').pause();return false;" style="cursor: hand;">
							<img id="playButton"  alt="" src="./material/img/play.jpg" onclick="document.getElementById('myTune').play();return false;" style="cursor: hand;">
							<img id="stopButton"  alt="" src="./material/img/stop.jpg" onclick="stopAudio();return false;" style="cursor: hand;">

  						</div>
  						
						<audio controls id="myTune">
						 <source height="50" width="150" src='<s:i18n name="application"><s:text name="saiten.answerimage.url" /></s:i18n>/<s:property value="%{#session.questionInfo.questionSeq}" /><s:text name="label.hyphen"/><s:property value="%{#session.questionInfo.subjectCode}" /><s:text name="label.hyphen"/><s:property value="%{#session.questionInfo.questionNum}" />/<s:property value="%{#session.tranDescScoreInfo.imageFileName}" />' alt="<s:text name="btn.scoring.alt.scaling" />" type="audio/mpeg" />
	        				<%--  <source height="50" width="150" src='<s:i18n name="application"><s:text name="saiten.answerimage.url" /></s:i18n>/<s:property value="%{#session.questionInfo.questionNum}" />/<s:property value="%{#session.tranDescScoreInfo.imageFileName}" />' alt="<s:text name="btn.scoring.alt.scaling" />" type="audio/mpeg" /> --%>
	        			</audio>
	        		</span> 
				</p>
								<s:if  test="(#session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID) || (#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID && #session.tranDescScoreInfo.lookAfterwardsCount>0)">
		<table style="width: 465px;">
		<tr><td>&nbsp;</td></tr>
		<tr class="box_less_width_input" >
			<td colspan="5">
				<span class="table_center_heading"><s:text name="label.look_afterwards" /></span>
			</td>
		</tr>
		
		<tr >
		<td >
			<!-- <p class="comment_area1" > -->
					<s:if  test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID">
						<s:textarea  name="markComment" id="markComment" value="" maxlength="512" cssStyle="width: 350px; height: 50px;" ></s:textarea>
					</s:if>
					<s:else>
							<s:textarea  name="markComment" id="markComment" value="%{#session.tranDescScoreInfo.lookAfterwardsComments}"  cssStyle="width: 350px; height: 50px;" disabled="true" ></s:textarea>
					</s:else>
			<!-- </p> -->
		</td>
		<td>
			<s:if  test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID">
				<a onclick="doMarkUnmark();" id="markUnamrk" class="btn btn-primary btn-scoring-sm" style="width:60px; height:20px; text-align: center;"><s:text name="label.mark.look_afterwoards"></s:text></a>
			</s:if><s:else>
				<a onclick="unmarkAll();" id="unmarkAll" class="btn btn-primary btn-scoring-sm" style="width:60px; height:20px; text-align: center;"><s:text name="label.unmark.look_afterwoards"></s:text></a>
			</s:else>

		</td>
		</tr>
		</table>
		</s:if>
		<s:if  test="(#session.questionInfo.menuId == @com.saiten.util.WebAppConst@KENSHU_SAMPLING_MENU_ID)">
		<table style="width: 465px;">
		<tr><td>&nbsp;</td></tr>
		<tr class="box_less_width_input" >
			<td colspan="5">
				<span class="table_center_heading"><s:text name="label.kenshu_comment" /></span>
			</td>
		</tr>
		
		<tr >
		<td >
			<!-- <p class="comment_area1" > -->
					<%-- <s:if  test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID"> --%>
					<%-- </s:if>
					<s:else>
							<s:textarea  name="kenshuComment" id="kenshuComment" value=""  cssStyle="width: 350px; height: 50px;" disabled="true" ></s:textarea>
					</s:else> --%>
			<!-- </p> -->
			
			<s:if test="#session.samplingSearch == @com.saiten.util.WebAppConst@ACCEPTANCE_DISPLAY">
				<s:textarea  name="kenshuComment" id="kenshuComment" value="%{#session.tranAcceptance.comment}" maxlength="512" cssStyle="width: 350px; height: 50px;" disabled="true"></s:textarea>
			</s:if> <s:else>
				<s:if test="#session.kenshuRecordInfo == null || #session.kenshuRecordInfo.comment == '' || #session.kenshuRecordInfo.comment == null" >
					<s:textarea  name="kenshuComment" id="kenshuComment" value="%{#session.kenshuRecordInfo.comment}" maxlength="512" cssStyle="width: 350px; height: 50px;"></s:textarea>
				</s:if><s:else>
					<s:textarea  name="kenshuComment" id="kenshuComment" value="%{#session.kenshuRecordInfo.comment}" maxlength="512" cssStyle="width: 350px; height: 50px;" disabled="true"></s:textarea>
				</s:else>
			</s:else>
		</td>
		<td>
			
			<s:if test="#session.samplingSearch == @com.saiten.util.WebAppConst@ACCEPTANCE_DISPLAY">
				<s:if test="#session.tranAcceptance.explainFlag == @com.saiten.util.WebAppConst@VALID_FLAG">
					<p class="btn btn-disabled" style="width:60px; height:20px; text-align: center;"><s:text name="label.unmark.look_afterwoards"></s:text></p>	
				</s:if> <s:else>
					<a  onclick="doKenshuMarkUnmark();" id="kenshumarkUnamrk" class="btn btn-primary btn-scoring-sm" style="width:60px; height:20px; text-align: center;"><s:text name="label.unmark.look_afterwoards"></s:text></a>	
				</s:else>
			</s:if> <s:else>
				<s:if test="#session.kenshuRecordInfo == null || #session.kenshuRecordInfo.comment == '' || #session.kenshuRecordInfo.comment == null" >
					<a  onclick="doKenshuMarkUnmark();" id="kenshumarkUnamrk" class="btn btn-primary btn-scoring-sm" style="width:60px; height:20px; text-align: center;"><s:text name="label.mark.look_afterwoards"></s:text></a>	
				</s:if> <s:else>
					<a  onclick="doKenshuMarkUnmark();" id="kenshumarkUnamrk" class="btn btn-primary btn-scoring-sm" style="width:60px; height:20px; text-align: center;"><s:text name="label.unmark.look_afterwoards"></s:text></a>	
				</s:else>
			</s:else>
			
			<%-- <s:if  test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@KENSHU_SAMPLING_MENU_ID">
				<a  onclick="doKenshuMarkUnmark();" id="kenshumarkUnamrk" class="btn btn-primary btn-scoring-sm" style="width:60px; height:20px; text-align: center;"><s:text name="label.mark.look_afterwoards"></s:text></a>
			</s:if><s:else>
				<a onclick="unmarkAll();" id="unmarkAll" class="btn btn-primary btn-scoring-sm" style="width:60px; height:20px; text-align: center;"><s:text name="label.unmark.look_afterwoards"></s:text></a>
			</s:else> --%>

		</td>
		</tr>
		</table>
		</s:if>
			</div> 			 
		</s:elseif><s:elseif test="#session.questionInfo.questionType == @com.saiten.util.WebAppConst@WRITING_TYPE">
			<div style="overflow-x: auto;overflow-y: auto;width: 465px;height: 555px;vertical-align: top;" align="center">
				<p class="score_writing_top">
					<span class="score_writing">
						 <br /><br /><br />
						<%-- <img id="answerImage" name="answerImage" src='<s:i18n name="application"><s:text name="saiten.answerimage.url" /></s:i18n>/<s:property value="%{#session.questionInfo.questionNum}" />/<s:property value="%{#session.tranDescScoreInfo.imageFileName}" />' alt="<s:text name="btn.scoring.alt.scaling" />"> --%>
						<s:textarea name="writing_file" cssStyle="width: 450px; height: 300px;" value="%{#session.tranDescScoreInfo.imageFileName}" disabled="true" >
						</s:textarea>
						
					</span> 
				</p>
								<s:if  test="(#session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID) || (#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID && #session.tranDescScoreInfo.lookAfterwardsCount>0)">
		<table style="width: 465px;">
		<tr><td>&nbsp;</td></tr>
		<tr class="box_less_width_input" >
			<td colspan="5">
				<span class="table_center_heading"><s:text name="label.look_afterwards" /></span>
			</td>
		</tr>
		
		<tr >
		<td >
			<!-- <p class="comment_area1" > -->
					<s:if  test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID">
						<s:textarea  name="markComment" id="markComment" value="" maxlength="512" cssStyle="width: 350px; height: 50px;" ></s:textarea>
					</s:if>
					<s:else>
							<s:textarea  name="markComment" id="markComment" value="%{#session.tranDescScoreInfo.lookAfterwardsComments}"  cssStyle="width: 350px; height: 50px;" disabled="true" ></s:textarea>
					</s:else>
			<!-- </p> -->
		</td>
		<td>
			<s:if  test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID">
				<a onclick="doMarkUnmark();" id="markUnamrk" class="btn btn-primary btn-scoring-sm" style="width:60px; height:20px; text-align: center;"><s:text name="label.mark.look_afterwoards"></s:text></a>
			</s:if><s:else>
				<a onclick="unmarkAll();" id="unmarkAll" class="btn btn-primary btn-scoring-sm" style="width:60px; height:20px; text-align: center;"><s:text name="label.unmark.look_afterwoards"></s:text></a>
			</s:else>

		</td>
		</tr>
		</table>
		</s:if>
		<s:if  test="(#session.questionInfo.menuId == @com.saiten.util.WebAppConst@KENSHU_SAMPLING_MENU_ID)">
		<table style="width: 465px;">
		<tr><td>&nbsp;</td></tr>
		<tr class="box_less_width_input" >
			<td colspan="5">
				<span class="table_center_heading"><s:text name="label.kenshu_comment" /></span>
			</td>
		</tr>
		
		<tr >
		<td >
			<!-- <p class="comment_area1" > -->
					<%-- <s:if  test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID"> --%>
					<%-- </s:if> --%>
					<%-- <s:else>
							<s:textarea  name="kenshuComment" id="kenshuComment" value=""  cssStyle="width: 350px; height: 50px;" disabled="true" ></s:textarea>
					</s:else> --%>
			<!-- </p> -->
			<s:if test="#session.samplingSearch == @com.saiten.util.WebAppConst@ACCEPTANCE_DISPLAY">
				<s:textarea  name="kenshuComment" id="kenshuComment" value="%{#session.tranAcceptance.comment}" maxlength="512" cssStyle="width: 350px; height: 50px;" disabled="true"></s:textarea>
			</s:if> <s:else>
				<s:if test="#session.kenshuRecordInfo == null || #session.kenshuRecordInfo.comment == '' || #session.kenshuRecordInfo.comment == null" >
					<s:textarea  name="kenshuComment" id="kenshuComment" value="%{#session.kenshuRecordInfo.comment}" maxlength="512" cssStyle="width: 350px; height: 50px;"></s:textarea>
				</s:if><s:else>
					<s:textarea  name="kenshuComment" id="kenshuComment" value="%{#session.kenshuRecordInfo.comment}" maxlength="512" cssStyle="width: 350px; height: 50px;" disabled="true"></s:textarea>
				</s:else>
			</s:else>
			
		</td>
		<td>
			<s:if test="#session.samplingSearch == @com.saiten.util.WebAppConst@ACCEPTANCE_DISPLAY">
				<s:if test="#session.tranAcceptance.explainFlag == @com.saiten.util.WebAppConst@VALID_FLAG">
					<p class="btn btn-disabled" style="width:60px; height:20px; text-align: center;"><s:text name="label.unmark.look_afterwoards"></s:text></p>	
				</s:if> <s:else>
					<a  onclick="doKenshuMarkUnmark();" id="kenshumarkUnamrk" class="btn btn-primary btn-scoring-sm" style="width:60px; height:20px; text-align: center;"><s:text name="label.unmark.look_afterwoards"></s:text></a>	
				</s:else>
			</s:if> <s:else>
				<s:if test="#session.kenshuRecordInfo == null || #session.kenshuRecordInfo.comment == '' || #session.kenshuRecordInfo.comment == null" >
					<a  onclick="doKenshuMarkUnmark();" id="kenshumarkUnamrk" class="btn btn-primary btn-scoring-sm" style="width:60px; height:20px; text-align: center;"><s:text name="label.mark.look_afterwoards"></s:text></a>	
				</s:if> <s:else>
					<a  onclick="doKenshuMarkUnmark();" id="kenshumarkUnamrk" class="btn btn-primary btn-scoring-sm" style="width:60px; height:20px; text-align: center;"><s:text name="label.unmark.look_afterwoards"></s:text></a>	
				</s:else>
			</s:else>
			
			<%-- <s:if  test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@KENSHU_SAMPLING_MENU_ID">
				<a  onclick="doKenshuMarkUnmark();" id="kenshumarkUnamrk" class="btn btn-primary btn-scoring-sm" style="width:60px; height:20px; text-align: center;"><s:text name="label.mark.look_afterwoards"></s:text></a>
			</s:if><s:else>
				<a onclick="unmarkAll();" id="unmarkAll" class="btn btn-primary btn-scoring-sm" style="width:60px; height:20px; text-align: center;"><s:text name="label.unmark.look_afterwoards"></s:text></a>
			</s:else> --%>

		</td>
		</tr>
		</table>
		</s:if>
			</div> 
						   
		</s:elseif>	
		</div>
		</div>
		
		 </td>
		 <!-- End left pane --> 
		 	   <!--3rd td --> 
		<td class="table-center-menu-td">	   
		<!-- Start right pane -->	
		
		<div id="content_right_pane">
		<div class="box_content_right_pane">
		
		<div class="div_center_heading">
		<span class="table_center_heading"><s:text name="label.scoring.checkpoints" /></span>
		<span class="box_content_right_pane_links">
			<s:set id="questionFileName" name="questionFileName" value="%{#session.questionInfo.questionFileName}"/>
			<s:set id="manualDocument" name="manualDocument" value="%{#session.questionInfo.manualDocument}"/>
			
			<span class="partition"><s:text name="label.partition" /></span> 
				<s:if test="@org.apache.commons.lang.StringUtils@isEmpty(#manualDocument)">
					<a href="#" style="text-decoration: none;border: none;"><s:text name="label.scoring.helpdocument"  /></a>
				</s:if>
				<s:else>
				<a href="javascript:openHelpDocWindow();"><s:text name="label.scoring.helpdocument" /></a>
				</s:else>
			<span class="partition"><s:text name="label.partition" /></span>
				<s:if test="@org.apache.commons.lang.StringUtils@isEmpty(#questionFileName)">
					<a href="#" style="text-decoration: none;border: none;"><s:text name="label.scoring.manualdocument"  /></a>
				</s:if>
				<s:else>
					<a href="javascript:openManualDocWindow();"><s:text name="label.scoring.manualdocument" /></a>
				</s:else>			 
			<span class="partition"><s:text name="label.partition" /></span>
			
		</span>
		</div>
		
		<div class="box">
		<div class="check_scroll" >
		
		<table id="checkbox" >
		
		<s:if test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@CHECKING_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@INSPECTION_MENU_ID">
			<s:hidden id="hiddenCheckPointArray" name="hiddenCheckPointArray" value="%{#session.questionInfo.checkPointList}"></s:hidden>
			<s:hidden id="hiddenSelectedCheckPointArray" name="hiddenSelectedCheckPointArray" value="%{#session.selectedCheckPointList}"></s:hidden>
		</s:if>
		<s:if test="((#session.questionInfo.menuId == @com.saiten.util.WebAppConst@MISMATCH_MENU_ID || ((@org.apache.commons.lang.ArrayUtils@contains(@com.saiten.util.WebAppConst@DENY_SCORING_STATES, #session.tranDescScoreInfo.scoringState) || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@DENY_MENU_ID) && (#session.questionInfo.questionType == @com.saiten.util.WebAppConst@SPEAKING_TYPE || #session.questionInfo.questionType == @com.saiten.util.WebAppConst@WRITING_TYPE))) && (bookmarkScreenFlag != true))
							|| ((bookmarkScreenFlag == true || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID) && (@org.apache.commons.lang.ArrayUtils@contains(@com.saiten.util.WebAppConst@MISMATCH_STATES, #session.tranDescScoreInfo.scoringState) || (@org.apache.commons.lang.ArrayUtils@contains(@com.saiten.util.WebAppConst@DENY_SCORING_STATES, #session.tranDescScoreInfo.scoringState) && (#session.questionInfo.questionType == @com.saiten.util.WebAppConst@SPEAKING_TYPE || #session.questionInfo.questionType == @com.saiten.util.WebAppConst@WRITING_TYPE))))">							
		  
		  <tr>
		  <td>
		   <table><tr>
		 	<td>
		 		<s:text name="label.openbrace"></s:text><s:text name="label.one"></s:text><s:text name="label.closingbrace"></s:text>
		 	</td>                 
		 	<td> &nbsp;
		 		<s:text name="label.openbrace"></s:text><s:text name="label.two"></s:text><s:text name="label.closingbrace"></s:text>
		 	</td>
		 	</tr></table></td>
		  </tr>
		</s:if>
		<c:set var="checkPointsShortCutsMap" value="${applicationScope['saitenConfigObject'].checkPointsShortCutsMap}"/>
		<tr><td>
		<div  style="overflow: auto;height: 320px;">
		<table id="checkbox">
		<s:if test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@KENSHU_SAMPLING_MENU_ID"> 
		</s:if> <s:else>
		
		
		<s:iterator value="#session.questionInfo.checkPointList" id="checkPointObj" status="checkPointStatus">
			<s:if test="#checkPointStatus.odd">
				<tr class="skp_color_0">
			</s:if>
			<s:else>
				<tr class="skp_color_white">
			</s:else>
			<!-- <label for="example"> -->
				<!-- MISMATCH -->
					<s:if test="((#session.questionInfo.menuId == @com.saiten.util.WebAppConst@MISMATCH_MENU_ID || ((@org.apache.commons.lang.ArrayUtils@contains(@com.saiten.util.WebAppConst@DENY_SCORING_STATES, #session.tranDescScoreInfo.scoringState) || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@DENY_MENU_ID) && (#session.questionInfo.questionType == @com.saiten.util.WebAppConst@SPEAKING_TYPE || #session.questionInfo.questionType == @com.saiten.util.WebAppConst@WRITING_TYPE))) && (bookmarkScreenFlag != true))
							|| ((bookmarkScreenFlag == true || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@KENSHU_SAMPLING_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID) && (@org.apache.commons.lang.ArrayUtils@contains(@com.saiten.util.WebAppConst@MISMATCH_STATES, #session.tranDescScoreInfo.scoringState) || (@org.apache.commons.lang.ArrayUtils@contains(@com.saiten.util.WebAppConst@DENY_SCORING_STATES, #session.tranDescScoreInfo.scoringState) && (#session.questionInfo.questionType == @com.saiten.util.WebAppConst@SPEAKING_TYPE || #session.questionInfo.questionType == @com.saiten.util.WebAppConst@WRITING_TYPE))))">
				
					<td bgcolor="pink" style="border-right:inset;">
						<s:if test="#session.firstTimeSelectedCheckPointList!= null && #session.firstTimeSelectedCheckPointList.contains(#checkPointObj.checkPoint)">
							<input type="checkbox"  accesskey="<s:property  value="%{#checkPointObj.checkPoint}" />" name="firstTimeCheckPoint" id="firstTimeCheckPoint" disabled="disabled" checked="checked" class="scoring_big_checkbox">
						</s:if><s:else>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</s:else>
					</td>
					<td bgcolor="pink"> 
						<s:if test="#session.secondTimeSelectedCheckPointList!= null && #session.secondTimeSelectedCheckPointList.contains(#checkPointObj.checkPoint)">
							<input type="checkbox" accesskey="<s:property  value="%{#checkPointObj.checkPoint}" />" name="secondTimeCheckPoint" id="secondTimeCheckPoint" disabled="disabled" checked="checked" class="scoring_big_checkbox">
						</s:if><s:else>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</s:else>
					</td>
				</s:if>					
				<!-- MISMATCH -->
				<td class="num" style='background-color:<s:property value="%{#checkPointObj.bgColor}" /> '><s:property value="%{#checkPointObj.checkPoint}" /></td>
				
				<c:set var="checkPoint" value="${checkPointObj.checkPoint}"/>
				<c:set var="shortCutKey" value="${checkPointsShortCutsMap[checkPoint + 0]}"></c:set>
				<td class="check" style='background-color:<s:property value="%{#checkPointObj.bgColor}" /> '>								
					<s:if test="bookmarkScreenFlag == true || (#session.questionInfo.menuId == @com.saiten.util.WebAppConst@CHECKING_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@INSPECTION_MENU_ID  || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@KENSHU_SAMPLING_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID)">
						<s:if test="#session.selectedCheckPointList!= null && #session.selectedCheckPointList.contains(#checkPointObj.checkPoint)">
							<input type="checkbox" accesskey="<s:property  value="%{#checkPointObj.checkPoint}" />" name="checkPoint" id="checkPoint" disabled="disabled" checked="checked" class="scoring_big_checkbox">
						</s:if><s:else><s:property value="&nbsp;"/>
							<input type="checkbox" accesskey="<s:property  value="%{#checkPointObj.checkPoint}" />" name="checkPoint" id="checkPoint" disabled="disabled" class="scoring_big_checkbox">
						</s:else>
					</s:if>
					<s:else>
						<s:if test="#session.selectedCheckPointList!= null && #session.selectedCheckPointList.contains(#checkPointObj.checkPoint)">
						 <input type="checkbox" accesskey="${shortCutKey}" name="checkPoint" id="${checkPoint}" checked="checked" value="<s:property value="%{#checkPointObj.checkPoint}" />:<s:property value="%{#checkPointObj.groupType}" />:<s:property value="%{#checkPointObj.groupId}" />" class="scoring_big_checkbox" onkeydown="uniKeyCode(event)">
						</s:if><s:else>
							<s:if test="(#checkPointObj.checkPoint == WebAppConst.ZERO) && (#session.questionInfo.menuId != @com.saiten.util.WebAppConst@CHECKING_MENU_ID && #session.questionInfo.menuId != @com.saiten.util.WebAppConst@INSPECTION_MENU_ID  && #session.questionInfo.menuId != @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID && #session.questionInfo.menuId != @com.saiten.util.WebAppConst@KENSHU_SAMPLING_MENU_ID)">
								<input type="checkbox" accesskey="${shortCutKey}" name="checkPoint" id="${checkPoint}" value="<s:property value="%{#checkPointObj.checkPoint}" />:<s:property value="%{#checkPointObj.groupType}" />:<s:property value="%{#checkPointObj.groupId}" />" class="scoring_big_checkbox" onkeydown="uniKeyCode(event)">
							</s:if><s:else>
								<input type="checkbox" accesskey="${shortCutKey}" name="checkPoint" id="${checkPoint}" value="<s:property value="%{#checkPointObj.checkPoint}" />:<s:property value="%{#checkPointObj.groupType}" />:<s:property value="%{#checkPointObj.groupId}" />" class="scoring_big_checkbox" autofocus="autofocus" onkeydown="uniKeyCode(event)">
							</s:else>
						</s:else>
					</s:else>
				</td>
				<td class="ans" style='background-color:<s:property value="%{#checkPointObj.bgColor}" /> '><s:property value="%{#checkPointObj.checkPointDescription}" escapeHtml="false" /></td>
				
		</s:iterator>
		</s:else>
		</table>
		</div>
		</td>
		</tr>
		
		<s:if  test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@KENSHU_SAMPLING_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SCORE_SAMP_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID">
			<tr><td colspan="5" >&nbsp;</td></tr>
				<tr class="box_less_width_input">
					<td colspan="5">
						<span class="table_center_heading"><s:text name="label.scoring.punchtext" /></span>
					</td>
				</tr>
				<tr><td colspan="5" style="border: none;">&nbsp;</td></tr>
			<tr><td colspan="5" class="wordwrap" style="border: none;"><pre style="margin-left: 5px;"><s:property value="#session.tranDescScoreInfo.answerInfo.punchText"/></pre></td></tr>
		</s:if>
	 <s:if test="!(#session.questionInfo.questionType == @com.saiten.util.WebAppConst@SPEAKING_TYPE || #session.questionInfo.questionType == @com.saiten.util.WebAppConst@WRITING_TYPE)">
		<s:if test="(bookmarkScreenFlag == true || #session.historyScreenFlag == true) || (#session.questionInfo.menuId == @com.saiten.util.WebAppConst@CHECKING_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@INSPECTION_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@DENY_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@KENSHU_SAMPLING_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SCORE_SAMP_MENU_ID || (#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID  && #session.tranDescScoreInfo.scoringState != @com.saiten.util.WebAppConst@MISMATCH_STATES[0]))">
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td class="big_result_blue" colspan="5">
					<s:set id="backToScoringFlag" value="#parameters['backToScoringFlag'][0]"></s:set>
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
						
					</s:else>
				</td>
			</tr>
		</s:if>
	</s:if>
		<s:if test="@org.apache.commons.lang.ArrayUtils@contains(@com.saiten.util.WebAppConst@APROVE_STATES, #session.tranDescScoreInfo.scoringState) || @org.apache.commons.lang.ArrayUtils@contains(@com.saiten.util.WebAppConst@DENY_STATES, #session.tranDescScoreInfo.scoringState)">
				<tr>
					<td class="big_result_blue" colspan="5">
						<s:if test="@org.apache.commons.lang.ArrayUtils@contains(@com.saiten.util.WebAppConst@APROVE_STATES, #session.tranDescScoreInfo.scoringState)">
							<s:text name="label.approve"></s:text>
						</s:if><s:elseif test="@org.apache.commons.lang.ArrayUtils@contains(@com.saiten.util.WebAppConst@DENY_STATES, #session.tranDescScoreInfo.scoringState)">
							<s:text name="label.deny"></s:text>
						</s:elseif>
					</td>
				</tr>
		</s:if>
		<tr><td colspan="5"><br /><s:hidden name="checkpointerror" id="checkpointerror" />
		<div id="myErrorMessages" style="color: red;"></div>
		</td></tr>
		
		<s:if test="bookmarkScreenFlag != true">
			<tr>
				<td colspan="5" style="border-bottom: none;">
					<div id="bottom_row" style="padding-top: 15px;">
						<div class="buttons">
							<s:if test="!(#session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@KENSHU_SAMPLING_MENU_ID)">
								<div id="popup-wrapper" style="background-color:#2D2D2D;">
									<jsp:include page="include/confirmScore.jsp"/>
								</div>
							</s:if> 
							<s:if test="!(#session.questionInfo.menuId == @com.saiten.util.WebAppConst@CHECKING_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@INSPECTION_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_BLIND_TYPE_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_LANGUAGE_SUPPORT_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@KENSHU_SAMPLING_MENU_ID)"> <!-- || ((#session.questionInfo.menuId == @com.saiten.util.WebAppConst@PENDING_MENU_ID) && (#session.questionInfo.questionType == @com.saiten.util.WebAppConst@SPEAKING_TYPE || #session.questionInfo.questionType == @com.saiten.util.WebAppConst@WRITING_TYPE)) -->
								<div id="pendingpopup-wrapper" style="background-color:#2D2D2D;">
									<jsp:include page="include/confirmPending.jsp"/>
								</div>
							</s:if> 
							<%-- <s:if test="!(#session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_BLIND_TYPE_MENU_ID || 
							#session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_LANGUAGE_SUPPORT_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID || 
							#session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID  || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@FIRST_SCORING_MENU_ID || 
							#session.questionInfo.menuId == @com.saiten.util.WebAppConst@SECOND_SCORING_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@PENDING_MENU_ID ||
							#session.questionInfo.menuId == @com.saiten.util.WebAppConst@SCORE_SAMP_MENU_ID) || #session.questionInfo.menuId =  @com.saiten.util.WebAppConst@DENY_MENU_ID"> 
								<div id="denypopup-wrapper" style="background-color:#2D2D2D;">
									<jsp:include page="include/confirmDeny.jsp"/>
								</div>
							</s:if> --%>
							<%-- <s:if test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@CHECKING_MENU_ID">
								<div id="denypopup-wrapper" style="background-color:#2D2D2D;">
									<jsp:include page="include/confirmDeny.jsp"/>
								</div>
							</s:if>
							<s:if test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@INSPECTION_MENU_ID">
								<div id="denypopup-wrapper" style="background-color:#2D2D2D;">
									<jsp:include page="include/confirmDeny.jsp"/>
								</div>
							</s:if> --%>
						</div>
				   <table><tr><td style="border-bottom: none;">	
				 	<s:if test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@CHECKING_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@INSPECTION_MENU_ID">
					<a>
						<%-- <s:submit accesskey="s" type="image" src="./material/img/button/approve.gif" cssStyle="width:99px; height:39px;" cssClass="rollover" label="%{getText('label.approve')}" name="approve" id="approve"/> --%>
						<s:submit accesskey="s" type="button" cssStyle="width:99px; height:39px;" cssClass="btn btn-primary btn-scoring-sm" label="%{getText('label.approve')}" name="approve" id="approve" />
					</a>
					<a>
		  				<%-- <s:submit accesskey="h" type="image" src="./material/img/button/deny.gif" cssStyle="width:99px; height:39px;" cssClass="rollover" label="%{getText('label.deny')}" name="deny" id="deny"/> --%>
		  				<s:submit accesskey="h" type="button" cssStyle="width:99px; height:39px;" cssClass="btn btn-primary btn-scoring-sm" label="%{getText('label.deny')}" name="deny" id="deny"/>
					</a>
					</s:if>
					
					<s:elseif test="(#session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_BLIND_TYPE_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_LANGUAGE_SUPPORT_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID )"> <!-- || ((#session.questionInfo.menuId == @com.saiten.util.WebAppConst@PENDING_MENU_ID) && (#session.questionInfo.questionType == @com.saiten.util.WebAppConst@SPEAKING_TYPE || #session.questionInfo.questionType == @com.saiten.util.WebAppConst@WRITING_TYPE)) -->
						<a>
							<%-- <s:submit accesskey="s" type="image" src="./material/img/button/input_bn-login.gif" cssStyle="width:99px; height:39px;" cssClass="rollover" label="%{getText('btn.scoring.alt.saiten')}" name="score" id="score"/> --%>
							<s:submit accesskey="s" type="button" cssStyle="width:99px; height:39px;" cssClass="btn btn-primary btn-scoring-sm" label="%{getText('btn.scoring.alt.saiten')}" name="score" id="score" />
						</a>
					</s:elseif>
					<s:elseif  test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID">
						<s:if  test="#session.questionInfo.prevRecordCount > @com.saiten.util.WebAppConst@ZERO">
							<a href="showAnswerDetails.action?prevOrNextFlag=<s:property value="%{@com.saiten.util.WebAppConst@TRUE}" />" id="prev" name="prev" class="btn btn-primary btn-scoring-sm" style="width:99px; height:39px;"><!-- <img class="rollover" src="./material/img/button/back.gif" alt="%{getText('label.cancel')}" /> --><s:text name="label.previous"></s:text></a>
						</s:if>
						<s:else>
							<!-- <img src="./material/img/button/back.gif" alt="%{getText('btn.scoring.alt.saiten')}" /> -->
							<p class="btn btn-disabled" style="width:97px; height:37px;margin-top: 4px;"><s:text name="label.previous"></s:text></p>
						</s:else>
						
						<s:if  test="#session.questionInfo.nextRecordCount > @com.saiten.util.WebAppConst@ZERO">
			  				<a href="showAnswerDetails.action?prevOrNextFlag=<s:property value="%{@com.saiten.util.WebAppConst@FALSE}" />" id="next" name="next" class="btn btn-primary btn-scoring-sm" style="width:99px; height:39px;"><!-- <img class="rollover" src="./material/img/button/next.gif" alt="%{getText('label.next')}" /> --><s:text name="label.next"></s:text></a>
			  			</s:if>
			  			<s:else>
							<!-- <img src="./material/img/button/next.gif" alt="%{getText('label.next')}" /> -->
							<p class="btn btn-disabled" style="width:97px; height:37px;margin-top: 4px;"><s:text name="label.next"></s:text></p>
						</s:else>						
					</s:elseif>
					<s:elseif  test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@KENSHU_SAMPLING_MENU_ID">
						<s:if  test="#session.questionInfo.prevRecordCount > @com.saiten.util.WebAppConst@ZERO">
							<a href="showKenshuAnswerDetails.action?prevOrNextFlag=<s:property value="%{@com.saiten.util.WebAppConst@TRUE}" />" id="prev" name="prev" class="btn btn-primary btn-scoring-sm" style="width:99px; height:39px;"><!-- <img class="rollover" src="./material/img/button/back.gif" alt="%{getText('label.cancel')}" /> --><s:text name="label.previous"></s:text></a>
						</s:if>
						<s:else>
							<!-- <img src="./material/img/button/back.gif" alt="%{getText('btn.scoring.alt.saiten')}" /> -->
							<p class="btn btn-disabled" style="width:97px; height:37px;margin-top: 4px;"><s:text name="label.previous"></s:text></p>
						</s:else>
						
						<s:if  test="#session.questionInfo.nextRecordCount > @com.saiten.util.WebAppConst@ZERO">
			  				<a href="showKenshuAnswerDetails.action?prevOrNextFlag=<s:property value="%{@com.saiten.util.WebAppConst@FALSE}" />" id="next" name="next" class="btn btn-primary btn-scoring-sm" style="width:99px; height:39px;"><!-- <img class="rollover" src="./material/img/button/next.gif" alt="%{getText('label.next')}" /> --><s:text name="label.next"></s:text></a>
			  			</s:if>
			  			<s:else>
							<!-- <img src="./material/img/button/next.gif" alt="%{getText('label.next')}" /> -->
							<p class="btn btn-disabled" style="width:97px; height:37px;margin-top: 4px;"><s:text name="label.next"></s:text></p>
						</s:else>						
					</s:elseif>
					<s:else>
						<a>
							<%-- <s:submit accesskey="s" type="image" src="./material/img/button/input_bn-login.gif" cssStyle="width:99px; height:39px;" cssClass="rollover" label="%{getText('btn.scoring.alt.saiten')}" name="score" id="score" /> --%>
							<s:submit accesskey="s" type="button" cssStyle="width:99px; height:39px;" cssClass="btn btn-primary btn-scoring-sm" label="%{getText('btn.scoring.alt.saiten')}" name="score" id="score" />
						</a>
						<a>
							<%-- <s:submit accesskey="h" type="image" src="./material/img/button/input1_bn-login.gif" cssStyle="width:99px; height:39px;" cssClass="rollover" label="%{getText('btn.scoring.alt.pending')}" name="pending" id="pending"/> --%>
			  				<s:submit accesskey="h" type="button" cssStyle="width:99px; height:39px;" cssClass="btn btn-primary btn-scoring-sm" label="%{getText('btn.scoring.alt.pending')}" name="pending" id="pending"/>
						</a>
					</s:else>
					</td><td style="border-bottom: none;"><table><tr><td style="font-size: 14px;border-bottom: none;">	
					<s:if test="bookmarkScreenFlag != true && !(#session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID) && !(#session.questionInfo.menuId == @com.saiten.util.WebAppConst@KENSHU_SAMPLING_MENU_ID)">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<span style="vertical-align: top;font-size: 14px;">
						<s:if test="(!(#session.qcAnswerSeqList.isEmpty()) && #session.isQcRecord && #session.tranDescScoreInfo.answerInfo.historySeq == null) || (#session.tranDescScoreInfo.answerInfo.qcSeq != null)">
							<input  type="checkbox" name="bookMark" id="bookMark" disabled="disabled" style="vertical-align: top;"/>
						</s:if><s:elseif test="#session.tranDescScoreInfo.answerInfo.bookMarkFlag == @com.saiten.util.WebAppConst@BOOKMARK_FLAG_TRUE">
							<input type="checkbox" name="bookMark" id="bookMark" checked="checked" style="vertical-align: top;"/>
						</s:elseif><s:else>
							<input  type="checkbox" name="bookMark" id="bookMark" style="vertical-align: top;"/>
						</s:else>
						<s:text name="label.scoring.bookmark" /><img src="./material/img/bookmark.png" style="vertical-align: top;">
						</span>
					</s:if>
					 </td></tr>
					 
					 
					 
					 
					 <tr><td style="font-size: 14px;border-bottom: none;">
					 <!-- removed writting and speaking condition for saitama release. -->
					<s:if test="((#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FIRST_SCORING_QUALITY_CHECK_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID))">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<span style="vertical-align: top;font-size: 14px;">
						<s:if test="#session.tranDescScoreInfo.answerInfo.qualityCheckFlag == @com.saiten.util.WebAppConst@QUALITY_MARK_FLAG_TRUE">
							<input type="checkbox" name="qualityMark" id="qualityMark" checked="checked" style="vertical-align: top;"/>
						</s:if><s:else>
							<input  type="checkbox" name="qualityMark" id="qualityMark" style="vertical-align: top;"/>
						</s:else>
						<s:text name="label.scoring.qualitymark" />
						</span>
			     	</s:if>
					</td></tr>
					
					<tr><td style="font-size: 14px;border-bottom: none;">	
					<s:if test="(#session.questionInfo.menuId == @com.saiten.util.WebAppConst@KENSHU_SAMPLING_MENU_ID) && (#session.samplingSearch == @com.saiten.util.WebAppConst@ACCEPTANCE_DISPLAY)">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<span style="vertical-align: top;font-size: 14px;">
						<%-- <s:if test="(!(#session.qcAnswerSeqList.isEmpty()) && #session.isQcRecord && #session.tranDescScoreInfo.answerInfo.historySeq == null) || (#session.tranDescScoreInfo.answerInfo.qcSeq != null)">
							<input  type="checkbox" name="bookMark" id="bookMark" disabled="disabled" style="vertical-align: top;"/>
						</s:if><s:elseif test="#session.tranDescScoreInfo.answerInfo.bookMarkFlag == @com.saiten.util.WebAppConst@BOOKMARK_FLAG_TRUE">
							<input type="checkbox" name="bookMark" id="bookMark" checked="checked" style="vertical-align: top;"/>
						</s:elseif><s:else> --%>
						<s:if test="#session.tranAcceptance.explainFlag == @com.saiten.util.WebAppConst@VALID_FLAG">
							<input  type="checkbox" name="bookMark" id="bookMark" style="vertical-align: top;" checked="checked" onclick="checkUncheckFlag();" disabled="disabled"/>
						</s:if> <s:else>
							<input  type="checkbox" name="bookMark" id="bookMark" style="vertical-align: top;" onclick="checkUncheckFlag();"/>
						</s:else>
						<%-- </s:else> --%>
						<s:text name="label.kenshu.explain.flag" />
						</span>
					</s:if>
					 </td></tr>
					
					</table></td></tr></table>
				</div>
				</td>
			</tr>	
		</s:if>
		<%-- <tr>
					<s:if test="(#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FIRST_SCORING_QUALITY_CHECK_MENU_ID) || (#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID)">
						
						<span style="vertical-align: top;">
						<s:if test="#session.tranDescScoreInfo.answerInfo.qualityCheckFlag == @com.saiten.util.WebAppConst@QUALITY_MARK_FLAG_TRUE">
							<input type="checkbox" name="qualityMark" id="qualityMark" checked="checked" style="vertical-align: top;"/>
						</s:if><s:else>
							<input  type="checkbox" name="qualityMark" id="qualityMark" style="vertical-align: top;"/>
						</s:else>
						<s:text name="label.scoring.qualitymark" />
						</span>
			
					</s:if>
		</tr> --%>			
		<tr><td colspan="5" >&nbsp;</td></tr>
		<tr class="box_less_width_input">
			<td colspan="5">
				<span class="table_center_heading"><s:text name="label.scoring.comment" /></span>
			</td>
		</tr>
		
		<tr>
		<td colspan="5">
			<p class="comment_area">
				<s:if test="bookmarkScreenFlag == true">
					<s:textarea  name="scorerComment" id="scorerComment" value="%{#session.tranDescScoreInfo.answerInfo.scorerComment}" disabled="true"></s:textarea>					
				</s:if>
				<s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@KENSHU_SAMPLING_MENU_ID">
					<s:if test="#session.samplingSearch == @com.saiten.util.WebAppConst@ACCEPTANCE_DISPLAY">
						<s:textarea  name="scorerComment" id="scorerComment" value="%{getText('label.scoring.openingbracket') + getText('label.kenshu.userId') + getText('label.colon') + #session.tranAcceptance.markBy + getText('label.scoring.closingbracket')}" disabled="true"></s:textarea>	
					</s:if>
					<s:elseif test="(#session.tranDescScoreInfo.latestScreenScorerId == '') || (#session.tranDescScoreInfo.latestScreenScorerId == null)" >
							<s:textarea  name="scorerComment" id="scorerComment" value="" disabled="true"></s:textarea>
						</s:elseif><s:else>
									<s:if test="((#session.tranDescScoreInfo.answerInfo.scorerComment != null) && (#session.tranDescScoreInfo.answerInfo.scorerComment != ''))"> 
												<s:textarea  name="scorerComment" id="scorerComment" value="%{getText('label.scoring.openingbracket') + getText('label.scoresearch.scorerid') + getText('label.colon') + #session.tranDescScoreInfo.latestScreenScorerId + getText('label.scoring.closingbracket') + '  ' + #session.tranDescScoreInfo.answerInfo.scorerComment}" disabled="true"></s:textarea>
									</s:if><s:else>
												<s:textarea  name="scorerComment" id="scorerComment" value="%{getText('label.scoring.openingbracket') + getText('label.scoresearch.scorerid') + getText('label.colon') + #session.tranDescScoreInfo.latestScreenScorerId + getText('label.scoring.closingbracket')}" disabled="true"></s:textarea>
									</s:else>
							</s:else>
				</s:elseif>
				<s:else>
					<s:if  test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID">
						<s:if test="(#session.tranDescScoreInfo.latestScreenScorerId == '') || (#session.tranDescScoreInfo.latestScreenScorerId == null)" >
							<s:textarea  name="scorerComment" id="scorerComment" value="" disabled="true"></s:textarea>
						</s:if><s:else>
									<s:if test="((#session.tranDescScoreInfo.answerInfo.scorerComment != null) && (#session.tranDescScoreInfo.answerInfo.scorerComment != ''))"> 
												<s:textarea  name="scorerComment" id="scorerComment" value="%{getText('label.scoring.openingbracket') + getText('label.scoresearch.scorerid') + getText('label.colon') + #session.tranDescScoreInfo.latestScreenScorerId + getText('label.scoring.closingbracket') + '  ' + #session.tranDescScoreInfo.answerInfo.scorerComment}" disabled="true"></s:textarea>
									</s:if><s:else>
												<s:textarea  name="scorerComment" id="scorerComment" value="%{getText('label.scoring.openingbracket') + getText('label.scoresearch.scorerid') + getText('label.colon') + #session.tranDescScoreInfo.latestScreenScorerId + getText('label.scoring.closingbracket')}" disabled="true"></s:textarea>
									</s:else>
							</s:else>
					</s:if>
					<s:else>
						<s:textarea  name="scorerComment" id="scorerComment" value="%{#session.tranDescScoreInfo.answerInfo.scorerComment}" maxlength="512"></s:textarea>
					</s:else>
				</s:else>
			</p>
		</td>
		</tr>
		

		</table>

		<s:if test="bookmarkScreenFlag == true">
			<div id="bottom_row" style="margin-top: 30px;">
			<div class="buttons">
			<s:if test="bookmarkScreenFlag == true ">
				<s:if test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID">
					<a href="bookmarkListing.action?d-1857439-p=${a}&forcedScoringFlag=true" class="btn btn-primary btn-xl">
						<%-- <img src="./material/img/button/bookmark_bn-login.gif" class="rollover" alt="<s:text name="label.alt.backToBookmarkScreen"></s:text>"> --%>
						<s:text name="label.alt.backToBookmarkScreen"></s:text>
					</a>
				</s:if><s:else>
					<a href="bookmarkListing.action?d-1857439-p=${a}" class="btn btn-primary btn-xl">
						<%-- <img src="./material/img/button/bookmark_bn-login.gif" class="rollover" alt="<s:text name="label.alt.backToBookmarkScreen"></s:text>"> --%>
						<s:text name="label.alt.backToBookmarkScreen"></s:text>
					</a>
				</s:else>
				
			<s:if test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@SCORE_SAMP_MENU_ID">
				<a href="resetScoreHistory.action?backToScoringFlag=<s:property value="%{@com.saiten.util.WebAppConst@TRUE}" />" class="btn btn-primary btn-xl">
					<%-- <img src="./material/img/button/bklist1_bn-login.gif" class="rollover" alt="<s:text name="label.button.resetScoreHistory"></s:text>"> --%>
					<s:text name="label.button.resetScoreHistory"></s:text>
				</a>
			</s:if><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID && #session.tranDescScoreInfoCopy != null">
				<a href="resetScoreHistory.action" class="btn btn-primary btn-xl">
					<%-- <img src="./material/img/button/bklist1_bn-login.gif" class="rollover" alt="<s:text name="label.button.resetScoreHistory"></s:text>"> --%>
					<s:text name="label.button.resetScoreHistory"></s:text>
				</a>
			</s:elseif><s:elseif test="!(#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID)">
				<a href="resetScoreHistory.action" class="btn btn-primary btn-xl">
					<%-- <img src="./material/img/button/bklist1_bn-login.gif" class="rollover" alt="<s:text name="label.button.resetScoreHistory"></s:text>"> --%>
					<s:text name="label.button.resetScoreHistory"></s:text>
				</a>
			</s:elseif><s:else>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</s:else>
				<span style="vertical-align: top;font-size: 14px;">
				   <!-- removed writting and speaking condition for saitama release. -->
				  <%--  <s:if test="#session.questionInfo.questionType == @com.saiten.util.WebAppConst@SPEAKING_TYPE || #session.questionInfo.questionType == @com.saiten.util.WebAppConst@WRITING_TYPE"> --%>
				   			<s:if test="#session.tranDescScoreInfo.answerInfo.qualityCheckFlag == @com.saiten.util.WebAppConst@QUALITY_MARK_FLAG_TRUE">
								<input type="checkbox" name="qualityMark" id="qualityMark" checked="checked" disabled="disabled" style="vertical-align: top;width: inherit;"/>
							</s:if><s:else>
								<input  type="checkbox" name="qualityMark" id="qualityMark" disabled="disabled" style="vertical-align: top;width: inherit;"/>
							</s:else>
							&nbsp;<s:text name="label.scoring.qualitymark" />
				 <%--   </s:if> --%>
				</span>
			 </s:if>
			</div>
		</div>
		</s:if>
		<s:if test="#session.historyScreenFlag == true">
			<div id="bottom_row" style="margin-top: 30px;">
			<div class="buttons">
						<s:if test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID">
							<a href="historyListing.action?d-6209173-p=${a}&forcedScoringFlag=true" style="text-decoration: none;border: none;" class="btn btn-primary btn-xl">
								<%-- <img src="./material/img/button/history_bn-login.gif" class="rollover" alt="<s:text name="label.alt.backToHistoryScreen"></s:text>"> --%>
								<s:text name="label.alt.backToHistoryScreen"></s:text>
							</a>
						</s:if><s:else>
							<a href="historyListing.action?d-6209173-p=${a}" style="text-decoration: none;border: none;" class="btn btn-primary btn-xl">
								<%-- <img src="./material/img/button/history_bn-login.gif" class="rollover" alt="<s:text name="label.alt.backToHistoryScreen"></s:text>"> --%>
								<s:text name="label.alt.backToHistoryScreen"></s:text>
							</a>
						</s:else>
						
						<s:if test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@SCORE_SAMP_MENU_ID">
							<a href="resetScoreHistory.action?backToScoringFlag=<s:property value="%{@com.saiten.util.WebAppConst@TRUE}" />"" style="text-decoration: none;border: none;" class="btn btn-primary btn-xl">
								<%-- <img src="./material/img/button/bklist1_bn-login.gif" class="rollover" alt="<s:text name="label.button.resetScoreHistory"></s:text>"> --%>
								<s:text name="label.button.resetScoreHistory"></s:text>
							</a>
						</s:if><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID && #session.tranDescScoreInfoCopy != null">
								<a href="resetScoreHistory.action" style="text-decoration: none;border: none;" class="btn btn-primary btn-xl">
									<%-- <img src="./material/img/button/bklist1_bn-login.gif" class="rollover" alt="<s:text name="label.button.resetScoreHistory"></s:text>"> --%>
									<s:text name="label.button.resetScoreHistory"></s:text>
								</a>
						</s:elseif><s:elseif test="!(#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID)">
								<a href="resetScoreHistory.action" style="text-decoration: none;border: none;" class="btn btn-primary btn-xl">
									<%-- <img src="./material/img/button/bklist1_bn-login.gif" class="rollover" alt="<s:text name="label.button.resetScoreHistory"></s:text>"> --%>
									<s:text name="label.button.resetScoreHistory"></s:text>
								</a>
						</s:elseif>
			</div>
		</div>	
		</s:if>
		<s:if test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@KENSHU_SAMPLING_MENU_ID  || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SCORE_SAMP_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_BLIND_TYPE_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_LANGUAGE_SUPPORT_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID">
			<div id="bottom_row" style="margin-top: 0px;">
				<div class="buttons">
				<s:if test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID">
				<a href="scoreSearch.action?selectedMenuId=REFERENCE_SAMP_MENU_ID" id="back" name="back" class="btn btn-primary btn-xl"><!-- <img class="rollover" src="./material/img/button/sampling_bn-back.gif" alt="%{getText('label.backtosearch')}" /> --><s:text name="label.backtosearch"></s:text></a>
			</s:if><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@SCORE_SAMP_MENU_ID">
				<a href="scoreSearch.action?selectedMenuId=SCORE_SAMP_MENU_ID" id="back" name="back" class="btn btn-primary btn-xl"><!-- <img class="rollover" src="./material/img/button/sampling_bn-back.gif" alt="%{getText('label.backtosearch')}" /> --><s:text name="label.backtosearch"></s:text></a>
				<s:if test="#session.recordNumber < #session.answerRecordsList.size()" >
			  		<a href="scoreSampling?prevOrNextFlag=<s:property value="%{@com.saiten.util.WebAppConst@FALSE}" />" id="next" name="next" class="btn btn-primary btn-xl" style="width:99px;margin-left:150px"><!-- <img class="rollover" src="./material/img/button/next.gif" alt="%{getText('label.next')}" /> --><s:text name="label.next"></s:text></a>
				</s:if>	
				<s:else>
					<p class="btn btn-disabled" style="width:99px;margin-left:150px"><s:text name="label.next"></s:text></p>
				</s:else>
			</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID">
				<a href="scoreSearch.action?selectedMenuId=FORCED_MENU_ID" id="back" name="back" class="btn btn-primary btn-xl"><!-- <img class="rollover" src="./material/img/button/sampling_bn-back.gif" alt="%{getText('label.backtosearch')}" /> --><s:text name="label.backtosearch"></s:text></a>
				<a href="backToForcedScoringList.action" id="back" name="back" class="btn btn-primary btn-xl"><!-- <img class="rollover" src="./material/img/button/search1_bn-login.gif" alt="%{getText('label.backtosearch')}" /> --><s:text name="label.alt.backtoList"></s:text></a>
				<a id="processDetails" name="processDetails" style="width:100px;height:25px;border-radius:6px;" class="btn btn-primary"><s:text name="label.processDetails"/></a>
			</s:elseif><s:elseif test="(#session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_BLIND_TYPE_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_LANGUAGE_SUPPORT_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID)">
				<a href="updateSpecialScoringMap.action?lockFlag=true" id="back" name="back" class="btn btn-primary btn-xl">
					<!-- <img class="rollover" src="./material/img/button/sampling_bn-back.gif" alt="%{getText('label.backtosearch')}" /> -->
					<s:text name="label.backtosearch"></s:text>
				</a>
			</s:elseif>
			<s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@KENSHU_SAMPLING_MENU_ID">
				<a href="kenshuSampling?selectedMenuId=<s:property value="@com.saiten.util.WebAppConst@KENSHU_SAMPLING_MENU_ID"/>" id="back" name="back" class="btn btn-primary btn-xl"><s:text name="label.back.to.gradwise.list"></s:text></a>
				<a href="showKenshuSampling?selectedMenuId=<s:property value="@com.saiten.util.WebAppConst@KENSHU_SAMPLING_MENU_ID"/>&sessionClearFlag=<s:property value="@com.saiten.util.WebAppConst@FALSE"/>" id="back" name="back" class="btn btn-primary btn-xl"><s:text name="label.backtosearch"></s:text></a>
			</s:elseif>
			</div>
			</div>	
		</s:if>
		</div>
		</div>
		</div>
		</div>
		</td>	   
		</tr>
		</table>
		</form>
		</div>
		</div>
	</div>
</body>
</html>