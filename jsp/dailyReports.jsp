<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<tiles:insertDefinition name="baseLayout">
	<tiles:putAttribute name="head-title">
		<title><s:text name="title.daily.csv.reports"/></title>
	</tiles:putAttribute>
	<tiles:putAttribute name="head-addition">
	    <script type="text/javascript" src="./material/scripts/js/dailyReports.js"></script>
		<style type="text/css">
 			label { width: auto; float: none; color: red;  display: block; } </style>
	</tiles:putAttribute>
	<tiles:putAttribute name="body-title">
		<!-- This portion will contain particular page body title. -->
	</tiles:putAttribute>
	<tiles:putAttribute name="body-main">
	   <!-- This portion will contain main body of particular page -->
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
			   </div><br/>
		  </div>
		</td>
		<td class="table-center-menu-td" width="70%">
		<s:form name="dailyReportForm" id="dailyReportForm" method="post" action="dailyReportDownload">
		    
		    <div class="search_box">
		       <h1 align="left">
		       <s:text name="label.daily.reports"/></h1>
			   <div id="dailyReport">
				   <table class="search_form search_table_width">
				       <tr>
				           <td colspan="5" style="padding-left: 730px;">
				           		<b><s:text name="label.daily.report.note"/></b>
				           </td>
				       <tr>
					   <tr>
					       <th class="partition">
					            <div id="allQuesCnt" style="display:block">
					               <input type="radio" name="dailyReports" id="studCountForAllQues" value="studCountForAllQues" onclick="allQuesCount();" checked="checked"/>
					            </div>
					       </th>
					       <td colspan="5" style="padding-left: 30px;" bgcolor="#4A6C9A" > 
					            <font color ="white" title="calibri" size="11"><b><s:text name="label.ques.wise.stud.count.all.ques"/></b></font>
					       </td>
					   </tr>
					   <tr>
					       <th class="partition">
					            <div id="specfQuesCnt">
					               <s:if test="#session.dailyReports==@com.saiten.util.WebAppConst@SPECIFIC_STUD_QUES_COUNT">
					                    <input type="radio" name="dailyReports" id="specifiedStudQuesCnt" value="specifiedStudQuesCnt" onclick="specQuesCount();" checked="checked"/>
					               </s:if><s:else>
					                   <input type="radio" name="dailyReports" id="specifiedStudQuesCnt" value="specifiedStudQuesCnt" onclick="specQuesCount();"/>
					               </s:else>					                
					            </div>
					       </th>
					       <td colspan="5" style="padding-left: 30px;" bgcolor="#4A6C9A" > 
					            <font color ="white" title="calibri" size="11"><b><s:text name="label.ques.wise.stud.count.specific.ques"/></b></font>
					       </td>
					   </tr>
					   <tr>
					   	  <th class="partition"></th>
					      <td colspan="5">
					          <font color ="black" title="calibri" size="11"><b>&nbsp;&nbsp;<s:text name="label.question.sequences"/></b></font>
					          <br/><br/><s:textarea name="dailyReportsInfo.questionSequences" id="questionSeq" disabled="true"/>
					      </td>
					   </tr>
					   <tr>
					       <th class="partition">
					            <div id="confirmAndWait">
					                <s:if test="#session.dailyReports==@com.saiten.util.WebAppConst@CONFIRM_AND_WAIT_STATE">
					                   <input type="radio" name="dailyReports" id="confirmAndWait" value="confirmAndWait" onclick="confAndWait();" checked="checked"/>
					                </s:if><s:else>
					                    <input type="radio" name="dailyReports" id="confirmAndWait" value="confirmAndWait" onclick="confAndWait();"/>
					                </s:else>
					            </div>
					       </th>
					       <td colspan="5" style="padding-left: 30px;" bgcolor="#4A6C9A" > 
					            <font color ="white" title="calibri" size="11"><b><s:text name="label.confirm.inspect.wait.count"/></b></font>
					       </td>
					   </tr>
					   <tr>
					       <th class="partition">
					            <div id="notConfirmAndWait">
					                <s:if test="#session.dailyReports==@com.saiten.util.WebAppConst@NOT_CONFIRM_AND_WAIT_STATE">					               
					                    <input type="radio" name="dailyReports" id="notConfirmAndWait" value="notConfirmAndWait" onclick="notConfAndWait();" checked="checked"/>
					                </s:if><s:else>
					                   <input type="radio" name="dailyReports" id="notConfirmAndWait" value="notConfirmAndWait" onclick="notConfAndWait();"/>
					                </s:else>					                
					            </div>
					       </th>
					       <td colspan="5" style="padding-left: 30px;" bgcolor="#4A6C9A" > 
					            <font color ="white" title="calibri" size="11"><b><s:text name="label.not.confirm.inspect.wait.count"/></b></font>
					       </td>
					   </tr>
					   <tr>
					       <th class="partition">
					            <div id="login_logout_report">
					               <s:if test="#session.dailyReports==@com.saiten.util.WebAppConst@LOGIN_LOGOUT_REPORT">					              			                    
					                     <input type="radio" name="dailyReports" id="loginLogoutReport" value="loginLogoutReport" onclick="enableDisableAndClearFields();" checked="checked"/>
					               </s:if><s:else>					                                 
					                     <input type="radio" name="dailyReports" id="loginLogoutReport" value="loginLogoutReport" onclick="enableDisableAndClearFields();" />
					               </s:else>
					            </div>
					       </th>
					       <td colspan="5" style="padding-left: 30px;" bgcolor="#4A6C9A" > 
					            <font color ="white" title="calibri" size="11"><b><s:text name="label.daily.login.report"/></b></font>
					       </td>
					   </tr>
					   <tr>
					       <th class="partition"></th>
					       <td colspan="5">
					           <font color ="black" title="calibri" size="11">&nbsp;&nbsp;<b><s:text name="label.daily.login.report.select.date"/></b></font>&nbsp;
					           <input type="date" name="dailyReportsInfo.currentDate" value='<s:property value="dailyReportsInfo.currentDate"/>' id="sysDate" disabled="disabled"/>
					       </td>
					   </tr>
					   <tr>
					       <th class="partition">
					            <div id="allQuestionCountForWgOnly" style="display:block">
					                <s:if test="#session.dailyReports==@com.saiten.util.WebAppConst@STUD_COUNT_FOR_ALL_QUES_FOR_WG_ONLY">
					                      <input type="radio" name="dailyReports" id="studCountForAllQuesForWgOnly" value="studCountForAllQuesForWgOnly" onclick="allQuesCount();" checked="checked"/>
					                </s:if><s:else>
					                     <input type="radio" name="dailyReports" id="studCountForAllQuesForWgOnly" value="studCountForAllQuesForWgOnly" onclick="allQuesCount();"/>
					                </s:else>					                
					            </div>
					       </th>
					       <td colspan="5" style="padding-left: 30px;" bgcolor="#4A6C9A" > 
					            <font color ="white" title="calibri" size="11"><b><s:text name="label.ques.wise.stud.count.all.ques.for.wg.only"/></b></font>
					       </td>
					   </tr>
					   <tr>
					       <th class="partition">
					            <div id="gradeIsAvailable" style="display:block">
					              <s:if test="#session.dailyReports==@com.saiten.util.WebAppConst@GRADEWISE_COUNT_WHERE_GRADE_IS_AVAILABLE">
					                   <input type="radio" name="dailyReports" id="gradeIsAvailable" value="gradeWiseCountGradeIsAvailable" onclick="allQuesCount();" checked="checked"/>
					              </s:if><s:else>
					                   <input type="radio" name="dailyReports" id="gradeIsAvailable" value="gradeWiseCountGradeIsAvailable" onclick="allQuesCount();"/>
					              </s:else>					                
					            </div>
					       </th>
					       <td colspan="5" style="padding-left: 30px;" bgcolor="#4A6C9A" > 
					            <font color ="white" title="calibri" size="11"><b><s:text name="label.daily.reports.gradewise.count.where.grade.is.available"/></b></font>
					       </td>
					   </tr>
					   <tr>
					       <th class="partition">
					            <div id="pendingCategoryIsSet" style="display:block">
					                 <s:if test="#session.dailyReports==@com.saiten.util.WebAppConst@QUES_SEQ_WISE_COUNT_WHERE_PENDING_CATEGORY_IS_SET">
					                     <input type="radio" name="dailyReports" id="pendingCategoryIsSet" value="pendingCategoryIsSet" onclick="allQuesCount();" checked="checked"/>
					                 </s:if><s:else>
					                     <input type="radio" name="dailyReports" id="pendingCategoryIsSet" value="pendingCategoryIsSet" onclick="allQuesCount();"/>
					                 </s:else>           
					            </div>
					       </th>
					       <td colspan="5" style="padding-left: 30px;" bgcolor="#4A6C9A" > 
					            <font color ="white" title="calibri" size="11"><b><s:text name="label.daily.reports.question.seq.wise.count.where.pending.category.is.set"/></b></font>
					       </td>
					  </tr>
					  <tr>
					       <th class="partition">
					            <div id="historyRecordsCount" style="display:block">
					                <s:if test="#session.dailyReports==@com.saiten.util.WebAppConst@QUESTION_WISE_COUNT_FOR_HISTORY_RECORDS">					                  
					                    <input type="radio" name="dailyReports" id="historyRecordsCount" value="historyRecordsCount" onclick="historyRecordCount();" checked="checked"/>   
					                </s:if><s:else>					                    
					                    <input type="radio" name="dailyReports" id="historyRecordsCount" value="historyRecordsCount" onclick="historyRecordCount();"/>
					                </s:else>
					             
					            </div>
					       </th>
					       <td colspan="5" style="padding-left: 30px;" bgcolor="#4A6C9A" > 
					            <font color ="white" title="calibri" size="11"><b><s:text name="label.daily.report.for.date.and.time.wise.count"/></b></font>
					       </td>
					   </tr>
					   <tr>
					       <th class="partition">
					            <s:text name="label.daily.report.from.date"/>
					       </th>
					       <td style="border-right-width: 0px;" >&nbsp;&nbsp;&nbsp;
					           <input type="date" name="dailyReportsInfo.fromDate" value='<s:property value="dailyReportsInfo.fromDate"/>' id="startDate" disabled="disabled"/>
					      </td>
					      <td style=" border-left-width: 0px;">
					           <s:text name="label.hours"/>&nbsp;<s:select name="dailyReportsInfo.startHours" list="%{session.hoursMap}" id="startHours" disabled="true"></s:select> 
							   &nbsp;<s:text name="label.minutes"/>&nbsp;<s:select name="dailyReportsInfo.startMinutes" list="%{session.minsMap}" id="startMinutes" disabled="true"></s:select>
							   &nbsp;<s:text name="label.seconds"/>&nbsp;<s:select name="dailyReportsInfo.startSeconds" list="%{session.secsMap}" id="startSeconds" disabled="true"></s:select>
					      </td>
					   </tr>
					   <tr>
					       <th class="partition">
					           <s:text name="label.daily.report.to.date"/>
					       </th>
					       <td style="border-right-width: 0px;">&nbsp;&nbsp;&nbsp;
					           <input type="date" name="dailyReportsInfo.toDate" value='<s:property value="dailyReportsInfo.toDate"/>' id="endDate" disabled="disabled"/>
					      </td>
					      <td style=" border-left-width: 0px;">
					           <s:text name="label.hours"/>&nbsp;<s:select name="dailyReportsInfo.endHours" list="%{session.hoursMap}" id="endHours" disabled="true"></s:select> 
							   &nbsp;<s:text name="label.minutes"/>&nbsp;<s:select name="dailyReportsInfo.endMinutes" list="%{session.minsMap}" id="endMinutes" disabled="true"></s:select>
							   &nbsp;<s:text name="label.seconds"/>&nbsp;<s:select name="dailyReportsInfo.endSeconds" list="%{session.secsMap}" id="endSeconds" disabled="true"></s:select>
					      </td>
					   </tr>
					    <tr>
					       <th class="partition">
					            <div id="objectiveReport" style="display:block">
					                <s:if test="#session.dailyReports==@com.saiten.util.WebAppConst@GRADE_WISE_OBJECTIVE_RECORDS">					                  
					                    <input type="radio" name="dailyReports" id="objectiveReport" value="objectiveReport" onclick="objectiveRep();" checked="checked"/>   
					                </s:if><s:else>					                    
					                    <input type="radio" name="dailyReports" id="objectiveReport" value="objectiveReport" onclick="objectiveRep();"/>
					                </s:else>
					             
					            </div>
					       </th>
					       <td colspan="5" style="padding-left: 30px;" bgcolor="#4A6C9A" > 
					            <font color ="white" title="calibri" size="11"><b><s:text name="label.report.for.markvalue.objective.student.count"/></b></font>
					       </td>
					    </tr>
				  </table>
				</div>
		</div><br/>
		<div id="button" style="text-align: right;margin-right: 40px;" >
	    	  <s:submit id="dailyReportDownload" value='%{getText("label.submit.button.download")}' cssStyle="height: 39px;" cssClass="btn btn-primary btn-xl" onclick="clearValidationMsg();"></s:submit>
		</div>
			  <div id="dataNotFound"><s:actionerror/></div>
		</s:form>
		</td></tr>
	</table>
	</tiles:putAttribute>
</tiles:insertDefinition>