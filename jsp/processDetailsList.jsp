<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@taglib uri="/struts-tags" prefix="s"%>

<tiles:insertDefinition name="baseLayout">
	<tiles:putAttribute name="head-title">
		<title><s:text name="label.forcedscoringhistorylist.title"/></title>
	</tiles:putAttribute>
	<tiles:putAttribute name="head-addition">
		<!-- This portion will contains js file includes and js code also. -->
		<style>
			@import url("./material/css/screen.css");
			#contents {
				width: 700px;
				background-color: white;
			}
			tr.odd:hover,tr.even:hover {
				cursor: default;
			}
			/* body {
				background-color: white;
			} */
		</style>
	</tiles:putAttribute>
	<tiles:putAttribute name="body-title">
		<!-- This portion will contain perticular page body title. -->
	</tiles:putAttribute>
	<tiles:putAttribute name="body-main">
		<div class="box" style="width: 700px;">
			<h1 align="left"><s:text name="label.processDetails"/></h1>
			<table class="displayTable" style="width: 100%;">
				<thead>
					<tr style="height: 25px;">
						<th colspan="6" style="padding-left: 10px;text-align: left;">
							<s:text name="label.scoring.questionnumber" />&nbsp;<s:text name="label.colon" /><s:property value="#session.questionInfo.subjectShortName" />&emsp;&emsp;
							<s:text name="label.scoring.ansformnumber" />&nbsp;<s:text name="label.colon" />
							<s:text name="label.scoring.openingbracket" /><s:property value="#session.tranDescScoreInfo.answerFormNumber" /><s:text name="label.scoring.closingbracket" />
						</th>
					</tr>
					<tr style="height: 20px;">
						<th><s:text name="label.header.date"/></th>
						<th><s:text name="label.header.status"/></th>
						<th><s:text name="label.scoresearch.scorerid"/></th>
						<th><s:text name="label.confirmscore.gradenum"/></th>
						<th><s:text name="label.confirmscore.result"/></th>
						<th><s:text name="label.scoresearch.pendingcategory"/></th>
					</tr>
			    </thead>
				<tbody>
				<s:iterator value="processDetailsList" id="tranDescInfo" status="stat">
					<s:set name="result" value="%{@com.saiten.util.SaitenUtil@getResultByGradeSequence(#tranDescInfo.gradeSeq,#tranDescInfo.answerInfo.questionSeq)}"></s:set>
					<s:if test="%{#stat.index%2==0}">
						<tr class="even">
					</s:if>
					<s:else>
						<tr class="odd">
					</s:else>
						<td><s:date name="#tranDescInfo.answerInfo.updateDate" format="yyyy/MM/dd HH:mm"/></td>
						<td><s:property value="%{@com.saiten.util.SaitenUtil@getStateNameByScoringState(#tranDescInfo.scoringState)}"/></td>
						<td><s:property value="#tranDescInfo.latestScreenScorerId"/></td>
						<td><s:property value="#tranDescInfo.gradeNum"/></td>
						<td>
							<s:if test="#result == @com.saiten.util.WebAppConst@F" >
								<s:text name="label.result.fail"></s:text>	
							</s:if>
							<s:elseif test="#result == @com.saiten.util.WebAppConst@D" >
								<s:text name="label.result.dubleCircle"></s:text>
							</s:elseif>
							<s:elseif test="#result == @com.saiten.util.WebAppConst@S" >
								<s:text name="label.result.SingleCircle"></s:text>
							</s:elseif>
						</td>
						<td><s:property value="#tranDescInfo.pendingCategory"/></td>
						</tr>
				</s:iterator>
				</tbody>
			</table>
			<table style="width: 100%;padding-top: 10px;">
			 	<tbody>
				 	<tr>	
				 		<td align="center">
							<div id="button">
								<a onclick="javascript:window.close();" class="btn btn-primary"><s:text name="label.alt.answerImage"/></a>
							</div>	
						</td>						
					</tr>	
				</tbody>
			</table>
		</div>	
	</tiles:putAttribute>
</tiles:insertDefinition>