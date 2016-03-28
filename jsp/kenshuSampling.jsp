<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:insertDefinition name="baseLayout">
	<tiles:putAttribute name="head-title">
		<title><s:text name="label.kenshusampling.title"></s:text></title>
	</tiles:putAttribute>
	<tiles:putAttribute name="head-addition">
	<script type="text/javascript" src="./material/scripts/js/additionalMethod_validate.js"></script>
		<script type="text/javascript" src="./material/scripts/js/kenshuSampling.js"></script>
		<script type="text/javascript" src="./material/scripts/jQuery/jquery.validate.js" ></script>
		<script type="text/javascript" src="./material/scripts/js/date_validation.js"></script>
		<script type="text/javascript"  src="./material/scripts/js/errorMessages.js" ></script>
	</tiles:putAttribute>
	<tiles:putAttribute name="body-title">
		<!-- This portion will contain perticular page body title. -->
	</tiles:putAttribute>
	<tiles:putAttribute name="body-main">
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
			<s:form name="kenshuSamplingForm" id="kenshuSamplingForm" method="post" action="kenshuSampling">
		 	<div class="search_box">
		 	<s:if test="#session.scorerInfo.roleId == @com.saiten.util.WebAppConst@KENSHU_ROLE_ID 
		|| #session.scorerInfo.roleId == @com.saiten.util.WebAppConst@ADMIN_ROLE_ID">
			 	<s:if test="#session.samplingSearch == @com.saiten.util.WebAppConst@KENSHU_SAMPLING_SEARCH">
					<h1 align="left"><input type="radio" class="searchType" name="kenshuSamplingSearch" id="kenshuSamplingSearch" checked="checked" value="<s:property value="@com.saiten.util.WebAppConst@KENSHU_SAMPLING_SEARCH" />" ><s:text name="label.kenshu.sampling.search" /></h1>
				</s:if>
				<s:else>
					<h1 align="left"><input type="radio" class="searchType" name="kenshuSamplingSearch" id="kenshuSamplingSearch" value="<s:property value="@com.saiten.util.WebAppConst@KENSHU_SAMPLING_SEARCH" />" ><s:text name="label.kenshu.sampling.search" /></h1>
				</s:else>
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
									<c:set var="sessionSubjectCode" value="${sessionScope.kenshuSamplingInfo.subjectCode}" />		
									<c:choose>
										<c:when test="${not ((roleId eq 4) or (fn:contains(loggedInScorerSubjectList,fn:split(subjectNameKey, '-')[1])))}">
											<input type="radio" id="subjectCode" name="kenshuSamplingInfo.subjectCode" value="${fn:split(entry.key, '-')[1]}" disabled="disabled">&nbsp;<c:out value="${entry.value}"/>
										</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${fn:contains(sessionSubjectCode, fn:split(subjectNameKey, '-')[1])}">
												<input type="radio" id="subjectCode" name="kenshuSamplingInfo.subjectCode" checked="checked" value="${fn:split(entry.key, '-')[1]}">&nbsp;<c:out value="${entry.value}"/>
											</c:when>
										<c:otherwise>
												<input type="radio" id="subjectCode" name="kenshuSamplingInfo.subjectCode" value="${fn:split(entry.key, '-')[1]}">&nbsp;<c:out value="${entry.value}"/>													
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
								<s:text name="label.scoresearch.questionnumber" />
							</th>
							<td class="search_form_class1" colspan="5" style="padding-left: 20px;">
								<s:textfield id="questionNum" name="kenshuSamplingInfo.questionNum" maxlength="5" size="15" value="%{#session.kenshuSamplingInfo.questionNum}" />
							</td>
						</tr>
						<tr>
							<th class="partition">
								<s:text name="label.scoresearch.resultcount" />
							</th>
							<td class="search_form_class1" colspan="5" style="padding-left: 20px;">
								<s:textfield id="resultCount" name="kenshuSamplingInfo.resultCount" maxlength="6" size="15" value="%{#session.kenshuSamplingInfo.resultCount}" />
							</td>
					</table>
			</s:if>
			
			
			<s:if test="#session.scorerInfo.roleId == @com.saiten.util.WebAppConst@WG_ROLE_ID" >
				<s:if test="#session.samplingSearch == @com.saiten.util.WebAppConst@KENSHU_SAMPLING_SEARCH">
					<h1 align="left"><input type="radio" class="searchType" name="kenshuSamplingSearch" id="kenshuSamplingSearch" checked="checked" disabled="disabled" value="<s:property value="@com.saiten.util.WebAppConst@KENSHU_SAMPLING_SEARCH" />" ><s:text name="label.kenshu.sampling.search" /></h1>
				</s:if>
				<s:else>
					<h1 align="left"><input type="radio" class="searchType" name="kenshuSamplingSearch" id="kenshuSamplingSearch" disabled="disabled" value="<s:property value="@com.saiten.util.WebAppConst@KENSHU_SAMPLING_SEARCH" />" ><s:text name="label.kenshu.sampling.search" /></h1>
				</s:else>
					 <table class="search_form search_table_width">
					 	<tr >
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
									<c:set var="sessionSubjectCode" value="${sessionScope.kenshuSamplingInfo.subjectCode}" />		
									<c:choose>
										<c:when test="${not ((roleId eq 4) or (fn:contains(loggedInScorerSubjectList,fn:split(subjectNameKey, '-')[1])))}">
											<input type="radio" id="subjectCode" name="kenshuSamplingInfo.subjectCode" value="${fn:split(entry.key, '-')[1]}" disabled="disabled">&nbsp;<c:out value="${entry.value}"/>
										</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${fn:contains(sessionSubjectCode, fn:split(subjectNameKey, '-')[1])}">
												<input type="radio" disabled="disabled" id="subjectCode" name="kenshuSamplingInfo.subjectCode" checked="checked" value="${fn:split(entry.key, '-')[1]}">&nbsp;<c:out value="${entry.value}"/>
											</c:when>
										<c:otherwise>
												<input type="radio" id="subjectCode" disabled="disabled" name="kenshuSamplingInfo.subjectCode" value="${fn:split(entry.key, '-')[1]}">&nbsp;<c:out value="${entry.value}"/>													
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
								<s:text name="label.scoresearch.questionnumber" />
							</th>
							<td class="search_form_class1" colspan="5" style="padding-left: 20px;">
								<s:textfield id="questionNum" disabled="true" name="kenshuSamplingInfo.questionNum" maxlength="5" size="15" value="%{#session.kenshuSamplingInfo.questionNum}" />
							</td>
						</tr>
						<tr>
							<th class="partition">
								<s:text name="label.scoresearch.resultcount" />
							</th>
							<td class="search_form_class1" colspan="5" style="padding-left: 20px;">
								<s:textfield id="resultCount" name="kenshuSamplingInfo.resultCount" maxlength="6" size="15" value="%{#session.kenshuSamplingInfo.resultCount}" />
							</td>
					</table>
			</s:if>
			
			
			<s:if test="#session.samplingSearch == @com.saiten.util.WebAppConst@ACCEPTANCE_DISPLAY 
			|| #session.scorerInfo.roleId == @com.saiten.util.WebAppConst@WG_ROLE_ID">
				<h1 align="left"><input type="radio" class="searchType" name="acceptanceDisplayRadio" id="acceptanceDisplayRadio" checked="checked" value="<s:property value="@com.saiten.util.WebAppConst@ACCEPTANCE_DISPLAY" />"><s:text name="label.kenshu.acceptance" /></h1>
			</s:if>
			<s:else>
				<h1 align="left"><input type="radio" class="searchType" name="acceptanceDisplayRadio" id="acceptanceDisplayRadio" value="<s:property value="@com.saiten.util.WebAppConst@ACCEPTANCE_DISPLAY" />"><s:text name="label.kenshu.acceptance" /></h1>
			</s:else>
		
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
								<c:set var="sessionSubjectCode" value="${sessionScope.acceptanceDisplayInfo.subjectCode}" />
								<c:choose>
									<c:when test="${not((roleId eq 4) or (fn:contains(loggedInScorerSubjectList,fn:split(subjectNameKey, '-')[1])))}">
										<input type="radio" id="subjectCodeA" name="acceptanceDisplayInfo.subjectCode" value="${fn:split(entry.key, '-')[1]}" disabled="disabled">&nbsp;<c:out value="${entry.value}"/>
									</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${fn:contains(sessionSubjectCode, fn:split(subjectNameKey, '-')[1])}">
											<input type="radio" id="subjectCodeA" name="acceptanceDisplayInfo.subjectCode" checked="checked" value="${fn:split(entry.key, '-')[1]}">&nbsp;<c:out value="${entry.value}"/>
										</c:when>
									<c:otherwise>
											<input type="radio" id="subjectCodeA" name="acceptanceDisplayInfo.subjectCode" value="${fn:split(entry.key, '-')[1]}">&nbsp;<c:out value="${entry.value}"/>													
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
							<s:text name="label.scoresearch.questionnumber" />
						</th>
						<td colspan="5" style="padding-left: 20px;">
							<s:textfield id="questionNumA" name="acceptanceDisplayInfo.questionNum" maxlength="5" size="15" value="%{#session.acceptanceDisplayInfo.questionNum}" />
						</td>
					</tr>
					<tr>
						<th class="partition">
							<s:text name="label.kenshu.userId" /><s:text name="lable.required" />
						</th>
						<td colspan="5" style="padding-left: 20px;">
							<s:textfield id="acceptanceDisplayInfo.kenshuUserId" name="acceptanceDisplayInfo.kenshuUserId" maxlength="7" size="15" value="%{#session.acceptanceDisplayInfo.kenshuUserId}" />
							<s:hidden id="userID" name="userID" value="%{#session.scorerInfo.scorerId}"></s:hidden>
						</td>
					</tr>
					<tr>
						<th class="partition" >
							<s:text name="lable.acceptance.search.criteria" />
						</th>
						<td colspan="5" style="padding-left: 20px;">
							<c:forEach var="criteria" items="${searchCriteria}" varStatus="searchCriteria">
								<%-- <input type="radio" id="acceptanceDisplaySubjectCode" name="acceptanceDisplayInfo.subjectCode" value="${criteria.value}">&nbsp;<c:out value="${criteria.value}"/> --%>
								
								<c:set var="sessionSearchCriteria" value="${sessionScope.acceptanceDisplayInfo.recordSearchCriteria}" />
								<c:set var="searchCriteriaKey" value="${criteria.key}" />
									
								<c:choose>
									<c:when test="${fn:contains(sessionSearchCriteria, searchCriteriaKey)}">
										<input type="radio" id="recordSearchCriteria" name="acceptanceDisplayInfo.recordSearchCriteria" checked="checked" value="${criteria.value}">&nbsp;<c:out value="${criteria.value}"/>
									</c:when>
								<c:otherwise>
										<input type="radio" id="recordSearchCriteria" name="acceptanceDisplayInfo.recordSearchCriteria" value="${criteria.value}">&nbsp;<c:out value="${criteria.value}"/>													
								</c:otherwise>
								</c:choose>
							</c:forEach>
						</td>
					</tr>
				</table>
				<div id="button" style="text-align: right;margin-right: 40px;" >
	    	  		<s:submit type="button" id="kenshuSearch" cssStyle="height: 39px;" cssClass="btn btn-primary btn-xl" label="%{getText('kenshusamplingsearch.search.btn')}"></s:submit>
				</div>

			</div>
			</s:form>
		</td>
		</tr>
		
		</table>
	</tiles:putAttribute>
</tiles:insertDefinition>