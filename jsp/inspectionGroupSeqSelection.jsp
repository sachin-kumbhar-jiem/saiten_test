﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib uri="/struts-tags" prefix="s" %>

<tiles:insertDefinition name="baseLayout">
	<tiles:putAttribute name="head-title"> 
		<title><s:text name="inspectionopertion.inspectiongroupseqselection.title" /></title>
	</tiles:putAttribute>
	<tiles:putAttribute name="head-addition">
		<!-- This portion will contains js file includes and js code also. -->
		<script type="text/javascript" language="javascript">
			$(document).ready(function(){
				// Set default focus
				$("#next").focus();
				
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
		  
		  <td class="table-center-menu-td">
			<div>
			  <div class="box">
			  <s:if test="%{inspectionGroupSeqMap.isEmpty()}">
			  		 <s:actionerror/>
			  </s:if><s:else>
			  	<h1 align="left"><s:text name="label.inspectiongroupseqselection.selectinspectiongroupseq" /><s:text name="label.openingbracket" /><s:property value="#session.questionInfo.subjectShortName" /><s:text name="label.closingbracket" /></h1>
				<div id="select_contents">
				<s:form name="inspectionGroupSeqSelectionForm" id="inspectionGroupSeqSelectionForm" action="showMarkValueOrGradeSelectionPage" method="post">
				<center>
				<table class="select_table">
					<tr>
					<%-- <td class="message">
						 <s:text name="label.questionselection.selectscoretarget" /> 
					</td>  --%>
					<td>
						<s:if test="!inspectionGroupSeqMap.isEmpty()">
							<s:set id="defaultSelectedInspectionGroupSeq" name="defaultSelectedInspectionGroupSeq" value="%{inspectionGroupSeqMap.keySet().toArray()[0]}"/>	
						</s:if>
						<s:select id="inspectionGroupSeq" name="inspectionGroupSeq" list="inspectionGroupSeqMap" cssClass="selectList" value="%{#defaultSelectedInspectionGroupSeq}" size="18" cssStyle="width:100%;"/>
					</td>
					</tr>
					
					<tr>
						<td class="button">
							<s:if test="!inspectionGroupSeqMap.isEmpty()">
								<%-- <s:submit type="image" src="./material/img/button/next_button.gif" cssClass="rollover" id="next" cssStyle="width: 180px; height: 39px;" label="%{getText('label.alt.next')}"/> --%>
								<s:submit type="button" cssClass="btn btn-primary btn-xl" id="next" cssStyle="width: 180px; height: 39px;" label="%{getText('label.alt.next')}"/>
							</s:if>
							<s:else>
								<%-- <img src="./material/img/button/next_button_on.gif" id="next" style="width: 180px; height: 39px;" alt="<s:text name="label.alt.next"></s:text>" /> --%>
								<p class="btn btn-disabled"><s:text name="label.alt.next"></s:text></p>
							</s:else>
						</td>
					</tr>
					
				</table>
				</center>
				</s:form>
				</div>
			  </s:else>
			</div>
		  </div>  
		</td>
		</tr>
		</table>
	</tiles:putAttribute>
</tiles:insertDefinition> 
