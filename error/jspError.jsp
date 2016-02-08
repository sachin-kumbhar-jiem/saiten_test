<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<title><s:text name="title.errorPage"></s:text></title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/material/css/import.css" media="all">

<style type="text/css">
 label { width: auto; float: none; color: red;  display: block;   }
</style>
<script type="text/javascript" src="./material/scripts/js/default.js"></script>
<script language="JavaScript">
	javascript:window.history.forward(1);
</script>
</head>
<body>

<div style="height: 100%;">
<div id="wrapper" style="height: 100%;">
<!-- ■■■header■■■ -->
<div></div>
<!-- //CLOSE■■■header■■■ -->

		<div id="contents" class="text14">
			<div></div>
			
		<!-- This portion will contain main body of perticular page -->
		<table class="table_width_height">
		 <tr>
		   <td colspan="2" class="table-side-menu-td" width="9%">
			<div id="side_menu">
				<div class="side_menu_top_margin">
					<p class="side_menu_heading_color">
					<p class="side_menu_alignment">
					</p>
				</div>			
			</div> 
		  </td>
		  
		  <td class="table-center-menu-td">
			<div >
			  <div class="box" >
				<h1 align="left"></h1>
				<div style="vertical-align: middle; margin-top: 25%;">
					<span class="errorMessage">内部アプリケーションエラーが発生しました。</span>
					<br><br>
						<a href="showSaitenMenu.action" id="back" name="back" class="btn btn-primary btn-xl">
							<!-- <img class="rollover" src="./material/img/button/saitentop_dark.gif" /> -->
							<s:text name='label.backtosaitenmenu' />
						</a>
						<s:if test="#session.saitenLoginEnabled != true">
							<br>
							<a href="backToSaitenLms.action" id="backToLms" name="backToLms" class="btn btn-primary btn-xl">
								<s:text name="label.backToSaitenLMS" />
							</a>						
						</s:if>
				</div>
			</div>
		  </div>  
		</td>
		</tr>
		</table>
	
		</div>
		
<!-- ■■■footer■■■ -->
<div></div><!-- //CLOSE■■■footer■■■ -->

	</div>

</div>
</body>
</html> 
