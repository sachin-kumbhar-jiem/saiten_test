﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle var="global" basename="global" scope="session" />  
<tiles:insertDefinition name="baseLayout">
	<tiles:putAttribute name="head-title"> 
			<s:if test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FIRST_SCORING_MENU_ID">
				<title><s:text name="firstTime.history.title" /></title>
			</s:if><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@SECOND_SCORING_MENU_ID">
				<title><s:text name="secondTime.history.title" /></title>
			</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@CHECKING_MENU_ID">
				<title><s:text name="checking.history.title" /></title>
			</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@DENY_MENU_ID">
				<title><s:text name="deny.history.title" /></title>
			</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@PENDING_MENU_ID">
				<title><s:text name="pending.history.title" /></title>
			</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@INSPECTION_MENU_ID">
				<title><s:text name="inspection.history.title" /></title>
			</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@MISMATCH_MENU_ID">
				<title><s:text name="mismatch.history.title" /></title>
			</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@NO_GRADE_MENU_ID">
				<title><s:text name="nograde.history.title" /></title>
			</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@OUT_BOUNDARY_MENU_ID">
				<title><s:text name="outofboundary.history.title" /></title>
			</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@SCORE_SAMP_MENU_ID">
				<title><s:text name="scoresampling.history.title"/></title>
			</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_BLIND_TYPE_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_LANGUAGE_SUPPORT_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID">
				<title><s:text name="specialscoresearch.history.title" /></title>
			</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID">
				<title><s:text name="label.forcedscoringhistorylist.title"/></title>
			</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FIRST_SCORING_QUALITY_CHECK_MENU_ID">
				<title><s:text name="label.firsttimequalitycheckhistorylist.title"/></title>
			</s:elseif>
	</tiles:putAttribute>
	<tiles:putAttribute name="head-addition">
		<!-- This portion will contains js file includes and js code also. -->
		<style>
			@import url("./material/css/displaytag.css");
			@import url("./material/css/screen.css");
		</style>
		<script type="text/javascript" language="javascript">
			$(document).ready(function(){
				$("body").addClass("body-overflow-auto-history-bookmark");
				$("#resetHistory").focus();
			});
		</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="body-title">
		<!-- This portion will contain perticular page body title. -->
	</tiles:putAttribute>
	<tiles:putAttribute name="body-main">
<table class="table_width_height">
 <tr>
   <td class="table-side-menu-td" width="10%">
	  <div id="side_menu"> <!-- 1st td --> 
		     <div class="side_menu_top_margin">
			  <p class="side_menu_heading_color"><s:property value="#session.scorerInfo.roleDescription"/><br><s:property value="#session.scorerInfo.scorerId"/></p>
			  <p class="side_menu_alignment"><s:text name="label.menu"></s:text>
			   <div class="green_menu"><!-- 3rd td --> 
				<a href="<s:url action="showSaitenMenu" includeParams="none" />" class="menu_button"><s:text name="label.backtosaitenmenu" /></a><p>
				<a href="#" class="menu_button_without_cursor"><s:text name="label.historylist"></s:text></a><p>
				<s:if test="(#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID) && (forcedScoringFlag == true)">	
					<a href="bookmarkListing.action?forcedScoringFlag=true" class="menu_button"><s:text name="label.bookmarklist" /></a><p>
				</s:if><s:else>
					<a href="<s:url action="bookmarkListing" includeParams="none" />" class="menu_button"><s:text name="label.bookmarklist" /></a><p>
				</s:else>
				
				<a href="<s:url action="logOut" includeParams="none" />" class="menu_button"><s:text name="label.logout" /></a><p>
		       </div>
		       <s:if test="#session.saitenLoginEnabled != true">
		         	<br>
					<div class="green_menu">
						<input type="button" name="submit" value="<s:text name="label.backToSaitenLMS" />" class="menu_button" onclick="javascript:location.href='backToSaitenLms.action';" />
					</div>
		       </s:if>
			 <p>
			 </div>
			 <br/>
	  </div>  
	</td>
   
  <td class="table-center-menu-td" width="90%">
  	<div class="box">
		<h1 align="left"><s:text name="label.historylist"></s:text></h1>
	</div>
	<div style="width: auto;border: none;">
		<s:if test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID">
			<c:set var="paginationUrl" value="/omrEnlargeHistoryPagination.action"></c:set>
		</s:if><s:else>
			<c:set var="paginationUrl" value="/historyPagination.action"></c:set>
		</s:else>
		<s:set var="speakingType" value="@com.saiten.util.WebAppConst@SPEAKING_TYPE"></s:set>
		<s:set var="writingType" value="@com.saiten.util.WebAppConst@WRITING_TYPE"></s:set>
		<s:set var="menuId" value="#session.questionInfo.menuId"></s:set>
		<s:set var="forcedMenuId" value="@com.saiten.util.WebAppConst@FORCED_MENU_ID"></s:set>
		<s:set var="firstScoringMenuId" value="@com.saiten.util.WebAppConst@FIRST_SCORING_MENU_ID"></s:set>
		<s:set var="secondScoringMenuId" value="@com.saiten.util.WebAppConst@SECOND_SCORING_MENU_ID"></s:set>
		<display:table id="historyInfoResult" sort="external" defaultsort="1" requestURI="${paginationUrl}"
	partialList="true" size="${sessionScope.recordCount}" name="historyInfoList" pagesize="${sessionScope.pagesize}" export="true" class="displayTable">
			<c:set var="pageSize"><fmt:message key="history.pagesize" bundle="${global}"></fmt:message></c:set>
			<c:choose>
				<c:when test="${sessionScope.a > 1}">
				<c:set var="rowNum" value="${historyInfoResult_rowNum+((sessionScope.a-1)*pageSize)}"></c:set>
			</c:when><c:otherwise>
				<c:set var="rowNum" value="${historyInfoResult_rowNum}"></c:set>
			</c:otherwise>
			</c:choose>
			<c:set var="historySequence" value="${historyInfoResult.historySequence}"></c:set>
			<c:set var="qcSeq" value="${historyInfoResult.qcSeq}"></c:set>
			<c:set var="isQualityRecord" value="${historyInfoResult.isQualityRecord}"></c:set>
			<c:set var="connectionString" value="${historyInfoResult.connectionString}"></c:set>
			<c:set var="questionSequence" value="${historyInfoResult.questionSequence}"></c:set>
			<c:choose>
			    <c:when test="${(menuId eq firstScoringMenuId || menuId eq secondScoringMenuId) }"> <!-- && (sessionScope.questionInfo.questionType eq speakingType || sessionScope.questionInfo.questionType eq writingType) -->
						<c:if test="${isQualityRecord == 0}">	
							<c:url var="historyScore" value="historyScoreAction.action">
								<c:param name="historySequence" value="${historySequence}"></c:param>
								<c:param name="rowNum" value="${historyInfoResult_rowNum}"></c:param>
								<c:param name="connectionString" value="${connectionString}"></c:param>
								<c:param name="questionSequence" value="${questionSequence}"></c:param>
							</c:url>
						</c:if>
						<c:if test="${isQualityRecord == 1}">
							<c:url var="historyScore" value="historyScoreAction.action">
								<c:param name="qcSeq" value="${qcSeq}"></c:param>
								<c:param name="rowNum" value="${historyInfoResult_rowNum}"></c:param>
								<c:param name="connectionString" value="${connectionString}"></c:param>
								<c:param name="questionSequence" value="${questionSequence}"></c:param>
							</c:url>
						</c:if>
				</c:when><c:otherwise>
							<c:url var="historyScore" value="historyScoreAction.action">
								<c:param name="historySequence" value="${historySequence}"></c:param>
								<c:param name="rowNum" value="${historyInfoResult_rowNum}"></c:param>
								<c:param name="connectionString" value="${connectionString}"></c:param>
								<c:param name="questionSequence" value="${questionSequence}"></c:param>
							</c:url>
				</c:otherwise>		
			</c:choose>	
			<display:column titleKey="label.header.srNo" class="displayColumnNum">
				<a href="<c:out value="${historyScore}"/>"><c:out value="${rowNum}"/></a>
			</display:column>
			<c:set var="bookmarkFlag" value="${historyInfoResult.bookmarkFlag}"></c:set>
			<c:choose>
				<c:when test="${fn:contains(bookmarkFlag, 'T')}">
					<display:column headerClass="bookmarkClass" title="" class="displayColumnChk" >
   						<a href="<c:out value="${historyScore}"/>"><img src="<c:url  value="./material/img/bookmark.png" />" /></a>
   					</display:column>
				</c:when><c:otherwise>
					<display:column headerClass="bookmarkClass" title="" class="displayColumnChk">
						<a href="<c:out value="${historyScore}"/>">&nbsp;</a>
					</display:column>
				</c:otherwise>
			</c:choose>
			<c:set var="subjectName" value="${historyInfoResult.subjectName}"></c:set>
          	<c:choose>
				<c:when test="${empty subjectName}">
					<display:column titleKey="label.header.subject" class="displayColumnChkSubject">
						<a href="<c:out value="${historyScore}"/>">&nbsp;</a>
					</display:column>
				</c:when><c:otherwise>
					<display:column titleKey="label.header.subject" class="displayColumnChkSubject">
						<a href="<c:out value="${historyScore}"/>"><c:out value="${subjectName}"></c:out></a>
					</display:column>
				</c:otherwise>
			</c:choose>
			<c:set var="questionNumber" value="${historyInfoResult.questionNumber}"></c:set>
          	<c:choose>
				<c:when test="${empty questionNumber}">
					<display:column titleKey="label.header.question" class="displayColumnChkSubjectQNum">
						<a href="<c:out value="${historyScore}"/>">&nbsp;</a>
					</display:column>
				</c:when><c:otherwise>
					<display:column titleKey="label.header.question" class="displayColumnChkSubjectQNum">
						<a href="<c:out value="${historyScore}"/>"><c:out value="${questionNumber}"/></a>
					</display:column>
				</c:otherwise>
			</c:choose>
			<c:set var="answerNumber" value="${historyInfoResult.answerNumber}"></c:set>
          	<c:choose>
				<c:when test="${empty answerNumber}">
					<display:column titleKey="label.header.answerNumber" class="displayColumnAnsNum">
						<a href="<c:out value="${historyScore}"/>">&nbsp;</a>
					</display:column>
				</c:when><c:otherwise>
					<display:column titleKey="label.header.answerNumber"  class="displayColumnAnsNum">
						<a href="<c:out value="${historyScore}"/>"><c:out value="${answerNumber}"/></a>
					</display:column>
				</c:otherwise>
			</c:choose> 
			<c:set var="scoringStateName" value="${historyInfoResult.scoringStateName}"></c:set>
          	<c:choose>
				<c:when test="${empty scoringStateName}">
					<display:column titleKey="label.header.status" class="displayColumnStatus">
						<a href="<c:out value="${historyScore}"/>">&nbsp;</a>
					</display:column>
				</c:when><c:otherwise>
					<display:column titleKey="label.header.status" class="displayColumnStatus">
						<a href="<c:out value="${historyScore}"/>"><c:out value="${scoringStateName}"/></a>
					</display:column>
				</c:otherwise>
			</c:choose> 
			<c:set var="updateDate" value="${historyInfoResult.updateDate}"></c:set>
          	<c:choose>
				<c:when test="${empty updateDate}">
					<display:column titleKey="label.header.date" class="displayColumnDate">
						<a href="<c:out value="${historyScore}"/>">&nbsp;</a>
					</display:column>
				</c:when><c:otherwise>
					<display:column titleKey="label.header.date" class="displayColumnDate">
						<a href="<c:out value="${historyScore}"/>"><fmt:formatDate value="${updateDate}" pattern="yyyy/MM/dd HH:mm"/></a>
					</display:column>
				</c:otherwise>
			</c:choose> 
			<c:set var="gradeNum" value="${historyInfoResult.gradeNum}"></c:set>
			<c:set var="result" value="${historyInfoResult.result}"></c:set>
			<c:set var="pendingCategory" value="${historyInfoResult.pendingCategory}"></c:set>
          	<c:choose>
          	<c:when test="${sessionScope.questionInfo.questionType ne speakingType && sessionScope.questionInfo.questionType ne writingType}">
          	<c:choose>
				<c:when test="${empty gradeNum && not empty pendingCategory}">
					<display:column titleKey="label.header.grade" class="displayColumnType">
						<a href="<c:out value="${historyScore}"/>">&nbsp;</a>
					</display:column>
				</c:when><c:when test="${empty gradeNum && empty pendingCategory}">
					<display:column titleKey="label.header.grade" class="displayColumnType">
						<a href="<c:out value="${historyScore}"/>"><fmt:message key="label.hyphen" bundle="${global}"></fmt:message></a>
					</display:column>
				</c:when><c:otherwise>
					<display:column titleKey="label.header.grade" class="displayColumnType">
						<a href="<c:out value="${historyScore}"/>"><c:out value="${gradeNum}"/></a>
					</display:column>
				</c:otherwise>
			</c:choose>
			<c:choose>
			<c:when test="${fn:contains(result, 'F') || fn:contains(result, 'D') || fn:contains(result, 'S') || empty pendingCategory}">
				<c:if test="${empty pendingCategory && empty result}">
					<display:column titleKey="label.header.result"  class="displayColumnVarid">
   					<a href="<c:out value="${historyScore}"/>"><fmt:message key="label.hyphen" bundle="${global}"></fmt:message></a>
   					</display:column>
				</c:if>
				<c:if test="${fn:contains(result, 'F')}">
					<display:column titleKey="label.header.result"  class="displayColumnVarid">
   					<a href="<c:out value="${historyScore}"/>"><fmt:message key="label.result.fail" bundle="${global}"></fmt:message></a>
   					</display:column>
				</c:if>
   				<c:if test="${fn:contains(result, 'D')}">
					<display:column titleKey="label.header.result"  class="displayColumnVarid">
   					<a href="<c:out value="${historyScore}"/>"><fmt:message key="label.result.dubleCircle" bundle="${global}"></fmt:message></a>
   					</display:column>
				</c:if>
				<c:if test="${fn:contains(result, 'S')}">
					<display:column titleKey="label.header.result"  class="displayColumnVarid">
   					<a href="<c:out value="${historyScore}"/>"><fmt:message key="label.result.SingleCircle" bundle="${global}"></fmt:message></a>
   					</display:column>
				</c:if>
			</c:when><c:otherwise>
				<display:column titleKey="label.header.result" class="displayColumnVarid">
					<a href="<c:out value="${historyScore}"/>">&nbsp;</a>
				</display:column>			
			</c:otherwise>
			</c:choose>
			</c:when>
			</c:choose>
			
			<c:choose>
				<c:when test="${menuId eq forcedMenuId }">　<!-- && (sessionScope.questionInfo.questionType eq speakingType || sessionScope.questionInfo.questionType eq writingType) -->
						<c:if test="${isQualityRecord == 1}">
							<display:column titleKey="label.forcedscoring.isqualitymark"  class="displayColumnVarid">
   							<a href="<c:out value="${historyScore}"/>"><fmt:message key="label.forcedscoring.qualityrecord" bundle="${global}"></fmt:message></a>
   							</display:column>
						</c:if>
						<c:if test="${isQualityRecord == 0}">
\							<display:column titleKey="label.forcedscoring.isqualitymark"  class="displayColumnVarid">
   							<a href="<c:out value="${historyScore}"/>"><fmt:message key="label.forcedscoring.notqualityrecord" bundle="${global}"></fmt:message></a>
   							</display:column>
						</c:if>
				</c:when>
			</c:choose>
			
          	<c:choose>
				<c:when test="${empty pendingCategory}">
					<display:column titleKey="label.header.pendingCategory" class="displayColumnPendingCategory">
						<a href="<c:out value="${historyScore}"/>">&nbsp;</a>
					</display:column>
				</c:when><c:otherwise>
					<display:column titleKey="label.header.pendingCategory" class="displayColumnPendingCategory">
						<a href="<c:out value="${historyScore}"/>"><c:out value="${pendingCategory}"/></a>
					</display:column>
				</c:otherwise>
			</c:choose>
			<c:set var="comment" value="${historyInfoResult.comment}"></c:set>
          	<c:choose>
				<c:when test="${empty comment}">
					<display:column titleKey="label.header.comment" class="displayColumnComment">
						<a href="<c:out value="${historyScore}"/>">&nbsp;</a>
					</display:column>
				</c:when><c:otherwise>
					<display:column titleKey="label.header.comment" class="displayColumnComment">
						<a href="<c:out value="${historyScore}"/>"><pre style="margin-left: 5px;margin-right: 5px;"><c:out value="${comment}"/></pre></a>
					</display:column>
				</c:otherwise>
			</c:choose>
			<display:setProperty value="false" name="basic.empty.showtable"  />
            <display:setProperty name="basic.msg.empty_list" ><s:text name="display.search.display.tag.basic.msg.empty_list"></s:text> </display:setProperty> 
            <display:setProperty name="paging.banner.one_item_found" ><s:text name="display.search.display.tag.paging.banner.one_items_found" /></display:setProperty>
            <display:setProperty name="paging.banner.all_items_found" ><s:text name="display.search.display.tag.paging.banner.all_items_found" /></display:setProperty>
            <display:setProperty name="paging.banner.some_items_found" ><s:text name="display.search.display.tag.paging.banner.some_items_found" /></display:setProperty>  
            <display:setProperty name="paging.banner.full" ><s:text name="display.search.display.tag.paging.banner.full" /></display:setProperty>
            <display:setProperty name="paging.banner.first" ><s:text name="display.search.display.tag.paging.banner.first" /></display:setProperty>
            <display:setProperty name="paging.banner.last" ><s:text name="display.search.display.tag.paging.banner.last" /></display:setProperty>  
            <display:setProperty name="paging.banner.item_name" ><s:text name="display.search.display.tag.paging.banner.item_name" /></display:setProperty>  
            <display:setProperty name="paging.banner.items_name" ><s:text name="display.search.display.tag.paging.banner.items_name" /></display:setProperty>
	</display:table>
	</div>
	<div align="center" style="padding-top: 20px; padding-bottom: 20px;">
		<s:if test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@SCORE_SAMP_MENU_ID">
			<a href="resetScoreHistory.action?backToScoringFlag=<s:property value="%{@com.saiten.util.WebAppConst@TRUE}" />"  id="resetHistory" class="btn btn-primary btn-xl">
				<%-- <img src="./material/img/button/bklist1_bn-login.gif" alt="<s:text name="label.button.resetScoreHistory"></s:text>" 
				class="rollover" /> --%>
				<s:text name="label.button.resetScoreHistory"></s:text>
			</a>
		</s:if><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID">
			<s:if test="(#session.tranDescScoreInfo != null && forcedScoringFlag != true) || (#session.tranDescScoreInfo != null && #session.tranDescScoreInfoCopy != null && forcedScoringFlag == true)">
				<a href="resetScoreHistory.action" id="resetHistory" class="btn btn-primary btn-xl">
					<%-- <img src="./material/img/button/bklist1_bn-login.gif" alt="<s:text name="label.button.resetScoreHistory"></s:text>" 
					class="rollover" /> --%>
					<s:text name="label.button.resetScoreHistory"></s:text>
				</a>
			</s:if>
			<a href="scoreSearch.action?selectedMenuId=FORCED_MENU_ID" id="back" name="back" class="btn btn-primary btn-xl">
				<!-- <img class="rollover" src="./material/img/button/sampling_bn-back.gif" alt="%{getText('label.backtosearch')}" /> -->
				<s:text name="label.backtosearch"></s:text>
			</a>
			<s:if test="#session.searchResultCount != @com.saiten.util.WebAppConst@ZERO">
				<a href="backToForcedScoringList.action" id="back" name="back" class="btn btn-primary btn-xl">
					<!-- <img class="rollover" src="./material/img/button/search1_bn-login.gif" alt="%{getText('label.backtosearch')}" /> -->
					<s:text name="label.alt.backtoList"></s:text>
				</a>
			</s:if>
			
		</s:elseif><s:else>
			<a href="resetScoreHistory.action" id="resetHistory" class="btn btn-primary btn-xl">
					<%-- <img src="./material/img/button/bklist1_bn-login.gif" alt="<s:text name="label.button.resetScoreHistory"></s:text>" 
					class="rollover" /> --%>
					<s:text name="label.button.resetScoreHistory"></s:text>
				</a>
		</s:else>
		
	</div>
</td>
 </tr>
</table>				
	</tiles:putAttribute>
	
</tiles:insertDefinition>