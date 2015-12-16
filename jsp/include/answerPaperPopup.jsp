<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="./material/scripts/jQuery/jquery.min.js"></script>
<script type="text/javascript" src="./material/scripts/js/answerPaperPopup.js"></script>
	<s:if test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@OUT_BOUNDARY_MENU_ID">
		<title><s:text name="outofboundary.entireImagePopup.title" /></title>
	</s:if><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@DENY_MENU_ID">
		<title><s:text name="deny.entireImagePopup.title" /></title>
	</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@SCORE_SAMP_MENU_ID">
		<title><s:text name="scoresampling.entireImagePopup.title"/></title>
	</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_BLIND_TYPE_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_LANGUAGE_SUPPORT_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID || #session.questionInfo.menuId == @com.saiten.util.WebAppConst@SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID">
		<title><s:text name="specialscoresearch.entireImagePopup.title" /></title>
	</s:elseif><s:elseif test="#session.questionInfo.menuId == @com.saiten.util.WebAppConst@FORCED_MENU_ID">
		<title><s:text name="label.forcedscoringanswerpaper.title"/></title>
	</s:elseif>
</head>

<body class="popup">
	<s:set var="testSetCode" value="%{#session.mstTestsetnumQuestion.testsetCode}"></s:set>
	<s:if test="#session.mstTestsetnumQuestion.side == '0'">
		<s:set var="folderName" value="-9998"></s:set>
		<s:set var="side" value="98"></s:set>
	</s:if>
	<s:elseif test="#session.mstTestsetnumQuestion.side == '1'">
		<s:set var="folderName" value="-9999"></s:set>
		<s:set var="side" value="99"></s:set>
	</s:elseif>
	<table width="670px">
		<tr>
			<td >
				<div id="magnifier" align="left">
					<!-- <a href="#" id="close" onclick="applyOverflowAuto();"><img src="./material/img/close.png" align="right"></a> -->
					<a href="#" id="zoomOut" onclick="dropPaperImage()"><img src="./material/img/magnifier_zoom_out.png" class="border">&nbsp;<s:text name="label.button.zoomOut"></s:text></a> 
					<a href="#" id="zoomIn" onclick="enlargePaperImage()"><img src="./material/img/magnifier_zoom_in.png" class="border">&nbsp;<s:text name="label.button.zoomIn"></s:text></a>
				</div>
			</td>
			<td>
				<%-- <div align="right"><a href="#" id="frontImage" onclick='loadFrontImage("<s:i18n name="application"><s:text name="saiten.answerimage.url" /></s:i18n>/<s:property value="%{#session.questionInfo.subjectCode}" />-9998/<s:property value="%{#testSetCode}" />98<s:property value="#session.tranDescScoreInfo.answerFormNumber" />.jpeg")' style="text-align: right"><s:text name="label.front"></s:text></a>
					&nbsp;<a href="#" id="backImage" onclick='loadBackImage("<s:i18n name="application"><s:text name="saiten.answerimage.url" /></s:i18n>/<s:property value="%{#session.questionInfo.subjectCode}" />-9999/<s:property value="%{#testSetCode}" />99<s:property value="#session.tranDescScoreInfo.answerFormNumber" />.jpeg")' style="text-align: right"><s:text name="label.back"></s:text></a>
				</div> --%>
			</td>
		</tr>
	</table>

	<div id="mag_image" style="text-align: center;">
		<img src='<s:i18n name="application"><s:text name="saiten.answerimage.url" /></s:i18n>/<s:property value="%{#session.questionInfo.subjectCode}" /><s:property value="%{#folderName}" />/<s:property value="%{#testSetCode}" /><s:property value="%{#side}" /><s:property value="#session.tranDescScoreInfo.answerFormNumber" />.jpeg' id="answerPaper" name="answerPaper" alt="<s:text name="label.alt.answerImage"></s:text>">
	</div>
</body>
</body>
</html>