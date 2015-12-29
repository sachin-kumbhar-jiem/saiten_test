<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="./material/scripts/js/confirmDeny.js"></script>
</head>

<body class="popup" >
		<h2 style="color: #FFFFFF"><s:text name="label.deny" /></h2><!-- make changes for deny -->
		<!-- <form name="dialogForm" id="dialogForm" method="post"> -->
		<div id="hold">
				<p><s:text name="label.confirmdeny.denycategory" /></p>
				<table id="categ" cellspacing="1" style="padding:0px 0px 0px 0px">
					<tr>
						<th><s:text name="label.confirmscore.gradenum" /></th>
						<th><s:text name="label.confirmscore.result" /></th>
					</tr>
					<tr>
						<td><dl><dd id="gradeNum1"></dd></dl></td>
						<td><dl><dd id="result1"></dd></dl></td>
					</tr>		
				</table>
				<p class="list">
				<s:if test="(#session.questionInfo.denyCategoryGroupMap != null) && (#session.questionInfo.denyCategoryGroupMap.size()>0)">
					<s:radio theme="simpleNew" id="denyCategory" name="denyCategory" list="#session.questionInfo.denyCategoryGroupMap" cssStyle="width: auto;" />
				</s:if>
					<s:hidden id="denyCategorySeq" name="denyCategorySeq" value="%{#session.tranDescScoreInfo.answerInfo.denyCategorySeq}" />
				</p>
		</div>
		<div align="center">
			<input type="button" class="white_short" name="registerDeny" id="registerDeny" value="<s:text name="label.register" />" />
			<input type="button" class="white_short" name="close" id="close" value="<s:text name="label.cancel" />" />
		</div>
		<!-- </form> -->
</body>
</body>
</html>