﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@taglib uri="/struts-tags" prefix="s"%>

<tiles:insertDefinition name="baseLayout">
	<tiles:putAttribute name="head-title">
		<title><s:text name="menu.btn.dailyStatusSearch"/></title>
	</tiles:putAttribute>
	<tiles:putAttribute name="head-addition">
		<!-- This portion will contains js file includes and js code also. -->
		<style>
			@import url("./material/css/screen.css");
			tr.odd:hover,tr.even:hover {
				cursor: default;
			}
		</style>
	</tiles:putAttribute>
	<tiles:putAttribute name="body-title">
		<!-- This portion will contain perticular page body title. -->
	</tiles:putAttribute>
	<tiles:putAttribute name="body-main">
	<table class="table_width_height" style="width: 100%">
		 <tr>
		   <td colspan="2" class="table-side-menu-td" width="6%">
			<div id="side_menu">
				<div class="side_menu_top_margin">
					<p class="side_menu_heading_color">
						<s:property value="#session.scorerInfo.roleDescription" /><br>
						<s:property value="#session.scorerInfo.scorerId" /><br/>
					</p>	
					<p class="side_menu_alignment"><s:text name="label.menu" />
						<div class="green_menu">
							<input type="button" name="submit" value='<s:text name="label.backtosaitenmenu" />' class="menu_button" 
								onclick="javascript:location.href='userMenu.action';">
							<input type="button" name="submit" value='<s:text name="label.logout" />' class="menu_button" 
								onclick="javascript:location.href='logOut.action';">
					  	</div>
		     	  		<s:if test="#session.saitenLoginEnabled != true">
			         		<br>
							<div class="green_menu">
								<input type="button" name="submit" value="<s:text name="label.backToSaitenLMS" />" class="menu_button" onclick="javascript:location.href='backToSaitenLms.action';" />
							</div>
		       			</s:if>
		       		</p>	
			</div>
		  <br/>
		 </div> 
		</td>
		  
		<td class="table-center-menu-td" width="92%">
		<div class="box">
			<h1 align="left" style="background-repeat:repeat;">
				<s:text name="dailyStatusSearch.report.title.subject" /> <s:text name="label.colon" /> <s:property value="questionInfo.subjectName"/>
				&ensp;&ensp;&ensp;<s:text name="label.scoring.questionnumber" /> <s:text name="label.colon" /> <s:property value="questionInfo.questionNum"/>
			</h1>
			<table class="displayTable" style="width: 100%;">
				<thead>
					<tr style="height: 25px;">
						<th colspan="18" style="padding-left: 10px;text-align: left;">
							<s:text name="dailyStatusQuestionWise.report.title.gradenum.details"/>
						</th>
					</tr>
					<tr style="height: 20px;">
						<th style="width:5%;" rowspan="2"><s:text name="label.gradeNum"/></th>
						<th style="width:5%;" rowspan="2"><s:text name="dailyStatusQuestionWise.report.title.confirm"/></th>
						<s:if test="#session.questionType == @com.saiten.util.WebAppConst@LONG_TYPE">
							<th style="width:5%;" rowspan="2"><s:text name="dailyStatusQuestionWise.report.title.checking.scoring.wait"/></th>
						</s:if><s:else>
							<th style="width:5%;" rowspan="2"><s:text name="dailyStatusQuestionWise.report.title.second.time.scoring.wait"/></th>
						</s:else>
						<th style="width:5%;" rowspan="2"><s:text name="dailyStatusQuestionWise.report.title.inspection.scoring.wait"/></th>
						<th style="width:5%;" rowspan="2"><s:text name="dailyStatusQuestionWise.report.title.denial.scoring.wait"/></th>
						<s:if test="!(#session.questionType == @com.saiten.util.WebAppConst@WRITING_TYPE) && !(#session.questionType == @com.saiten.util.WebAppConst@SPEAKING_TYPE)">
							<th style="width:5%;" colspan="9"><s:text name="dailyStatusSearch.report.title.first.time.scoring.temp"/></th>
						</s:if><s:else>
							<th style="width:5%;" colspan="8"><s:text name="dailyStatusSearch.report.title.first.time.scoring.temp"/></th>
						</s:else>
						<s:if test="!(#session.questionType == @com.saiten.util.WebAppConst@WRITING_TYPE) && !(#session.questionType == @com.saiten.util.WebAppConst@SPEAKING_TYPE)">
							<th style="width:5%;" colspan="2"><s:text name="dailyStatusSearch.report.scorerwise.common.title.cheking"/></th>
						</s:if>	
						<th style="width:5%;" colspan="2"><s:text name="dailyStatusSearch.report.scorerwise.title.inespection.menu"/></th>
					</tr>
					<tr style="height: 20px;">
						<th style="width:4%;"><s:text name="dailyStatusQuestionWise.report.title.first.time"/></th>
						<th style="width:4%;"><s:text name="dailyStatusQuestionWise.report.title.second.time"/></th>
						<th style="width:5%;"><s:text name="btn.scoring.alt.pending"/></th>
						<th style="width:5%;"><s:text name="dailyStatusQuestionWise.report.title.mismatch"/></th>
						<s:if test="!(#session.questionType == @com.saiten.util.WebAppConst@WRITING_TYPE) && !(#session.questionType == @com.saiten.util.WebAppConst@SPEAKING_TYPE)">
							<th style="width:5%;"><s:text name="dailyStatusQuestionWise.report.title.outofboundary"/></th>
						</s:if>
						<th style="width:5%;"><s:text name="label.deny"/></th>
						<th style="width:5%;"><s:text name="label.scoresearch.gradenotavailable"/></th>
						<th style="width:5%;"><s:text name="dailyStatusQuestionWise.report.title.scoreSampling"/></th>
						<th style="width:5%;"><s:text name="dailyStatusQuestionWise.report.title.forced"/></th>
						<s:if test="!(#session.questionType == @com.saiten.util.WebAppConst@WRITING_TYPE) && !(#session.questionType == @com.saiten.util.WebAppConst@SPEAKING_TYPE)">
							<th style="width:5%;"><s:text name="dailyStatusSearch.report.title.checking.scoring.approve.temp"/></th>
							<th style="width:5%;"><s:text name="dailyStatusSearch.report.title.checking.scoring.deny.temp"/></th>
						</s:if>	
						<th style="width:5%;"><s:text name="dailyStatusSearch.report.title.checking.scoring.approve.temp"/></th>
						<th style="width:5%;"><s:text name="dailyStatusSearch.report.title.checking.scoring.deny.temp"/></th>
						
					</tr>
			    </thead>
				<tbody>
				<s:iterator value="gradeWiseReportList" id="dailyStatusInfo" status="stat">
					<s:if test="%{#stat.index%2==0}">
						<tr class="even">
					</s:if>
					<s:else>
						<tr class="odd">
					</s:else>
					<s:if test="#stat.last">
						<td><s:text name="dailyStatusQuestionWise.report.total"/></td>
					</s:if>
					<s:else>
						<td><s:property value="#dailyStatusInfo.gradeNum"/></td>
					</s:else>
						<td><s:property value="#dailyStatusInfo.confirmBatch"/></td>
						<s:if test="#session.questionType == @com.saiten.util.WebAppConst@LONG_TYPE">
							<td><s:property value="#dailyStatusInfo.checkingWorkWait"/></td>
						</s:if><s:else>
							<td><s:property value="#dailyStatusInfo.secondTimeScoringWait"/></td>
						</s:else>
						<td><s:property value="#dailyStatusInfo.inspectionMenuWait"/></td>
						<td><s:property value="#dailyStatusInfo.denyuScoringWait"/></td>
						<td><s:property value="#dailyStatusInfo.firstTimeScoringTemp"/></td>
						<td><s:property value="#dailyStatusInfo.secondTimeScoringTemp"/></td>
						<td><s:property value="#dailyStatusInfo.pendingScoringTemp"/></td>
						<td><s:property value="#dailyStatusInfo.mismatchScoringTemp"/></td>
						<s:if test="!(#session.questionType == @com.saiten.util.WebAppConst@WRITING_TYPE) && !(#session.questionType == @com.saiten.util.WebAppConst@SPEAKING_TYPE)">
							<td><s:property value="#dailyStatusInfo.outOfBoundaryScoringTemp"/></td>
						</s:if>	
						<td><s:property value="#dailyStatusInfo.denyuScoringTemp"/></td>
						<td><s:property value="#dailyStatusInfo.noGradeScoringTemp"/></td>
						<td><s:property value="#dailyStatusInfo.scoringSamplingTemp"/></td>
						<td><s:property value="#dailyStatusInfo.forcedScoringTemp"/></td>
						<s:if test="!(#session.questionType == @com.saiten.util.WebAppConst@WRITING_TYPE) && !(#session.questionType == @com.saiten.util.WebAppConst@SPEAKING_TYPE)">
							<td><s:property value="#dailyStatusInfo.checkingApproveTemp"/></td>
							<td><s:property value="#dailyStatusInfo.chekingDenyTemp"/></td>
						</s:if>
						<td><s:property value="#dailyStatusInfo.inspectionMenuApprove"/></td>
						<td><s:property value="#dailyStatusInfo.inspectionMenuDeny"/></td>
					</tr>
				</s:iterator>
				</tbody>
			</table>
			
			<br/><br/>
			
			<table class="displayTable" style="width: 100%;">
				<thead>
					<tr style="height: 25px;">
						<th colspan="11" style="padding-left: 10px;text-align: left;">
							<s:text name="dailyStatusQuestionWise.report.title.pendingCategory.details"/>
						</th>
					</tr>
					<tr style="height: 20px;">
						<th style="width:10%;" rowspan="2"><s:text name="dailyStatusQuestionWise.report.title.pendingCategory"/></th>
						<th style="width:5%;" rowspan="2"><s:text name="dailyStatusQuestionWise.report.title.pending.scoring.wait"/></th>
						<th style="width:5%;" colspan="9"><s:text name="dailyStatusSearch.report.title.first.time.scoring.pending"/></th>
					</tr>
					<tr style="height: 20px;">
						<th style="width:5%;"><s:text name="dailyStatusQuestionWise.report.title.first.time"/></th>
						<th style="width:5%;"><s:text name="dailyStatusQuestionWise.report.title.second.time"/></th>
						<th style="width:5%;"><s:text name="btn.scoring.alt.pending"/></th>
						<th style="width:5%;"><s:text name="dailyStatusQuestionWise.report.title.mismatch"/></th>
						<s:if test="!(#session.questionType == @com.saiten.util.WebAppConst@WRITING_TYPE) && !(#session.questionType == @com.saiten.util.WebAppConst@SPEAKING_TYPE)">
							<th style="width:5%;"><s:text name="dailyStatusQuestionWise.report.title.outofboundary"/></th>
						</s:if>	
						<th style="width:5%;"><s:text name="label.deny"/></th>
						<th style="width:5%;"><s:text name="label.scoresearch.gradenotavailable"/></th>
						<th style="width:5%;"><s:text name="dailyStatusQuestionWise.report.title.scoreSampling"/></th>
						<th style="width:5%;"><s:text name="dailyStatusQuestionWise.report.title.forced"/></th>
					</tr>
			    </thead>
				<tbody>
				<s:iterator value="pendingCategoryWiseReportList" id="dailyStatusInfo" status="stat">
					<s:if test="%{#stat.index%2==0}">
						<tr class="even">
					</s:if>
					<s:else>
						<tr class="odd">
					</s:else>
					<s:if test="#stat.last">
						<td><s:text name="dailyStatusQuestionWise.report.total"/></td>
					</s:if>
					<s:else>
						<td><s:property value="#dailyStatusInfo.pendingCategory"/></td>
					</s:else>
						<td><s:property value="#dailyStatusInfo.pendingScoringWait"/></td>
						<td><s:property value="#dailyStatusInfo.firstTimeScoringPending"/></td>
						<td><s:property value="#dailyStatusInfo.secondTimeScoringPending"/></td>
						<td><s:property value="#dailyStatusInfo.pendingScorePendingTemp"/></td>
						<td><s:property value="#dailyStatusInfo.mismatchScoringPending"/></td>
						<s:if test="!(#session.questionType == @com.saiten.util.WebAppConst@WRITING_TYPE) && !(#session.questionType == @com.saiten.util.WebAppConst@SPEAKING_TYPE)">
							<td><s:property value="#dailyStatusInfo.outOfBoundaryPendingTemp"/></td>
						</s:if>
						<td><s:property value="#dailyStatusInfo.denyuScoringPending"/></td>
						<td><s:property value="#dailyStatusInfo.noGradePengingTemp"/></td>
						<td><s:property value="#dailyStatusInfo.scoringSamplingPending"/></td>
						<td><s:property value="#dailyStatusInfo.forcedScoringPending"/></td>
					</tr>
				</s:iterator>
				</tbody>
			</table>
			<br/><br/>
			<table style="width: 100%;padding-top: 10px;">
			 	<tbody>
				 	<tr>	
				 		<td align="center">
							<div id="button">
								<a href="dailyStatusReportSearchList.action<s:if test="#parameters['pageNum'][0] != null">?d-49216-p=<s:property value="%{#parameters['pageNum'][0]}"/>&back=true</s:if><s:else>?back=true</s:else>" class="btn btn-primary btn-xl"><s:text name="label.previous" /></a>
								<a href="dailyStatusReportSearch.action?selectedMenuId=<s:property value="@com.saiten.util.WebAppConst@DAILY_STATUS_REPORT_MENU_ID"/>" class="btn btn-primary btn-xl"  id="forcedScoring">
					 				<s:text name="label.backtosearch" />				
								</a>
							</div>	
						</td>						
					</tr>	
				</tbody>
			</table>
		</div>
		</td>
		</tr>
		</table>	
	</tiles:putAttribute>
</tiles:insertDefinition>