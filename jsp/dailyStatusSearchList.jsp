<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>

<% session.setAttribute("Back","true"); %>
<tiles:insertDefinition name="baseLayout">
	<tiles:putAttribute name="head-title">
	    <title><s:text name="menu.btn.dailyStatusSearch"/></title>
	</tiles:putAttribute>
	<tiles:putAttribute name="head-addition">
		<script type="text/javascript" src="./material/scripts/jQuery/jquery.tools.min.js"></script>
		<script type="text/javascript"  src="./material/scripts/js/dailyStatusSearchReport.js" ></script>
		<script type="text/javascript">
		
		</script>
		<style>
			@import url("./material/css/displaytag.css");
			@import url("./material/css/screen.css");
			#nameTable tbody tr td a {
				color: blue;
			}
		</style>
		<style type="text/css">
 			label { width: auto; float: none; color: red;  display: block;   }
		</style>
	</tiles:putAttribute>
	<tiles:putAttribute name="body-title">
		<!-- This portion will contain perticular page body title. -->
	</tiles:putAttribute>
	<tiles:putAttribute name="body-main">
	
		<!-- This portion will contain main body of perticular page -->
		<s:form action="update" name="update" id="update">
		<s:hidden id="qtype" name="qtype" value="%{#session.questionType}"></s:hidden>
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
	
<div id="list_pane" >
<div class="box" >
<h1 align="left"><s:text name="menu.btn.dailyStatusSearch" /></h1>

<s:set var="pageNo" value="%{#parameters['d-7500665-p'][0]}"></s:set>
<s:set var="menuId" value="#session.questionInfo.menuId"></s:set>
<%-- <s:set var="forcedMenuId" value="@com.saiten.util.WebAppConst@FORCED_MENU_ID"></s:set> --%>
<s:set var="notScoringStates" value="@com.saiten.util.WebAppConst@FORCED_SCORING_NOT_SCORED_STATES"></s:set>			

	<div align="left" class="selectCheckBox"><s:hidden name="selectCheckBoxToUpdate" id="selectCheckBoxToUpdate"></s:hidden></div>
		<div id="c_b">
		<s:if test="%{#parameters['d-7500665-p'][0]} != ''">
			<s:set var="pageNo" value="%{#parameters['d-7500665-p'][0]}"></s:set>
		</s:if>
		<s:else>
			<s:set var="pageNo" value="@com.saiten.util.WebAppConst@ONE"></s:set>
		</s:else>
		<br />
		<s:if test="%{dailyStatusReportList!=null && !dailyStatusReportList.isEmpty()}">

		<h2 align="left">&nbsp;<s:text name="dailyStatusSearch.report.title.subject" /> <s:text name="label.colon" /> <s:property value="dailyStatusReportList.get(0).subjectName"/></h2>
		<br />
		<table ><tr><td>
		<div id="nameTableSpan1" style="float:left;width:940px;"></div>  
		</td></tr>
		<tr><td><table><tr><td style="vertical-align: top;">
		<div id="nameTableSpan" style="float:left;width:250px;"></div>  
		</td><td style="vertical-align: top;width:123%">
		<div id="dataTableSpan" style="overflow:auto;left;width:675px;"></div>  
		<div id="ladderDiv"  style="width:50px;display: none">
	    <display:table  cellspacing="2" export="true"  id="data" 
                    name="dailyStatusReportList"
                    requestURI="/dailyStatusReportSearchList.action"  pagesize="${sessionScope.dailyStatusReportPageSize}" >                    
            <display:column style="width:4%;" property="questionNum" href="dailyStatusQuestionDetails.action" paramId="questionSeq" paramProperty="questionSeq" titleKey='dailyStatusSearch.report.title.question.num'   />
            <display:column style="width:4%;" property="answerTotal" titleKey='dailyStatusSearch.report.title.answerTotal'   />
            <display:column style="width:4%;" property="answerDecision" titleKey='dailyStatusSearch.report.title.answer.decision'  />
            <s:if test="!(#session.questionType == @com.saiten.util.WebAppConst@WRITING_TYPE) && !(#session.questionType == @com.saiten.util.WebAppConst@SPEAKING_TYPE)">
            	<display:column style="width:4%;" property="confirmBatch" titleKey='dailyStatusSearch.report.title.confirm.batch'  />
            </s:if>	 
            <s:else>
          		  <display:column style="width:0%;"  />
            </s:else>
            <display:column style="width:4%;" property="firstTimeScoringWait" titleKey='dailyStatusSearch.report.title.first.time.scoring.wait'   />
            <display:column style="width:4%;" property="firstTimeScoringTemp" titleKey='dailyStatusSearch.report.title.first.time.scoring.temp'   />
            <display:column style="width:4%;" property="firstTimeScoringPending" titleKey='dailyStatusSearch.report.title.first.time.scoring.pending'   />       
            <display:column style="width:4%;" property="secondTimeScoringWait" titleKey='dailyStatusSearch.report.title.second.time.scoring.wait'   />
            <display:column style="width:4%;" property="secondTimeScoringTemp" titleKey='dailyStatusSearch.report.title.second.time.scoring.temp'   />
            <display:column style="width:4%;" property="secondTimeScoringPending" titleKey='dailyStatusSearch.report.title.second.time.scoring.pending'   />
            <s:if test="!(#session.questionType == @com.saiten.util.WebAppConst@WRITING_TYPE) && !(#session.questionType == @com.saiten.util.WebAppConst@SPEAKING_TYPE)">
            	<display:column style="width:4%;" property="checkingWorkWait" titleKey='dailyStatusSearch.report.title.checking.scoring.wait'   />
            	<display:column style="width:4%;" property="checkingApproveTemp" titleKey='dailyStatusSearch.report.title.checking.scoring.approve.temp'   />
            	<display:column style="width:4%;" property="chekingDenyTemp" titleKey='dailyStatusSearch.report.title.checking.scoring.deny.temp'   />
            </s:if>
            <display:column style="width:4%;" property="pendingScoringWait" titleKey='dailyStatusSearch.report.title.pending.scoring.wait'   />
            <display:column style="width:4%;" property="pendingScoringTemp" titleKey='dailyStatusSearch.report.title.pending.scoring.temp'   />
            <display:column style="width:4%;" property="pendingScorePendingTemp" titleKey='dailyStatusSearch.report.title.pending.scoring.pending'   />                 
            <display:column style="width:4%;" property="mismatchScoringWait" titleKey='dailyStatusSearch.report.title.mismatch.scoring.wait'   />
            <display:column style="width:4%;" property="mismatchScoringTemp" titleKey='dailyStatusSearch.report.title.mismatch.scoring.temp'  />
            <display:column style="width:4%;" property="mismatchScoringPending" titleKey='dailyStatusSearch.report.title.mismatch.scoring.pending'   />  
            <display:column style="width:4%;" property="inspectionMenuWait" titleKey='dailyStatusSearch.report.title.inespection.menu.wait'   />
            <display:column style="width:4%;" property="inspectionMenuApprove" titleKey='dailyStatusSearch.report.title.inespection.menu.approve'  />
            <display:column style="width:4%;" property="inspectionMenuDeny" titleKey='dailyStatusSearch.report.title.inespection.menu.deny'   />
            <s:if test="!(#session.questionType == @com.saiten.util.WebAppConst@WRITING_TYPE) && !(#session.questionType == @com.saiten.util.WebAppConst@SPEAKING_TYPE)">
            	<display:column style="width:4%;" property="outOfBoundaryScoringWait" titleKey='dailyStatusSearch.report.title.outofboundary.scoring.wait'   />
            	<display:column style="width:4%;" property="outOfBoundaryScoringTemp" titleKey='dailyStatusSearch.report.title.outofboundary.scoring.temp'  />
            	<display:column style="width:4%;" property="outOfBoundaryPendingTemp" titleKey='dailyStatusSearch.report.title.outofboundary.scoring.pending'   />
            </s:if>	             
            <display:column style="width:4%;" property="denyuScoringWait" titleKey='dailyStatusSearch.report.title.deny.scoring.wait'  />
            <display:column style="width:4%;" property="denyuScoringTemp" titleKey='dailyStatusSearch.report.title.deny.scoring.temp'   />
            <display:column style="width:4%;" property="denyuScoringPending" titleKey='dailyStatusSearch.report.title.deny.scoring.pending'   /> 
            <display:column style="width:4%;" property="noGradeScoringWait" titleKey='dailyStatusSearch.report.title.nograde.scoring.wait'  />
            <display:column style="width:4%;" property="noGradeScoringTemp" titleKey='dailyStatusSearch.report.title.nograde.scoring.temp'   />
            <display:column style="width:4%;" property="noGradePengingTemp" titleKey='dailyStatusSearch.report.title.nograde.scoring.pending'   /> 
            <display:column style="width:4%;" property="scoringSamplingTemp" titleKey='dailyStatusSearch.report.title.sample.scoring.temp'  />
            <display:column style="width:4%;" property="scoringSamplingPending" titleKey='dailyStatusSearch.report.title.sample.scoring.pending'   /> 
            <display:column style="width:4%;" property="forcedScoringTemp" titleKey='dailyStatusSearch.report.title.forced.scoring.temp'  />
            <display:column style="width:4%;" property="forcedScoringPending" titleKey='dailyStatusSearch.report.title.forced.scoring.pending'  />
            
            <%-- <display:column style="width:4%;" property="punchWait" titleKey='dailyStatusSearch.report.title.punch.wait'  />
            <display:column style="width:4%;" property="punchDataReadWait" titleKey='dailyStatusSearch.report.title.punch.data.read.wait'  />
            <display:column style="width:4%;" property="batchScoringWait" titleKey='dailyStatusSearch.report.title.batch.scoring.wait'  />
            <display:column style="width:4%;" property="batchScoringOputputComplete" titleKey='dailyStatusSearch.report.title.batch.scoring.output.complete'  />
            <display:column style="width:4%;" property="batchScoringComplete" titleKey='dailyStatusSearch.report.title.batch.scoring.complete'  />
            <display:column style="width:4%;" property="scoringNotPossibleByBatch" titleKey='dailyStatusSearch.report.title.scoring.not.possible.by.batch'  />
            <display:column style="width:4%;" property="scoringCompleteByBatch" titleKey='dailyStatusSearch.report.title.scoring.comlete.by.batch'  /> --%>
             
            <display:setProperty value="false" name="basic.empty.showtable"  />
            <display:setProperty name="basic.msg.empty_list" ><s:text name="display.search.display.tag.basic.msg.empty_list"></s:text> </display:setProperty> 
            <display:setProperty name="paging.banner.one_item_found" ><s:text name="display.search.display.tag.paging.banner.one_items_found" /></display:setProperty>
            <display:setProperty name="paging.banner.all_items_found" ><s:text name="display.search.display.tag.paging.banner.all_items_found" /></display:setProperty>
            <display:setProperty name="paging.banner.some_items_found" ><s:text name="display.search.display.tag.paging.banner.some_items_found" /></display:setProperty>  
            <display:setProperty name="paging.banner.full" ><s:text name="display.search.display.tag.paging.banner.full" /></display:setProperty>
            <display:setProperty name="paging.banner.first" ><s:text name="display.search.display.tag.paging.banner.first" /></display:setProperty>
            <display:setProperty name="paging.banner.last" ><s:text name="display.search.display.tag.paging.banner.last" /></display:setProperty>  
            <display:setProperty name="paging.banner.item_name" ><s:text name="display.search.display.tag.paging.banner.item_name" /></display:setProperty>  
            <display:setProperty name="paging.banner.items_name" ><s:text name="display.search.display.tag.paging.banner.items_name" /></display:setProperty>  
         </display:table>
        </div>
        </td></tr></table></td></tr></table>
         </s:if>
         <s:else>
            <display:table export="true"  id="data1" 
                    name="dailyStatusSearchScorerInfo"
                    requestURI="/dailyStatusReportSearchList.action" pagesize="${sessionScope.dailyStatusReportPageSize}"  class="displayTable">
            <display:column style="width:9%;"  property="userId" titleKey='dailyStatusSearch.report.scorerwise.title.scorer.id'   />
            <display:column style="width:9%;"  property="userRoll" titleKey='dailyStatusSearch.report.scorerwise.title.user.roll'   />
            <display:column style="width:9%;"  property="scoringTotal" titleKey='dailyStatusSearch.report.scorerwise.title.scoring.total'   />
            <display:column style="width:9%;"  property="firstTimeScoringTotal" titleKey='dailyStatusSearch.report.scorerwise.title.first.time.scoring.total'  />
            <display:column style="width:9%;"  property="secondTimeScoringTotal" titleKey='dailyStatusSearch.report.scorerwise.title.second.time.scoring.total'   />
            <display:column style="width:9%;" property="pending" titleKey='dailyStatusSearch.report.scorerwise.title.pending' />
            <display:column style="width:9%;" property="mismatchScoringTotal" titleKey='dailyStatusSearch.report.scorerwise.title.mismatch.scoring.total'   />       
            <display:column style="width:9%;" property="inspectionMenu" titleKey='dailyStatusSearch.report.scorerwise.title.inespection.menu'  />
            <display:column style="width:9%;" property="denyuScoringTotal" titleKey='dailyStatusSearch.report.scorerwise.title.deny.scoring.total'  />
            <display:column style="width:9%;" property="scoringSamplingTotal" titleKey='dailyStatusSearch.report.scorerwise.title.scoring.sampling.total'  />     
            <display:column style="width:10%;" property="forcedScoring" titleKey='dailyStatusSearch.report.scorerwise.title.forced.scoring'  />
            <display:setProperty value="false" name="basic.empty.showtable"  />
            <display:setProperty name="basic.msg.empty_list" ><s:text name="display.search.display.tag.basic.msg.empty_list"></s:text> </display:setProperty> 
            <display:setProperty name="paging.banner.one_item_found" ><s:text name="display.search.display.tag.paging.banner.one_items_found" /></display:setProperty>
            <display:setProperty name="paging.banner.all_items_found" ><s:text name="display.search.display.tag.paging.banner.all_items_found" /></display:setProperty>
            <display:setProperty name="paging.banner.some_items_found" ><s:text name="display.search.display.tag.paging.banner.some_items_found" /></display:setProperty>  
            <display:setProperty name="paging.banner.full" ><s:text name="display.search.display.tag.paging.banner.full" /></display:setProperty>
            <display:setProperty name="paging.banner.first" ><s:text name="display.search.display.tag.paging.banner.first" /></display:setProperty>
            <display:setProperty name="paging.banner.last" ><s:text name="display.search.display.tag.paging.banner.last" /></display:setProperty>  
            <display:setProperty name="paging.banner.item_name" ><s:text name="display.search.display.tag.paging.banner.item_name" /></display:setProperty>  
            <display:setProperty name="paging.banner.items_name" ><s:text name="display.search.display.tag.paging.banner.items_name" /></display:setProperty> 
        </display:table>
         </s:else>
		</div>
</div>	

</div>
<br /><br />
<a href="dailyStatusReportSearch.action?selectedMenuId=<s:property value="@com.saiten.util.WebAppConst@DAILY_STATUS_REPORT_MENU_ID"/>" class="btn btn-primary btn-xl"  id="forcedScoring">
					 <s:text name="label.backtosearch" />				
					</a>
 </td>
	
</tr>


</table>
</s:form>
	</tiles:putAttribute>
</tiles:insertDefinition> 
