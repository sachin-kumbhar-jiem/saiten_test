<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %> 
<%@taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Language" content="ja">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<title><s:text name="informationPage.title" /></title>
<script type="text/javascript" src="./material/scripts/js/enlarge_image.js"></script>
<script type="text/javascript" src="./material/scripts/jQuery/jquery.min.js"></script>
<script type="text/javascript" src="./material/scripts/js/forward_dialog.js"></script>
<script type="text/javascript" src="./material/scripts/js/list_action.js"></script>
<script type="text/javascript" src="./material/scripts/js/chbox_action.js"></script>
<script type="text/javascript" src="./material/scripts/js/default.js"></script>
<script type="text/javascript" src="./material/scripts/jQuery/jquery.validate.js" ></script>
<script type="text/javascript" src="./material/scripts/js/script.js"></script>
<script type="text/javascript" src="./material/scripts/js/scoring.js"></script>
<script type="text/javascript" src="./material/scripts/js/modalPopLite.js"></script>
<script language="JavaScript">
	javascript:window.history.forward(1);
</script>
<script language="JavaScript">
function enableLinksAndButtons(){
	$(':button').prop('disabled', false); // Enable all the button
	$('a').unbind('click'); // Enable all the links
}
</script>

<script language="JavaScript">
	function setFoucs() {
		var nextButton = document.getElementById('scoreSamplingNext');
		if (nextButton != null) {
			nextButton.focus();
		}
	}
</script>

<link href="./material/css/modalPopLite.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="./material/css/import.css" media="all">
		
<style type="text/css">
 label { width: auto; float: none; color: red;  display: block;   }
</style>
</head>

<s:if test="#session.scorerInfo.roleId == @com.saiten.util.WebAppConst@SCORER_ROLE_ID">
	<s:set id="bodyColor" name="bodyColor" value="%{'scorer-body-color'}" />
</s:if>
<s:elseif test="#session.scorerInfo.roleId == @com.saiten.util.WebAppConst@SV_ROLE_ID">
	<s:set id="bodyColor" name="bodyColor" value="%{'supervisor-body-color'}" />
</s:elseif>
<s:elseif test="#session.scorerInfo.roleId == @com.saiten.util.WebAppConst@WG_ROLE_ID">
	<s:set id="bodyColor" name="bodyColor" value="%{'wg-body-color'}" />
</s:elseif>
<s:else>
	<s:set id="bodyColor" name="bodyColor" value="%{'admin-body-color'}" />
</s:else>

<body class="<s:property value="%{#bodyColor}"/>" onload="enableLinksAndButtons(); setFoucs();">
<div>
	<div id="wrapper">
		<div id="contents" class="text14">
		<div></div>
			<form  name="scoreActionForm" id="scoreActionForm" action="scoreAction" method="post">
			<table>
		     <tr>
			  <!-- 1st td --> 
		        <td colspan="2" class="table-side-menu-td" width="6%" style="text-align: center;">
			       <div id="side_menu"> <!-- 1st td --> 
				     <div class="side_menu_top_margin">
					  <p class="side_menu_heading_color">
						<s:property value="#session.scorerInfo.roleDescription" /><br>
						<s:property value="#session.scorerInfo.scorerId" /><br/>
					</div><br/>
			   </div>
		      </td>
			   
			   <!-- 2nd td --> 
			   
			   <td class="table-center-menu-td-errorpage">
			   <!-- Start left pane -->
		
		    <div class="box_content_left_pane_input_normal_error_page">
			  <div class="div_center_heading"></div>
			  <p class="score_img_top"></p>
		    </div>
		
			<div style="margin-top: 15%; width: 934px">
			
				<h3> <span class="infoMessage"><s:text name="warning.message.invalid.token.message1"></s:text></span></h3>
				<div style="text-align: left; margin-left: 330px">
					<span class="infoMessage" ><s:text name="warning.message.invalid.token.message2"></s:text></span> <br/>
					<span class="infoMessage" ><s:text name="warning.message.invalid.token.message3"></s:text></span> <br/>
					<span class="infoMessage"><s:text name="warning.message.invalid.token.message4"></s:text></span> <br/>
					<span class="infoMessage"><s:text name="warning.message.invalid.token.message5"></s:text></span> <br/>
				</div>
			</div>
		
	    
		
		 </td>
		 <!-- End left pane --> 
		 	   <!--3rd td --> 
		</tr>
		</table>
		</form>

		</div>
		</div>
	</div>
</body>
</html>
