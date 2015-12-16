
//Our validation script will go here.
$(document).ready(function(){
	$("#registerScore").click(function(){
		$("#registerScore").prop('disabled', true);
		$('#cancel').trigger('click');
	});
	
	$("#cancel").click(function(){
		$("#0").focus();
	});
	
	/*$('#registerScore').keypress(function(e) {
		  if(e.which == 13) {
			  e.preventDefault();
		  } else if(e.which == 32) {
			  $('#registerScore').trigger('click');
		  } else {
			  return false;
		  }
	});*/
});


function cancelScoring(){
	if($("#score").length >0){
		$('#score').prop('disabled', false);
	} 
	if($("#approve").length >0){
		$('#approve').prop('disabled', false);
	}
	if($("#deny").length >0){
		$('#deny').prop('disabled', false);
	}
	
	/*if($("#score").length >0){
		$("#score").focus();
	}else*/ if($("#approve").length >0){
		$("#approve").focus();
	}
	$('html, body').animate({ scrollTop: 0 }, 0);
}

function confirmScore(){
	var requestData = {};   
	requestData["checkPoint"] = selectedCheckPoints+"";
	requestData["bookMarkFlag"] =  $("#bookMark").is(":checked") ? true : false;
	requestData["qualityCheckFlag"] =  $("#qualityMark").is(":checked") ? true : false;
	requestData["scorerComment"] =  $('#scorerComment').val();
	requestData["approveOrDeny"] = $('#approveOrDeny').val();
	var isSuccess;
	
	$.ajax({
		type: "POST",
		url: './confirmScore.action',
		async: false,
		dataType:"json",
		cache: false,
		data: $.param(requestData),
		success: function(json, status, request){
			if(json.result.statusCode == 401){
				isSuccess = false;
				location.href = 'scoreAction.action';
			} else {
				$("#gradeNum").text(json.result.gradeNum);
				$("#result").text(json.result.gradeResult);
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