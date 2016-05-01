﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script>
var answerFormNumLength = '<s:property value="%{@com.saiten.util.SaitenUtil@getConfigMap().get('answerformnum.lengh')}"/>';
var search_by_scorer_role_id = '<s:property value="%{@com.saiten.util.SaitenUtil@getConfigMap().get('search.by.scorer_role_id')}"/>';
</script>
<tiles:insertDefinition name="baseLayout">
	<tiles:putAttribute name="head-title">
		<s:if test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@SCORE_SAMP_MENU_ID">
			<title><s:text name="label.scoresamplingsearch.title"/></title>
		</s:if><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID">
			<title><s:text name="label.forcedscoringsearch.title"/></title>
		</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@STATE_TRAN_MENU_ID">
			<title><s:text name="label.statetransition.search.title"/></title>
		</s:elseif>
		<s:else>
			<title><s:text name="label.scoresearch.title" /></title>
		</s:else> 
	</tiles:putAttribute>
	<tiles:putAttribute name="head-addition">
		<!-- This portion will contains js file includes and js code also. -->
		<script type="text/javascript" src="./material/scripts/js/scoreSearch.js"></script>
		<script type="text/javascript" src="./material/scripts/js/additionalMethod_validate.js"></script>
		<script type="text/javascript" src="./material/scripts/js/date_validation.js"></script>
		<%-- <script type="text/javascript"  src="./material/scripts/js/errorMessages.js" ></script> --%>
		<style type="text/css">
 			label { width: auto; float: none; color: red;  display: block; }
		</style>
	</tiles:putAttribute>
	<tiles:putAttribute name="body-title">
		<!-- This portion will contain perticular page body title. -->
	</tiles:putAttribute>
	<tiles:putAttribute name="body-main">
		<!-- This portion will contain main body of perticular page -->
		<table class="height_for_listpage">
		 <tr>
		   <td colspan="2" class="table-side-menu-td" width="6%">
			<div id="side_menu">
				<div class="side_menu_top_margin">
					<p class="side_menu_heading_color">
						<s:property value="#session.scorerInfo.roleDescription" /><br>
						<s:property value="#session.scorerInfo.scorerId" /><br/>
					<p class="side_menu_alignment"><s:text name="label.menu" />
						<div class="green_menu">
							<input type="button" name="submit" value="<s:text name="label.backtosaitenmenu" />" class="menu_button" 
								onclick="javascript:location.href='userMenu.action';">
							
							<input type="button" name="submit" value="<s:text name="label.logout" />" class="menu_button" 
								onclick="javascript:location.href='logOut.action';">
					  	</div>
		       <s:if test="#session.saitenLoginEnabled != true">
		         	<br>
					<div class="green_menu">
						<input type="button" name="submit" value="<s:text name="label.backToSaitenLMS" />" class="menu_button" onclick="javascript:location.href='backToSaitenLms.action';" />
					</div>
		       </s:if>
			</div>
		  <br/>
		 </div> 
		</td>
		   
		<td class="table-center-menu-td" width="70%">
		<s:if test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@STATE_TRAN_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID">
			<s:set name="targetAction" id="targetAction" value="%{'searchCount'}"></s:set>
		</s:if>
		<s:else>
			<s:set name="targetAction" id="targetAction" value="%{'showScoreSearch'}"></s:set>
		</s:else>
		<s:form name="scoreSearchForm" id="scoreSearchForm" method="post" action="%{#targetAction}">
		<s:if test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@STATE_TRAN_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID">
			<s:hidden id="forceAndStateTransitionFlag" name="forceAndStateTransitionFlag" value="false"></s:hidden>
		</s:if>
		 <input type="hidden" name="searchMode" value="default">
		  <div id="list_pane">
		    <div class="search_box">
		     		<h1 align="left"><s:text name="label.scoresearch.answersearch" /></h1>	
					<table class="search_form search_table_width">
									<!-- 教科 -->
									<tr>
										<th class="partition">
											<s:text name="label.scoresearch.subjectnamelist" />
										</th>
										<td colspan="5"> 
											<c:forEach var="entry" items="${scoreSearchInfo.subjectNameList}" varStatus="subjectNameListStatus">
													<c:set var="subjectNameKey" value="${entry.key}" />
													<c:set var="roleId" value="${sessionScope.scorerInfo.roleId}"></c:set>
													<c:if test="${!subjectNameListStatus.first}">
														<c:if test="${not (schoolType eq fn:split(subjectNameKey, '-')[0])}">
															</br>
														</c:if>
													</c:if>
													
													<c:set var="sessionSubjectCode" value="${sessionScope.scoreInputInfo.subjectCode}" />
													
													<c:choose>
														<c:when test="${not ((roleId eq 4) or (roleId eq 6) or (fn:contains(loggedInScorerSubjectList,fn:split(subjectNameKey, '-')[1])))}">
															<input type="radio" id="subjectCode" name="scoreInputInfo.subjectCode" value="${fn:split(entry.key, '-')[1]}" disabled="disabled">&nbsp;<c:out value="${entry.value}"/>
														</c:when>
														<c:otherwise>
														<c:choose>
															<c:when test="${fn:contains(sessionSubjectCode, fn:split(subjectNameKey, '-')[1])}">
																<input type="radio" id="subjectCode" name="scoreInputInfo.subjectCode" checked="checked" value="${fn:split(entry.key, '-')[1]}">&nbsp;<c:out value="${entry.value}"/>
															</c:when>
															<c:otherwise>
																<input type="radio" id="subjectCode" name="scoreInputInfo.subjectCode" value="${fn:split(entry.key, '-')[1]}">&nbsp;<c:out value="${entry.value}"/>													
															</c:otherwise>
														</c:choose>
														</c:otherwise>
													</c:choose>
														
													<c:set var="schoolType" value="${fn:split(subjectNameKey, '-')[0]}" />
											</c:forEach>
										</td>
									</tr>
									<!-- 設問番号、答案番号 -->
									<tr>
										<th class="partition">
											<s:text name="label.scoresearch.questionnumber" />
										</th>
										<td class="search_form_class1">
											<s:textfield id="questionNum" name="scoreInputInfo.questionNum" maxlength="5" size="15" value="%{#session.scoreInputInfo.questionNum}"/>
										</td>
										<th class="partition">
											<s:text name="label.scoresearch.answerformnum" />
										</th>
										<td class="search_form_class1">
											<s:textfield id="answerFormNum" name="scoreInputInfo.answerFormNum" maxlength="%{@com.saiten.util.SaitenUtil@getConfigMap().get('answerformnum.lengh')}" size="13" value="%{#session.scoreInputInfo.answerFormNum}" />
										</td>
										<s:if  test=" !(#session.questionInfo.menuId == @com.saiten.util.WebAppConst@STATE_TRAN_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID)">
										<th class="partition">
											<s:text name="label.scoresearch.resultcount" />
										</th>
										<td class="search_form_class1" >	
										  	<s:if test="#session.scoreInputInfo.resultCount!=null">
										        <s:textfield id="resultCount" name="scoreInputInfo.resultCount" maxlength="6" size="5" value="%{#session.scoreInputInfo.resultCount}" />
										  	</s:if><s:else>
										        <s:textfield id="resultCount" name="scoreInputInfo.resultCount" maxlength="6" size="5" value="%{@com.saiten.util.WebAppConst@TOTAL_RESULT_COUNT_SET_DEFAULT}" />
										  	</s:else>
										</td>
										</s:if>
									</tr>
									<s:if test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@SCORE_SAMP_MENU_ID">
										<tr>
											<th class="partition">
												<s:text name="label.scoresamplingflag.text1"></s:text>
											</th>
											<td colspan="5">
												<s:if test="#session.scoreInputInfo.samplingFlag == true">
													<input type="checkbox" id="samplingFlag" name="scoreInputInfo.samplingFlag" value="true" checked="checked">
												</s:if>
												<s:else>
													<input type="checkbox" id="samplingFlag" name="scoreInputInfo.samplingFlag" value="true">							
												</s:else>
												&nbsp;<s:text name="label.scoresamplingflag.text"></s:text>
											</td>
										</tr>
									</s:if>
							</table>	
				    </div>
		  </div><!-- Close First Div-->
		
		
		<div id="list_pane">
		   <div class="search_box" id="history_div">
		     		<h1 align="left">
		     			<s:text name="label.scoresearch.historyblock" />&nbsp;
		     			<s:if test="#session.scoreInputInfo.scoreHistoryInfo.historyBlock == true">
							<input type="checkbox" id="historyBlock" name="scoreInputInfo.scoreHistoryInfo.historyBlock" value="true" onclick="javascript:toggleProcessSearch();" checked="checked">
						</s:if>
						<s:else>
							<input type="checkbox" id="historyBlock" name="scoreInputInfo.scoreHistoryInfo.historyBlock" value="true" onclick="javascript:toggleProcessSearch();">							
						</s:else>
						<s:text name="label.scoresearch.historysearchcriteria" />
					</h1>	
					
					<table class="search_form  search_table_width"> 
		
									<!-- 過去の採点者ID -->
									<tr>
										<th class="partition">
											<s:text name="label.scoresearch.historyscorerid" />
										</th>
										<td colspan="5" style="padding-left: 0px;">
											<table class="scorer_id_table">
												<tr>
													<td>
														<s:textfield id="historyScorerId1" name="scoreInputInfo.scoreHistoryInfo.historyScorerId1" maxlength="7" size="7" value="%{#session.scoreInputInfo.scoreHistoryInfo.historyScorerId1}" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;			
													</td>
													<td>
														<s:textfield id="historyScorerId2" name="scoreInputInfo.scoreHistoryInfo.historyScorerId2" maxlength="7" size="7" value="%{#session.scoreInputInfo.scoreHistoryInfo.historyScorerId2}" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													</td>
													<td>
														<s:textfield id="historyScorerId3" name="scoreInputInfo.scoreHistoryInfo.historyScorerId3" maxlength="7" size="7" value="%{#session.scoreInputInfo.scoreHistoryInfo.historyScorerId3}" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													</td>
													<td>
														<s:textfield id="historyScorerId4" name="scoreInputInfo.scoreHistoryInfo.historyScorerId4" maxlength="7" size="7" value="%{#session.scoreInputInfo.scoreHistoryInfo.historyScorerId4}" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													</td>
													<td>
														<s:textfield id="historyScorerId5" name="scoreInputInfo.scoreHistoryInfo.historyScorerId5" maxlength="7" size="7" value="%{#session.scoreInputInfo.scoreHistoryInfo.historyScorerId5}" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<s:if test="@com.saiten.util.SaitenUtil@getConfigMap().get('search.by.scorer_role_id') == 'true'">
									<tr>
										<th class="partition">
											<s:text name="label.scorer.role" />
											<input type="button" name="scorerCheckButton" value="<s:text name="label.scoresearch.selectall" />"
											 onclick="changeCheckBox('scoreInputInfo.scoreHistoryInfo.historyScorerRole', true)">
											<input type="button" name="scorerCheckButton" value="<s:text name="label.scoresearch.cancelall" />" onclick="changeCheckBox('scoreInputInfo.scoreHistoryInfo.historyScorerRole', false)">
										</th>
										<td colspan="5" >
											<s:if test="@org.apache.commons.lang.ArrayUtils@contains(#session.scoreInputInfo.scoreHistoryInfo.historyScorerRole, 1)">
												<input type="checkbox" id="historyScorerRole1" name="scoreInputInfo.scoreHistoryInfo.historyScorerRole" value="1" checked="checked">
											</s:if>
												<s:else>
											<input type="checkbox" id="historyScorerRole1" name="scoreInputInfo.scoreHistoryInfo.historyScorerRole" value="1" >
												</s:else>
											<s:text name="label.scorer" />	
											&nbsp;&nbsp;&nbsp;
											<s:if test="@org.apache.commons.lang.ArrayUtils@contains(#session.scoreInputInfo.scoreHistoryInfo.historyScorerRole, 2)">
												<input type="checkbox" id="historyScorerRole2" name="scoreInputInfo.scoreHistoryInfo.historyScorerRole" value="2" checked="checked">
											</s:if>
												<s:else>
											<input type="checkbox" id="historyScorerRole2" name="scoreInputInfo.scoreHistoryInfo.historyScorerRole" value="2" >
												</s:else>
											<s:text name="label.supervisor" />
											&nbsp;&nbsp;&nbsp;
											<s:if test="@org.apache.commons.lang.ArrayUtils@contains(#session.scoreInputInfo.scoreHistoryInfo.historyScorerRole, 3)">
												<input type="checkbox" id="historyScorerRole3" name="scoreInputInfo.scoreHistoryInfo.historyScorerRole" value="3" checked="checked">
											</s:if>
											<s:else>
												<input type="checkbox" id="historyScorerRole3" name="scoreInputInfo.scoreHistoryInfo.historyScorerRole" value="3" >
											</s:else>
											<s:text name="label.wg" />
											&nbsp;&nbsp;&nbsp;
											<s:if test="@org.apache.commons.lang.ArrayUtils@contains(#session.scoreInputInfo.scoreHistoryInfo.historyScorerRole, 4)">
												<input type="checkbox" id="historyScorerRole4" name="scoreInputInfo.scoreHistoryInfo.historyScorerRole" value="4" checked="checked">
											</s:if>
											<s:else>
												<input type="checkbox" id="historyScorerRole4" name="scoreInputInfo.scoreHistoryInfo.historyScorerRole" value="4" >
											</s:else>
											<s:text name="label.admin" />									
										</td>
									</tr>									
									</s:if>

									<s:if test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID">
													<tr>
														<th class="partition">
															<s:text name="label.qualitymarkflag.text1"></s:text>
														</th>
														<td colspan="5">
															<s:if test="#session.scoreInputInfo.scoreHistoryInfo.historyQualityCheckFlag == true">
																	<input type="checkbox" id="historyQualityCheckFlag" name="scoreInputInfo.scoreHistoryInfo.historyQualityCheckFlag" value="true" checked="checked">
															</s:if><s:else>
																	<input type="checkbox" id="historyQualityCheckFlag" name="scoreInputInfo.scoreHistoryInfo.historyQualityCheckFlag" value="true">							
															</s:else>
														</td>
													</tr>
									</s:if>
									
									<!-- 過去の解答類型 -->
									<tr>
										<th class="partition">
											<s:text name="label.scoresearch.historygrade" /><br>
											<s:text name="label.scoresearch.historycategorypending" /><br>
											<s:text name="label.scoresearch.historycategorydeny"/>
											<input type="button" name="processCheckButton" value="<s:text name="label.scoresearch.selectall" />"
											 onclick="changeCheckBox('scoreInputInfo.scoreHistoryInfo.historyGradeNum', true)">
											<input type="button" name="processCheckButton" value="<s:text name="label.scoresearch.cancelall" />" onclick="changeCheckBox('scoreInputInfo.scoreHistoryInfo.historyGradeNum', false)">
										</th>
										<td colspan="5">
											<table>
												<tr>
													<td class="inner">
														<s:if test="#session.scoreInputInfo.scoreHistoryInfo.historyCategoryType == 1">
															<input type="radio" name="scoreInputInfo.scoreHistoryInfo.historyCategoryType" id="historyCategoryType1" checked="checked" 
																value="1" onclick="changeHistoryCategoryType(true, true)" onkeypress="changeHistoryCategoryType(true, true)"/>&nbsp;<s:text name="label.scoresearch.notspecified" />&nbsp;
														</s:if>
														<s:else>
															<input type="radio" name="scoreInputInfo.scoreHistoryInfo.historyCategoryType" id="historyCategoryType1" 
																value="1" onclick="changeHistoryCategoryType(true, true)" onkeypress="changeHistoryCategoryType(true, true)"/>&nbsp;<s:text name="label.scoresearch.notspecified" />&nbsp;
														</s:else>
												 	</td>
												</tr>
												<tr>
													<td class="inner">
													<s:if test="#session.scoreInputInfo.scoreHistoryInfo.historyCategoryType == 2">
														<input type="radio" name="scoreInputInfo.scoreHistoryInfo.historyCategoryType" id="historyCategoryType2" checked="checked"
															value="2" onclick="changeHistoryCategoryType(true, true)" onkeypress="changeHistoryCategoryType(true, true)"/>&nbsp;<s:text name="label.scoresearch.nograde" />&nbsp;
													</s:if>
													<s:else>
														<input type="radio" name="scoreInputInfo.scoreHistoryInfo.historyCategoryType" id="historyCategoryType2" 
															value="2" onclick="changeHistoryCategoryType(true, true)" onkeypress="changeHistoryCategoryType(true, true)"/>&nbsp;<s:text name="label.scoresearch.nograde" />&nbsp;
													</s:else>
												 	</td>
												</tr>
												<tr>
												 	<td class="inner">
												 		<s:if test="#session.scoreInputInfo.scoreHistoryInfo.historyCategoryType == 3">
													 		<input type="radio" name="scoreInputInfo.scoreHistoryInfo.historyCategoryType" id="historyCategoryType3" checked="checked"
													 			value="3" onclick="changeHistoryCategoryType(false, true)" onkeypress="changeHistoryCategoryType(false, true)"/>&nbsp;<s:text name="label.scoresearch.grade" />&nbsp;
												 		</s:if>
												 		<s:else>
												 			<input type="radio" name="scoreInputInfo.scoreHistoryInfo.historyCategoryType" id="historyCategoryType3" 
													 			value="3" onclick="changeHistoryCategoryType(false, true)" onkeypress="changeHistoryCategoryType(false, true)"/>&nbsp;<s:text name="label.scoresearch.grade" />&nbsp;
												 		</s:else>
												 	</td>
													<td class="inner">
														<s:if test="@org.apache.commons.lang.ArrayUtils@contains(#session.scoreInputInfo.scoreHistoryInfo.historyGradeNum, 0)">
															<input type="checkbox" id="historyGradeNum" name="scoreInputInfo.scoreHistoryInfo.historyGradeNum" value="0"  onchange="checkAtLeastOneHistoryGradeSelected()" checked="checked">
														</s:if>
														<s:else>
															<input type="checkbox" id="historyGradeNum" name="scoreInputInfo.scoreHistoryInfo.historyGradeNum" value="0"  onchange="checkAtLeastOneHistoryGradeSelected()">
														</s:else>
														<s:text name="label.scoresearch.grade0" />
													</td>
													<td class="inner">
														<s:if test="@org.apache.commons.lang.ArrayUtils@contains(#session.scoreInputInfo.scoreHistoryInfo.historyGradeNum, 1)">
															<input type="checkbox" id="historyGradeNum" name="scoreInputInfo.scoreHistoryInfo.historyGradeNum" value="1" onchange="checkAtLeastOneHistoryGradeSelected()" checked="checked">
														</s:if>
														<s:else>
															<input type="checkbox" id="historyGradeNum" name="scoreInputInfo.scoreHistoryInfo.historyGradeNum" value="1" onchange="checkAtLeastOneHistoryGradeSelected()">
														</s:else>
														<s:text name="label.scoresearch.grade1" />
													</td>
													<td class="inner">
														<s:if test="@org.apache.commons.lang.ArrayUtils@contains(#session.scoreInputInfo.scoreHistoryInfo.historyGradeNum, 2)">
															<input type="checkbox" id="historyGradeNum" name="scoreInputInfo.scoreHistoryInfo.historyGradeNum" value="2" onchange="checkAtLeastOneHistoryGradeSelected()" checked="checked">
														</s:if>
														<s:else>
															<input type="checkbox" id="historyGradeNum" name="scoreInputInfo.scoreHistoryInfo.historyGradeNum" value="2" onchange="checkAtLeastOneHistoryGradeSelected()">
														</s:else>
														<s:text name="label.scoresearch.grade2" />
													</td>
													<td class="inner">
														<s:if test="@org.apache.commons.lang.ArrayUtils@contains(#session.scoreInputInfo.scoreHistoryInfo.historyGradeNum, 3)">
															<input type="checkbox" id="historyGradeNum" name="scoreInputInfo.scoreHistoryInfo.historyGradeNum" value="3" onchange="checkAtLeastOneHistoryGradeSelected()" checked="checked">
														</s:if>
														<s:else>
															<input type="checkbox" id="historyGradeNum" name="scoreInputInfo.scoreHistoryInfo.historyGradeNum" value="3" onchange="checkAtLeastOneHistoryGradeSelected()">
														</s:else>
														<s:text name="label.scoresearch.grade3" />
													</td>
													<td class="inner">
														<s:if test="@org.apache.commons.lang.ArrayUtils@contains(#session.scoreInputInfo.scoreHistoryInfo.historyGradeNum, 4)">
															<input type="checkbox" id="historyGradeNum" name="scoreInputInfo.scoreHistoryInfo.historyGradeNum" value="4" onchange="checkAtLeastOneHistoryGradeSelected()" checked="checked">
														</s:if>
														<s:else>
															<input type="checkbox" id="historyGradeNum" name="scoreInputInfo.scoreHistoryInfo.historyGradeNum" value="4" onchange="checkAtLeastOneHistoryGradeSelected()">
														</s:else>
														<s:text name="label.scoresearch.grade4" />
													</td>
													
												</tr> 
												<tr> 
													<td class="inner"></td>
													<td class="inner">
														<s:if test="@org.apache.commons.lang.ArrayUtils@contains(#session.scoreInputInfo.scoreHistoryInfo.historyGradeNum, 5)">
															<input type="checkbox" id="historyGradeNum" name="scoreInputInfo.scoreHistoryInfo.historyGradeNum" value="5" onchange="checkAtLeastOneHistoryGradeSelected()" checked="checked">
														</s:if>
														<s:else>
															<input type="checkbox" id="historyGradeNum" name="scoreInputInfo.scoreHistoryInfo.historyGradeNum" value="5" onchange="checkAtLeastOneHistoryGradeSelected()">
														</s:else>
														<s:text name="label.scoresearch.grade5" />
													</td>
													<td class="inner">
														<s:if test="@org.apache.commons.lang.ArrayUtils@contains(#session.scoreInputInfo.scoreHistoryInfo.historyGradeNum, 6)">
															<input type="checkbox" id="historyGradeNum" name="scoreInputInfo.scoreHistoryInfo.historyGradeNum" value="6" onchange="checkAtLeastOneHistoryGradeSelected()" checked="checked">
														</s:if>
														<s:else>
															<input type="checkbox" id="historyGradeNum" name="scoreInputInfo.scoreHistoryInfo.historyGradeNum" value="6" onchange="checkAtLeastOneHistoryGradeSelected()">
														</s:else>
														<s:text name="label.scoresearch.grade6" />
													</td>
													<td class="inner">
														<s:if test="@org.apache.commons.lang.ArrayUtils@contains(#session.scoreInputInfo.scoreHistoryInfo.historyGradeNum, 7)">
															<input type="checkbox" id="historyGradeNum" name="scoreInputInfo.scoreHistoryInfo.historyGradeNum" value="7" onchange="checkAtLeastOneHistoryGradeSelected()" checked="checked">
														</s:if>
														<s:else>
															<input type="checkbox" id="historyGradeNum" name="scoreInputInfo.scoreHistoryInfo.historyGradeNum" value="7" onchange="checkAtLeastOneHistoryGradeSelected()">
														</s:else>
														<s:text name="label.scoresearch.grade7" />
													</td>
													<td class="inner">
														<s:if test="@org.apache.commons.lang.ArrayUtils@contains(#session.scoreInputInfo.scoreHistoryInfo.historyGradeNum, 8)">
															<input type="checkbox" id="historyGradeNum" name="scoreInputInfo.scoreHistoryInfo.historyGradeNum" value="8" onchange="checkAtLeastOneHistoryGradeSelected()">
														</s:if>
														<s:else>
															<input type="checkbox" id="historyGradeNum" name="scoreInputInfo.scoreHistoryInfo.historyGradeNum" value="8" onchange="checkAtLeastOneHistoryGradeSelected()">
															<!-- value passed was 4. changed it to 8 -->
														</s:else>
														<s:text name="label.scoresearch.grade8" />
													</td>
													<td class="inner">
														<s:if test="@org.apache.commons.lang.ArrayUtils@contains(#session.scoreInputInfo.scoreHistoryInfo.historyGradeNum, 9)">
															<input type="checkbox" id="historyGradeNum" name="scoreInputInfo.scoreHistoryInfo.historyGradeNum" value="9" onchange="checkAtLeastOneHistoryGradeSelected()" checked="checked">
														</s:if>
														<s:else>
															<input type="checkbox" id="historyGradeNum" name="scoreInputInfo.scoreHistoryInfo.historyGradeNum" value="9" onchange="checkAtLeastOneHistoryGradeSelected()">
														</s:else>
														<s:text name="label.scoresearch.grade9" />
													</td>
													<td class="inner">
														<s:if test="@org.apache.commons.lang.ArrayUtils@contains(#session.scoreInputInfo.scoreHistoryInfo.historyGradeNum, -1)">
															<input type="checkbox" id="historyGradeNum" name="scoreInputInfo.scoreHistoryInfo.historyGradeNum" value="-1" onchange="checkAtLeastOneHistoryGradeSelected()" checked="checked">
														</s:if>
														<s:else>
															<input type="checkbox" id="historyGradeNum" name="scoreInputInfo.scoreHistoryInfo.historyGradeNum" value="-1" onchange="checkAtLeastOneHistoryGradeSelected()">
														</s:else>
														<s:text name="label.scoresearch.gradenotavailable" />
													</td>
												</tr>
												<tr>
													<td class="inner">
														&nbsp;
													</td>
													<td colspan="10" class="inner">
														<!-- For displaying errorMessage, For select at least on grade. -->
														<input type="text" id="selectHistoryGrade" name="selectHistoryGrade" style="border: none;background-color: transparent;color: red;width: 400px;" readonly="readonly">
													</td>
												</tr>
												<tr>
												 	<td class="inner" style="vertical-align: top;">
												 		<s:if test="#session.scoreInputInfo.scoreHistoryInfo.historyCategoryType == 4">
													 		<input type="radio" name="scoreInputInfo.scoreHistoryInfo.historyCategoryType" id="historyCategoryType4" checked="checked"
													 			value="4" onclick="changeHistoryCategoryType(true, false)" onkeypress="changeHistoryCategoryType(true, false)"/>&nbsp;<s:text name="label.scoresearch.pendingcategory" />&nbsp;
													 	</s:if>
													 	<s:else>
													 		<input type="radio" name="scoreInputInfo.scoreHistoryInfo.historyCategoryType" id="historyCategoryType4" 
													 			value="4" onclick="changeHistoryCategoryType(true, false)" onkeypress="changeHistoryCategoryType(true, false)"/>&nbsp;<s:text name="label.scoresearch.pendingcategory" />&nbsp;
													 	</s:else>
												 	</td>	
												 	<td colspan="10" class="inner" style="vertical-align: top;">
												 		<s:textfield id="historyPendingCategory" name="scoreInputInfo.scoreHistoryInfo.historyPendingCategory" cssClass="disable_bg" size="30" disabled="true" value="%{#session.scoreInputInfo.scoreHistoryInfo.historyPendingCategory}" />
												 		<input type="text" id="historyPendingCategoryHidden" name="historyPendingCategoryHidden" style="border: none;background-color: transparent;color: red;width: 00px;" readonly="readonly">
												 	</td>
												</tr>
												<tr>
													<td class="inner" style="vertical-align: top;">
													<s:if test="#session.scoreInputInfo.scoreHistoryInfo.historyCategoryType == 5">
														<input type="radio" name="scoreInputInfo.scoreHistoryInfo.historyCategoryType" id="historyCategoryType5" checked="checked"
													 			value="5" onclick="changeHistoryCategoryType(true, false)" onkeypress="changeHistoryCategoryType(true, false)"/>&nbsp;<s:text name="label.scoresearch.denycategory" />&nbsp;
													</s:if>
													<s:else>
												 		<input type="radio" name="scoreInputInfo.scoreHistoryInfo.historyCategoryType" id="historyCategoryType5"
													 			value="5" onclick="changeHistoryCategoryType(true, false)" onkeypress="changeHistoryCategoryType(true, false)"/>&nbsp;<s:text name="label.scoresearch.denycategory" />&nbsp;
													 </s:else>
													</td>
													<td colspan="10" class="inner" style="vertical-align: top;">
												 		<s:textfield id="historyDenyCategory" name="scoreInputInfo.scoreHistoryInfo.historyDenyCategory" cssClass="disable_bg" size="30" disabled="true" value="%{#session.scoreInputInfo.scoreHistoryInfo.historyDenyCategory}" />
												 		<input type="text" id="historyDenyCategoryHidden" name="historyDenyCategoryHidden" style="border: none;background-color: transparent;color: red;width: 00px;" readonly="readonly">
												 	</td>
												 </tr>
											</table>
										</td>
									</tr>
									<!-- 過去のSKP -->
									<tr>
										<th class="partition">
											<s:text name="label.scoresearch.historypendingcategory" />
										</th>
										<td colspan="5">
											<table>
												<tr>
													<td class="inner">
														<s:text name="label.scoresearch.historyincludecheckpoint" />
													</td>
													<td class="inner">
														<s:text name="label.colon" />
													</td>
													<td class="inner">
														<s:textfield id="historyIncludeCheckPoints" name="scoreInputInfo.scoreHistoryInfo.historyIncludeCheckPoints" size="30" value="%{#session.scoreInputInfo.scoreHistoryInfo.historyIncludeCheckPoints}" />
													</td>
													<td class="inner">
													    <input type="radio" id="pastSkpAndCondition" name="scoreInputInfo.scoreHistoryInfo.pastSkpConditions" value="skpAndCondition" checked="checked"/>&nbsp;<s:text name="label.search.screen.skp.and.condition"></s:text>
													</td>
													<td class="inner">
													 	<s:if test="#session.scoreInputInfo.scoreHistoryInfo.pastSkpConditions == @com.saiten.util.WebAppConst@SKP_OR_CONDITION">
													 	    <input type="radio" id="pastSkpORCondition" name="scoreInputInfo.scoreHistoryInfo.pastSkpConditions" value="skpORCondition" checked="checked"/>&nbsp;<s:text name="label.search.screen.skp.or.condition"></s:text>
													 	</s:if><s:else>
													 	    <input type="radio" id="pastSkpORCondition" name="scoreInputInfo.scoreHistoryInfo.pastSkpConditions" value="skpORCondition"/>&nbsp;<s:text name="label.search.screen.skp.or.condition"></s:text>
													 	</s:else>
													</td>
												</tr>    
												<tr>
													<td class="inner">
														<s:text name="label.scoresearch.historyexcludecheckpoint" />
													</td>
													<td class="inner">
														<s:text name="label.colon" />
													</td>
													<td class="inner">
														<s:textfield id="historyExcludeCheckPoints" name="scoreInputInfo.scoreHistoryInfo.historyExcludeCheckPoints" size="30"  value="%{#session.scoreInputInfo.scoreHistoryInfo.historyExcludeCheckPoints}" />
													</td>
												</tr>
												<tr>
													<td colspan="6" class="inner">
														<!-- For displaying ErrorMessage : Same checkPoint should not enter into 'historyIncludeCheckPoints' and 'historyExcludeCheckPoints' -->
														<%-- <s:hidden id="historyCheckPointHidden" name="historyCheckPointHidden"></s:hidden> --%>
														<input type="text" id="historyCheckPointHidden" name="historyCheckPointHidden" style="border: none;background-color: transparent;color: red;width: 400px;" readonly="readonly">	
													</td>
												</tr>
											</table>
										</td>
									</tr>
									
									<!-- 過去の採点操作 -->
									<tr>
										<th class="partition">
											<s:text name="label.scoresearch.historyeventlist" /><br>
											<input type="button" name="eventCheckButton" value="<s:text name="label.scoresearch.selectall" />" onclick="changeCheckBox('scoreInputInfo.scoreHistoryInfo.historyEventList', true)">
											<input type="button" name="eventCheckButton" value="<s:text name="label.scoresearch.cancelall" />" onclick="changeCheckBox('scoreInputInfo.scoreHistoryInfo.historyEventList', false)">
										</th>
										<td colspan="5">
											<table>
												<tr>
													<s:iterator value="scoreSearchInfo.historyEventList" id="historyEventObj" status="historyEventStatus">
														<td class="inner">
															<s:set id="historyEventListKey" name="historyEventListKey" value="key" />
															<s:if test="@org.apache.commons.lang.ArrayUtils@contains(#session.scoreInputInfo.scoreHistoryInfo.historyEventList, #historyEventListKey)">
																<input type="checkbox" id="historyEventList" name="scoreInputInfo.scoreHistoryInfo.historyEventList" value='<s:property value="key"/>' onchange="checkAtLeastOneHistoryEventSelected()" checked="checked">&nbsp;<s:property value="value"/>
															</s:if>
															<s:else>
																<input type="checkbox" id="historyEventList" name="scoreInputInfo.scoreHistoryInfo.historyEventList" value='<s:property value="key"/>' onchange="checkAtLeastOneHistoryEventSelected()">&nbsp;<s:property value="value"/>
															</s:else>
														</td>
														<s:if test="#historyEventStatus.count % 4 == 0">
															</tr><tr>
														</s:if>
													</s:iterator>
												</tr>
												<tr>
													<td colspan="5" class="inner">
														<!-- For displaying errorMessage, For select at least on event. -->
														<input type="text" id="selectHistoryEvent" name="selectHistoryEvent" style="border: none;background-color: transparent;color: red;width: 400px;" readonly="readonly">
													</td>
												</tr>
											</table>
										</td>
									</tr>
									
									<!-- 上記採点操作が行われた日時 -->
									<tr>
										<th class="partition">
											<s:text name="label.scoresearch.historyoperationdate1" /><br/>
											<s:text name="label.scoresearch.historyoperationdate2" />
										</th>
										<td colspan="5">
											<table >
												<tr >
													<td style="border: none;padding: 0px;">
														<s:select id="historyUpdateDateStartYear" name="scoreInputInfo.scoreHistoryInfo.historyUpdateDateStartYear" list="scoreSearchInfo.yearList" value="%{#session.scoreInputInfo.scoreHistoryInfo.historyUpdateDateStartYear}" />
														<s:text name="label.year" />
														
														<s:select id="historyUpdateDateStartMonth" name="scoreInputInfo.scoreHistoryInfo.historyUpdateDateStartMonth" list="scoreSearchInfo.monthList" value="%{#session.scoreInputInfo.scoreHistoryInfo.historyUpdateDateStartMonth}" />
														<s:text name="label.month" />
														
														<s:select id="historyUpdateDateStartDay" name="scoreInputInfo.scoreHistoryInfo.historyUpdateDateStartDay" list="scoreSearchInfo.daysList" value="%{#session.scoreInputInfo.scoreHistoryInfo.historyUpdateDateStartDay}" />
														<s:text name="label.day" />
														
														<s:select id="historyUpdateDateStartHours" name="scoreInputInfo.scoreHistoryInfo.historyUpdateDateStartHours" list="scoreSearchInfo.hoursList" value="%{#session.scoreInputInfo.scoreHistoryInfo.historyUpdateDateStartHours}" />
														<s:text name="label.hours" />
														
														<s:select id="historyUpdateDateStartMin" name="scoreInputInfo.scoreHistoryInfo.historyUpdateDateStartMin" list="scoreSearchInfo.minutesList" value="%{#session.scoreInputInfo.scoreHistoryInfo.historyUpdateDateStartMin}" />
														<s:text name="label.minutes" />
														&nbsp;
														<s:text name="label.tilde" />
														&nbsp;
														<s:select id="historyUpdateDateEndYear" name="scoreInputInfo.scoreHistoryInfo.historyUpdateDateEndYear" list="scoreSearchInfo.yearList" value="%{#session.scoreInputInfo.scoreHistoryInfo.historyUpdateDateEndYear}" />
														<s:text name="label.year" />
														<s:select id="historyUpdateDateEndMonth" name="scoreInputInfo.scoreHistoryInfo.historyUpdateDateEndMonth" list="scoreSearchInfo.monthList" value="%{#session.scoreInputInfo.scoreHistoryInfo.historyUpdateDateEndMonth}" />
											
														<s:text name="label.month" />
														<s:select id="historyUpdateDateEndDay" name="scoreInputInfo.scoreHistoryInfo.historyUpdateDateEndDay" list="scoreSearchInfo.daysList" value="%{#session.scoreInputInfo.scoreHistoryInfo.historyUpdateDateEndDay}" />
											
														<s:text name="label.day" />
														<s:select id="historyUpdateDateEndHours" name="scoreInputInfo.scoreHistoryInfo.historyUpdateDateEndHours" list="scoreSearchInfo.hoursList" value="%{#session.scoreInputInfo.scoreHistoryInfo.historyUpdateDateEndHours}" />
											
														<s:text name="label.hours" />
														<s:select id="historyUpdateDateEndMin" name="scoreInputInfo.scoreHistoryInfo.historyUpdateDateEndMin" list="scoreSearchInfo.minutesList" value="%{#session.scoreInputInfo.scoreHistoryInfo.historyUpdateDateEndMin}" />
											
														<s:text name="label.minutes" />
													</td>
												</tr>
												<tr>
													<td style="border: none;padding: 0px;">
														<!-- For Displaying date validations ErrorMessages -->
														<s:hidden name="historyFromValidDate" id="historyFromValidDate"></s:hidden>
														<s:hidden name="historyToValidDate" id="historyToValidDate"></s:hidden>
														<s:hidden name="historyValidDate" id="historyValidDate"></s:hidden>
														<input type="text" id="historyDateError" name="historyDateError" style="border: none;background-color: transparent;color: red;width: 400px;" readonly="readonly">
													</td>
												</tr>
											</table>
											
										</td>
									</tr>
									
								</table>
				    </div>
		  </div> <!-- Close second Div-->
		
		
		<div id="list_pane">
		   <div class="search_box" id="current_div">
		     		<h1 align="left"><s:text name="label.scoresearch.currentblock" />&nbsp;
						<s:if test="#session.scoreInputInfo.scoreCurrentInfo.currentBlock == true">
							<input type="checkbox" name="scoreInputInfo.scoreCurrentInfo.currentBlock" value="true" onclick="javascript:toggleStateSearch();" id="currentBlock" checked="checked">
						</s:if>
						<s:else>
							<input type="checkbox" name="scoreInputInfo.scoreCurrentInfo.currentBlock" value="true" onclick="javascript:toggleStateSearch();" id="currentBlock">
						</s:else>
						<s:text name="label.scoresearch.currentsearchcriteria" />	
					</h1>	
					<table class="search_form search_table_width">
									<!-- 答案番号、採点者ID -->
									<tr>
										<th class="partition">
											<s:text name="label.scoresearch.currentscorerid" />
										</th>
										<td colspan="5" style="padding-left: 0px;">
											<table class="scorer_id_table">
												<tr>
													<td>
														<s:textfield id="currentScorerId1" name="scoreInputInfo.scoreCurrentInfo.currentScorerId1" maxlength="7" size="7" value="%{#session.scoreInputInfo.scoreCurrentInfo.currentScorerId1}" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													</td>
													<td>
														<s:textfield id="currentScorerId2" name="scoreInputInfo.scoreCurrentInfo.currentScorerId2" maxlength="7" size="7" value="%{#session.scoreInputInfo.scoreCurrentInfo.currentScorerId2}" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													</td>
													<td>
														<s:textfield id="currentScorerId3" name="scoreInputInfo.scoreCurrentInfo.currentScorerId3" maxlength="7" size="7" value="%{#session.scoreInputInfo.scoreCurrentInfo.currentScorerId3}" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													</td>
													<td>
														<s:textfield id="currentScorerId4" name="scoreInputInfo.scoreCurrentInfo.currentScorerId4" maxlength="7" size="7" value="%{#session.scoreInputInfo.scoreCurrentInfo.currentScorerId4}" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													</td>
													<td>
														<s:textfield id="currentScorerId5" name="scoreInputInfo.scoreCurrentInfo.currentScorerId5" maxlength="7" size="7" value="%{#session.scoreInputInfo.scoreCurrentInfo.currentScorerId5}" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<s:if test="@com.saiten.util.SaitenUtil@getConfigMap().get('search.by.scorer_role_id') == 'true' ">
									<tr>
										<th class="partition">
											<s:text name="label.scorer.role" />
											<input type="button" name="currentScorerCheckButton" value="<s:text name="label.scoresearch.selectall" />" 
											onclick="changeCheckBox('scoreInputInfo.scoreCurrentInfo.currentScorerRole', true)">
											<input type="button" name="currentScorerCheckButton" value="<s:text name="label.scoresearch.cancelall" />"
											 onclick="changeCheckBox('scoreInputInfo.scoreCurrentInfo.currentScorerRole', false)">
										</th>
										<td colspan="5" >
											<s:if test="@org.apache.commons.lang.ArrayUtils@contains(#session.scoreInputInfo.scoreCurrentInfo.currentScorerRole, 1)">
												<input type="checkbox" id="currentScorerRole1" name="scoreInputInfo.scoreCurrentInfo.currentScorerRole" value="1" checked="checked">
											</s:if>
												<s:else>
											<input type="checkbox" id="currentScorerRole1" name="scoreInputInfo.scoreCurrentInfo.currentScorerRole" value="1" >
												</s:else>
											<s:text name="label.scorer" />	
											&nbsp;&nbsp;&nbsp;
											<s:if test="@org.apache.commons.lang.ArrayUtils@contains(#session.scoreInputInfo.scoreCurrentInfo.currentScorerRole, 2)">
												<input type="checkbox" id="currentScorerRole2" name="scoreInputInfo.scoreCurrentInfo.currentScorerRole" value="2" checked="checked">
											</s:if>
												<s:else>
											<input type="checkbox" id="currentScorerRole2" name="scoreInputInfo.scoreCurrentInfo.currentScorerRole" value="2" >
												</s:else>
											<s:text name="label.supervisor" />
											&nbsp;&nbsp;&nbsp;
											<s:if test="@org.apache.commons.lang.ArrayUtils@contains(#session.scoreInputInfo.scoreCurrentInfo.currentScorerRole, 3)">
												<input type="checkbox" id="currentScorerRole3" name="scoreInputInfo.scoreCurrentInfo.currentScorerRole" value="3" checked="checked">
											</s:if>
											<s:else>
												<input type="checkbox" id="currentScorerRole3" name="scoreInputInfo.scoreCurrentInfo.currentScorerRole" value="3" >
											</s:else>
											<s:text name="label.wg" />
											&nbsp;&nbsp;&nbsp;
											<s:if test="@org.apache.commons.lang.ArrayUtils@contains(#session.scoreInputInfo.scoreCurrentInfo.currentScorerRole, 4)">
												<input type="checkbox" id="currentScorerRole4" name="scoreInputInfo.scoreCurrentInfo.currentScorerRole" value="4" checked="checked">
											</s:if>
											<s:else>
												<input type="checkbox" id="currentScorerRole4" name="scoreInputInfo.scoreCurrentInfo.currentScorerRole" value="4" >
											</s:else>
											<s:text name="label.admin" />									
										</td>
									</tr>
									</s:if>
									<tr>
										<th class="partition">
											<s:text name="label.scoring.punchtext" />
										</th>
										<td colspan="5" style="padding-left: 20px;">
											<s:textfield id="punchText" name="scoreInputInfo.scoreCurrentInfo.punchText" maxlength="256" size="60" value="%{#session.scoreInputInfo.scoreCurrentInfo.punchText}" />
											<s:select id="punchTextData" list="scoreSearchInfo.punchTextMap" name="scoreInputInfo.scoreCurrentInfo.punchTextData" value="%{#session.scoreInputInfo.scoreCurrentInfo.punchTextData}"></s:select>			
										</td>
									</tr>
									<s:if test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID">
													<tr>
														<th class="partition">
															<s:text name="label.qualitymarkflag.text1"></s:text>
														</th>
														<td colspan="3">
															<s:if test="#session.scoreInputInfo.scoreCurrentInfo.currentQualityCheckFlag == true">
																	<input type="checkbox" id="currentQualityCheckFlag" name="scoreInputInfo.scoreCurrentInfo.currentQualityCheckFlag" value="true" checked="checked">
															</s:if><s:else>
																	<input type="checkbox" id="currentQualityCheckFlag" name="scoreInputInfo.scoreCurrentInfo.currentQualityCheckFlag" value="true">							
															</s:else>
														</td>
														<s:if test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID">
															<th class="partition">
																<s:text name="label.look_afterwards"></s:text>
															</th>
															<td colspan="2">
																<s:if test="#session.scoreInputInfo.scoreCurrentInfo.lookAfterwardsFlag == true">
																	<input type="checkbox" id="lookAfterwardsFlag" name="scoreInputInfo.scoreCurrentInfo.lookAfterwardsFlag" value="true" checked="checked">
																</s:if><s:else>
																	<input type="checkbox" id="lookAfterwardsFlag" name="scoreInputInfo.scoreCurrentInfo.lookAfterwardsFlag" value="true">							
																</s:else>
															</td>														
														</s:if>
													</tr>
												</s:if>
									
									<!-- 解答類型 -->
									<tr>
										<th class="partition">
											<s:text name="label.scoresearch.currentanswertype1" /><br>
											<s:text name="label.scoresearch.currentanswertype2" /><br>
											<s:text name="label.scoresearch.currentcategorydeny" />
											<input type="button" name="currentGradeCheckButton" value="<s:text name="label.scoresearch.selectall" />" 
											onclick="changeCheckBox('scoreInputInfo.scoreCurrentInfo.currentGradeNum', true)">
											<input type="button" name="currentGradeCheckButton" value="<s:text name="label.scoresearch.cancelall" />"
											 onclick="changeCheckBox('scoreInputInfo.scoreCurrentInfo.currentGradeNum', false)">
										</th>
										<td colspan="5">
											<table>
												<tr>
													<td class="inner">
														<s:if test="#session.scoreInputInfo.scoreCurrentInfo.currentCategoryType == 1">
															<input type="radio" name="scoreInputInfo.scoreCurrentInfo.currentCategoryType" id="currentCategoryType1" checked="checked" 
																value="1" onclick="changeCategoryType(true, true)" onkeypress="changeCategoryType(true, true)"/>&nbsp;<s:text name="label.scoresearch.notspecified" />&nbsp;
														</s:if>
														<s:else>
															<input type="radio" name="scoreInputInfo.scoreCurrentInfo.currentCategoryType" id="currentCategoryType1"  
																value="1" onclick="changeCategoryType(true, true)" onkeypress="changeCategoryType(true, true)"/>&nbsp;<s:text name="label.scoresearch.notspecified" />&nbsp;
														</s:else>
												 	</td>
												</tr>
												<tr>
													<td class="inner">
														<s:if test="#session.scoreInputInfo.scoreCurrentInfo.currentCategoryType == 2">
															<input type="radio" name="scoreInputInfo.scoreCurrentInfo.currentCategoryType" id="currentCategoryType2" checked="checked" 
																value="2" onclick="changeCategoryType(true, true)" onkeypress="changeCategoryType(true, true)"/>&nbsp;<s:text name="label.scoresearch.nograde" />&nbsp;
														</s:if>
														<s:else>
															<input type="radio" name="scoreInputInfo.scoreCurrentInfo.currentCategoryType" id="currentCategoryType2"  
																value="2" onclick="changeCategoryType(true, true)" onkeypress="changeCategoryType(true, true)"/>&nbsp;<s:text name="label.scoresearch.nograde" />&nbsp;
														</s:else>
												 	</td>
												</tr>
												<tr>
												 	<td class="inner">
												 		<s:if test="#session.scoreInputInfo.scoreCurrentInfo.currentCategoryType == 3">
													 		<input type="radio" name="scoreInputInfo.scoreCurrentInfo.currentCategoryType" id="currentCategoryType3" checked="checked" 
													 			value="3" onclick="changeCategoryType(false, true)" onkeypress="changeCategoryType(false, true)"/>&nbsp;<s:text name="label.scoresearch.grade" />&nbsp;
												 		</s:if>
												 		<s:else>
												 			<input type="radio" name="scoreInputInfo.scoreCurrentInfo.currentCategoryType" id="currentCategoryType3"  
													 			value="3" onclick="changeCategoryType(false, true)" onkeypress="changeCategoryType(false, true)"/>&nbsp;<s:text name="label.scoresearch.grade" />&nbsp;
														</s:else>
												 	</td>
													<td class="inner">
														<s:if test="@org.apache.commons.lang.ArrayUtils@contains(#session.scoreInputInfo.scoreCurrentInfo.currentGradeNum, 0)">
															<input type="checkbox" id="currentGradeNum" name="scoreInputInfo.scoreCurrentInfo.currentGradeNum" value="0" onchange="checkAtLeastOneCurrentGradeSelected()" checked="checked">
														</s:if>
														<s:else>
															<input type="checkbox" id="currentGradeNum" name="scoreInputInfo.scoreCurrentInfo.currentGradeNum" value="0" onchange="checkAtLeastOneCurrentGradeSelected()">
														</s:else>
														<s:text name="label.scoresearch.grade0" />
													</td>
													<td class="inner">
														<s:if test="@org.apache.commons.lang.ArrayUtils@contains(#session.scoreInputInfo.scoreCurrentInfo.currentGradeNum, 1)">
															<input type="checkbox" id="currentGradeNum" name="scoreInputInfo.scoreCurrentInfo.currentGradeNum" value="1" onchange="checkAtLeastOneCurrentGradeSelected()" checked="checked">
														</s:if>
														<s:else>
															<input type="checkbox" id="currentGradeNum" name="scoreInputInfo.scoreCurrentInfo.currentGradeNum" value="1" onchange="checkAtLeastOneCurrentGradeSelected()">
														</s:else>
														<s:text name="label.scoresearch.grade1" />
													</td>
													<td class="inner">
														<s:if test="@org.apache.commons.lang.ArrayUtils@contains(#session.scoreInputInfo.scoreCurrentInfo.currentGradeNum, 2)">
															<input type="checkbox" id="currentGradeNum" name="scoreInputInfo.scoreCurrentInfo.currentGradeNum" value="2" onchange="checkAtLeastOneCurrentGradeSelected()" checked="checked">
														</s:if>
														<s:else>
															<input type="checkbox" id="currentGradeNum" name="scoreInputInfo.scoreCurrentInfo.currentGradeNum" value="2" onchange="checkAtLeastOneCurrentGradeSelected()">
														</s:else>
														<s:text name="label.scoresearch.grade2" />
													</td>
													<td class="inner">
														<s:if test="@org.apache.commons.lang.ArrayUtils@contains(#session.scoreInputInfo.scoreCurrentInfo.currentGradeNum, 3)">
															<input type="checkbox" id="currentGradeNum" name="scoreInputInfo.scoreCurrentInfo.currentGradeNum" value="3" onchange="checkAtLeastOneCurrentGradeSelected()" checked="checked">
														</s:if>
														<s:else>
															<input type="checkbox" id="currentGradeNum" name="scoreInputInfo.scoreCurrentInfo.currentGradeNum" value="3" onchange="checkAtLeastOneCurrentGradeSelected()">
														</s:else>
														<s:text name="label.scoresearch.grade3" />
													</td>
													<td class="inner">
														<s:if test="@org.apache.commons.lang.ArrayUtils@contains(#session.scoreInputInfo.scoreCurrentInfo.currentGradeNum, 4)">
															<input type="checkbox" id="currentGradeNum" name="scoreInputInfo.scoreCurrentInfo.currentGradeNum" value="4" onchange="checkAtLeastOneCurrentGradeSelected()" checked="checked">
														</s:if>
														<s:else>
															<input type="checkbox" id="currentGradeNum" name="scoreInputInfo.scoreCurrentInfo.currentGradeNum" value="4" onchange="checkAtLeastOneCurrentGradeSelected()">
														</s:else>
														<s:text name="label.scoresearch.grade4" />
													</td>
													
												</tr> 
												<tr> 
													<td class="inner"></td>
													<td class="inner">
														<s:if test="@org.apache.commons.lang.ArrayUtils@contains(#session.scoreInputInfo.scoreCurrentInfo.currentGradeNum, 5)">
															<input type="checkbox" id="currentGradeNum" name="scoreInputInfo.scoreCurrentInfo.currentGradeNum" value="5" onchange="checkAtLeastOneCurrentGradeSelected()" checked="checked">
														</s:if>
														<s:else>
															<input type="checkbox" id="currentGradeNum" name="scoreInputInfo.scoreCurrentInfo.currentGradeNum" value="5" onchange="checkAtLeastOneCurrentGradeSelected()">
														</s:else>
														<s:text name="label.scoresearch.grade5" />
													</td>
													<td class="inner">
														<s:if test="@org.apache.commons.lang.ArrayUtils@contains(#session.scoreInputInfo.scoreCurrentInfo.currentGradeNum, 6)">
															<input type="checkbox" id="currentGradeNum" name="scoreInputInfo.scoreCurrentInfo.currentGradeNum" value="6" onchange="checkAtLeastOneCurrentGradeSelected()" checked="checked">
														</s:if>
														<s:else>
															<input type="checkbox" id="currentGradeNum" name="scoreInputInfo.scoreCurrentInfo.currentGradeNum" value="6" onchange="checkAtLeastOneCurrentGradeSelected()">
														</s:else>
														<s:text name="label.scoresearch.grade6" />
													</td>
													<td class="inner">
														<s:if test="@org.apache.commons.lang.ArrayUtils@contains(#session.scoreInputInfo.scoreCurrentInfo.currentGradeNum, 7)">
															<input type="checkbox" id="currentGradeNum" name="scoreInputInfo.scoreCurrentInfo.currentGradeNum" value="7" onchange="checkAtLeastOneCurrentGradeSelected()" checked="checked">
														</s:if>
														<s:else>
															<input type="checkbox" id="currentGradeNum" name="scoreInputInfo.scoreCurrentInfo.currentGradeNum" value="7" onchange="checkAtLeastOneCurrentGradeSelected()">
														</s:else>
														<s:text name="label.scoresearch.grade7" />
													</td>
													<td class="inner">
														<s:if test="@org.apache.commons.lang.ArrayUtils@contains(#session.scoreInputInfo.scoreCurrentInfo.currentGradeNum, 8)">
															<input type="checkbox" id="currentGradeNum" name="scoreInputInfo.scoreCurrentInfo.currentGradeNum" value="8" onchange="checkAtLeastOneCurrentGradeSelected()" checked="checked">
														</s:if>
														<s:else>
															<input type="checkbox" id="currentGradeNum" name="scoreInputInfo.scoreCurrentInfo.currentGradeNum" value="8" onchange="checkAtLeastOneCurrentGradeSelected()">
														</s:else>
														<s:text name="label.scoresearch.grade8" />
													</td>
													<td class="inner">
														<s:if test="@org.apache.commons.lang.ArrayUtils@contains(#session.scoreInputInfo.scoreCurrentInfo.currentGradeNum, 9)">
															<input type="checkbox" id="currentGradeNum" name="scoreInputInfo.scoreCurrentInfo.currentGradeNum" value="9" onchange="checkAtLeastOneCurrentGradeSelected()" checked="checked">
														</s:if>
														<s:else>
															<input type="checkbox" id="currentGradeNum" name="scoreInputInfo.scoreCurrentInfo.currentGradeNum" value="9" onchange="checkAtLeastOneCurrentGradeSelected()">
														</s:else>
														<s:text name="label.scoresearch.grade9" />
													</td>
													<td class="inner">
														<s:if test="@org.apache.commons.lang.ArrayUtils@contains(#session.scoreInputInfo.scoreCurrentInfo.currentGradeNum, -1)">
															<input type="checkbox" id="currentGradeNum" name="scoreInputInfo.scoreCurrentInfo.currentGradeNum" value="-1" onchange="checkAtLeastOneCurrentGradeSelected()" checked="checked">
														</s:if>
														<s:else>
															<input type="checkbox" id="currentGradeNum" name="scoreInputInfo.scoreCurrentInfo.currentGradeNum" value="-1" onchange="checkAtLeastOneCurrentGradeSelected()">
														</s:else>
														<s:text name="label.scoresearch.gradenotavailable" />
													</td>
												</tr>
												<tr>
													<td class="inner">
														&nbsp;
													</td>
													<td colspan="10" class="inner">
														<!-- For displaying ErrorMessage : Select at least one grade. -->
														<input type="text" id="selectCurrentGrade" name="selectCurrentGrade" style="border: none;background-color: transparent;color: red;width: 400px;" readonly="readonly">
													</td>
												</tr>
												<tr>
													<td class="inner" style="vertical-align: top;">
														<s:if test="#session.scoreInputInfo.scoreCurrentInfo.currentCategoryType == 4">
															<input type="radio" name="scoreInputInfo.scoreCurrentInfo.currentCategoryType" id="currentCategoryType4" checked="checked"
																value="4" onclick="changeCategoryType(true, false)" onkeypress="changeCategoryType(true, false)"/>&nbsp;<s:text name="label.scoresearch.pendingcategory" />&nbsp;
														</s:if>
														<s:else>
															<input type="radio" name="scoreInputInfo.scoreCurrentInfo.currentCategoryType" id="currentCategoryType4" 
																value="4" onclick="changeCategoryType(true, false)" onkeypress="changeCategoryType(true, false)"/>&nbsp;<s:text name="label.scoresearch.pendingcategory" />&nbsp;
														</s:else>
													</td>	
													<td colspan="10" class="inner" style="vertical-align: top;">
														<s:textfield id="currentPendingCategory" name="scoreInputInfo.scoreCurrentInfo.currentPendingCategory" cssClass="disable_bg" size="30" disabled="true" value="%{#session.scoreInputInfo.scoreCurrentInfo.currentPendingCategory}" />
														<input type="text" id="currentPendingCategoryHidden" name="currentPendingCategoryHidden" style="border: none;background-color: transparent;color: red;width: 00px;" readonly="readonly">
													</td>
												</tr>
												<tr>
													<td class="inner" style="vertical-align: top;">
													<s:if test="#session.scoreInputInfo.scoreCurrentInfo.currentCategoryType == 5">
														<input type="radio" name="scoreInputInfo.scoreCurrentInfo.currentCategoryType" id="currentCategoryType5" checked="checked"
													 			value="5" onclick="changeCategoryType(true, false)" onkeypress="changeCategoryType(true, false)"/>&nbsp;<s:text name="label.scoresearch.denycategory" />&nbsp;
													</s:if>
													<s:else>
												 		<input type="radio" name="scoreInputInfo.scoreCurrentInfo.currentCategoryType" id="currentCategoryType5"
													 			value="5" onclick="changeCategoryType(true, false)" onkeypress="changeCategoryType(true, false)"/>&nbsp;<s:text name="label.scoresearch.denycategory" />&nbsp;
													</s:else>
													</td>
													<td colspan="10" class="inner" style="vertical-align: top;">
												 		<s:textfield id="currentDenyCategory" name="scoreInputInfo.scoreCurrentInfo.currentDenyCategory" cssClass="disable_bg" size="30" disabled="true" value="%{#session.scoreInputInfo.scoreCurrentInfo.currentDenyCategory}" />
												 		<input type="text" id="currentDenyCategoryHidden" name="currentDenyCategoryHidden" style="border: none;background-color: transparent;color: red;width: 00px;" readonly="readonly">
												 	</td>
												 </tr>
											</table>
										</td>
									</tr>
		
		
									<!--最新SKP -->
									<tr>
										<th class="partition">
											<s:text name="label.scoresearch.currentcategorypending" />
										</th>
										<td colspan="5">
											<table>
												<tr>
													<td class="inner">
														<s:text name="label.scoresearch.currentincludecheckpoint" />
													</td>
													<td class="inner">
														<s:text name="label.colon" />
													</td>
													<td class="inner">
														<s:textfield id="currentIncludeCheckPoints" name="scoreInputInfo.scoreCurrentInfo.currentIncludeCheckPoints" size="30" value="%{#session.scoreInputInfo.scoreCurrentInfo.currentIncludeCheckPoints}" />
													</td>
													<td class="inner">
													       <input type="radio" id="currSkpAndCondition" name="scoreInputInfo.scoreCurrentInfo.currentSkpConditions" value="skpAndCondition" checked="checked"/>&nbsp;<s:text name="label.search.screen.skp.and.condition"></s:text>
													</td>
													<td class="inner">
													    <s:if test="#session.scoreInputInfo.scoreCurrentInfo.currentSkpConditions == @com.saiten.util.WebAppConst@SKP_OR_CONDITION">
													    	<input type="radio" id="currSkpORCondition" name="scoreInputInfo.scoreCurrentInfo.currentSkpConditions" value="skpORCondition" checked="checked"/>&nbsp;<s:text name="label.search.screen.skp.or.condition"></s:text>
													    </s:if><s:else>
													        <input type="radio" id="currSkpORCondition" name="scoreInputInfo.scoreCurrentInfo.currentSkpConditions" value="skpORCondition" />&nbsp;<s:text name="label.search.screen.skp.or.condition"></s:text>
													    </s:else>
													</td>
												</tr>
												<tr>
													<td class="inner">
														<s:text name="label.scoresearch.currentexcludecheckpoint" />
													</td>
													<td class="inner">
														<s:text name="label.colon" />
													</td>
													<td class="inner">
														<s:textfield id="currentExcludeCheckPoints" name="scoreInputInfo.scoreCurrentInfo.currentExcludeCheckPoints" size="30" value="%{#session.scoreInputInfo.scoreCurrentInfo.currentExcludeCheckPoints}" />
													</td>
												</tr>
												<tr>
													<td colspan="6" class="inner">
														<!-- For displaying ErrorMessage : Same check point should not selected into 'currentIncludeCheckPoints' and 'currentExcludeCheckPoints' -->
														<%-- <s:hidden id="currentCheckPointHidden" name="currentCheckPointHidden"></s:hidden> --%>
														<input type="text" id="currentCheckPointHidden" name="currentCheckPointHidden" style="border: none;background-color: transparent;color: red;width: 400px;" readonly="readonly">			
													</td>
												</tr>
											</table>
										</td>
									</tr>
		
		
									<!-- 採点状態 -->
									<tr> 
										<th class="partition">
											<s:text name="label.scoresearch.currentstatelist" /><br>
											<input type="button" name="stateCheckButton" value="<s:text name="label.scoresearch.selectall" />" onclick="changeCheckBox('scoreInputInfo.scoreCurrentInfo.currentStateList', true)">
											<input type="button" name="stateCheckButton" value="<s:text name="label.scoresearch.cancelall" />" onclick="changeCheckBox('scoreInputInfo.scoreCurrentInfo.currentStateList', false)">
										</th>
										<td colspan="5">
											<table>
												<tr>
													<s:iterator value="scoreSearchInfo.currentStateList" id="currentStateObj" status="currentStateStatus">
															<td class="inner">
																<s:set id="currentStateListKey" name="currentStateListKey" value="key" />
																<s:if test="@org.apache.commons.lang.ArrayUtils@contains(#session.scoreInputInfo.scoreCurrentInfo.currentStateList, #currentStateListKey)">
																	<input type="checkbox" id="currentStateList" name="scoreInputInfo.scoreCurrentInfo.currentStateList" value='<s:property value="key"/>' onchange="checkAtLeastOneCurrentStateSelected()" checked="checked">&nbsp;<s:property value="value"/>
																</s:if>
																<s:else>
																	<input type="checkbox" id="currentStateList" name="scoreInputInfo.scoreCurrentInfo.currentStateList" value='<s:property value="key"/>' onchange="checkAtLeastOneCurrentStateSelected()">&nbsp;<s:property value="value"/>
																</s:else>
															</td>
															<s:if test="#currentStateStatus.count % 3 == 0">
																</tr><tr>
															</s:if>
													</s:iterator> 
												</tr>
												<tr>
													<td colspan="5" class="inner">
														<!-- For displaying errorMessage, For select at least on state. -->
														<input type="text" id="selectCurrentState" name="selectCurrentState" style="border: none;background-color: transparent;color: red;width: 400px;" readonly="readonly">
													</td>
												</tr>
											</table>
										</td>
									</tr>
		
									<!-- 日付 -->
									<tr>
										<th class="partition">
											<s:text name="label.scoresearch.currentupdatedate" />
										</th>
										<td colspan="5">
											<table>
												<tr>
													<td style="border: none;padding: 0px;">
														<s:select id="currentUpdateDateStartYear" name="scoreInputInfo.scoreCurrentInfo.currentUpdateDateStartYear" list="scoreSearchInfo.yearList" value="%{#session.scoreInputInfo.scoreCurrentInfo.currentUpdateDateStartYear}" />
														<s:text name="label.year" />
														<s:select id="currentUpdateDateStartMonth" name="scoreInputInfo.scoreCurrentInfo.currentUpdateDateStartMonth" list="scoreSearchInfo.monthList" value="%{#session.scoreInputInfo.scoreCurrentInfo.currentUpdateDateStartMonth}" />
														<s:text name="label.month" />
														<s:select id="currentUpdateDateStartDay" name="scoreInputInfo.scoreCurrentInfo.currentUpdateDateStartDay" list="scoreSearchInfo.daysList" value="%{#session.scoreInputInfo.scoreCurrentInfo.currentUpdateDateStartDay}" />
											
														<s:text name="label.day" />
														<s:select id="currentUpdateDateStartHours" name="scoreInputInfo.scoreCurrentInfo.currentUpdateDateStartHours" list="scoreSearchInfo.hoursList" value="%{#session.scoreInputInfo.scoreCurrentInfo.currentUpdateDateStartHours}" />
											
														<s:text name="label.hours" />
														<s:select id="currentUpdateDateStartMin" name="scoreInputInfo.scoreCurrentInfo.currentUpdateDateStartMin" list="scoreSearchInfo.minutesList" value="%{#session.scoreInputInfo.scoreCurrentInfo.currentUpdateDateStartMin}" />
											
														<s:text name="label.minutes" />
														&nbsp;
														<s:text name="label.tilde" />
														&nbsp;
														<s:select id="currentUpdateDateEndYear" name="scoreInputInfo.scoreCurrentInfo.currentUpdateDateEndYear" list="scoreSearchInfo.yearList" value="%{#session.scoreInputInfo.scoreCurrentInfo.currentUpdateDateEndYear}" />
														<s:text name="label.year" /> 
														<s:select id="currentUpdateDateEndMonth" name="scoreInputInfo.scoreCurrentInfo.currentUpdateDateEndMonth" list="scoreSearchInfo.monthList" value="%{#session.scoreInputInfo.scoreCurrentInfo.currentUpdateDateEndMonth}" />
														<s:text name="label.month" />
														<s:select id="currentUpdateDateEndDay" name="scoreInputInfo.scoreCurrentInfo.currentUpdateDateEndDay" list="scoreSearchInfo.daysList" value="%{#session.scoreInputInfo.scoreCurrentInfo.currentUpdateDateEndDay}" />
		
														<s:text name="label.day" />
														<s:select id="currentUpdateDateEndHours" name="scoreInputInfo.scoreCurrentInfo.currentUpdateDateEndHours" list="scoreSearchInfo.hoursList" value="%{#session.scoreInputInfo.scoreCurrentInfo.currentUpdateDateEndHours}" />
													
														<s:text name="label.hours" />
														<s:select id="currentUpdateDateEndMin" name="scoreInputInfo.scoreCurrentInfo.currentUpdateDateEndMin" list="scoreSearchInfo.minutesList" value="%{#session.scoreInputInfo.scoreCurrentInfo.currentUpdateDateEndMin}" />
											
														<s:text name="label.minutes" />
													</td>
												</tr>
												<tr>
													<td style="border: none;padding: 0px;">
														<s:hidden name="currentFromValidDate" id="currentFromValidDate"></s:hidden>
														<s:hidden name="currentToValidDate" id="currentToValidDate"></s:hidden>
														<s:hidden name="currentValidDate" id="currentValidDate"></s:hidden>
														<input type="text" id="currentDateError" name="currentDateError" style="border: none;background-color: transparent;color: red;width: 400px;" readonly="readonly">
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
					       </div>
					       <div id="button" align="center">
							<!--<input type="button" name="submit" value="ブックマークから削除" class="white_middle left">-->
							
							    <a>
							    		<%-- <s:submit type="image" src="./material/img/button/SerchCrt_bn-login.gif" cssClass="rollover infobutton_without_margin" id="search" cssStyle="width: 144px; height: 39px;" label="%{getText('label.next')}"/> --%>
							    		<s:submit type="button" cssClass="btn btn-primary btn-xl infobutton_without_margin" id="search" cssStyle="width: 144px; height: 39px;" label="%{getText('label.next')}"/>
		  						</a>
								
							
						  </div>
		        </div><!-- Close Third Div-->
				
		</s:form>
		</td>
		</tr>
	</table>
	</tiles:putAttribute>
</tiles:insertDefinition> 
