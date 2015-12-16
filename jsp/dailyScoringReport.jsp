<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib uri="/struts-tags" prefix="s" %>

<tiles:insertDefinition name="baseLayout">
	<tiles:putAttribute name="head-title">
		<title><s:text name="title.daily.score.report"/></title>
	</tiles:putAttribute>
	<tiles:putAttribute name="head-addition">
		<!-- This portion will contains js file includes and js code also. --> 
		<script type="text/javascript" src="./material/scripts/js/dailyScoringReport.js"></script> 	
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
		<s:form name="dailyScoreReportForm" id="dailyScoreReportForm" method="post" action="dailyScoringReportDownload">
		    <div class="search_box"> 
		       <h1 align="left">
		       <s:if test="downloadInfo.reportType != null && downloadInfo.reportType.equals('summary')">
		       		<input type="radio" name="downloadInfo.reportType" id="reports" onclick="dailyReports();resetDate();" value="daily"/>
		       </s:if>
		       <s:else>
		       		<input type="radio" name="downloadInfo.reportType" id="reports" checked="checked" onclick="dailyReports();resetDate();" value="daily"/>
		       </s:else>
		       <s:text name="label.daily.reports"/></h1>
			   <div id="dailyRtgProgressReport">
				   <table class="search_form search_table_width"> 
					   <tr> <th class="partition">
					              <div id="ratingProgRpt">
					              		<s:if test="downloadInfo.ratingProgressReport!=null">
					                    	<s:checkbox name="downloadInfo.ratingProgressReport" id="rating_progress" onclick="dailyRatingProgress();"/>
					              		</s:if>
					              		<s:else>
					              			<s:checkbox name="downloadInfo.ratingProgressReport" id="rating_progress" onclick="dailyRatingProgress();" value="true"/>
					              		</s:else>
					              </div>
					        </th>  
					        <td colspan="5" style="padding-left: 30px;" bgcolor="#4A6C9A" > 
					            <font color ="white" title="calibri" size="11"><b><s:text name="label.daily.rating.progress.report"/></b></font>
					        </td>	   
					   </tr><tr/><tr/><tr/>  
					   <tr> 
					        <th class="partition"> 
					             <div id="discpAnalysisReport">
					             		<s:if test="downloadInfo.discrepancyAnalysisReport!=null">
					             			<s:checkbox name="downloadInfo.discrepancyAnalysisReport" id="analysis_report"/> 
					             		</s:if>
					             		<s:else>
					             			<s:checkbox name="downloadInfo.discrepancyAnalysisReport" id="analysis_report" value="true"/> 
					             		</s:else>
					             </div>
					        </th>  
					        
					        <td colspan="5" style="padding-left: 30px;" bgcolor="#4A6C9A" > 
					            <font color ="white" title="calibri" size="11"><b><s:text name="label.daily.discrepancy.analysis.report"/></b></font>
					        </td>	   
					   </tr>             
				  </table>
				  <s:hidden name="rtgProgRpt" id="rtgProgRpt"></s:hidden>   
				</div>    
		   </div><br/>
							
		  <div class="search_box">  
		      <h1 align="left">
		      <s:if test="downloadInfo.reportType != null && downloadInfo.reportType.equals('summary')">
		      		<input type="radio" name="downloadInfo.reportType" id="summDiscpAnalyRpt" checked="checked" onclick="summaryReports(); resetDate();" value="summary"/>  
		      </s:if>
		      <s:else>
		      		<input type="radio" name="downloadInfo.reportType" id="summDiscpAnalyRpt" onclick="summaryReports(); resetDate();" value="summary"/>  
		      </s:else>	   
		      <s:text name="label.summary.discrepancy.analysis.report"/></h1>
		  </div> <br/><br/>   
		  
<!------------------------------------- Date and Time Section starts ------------------------------------------------> 
		  <div class="search_box"> 
		     		<h1 align="left">&nbsp;&nbsp;&nbsp;<s:text name="label.date.time.selection"/></h1>
		  </div> 
		  <div class="search_box">
			  <div id="questionwiseDiv">
				 <table class="search_form search_table_width">
					<tr>
						 <th class="partition"><font color="#ff6666">*</font>&nbsp;<s:text name="label.date"/></th>
						 <td colspan="5" style="padding-left: 50px;">
							  <table> 
							     <tr> 
							         <td style="padding: 0px;border: none;" valign="top"> 
							             <s:text name="label.from"/> &nbsp;&nbsp;<input type="date" name="downloadInfo.startDate" value='<s:property value="downloadInfo.startDate"/>' id="startDate"> 
							         </td>
							         
							         <td style="padding: 0px;border: none;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;~&nbsp;&nbsp;&nbsp; 
							         </td>   
							         
							         <td style="padding: 0px;border: none;" valign="top">
							             <s:text name="label.to"/> &nbsp;&nbsp;
							             <s:if test="downloadInfo.reportType != null && downloadInfo.reportType.equals('summary')">
							             	<input type="date" name="downloadInfo.endDate" value='<s:property value="downloadInfo.endDate"/>' id="endDate"> 
							         	 </s:if>
							         	 <s:else>
							         	 	<input type="date" name="downloadInfo.endDate" value='<s:property value="downloadInfo.endDate"/>' id="endDate" disabled="disabled"> 
							         	 </s:else>
							         </td>  
							     </tr>
							  </table>
						 </td> 
					</tr> 
				    <tr>
					     <th class="partition"><font color="#ff6666">*</font>&nbsp;<s:text name="label.time"/></th>
						 <td colspan="5" style="padding-left: 50px;">  
							  <table> 
								  <tr><td style="padding: 0px;border: none;" valign="top">
								  		  <s:text name="label.hours"/>&nbsp;<s:select name="downloadInfo.startHours" list="#session.hoursMap" id="startHours"></s:select>&nbsp;&nbsp; 
								  		  <s:text name="label.minutes"/>&nbsp;<s:select name="downloadInfo.startMinutes" list="#session.minsMap" id="startMinutes"></s:select>&nbsp;&nbsp;
								  	  </td> 
								  	  <td style="padding: 0px;border: none;">&nbsp;&nbsp;&nbsp;~&nbsp;&nbsp;&nbsp;</td> 
								  	  <td style="padding: 0px;border: none;" valign="top"> 
								  	        <s:text name="label.hours"/>&nbsp;
								  	    	<s:if test="downloadInfo.endHours!=null"><s:select name="downloadInfo.endHours" list="#session.hoursMap" id="endHours"/></s:if>
								  	    	<s:else><s:select name="downloadInfo.endHours" list="#session.hoursMap" id="endHours" value="23"></s:select></s:else>&nbsp;&nbsp;  
								  	    	<s:text name="label.minutes"/>&nbsp;
								  	    	<s:if test="downloadInfo.endMinutes!=null"><s:select name="downloadInfo.endMinutes" list="#session.minsMap" id="endMinutes"/></s:if> 
								  	    	<s:else><s:select name="downloadInfo.endMinutes" list="#session.minsMap" id="endMinutes" value="59"/></s:else>    									
						              </td> 
						              
						          </tr>
						      </table>
						 </td>
			       </tr>
			   </table>
			</div>
		 </div><br/><br/> 
    <!----------------------------------- Date and Time section ends -------------------------------------------------->
		
		<div id="button" style="text-align: right;margin-right: 40px;" >
	    	  <s:submit id="dailyReportDownload" value='Download' cssStyle="height: 39px;" cssClass="btn btn-primary btn-xl"></s:submit>
		</div>
		<div id="dataNotFound"><s:actionerror/></div>
		</s:form>
		</td></tr>
	</table>  
	</tiles:putAttribute>
</tiles:insertDefinition>