
//Our validation script will go here.
$(document).ready(function(){
	$("#registerDeny").click(function(){
		$("#registerDeny").prop('disabled', true);
		$('#close').trigger('click');
	});
	$("#close").click(function(){
		$("#0").focus();
	});
});

$(document).ready(function(){
	$("#registerDeny").click(function()	{
		var denyCategorySeq = $("input[name='denyCategory']:checked").val();
		var bookMark =  $("#bookMark").is(":checked") ? true : false;
		var qualityMark = $("#qualityMark").is(":checked") ? true : false;
		var scorerComment =  $('#scorerComment').val();
			
		/*location.href = 'registerPending.action?pendingCategorySeq='+pendingCategorySeq+"&bookMarkFlag="+bookMark+"&qualityCheckFlag="+qualityMark+"&scorerComment="+encodeURIComponent(scorerComment);*/
		location.href = 'registerDeny.action?denyCategorySeq='+denyCategorySeq+"&bookMarkFlag="+bookMark+"&qualityCheckFlag="+qualityMark+"&scorerComment="+encodeURIComponent(scorerComment);
	});		
});

function cancelDeny(){
	$('#deny').prop('disabled', false);
	//$("#score").focus();
	$('html, body').animate({ scrollTop: 0 }, 0);
}

function confirmDeny(){
	var requestData = {};   
	requestData["checkPoint"] = selectedCheckPoints+"";
	var isSuccess;
	
	$.ajax({
		type: "POST",
		url: './confirmDeny.action',
		async: false,
		dataType:"json",
		cache: false,
		data: $.param(requestData),
		success: function(json, status, request){
			if(json.result.statusCode == 401){
				isSuccess = false;
				location.href = 'scoreAction.action';
			} else {
				$("#gradeNum1").text(json.result.gradeNum);
				$("#result1").text(json.result.gradeResult);
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