//function for enable all links & buttons which are disabled.
function enableLinksAndButtons(){
	$(':button').prop('disabled', false); // Enable all the button
	$('a').unbind('click'); // Enable all the links
}
//function for disable all links & buttons which are enabled.
function disableLinksAndButtons(){
	$(':button').prop('disabled', true); // Disable all the button
	var disableLink = function(){ return false;};
	$('a').bind('click', disableLink); // Disable all the links
}

function onloadScoringScreen(){
	enableLinksAndButtons();
	if ($('#0').prop("disabled") == false) {
		$("#0").focus();
	}else{
		$('#approve').focus();
	}
}
//Our validation script will go here.
$(function () {
	if(($('#score').length>0) || ($('#approve').length>0) || ($('#deny').length>0)){
		$('#popup-wrapper').modalPopLite({ openButton: '#score', approveButton: '#approve', denyButton: '#deny', closeButton: '#cancel', isModal: true, callBack: cancelScoring });
	}
});

$(function () {
	if(($('#pending').length>0)){
		$('#pendingpopup-wrapper').modalPopLite({ openButton: '#pending', closeButton: '#close', isModal: true, callBack: cancelPending });
	}
});



var selectedCheckPoints = [];
var selectedButtonId;

$(document).ready(function() {
	//set default focus on '0' checkpoint on every answer loading.
	
	//disable all links & buttons on scoring screen, while everytime loading new answer.
	disableLinksAndButtons();
	$('#processDetails').click(function(){
		var childWin = window.open('findProcessDetails.action?answerSequence='+$("#answerSeq").val(),'child', "width=720, height=400, location=no, menubar=no, scrollbars=yes, status=no, toolbar=no");
	    if (window.focus) {childWin.focus()}
	});
	
	function checkPointsValidation(){
		$("#myErrorMessages").html('');
		$(".error").html('');
		var checkPointGroupTypeArray = $(':checkbox[name=checkPoint]:checked').map(function(){
			 return $(this).val();
		}).get();
		
		var availableCheckPointsArray = $(':checkbox[name=checkPoint]').map(function(){
			 return $(this).val();
		}).get();
		
		/*if(checkPointGroupTypeArray.length<1){
			$("#myErrorMessages").html(SELECT_ATLEAST_ONE_RECORD);
			return false;
		}*/
		
		//alert( " checkPointGroupTypeArray "+checkPointGroupTypeArray+" availableCheckPointsArray"+availableCheckPointsArray);
		var type0Selected=0;
		var type1Selected=0;
		var type2Selected=0;
		var type3Selected=0;
		var type4Selected=0;
		
		//type 1
		var type1GroupMoreSelectedId= {};
		var groupIdType1={};
		var type1AllSelectedId= {};
		var mustSelectForType1={};
		
		//type 2
		var type2GroupMoreSelectedId= {};
		var groupIdType2={};
		var type2AllSelectedId= {};
		
		//type 3
		var type3GroupMoreSelectedId= {};
		var groupIdType3={};
		var type3AllSelectedId= {};
		var mustSelectForType3={};
		
		//type 4
		var type4GroupMoreSelectedId= {};
		var groupIdType4={};
		var type4AllSelectedId= {};
		var mustSelectForType4={};
		
		for(chkCount=0;chkCount<checkPointGroupTypeArray.length;chkCount++){
			var tempValue=checkPointGroupTypeArray[chkCount].split(":")[2];
			 tempValue=(tempValue!='' && tempValue!=null)?tempValue:'blank';
		  switch(parseInt(checkPointGroupTypeArray[chkCount].split(":")[1])){
		  case 0: type0Selected++;
		           break;
		  case 1 : type1Selected++;
		     if((tempValue) in groupIdType1){
	        	 if(tempValue in type1GroupMoreSelectedId){
	        	 type1GroupMoreSelectedId[tempValue]=type1GroupMoreSelectedId[tempValue]+","+(checkPointGroupTypeArray[chkCount].split(":")[0]);
	        		 }
	        	 else{
	        		 type1GroupMoreSelectedId[tempValue]=(checkPointGroupTypeArray[chkCount].split(":")[0]);			        		
	        	 }
	        	 
	        	 if(tempValue in type1AllSelectedId){
	        		 type1AllSelectedId[tempValue]=type1AllSelectedId[tempValue]+","+(checkPointGroupTypeArray[chkCount].split(":")[0]);
	        	 }
	        	 else{
	        		 type1AllSelectedId[tempValue]=(checkPointGroupTypeArray[chkCount].split(":")[0]);
	        	 }
	         }
	         else{
	        	 groupIdType1[tempValue]=tempValue;
	        	 type1AllSelectedId[tempValue]=(checkPointGroupTypeArray[chkCount].split(":")[0]);
	         }
	         break;
		  case 2:  type2Selected++;			         			        
		         //alert("switch"+type2GroupMoreSelectedId[tempValue]);
		         if((tempValue) in groupIdType2){
		        	 if(tempValue in type2GroupMoreSelectedId){
		        	 type2GroupMoreSelectedId[tempValue]=type2GroupMoreSelectedId[tempValue]+","+(checkPointGroupTypeArray[chkCount].split(":")[0]);
		        	// alert("if"+type2GroupMoreSelectedId[tempValue]+"");			        	 
		        	 }
		        	 else{
		        		 type2GroupMoreSelectedId[tempValue]=(checkPointGroupTypeArray[chkCount].split(":")[0]);			        		
		        	 }
		        	 
		        	 if(tempValue in type2AllSelectedId){
		        		 type2AllSelectedId[tempValue]=type2AllSelectedId[tempValue]+","+(checkPointGroupTypeArray[chkCount].split(":")[0]);
		        	 }
		        	 else{
		        		 type2AllSelectedId[tempValue]=(checkPointGroupTypeArray[chkCount].split(":")[0]);
		        	 }
		         }
		         else{
		        	 groupIdType2[tempValue]=tempValue;
		        	 type2AllSelectedId[tempValue]=(checkPointGroupTypeArray[chkCount].split(":")[0]);
		         }
		         break;
		  case 3:  type3Selected++;
		     if((tempValue) in groupIdType3){
	        	 if(tempValue in type3GroupMoreSelectedId){
	        	 type3GroupMoreSelectedId[tempValue]=type3GroupMoreSelectedId[tempValue]+","+(checkPointGroupTypeArray[chkCount].split(":")[0]);
	        		 }
	        	 else{
	        		 type3GroupMoreSelectedId[tempValue]=(checkPointGroupTypeArray[chkCount].split(":")[0]);			        		
	        	 }
	        	 
	        	 if(tempValue in type3AllSelectedId){
	        		 type3AllSelectedId[tempValue]=type3AllSelectedId[tempValue]+","+(checkPointGroupTypeArray[chkCount].split(":")[0]);
	        	 }
	        	 else{
	        		 type3AllSelectedId[tempValue]=(checkPointGroupTypeArray[chkCount].split(":")[0]);
	        	 }
	         }
	         else{
	        	 groupIdType3[tempValue]=tempValue;
	        	 type3AllSelectedId[tempValue]=(checkPointGroupTypeArray[chkCount].split(":")[0]);
	         }
	         break;
		  case 4:  type4Selected++;
		     if((tempValue) in groupIdType4){
	        	 if(tempValue in type4GroupMoreSelectedId){
	        	 type4GroupMoreSelectedId[tempValue]=type4GroupMoreSelectedId[tempValue]+","+(checkPointGroupTypeArray[chkCount].split(":")[0]);
	        		 }
	        	 else{
	        		 type4GroupMoreSelectedId[tempValue]=(checkPointGroupTypeArray[chkCount].split(":")[0]);			        		
	        	 }
	        	 
	        	 if(tempValue in type4AllSelectedId){
	        		 type4AllSelectedId[tempValue]=type4AllSelectedId[tempValue]+","+(checkPointGroupTypeArray[chkCount].split(":")[0]);
	        	 }
	        	 else{
	        		 type4AllSelectedId[tempValue]=(checkPointGroupTypeArray[chkCount].split(":")[0]);
	        	 }
	         }
	         else{
	        	 groupIdType4[tempValue]=tempValue;
	        	 type4AllSelectedId[tempValue]=(checkPointGroupTypeArray[chkCount].split(":")[0]);
	         }
	         break;
		   }
        }
		
		var totalMsg="";
		//alert("type1Selected"+type1Selected+" type2Selected"+type2Selected+" type3Selected"+type3Selected+" type4Selected"+type4Selected);
		if(type4Selected>0){
			if(type4Selected>0){
				var tempSelected='';
				for (var a in type4AllSelectedId) {
					 if(tempSelected==''){
					  tempSelected=type4AllSelectedId[a];
					 }else{
						 tempSelected=tempSelected+","+type4AllSelectedId[a];
					 }						 
				}
				 if(type0Selected>0 || type1Selected>0 || type2Selected>0 || type3Selected>0 ){
						totalMsg=TYPE_4_SELECTED_THEN_OTHER_TYPE_NOT_SELECTED+"　{ "+tempSelected+"　} "+" <br/>";
					}
				 else if(type4Selected>1){
					
					totalMsg=SELECT_MAX_ONE_FROM+"{　"+tempSelected+"　}"+totalMsg+" <br />";
				}
				else{
				for (var a in type4GroupMoreSelectedId) {
					totalMsg=SELECT_MAX_ONE_FROM+"{　"+type4AllSelectedId[a]+"　}"+totalMsg+" <br />";
					}
				}

			}
			
		}
		else {
			
			if(type2Selected>0){
					for (var a in type2GroupMoreSelectedId) {
						totalMsg=SELECT_MAX_ONE_FROM+"{　"+type2AllSelectedId[a]+"　}"+totalMsg+" <br />";
						}

     		}
			
			if(type3Selected>0){
				for (var a in type3GroupMoreSelectedId) {
					totalMsg=SELECT_MAX_ONE_FROM+"{　"+type3AllSelectedId[a]+"　}"+" <br />";
					}			

 	     	}
			
			for(chkCount=0;chkCount<availableCheckPointsArray.length;chkCount++){
				var tempValue=availableCheckPointsArray[chkCount].split(":")[2];
				tempValue=(tempValue!='' && tempValue!=null)?tempValue:'blank';
				if(parseInt(availableCheckPointsArray[chkCount].split(":")[1])==3){		
					//alert(tempValue+" "+availableCheckPointsArray[chkCount].split(":")[0]);
				if(tempValue in type3AllSelectedId){
					//alert('available');
				}
				else{
					if(tempValue in mustSelectForType3){
						mustSelectForType3[tempValue]=mustSelectForType3[tempValue]+","+availableCheckPointsArray[chkCount].split(":")[0];
					}
					else{
						mustSelectForType3[tempValue]=availableCheckPointsArray[chkCount].split(":")[0];
					}
				}
				}
				if(parseInt(availableCheckPointsArray[chkCount].split(":")[1])==1){
					if(tempValue in type1AllSelectedId){
						//alert('available');
					}
					else{
						if(tempValue in mustSelectForType1){
							mustSelectForType1[tempValue]=mustSelectForType1[tempValue]+","+availableCheckPointsArray[chkCount].split(":")[0];
						}
						else{
							mustSelectForType1[tempValue]=availableCheckPointsArray[chkCount].split(":")[0];
						}
					}
				}
				if(parseInt(availableCheckPointsArray[chkCount].split(":")[1])==4){
					if(tempValue in type4AllSelectedId){
						//alert('available');
					}
					else{
						if(tempValue in mustSelectForType4){
							mustSelectForType4[tempValue]=mustSelectForType4[tempValue]+","+availableCheckPointsArray[chkCount].split(":")[0];
						}
						else{
							mustSelectForType4[tempValue]=availableCheckPointsArray[chkCount].split(":")[0];
						}
					}
				}
			}	 
			
			
			var i=0;
			var chkMultipleGrp=0;
			var mandetoryCheckMessage='';
			
			//TYPE 1 Must Select
			for (var a in mustSelectForType1) {
				if(mandetoryCheckMessage!=''){
					mandetoryCheckMessage=mandetoryCheckMessage+" , "
				}
				mandetoryCheckMessage=mandetoryCheckMessage+" { "+mustSelectForType1[a]+" } ";
				i++;
				chkMultipleGrp++;
			}	
			

					
			
			//TYPE 3 Must Select
			i=0;
			for (var a in mustSelectForType3) {
				if(mandetoryCheckMessage!=''){
					mandetoryCheckMessage=mandetoryCheckMessage+" , "
				}
				mandetoryCheckMessage=mandetoryCheckMessage+" { "+mustSelectForType3[a]+" } ";
				i++;
				chkMultipleGrp++;
			}	
			
			if(mandetoryCheckMessage!='' && chkMultipleGrp>1){
					mandetoryCheckMessage=" "+mandetoryCheckMessage
			}
			
			//TYPE 4 Must Select
			i=0;
			var type4MandetoryCheckMessage='';
			 if(type0Selected<1 && type1Selected<1 && type2Selected<1 && type3Selected<1 ){
			     for (var a in mustSelectForType4) {
			    	  if( i==0){
			    		  type4MandetoryCheckMessage=mustSelectForType4[a];
			    	  }else{
			    		  type4MandetoryCheckMessage=type4MandetoryCheckMessage+","+mustSelectForType4[a];
			    	  }		    		
			    		
			    	 i++;
			   }
			 }
			 
			 if(type4MandetoryCheckMessage!=''){
				 if(mandetoryCheckMessage!=''){
		    		 mandetoryCheckMessage=mandetoryCheckMessage+ "  "+",";
		    	 }
				 mandetoryCheckMessage=mandetoryCheckMessage+" { "+type4MandetoryCheckMessage+" } ";
			 }
			 
			 if(mandetoryCheckMessage!=''){
					mandetoryCheckMessage=SELECT_SINGLE_RECORD_FROM_THEM+mandetoryCheckMessage+" <br />";
					 totalMsg=mandetoryCheckMessage;
				}
			
			
		}
		
		$("#myErrorMessages").html(totalMsg);
		if(totalMsg!=''){
			return false;
		}
		else{
			if(checkPointGroupTypeArray.length<1){
				$("#myErrorMessages").html(SELECT_ATLEAST_ONE_RECORD);
				return false;
			}else {
			 return true;
			}
		}
	}
	
	
		var availableCheckPoints = [];
		var groupType0= [];
		var groupType1= [];
		var groupType2= [];
		var groupType3= [];
		var groupType4= [];
		var availableGroupType0= [];
		var availableGroupType1= [];
		var availableGroupType2= [];
		var availableGroupType3= [];
		var availableGroupType4= [];
		var message = "";
		var messageFunc = function () { return message; };
		/*if($("#score").length > 0){
			$("#score").focus();
		}else*/ if($("#approve").length > 0){
			$("#approve").focus();
		}/*else if($("#deny").length > 0){
			$("#deny").focus();
		}*/
		
		if($("#next").length > 0){
			$("#next").focus();
		}else if($("#prev").length > 0){
			$("#prev").focus();
		}
		
		$('html, body').animate({ scrollTop: 0 }, 0);
		
		
		
		function buildGroupTypeArray(){
			var checkPointGroupTypeArray = $(':checkbox[name=checkPoint]:checked').map(function(){
				 return $(this).val();
			}).get();
			
			var availableCheckPointsArray = $(':checkbox[name=checkPoint]').map(function(){
				 return $(this).val();
			}).get();
			
			if(($('#hiddenCheckPointArray').length>0) && ($('#hiddenSelectedCheckPointArray').length>0)){
				var hiddenCheckPointArray = [];
				var hiddenSelectedCheckPointArray= [];
				if($('#hiddenCheckPointArray').length>0){
					hiddenCheckPointArray=$('[name=hiddenCheckPointArray]').val();
					hiddenCheckPointArray=hiddenCheckPointArray.replace(/[\[\]\ ]+/g,'');
					hiddenCheckPointArray=hiddenCheckPointArray.split(",");
					availableCheckPointsArray=hiddenCheckPointArray;
				}
				if($('#hiddenSelectedCheckPointArray').length>0){
					hiddenSelectedCheckPointArray=$('[name=hiddenSelectedCheckPointArray]').val();
					hiddenSelectedCheckPointArray=hiddenSelectedCheckPointArray.replace(/[\[\]\ ]+/g,'');
					hiddenSelectedCheckPointArray=hiddenSelectedCheckPointArray.split(",");
				}
				
				var tempCheckPointGroupTypeArray= [];
				for (var i = 0; i <hiddenSelectedCheckPointArray.length ; i++) {
					var selecteCheckPoint = hiddenSelectedCheckPointArray[i];
					for (var j = 0; j <availableCheckPointsArray.length ; j++) {
						var availableCheckPointArray = availableCheckPointsArray[j].split(":");
						if(availableCheckPointArray[0] == selecteCheckPoint){
							tempCheckPointGroupTypeArray.push(availableCheckPointArray[0]+":"+availableCheckPointArray[1]);
						}
					}
				}
				
				if(tempCheckPointGroupTypeArray.length > 0){
					checkPointGroupTypeArray = tempCheckPointGroupTypeArray;
				}
			}
			
			for (var i = 0; i <availableCheckPointsArray.length ; i++) {
				var availableCheckPointArray = availableCheckPointsArray[i].split(":");
				
				if(availableCheckPointArray[1]==4)
					availableGroupType4.push(availableCheckPointArray[0]);
				else if(availableCheckPointArray[1]==0)
					availableGroupType0.push(availableCheckPointArray[0]);
				else if(availableCheckPointArray[1]==1)
					availableGroupType1.push(availableCheckPointArray[0]);
				else if(availableCheckPointArray[1]==2)
					availableGroupType2.push(availableCheckPointArray[0]);
				else if(availableCheckPointArray[1]==3)
					availableGroupType3.push(availableCheckPointArray[0]);
				
				availableCheckPoints.push(availableCheckPointArray[1]);
			}
			
			for (var i = 0; i <checkPointGroupTypeArray.length ; i++) {
				var checkPointArray = checkPointGroupTypeArray[i].split(":");
				
				if(checkPointArray[1]==4)
					groupType4.push(checkPointArray[0]);
				else if(checkPointArray[1]==0)
					groupType0.push(checkPointArray[0]);
				else if(checkPointArray[1]==1)
					groupType1.push(checkPointArray[0]);
				else if(checkPointArray[1]==2)
					groupType2.push(checkPointArray[0]);
				else if(checkPointArray[1]==3)
					groupType3.push(checkPointArray[0]);
				
				selectedCheckPoints.push(checkPointArray[0]);
			}
		}
		
		function resetValues(){
			availableCheckPoints=[];
			groupType0= [];
			groupType1= [];
			groupType2= [];
			groupType3= [];
			groupType4= [];
			availableGroupType0= [];
			availableGroupType1= [];
			availableGroupType2= [];
			availableGroupType3= [];
			availableGroupType4= [];
		}
		
		
		/*$.validator.addMethod("validateGroupType4", function validateGroupType4(value, element) {
			var retValue;
			
			if(selectedButtonId=='pending')
				return true;
			
			buildGroupTypeArray();
			
			if(selectedCheckPoints.length > 0) {
				
				if(!(groupType4.length>=1 && (groupType0.length>=1 || groupType1.length>=1
						|| groupType2.length>=1 || groupType3.length>=1))){
					if(groupType4.length<=1){
						return true;
					}else{
						message = "{"+availableGroupType4+"} の中から、一つのチェックポイントだけ選択してください。";
						retValue = false;
					}
				}else{
					message = "{"+availableGroupType4+"} を選択されている場合、他のチェックポイントを選択してはいけません。";
					retValue = false;
				}
			} else {
				message = "採点する時、少なくとも一つのチェックポイントを選択してください。";
				retValue = false;
			}
			
			if(retValue == false)
				 resetValues();
			
			return retValue;
		}, messageFunc);
		
		
		$.validator.addMethod("validateGroupType1", function validateGroupType1(value, element) {
			var retValue=true;
			
			if(selectedButtonId=='pending')
				return retValue;
			
			if(groupType4.length==0 && $.inArray("1", availableCheckPoints)!=-1) {
				if(groupType1.length <1) {
					
					if(availableGroupType4.length>=1 && groupType4.length==0) {
			        	message="{"+availableGroupType4+"}又は";
			        }
					if(availableGroupType3.length>=1 && groupType3.length==0) {
				        message+="{"+availableGroupType3+"}と";
				    }
				    
			        message += "{"+availableGroupType1+"} の中からもチェックポイントを選択してください。 ";
			       
					retValue = false;
				}
			}
			
			if(retValue == false)
				 resetValues();
			
			return retValue;
		}, messageFunc);
		
		$.validator.addMethod("validateGroupType3", function validateGroupType3(value, element) {
			var retValue=true;
			
			if(selectedButtonId=='pending')
				return retValue;
			
			if(groupType4.length ==0 && groupType3.length ==0 && $.inArray("3", availableCheckPoints)!=-1) {
						
					if(availableGroupType4.length>=1 && groupType4.length==0) {
			        	message="{"+availableGroupType4+"}又は";
			        }
					if(availableGroupType1.length>=1 && groupType1.length==0) {
			        	message+="{"+availableGroupType1+"}と";
			        }
			        
					message += "{"+availableGroupType3+"} の中からもチェックポイントを選択してください。 ";
					
			        retValue=false;
				}else if(groupType3.length > 1){
					message = "{"+availableGroupType3+"} の中から、一つのチェックポイントだけ選択してください。 ";
					retValue=false;
				}				
			
			if(retValue == false)
				 resetValues();
			
			return retValue;
		}, messageFunc);
		
		$.validator.addMethod("validateGroupType2", function validateGroupType2(value, element) {
			var retValue=true;
			
			if(selectedButtonId=='pending')
				return retValue;
			
			if(groupType4.length==0 && $.inArray("2", availableCheckPoints)!=-1) {
				if(groupType2.length > 1) {
					message = "{"+availableGroupType2+"} の中から、一つのチェックポイントだけ選択してください。 ";
			        retValue=false;
				}
			}
				
			if(retValue == false)
				 resetValues();
			 
			return retValue;
			
		}, messageFunc);*/
		
		$.validator.addMethod("validateCheckPointLength", function validateCheckPointLength(value, element) {
			var retValue;
			$("#myErrorMessages").html('');
			if(selectedButtonId=='score' || selectedButtonId=='approve' || selectedButtonId=='deny' || selectedButtonId=='pending')
				retValue=true;
			
			if(!retValue && $(':checkbox[name=checkPoint]:checked').length > 0) {
				message = CHECKBOX_NOT_SELECT_FOR_PENDING;
				retValue=false;
			} else {
				retValue=true;
			}
				
			resetValues();
			
			return retValue;
			
		}, messageFunc);
		
/*		$(".scoring_big_checkbox").change(function(){
			checkPointsValidation()
		});
*/		
		$("#score").click(function(){
			selectedCheckPoints = [];
			buildGroupTypeArray();
			checkPointsValidation();
		if(!checkPointsValidation()){
				return false;
			}
			else{
				return true;
		}
		});
		
		$("#approve").click(function(){
			buildGroupTypeArray();
		if(!checkPointsValidation()){
				return false;
			}
			else{
				return true;
		}
		});
		
		
		$("#scoreActionForm").validate({
	    
			rules: {	
				 "checkpointerror" : {
//						validateGroupType4:true,
//						validateGroupType1:true,
//						validateGroupType3:true,
//						validateGroupType2:true,
						validateCheckPointLength:true
	    			}
		    }  
		});
		
		$("#pending").click(function() {
			var pendingCategorySeq = $('#pendingCategorySeq').val();
			
			if(pendingCategorySeq != '') {
				$('input:radio[name=pendingCategory]').filter('[value='+pendingCategorySeq+']').attr('checked', true);
			} else {
				$("input:radio[name=pendingCategory]:first").attr('checked', true);
			}
		});
		
		$("#deny").click(function() {
			buildGroupTypeArray();
			
var 		denyCategorySeq = $('#denyCategorySeq').val();
			
			if(denyCategorySeq != '') {
				$('input:radio[name=denyCategory]').filter('[value='+denyCategorySeq+']').attr('checked', true);
			} else {
				$("input:radio[name=denyCategory]:first").attr('checked', true);
			}
		});
});

function stopAudio(){
	try{
		document.getElementById('myTune').pause();
		document.getElementById('myTune').currentTime=0;
	}
	catch(e){}
}
function uniKeyCode(event) {
	var key = event.keyCode;
	var id = document.getElementById(arr[key]);
	if(id != null && id.checked == true){
		id.checked = false;
	}else if(id != null && id.checked == false){
		id.checked = true;
	}
	
}


function doMarkUnmark(){
	
	var markUnamrk = $('#markUnamrk').text();
	if(markUnamrk == MARK_lOOK_AFTERWARDS){
		doMark();
		$('#markUnamrk').text(UNMARK_LOOK_AFTERWARDS);
	}else if(markUnamrk == UNMARK_LOOK_AFTERWARDS){
		doUnmark();
		$('#markUnamrk').text(MARK_lOOK_AFTERWARDS);
	}
}

function doMark(){
	
	var isSuccess;
	var requestData = {};
	requestData["markComment"] = $('#markComment').val();
	
	$.ajax({
		type: "POST",
		url: './markLookAfterwords.action',
		async: false,
		dataType:"json",
		cache: false,
		data: $.param(requestData),
		success: function(json, status, request){
				isSuccess = true;
		},
		error: function(request, status, error){
			alert(INTERNAL_SERVER_ERROR);
			isSuccess = false;
		}
	});
	
	return isSuccess;
}

function doUnmark(){
	var isSuccess;
	var requestData = {};
	requestData["markComment"] = $('#markComment').val();
	
	$.ajax({
		type: "POST",
		url: './unmarkLookAfterwards.action',
		async: false,
		dataType:"json",
		cache: false,
		data: $.param(requestData),
		success: function(json, status, request){
				isSuccess = true;
		},
		error: function(request, status, error){
			alert(INTERNAL_SERVER_ERROR);
			isSuccess = false;
		}
	});
	
	return isSuccess;
}

function unmarkAll(){
	var isSuccess;
	var requestData = {};
	$.ajax({
		type: "POST",
		url: './unmarkAllLookAfterwards.action',
		async: false,
		dataType:"json",
		cache: false,
		data: $.param(requestData),
		success: function(json, status, request){
				isSuccess = true;
				$("#unmarkAll").removeAttr('onclick');
				$("#unmarkAll").addClass("btn btn-disabled");
		},
		error: function(request, status, error){
			alert(INTERNAL_SERVER_ERROR);
			isSuccess = false;
		}
	});
	
	return isSuccess;
}

function doKenshuMarkUnmark(){
	
	var markUnamrk = $('#kenshumarkUnamrk').text();
	if(markUnamrk == MARK_lOOK_AFTERWARDS){
		doKenshuMark();
		$("#kenshuComment").attr("disabled", true);
		$('#kenshumarkUnamrk').text(UNMARK_LOOK_AFTERWARDS);
	}else if(markUnamrk == UNMARK_LOOK_AFTERWARDS){
		$("#kenshuComment").val("");
		doKenshuUnmark();
		$("#kenshuComment").attr("disabled", false);
		$('#kenshumarkUnamrk').text(MARK_lOOK_AFTERWARDS);
	}
}

function doKenshuMark(){
	
	var isSuccess;
	var requestData = {};
	requestData["markComment"] = $('#kenshuComment').val();
	
	$.ajax({
		type: "POST",
		url: './markKenshuRecord.action',
		async: false,
		dataType:"json",
		cache: false,
		data: $.param(requestData),
		success: function(json, status, request){
				isSuccess = true;
		},
		error: function(request, status, error){
			alert(INTERNAL_SERVER_ERROR);
			isSuccess = false;
		}
	});
	
	return isSuccess;
}

function doKenshuUnmark(){
	var isSuccess;
	var requestData = {};
	requestData["markComment"] = $('#kenshuComment').val();
	
	$.ajax({
		type: "POST",
		url: './unmarkKenshuRecord.action',
		async: false,
		dataType:"json",
		cache: false,
		data: $.param(requestData),
		success: function(json, status, request){
				isSuccess = true;
		},
		error: function(request, status, error){
			alert(INTERNAL_SERVER_ERROR);
			isSuccess = false;
		}
	});
	
	return isSuccess;
}

/*function unmarkAll(){
	var isSuccess;
	var requestData = {};
	$.ajax({
		type: "POST",
		url: './unmarkAllLookAfterwards.action',
		async: false,
		dataType:"json",
		cache: false,
		data: $.param(requestData),
		success: function(json, status, request){
				isSuccess = true;
				$("#unmarkAll").removeAttr('onclick');
				$("#unmarkAll").addClass("btn btn-disabled");
		},
		error: function(request, status, error){
			alert(INTERNAL_SERVER_ERROR);
			isSuccess = false;
		}
	});
	
	return isSuccess;
}*/

function checkUncheckFlag () {
	var atLeastOneIsChecked = $('input[name="bookMark"]:checked').length > 0;
	if(atLeastOneIsChecked == true) {
		setExplainFlag();
		$("#kenshumarkUnamrk").attr("class", "btn btn-disabled");
	}else {
		clearExplainFlag ();
		$("#kenshumarkUnamrk").attr("class", "btn btn-primary btn-scoring-sm");
	}
}

function setExplainFlag () {
	var isSuccess;
	var requestData = {};
	requestData["markComment"] = $('#kenshuComment').val();
	
	$.ajax({
		type: "POST",
		url: './setExplainFlag.action',
		async: false,
		dataType:"json",
		cache: false,
		data: $.param(requestData),
		success: function(json, status, request){
				isSuccess = true;
		},
		error: function(request, status, error){
			alert(INTERNAL_SERVER_ERROR);
			isSuccess = false;
		}
	});
	
	return isSuccess;
}

function clearExplainFlag () {
	var isSuccess;
	var requestData = {};
	requestData["markComment"] = $('#kenshuComment').val();
	
	$.ajax({
		type: "POST",
		url: './clearExplainFlag.action',
		async: false,
		dataType:"json",
		cache: false,
		data: $.param(requestData),
		success: function(json, status, request){
				isSuccess = true;
		},
		error: function(request, status, error){
			alert(INTERNAL_SERVER_ERROR);
			isSuccess = false;
		}
	});
	
	return isSuccess;
}