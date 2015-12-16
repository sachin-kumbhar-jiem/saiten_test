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
					<p><s:text name="label.login.note1"></s:text></p>

					<s:form name="loginForm" id="loginForm" action="login">
						<s:actionerror/>
						<s:hidden name="loginAttempt" value="%{@com.saiten.util.WebAppConst@LOGIN_ATTEMPT}" id="loginAttempt" />
						<table class="login01Box">
							<tr>
								<td class="text-right"><s:text name="label.id"></s:text></td>
								<td><s:textfield id="scorerId" name="scorerInfo.scorerId" size="50" cssClass="form-textArea" cssStyle="ime-mode:disabled" maxLength="7"></s:textfield>
								</td>
							</tr>
							<tr>
								<td class="text-right"><s:text name="label.password"></s:text></td>
								<td><s:password id="password" name="scorerInfo.password" size="50" cssClass="form-textArea" cssStyle="ime-mode:disabled" maxLength="256"></s:password>
								</td>
							</tr>
							<tr>
								<td class="login-sen"></td>
								<td class="login-sen">
										<%-- <s:submit type="image" src="./material/img/button/bn-login.gif" cssClass="rollover" label="%{getText('label.alt.login')}"></s:submit> --%>
										<s:submit type="button" cssClass="btn btn-primary btn-lg" label="%{getText('label.alt.login')}"></s:submit>
								</td>
							</tr>	
						</table>
					</s:form>
					</div>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>