function goBookmark() {
	var browseForm = document.getElementById("browseForm");
	browseForm.scoringSeq.value = event.srcElement.parentElement.cells[0].firstChild.nodeValue;
	browseForm.questionId.value = event.srcElement.parentElement.cells[1].firstChild.nodeValue;
	browseForm.submit();
}

function goHistory() {
	var browseForm = document.getElementById("browseForm");
	if(event.srcElement.tagName == 'IMG'){
		browseForm.scoringSeq.value = event.srcElement.parentElement.parentElement.cells[0].firstChild.nodeValue;
		browseForm.questionId.value = event.srcElement.parentElement.parentElement.cells[1].firstChild.nodeValue;
	} else {
		browseForm.scoringSeq.value = event.srcElement.parentElement.cells[0].firstChild.nodeValue;
		browseForm.questionId.value = event.srcElement.parentElement.cells[1].firstChild.nodeValue;
	}
	browseForm.submit();
}

function goSpecificDetail() {
	var browseForm = document.getElementById("browseForm");
	browseForm.questionId.value = event.srcElement.parentElement.cells[0].firstChild.nodeValue;
	browseForm.answerSeq.value = event.srcElement.parentElement.cells[1].firstChild.nodeValue;
	browseForm.submit.click();
}

function goProcessHistoryDetail() {
	var browseForm = document.getElementById("browseForm");
	browseForm.questionId.value = event.srcElement.parentElement.cells[0].firstChild.nodeValue;
	browseForm.scoringSeq.value = event.srcElement.parentElement.cells[1].firstChild.nodeValue;
	browseForm.submit.click();
}

function checkAll() {
  var shortFlags = document.getElementsByName("deleteFlagShort");
  for(i = 0; i < shortFlags.length; i++) {
    shortFlags.item(i).checked = event.srcElement.checked;
  }
  var descFlags = document.getElementsByName("deleteFlagLong");
  for(i = 0; i < descFlags.length; i++) {
    descFlags.item(i).checked = event.srcElement.checked;
  }
}
function checkAllBookmarks() {
	  var shortFlags = document.getElementsByName("historySequence");
	  for(i = 0; i < shortFlags.length; i++) {
	    shortFlags.item(i).checked = event.srcElement.checked;
	  }
}
function uncheckAllCheckboxes(){
	var selectdCheckBoxes=0;
	var recordCount= countRows();
	for(i = 1; i <= recordCount; i++) {
	    if((document.getElementById("checkBox"+i)!=null)&&(document.getElementById("checkBox"+i).checked==true)){
	    	selectdCheckBoxes++;
	    }
	  }
	if(selectdCheckBoxes==0){
		document.getElementById("selectAll").checked = false;
	}
}
function countRows() {
		var rowCount=0;
		rowCount=document.getElementById("bookmarkInfoResult").rows.length-1;
		return rowCount;
}