<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib uri="/struts-tags" prefix="s" %>

<tiles:insertDefinition name="baseLayout">
	<tiles:putAttribute name="head-title"> 
			<s:if test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FIRST_SCORING_MENU_ID">
				<title><s:text name="firstTime.markvalueselection.title" /></title>
			</s:if><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@SECOND_SCORING_MENU_ID">
				<title><s:text name="secondTime.markvalueselection.title" /></title>
			</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@CHECKING_MENU_ID">
				<title><s:text name="checking.markvalueselection.title" /></title>
			</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@DENY_MENU_ID">
				<title><s:text name="deny.markvalueselection.title" /></title>
			</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@PENDING_MENU_ID">
				<title><s:text name="pending.markvalueselection.title" /></title>
			</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@INSPECTION_MENU_ID">
				<title><s:text name="inspection.markvalueselection.title" /></title>
			</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@MISMATCH_MENU_ID">
				<title><s:text name="mismatch.markvalueselection.title" /></title>
			</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@NO_GRADE_MENU_ID">
				<title><s:text name="nograde.markvalueselection.title" /></title>
			</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@OUT_BOUNDARY_MENU_ID">
				<title><s:text name="outofboundary.markvalueselection.title" /></title>
			</s:elseif>
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
			  <s:if test="%{selectedQuestionMarkValueMap.isEmpty()}">
			  		 <s:actionerror/>
			  </s:if><s:else>
			  	<h1 align="left"><s:text name="label.markvalueselection.selectmarkvalue" />&nbsp;<s:text name="label.scoring.openingbracket" /><s:property value="subjectShortName"/><s:text name="label.scoring.closingbracket" /></h1>
				<div id="select_contents">
				<s:if test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@CHECKING_MENU_ID  || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@INSPECTION_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@DENY_MENU_ID">
					<s:set name="targetAction" id="targetAction" value="%{'gradeSelection'}"></s:set>
				</s:if><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@PENDING_MENU_ID">
					<s:set name="targetAction" id="targetAction" value="%{'pendingCategorySelection'}"></s:set>
				</s:elseif><s:else>
					<s:set name="targetAction" id="targetAction" value="%{'showScoringPage'}"></s:set>
				</s:else>
				<s:form name="markValueSelectionForm" id="markValueSelectionForm" action="%{#targetAction}" method="post">
				<center>
				<table class="select_table">
					<tr>
					<%-- <td class="message">
						 <s:text name="label.questionselection.selectscoretarget" /> 
					</td>  --%>
					<td>
						<s:if test="!selectedQuestionMarkValueMap.isEmpty()">
							<s:set id="defaultselectedMarkValue" name="defaultselectedMarkValue" value="%{selectedQuestionMarkValueMap.keySet().toArray()[0]}"/>	
						</s:if>
						<s:select id="selectedMarkValue" name="selectedMarkValue" list="selectedQuestionMarkValueMap" cssClass="selectList" value="%{#defaultselectedMarkValue}" size="18" />
					</td>
					</tr>
					<tr>
						<td class="button">
							<s:if test="!selectedQuestionMarkValueMap.isEmpty()">
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
