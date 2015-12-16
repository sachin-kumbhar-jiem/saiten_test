 <%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
 <%@taglib uri="/struts-tags" prefix="s"%>

<tiles:insertDefinition name="baseLayout">
	<tiles:putAttribute name="head-title">
			<title><s:text name="title.login"></s:text></title>
	</tiles:putAttribute>
	<tiles:putAttribute name="head-addition">
		<!-- This portion will contains js file includes and js code also. -->
		<script type="text/javascript"  src="./material/scripts/js/login.js" ></script>
		<script type="text/javascript" src="./material/scripts/js/additionalMethod_validate.js"></script>
		<%-- <script type="text/javascript"  src="./material/scripts/js/errorMessages.js" ></script> --%>
		<style type="text/css">
 			label { width: auto; float: none; color: red;  display: block;   }
		</style>
	</tiles:putAttribute>
	<tiles:putAttribute name="body-title">
		<!-- This portion will contain perticular page body title. -->
	</tiles:putAttribute>
	<tiles:putAttribute name="body-main">
		<!-- ■■■contents-inner■■■ -->
		<div class="login_center">	
			<div class="login01">
				<h2><%-- <img src="./material/img/title/title-login.gif" width="851" height="44" alt="<s:text name="label.alt.login"></s:text>"> --%><s:text name="label.alt.login"></s:text></h2>
					<div class="login01-inner">
					<s:form name="loginForm" id="loginForm" action="login">
						<table class="login01Box">
							<tr align="center">
								<td>
									<s:text name="error.login"></s:text>
								</td>	
							</tr>
							<tr align="center">
								<td id="loginpage">
									<a href="<s:i18n name="application"><s:text name="saitenlms.indexpage.url" /></s:i18n>" ><s:text name="label.backToSaitenLMS"></s:text></a>
								</td>
							</tr>	
						</table>
					</s:form>
					</div>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>