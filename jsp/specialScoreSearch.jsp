<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<tiles:insertDefinition name="baseLayout">
	<tiles:putAttribute name="head-title"> 
			<title><s:text name="label.specialscoresearch.title" /></title>
	</tiles:putAttribute>
	<tiles:putAttribute name="head-addition">
		<!-- This portion will contains js file includes and js code also. -->
		<script type="text/javascript" src="./material/scripts/js/specialScoreSearch.js"></script>
		<script type="text/javascript" src="./material/scripts/js/additionalMethod_validate.js"></script>
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
		<s:form name="specialScoreSearchForm" id="specialScoreSearchForm" method="post">
		 <input type="hidden" name="searchMode" value="default">
		  <div id="list_pane">
		    <div class="search_box">
		     		<h1 align="center"><s:text name="label.specialscoresearch.specialscoring" /></h1>	
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
													
													<c:set var="sessionSubjectCode" value="${sessionScope.specialScoreInputInfo.subjectCode}" />
													
													<c:choose>
														<c:when test="${not ((roleId eq 4) or (fn:contains(loggedInScorerSubjectList,fn:split(subjectNameKey, '-')[1])))}">
															<input type="radio" id="subjectCode" name="specialScoreInputInfo.subjectCode" value="${fn:split(entry.key, '-')[1]}" disabled="disabled">&nbsp;<c:out value="${entry.value}"/>
														</c:when>
														<c:otherwise>
															<c:choose>
																<c:when test="${fn:contains(sessionSubjectCode, fn:split(subjectNameKey, '-')[1])}">
																	<input type="radio" id="subjectCode" name="specialScoreInputInfo.subjectCode" checked="checked" value="${fn:split(entry.key, '-')[1]}">&nbsp;<c:out value="${entry.value}"/>
																</c:when>
																<c:otherwise>
																	<input type="radio" id="subjectCode" name="specialScoreInputInfo.subjectCode" value="${fn:split(entry.key, '-')[1]}">&nbsp;<c:out value="${entry.value}"/>													
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
											<s:text name="label.scoresearch.specialscoringoptions" />
										</th>
										<td colspan="5">
														<s:radio theme="simple"  list="#{'253':'拡大答案','255':'OMR読取不可答案 '}" name="specialScoreInputInfo.specialScoringType" value="%{#session.specialScoreInputInfo.specialScoringType}"></s:radio><br>
														<s:radio theme="simple"  list="#{'251':'点字答案','257':'外国語・方言答案'}" name="specialScoreInputInfo.specialScoringType" value="%{#session.specialScoreInputInfo.specialScoringType}"></s:radio>
										</td>
									</tr>
									<tr>
										<th class="partition">
											<s:text name="label.scoresearch.questionnumber.notmandatory" />
										</th>
										<td class="search_form_class1">
											<s:textfield id="questionNum" name="specialScoreInputInfo.questionNum" maxlength="5" size="5" value="%{#session.specialScoreInputInfo.questionNum}"/>
										</td>
									</tr>
									<tr>
										<th class="partition">
											<s:text name="label.scoresearch.answerformnum" />
										</th>
										<td class="search_form_class1">
											<s:textfield id="answerFormNum" name="specialScoreInputInfo.answerFormNum" maxlength="%{@com.saiten.util.SaitenUtil@getConfigMap().get('answerformnum.lengh')}" size="13" value="%{#session.specialScoreInputInfo.answerFormNum}"/>
										</td>
									</tr>
									<tr>
									</tr>
							</table>	
				    </div>
				    		&nbsp;&nbsp;<span class="infoMsgColor"><s:text name="label.specialscoresearch.comment1"></s:text></span><br><br>
				    		
				    		<span class="infoMsgColor"><s:text name="label.specialscoresearch.comment2"></s:text></span><br><br>
				    				
				    				<a>
							    		<%-- <s:submit type="image" src="./material/img/button/SerchCrt_bn-login.gif" cssClass="rollover infobutton_without_margin" id="search" cssStyle="width: 144px; height: 39px;" label="%{getText('label.next')}"/> --%>
							    		<s:submit type="button" cssClass="btn btn-primary btn-xl infobutton_without_margin" id="search" cssStyle="width: 144px; height: 39px;" label="%{getText('label.next')}"/>										 
		  							</a>
		  </div><!-- Close First Div-->
		  </s:form>
		</td>
		</tr>
	</table>
	</tiles:putAttribute>
</tiles:insertDefinition> 