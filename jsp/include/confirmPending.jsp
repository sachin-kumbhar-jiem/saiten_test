<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="./material/scripts/js/confirmPending.js"></script>
</head>

<body class="popup" >
		<h2 style="color: #FFFFFF"><s:text name="label.confirmpending.pending" /></h2>
		<!-- <form name="dialogForm" id="dialogForm" method="post"> -->
		<div id="hold">
				<p><s:text name="label.confirmpending.pendingcategory" /></p>
				<p class="list">
				<s:if test="(#session.questionInfo.pendingCategoryGroupMap != null) && (#session.questionInfo.pendingCategoryGroupMap.size()>0)">
					<s:radio theme="simpleNew" id="pendingCategory" name="pendingCategory" list="#session.questionInfo.pendingCategoryGroupMap" cssStyle="width: auto;" />
				</s:if>
					<s:hidden id="pendingCategorySeq" name="pendingCategorySeq" value="%{#session.tranDescScoreInfo.answerInfo.pendingCategorySeq}" />
				</p>
		</div>
		<div align="center">
			<input type="button" class="white_short" name="registerPending" id="registerPending" value="<s:text name="label.register" />" />
			<input type="button" class="white_short" name="close" id="close" value="<s:text name="label.cancel" />" />
		</div>
		<!-- </form> -->
</body>
</body>
</html>