<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="./material/scripts/jQuery/jquery.min.js"></script>
<script type="text/javascript" src="./material/scripts/js/answerImagePopup.js"></script>
<%-- <script type="text/javascript" src="./material/scripts/js/mm_dialog.js"></script>
<script type="text/javascript" src="./material/scripts/js/forward_dialog.js"></script>
<script type="text/javascript" src="./material/scripts/js/list_action.js"></script>
<script type="text/javascript" src="./material/scripts/js/enlarge_image.js"></script>
<script type="text/javascript" src="./material/scripts/js/enlarge_image.js"></script> --%>
</head>

<body class="popup">
	
	<div id="magnifier">
		<!-- <a href="#" id="close" onclick="applyOverflowAuto();"><img src="./material/img/close.png" align="right"></a> -->
		<a href="#" id="zoomOut" onclick="dropImage()"><img src="./material/img/magnifier_zoom_out.png" class="border">&nbsp;<s:text name="label.button.zoomOut"></s:text></a> 
		<a href="#" id="zoomIn" onclick="enlargeImage()"><img src="./material/img/magnifier_zoom_in.png" class="border">&nbsp;<s:text name="label.button.zoomIn"></s:text></a>
		<a href="#" id="original_size" onclick="setImageToDefaultSize()" style="color: black;"><img src="./material/img/default1.png" class="border">&nbsp;<s:text name="label.button.dafault.image.size"></s:text></a>
	</div>
	<div id="mag_image">
		<img src='<s:i18n name="application"><s:text name="saiten.answerimage.url" /></s:i18n>/<s:property value="%{#session.questionInfo.questionNum}" />/<s:property value="%{#session.tranDescScoreInfo.imageFileName}" />' id="image1" name="image1" alt="<s:text name="label.alt.answerImage"></s:text>">
	</div>
</body>
</body>
</html>