<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle var="global" basename="global" scope="session" /> 

<tiles:insertDefinition name="baseLayout">
	<tiles:putAttribute name="head-title">
		<s:if test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID">
			<title><s:text name="label.forcedscoringlist.title"/></title>
		</s:if><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@STATE_TRAN_MENU_ID">
			<title><s:text name="label.statetransition.searchresult.title"/></title>
		</s:elseif>
	</tiles:putAttribute>
	<tiles:putAttribute name="head-addition">
		<script type="text/javascript" src="./material/scripts/jQuery/jquery.tools.min.js"></script>
		<script type="text/javascript"  src="./material/scripts/js/searchList.js" ></script>
		<script type="text/javascript">
			$(window).load(function () {
				var selectAllFlag = '<s:property value="%{#session.selectAllFlag}" />';
					
				if(selectAllFlag == 'true'){
					$("#selectall").trigger('click');
					$("#pageselectall").trigger('click');
					$('#selectAllFlag').val('true');
				}
			});
		</script>
		<script type="text/javascript">
		 $(function() {
			 	 $("a[title]").tooltip({position: "bottom left"});
		}); 
		 
		</script>
		<style>
			@import url("./material/css/displaytag.css");
			@import url("./material/css/screen.css");
		</style>
		<style type="text/css">
 			label { width: auto; float: none; color: red;  display: block;   }
		</style>
	</tiles:putAttribute>
	<tiles:putAttribute name="body-title">
		<!-- This portion will contain perticular page body title. -->
	</tiles:putAttribute>
	<tiles:putAttribute name="body-main">
		<!-- This portion will contain main body of perticular page -->
		<s:form action="update" name="update" id="update">
		<table class="table_width_height">
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
								onclick="javascript:location.href='userMenu.action';"><p>
							<s:if  test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID">
								<a href="<s:url action="historyListing" includeParams="none" />" class="menu_button"><s:text name="label.historylist" /></a><p>
								<a href="<s:url action="bookmarkListing" includeParams="none" />" class="menu_button"><s:text name="label.bookmarklist" /></a><p>
							</s:if>
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
		  
		  <td class="table-center-menu-td" width="90%">
	
<div id="list_pane">
<div class="box">
<h1 align="left"><s:text name="label.searchResultList"></s:text></h1>

<s:set var="pageNo" value="%{#parameters['d-7500665-p'][0]}"></s:set>
<c:set var="pageSize"><fmt:message key="forcedAndStateTransition.listpage.pagesize" bundle="${global}"></fmt:message></c:set>
<s:set var="menuId" value="#session.questionInfo.menuId"></s:set>
<s:set var="forcedMenuId" value="@com.saiten.util.WebAppConst@FORCED_MENU_ID"></s:set>
<s:set var="notScoringStates" value="@com.saiten.util.WebAppConst@FORCED_SCORING_NOT_SCORED_STATES"></s:set>
<s:set var="questionType" value="#session.questionInfo.questionType"></s:set>
<s:set var="speakingType" value="@com.saiten.util.WebAppConst@SPEAKING_TYPE"></s:set>
<s:set var="writingType" value="@com.saiten.util.WebAppConst@WRITING_TYPE"></s:set>			
<c:choose>
	<c:when test="${menuId ne forcedMenuId}">
	<div align="left" class="selectCheckBox"><s:hidden name="selectCheckBoxToUpdate" id="selectCheckBoxToUpdate"></s:hidden></div>
		<div style="width: auto;border: none;" id="c_b">
		<s:if test="%{#parameters['d-7500665-p'][0]} != ''">
			<s:set var="pageNo" value="%{#parameters['d-7500665-p'][0]}"></s:set>
		</s:if>
		<s:else>
			<s:set var="pageNo" value="@com.saiten.util.WebAppConst@ONE"></s:set>
		</s:else>
		<s:hidden id="selectAllFlag" name="selectAllFlag"></s:hidden>
		<div>
			<table>
				<tr>
					<td>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="selectall" name="selectall" class="" value="" onchange="setSelectFlag();" align="left"/> : <s:text name="label.statetransition.updateAllSearchedRecords"></s:text>
					</td>
					<td></td>
				</tr>
			</table>
		</div>
	
         <display:table id="scoreSamplingInfo" sort="external" defaultsort="1" requestURI="/forcedScoringPagination.action"
			partialList="true" size="${sessionScope.recordCount}" name="scoreSamplingInfoList" pagesize="${sessionScope.pagesize}" export="true" class="displayTableTest">       
			<display:column style="width:5%;" title='<input type="checkbox" id="pageselectall" name="pageselectall" /> ' media="html" class="displayColumnChk">
				<input type="checkbox" id="answerSeq" name="answerSeq" class="case" value="${scoreSamplingInfo.answerSeq}" />
			</display:column>
			<c:choose>
				<c:when test="${sessionScope.pageNumber > 1}">
				<c:set var="rowNum" value="${scoreSamplingInfo_rowNum+((sessionScope.pageNumber-1)*pageSize)}"></c:set>
			</c:when><c:otherwise>
				<c:set var="rowNum" value="${scoreSamplingInfo_rowNum}"></c:set>
			</c:otherwise>
			</c:choose>
			<display:column style="width:3%;" titleKey="label.header.srNo" class="displayColumnNum">
				<a href="<c:out value="${forcedScore}"/>"><c:out value="${rowNum}"/></a>
			</display:column>
			<display:column style="width:8%;" property="subjectName"  class="displayColumnChkSubject" titleKey="label.header.subject"/>
			<display:column style="width:3%;"  property="questionNumber"  class="displayColumnChkSubjectQNum" titleKey="label.header.question" />
			<display:column style="width:8%;" property="answerNumber"  class="displayColumnAnsNum" titleKey="label.header.answerNumber" />
			<display:column style="width:12%;" property="scoringStateName"  class="displayColumnStatus" titleKey="label.header.status" />
			<display:column style="width:12%;" property="updateDate"  class="displayColumnDate" format="{0,date,yyyy/MM/dd HH:mm}" titleKey="label.header.date" />
			<display:column style="width:8%;" property="latestScreenScorerId"  class="displayColumnAnsNum" titleKey="label.header.scorerId"/>
			<display:column style="width:5%;" property="gradeNum" class="displayColumnType" titleKey="label.header.grade" />
			<c:set var="gradeNum" value="${scoreSamplingInfo.gradeNum}"></c:set>
			<c:set var="result" value="${scoreSamplingInfo.result}"></c:set>
			<c:set var="pendingCategory" value="${scoreSamplingInfo.pendingCategory}"></c:set>
			<c:choose>
			<c:when test="${sessionScope.questionInfo.questionType ne speakingType && sessionScope.questionInfo.questionType ne writingType}">
			<c:choose>
			<c:when test="${fn:contains(result, 'F') || fn:contains(result, 'D') || fn:contains(result, 'S') || empty pendingCategory}">
				<c:if test="${fn:contains(notScoringStates, scoreSamplingInfo.scoringState)}">
					
					<display:column style="width:5%;" value=""  class="displayColumnVarid" titleKey="label.header.result" />
				</c:if>
				<c:if test="${(empty pendingCategory && empty result) and (fn:contains(notScoringStates, scoreSamplingInfo.scoringState) != true)}">
					<display:column style="width:5%;" value="-"  class="displayColumnVarid" titleKey="label.header.result" />
				</c:if>
				<c:if test="${fn:contains(result, 'F')}">
				<s:set var="resultF" value="%{getText('label.result.fail')}"></s:set>
					<display:column style="width:5%;" value="${resultF}"  class="displayColumnVarid" titleKey="label.header.result" />
				</c:if>
   				<c:if test="${fn:contains(result, 'D')}">
   				<s:set var="resultD" value="%{getText('label.result.dubleCircle')}"></s:set>
					<display:column style="width:5%;" value="${resultD}"  class="displayColumnVarid" titleKey="label.header.result" />
				</c:if>
				<c:if test="${fn:contains(result, 'S')}">
				<s:set var="resultS" value="%{getText('label.result.SingleCircle')}"></s:set>
					<display:column style="width:5%;" value="${resultS}"  class="displayColumnVarid" titleKey="label.header.result" />
				</c:if>
			</c:when>
			<c:otherwise>
				<display:column style="width:5%;" value=""  class="displayColumnVarid" titleKey="label.header.result" />		
			</c:otherwise>
			</c:choose>
			</c:when>
			</c:choose>			
			
			<display:column style="width:5%;" property="checkPoints"  class="displayColumnType" titleKey="label.header.checkpoints" />
			<display:setProperty value="false" name="basic.empty.showtable"  />
            <display:setProperty name="basic.msg.empty_list" ><span class="infoMsgColor"><s:text name="display.search.display.tag.basic.msg.empty_list"></s:text></span></display:setProperty> 
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
	</c:when>
	<c:when test="${menuId eq forcedMenuId}">
		<display:table id="scoreSamplingInfo" sort="external" defaultsort="1" requestURI="/forcedScoringPagination.action"
			partialList="true" size="${sessionScope.recordCount}" name="scoreSamplingInfoList" pagesize="${sessionScope.pagesize}" export="true" class="displayTable">
			<c:set var="answerSequence" value="${scoreSamplingInfo.answerSeq}"></c:set>
			<c:url var="forcedScore" value="forcedScoring.action">
				<c:param name="answerSequence" value="${answerSequence}"></c:param>
			</c:url>
			<c:choose>
				<c:when test="${sessionScope.pageNumber > 1}">
				<c:set var="rowNum" value="${scoreSamplingInfo_rowNum+((sessionScope.pageNumber-1)*pageSize)}"></c:set>
			</c:when><c:otherwise>
				<c:set var="rowNum" value="${scoreSamplingInfo_rowNum}"></c:set>
			</c:otherwise>
			</c:choose>
			<display:column titleKey="label.header.srNo" class="displayColumnNum">
				<a href="<c:out value="${forcedScore}"/>"><c:out value="${rowNum}"/></a>
			</display:column>
			<c:set var="subjectName" value="${scoreSamplingInfo.subjectName}"></c:set>
          	<c:choose>
				<c:when test="${empty subjectName}">
					<display:column titleKey="label.header.subject" class="displayColumnChkSubject">
						<a href="<c:out value="${forcedScore}"/>">&nbsp;</a>
					</display:column>
				</c:when><c:otherwise>
					<display:column titleKey="label.header.subject" class="displayColumnChkSubject">
						<a href="<c:out value="${forcedScore}"/>"><c:out value="${subjectName}"></c:out></a>
					</display:column>
				</c:otherwise>
			</c:choose>
			<c:set var="questionNumber" value="${scoreSamplingInfo.questionNumber}"></c:set>
          	<c:choose>
				<c:when test="${empty questionNumber}">
					<display:column titleKey="label.header.question" class="displayColumnChkSubjectQNum">
						<a href="<c:out value="${forcedScore}"/>">&nbsp;</a>
					</display:column>
				</c:when><c:otherwise>
					<display:column titleKey="label.header.question" class="displayColumnChkSubjectQNum">
						<a href="<c:out value="${forcedScore}"/>"><c:out value="${questionNumber}"/></a>
					</display:column>
				</c:otherwise>
			</c:choose>
			<c:set var="answerNumber" value="${scoreSamplingInfo.answerNumber}"></c:set>
          	<c:choose>
				<c:when test="${empty answerNumber}">
					<display:column titleKey="label.header.answerNumber" class="displayColumnAnsNum">
						<a href="<c:out value="${forcedScore}"/>">&nbsp;</a>
					</display:column>
				</c:when><c:otherwise>
					<display:column titleKey="label.header.answerNumber"  class="displayColumnAnsNum">
						<a href="<c:out value="${forcedScore}"/>"><c:out value="${answerNumber}"/></a>
					</display:column>
				</c:otherwise>
			</c:choose> 
			<c:set var="scoringStateName" value="${scoreSamplingInfo.scoringStateName}"></c:set>
          	<c:choose>
				<c:when test="${empty scoringStateName}">
					<display:column titleKey="label.header.status" class="displayColumnStatus">
						<a href="<c:out value="${forcedScore}"/>">&nbsp;</a>
					</display:column>
				</c:when><c:otherwise>
					<display:column titleKey="label.header.status" class="displayColumnStatus">
						<a href="<c:out value="${forcedScore}"/>"><c:out value="${scoringStateName}"/></a>
					</display:column>
				</c:otherwise>
			</c:choose> 
			<c:set var="updateDate" value="${scoreSamplingInfo.updateDate}"></c:set>
          	<c:choose>
				<c:when test="${empty updateDate}">
					<display:column titleKey="label.header.date" class="displayColumnDate">
						<a href="<c:out value="${forcedScore}"/>">&nbsp;</a>
					</display:column>
				</c:when><c:otherwise>
					<display:column titleKey="label.header.date" class="displayColumnDate">
						<a href="<c:out value="${forcedScore}"/>"><fmt:formatDate value="${updateDate}" pattern="yyyy/MM/dd HH:mm"/></a>
					</display:column>
				</c:otherwise>
			</c:choose>
			<c:set var="latestScreenScorerId" value="${scoreSamplingInfo.latestScreenScorerId }"></c:set>
			<c:choose>
				<c:when test="${empty latestScreenScorerId}">
					<display:column titleKey="label.header.scorerId" class="displayColumnAnsNum">
						<a href="<c:out value="${forcedScore}"/>">&nbsp;</a>
					</display:column>
				</c:when><c:otherwise>
					<display:column titleKey="label.header.scorerId" class="displayColumnAnsNum">
						<a href="<c:out value="${forcedScore}"/>"><c:out value="${latestScreenScorerId}"/></a>
					</display:column>
				</c:otherwise>
			</c:choose>
			<c:set var="gradeNum" value="${scoreSamplingInfo.gradeNum}"></c:set>
			<c:set var="result" value="${scoreSamplingInfo.result}"></c:set>
			<c:set var="pendingCategory" value="${scoreSamplingInfo.pendingCategory}"></c:set>
			<c:set var="isQualityRecord" value="${scoreSamplingInfo.answerInfo.qualityCheckFlag}"></c:set>
			<c:out value="${fn:contains(notScoringStates, scoreSamplingInfo.scoringState)}"></c:out>
			<c:choose>
			<c:when test="${sessionScope.questionInfo.questionType ne speakingType && sessionScope.questionInfo.questionType ne writingType}">
          	<c:choose>
				<c:when test="${(empty gradeNum && not empty pendingCategory) or (fn:contains(notScoringStates, scoreSamplingInfo.scoringState))}">
					<display:column titleKey="label.header.grade" class="displayColumnType">
						<a href="<c:out value="${forcedScore}"/>">&nbsp;</a>
					</display:column>
				</c:when><c:when test="${empty gradeNum && empty pendingCategory}">
					<display:column titleKey="label.header.grade" class="displayColumnType">
						<a href="<c:out value="${forcedScore}"/>"><fmt:message key="label.hyphen" bundle="${global}"></fmt:message></a>
					</display:column>
				</c:when><c:otherwise>
					<display:column titleKey="label.header.grade" class="displayColumnType">
						<a href="<c:out value="${forcedScore}"/>"><c:out value="${gradeNum}"/></a>
					</display:column>
				</c:otherwise>
			</c:choose>
			<c:choose>
			<c:when test="${fn:contains(result, 'F') || fn:contains(result, 'D') || fn:contains(result, 'S') || empty pendingCategory}">
				<c:if test="${fn:contains(notScoringStates, scoreSamplingInfo.scoringState)}">
					<display:column titleKey="label.header.result" class="displayColumnVarid">
						<a href="<c:out value="${forcedScore}"/>">&nbsp;</a>
					</display:column>
				</c:if>
				<c:if test="${(empty pendingCategory && empty result) and (fn:contains(notScoringStates, scoreSamplingInfo.scoringState) != true)}">
					<display:column titleKey="label.header.result"  class="displayColumnVarid">
   						<a href="<c:out value="${forcedScore}"/>"><fmt:message key="label.hyphen" bundle="${global}"></fmt:message></a>
   					</display:column>
				</c:if>
				<c:if test="${fn:contains(result, 'F')}">
					<display:column titleKey="label.header.result"  class="displayColumnVarid">
   					<a href="<c:out value="${forcedScore}"/>"><fmt:message key="label.result.fail" bundle="${global}"></fmt:message></a>
   					</display:column>
				</c:if>
   				<c:if test="${fn:contains(result, 'D')}">
					<display:column titleKey="label.header.result"  class="displayColumnVarid">
   					<a href="<c:out value="${forcedScore}"/>"><fmt:message key="label.result.dubleCircle" bundle="${global}"></fmt:message></a>
   					</display:column>
				</c:if>
				<c:if test="${fn:contains(result, 'S')}">
					<display:column titleKey="label.header.result"  class="displayColumnVarid">
   					<a href="<c:out value="${forcedScore}"/>"><fmt:message key="label.result.SingleCircle" bundle="${global}"></fmt:message></a>
   					</display:column>
				</c:if>
			</c:when>
			<c:otherwise>
				<display:column titleKey="label.header.result" class="displayColumnVarid">
					<a href="<c:out value="${forcedScore}"/>">&nbsp;</a>
				</display:column>			
			</c:otherwise>
			</c:choose>
			</c:when>
			</c:choose>
			<c:choose>
				<%-- <c:when test="${sessionScope.questionInfo.questionType eq speakingType || sessionScope.questionInfo.questionType eq writingType}">
				 --%>	
						<c:when test="${fn:contains(isQualityRecord, 'T')}">
							<display:column titleKey="label.forcedscoring.isqualitymark"  class="displayColumnVarid">
   							<a href="<c:out value="${forcedScore}"/>"><fmt:message key="label.forcedscoring.qualityrecord" bundle="${global}"></fmt:message></a>
   							</display:column>
						</c:when>
						<c:when test="${fn:contains(isQualityRecord, 'F')}">
							<display:column titleKey="label.forcedscoring.isqualitymark"  class="displayColumnVarid">
   							<a href="<c:out value="${forcedScore}"/>"><fmt:message key="label.forcedscoring.notqualityrecord" bundle="${global}"></fmt:message></a>
   							</display:column>
						</c:when>
				<%-- </c:when> --%>
			</c:choose>	
			
			<c:set var="checkPoints" value="${scoreSamplingInfo.checkPoints}"></c:set>
			<c:choose>
				<c:when test="${empty checkPoints}">
					<display:column titleKey="label.header.checkpoints" class="displayColumnCheckPoints">
						<a href="<c:out value="${forcedScore}"/>">&nbsp;</a>
					</display:column>
				</c:when><c:otherwise>
					<display:column titleKey="label.header.checkpoints" class="displayColumnCheckPoints">
						<a href="<c:out value="${forcedScore}"/>"><pre style="margin-left: 5px;margin-right: 5px;"><c:out value="${checkPoints}"/></pre></a>
					</display:column>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${empty pendingCategory}">
					<display:column titleKey="label.header.pendingCategory" class="displayColumnPendingCategory">
						<a href="<c:out value="${forcedScore}"/>">&nbsp;</a>
					</display:column>
				</c:when><c:otherwise>
					<display:column titleKey="label.header.pendingCategory" class="displayColumnPendingCategory">
						<a href="<c:out value="${forcedScore}"/>"><c:out value="${pendingCategory}"/></a>
					</display:column>
				</c:otherwise>
			</c:choose>
			<c:set var="commentCount" value="${scoreSamplingInfo.commentCount}"></c:set>
			<c:choose>
			<c:when test="${empty commentCount}">
				<display:column titleKey="label.header.comment" class="displayColumnCommentCount">
					<a href="<c:out value="${forcedScore}"/>">&nbsp;</a>
				</display:column>
			</c:when><c:otherwise>
			<c:choose>
				<c:when test="${commentCount eq 0}">
					<display:column titleKey="label.header.comment" class="displayColumnCommentCount">
						<pre style="margin-left: 5px;margin-right: 5px;vertical-align: middle;"><c:out value="${commentCount}"/></pre>
					</display:column>
				</c:when><c:otherwise>
					<display:column titleKey="label.header.comment" class="displayColumnCommentCount">
						<a title="${scoreSamplingInfo.comments}" ><pre style="margin-left: 5px;margin-right: 5px;vertical-align: middle;"><c:out value="${commentCount}"/>&nbsp;<img src="<c:url  value='./material/img/comment.png' />" /></pre></a>
					</display:column>
				</c:otherwise>
			</c:choose>	
			</c:otherwise>
			</c:choose>
			<display:setProperty value="false" name="basic.empty.showtable"  />
            <display:setProperty name="basic.msg.empty_list" ><span class="infoMsgColor"><s:text name="display.search.display.tag.basic.msg.empty_list"></s:text></span></display:setProperty> 
            <display:setProperty name="paging.banner.one_item_found" ><s:text name="display.search.display.tag.paging.banner.one_items_found" /></display:setProperty>
            <display:setProperty name="paging.banner.all_items_found" ><s:text name="display.search.display.tag.paging.banner.all_items_found" /></display:setProperty>
            <display:setProperty name="paging.banner.some_items_found" ><s:text name="display.search.display.tag.paging.banner.some_items_found" /></display:setProperty>  
            <display:setProperty name="paging.banner.full" ><s:text name="display.search.display.tag.paging.banner.full" /></display:setProperty>
            <display:setProperty name="paging.banner.first" ><s:text name="display.search.display.tag.paging.banner.first" /></display:setProperty>
            <display:setProperty name="paging.banner.last" ><s:text name="display.search.display.tag.paging.banner.last" /></display:setProperty>  
            <display:setProperty name="paging.banner.item_name" ><s:text name="display.search.display.tag.paging.banner.item_name" /></display:setProperty>  
            <display:setProperty name="paging.banner.items_name" ><s:text name="display.search.display.tag.paging.banner.items_name" /></display:setProperty>
		</display:table>
	</c:when>
</c:choose>
	

 <table style="width: 90%;padding-top: 10px;">
		 	<tr>	
		 		<s:if test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@STATE_TRAN_MENU_ID">
		 			<td align="right">		
					<div id="button">
							<s:if test="#session.scoreSamplingInfoList.size() > @com.saiten.util.WebAppConst@ZERO">
							<!-- <img src="" class="rollover" > -->
								<%-- <s:submit type="image" src="./material/img/button/statechange_bn-dark.gif" cssClass="rollover" label="%{getText('label.alt.login')}" id="updateRecords"></s:submit> --%>
								<s:submit type="button" cssClass="btn btn-primary" cssStyle="font-weight: bold;font-family: 'MS PGothic';"  label="%{getText('label.alt.updateinspectionflag')}" id="updateRecords"></s:submit>
	  						</s:if>
					</div>	
				</td>
				<td width="20px;">&nbsp;</td>
		 		</s:if>
		 		<td align="left">
					<div id="button"  >
						<s:if test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@STATE_TRAN_MENU_ID">
							<a href="scoreSearch.action?selectedMenuId=<s:property value="@com.saiten.util.WebAppConst@STATE_TRAN_MENU_ID"/>" class="btn btn-primary" style="font-weight: bold;font-family: 'MS PGothic';">
								<!-- <img src="./material/img/button/search_bn-login.gif" class="rollover" > -->
								<s:text name="label.backtosearch"></s:text>
  							</a>
						</s:if><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID">
							<a href="scoreSearch.action?selectedMenuId=<s:property value="@com.saiten.util.WebAppConst@FORCED_MENU_ID"/>" class="btn btn-primary" style="font-weight: bold;font-family: 'MS PGothic';">
								<!-- <img src="./material/img/button/search_bn-login.gif" class="rollover" > -->
								<s:text name="label.backtosearch"></s:text>
  							</a>
						</s:elseif>
						 
					</div>	
				</td>						
			</tr>					
		</table> 	
</div>	
		   			
</div>

 </td>
		
</tr>
</table>
</s:form>
	</tiles:putAttribute>
</tiles:insertDefinition> 
