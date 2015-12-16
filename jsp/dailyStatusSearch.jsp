<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script>

var backButtonSession=<%=session.getAttribute("Back") %>;
</script>
 <% session.setAttribute("Back",null); %>
<tiles:insertDefinition name="baseLayout">
	<tiles:putAttribute name="head-title">
		<title><s:text name="menu.btn.dailyStatusSearch"/></title>
	</tiles:putAttribute>
	<tiles:putAttribute name="head-addition">
		<!-- This portion will contains js file includes and js code also. -->
		 <script type="text/javascript" src="./material/scripts/js/dailyStatusSearch.js"></script> 
		<script type="text/javascript" src="./material/scripts/js/additionalMethod_validate.js"></script>
		<script type="text/javascript" src="./material/scripts/js/date_validation.js"></script>

		<script type="text/javascript" src="./material/scripts/jQuery/jquery.validate.js" ></script>
		<style type="text/css">
 			label { width: auto; float: none; color: red;  display: block; }
		</style>
	</tiles:putAttribute>
	<tiles:putAttribute name="body-title">
		<!-- This portion will contain perticular page body title. -->
	</tiles:putAttribute>
	<tiles:putAttribute name="body-main">
		<!-- This portion will contain main body of perticular page -->
		<input type="hidden" id="loggedInScorerSubjectList" value='<s:property value="loggedInScorerSubjectList"/> ' >
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
		<s:form name="dailyStatusSearchForm" id="dailyStatusSearchForm" method="post" action="dailyStatusReportSearchList">
		 <input type="hidden" name="searchMode" value="default">
		     <div class="search_box">
		     	 <div id="questionTypeDiv">
					<table class="search_form search_table_width">
									<!-- 教科 -->
									<tr>
										<th class="partition">
											<s:text name="label.dailyStatusReport.questionType" />
										</th>
										<td colspan="5">
											<s:set var="incrementer" value="1"></s:set>
										  <s:iterator value="scoreSearchInfo.questionTypeMap" id="questionTypeObj" status="questionTypeStatus">
														
															<s:set id="questionTypeKey" name="questionTypeKey" value="key" />
															<s:if test="#session.dailyStatusSearchInfo != null">
																<s:if test="#session.dailyStatusSearchInfo.evalSeq == #questionTypeKey">
																	<input type="radio" id="questionTypeMap" class="questionType" name="dailyStatusSearchInfo.evalSeq" value='<s:property value="key"/>' checked="checked">&nbsp;<s:property value="value"/>
																</s:if>
																<s:else>
																	<input type="radio" id="questionTypeMap" class="questionType" name="dailyStatusSearchInfo.evalSeq" value='<s:property value="key"/>' >&nbsp;<s:property value="value"/>
																</s:else>
															</s:if><s:else>
																<s:if test="#incrementer == 1">
																	<input type="radio" id="questionTypeMap" class="questionType" name="dailyStatusSearchInfo.evalSeq" value='<s:property value="key"/>' checked="checked">&nbsp;<s:property value="value"/>
																	<s:set var="incrementer" value="2"></s:set>
																</s:if><s:else>
																	<input type="radio" id="questionTypeMap" class="questionType" name="dailyStatusSearchInfo.evalSeq" value='<s:property value="key"/>' >&nbsp;<s:property value="value"/>	
																</s:else>
																
															</s:else>
															
														
													</s:iterator>
														
										   <%-- <s:if test='%{dailyStatusSearchInfo.quetionTypeRadio!=null && dailyStatusSearchInfo.quetionTypeRadio.equals(""+@com.saiten.util.WebAppConst@SPEAKING_TYPE)}'>
											  <input type="radio" name="dailyStatusSearchInfo.quetionTypeRadio" class="questionType" id="writingRadio" value='<s:property value="@com.saiten.util.WebAppConst@WRITING_TYPE" />' /><s:text name="label.dailyStatusReport.questionType.writing" />
											&nbsp;&nbsp; <input type="radio" name="dailyStatusSearchInfo.quetionTypeRadio" class="questionType" id="speakingRadio" checked="checked" value='<s:property value="@com.saiten.util.WebAppConst@SPEAKING_TYPE" />' /> <s:text name="label.dailyStatusReport.questionType.speaking" />
											</s:if>
											<s:else>
											  <input type="radio" name="dailyStatusSearchInfo.quetionTypeRadio" class="questionType" id="writingRadio" value='<s:property value="@com.saiten.util.WebAppConst@WRITING_TYPE" />' checked="checked"/><s:text name="label.dailyStatusReport.questionType.writing" />
											&nbsp;&nbsp; <input type="radio" name="dailyStatusSearchInfo.quetionTypeRadio" class="questionType" id="speakingRadio" value='<s:property value="@com.saiten.util.WebAppConst@SPEAKING_TYPE" />' /> <s:text name="label.dailyStatusReport.questionType.speaking" />
											</s:else>	 --%>
											<!-- For saitama purpose -->
											<%-- <s:if test='%{dailyStatusSearchInfo.quetionTypeRadio!=null && dailyStatusSearchInfo.quetionTypeRadio.equals(""+@com.saiten.util.WebAppConst@SHORT_TYPE)}'>
											  <input type="radio" name="dailyStatusSearchInfo.quetionTypeRadio" class="questionType" id="shortRadio"  checked="checked" value='<s:property value="@com.saiten.util.WebAppConst@SHORT_TYPE" />' /><s:text name="label.dailyStatusReport.questionType.short" />
											&nbsp;&nbsp; <input type="radio" name="dailyStatusSearchInfo.quetionTypeRadio" class="questionType" id="shortsScreenObjectiveRadio" value='<s:property value="@com.saiten.util.WebAppConst@SHORT_SCREEN_OBJECTIVE_TYPE" />' /><s:text name="label.dailyStatusReport.questionType.shortscreenobjective" />
											&nbsp;&nbsp; <input type="radio" name="dailyStatusSearchInfo.quetionTypeRadio" class="questionType" id="longRadio" value='<s:property value="@com.saiten.util.WebAppConst@LONG_TYPE" />' /> <s:text name="label.dailyStatusReport.questionType.long" />
											&nbsp;&nbsp; <input type="radio" name="dailyStatusSearchInfo.quetionTypeRadio" class="questionType" id="longScreenObjectiveRadio" value='<s:property value="@com.saiten.util.WebAppConst@LONG_SCREEN_OBJECITVE_TYPE" />' /> <s:text name="label.dailyStatusReport.questionType.longscreenobjective" />
											&nbsp;&nbsp; <input type="radio" name="dailyStatusSearchInfo.quetionTypeRadio" class="questionType" id="batchScoringRadio" value='<s:property value="@com.saiten.util.WebAppConst@BATCH_SCORING_TYPE" />' /> <s:text name="label.dailyStatusReport.questionType.batch" />
											</s:if>
											<s:elseif test='%{dailyStatusSearchInfo.quetionTypeRadio!=null && dailyStatusSearchInfo.quetionTypeRadio.equals(""+@com.saiten.util.WebAppConst@SHORT_SCREEN_OBJECTIVE_TYPE)}'>
											  <input type="radio" name="dailyStatusSearchInfo.quetionTypeRadio" class="questionType" id="shortRadio" value='<s:property value="@com.saiten.util.WebAppConst@SHORT_TYPE" />' /><s:text name="label.dailyStatusReport.questionType.short" />
											&nbsp;&nbsp; <input type="radio" name="dailyStatusSearchInfo.quetionTypeRadio" class="questionType" id="shortsScreenObjectiveRadio" checked="checked" value='<s:property value="@com.saiten.util.WebAppConst@SHORT_SCREEN_OBJECTIVE_TYPE" />' /><s:text name="label.dailyStatusReport.questionType.shortscreenobjective" />
											&nbsp;&nbsp; <input type="radio" name="dailyStatusSearchInfo.quetionTypeRadio" class="questionType" id="longRadio" value='<s:property value="@com.saiten.util.WebAppConst@LONG_TYPE" />' /> <s:text name="label.dailyStatusReport.questionType.long" />
											&nbsp;&nbsp; <input type="radio" name="dailyStatusSearchInfo.quetionTypeRadio" class="questionType" id="longScreenObjectiveRadio" value='<s:property value="@com.saiten.util.WebAppConst@LONG_SCREEN_OBJECITVE_TYPE" />' /> <s:text name="label.dailyStatusReport.questionType.longscreenobjective" />
											&nbsp;&nbsp; <input type="radio" name="dailyStatusSearchInfo.quetionTypeRadio" class="questionType" id="batchScoringRadio" value='<s:property value="@com.saiten.util.WebAppConst@BATCH_SCORING_TYPE" />' /> <s:text name="label.dailyStatusReport.questionType.batch" />
											</s:elseif>
											<s:elseif test='%{dailyStatusSearchInfo.quetionTypeRadio!=null && dailyStatusSearchInfo.quetionTypeRadio.equals(""+@com.saiten.util.WebAppConst@LONG_TYPE)}'>
											  <input type="radio" name="dailyStatusSearchInfo.quetionTypeRadio" class="questionType" id="shortRadio" value='<s:property value="@com.saiten.util.WebAppConst@SHORT_TYPE" />' /><s:text name="label.dailyStatusReport.questionType.short" />
											&nbsp;&nbsp; <input type="radio" name="dailyStatusSearchInfo.quetionTypeRadio" class="questionType" id="shortsScreenObjectiveRadio" value='<s:property value="@com.saiten.util.WebAppConst@SHORT_SCREEN_OBJECTIVE_TYPE" />' /><s:text name="label.dailyStatusReport.questionType.shortscreenobjective" />
											&nbsp;&nbsp; <input type="radio" name="dailyStatusSearchInfo.quetionTypeRadio" class="questionType" id="longRadio" checked="checked" value='<s:property value="@com.saiten.util.WebAppConst@LONG_TYPE" />' /> <s:text name="label.dailyStatusReport.questionType.long" />
											&nbsp;&nbsp; <input type="radio" name="dailyStatusSearchInfo.quetionTypeRadio" class="questionType" id="longScreenObjectiveRadio" value='<s:property value="@com.saiten.util.WebAppConst@LONG_SCREEN_OBJECITVE_TYPE" />' /> <s:text name="label.dailyStatusReport.questionType.longscreenobjective" />
											&nbsp;&nbsp; <input type="radio" name="dailyStatusSearchInfo.quetionTypeRadio" class="questionType" id="batchScoringRadio" value='<s:property value="@com.saiten.util.WebAppConst@BATCH_SCORING_TYPE" />' /> <s:text name="label.dailyStatusReport.questionType.batch" />
											</s:elseif>	
											<s:elseif test='%{dailyStatusSearchInfo.quetionTypeRadio!=null && dailyStatusSearchInfo.quetionTypeRadio.equals(""+@com.saiten.util.WebAppConst@LONG_SCREEN_OBJECITVE_TYPE)}'>
											  <input type="radio" name="dailyStatusSearchInfo.quetionTypeRadio" class="questionType" id="shortRadio"  value='<s:property value="@com.saiten.util.WebAppConst@SHORT_TYPE" />' /><s:text name="label.dailyStatusReport.questionType.short" />
											&nbsp;&nbsp; <input type="radio" name="dailyStatusSearchInfo.quetionTypeRadio" class="questionType" id="shortsScreenObjectiveRadio" value='<s:property value="@com.saiten.util.WebAppConst@SHORT_SCREEN_OBJECTIVE_TYPE" />' /><s:text name="label.dailyStatusReport.questionType.shortscreenobjective" />
											&nbsp;&nbsp; <input type="radio" name="dailyStatusSearchInfo.quetionTypeRadio" class="questionType" id="longRadio" value='<s:property value="@com.saiten.util.WebAppConst@LONG_TYPE" />' /> <s:text name="label.dailyStatusReport.questionType.long" />
											&nbsp;&nbsp; <input type="radio" name="dailyStatusSearchInfo.quetionTypeRadio" class="questionType" id="longScreenObjectiveRadio" checked="checked" value='<s:property value="@com.saiten.util.WebAppConst@LONG_SCREEN_OBJECITVE_TYPE" />' /> <s:text name="label.dailyStatusReport.questionType.longscreenobjective" />
											&nbsp;&nbsp; <input type="radio" name="dailyStatusSearchInfo.quetionTypeRadio" class="questionType" id="batchScoringRadio" value='<s:property value="@com.saiten.util.WebAppConst@BATCH_SCORING_TYPE" />' /> <s:text name="label.dailyStatusReport.questionType.batch" />
											</s:elseif>
											<s:elseif test='%{dailyStatusSearchInfo.quetionTypeRadio!=null && dailyStatusSearchInfo.quetionTypeRadio.equals(""+@com.saiten.util.WebAppConst@BATCH_SCORING_TYPE)}'>
											  <input type="radio" name="dailyStatusSearchInfo.quetionTypeRadio" class="questionType" id="shortRadio"  value='<s:property value="@com.saiten.util.WebAppConst@SHORT_TYPE" />' /><s:text name="label.dailyStatusReport.questionType.short" />
											&nbsp;&nbsp; <input type="radio" name="dailyStatusSearchInfo.quetionTypeRadio" class="questionType" id="shortsScreenObjectiveRadio" value='<s:property value="@com.saiten.util.WebAppConst@SHORT_SCREEN_OBJECTIVE_TYPE" />' /><s:text name="label.dailyStatusReport.questionType.shortscreenobjective" />
											&nbsp;&nbsp; <input type="radio" name="dailyStatusSearchInfo.quetionTypeRadio" class="questionType" id="longRadio" value='<s:property value="@com.saiten.util.WebAppConst@LONG_TYPE" />' /> <s:text name="label.dailyStatusReport.questionType.long" />
											&nbsp;&nbsp; <input type="radio" name="dailyStatusSearchInfo.quetionTypeRadio" class="questionType" id="longScreenObjectiveRadio" value='<s:property value="@com.saiten.util.WebAppConst@LONG_SCREEN_OBJECITVE_TYPE" />' /> <s:text name="label.dailyStatusReport.questionType.longscreenobjective" />
											&nbsp;&nbsp; <input type="radio" name="dailyStatusSearchInfo.quetionTypeRadio" class="questionType" id="batchScoringRadio" checked="checked" value='<s:property value="@com.saiten.util.WebAppConst@BATCH_SCORING_TYPE" />' /> <s:text name="label.dailyStatusReport.questionType.batch" />
											</s:elseif>
											<s:else>
												<input type="radio" name="dailyStatusSearchInfo.quetionTypeRadio" class="questionType" id="shortRadio"  checked="checked" value='<s:property value="@com.saiten.util.WebAppConst@SHORT_TYPE" />' /><s:text name="label.dailyStatusReport.questionType.short" />
											&nbsp;&nbsp; <input type="radio" name="dailyStatusSearchInfo.quetionTypeRadio" class="questionType" id="shortsScreenObjectiveRadio" value='<s:property value="@com.saiten.util.WebAppConst@SHORT_SCREEN_OBJECTIVE_TYPE" />' /><s:text name="label.dailyStatusReport.questionType.shortscreenobjective" />
											&nbsp;&nbsp; <input type="radio" name="dailyStatusSearchInfo.quetionTypeRadio" class="questionType" id="longRadio" value='<s:property value="@com.saiten.util.WebAppConst@LONG_TYPE" />' /> <s:text name="label.dailyStatusReport.questionType.long" />
											&nbsp;&nbsp; <input type="radio" name="dailyStatusSearchInfo.quetionTypeRadio" class="questionType" id="longScreenObjectiveRadio"  value='<s:property value="@com.saiten.util.WebAppConst@LONG_SCREEN_OBJECITVE_TYPE" />' /> <s:text name="label.dailyStatusReport.questionType.longscreenobjective" />
											&nbsp;&nbsp; <input type="radio" name="dailyStatusSearchInfo.quetionTypeRadio" class="questionType" id="batchScoringRadio" value='<s:property value="@com.saiten.util.WebAppConst@BATCH_SCORING_TYPE" />' /> <s:text name="label.dailyStatusReport.questionType.batch" />
											</s:else>	 --%>									
										</td>
									</tr>
								</table>
								</div>
								</div>	  
		    <div class="search_box">
		          <s:if test="%{dailyStatusSearchInfo.dailyStatusSearchRadioButton!=null && dailyStatusSearchInfo.dailyStatusSearchRadioButton.equals(@com.saiten.util.WebAppConst@SCORERWISE_SEARCH)}">
		            <h1 align="left"><input type="radio" class="searchType" name="dailyStatusSearchInfo.dailyStatusSearchRadioButton" id="questionwiseSearchRadio" value="<s:property value="@com.saiten.util.WebAppConst@QUESTIONWISE_SEARCH" />"><s:text name="label.dailyStatusSearch.qustionwiseSearch" /></h1>
		          </s:if>
		     	  <s:else>
		     	   <h1 align="left"><input type="radio" class="searchType" name="dailyStatusSearchInfo.dailyStatusSearchRadioButton" id="questionwiseSearchRadio" value="<s:property value="@com.saiten.util.WebAppConst@QUESTIONWISE_SEARCH" />" checked="checked"><s:text name="label.dailyStatusSearch.qustionwiseSearch" /></h1>
		     	  </s:else>
		     	  		
		     	 <div id="questionwiseDiv">
					<table class="search_form search_table_width">
									
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
													
													<c:set var="sessionSubjectCode" value="${dailyStatusSearchInfo.subjectCode}" />
													
													<c:choose>
														<c:when test="${not ((roleId eq 4) or (fn:contains(loggedInScorerSubjectList,fn:split(subjectNameKey, '-')[1])))}">
															<input type="radio" id="subjectCode" name="dailyStatusSearchInfo.subjectCode" value="${fn:split(entry.key, '-')[1]}" disabled="disabled">&nbsp;<c:out value="${entry.value}"/>
														</c:when>
														<c:otherwise>
														<c:choose>
															<c:when test="${fn:contains(sessionSubjectCode, fn:split(subjectNameKey, '-')[1])}">
																<input type="radio" id="subjectCode" name="dailyStatusSearchInfo.subjectCode" checked="checked" value="${fn:split(entry.key, '-')[1]}">&nbsp;<c:out value="${entry.value}"/>
															</c:when>
															<c:otherwise>
																<input type="radio" id="subjectCode" name="dailyStatusSearchInfo.subjectCode" value="${fn:split(entry.key, '-')[1]}">&nbsp;<c:out value="${entry.value}"/>													
															</c:otherwise>
														</c:choose>
														</c:otherwise>
													</c:choose>
														
													<c:set var="schoolType" value="${fn:split(subjectNameKey, '-')[0]}" />
											</c:forEach>
										</td>
									</tr>
									
									<tr>
										<th class="partition">
											<s:text name="label.dailyStatusReport.questionnumber" />
										</th>
										<td class="search_form_class1" style="padding-right: 0px;">
											<s:textfield id="questionNum" name="dailyStatusSearchInfo.questionNum" maxlength="5" size="15" onkeypress='validateNUM(event,this)' />
										</td>
									</tr>
<%-- 									<tr>
										<th class="partition">
											<s:text name="label.dailyStatusReport.questionwiseSearchDate" /><br/>
										</th>
										<td colspan="5">
											<table >
												<tr >
													<td style="border: none;padding: 0px;">
													 <table style="border: none;padding: 0px;">
													  <tr>
													  <td style="border: none;padding: 0px;">
														<s:text name="dailyStatusSearch.report.label.start.date" />
													 </td>
													 <td style="border: none;padding: 0px;">	
														<s:text name="label.colon" />&nbsp;
													 </td>
													 <td style="border: none;padding: 0px;">	
														<s:select id="questionwiseUpdateDateStartYear" name="dailyStatusSearchInfo.questionwiseSelectedYearFrom" list="scoreSearchInfo.yearList"  />
														<s:text name="label.year" />
														<s:select id="questionwiseUpdateDateStartMonth" name="dailyStatusSearchInfo.questionwiseSelectedMonthFrom" list="scoreSearchInfo.monthList" />
											
														<s:text name="label.month" />
														<s:select id="questionwiseUpdateDateStartDay" name="dailyStatusSearchInfo.questionwiseSelectedDayFrom" list="scoreSearchInfo.daysList"  />
											
														<s:text name="label.day" />
														<s:select id="questionwiseUpdateDateStartHours" name="dailyStatusSearchInfo.questionwiseSelectedStartHours" list="scoreSearchInfo.hoursList" />
											
														<s:text name="label.hours" />
														<s:select id="questionwiseUpdateDateStartMin" name="dailyStatusSearchInfo.questionwiseSelectedStartMin" list="scoreSearchInfo.minutesList" />
													
														<s:text name="label.minutes" />
													  </td>
													  </tr>
													  	<tr>
													  <td style="border: none;padding: 0px;">
														<s:text name="dailyStatusSearch.report.label.end.date" />
													 </td>
													 <td style="border: none;padding: 0px;">	
														<s:text name="label.colon" />&nbsp;
													 </td>
													 <td style="border: none;padding: 0px;">	
														<s:select id="questionwiseUpdateDateEndYear" name="dailyStatusSearchInfo.questionwiseSelectedYearTo" list="scoreSearchInfo.yearList"  />
														<s:text name="label.year" />
														<s:select id="questionwiseUpdateDateEndMonth" name="dailyStatusSearchInfo.questionwiseSelectedMonthTo" list="scoreSearchInfo.monthList"  />
											
														<s:text name="label.month" />
														<s:select id="questionwiseUpdateDateEndDay" name="dailyStatusSearchInfo.questionwiseSelectedDayTo" list="scoreSearchInfo.daysList" />
											
														<s:text name="label.day" />
														<s:select id="questionwiseUpdateDateEndHours" name="dailyStatusSearchInfo.questionwiseSelectedEndHours" list="scoreSearchInfo.hoursList"  />
											
														<s:text name="label.hours" />
														<s:select id="questionwiseUpdateDateEndMin" name="dailyStatusSearchInfo.questionwiseSelectedEndMin" list="scoreSearchInfo.minutesList" />
											
														<s:text name="label.minutes" />
													</td>	
													</table>	
													</td>
												</tr>
												<tr>
													<td style="border: none;padding: 0px;">
														<!-- For Displaying date validations ErrorMessages -->
														<s:hidden name="questionwiseFromValidDate" id="questionwiseFromValidDate"></s:hidden>
														<s:hidden name="questionwiseToValidDate" id="questionwiseToValidDate"></s:hidden>
														<s:hidden name="questionwiseValidDate" id="questionwiseValidDate"></s:hidden>
													</td>
												</tr>
											</table>
											
										</td>
									</tr>
 --%>							</table>	
				    </div>
		  </div><!-- Close First Div-->
<%-- 		    <div class="search_box">
		    	  <s:if test="%{dailyStatusSearchInfo.dailyStatusSearchRadioButton!=null && dailyStatusSearchInfo.dailyStatusSearchRadioButton.equals(@com.saiten.util.WebAppConst@SCORERWISE_SEARCH)}">
		            <h1 align="left"><input type="radio" class="searchType" checked="checked" name="dailyStatusSearchInfo.dailyStatusSearchRadioButton" id="scorewiseSearchRadio" value="<s:property value="@com.saiten.util.WebAppConst@SCORERWISE_SEARCH" />"><s:text name="label.dailyStatusSearch.userwiseSearch" /></h1>
		          </s:if>
		     	  <s:else>
		     	   <h1 align="left"><input type="radio" class="searchType" name="dailyStatusSearchInfo.dailyStatusSearchRadioButton" id="scorewiseSearchRadio" value="<s:property value="@com.saiten.util.WebAppConst@SCORERWISE_SEARCH" />"><s:text name="label.dailyStatusSearch.userwiseSearch" /></h1>
		     	  </s:else>
		     			
		     		<div id="scorewiseDiv">
					<table class="search_form search_table_width">
									<!-- 教科 -->
									<tr>
										<th class="partition">
											<s:text name="label.dailyStatusSearch.selectRoll" />
										</th>
										<td style="padding-right: 0px;" > 
										 <s:if test="%{dailyStatusSearchInfo.selectedRollList!=null && !dailyStatusSearchInfo.selectedRollList.isEmpty()}">
										  <s:checkboxlist id="selectedRollCheckBoxList" theme="horizantal-checkbox"  name="dailyStatusSearchInfo.selectedRoll" list="%{rollMap}" value="%{dailyStatusSearchInfo.selectedRollList}" />
									    </s:if>
									    <s:else>
									      <s:checkboxlist id="selectedRollCheckBoxList" theme="horizantal-checkbox"  name="dailyStatusSearchInfo.selectedRoll" list="%{rollMap}" value="%{defaultSelectedRoll}" />
									    </s:else>
									    </td>
									</tr>
									<!-- 設問番号、答案番号 -->
									<tr>
										<th class="partition">
											<s:text name="label.dailyStatusReport.userId" />
										</th>
										<td class="search_form_class1" style="padding-right: 0px;">
											<s:textfield id="userId" name="dailyStatusSearchInfo.userId" onkeypress='validateAplhaNum(event,this)' maxlength="7" size="15" />
										</td>
									</tr>
									<tr>
										<th class="partition">
											<s:text name="label.dailyStatusReport.userwiseSearchDate" /><br/>
										</th>
										<td colspan="5">
											<table >
												<tr >
													<td style="border: none;padding: 0px;">
													 <table style="border: none;padding: 0px;">
													  <tr>
													  <td style="border: none;padding: 0px;">
														<s:text name="dailyStatusSearch.report.label.start.date" />
													 </td>
													 <td style="border: none;padding: 0px;">	
														<s:text name="label.colon" />&nbsp;
													 </td>
													 <td style="border: none;padding: 0px;">
													  	<s:select id="scorewiseUpdateDateStartYear" name="dailyStatusSearchInfo.scorewiseSelectedYearFrom" list="scoreSearchInfo.yearList"  />
														<s:text name="label.year" />
														<s:select id="scorewiseUpdateDateStartMonth" name="dailyStatusSearchInfo.scorewiseSelectedMonthFrom" list="scoreSearchInfo.monthList" />
											
														<s:text name="label.month" />
														<s:select id="scorewiseUpdateDateStartDay" name="dailyStatusSearchInfo.scorewiseSelectedDayFrom" list="scoreSearchInfo.daysList"  />
											
														<s:text name="label.day" />
														<s:select id="scorewiseUpdateDateStartHours" name="dailyStatusSearchInfo.scorewiseSelectedStartHours" list="scoreSearchInfo.hoursList" />
											
														<s:text name="label.hours" />
														<s:select id="scorewiseUpdateDateStartMin" name="dailyStatusSearchInfo.scorewiseSelectedStartMin" list="scoreSearchInfo.minutesList"  />
													
														<s:text name="label.minutes" />														
													</td>
													</tr>
													  <tr>
													  <td style="border: none;padding: 0px;">
														<s:text name="dailyStatusSearch.report.label.end.date" />
													 </td>
													 <td style="border: none;padding: 0px;">	
														<s:text name="label.colon" />&nbsp;
													 </td>
													<td style="border: none;padding: 0px;">
														<s:select id="scorewiseUpdateDateEndYear" name="dailyStatusSearchInfo.scorewiseSelectedYearTo" list="scoreSearchInfo.yearList" />
														<s:text name="label.year" />
														<s:select id="scorewiseUpdateDateEndMonth" name="dailyStatusSearchInfo.scorewiseSelectedMonthTo" list="scoreSearchInfo.monthList"  />
											
														<s:text name="label.month" />
														<s:select id="scorewiseUpdateDateEndDay" name="dailyStatusSearchInfo.scorewiseSelectedDayTo" list="scoreSearchInfo.daysList"  />
											
														<s:text name="label.day" />
														<s:select id="scorewiseUpdateDateEndHours" name="dailyStatusSearchInfo.scorewiseSelectedEndHours" list="scoreSearchInfo.hoursList"  />
											
														<s:text name="label.hours" />
														<s:select id="scorewiseUpdateDateEndMin" name="dailyStatusSearchInfo.scorewiseSelectedEndMin" list="scoreSearchInfo.minutesList"  />
											
														<s:text name="label.minutes" />
													</td>
													</tr>
													</table>	 
													</td>
												</tr>
												<tr>
													<td style="border: none;padding: 0px;">
														<!-- For Displaying date validations ErrorMessages -->
														<s:hidden name="scorewiseFromValidDate" id="scorewiseFromValidDate"></s:hidden>
														<s:hidden name="scorewiseToValidDate" id="scorewiseToValidDate"></s:hidden>
														<s:hidden name="scorewiseValidDate" id="scorewiseValidDate"></s:hidden>
													</td>
												</tr>
											</table>
											
										</td>
									</tr>
							</table>	
				    </div>
		  </div> --%><!-- Close First Div-->
		  
		<div id="button" style="text-align: right;margin-right: 40px;" >
	    		<s:submit id="search" value='%{getText("menu.btn.dailyStatusSearch")}'   cssStyle="height: 39px;" cssClass="btn btn-primary btn-xl" ></s:submit>
		</div>
		
		</s:form>
		</td>
		</tr>
	</table>
	</tiles:putAttribute>
</tiles:insertDefinition> 
