
//Our validation script will go here.
//$(document).ready(function(){
	$("#registerPending").click(function(){
		//$("#registerPending").prop('disabled', true);
		//$('#close').trigger('click');
		disableLinksAndButtons();
		
		var pendingCategorySeq = $("input[name='pendingCategory']:checked").val();
		var bookMark =  $("#bookMark").is(":checked") ? true : false;
		var qualityMark = $("#qualityMark").is(":checked") ? true : false;
		var scorerComment =  $('#scorerComment').val();
		 var token = $("input[name=token]").val();
		location.href = 'registerPending.action?pendingCategorySeq='+pendingCategorySeq+"&bookMarkFlag="+bookMark+"&qualityCheckFlag="+qualityMark+"&struts.token.name=token&token="+token+"&scorerComment="+encodeURIComponent(scorerComment);
	});
	$("#close").click(function(){
		enableLinksAndButtons();
		if ($('#0').prop("disabled") == false) {
			$("#0").focus();
		}else{
			$("#approve").focus();
		}
	});
//});

/*$(document).ready(function(){
	$("#registerPending").click(function()	{
		var pendingCategorySeq = $("input[name='pendingCategory']:checked").val();
		var bookMark =  $("#bookMark").is(":checked") ? true : false;
		var qualityMark = $("#qualityMark").is(":checked") ? true : false;
		var scorerComment =  $('#scorerComment').val();
			
		location.href = 'registerPending.action?pendingCategorySeq='+pendingCategorySeq+"&bookMarkFlag="+bookMark+"&qualityCheckFlag="+qualityMark+"&scorerComment="+encodeURIComponent(scorerComment);
		
	});		
});*/

function cancelPending(){
	$('#pending').prop('disabled', false);
	//$("#score").focus();
	$('html, body').animate({ scrollTop: 0 }, 0);
}

function confirmPending(){
	
	var isSuccess;
	
	$.ajax({
		type: "POST",
		url: './confirmPending.action',
		async: false,
		dataType:"json",
		cache: false,
		success: function(json, status, request){
			if(json.result.statusCode == 401){
				isSuccess = false;
				location.href = 'scoreAction.action';
			} else {
				isSuccess = true;
			}
		},
		error: function(request, status, error){
			alert(INTERNAL_SERVER_ERROR);
			isSuccess = false;
		}
	});
	
	return isSuccess;
}