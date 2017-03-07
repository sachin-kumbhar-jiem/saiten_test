﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib uri="/struts-tags" prefix="s" %>

<tiles:insertDefinition name="baseLayout">
	<tiles:putAttribute name="head-title"> 
		<title><s:text name="label.manualupload.title" /></title>
	</tiles:putAttribute>
	<tiles:putAttribute name="head-addition">
		<!-- This portion will contains js file includes and js code also. -->
		<script type="text/javascript" language="javascript">
			$(document).ready(function(){
				// Set default focus
				$("#upload").focus();
				
				// Avoid scrolling
				$("body").addClass("body-overflow-auto-question-selection");
			});
		</script>	
	</tiles:putAttribute>
	<tiles:putAttribute name="body-title">
		<!-- This portion will contain perticular page body title. -->
	</tiles:putAttribute>
	<tiles:putAttribute name="body-main">
		<!-- This portion will contain main body of perticular page -->
		<table class="table_width_height">
		 <tr>
		   <td colspan="2" class="table-side-menu-td" width="6%">
			<div id="side_menu">
				<div class="side_menu_top_margin">
					<p class="side_menu_heading_color">
						<s:property value="#session.scorerInfo.roleDescription" /><br>
						<s:property value="#session.scorerInfo.scorerId" /><br/>
					<p class="side_menu_alignment"><s:text name="label.menu"/></p>
						<div class="green_menu">
						  	<input type="button" name="submit2" value="<s:text name="label.backtosaitenmenu" />" class="menu_button" onclick="location.href='userMenu.action';">
							<input type="button" name="submit" value="<s:text name="label.logout" />" class="menu_button" onclick="javascript:location.href='logOut.action';" />
						</div>
				</div>
				<div class="green_menu" id="Submit"></div>
				<s:if test="#session.saitenLoginEnabled != true">
					<br>
					<div class="green_menu">
						<input type="button" name="submit" value="<s:text name="label.backToSaitenLMS" />" class="menu_button" onclick="javascript:location.href='backToSaitenLms.action';" />
					</div>
				</s:if>
			</div> 
		  </td>
		  
		  <td class="table-center-menu-td" width="70%">
			<s:form name="manualUploadForm" id="manualUploadForm" action="upload" method="post" enctype="multipart/form-data">
		    <div class="box">
		       <h1 align="left">
		       		<s:text name="label.manualupload"/>
		       </h1>
			   <div id="dailyReport">
			   		<s:if test="hasActionErrors()">
					   <jsp:forward page="../error/manualUploadError.jsp"></jsp:forward>
					</s:if>
				   <table class="search_form search_table_width"> 
					   <tr>
					   	  <th class="partition" style="width: 20%"><s:text name="label.manualupload.questionlist"/></th>
					      <td colspan="5">
					      
					      <s:if test="questionSequence != null">
					      		<s:set id="defaultselectedQuestion" name="defaultselectedQuestion" value="%{questionSequence}"/>
					      </s:if>
					      <s:else>
					      	<s:if test="!#session.manualUploadQuestionMap.isEmpty()">
								<s:set id="defaultselectedQuestion" name="defaultselectedQuestion" value="%{#session.manualUploadQuestionMap.keySet().toArray()[0]}"/>	
							  </s:if>
					      </s:else>
						      	<s:select id="questionSequence" name="questionSequence" list="%{#session.manualUploadQuestionMap}" cssClass="selectList" value="%{#defaultselectedQuestion}" size="18" cssStyle="width:30%;"/>
					      </td>
					   </tr>
					   <tr>
					       <th class="partition" style="width: 20%"><s:text name="label.manualupload.manualtype"/></th>
					       <td colspan="5">
					           <s:radio theme="simple" name="manualType" list="%{#session.manualList}" value="manualType"/>
					       </td>
					   </tr>
					   <tr>
					       <th class="partition" style="width: 20%"><s:text name="label.manualupload.uploadmanual"/></th>
					       <td colspan="5">
					           <!-- <input type="file" name="manual" /> -->
					           <s:file name="manual" />
					           <%-- <br><span class="errorMessage"><s:property value="%{@org.apache.commons.lang.StringUtils@substring(fieldErrors['manualFileName'], 1, @org.apache.commons.lang.StringUtils@length(fieldErrors['manualFileName']) - 1)}"/></span> --%>		           
					       </td>
					   </tr>
				  </table>
				</div>
			</div>
			<div id="button" style="text-align: center;margin-right: 40px;" >
		    	<s:if test="!#session.manualUploadQuestionMap.isEmpty()">
					<s:submit id="upload" value="%{getText('label.alt.submit')}" cssClass="btn btn-primary btn-xl" cssStyle="height: 39px;" />
				</s:if>
				<s:else>
					<p class="btn btn-disabled" style="text-align: center; width: 130px;"><s:text name="label.alt.submit"></s:text></p>
				</s:else>
			</div>
			</s:form>
			</td>
			</tr>
			</table>
	</tiles:putAttribute>
</tiles:insertDefinition> 
