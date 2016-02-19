<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:insertDefinition name="baseLayout">
	<tiles:putAttribute name="head-title">
		<title><s:text name="label.kenshusampling.title"></s:text></title>
	</tiles:putAttribute>
	<tiles:putAttribute name="head-addition">
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
		 	<div class="search_box">
		 		<table class="search_form search_table_width">
		 			<tr>
		 				<th class="partition" width="20%">
							<s:text name="label.header.subject" />
						</th>
						<td style="font-family:verdana" width="30%">
							<s:property value="subjectName" />
						</td>
						<th class="partition" width="20%">
							<s:text name="label.kenshu.userId" />
						</th>
						
						<s:if test="#session.samplingSearch == @com.saiten.util.WebAppConst@ACCEPTANCE_DISPLAY">
							<td style="font-family:verdana" width="30%">
								<s:property value="acceptanceDisplayInfo.kenshuUserId" />
							</td>
						</s:if><s:elseif test="#session.samplingSearch == @com.saiten.util.WebAppConst@KENSHU_SAMPLING_SEARCH">
							<td style="font-family:verdana" width="30%">
								<s:property value="#session.scorerInfo.scorerId" />
							</td>
						</s:elseif>
		 			</tr>
		 			<tr>
		 				<th class="partition" width="20%">
							<s:text name="label.scoring.questionnumber" />
						</th>
						
						<s:if test="#session.samplingSearch == @com.saiten.util.WebAppConst@ACCEPTANCE_DISPLAY">
							<td style="font-family:verdana" width="30%">
								<s:property value="acceptanceDisplayInfo.questionNum" />
							</td>
						</s:if><s:elseif test="#session.samplingSearch == @com.saiten.util.WebAppConst@KENSHU_SAMPLING_SEARCH">
							<td style="font-family:verdana" width="30%">
								<s:property value="kenshuSamplingInfo.questionNum" />
							</td>
						</s:elseif>
						
						<s:if test="#session.samplingSearch == @com.saiten.util.WebAppConst@ACCEPTANCE_DISPLAY">
							<th class="partition" width="20%">
								<s:text name="label.search.criteria" />
							</th>
							<td style="font-family:verdana" width="30%">
								<s:property value="acceptanceDisplayInfo.recordSearchCriteria" />
							</td>
							
						</s:if><s:elseif test="#session.samplingSearch == @com.saiten.util.WebAppConst@KENSHU_SAMPLING_SEARCH">
							<th class="partition" width="20%">
								<s:text name="label.total.result.count" />
							</th>
							<td style="font-family:verdana" width="30%">
								<s:property value="kenshuSamplingInfo.resultCount" />
							</td>
							
						</s:elseif>
							
						
						
		 			</tr>
		 		</table>
		 		<br>
		 		<br>
		 			<h1 align="left"><s:text name="label.dailyStatusSearch.qustionwiseSearch" /></h1>
		 		<br>
		 		<br>
		 		
		 		<s:if test="kenshuSamplingSearchRecordInfoList.size() == 0">
		 			<span class="errorMessage"><s:text name="error.message.no.records" /></span>
		 		</s:if><s:else>
		 			<table class="displayTable" style="width: 70%;">
				<thead>
					<tr style="height: 25px;">
						
						<th style="padding-left: 10px;text-align: center;width:10%;">
		 					<s:text name="label.confirmscore.gradenum" />
		 				</th>
		 				 <th style="padding-left: 10px;text-align: center;width:15%;">
		 					<s:text name="lable.kenshusampling.ratio.title" />
		 				</th>
		 				<th style="padding-left: 10px;text-align: center;width:20%;">
		 					<s:text name="lable.kenshusampling.target.number" />
		 				</th>
		 				<th style="padding-left: 10px;text-align: center;">
		 					<%-- <s:text name="lable.kenshusampling.target.number" /> --%>
		 				</th>
					</tr>
			    </thead>
				<tbody>
				<s:iterator value="kenshuSamplingSearchRecordInfoList" id="kenshuObj" status="stat">
					<s:if test="%{#stat.index%2==0}">
						<tr class="even" style="height: 25px;">
					</s:if>
					<s:else>
						<tr class="odd">
					</s:else>
		 			
		 					<td style="width:10%;font-family:verdana">
		 						<s:property value="#kenshuObj.gradeNum"/>
			 				</td>
			 				<td style="width:15%;font-family:verdana">
			 					<s:property value="#kenshuObj.ratio"/>
			 				</td>
			 				<td style="width:20%;font-family:verdana">
			 					<s:property value="#kenshuObj.totalNumber"/>
			 				</td>
			 				<td style="padding-left: 10px;text-align: left;font-family:verdana">
			 					<s:if test="#session.samplingSearch == @com.saiten.util.WebAppConst@ACCEPTANCE_DISPLAY">
									<a href="showKenshuSamplingList.action?slectedGrade=<s:property value="#kenshuObj.gradeNum" />&totalRecordCount=<s:property value="#kenshuObj.totalNumber" />" id="prev" name="prev" style="width:69px; height:15px;color:green;"><s:text name="label.start.grade.wise.answer.checking"></s:text></a>
								</s:if><s:elseif test="#session.samplingSearch == @com.saiten.util.WebAppConst@KENSHU_SAMPLING_SEARCH">
				 					<a href="showKenshuSamplingList.action?slectedGrade=<s:property value="#kenshuObj.gradeNum" />&totalRecordCount=<s:property value="#kenshuObj.totalNumber" />" id="prev" name="prev" style="width:69px; height:15px;color:green;"><s:text name="label.start.grade.wise.kenshu.sampling"></s:text></a>
								</s:elseif>
							
			 				
			 				</td>
					</tr>
				</s:iterator>
				</tbody>
			</table>
		 		</s:else>
		 		
		 		
			<br> <br>
					<%-- <a href="showKenshuSamplingList.action?slectedGrade='all' " id="prev" name="prev"  class="btn btn-primary btn-xl" style="width:200px; margin-right: 50px"><s:text name="btn.kenshu.sampling"></s:text></a> --%>
					<a href="showKenshuSampling?selectedMenuId=<s:property value="@com.saiten.util.WebAppConst@KENSHU_SAMPLING_MENU_ID"/>&sessionClearFlag=<s:property value="@com.saiten.util.WebAppConst@FALSE"/>" id="back" name="back" class="btn btn-primary btn-xl" style="margin-right: 80px"><s:text name="label.backtosearch"></s:text></a>
		 	</div>
		 </td>
		 </tr>
		</table>
	</tiles:putAttribute>
</tiles:insertDefinition>