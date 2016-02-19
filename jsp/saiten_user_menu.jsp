 <%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
 <%@taglib uri="/struts-tags" prefix="s"%>

<tiles:insertDefinition name="baseLayout">
	<tiles:putAttribute name="head-title"> 
		<title><s:text name="title.userMenu"></s:text></title>
	</tiles:putAttribute>
	<tiles:putAttribute name="head-addition">
		<!-- This portion will contains js file includes and js code also. -->
		<script type="text/javascript" language="javascript">
			$(document).ready(function(){
				$("body").addClass("body-overflow-auto-menu");
				$("#firstTimeScoring").focus();
			});
		</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="body-title">
		<!-- This portion will contain perticular page body title. -->
	</tiles:putAttribute>
	<tiles:putAttribute name="body-main">
		<table class="table_width_height_menu">
		<% session.setAttribute("Back",null); %>
 <tr>
   <td class="table-side-menu-td" width="10%">
	       <div id="side_menu"> <!-- 1st td --> 
		     <div class="side_menu_top_margin">
			  <p class="side_menu_heading_color"><s:property value="#session.scorerInfo.roleDescription"/><br><s:property value="#session.scorerInfo.scorerId"/></p>
			  <p class="side_menu_alignment"><s:text name="label.menu"></s:text>
			   <div class="green_menu"><!-- 3rd td --> 
					<a href="<s:url action="logOut" includeParams="none" />" class="menu_button"><s:text name="label.logout" /></a>
			   </div>
			   <br>
			   <s:if test="#session.saitenLoginEnabled != true">
			   		<div class="green_menu">
			   			<input type="button" name="submit" value="<s:text name="label.backToSaitenLMS" />" class="menu_button" onclick="javascript:location.href='backToSaitenLms.action';" />
			   		</div>			   
			   </s:if>

			</div>
			
	   </div>
      </td>
  <td class="table-center-menu-td" width="90%">
	<div>
	<s:if test="#session.scorerInfo.roleId == @com.saiten.util.WebAppConst@SCORER_ROLE_ID 
		|| #session.scorerInfo.roleId == @com.saiten.util.WebAppConst@SV_ROLE_ID 
		|| #session.scorerInfo.roleId == @com.saiten.util.WebAppConst@WG_ROLE_ID 
		|| #session.scorerInfo.roleId == @com.saiten.util.WebAppConst@ADMIN_ROLE_ID">
	  <div class="box">
		<h1 align="left"><s:text name="label.userMenu.note1"></s:text></h1>
		<table class="button_grid">
		<tr>
			<td class="right_side_height_button">
				<s:if test="userMenuIdList.contains(@com.saiten.util.WebAppConst@FIRST_SCORING_MENU_ID)">
					<a href="questionSelection.action?selectedMenuId=<s:property value="@com.saiten.util.WebAppConst@FIRST_SCORING_MENU_ID"/>" id="firstTimeScoring" class="btn btn-primary btn-xl">
						<s:text name="label.alt.firstScoring"></s:text>
					</a>				
				</s:if><s:else>
					<p class="btn btn-disabled"><s:text name="label.alt.firstScoring"></s:text></p>
				</s:else>
			</td>
			<td class="right_side_height_button">
				<s:if test="userMenuIdList.contains(@com.saiten.util.WebAppConst@SECOND_SCORING_MENU_ID)">
					<a href="questionSelection.action?selectedMenuId=<s:property value="@com.saiten.util.WebAppConst@SECOND_SCORING_MENU_ID"/>" id="secondTimeScoring" class="btn btn-primary btn-xl">
						<s:text name="label.alt.secondScoring"></s:text>	
					</a>				
				</s:if><s:else>
					<p class="btn btn-disabled"><s:text name="label.alt.secondScoring"></s:text></p>
				</s:else>
			</td>
			<td class="right_side_height_button">
				<s:if test="userMenuIdList.contains(@com.saiten.util.WebAppConst@CHECKING_MENU_ID)">
					<a href="questionSelection.action?selectedMenuId=<s:property value="@com.saiten.util.WebAppConst@CHECKING_MENU_ID"/>" id="checkingScoring" class="btn btn-primary btn-xl">
						<s:text name="label.alt.checking"></s:text>
					</a>				
				</s:if><s:else>
					<p class="btn btn-disabled"><s:text name="label.alt.checking"></s:text></p>
				</s:else>
			</td>
			<%-- <td class="right_side_height_button">
				<s:if test="userMenuIdList.contains(@com.saiten.util.WebAppConst@INSPECTION_MENU_ID)">
					<a href="questionSelection.action?selectedMenuId=<s:property value="@com.saiten.util.WebAppConst@INSPECTION_MENU_ID"/>" id="inspectionScoring" class="btn btn-primary btn-xl">
						<s:text name="label.alt.inspection"></s:text>				
					</a>	
				</s:if><s:else>
					<p class="btn btn-disabled"><s:text name="label.alt.inspection"></s:text></p>
				</s:else>
			</td> --%>
		</tr>
		<tr>
			<td class="right_side_height_button">
				<s:if test="userMenuIdList.contains(@com.saiten.util.WebAppConst@DENY_MENU_ID)">
					<a href="questionSelection.action?selectedMenuId=<s:property value="@com.saiten.util.WebAppConst@DENY_MENU_ID"/>" id="denialScoring" class="btn btn-primary btn-xl">
						<s:text name="label.alt.deny"></s:text>		
					</a>
				</s:if><s:else>
					<p class="btn btn-disabled"><s:text name="label.alt.deny"></s:text></p>	
				</s:else>
			</td>
			<td class="right_side_height_button">
				<s:if test="userMenuIdList.contains(@com.saiten.util.WebAppConst@PENDING_MENU_ID)">
					<a href="questionSelection.action?selectedMenuId=<s:property value="@com.saiten.util.WebAppConst@PENDING_MENU_ID"/>" id="pendingScoring" class="btn btn-primary btn-xl">
						<s:text name="label.alt.pending"></s:text>				
					</a>	
				</s:if><s:else>
					<p class="btn btn-disabled"><s:text name="label.alt.pending"></s:text></p>	
				</s:else>
			</td>
			<td class="right_side_height_button">
				<s:if test="userMenuIdList.contains(@com.saiten.util.WebAppConst@INSPECTION_MENU_ID)">
					<a href="questionSelection.action?selectedMenuId=<s:property value="@com.saiten.util.WebAppConst@INSPECTION_MENU_ID"/>" id="inspectionScoring" class="btn btn-primary btn-xl">
						<s:text name="label.alt.inspection"></s:text>				
					</a>	
				</s:if><s:else>
					<p class="btn btn-disabled"><s:text name="label.alt.inspection"></s:text></p>
				</s:else>
			</td>
		</tr>
		</table>
		</div>
	</s:if> 	
	
	<s:if test="#session.scorerInfo.roleId == @com.saiten.util.WebAppConst@SV_ROLE_ID 
		|| #session.scorerInfo.roleId == @com.saiten.util.WebAppConst@WG_ROLE_ID 
		|| #session.scorerInfo.roleId == @com.saiten.util.WebAppConst@ADMIN_ROLE_ID">
		<div class="box">
		<h1 align="left"><s:text name="label.userMenu.note2"></s:text></h1>
		<table class="button_grid">
		<tr>
			<td class="right_side_height_button">
				<s:if test="userMenuIdList.contains(@com.saiten.util.WebAppConst@MISMATCH_MENU_ID)">
					<a href="questionSelection.action?selectedMenuId=<s:property value="@com.saiten.util.WebAppConst@MISMATCH_MENU_ID"/>" id="mismatchScoring" class="btn btn-primary btn-xl">
						<s:text name="label.alt.mismatchScoring"></s:text>		
					</a>
				</s:if><s:else>
					<p class="btn btn-disabled"><s:text name="label.alt.mismatchScoring"></s:text></p>
				</s:else>
			</td>
			<td class="right_side_height_button">
				<s:if test="userMenuIdList.contains(@com.saiten.util.WebAppConst@NO_GRADE_MENU_ID)">
					<a href="questionSelection.action?selectedMenuId=<s:property value="@com.saiten.util.WebAppConst@NO_GRADE_MENU_ID"/>" id="nogradeScoring" class="btn btn-primary btn-xl">
						<s:text name="label.alt.noGradeScoring"></s:text>
					</a>	
				</s:if><s:else>
					<p class="btn btn-disabled"><s:text name="label.alt.noGradeScoring"></s:text></p>
				</s:else>
			</td>
			<td class="right_side_height_button">
				<s:if test="userMenuIdList.contains(@com.saiten.util.WebAppConst@OUT_BOUNDARY_MENU_ID)">
					<a href="questionSelection.action?selectedMenuId=<s:property value="@com.saiten.util.WebAppConst@OUT_BOUNDARY_MENU_ID"/>" id="outboundaryScoring" class="btn btn-primary btn-xl">
						<s:text name="label.alt.outOfBoundary"></s:text>			
					</a>
				</s:if><s:else>
					<p class="btn btn-disabled"><s:text name="label.alt.outOfBoundary"></s:text></p>
				</s:else>
			</td>
			<%-- <td class="right_side_height_button">
				<s:if test="userMenuIdList.contains(@com.saiten.util.WebAppConst@DENY_MENU_ID)">
					<a href="questionSelection.action?selectedMenuId=<s:property value="@com.saiten.util.WebAppConst@DENY_MENU_ID"/>" id="denialScoring" class="btn btn-primary btn-xl">
						<s:text name="label.alt.deny"></s:text>		
					</a>
				</s:if><s:else>
					<p class="btn btn-disabled"><s:text name="label.alt.deny"></s:text></p>	
				</s:else>
			</td>
			<td class="right_side_height_button">
				<s:if test="userMenuIdList.contains(@com.saiten.util.WebAppConst@PENDING_MENU_ID)">
					<a href="questionSelection.action?selectedMenuId=<s:property value="@com.saiten.util.WebAppConst@PENDING_MENU_ID"/>" id="pendingScoring" class="btn btn-primary btn-xl">
						<s:text name="label.alt.pending"></s:text>				
					</a>	
				</s:if><s:else>
					<p class="btn btn-disabled"><s:text name="label.alt.pending"></s:text></p>	
				</s:else>
			</td> --%>
			
		</tr>
		<tr>
			<td class="right_side_height_button">
				<s:if test="userMenuIdList.contains(@com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID)">
					<a href="scoreSearch.action?selectedMenuId=<s:property value="@com.saiten.util.WebAppConst@REFERENCE_SAMP_MENU_ID"/>" id="referenceSampling" class="btn btn-primary btn-xl">
						<s:text name="label.alt.referenceSampling"></s:text>				
					</a>	
				</s:if><s:else>
					<p class="btn btn-disabled"><s:text name="label.alt.referenceSampling"></s:text></p>	
				</s:else>
			</td>
			<td class="right_side_height_button">
				<s:if test="userMenuIdList.contains(@com.saiten.util.WebAppConst@SCORE_SAMP_MENU_ID)">
					<a href="scoreSearch.action?selectedMenuId=<s:property value="@com.saiten.util.WebAppConst@SCORE_SAMP_MENU_ID"/>" id="scoreSampling" class="btn btn-primary btn-xl">
						<s:text name="label.alt.scoreSampling"></s:text>	
					</a>			
				</s:if><s:else>
					<p class="btn btn-disabled"><s:text name="label.alt.scoreSampling"></s:text></p>
				</s:else>
			</td>
			<td class="right_side_height_button">
				<s:if test="userMenuIdList.contains(@com.saiten.util.WebAppConst@SPECIAL_SAMP_INSPECTION_MENU_ID)">
					<a href="specialScoreSearch.action?selectedMenuId=<s:property value="@com.saiten.util.WebAppConst@SPECIAL_SAMP_INSPECTION_MENU_ID"/>" id="specialScoring" class="btn btn-primary btn-xl">
						<s:text name="label.alt.specialSampling"></s:text>
					</a>	
				</s:if><s:else>
					<p class="btn btn-disabled"><s:text name="label.alt.specialSampling"></s:text></p>
				</s:else>
			</td>
		</tr>
		</table>
		</div>
	</s:if>
	<s:if test="#session.scorerInfo.roleId == @com.saiten.util.WebAppConst@WG_ROLE_ID 
		|| #session.scorerInfo.roleId == @com.saiten.util.WebAppConst@ADMIN_ROLE_ID">
		<div class="box">
		<h1 align="left"><s:text name="label.userMenu.note3"></s:text></h1>
		<table class="button_grid">
		<tr>
			<td class="right_side_height_button">
				<s:if test="userMenuIdList.contains(@com.saiten.util.WebAppConst@FORCED_MENU_ID)">
					<a href="scoreSearch.action?selectedMenuId=<s:property value="@com.saiten.util.WebAppConst@FORCED_MENU_ID"/>" id="forcedScoring" class="btn btn-primary btn-xl">
						<s:text name="label.alt.forcedScoring"></s:text>				
					</a>
				</s:if><s:else>
					<p class="btn btn-disabled"><s:text name="label.alt.forcedScoring"></s:text></p>
				</s:else>
			</td>
			<td class="right_side_height_button">
			<s:if test="#session.scorerInfo.noDbUpdate != @com.saiten.util.WebAppConst@NO_DB_UPDATE_TRUE">
			    <s:if test="userMenuIdList.contains(@com.saiten.util.WebAppConst@STATE_TRAN_MENU_ID)">
					<a href="scoreSearch.action?selectedMenuId=<s:property value="@com.saiten.util.WebAppConst@STATE_TRAN_MENU_ID"/>" id="stateTransition" class="btn btn-primary btn-xl">
						<s:text name="label.alt.stateTransitionPlanning"></s:text>
					</a>	
				</s:if><s:else>
					<p class="btn btn-disabled"><s:text name="label.alt.stateTransitionPlanning"></s:text></p>
				</s:else>
			</s:if>
				
			</td>
			<!-- <td></td> -->
			<td class="right_side_height_button">
				<s:if test="userMenuIdList.contains(@com.saiten.util.WebAppConst@FIRST_SCORING_QUALITY_CHECK_MENU_ID)">
					<a href="questionSelection.action?selectedMenuId=<s:property value="@com.saiten.util.WebAppConst@FIRST_SCORING_QUALITY_CHECK_MENU_ID"/>" id="firstTimeQualityCheckScoring" class="btn btn-primary btn-xl">
						<s:text name="label.alt.firstTimeQualityCheckScoring"></s:text>
					</a>				
				</s:if><s:else>
					<p class="btn btn-disabled"><s:text name="label.alt.firstTimeQualityCheckScoring"></s:text></p>
				</s:else>
			</td>
			<!-- <td>&nbsp;</td> -->
			
			<%-- <td class="right_side_height_button" >
			   <s:if test="userMenuIdList.contains(@com.saiten.util.WebAppConst@DAILY_STATUS_REPORT_MENU_ID)">
					<a href="dailyStatusReportSearch.action?selectedMenuId=<s:property value="@com.saiten.util.WebAppConst@DAILY_STATUS_REPORT_MENU_ID"/>" id="forcedScoring" class="btn btn-primary btn-xl">
						 <s:text name="menu.btn.dailyStatusSearch" />				
					</a>
				</s:if><s:else>
					<p class="btn btn-disabled"><s:text name="menu.btn.dailyStatusSearch" /></p>
				</s:else>
			</td> --%>
		</tr>
		<tr>
			<td class="right_side_height_button" >
			   <s:if test="userMenuIdList.contains(@com.saiten.util.WebAppConst@DAILY_STATUS_REPORT_MENU_ID)">
					<a href="dailyStatusReportSearch.action?selectedMenuId=<s:property value="@com.saiten.util.WebAppConst@DAILY_STATUS_REPORT_MENU_ID"/>" id="forcedScoring" class="btn btn-primary btn-xl">
					 <s:text name="menu.btn.dailyStatusSearch" />				
					</a>
				</s:if><s:else>
					<p class="btn btn-disabled"><s:text name="menu.btn.dailyStatusSearch" /></p>
				</s:else>
			</td>
			
			<td class="right_side_height_button">
			   <s:if test="userMenuIdList.contains(@com.saiten.util.WebAppConst@DAILY_REPORT_MENU_ID)">
			      <a href="dailyReportInit.action" id="dailyReport" class="btn btn-primary btn-xl">
	               	   <s:text name="menu.btn.dailyReport"/></a>				
			   </s:if><s:else>
					<p class="btn btn-disabled"><s:text name="menu.btn.dailyReport"/></p>
			   </s:else>
		    </td>
		    
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		
		</table>
		</div>		
	</s:if>	
	</div> 
	<s:if test="#session.scorerInfo.roleId == @com.saiten.util.WebAppConst@KENSHU_ROLE_ID">
		<div class="box">
		<h1 align="left"><s:text name="label.ft.userMenu.note4"></s:text></h1>
		<table class="button_grid">
		<tr>
			<td class="right_side_height_button">
				<s:if test="userMenuIdList.contains(@com.saiten.util.WebAppConst@KENSHU_SAMPLING_MENU_ID)">
					<a href="showKenshuSampling.action?selectedMenuId=<s:property value="@com.saiten.util.WebAppConst@KENSHU_SAMPLING_MENU_ID"/>&sessionClearFlag=<s:property value="@com.saiten.util.WebAppConst@TRUE"/>" id="forcedScoring" class="btn btn-primary btn-xl">
						<s:text name="label.alt.kenshusampling"></s:text>				
					</a>
				</s:if><s:else>
					<p class="btn btn-disabled"><s:text name="label.alt.kenshusampling"></s:text></p>
				</s:else>
			</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		
		
		</table>
		</div>		
	</s:if>	 
 </td>
 </tr>
</table>	
	</tiles:putAttribute>
</tiles:insertDefinition>
