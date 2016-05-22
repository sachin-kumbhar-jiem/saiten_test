﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Language" content="ja">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<title><s:text name="informationPage.title" /></title>
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
<script language="JavaScript">
	javascript:window.history.forward(1);
</script>
<script language="JavaScript">
function enableLinksAndButtons(){
	$(':button').prop('disabled', false); // Enable all the button
	$('a').unbind('click'); // Enable all the links
}
</script>

<script language="JavaScript">
	function setFoucs() {
		var nextButton = document.getElementById('scoreSamplingNext');
		if (nextButton != null) {
			nextButton.focus();
		}
	}
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

<body class="<s:property value="%{#bodyColor}"/>" onload="enableLinksAndButtons(); setFoucs();">
<div>
	<div id="wrapper">
		<div id="contents" class="text14">
		<div></div>
			<form  name="scoreActionForm" id="scoreActionForm" action="scoreAction" method="post">
			<table>
		     <tr>
			  <!-- 1st td --> 
		        <td colspan="2" class="table-side-menu-td" width="6%" style="text-align: center;">
			       <div id="side_menu"> <!-- 1st td --> 
				     <div class="side_menu_top_margin">
					  <p class="side_menu_heading_color">
							<s:property value="#session.scorerInfo.roleDescription" /><br>
							<s:property value="#session.scorerInfo.scorerId" /><br/>
					  <p class="side_menu_alignment"><s:text name="label.menu" />
					   <div class="green_menu"><!-- 3rd td --> 
							
							<input type="button" name="submit" value="<s:text name="label.backtosaitenmenu" />" class="menu_button"
							 onclick="javascript:location.href='userMenu.action';" />
							
							<s:if  test="!(#session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@STATE_TRAN_MENU_ID)">
								<s:if test="#session.questionInfo.questionSeq != @com.saiten.util.WebAppConst@ZERO">
									<a href="<s:url action="historyListing" includeParams="none" />" class="menu_button"><s:text name="label.historylist" /></a><p>
									<a href="<s:url action="bookmarkListing" includeParams="none" />" class="menu_button"><s:text name="label.bookmarklist" /></a><p>	
								</s:if>
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
					
					<s:if  test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_BLIND_TYPE_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_LANGUAGE_SUPPORT_MENU_ID">
						<div class="green_menu">
							<p class="menu_button_without_cursor"><s:text name="label.scoring.questionnumber" /></p>
							<p class="menu_button_without_cursor" style="width: 80px; word-break: break-all;" >
								<span class="big_font"><s:property value="#session.questionInfo.subjectShortName.split('-')[0]" /></span>
							</p>
						</div>
					</s:if>
					<s:else>
						<div class="green_menu">
							<p class="menu_button_without_cursor"><s:text name="label.scoring.questionnumber" /></p>
							<p class="menu_button_without_cursor" style="width: 80px; word-break: break-all;" >
								<span class="menu_button_without_cursor"><s:property value="#session.questionInfo.subjectShortName" /></span>
							</p>
						</div>
					</s:else>
					<s:if  test="!(#session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@STATE_TRAN_MENU_ID)">
						<br/>
						<div class="green_menu">
							<p class="menu_button_without_cursor"><s:text name="label.scoring.pregrading" /></p>
							<p class="menu_button_without_cursor">
								<s:if  test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID">
									<s:property value="#session.historyRecordCount" /><s:text name="label.scoring.pregradingcount" />
								</s:if>
								<s:else>
									<s:property value="#session.questionInfo.historyRecordCount" /><s:text name="label.scoring.pregradingcount" />
								</s:else>
							</p>
						</div>
					</s:if>
			   </div>
		      </td>
			   
			   <!-- 2nd td --> 
			   
			   <td class="table-center-menu-td-errorpage">
			   <!-- Start left pane -->
		<div id="content_left_pane">
		    <div class="box_content_left_pane_input_normal_error_page">
			  <div class="div_center_heading"></div>
			  <p class="score_img_top"></p>
		    </div>
		
			<div style="margin-top: 40%; width: 934px">
				
				<s:if test="#parameters['decline'][0] == @java.lang.String@valueOf(@com.saiten.util.WebAppConst@TRUE)">
					<span class="errorMessage"><s:text name="label.answernotavailable.specialansweralreadyscored" /></span>
					<br><br>
					<a href="specialScoreSearch.action?selectedMenuId=SPECIAL_SAMP_INSPECTION_MENU_ID" id="back" name="back" class="btn btn-primary btn-xl"><!-- <img class="rollover" src="./material/img/button/sampling_bn-back.gif" alt="%{getText('label.backtosearch')}" /> --><s:text name="label.backtosearch"></s:text></a>
				</s:if>
				<s:elseif test="#parameters['lockFlag'][0] == @java.lang.String@valueOf(@com.saiten.util.WebAppConst@TRUE)">
					<span class="errorMessage"><s:text name="label.answernotavailable.answeralreadyscored" /></span>
					<s:if test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@SCORE_SAMP_MENU_ID">
						<br><br>
						<a href="scoreSearch.action?selectedMenuId=SCORE_SAMP_MENU_ID" id="back" name="back" class="btn btn-primary btn-xl"><!-- <img class="rollover" src="./material/img/button/sampling_bn-back.gif" alt="%{getText('label.backtosearch')}" /> --><s:text name="label.backtosearch"></s:text></a>
						&nbsp;&nbsp;
						<a  href="scoreSampling.action" class="btn btn-primary btn-xl" id="scoreSamplingNext"><!-- <img class="rollover" src="./material/img/button/next_button.gif" alt="%{getText('label.next')}"/> --><s:text name="label.next"></s:text></a>
					</s:if>
					<s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID">
						<br><br>
						<a href="scoreSearch.action?selectedMenuId=FORCED_MENU_ID" id="back" name="back" class="btn btn-primary btn-xl"><!-- <img class="rollover" src="./material/img/button/sampling_bn-back.gif" alt="%{getText('label.backtosearch')}" /> --><s:text name="label.backtosearch"></s:text></a>
						&nbsp;&nbsp;
						<a href="backToForcedScoringList.action" class="btn btn-primary btn-xl"><!-- <img class="rollover" src="./material/img/button/search1_bn-login.gif" alt="%{getText('label.next')}"/> --><s:text name="label.next"></s:text></a>
					</s:elseif>
					<s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_BLIND_TYPE_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_LANGUAGE_SUPPORT_MENU_ID">
						<br><br>
						<a href="updateSpecialScoringMap.action" id="back" name="back" class="btn btn-primary btn-xl"><!-- <img class="rollover" src="./material/img/button/sampling_bn-back.gif" alt="%{getText('label.backtosearch')}" /> --><s:text name="label.backtosearch"></s:text></a>
						&nbsp;&nbsp;
						<a href="specialSampling.action" class="btn btn-primary btn-xl"><!-- <img class="rollover" style="width: 180px; height: 39px" src="./material/img/button/next_button.gif" alt="%{getText('label.next')}"/> --><s:text name="label.next"></s:text></a>
					</s:elseif>
					<s:else>
						<%-- <div style="margin-top: 2%;"><s:submit type="image" src="./material/img/button/next_button.gif" cssClass="rollover" label="%{getText('label.next')}"></s:submit></div> --%>
						<div style="margin-top: 2%;"><s:submit type="button" cssClass="btn btn-primary btn-xl" cssStyle="width: 144px; height: 39px;" label="%{getText('label.next')}"></s:submit></div>
					</s:else>
				</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@STATE_TRAN_MENU_ID && totalRecordsCount !=null">
							<s:if test="updatedCount != totalRecordsCount">
								<span class="errorMessage"><s:property value="updatedCount"/><s:text name="label.forwardslash"/><s:property value="totalRecordsCount"/><s:text name="label.statetransition.recordsUpdatedAndSomeRecordsInProgress" /></span>
							</s:if><s:else>
						<span class="errorMessage"><s:property value="updatedCount"/><s:text name="label.forwardslash"/><s:property value="totalRecordsCount"/><s:text name="label.statetransition.recordsUpdated" /></span><br>
						<span class="errorMessage">
							<s:set var="locale" value="getText('label.locale')"/>
							<s:if test="#locale == 'ja'">
								<s:text name="label.statetransition.inspectgroupseqmsg1" /><s:property value="#session.inspectGroupSeq"/><s:text name="label.statetransition.inspectgroupseqmsg2" />
							</s:if>
							<s:else>
								<s:text name="label.statetransition.inspectgroupseqmsg" /><s:property value="@com.saiten.util.WebAppConst@SINGLE_SPACE"/><s:property value="#session.inspectGroupSeq"/>
							</s:else>
						</span>
						</s:else>
						<br><br>
						<a href="showSearchList.action?forceAndStateTransitionFlag=<s:property value="%{@com.saiten.util.WebAppConst@TRUE}" />" id="back" name="back" class="btn btn-primary btn-xl"><!-- <img class="rollover" src="./material/img/button/search1_bn-login.gif" alt="%{getText('label.backtosearch')}" /> --><s:text name="label.alt.backtoList"></s:text></a>
						&nbsp;&nbsp;
							<a href="scoreSearch.action?selectedMenuId=<s:property value="@com.saiten.util.WebAppConst@STATE_TRAN_MENU_ID"/>" id="back" name="back" class="btn btn-primary btn-xl"><!-- <img class="rollover" src="./material/img/button/sampling_bn-back.gif" alt="%{getText('label.backtosearch')}" /> --><s:text name="label.backtosearch"></s:text></a>
					</s:elseif>
				<s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@KENSHU_SAMPLING_MENU_ID">
						<span class="errorMessage"><s:text name="error.message.kenshu" /></span>
						<br><br>
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
						<br><br>
						<a href="showKenshuSampling.action?selectedMenuId=<s:property value="@com.saiten.util.WebAppConst@KENSHU_SAMPLING_MENU_ID"/>&sessionClearFlag=<s:property value="@com.saiten.util.WebAppConst@FALSE"/>" id="back" name="back" class="btn btn-primary btn-xl"><s:text name="label.backtosearch"></s:text></a>
					</s:elseif>
				<s:else> 
					<span class="errorMessage"><s:text name="label.answernotavailable.norecordfound" /></span>
					<s:if test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID">
						<br><br>
						<a href="scoreSearch.action?selectedMenuId=REFERENCE_SAMP_MENU_ID" id="back" name="back" class="btn btn-primary btn-xl"><!-- <img class="rollover" src="./material/img/button/sampling_bn-back.gif" alt="%{getText('label.backtosearch')}" /> --><s:text name="label.backtosearch"></s:text></a>
					</s:if>
					<s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@SCORE_SAMP_MENU_ID">
						<br><br>
						<a href="scoreSearch.action?selectedMenuId=SCORE_SAMP_MENU_ID" id="back" name="back" class="btn btn-primary btn-xl"><!-- <img class="rollover" src="./material/img/button/sampling_bn-back.gif" alt="%{getText('label.backtosearch')}" /> --><s:text name="label.backtosearch"></s:text></a>
					</s:elseif>
					<s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_BLIND_TYPE_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_LANGUAGE_SUPPORT_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SAMP_INSPECTION_MENU_ID">
						<br><br>
						<a href="updateSpecialScoringMap.action" id="back" name="back" class="btn btn-primary btn-xl"><!-- <img class="rollover" src="./material/img/button/sampling_bn-back.gif" alt="%{getText('label.backtosearch')}" /> --><s:text name="label.backtosearch"></s:text></a>
					</s:elseif>
					<s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID">
						<br><br>
						<a href="scoreSearch.action?selectedMenuId=<s:property value="@com.saiten.util.WebAppConst@FORCED_MENU_ID"/>" id="back" name="back" class="btn btn-primary btn-xl"><!-- <img class="rollover" src="./material/img/button/sampling_bn-back.gif" alt="%{getText('label.backtosearch')}" /> --><s:text name="label.backtosearch"></s:text></a>
					</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@STATE_TRAN_MENU_ID">
						<br><br>
						<a href="scoreSearch.action?selectedMenuId=<s:property value="@com.saiten.util.WebAppConst@STATE_TRAN_MENU_ID"/>" id="back" name="back" class="btn btn-primary btn-xl"><!-- <img class="rollover" src="./material/img/button/sampling_bn-back.gif" alt="%{getText('label.backtosearch')}" /> --><s:text name="label.backtosearch"></s:text></a>
					</s:elseif>
				</s:else>
				
			</div>
		
			<div class="box_less_width_input_error_page">
				<p class="comment_area"></p>
			</div>
	    </div>
		
		 </td>
		 <!-- End left pane --> 
		 	   <!--3rd td --> 
		</tr>
		</table>
		</form>

		</div>
		</div>
	</div>
</body>
</html>
