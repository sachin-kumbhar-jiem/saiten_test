<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Language" content="ja">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">

<!-- head-title portion:  get title dynamically -->

<tiles:insertAttribute name="head-title" />

<!-- include common js and css files -->
		<script type="text/javascript" src="./material/scripts/js/enlarge_image.js"></script>
		<script type="text/javascript" src="./material/scripts/jQuery/jquery.min.js"></script>
		<script type="text/javascript" src="./material/scripts/js/forward_dialog.js"></script>
		<script type="text/javascript" src="./material/scripts/js/list_action.js"></script>
		<script type="text/javascript" src="./material/scripts/js/chbox_action.js"></script>
		<script type="text/javascript" src="./material/scripts/js/default.js"></script>
		<script type="text/javascript" src="./material/scripts/jQuery/jquery.validate.js" ></script>
		<script type="text/javascript" src="./material/scripts/js/script.js"></script>
		<%-- <script type="text/javascript"  src="./material/scripts/js/errorMessages.js" ></script> --%>
		<link rel="stylesheet" type="text/css" href="./material/css/import.css" media="all">
		
		
<style type="text/css">
 label { width: auto; float: none; color: red;  display: block;   }
</style>

<!-- head-addition portion: contains js or css code for page wise -->
<tiles:insertAttribute name="head-addition" />
</head>
<body>

<!-- This file contains JavaScript messages used for Localization -->
<jsp:include page="../include/jsMessages.jsp"></jsp:include>

<div style="height: 100%;">

	<div id="wrapper" style="height: 100%;">
		<tiles:insertAttribute name="body-header" />
		<div id="contents" class="text14">
			<div>
				<tiles:insertAttribute name="body-title" />
			</div>
			<tiles:insertAttribute name="body-main" />
		</div>
		<tiles:insertAttribute name="body-footer" />
	</div>

</div>
</body>
</html>