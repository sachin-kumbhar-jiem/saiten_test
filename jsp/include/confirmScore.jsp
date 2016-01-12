<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="./material/scripts/js/confirmScore.js"></script>
</head>

<body class="popup">

	<!-- <form name="dialogForm" id="dialogForm" method="post"> -->
		<h2 style="color: #FFFFFF"><input type="text" id="scorePopupHeading" name="scorePopupHeading" value='<s:text name="label.confirmscore.confirmscore"></s:text>' style="background-color: #2D2D2D;color: white;border: none;padding-right: 2px;text-align: left;min-width: 260px;" readonly="readonly"></h2>
		<table id="categ" cellspacing="1" style="padding:0px 0px 0px 0px">
			<tr>
				<th><s:text name="label.confirmscore.gradenum" /></th>
				<th><s:text name="label.confirmscore.result" /></th>
			</tr>
			<tr>
				<td><dl><dd id="gradeNum"></dd></dl></td>
				<td><dl><dd id="result"></dd></dl></td>
			</tr>		
		</table>
		
			<p class="list" id="denyMap">
					<s:if test="(#session.questionInfo.denyCategoryGroupMap != null) && (#session.questionInfo.denyCategoryGroupMap.size()>0)">
						<s:radio theme="simpleNew" id="denyCategory" name="denyCategory" list="#session.questionInfo.denyCategoryGroupMap" cssStyle="width: auto;" />
					</s:if>
						<s:hidden id="denyCategorySeq" name="denyCategorySeq" value="%{#session.tranDescScoreInfo.answerInfo.denyCategorySeq}" />
			</p>
		
		<s:hidden id="approveOrDeny" name="approveOrDeny" value="#session.approveOrDeny"></s:hidden>
		<div class="categ_popup_buttons" id="categ_popup_buttons">
			<input type="button" name="registerScore" id="registerScore" class="white_short"  value="<s:text name="label.register" />" />
			<input type="button" name="cancel" id="cancel" class="white_short" value="<s:text name="label.cancel" />" />
		</div>
				
	<!-- </form> -->

</body>
</body>
</html>