﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib uri="/struts-tags" prefix="s" %>

<tiles:insertDefinition name="baseLayout">
	<tiles:putAttribute name="head-title">
		<s:if test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID">
			<title><s:text name="label.forcedscoringcount.title"/></title>
		</s:if><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@STATE_TRAN_MENU_ID">
			<title><s:text name="label.statetransition.searchcount.title"/></title>
		</s:elseif>
	</tiles:putAttribute>
	<tiles:putAttribute name="head-addition">
	    <script type="text/javascript">
		     function disableButton(){
		       $("#showList").blur(); 
		   	   $('#showList').addClass('btn-disabled');
		     }
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
					<p class="side_menu_alignment"><s:text name="label.menu" />
						<div class="green_menu">
							<input type="button" name="submit" value="<s:text name="label.backtosaitenmenu" />" class="menu_button" 
								onclick="javascript:location.href='userMenu.action';"><p>
							<s:if  test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID">
								<a href="<s:url action="historyListing" includeParams="none" />" class="menu_button"><s:text name="label.historylist" /></a><p>
								<a href="<s:url action="bookmarkListing" includeParams="none" />" class="menu_button"><s:text name="label.bookmarklist" /></a><p>
							</s:if>
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
		  
		  <td class="table-center-menu-td" width="94%">

  <div id="list_pane">
    <div class="box">
     		<h1>
						<s:text name="label.statetransition.searchcount"></s:text>
			</h1>
					<p>
						<span class="infoMsgColor"><s:text name="label.statetransition.searchresult"></s:text></span>&nbsp;
						<strong>
							<span style="font-size:30px;">
								<s:property value="recordCount"/>
							</span>
						</strong>
						<span class="infoMsgColor"><s:text name="label.statetransition.casesfound"></s:text></span>
					</p>
		    </div>

						<div id="button" style="padding-top: 10px;">
							<s:if test="recordCount == @com.saiten.util.WebAppConst@ZERO">
								<!-- <img src="./material/img/button/search1_bn-login_on.gif"> -->
								<p class="btn btn-disabled"><s:text name="label.alt.displayList"></s:text></p>
							</s:if><s:else>
								<s:if test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID">
									<a href="loadForcedScoringList.action?forceAndStateTransitionFlag=<s:property value="%{@com.saiten.util.WebAppConst@TRUE}" />" class="btn btn-primary btn-xl" id="showList" onclick="disableButton()">
										<!-- <img src="./material/img/button/search1_bn-login.gif" class="rollover"> -->
										<s:text name="label.alt.displayList"></s:text>
  									</a>
								</s:if><s:else>
									<a href="showSearchList.action?forceAndStateTransitionFlag=<s:property value="%{@com.saiten.util.WebAppConst@TRUE}" />" class="btn btn-primary btn-xl" id="showList" onclick="disableButton()">
										<!-- <img src="./material/img/button/search1_bn-login.gif" class="rollover"> -->
										<s:text name="label.alt.displayList"></s:text>
  									</a>
								</s:else>
								
								
							</s:else>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<s:if test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID">
							<a href="scoreSearch.action?selectedMenuId=FORCED_MENU_ID" class="btn btn-primary btn-xl">
								<!-- <img src="./material/img/button/search_bn-login.gif" class="rollover" > -->
								<s:text name="label.backtosearch"></s:text>
  							</a>	
						</s:if><s:else>
							<a href="scoreSearch.action?selectedMenuId=<s:property value="@com.saiten.util.WebAppConst@STATE_TRAN_MENU_ID"/>" class="btn btn-primary btn-xl">
								<!-- <img src="./material/img/button/search_bn-login.gif" class="rollover" > -->
								<s:text name="label.backtosearch"></s:text>
  						 	</a>
						</s:else>
							 
						</div>
						
  		</div><!-- Close First Div-->

		</td>
		</tr>
		</table>
	</tiles:putAttribute>
</tiles:insertDefinition> 
