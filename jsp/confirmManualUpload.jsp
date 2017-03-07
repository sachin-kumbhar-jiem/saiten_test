<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib uri="/struts-tags" prefix="s" %>

<tiles:insertDefinition name="baseLayout">
	<tiles:putAttribute name="head-title"> 
		<title><s:text name="label.confirmmanualupload.title" /></title>
	</tiles:putAttribute>
	<tiles:putAttribute name="head-addition">
		<!-- This portion will contains js file includes and js code also. -->
		<script type="text/javascript" language="javascript">
			$(document).ready(function(){
				// Set default focus
				$("#uploadManual").focus();
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
		  
		  <td class="table-center-menu-td" width="94%">
			  <div id="list_pane">
			    <div class="box">
		     		<h1 align="left">
						<s:text name="label.confirmmanualupload"></s:text>
					</h1>
					
			    </div>
				<div style="margin-top: 10%; width: 934px">
						<div id="button" style="padding-top: 10px;" >
							<p>
								<span class="errorMessage"><s:text name="label.confirmmanualupload.msg" /></span>
							</p>
							<br>
							<a href="uploadManual.action?selectedMenuId=<s:property value="@com.saiten.util.WebAppConst@MANUAL_UPLOAD_MENU_ID"/>" id="uploadManual" class="btn btn-primary btn-xl">
								<s:text name="label.previous"></s:text>
	  						 </a>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="userMenu.action" id="userMenu" class="btn btn-primary btn-xl">
								<s:text name="label.backtosaitenmenu"></s:text>
	  						 </a>
						</div>		
				</div>
	  		</div><!-- Close First Div-->

		</td>
		</tr>
		</table>
	</tiles:putAttribute>
</tiles:insertDefinition> 
