 
function changeCheckBox(name, check) {
  var elements = document.getElementsByName(name);
  for(i = 0; i < elements.length; i++) {
    document.getElementsByName(name).item(i).checked = check;
  }
}
function changeCategoryType(checkDisable, textDisable) {
	var elements = document.getElementsByName('scoreInputInfo.scoreCurrentInfo.currentGradeNum');
	for(i = 0; i < elements.length; i++) {
		elements.item(i).disabled = checkDisable;
	}
	
	var currentPendingCategory = document.getElementsByName("scoreInputInfo.scoreCurrentInfo.currentPendingCategory");
	var currentIncludeCheckPoints = document.getElementsByName('scoreInputInfo.scoreCurrentInfo.currentIncludeCheckPoints');
	var currentExcludeCheckPoints = document.getElementsByName('scoreInputInfo.scoreCurrentInfo.currentExcludeCheckPoints');
	var currentDenyCategory = document.getElementsByName("scoreInputInfo.scoreCurrentInfo.currentDenyCategory");
	var radio = document.getElementsByName('scoreInputInfo.scoreCurrentInfo.currentCategoryType');
	
	if (radio[4].checked) {
		currentDenyCategory.item(0).disabled = textDisable;
		currentDenyCategory.item(0).className = "";
		currentPendingCategory.item(0).disabled = !textDisable;
		currentIncludeCheckPoints.item(0).disabled = textDisable;
		currentExcludeCheckPoints.item(0).disabled = textDisable;
		currentPendingCategory.item(0).className = "disable_bg";
		currentIncludeCheckPoints.item(0).className = "";
		currentExcludeCheckPoints.item(0).className = "";
		
	}else {
		currentPendingCategory.item(0).disabled = textDisable;
		currentIncludeCheckPoints.item(0).disabled = !textDisable;
		currentExcludeCheckPoints.item(0).disabled = !textDisable;
		currentDenyCategory.item(0).disabled = true;
		currentDenyCategory.item(0).className = "disable_bg";
		
		if (textDisable) {
			currentPendingCategory.item(0).className = "disable_bg";
			currentIncludeCheckPoints.item(0).className = "";
			currentExcludeCheckPoints.item(0).className = "";
		} else {
			currentPendingCategory.item(0).className = "";
			currentIncludeCheckPoints.item(0).className = "disable_bg";
			currentExcludeCheckPoints.item(0).className = "disable_bg";
		}
	}
	
	
	
	for(i=0; i < document.getElementsByName('currentGradeCheckButton').length; i++) {
		document.getElementsByName('currentGradeCheckButton')[i].disabled = checkDisable;
	}
	
	changeCurrentBlock(checkDisable, textDisable, false);
	
	var radio = document.getElementsByName('scoreInputInfo.scoreCurrentInfo.currentCategoryType');
	if(!(radio[2].checked)){
		$("#selectCurrentGrade").val("");
	}
}

function changeCurrentBlock(checkDisable, textDisable, currentBlockDisable) {
	var currentPendingCategory = document.getElementsByName('scoreInputInfo.scoreCurrentInfo.currentPendingCategory');
	var currentIncludeCheckPoints = document.getElementsByName('scoreInputInfo.scoreCurrentInfo.currentIncludeCheckPoints');
	var currentExcludeCheckPoints = document.getElementsByName('scoreInputInfo.scoreCurrentInfo.currentExcludeCheckPoints');
	var radio = document.getElementsByName('scoreInputInfo.scoreCurrentInfo.currentCategoryType');
	var currentBlock = document.getElementsByName('scoreInputInfo.scoreCurrentInfo.currentBlock');
	var currentDenyCategory = document.getElementsByName("scoreInputInfo.scoreCurrentInfo.currentDenyCategory");
	
	if (!(radio[3].checked)) {
		currentPendingCategory.item(0).value='';
	}

	if((radio[3].checked) || !currentBlock[0].checked){
		currentIncludeCheckPoints.item(0).value='';
		currentExcludeCheckPoints.item(0).value='';
	}
	
	if (!(radio[4].checked)) {
		currentDenyCategory.item(0).value='';
	}

	if((radio[4].checked) || !currentBlock[0].checked){
		currentIncludeCheckPoints.item(0).value='';
		currentExcludeCheckPoints.item(0).value='';
	}
	
	if(checkDisable){
		changeCheckBox('scoreInputInfo.scoreCurrentInfo.currentGradeNum',!checkDisable);
	}
	
	if(currentBlockDisable){
		document.getElementById("currentScorerId1").value = '';
		document.getElementById("currentScorerId2").value = '';
		document.getElementById("currentScorerId3").value = '';
		document.getElementById("currentScorerId4").value = '';
		document.getElementById("currentScorerId5").value = '';
		document.getElementById("punchText").value = '';
		
		changeCheckBox('scoreInputInfo.scoreCurrentInfo.currentStateList',!currentBlockDisable);
		
	    var now = new Date();
	    now.setDate(now.getDate() - 1);
	    
	    $("#currentUpdateDateStartYear").val(now.getFullYear());
	    $("#currentUpdateDateStartMonth").val(now.getMonth() + 1);
		$("#currentUpdateDateStartDay").val(now.getDate());
		$("#currentUpdateDateStartHours").val(0);
		$("#currentUpdateDateStartMin").val(0);
		
		$("#currentUpdateDateEndYear").val(now.getFullYear());
	    $("#currentUpdateDateEndMonth").val(now.getMonth() + 1);
		$("#currentUpdateDateEndDay").val(now.getDate());
		$("#currentUpdateDateEndHours").val(23);
		$("#currentUpdateDateEndMin").val(59);
	}
}

function changeHistoryCategoryType(checkDisable, textDisable) {
	var elements = document.getElementsByName('scoreInputInfo.scoreHistoryInfo.historyGradeNum');
	for(i = 0; i < elements.length; i++) {
		elements.item(i).disabled = checkDisable;
	}
	
	var historyPendingCategory = document.getElementsByName('scoreInputInfo.scoreHistoryInfo.historyPendingCategory');
	var historyIncludeCheckPoints = document.getElementsByName('scoreInputInfo.scoreHistoryInfo.historyIncludeCheckPoints');
	var historyExcludeCheckPoints = document.getElementsByName('scoreInputInfo.scoreHistoryInfo.historyExcludeCheckPoints');
	var historyDenyCategory = document.getElementsByName('scoreInputInfo.scoreHistoryInfo.historyDenyCategory');
	var radio = document.getElementsByName('scoreInputInfo.scoreHistoryInfo.historyCategoryType');
	
	if (radio[4].checked) {
		historyDenyCategory.item(0).disabled = textDisable;
		historyDenyCategory.item(0).className = "";
		historyPendingCategory.item(0).disabled = !textDisable;
		historyIncludeCheckPoints.item(0).disabled = textDisable;
		historyExcludeCheckPoints.item(0).disabled = textDisable;
		historyPendingCategory.item(0).className = "disable_bg";
		historyIncludeCheckPoints.item(0).className = "";
		historyExcludeCheckPoints.item(0).className = "";
	} else {
		historyPendingCategory.item(0).disabled = textDisable;
		historyIncludeCheckPoints.item(0).disabled = !textDisable;
		historyExcludeCheckPoints.item(0).disabled = !textDisable;
		historyDenyCategory.item(0).disabled = true;
		historyDenyCategory.item(0).className = "disable_bg";
		if (textDisable) {
			historyPendingCategory.item(0).className = "disable_bg";
			
			historyIncludeCheckPoints.item(0).className = "";
			historyExcludeCheckPoints.item(0).className = "";
		} else {
			historyPendingCategory.item(0).className = "";
			
			historyIncludeCheckPoints.item(0).className = "disable_bg";
			historyExcludeCheckPoints.item(0).className = "disable_bg";
		}
	}
	
	
	for(i=0; i < document.getElementsByName('processCheckButton').length; i++) {
		document.getElementsByName('processCheckButton')[i].disabled = checkDisable;
	}
	
	changeHistoryBlock(checkDisable, textDisable, false);
	
	var radio = document.getElementsByName('scoreInputInfo.scoreHistoryInfo.historyCategoryType');
	if(!(radio[2].checked)){
		$("#selectHistoryGrade").val("");
	}
}

function changeHistoryBlock(checkDisable, textDisable, historyBlockDisable) {
	var historyPendingCategory = document.getElementsByName('scoreInputInfo.scoreHistoryInfo.historyPendingCategory');
	var historyIncludeCheckPoints = document.getElementsByName('scoreInputInfo.scoreHistoryInfo.historyIncludeCheckPoints');
	var historyExcludeCheckPoints = document.getElementsByName('scoreInputInfo.scoreHistoryInfo.historyExcludeCheckPoints');
	var radio = document.getElementsByName('scoreInputInfo.scoreHistoryInfo.historyCategoryType');
	var historyBlock = document.getElementsByName('scoreInputInfo.scoreHistoryInfo.historyBlock');
	var historyDenyCategory = document.getElementsByName('scoreInputInfo.scoreHistoryInfo.historyDenyCategory');
	
	if (!(radio[3].checked)) {
		historyPendingCategory.item(0).value='';
	}
	
	if((radio[3].checked || !historyBlock[0].checked)){
		historyIncludeCheckPoints.item(0).value='';
		historyExcludeCheckPoints.item(0).value='';
	}
	
	if (!(radio[4].checked)) {
		historyDenyCategory.item(0).value='';
	}
	
	if((radio[4].checked || !historyBlock[0].checked)){
		historyIncludeCheckPoints.item(0).value='';
		historyExcludeCheckPoints.item(0).value='';
	}
	
	if(checkDisable){
		changeCheckBox('scoreInputInfo.scoreHistoryInfo.historyGradeNum',!checkDisable);
	}
	
	if(historyBlockDisable){
		document.getElementById("historyScorerId1").value = '';
		document.getElementById("historyScorerId2").value = '';
		document.getElementById("historyScorerId3").value = '';
		document.getElementById("historyScorerId4").value = '';
		document.getElementById("historyScorerId5").value = '';
		
		changeCheckBox('scoreInputInfo.scoreHistoryInfo.historyEventList',!historyBlockDisable);
		
		var now = new Date();
		now.setDate(now.getDate() - 1);
		    
		
		$("#historyUpdateDateStartYear").val(now.getFullYear());
		$("#historyUpdateDateStartMonth").val(now.getMonth() + 1);
		$("#historyUpdateDateStartDay").val(now.getDate());
		$("#historyUpdateDateStartHours").val(0);
		$("#historyUpdateDateStartMin").val(0);
			
		$("#historyUpdateDateEndYear").val(now.getFullYear());
		$("#historyUpdateDateEndMonth").val(now.getMonth() + 1);
		$("#historyUpdateDateEndDay").val(now.getDate());
		$("#historyUpdateDateEndHours").val(23);
		$("#historyUpdateDateEndMin").val(59);
	}
}

function defaultValidation() {
	var radio = document.getElementsByName('scoreInputInfo.scoreCurrentInfo.currentCategoryType');
	if (radio[2].checked) {
		changeCategoryType(false, true);
	} else if (radio[3].checked) {
		changeCategoryType(true, false);
	} else if (radio[4].checked) {
		changeCategoryType(true, false);
	}else {
		changeCategoryType(true, true);
	}
}

function defaultValidationPast() {
	var radio = document.getElementsByName('scoreInputInfo.scoreHistoryInfo.historyCategoryType');
	if (radio[2].checked) {
		changeHistoryCategoryType(false, true);
	} else if (radio[3].checked) {
		changeHistoryCategoryType(true, false);
	} else if (radio[4].checked) {
		changeHistoryCategoryType(true, false);
	} else {
		changeHistoryCategoryType(true, true);
	}
}

function toggleStateSearch() {
	var isDisable = !document.getElementById("currentBlock").checked;
	var form = document.getElementById("scoreSearchForm");
	var bgClass = "";
	if (isDisable) {
		bgClass = "disable_bg"
	}
	
	form.currentScorerId1.disabled = isDisable;
	form.currentScorerId1.className = bgClass;
	form.currentScorerId2.disabled = isDisable;
	form.currentScorerId2.className = bgClass;
	form.currentScorerId3.disabled = isDisable;
	form.currentScorerId3.className = bgClass;
	form.currentScorerId4.disabled = isDisable;
	form.currentScorerId4.className = bgClass;
	form.currentScorerId5.disabled = isDisable;
	form.currentScorerId5.className = bgClass;
	
	if(search_by_scorer_role_id == 'true'){
		form.currentScorerRole1.disabled = isDisable;
		form.currentScorerRole1.className = bgClass;
		form.currentScorerRole2.disabled = isDisable;
		form.currentScorerRole2.className = bgClass;
		form.currentScorerRole3.disabled = isDisable;
		form.currentScorerRole3.className = bgClass;
		form.currentScorerRole4.disabled = isDisable;
		form.currentScorerRole4.className = bgClass;
		if(isDisable){
			form.currentScorerRole1.checked = !isDisable;
			form.currentScorerRole2.checked = !isDisable;
			form.currentScorerRole3.checked = !isDisable;
			form.currentScorerRole4.checked = !isDisable;
		}
	}
	
	form.punchText.disabled = isDisable;
	form.punchText.className = bgClass;
	
	if(form.currentQualityCheckFlag!=null){
	 form.currentQualityCheckFlag.disabled = isDisable;
	}

	if(isDisable){
		document.getElementById('currentCategoryType1').checked=true;
		if(document.getElementById('currentQualityCheckFlag')!=null){
		 document.getElementById('currentQualityCheckFlag').checked=false;
		}
	}
	
	if(form.lookAfterwardsFlag!=null){
		 form.lookAfterwardsFlag.disabled = isDisable;
	}
	
	if(isDisable){
		document.getElementById('currentCategoryType1').checked=true;
		if(document.getElementById('lookAfterwardsFlag')!=null){
		 document.getElementById('lookAfterwardsFlag').checked=false;
		}
	}
	
	for(i=0; i < document.getElementsByName("scoreInputInfo.scoreCurrentInfo.currentCategoryType").length; i++) {
		document.getElementsByName("scoreInputInfo.scoreCurrentInfo.currentCategoryType")[i].disabled = isDisable;
	}
	for(i=0; i < document.getElementsByName("scoreInputInfo.scoreCurrentInfo.currentGradeNum").length; i++) {
		document.getElementsByName("scoreInputInfo.scoreCurrentInfo.currentGradeNum")[i].disabled = isDisable;
	}
	
	form.currentPendingCategory.disabled = isDisable;
	form.currentPendingCategory.className = bgClass;
	form.currentDenyCategory.disabled = isDisable;
	form.currentDenyCategory.className = bgClass;
	form.currentIncludeCheckPoints.disabled = isDisable;
	form.currentIncludeCheckPoints.className = bgClass;
	form.currentExcludeCheckPoints.disabled = isDisable;
	form.currentExcludeCheckPoints.className = bgClass;
	
	form.currSkpAndCondition.disabled=isDisable;
	form.currSkpORCondition.disabled=isDisable;

	for(i=0; i < form.currentStateList.length; i++) {
		form.currentStateList[i].disabled = isDisable;
	}
	
	for(i=0; i < form.currentGradeCheckButton.length; i++) {
		form.currentGradeCheckButton[i].disabled = isDisable;
	}
	
	for(i=0; i < form.stateCheckButton.length; i++) {
		form.stateCheckButton[i].disabled = isDisable;
	}
	for(i=0; i < form.currentScorerCheckButton.length; i++) {
		form.currentScorerCheckButton[i].disabled = isDisable;
	}
	
	form.punchTextData.disabled = isDisable;
	form.currentUpdateDateStartYear.disabled = isDisable;
	form.currentUpdateDateStartMonth.disabled = isDisable;
	form.currentUpdateDateStartDay.disabled = isDisable;
	form.currentUpdateDateStartHours.disabled = isDisable;
	form.currentUpdateDateStartMin.disabled = isDisable;
	
	form.currentUpdateDateEndYear.disabled = isDisable;
	form.currentUpdateDateEndMonth.disabled = isDisable;
	form.currentUpdateDateEndDay.disabled = isDisable;
	form.currentUpdateDateEndHours.disabled = isDisable;
	form.currentUpdateDateEndMin.disabled = isDisable;
	
	form.punchTextData.className = bgClass;
	form.currentUpdateDateStartYear.className = bgClass;
	form.currentUpdateDateStartMonth.className = bgClass;
	form.currentUpdateDateStartDay.className = bgClass;
	form.currentUpdateDateStartHours.className = bgClass;
	form.currentUpdateDateStartMin.className = bgClass;
	
	form.currentUpdateDateEndYear.className = bgClass;
	form.currentUpdateDateEndMonth.className = bgClass;
	form.currentUpdateDateEndDay.className = bgClass;
	form.currentUpdateDateEndHours.className = bgClass;
	form.currentUpdateDateEndMin.className = bgClass;

	if (!isDisable) {
		defaultValidation();
	}
	changeMode();
	
	changeCurrentBlock(isDisable, !isDisable, isDisable);
	
	if (isDisable) {
		removeCurrentBlockErrorMessages();
	}
}

function toggleProcessSearch() {
	var isDisable = !document.getElementById("historyBlock").checked;
	var form = document.getElementById("scoreSearchForm");
	var bgClass = "";
	if (isDisable) {
		bgClass = "disable_bg"
	}
	form.historyScorerId1.disabled = isDisable;
	form.historyScorerId1.className = bgClass;
	form.historyScorerId2.disabled = isDisable;
	form.historyScorerId2.className = bgClass;
	form.historyScorerId3.disabled = isDisable;
	form.historyScorerId3.className = bgClass;
	form.historyScorerId4.disabled = isDisable;
	form.historyScorerId4.className = bgClass;
	form.historyScorerId5.disabled = isDisable;
	form.historyScorerId5.className = bgClass;
	
	if(search_by_scorer_role_id == 'true'){
		form.historyScorerRole1.disabled = isDisable;
		form.historyScorerRole1.className = bgClass;
		form.historyScorerRole2.disabled = isDisable;
		form.historyScorerRole2.className = bgClass;
		form.historyScorerRole3.disabled = isDisable;
		form.historyScorerRole3.className = bgClass;
		form.historyScorerRole4.disabled = isDisable;
		form.historyScorerRole4.className = bgClass;
		if(isDisable){
			form.historyScorerRole1.checked = !isDisable;
			form.historyScorerRole2.checked = !isDisable;
			form.historyScorerRole3.checked = !isDisable;
			form.historyScorerRole4.checked = !isDisable;
		}
	}
	
	if(form.historyQualityCheckFlag!=null){
	form.historyQualityCheckFlag.disabled = isDisable;
	}
	
	if(isDisable){
		document.getElementById('historyCategoryType1').checked=true;
		if(document.getElementById('historyQualityCheckFlag')!=null){
		 document.getElementById('historyQualityCheckFlag').checked=false;
		}
	}
	
	for(i=0; i < document.getElementsByName("scoreInputInfo.scoreHistoryInfo.historyCategoryType").length; i++) {
		document.getElementsByName("scoreInputInfo.scoreHistoryInfo.historyCategoryType")[i].disabled = isDisable;
	}
	for(i=0; i < document.getElementsByName("scoreInputInfo.scoreHistoryInfo.historyGradeNum").length; i++) {
		document.getElementsByName("scoreInputInfo.scoreHistoryInfo.historyGradeNum")[i].disabled = isDisable;
	}
	form.historyIncludeCheckPoints.disabled = isDisable;
	form.historyIncludeCheckPoints.className = bgClass;
	form.historyExcludeCheckPoints.disabled = isDisable;
	form.historyExcludeCheckPoints.className = bgClass;
	
	for(i=0; i < form.historyEventList.length; i++) {
		form.historyEventList[i].disabled = isDisable;
	}
	for(i=0; i < form.processCheckButton.length; i++) {
		form.processCheckButton[i].disabled = isDisable;
	}
	for(i=0; i < form.scorerCheckButton.length; i++) {
		form.scorerCheckButton[i].disabled = isDisable;
	}
	for(i=0; i < form.eventCheckButton.length; i++) {
		form.eventCheckButton[i].disabled = isDisable;
	}
	
	form.historyPendingCategory.disabled = isDisable;
	form.historyPendingCategory.className = bgClass;
	
	form.historyDenyCategory.disabled = isDisable;
	form.historyDenyCategory.className = bgClass;
	
	form.historyUpdateDateStartYear.disabled = isDisable;
	form.historyUpdateDateStartMonth.disabled = isDisable;
	form.historyUpdateDateStartDay.disabled = isDisable;
	form.historyUpdateDateStartHours.disabled = isDisable;
	form.historyUpdateDateStartMin.disabled = isDisable;
	
	form.historyUpdateDateEndYear.disabled = isDisable;
	form.historyUpdateDateEndMonth.disabled = isDisable;
	form.historyUpdateDateEndDay.disabled = isDisable;
	form.historyUpdateDateEndHours.disabled = isDisable;
	form.historyUpdateDateEndMin.disabled = isDisable;
	
	form.historyUpdateDateStartYear.className = bgClass;
	form.historyUpdateDateStartMonth.className = bgClass;
	form.historyUpdateDateStartDay.className = bgClass;
	form.historyUpdateDateStartHours.className = bgClass;
	form.historyUpdateDateStartMin.className = bgClass;
	
	form.historyUpdateDateEndYear.className = bgClass;
	form.historyUpdateDateEndMonth.className = bgClass;
	form.historyUpdateDateEndDay.className = bgClass;
	form.historyUpdateDateEndHours.className = bgClass;
	form.historyUpdateDateEndMin.className = bgClass;
	
	form.pastSkpAndCondition.disabled=isDisable;
	form.pastSkpORCondition.disabled=isDisable;
	
	if (!isDisable) {
		defaultValidationPast();
	}
	changeMode();
	
	changeHistoryBlock(isDisable, !isDisable, isDisable);
	
	if (isDisable) {
		removeHistoryBlockErrorMessages();
	}
}

function changeMode() {
	var form = document.getElementById("scoreSearchForm");
	if (document.getElementById("historyBlock").checked) {
		if (document.getElementById("currentBlock").checked) {
			form.searchMode.value = "sampling";
		} else {
			form.searchMode.value = "searchProcess";
		}
	} else {
		if (document.getElementById("currentBlock").checked) {
			form.searchMode.value = "searchState";
		} else {
			form.searchMode.value = "default";
		}
	}
}

function setDefaultCB() {
	var form = document.getElementById("scoreSearchForm");
	if (form.historyGradeNum[0].disabled) {
		for(i=0; i < form.historyGradeNum.length; i++) {
			form.historyGradeNum[i].checked = true;
		}
	}
	if (form.historyEventList[0].disabled) {
		for(i=0; i < form.historyEventList.length; i++) {
			form.historyEventList[i].checked = true;
		}
	}
	if (form.currentGradeNum[0].disabled) {
		for(i=0; i < form.currentGradeNum.length; i++) {
			form.currentGradeNum[i].checked = true;
		}
	}
	if (form.scoringState[0].disabled) {
		for(i=0; i < form.scoringState.length; i++) {
			form.scoringState[i].checked = true;
		}
	}
}