// 画像拡大 | 答案画像の横幅を取得する
//function getWidth() {
//   image1_width = image1.width;
//   image1_height = image1.height;
//}	

var image1_width = 0;
var image1_height = 0;
var ratio = 0;
var size = new Array(5);
size[0] = 0.5;
size[1] = 0.6;
size[2] = 0.8;
size[3] = 1;
size[4] = 1.3;
size[5] = 1.6;
size[6] = 1.9;
var sizeSeq = 0;
//function setWidth() {
//	image1_width = image1.width;
//	image1_height = image1.height;
//	ratio = image1_height / image1_width;
//	sizeSeq = 2;
//	dialog_width = image1.width;
//	dialog_height = image1.height+100;
//	resizeBy(dialog_width,dialog_height);
//}

// 画像拡大ダイアログ・原票拡大ダイアログ | getWidth()の結果をもとに画像・ダイアログを拡大する
function enlargeImage(){
	if (sizeSeq < 6) {
		sizeSeq = sizeSeq + 1;
		image1.width = image1_width * size[sizeSeq];
		image1.height = image1.width * ratio;
		dialog_width = image1.width+20;
		dialog_height = image1.height+100;
		resizeBy(dialog_width,dialog_height);
   }
}

// 画像拡大ダイアログ・原票拡大ダイアログ | getWidth()の結果をもとに画像・ダイアログを縮小する
function dropImage(){
	if (sizeSeq > 0) {
		sizeSeq = sizeSeq - 1;
		image1.width = image1_width * size[sizeSeq];
		image1.height = image1.width * ratio;
		dialog_width = image1.width+20;
		dialog_height = image1.height+100;
		resizeBy(dialog_width,dialog_height);
   }
}

// 画像拡大ダイアログ | 開く
var ImageDialog;
function callModelessDialog(dialog_url){
if (!ImageDialog || ImageDialog.closed) {
	var form = document.getElementById("parentForm");
	var prm = new DialogParam("Dialog");
	prm.src = form.image1.src;
	displayWidth = document.image1.width+30 + "px";
	displayHeight = document.image1.height+140 + "px";
	ImageDialog = showModelessDialog(dialog_url,prm,"status:false;dialogWidth:"+displayWidth+";dialogHeight:"+displayHeight+";dialogLeft:85;dialogTop:0;edge:sunken;help:no;resizable:1;status:no");
	}
}

var PaperDialog;
function callPaperDialog(dialog_url, image_url){
if (!PaperDialog || PaperDialog.closed) {
	var prm = new DialogParam("Dialog");
	//var dummyImg = new Image();
	//dummyImg.src = image_url;
	prm.src = image_url;
	displayWidth = document.image1.width+30 + "px";
	displayHeight = document.image1.height+140 + "px";
	PaperDialog = showModelessDialog(dialog_url,prm,"status:false;dialogWidth:"+displayWidth+";dialogHeight:"+displayHeight+";dialogLeft:85;dialogTop:0;edge:sunken;help:no;resizable:1;status:no");
	}
}

var ManualDialog
function callManualDialog(dialog_url){
	if (!ManualDialog || ManualDialog.closed) {
		var prm = new DialogParam("Dialog");
		ManualDialog = showModelessDialog(dialog_url,prm,"dialogWidth:662px;dialogHeight:350px;dialogLeft:73px;dialogTop:283px;edge:sunken;help:no;resizable:1;status:no");
	}
}


// 画像拡大ダイアログ | 画像パスを渡す
function getImgPath() {
	var arg = dialogArguments;
	document.image1.src = arg.src;
	setDialogSize();
}

// 画像拡大ダイアログ | 画像パスを渡す
function getPaperPath() {
	var arg = dialogArguments;
	document.image1.src = document.getElementById("context").innerText + "/" + arg.src;
	setDialogSize();
}

function setDialogSize() {
	if (document.image1.complete) {
		image1_width = image1.width;
		image1_height = image1.height;
		ratio = image1_height / image1_width;
		sizeSeq = 3;
		dialog_width = image1.width+20;
		dialog_height = image1.height+100;
		resizeBy(dialog_width,dialog_height);
	} else {
		setTimeout("setDialogSize()", 50);
	}
}


 
// 採点結果確認画面・確認結果確認画面 | 開く
function callModalDialog(filename){
	showModalDialog(filename,window,"center:yes;status:false;center:1;dialogWidth:300px;dialogHeight:210px;edge:sunken;help:no;resizable:no;status:no;scroll:no;");
}

// 保留確認ダイアログを開く
function callHoldDialog(filename){
	showModalDialog(filename,window,"center:yes;status:false;center:1;dialogWidth:500px;dialogHeight:460px;edge:sunken;help:no;resizable:yes;status:no;scroll:no;");
}

// ダイアログを閉じる
function disp(){
	var sData = dialogArguments;
	self.window.close();
}



// Enterキーをdisableする関数
function KeyEvent(e){
	if(event.srcElement.name!="comment"){ 
    pressKey=event.keyCode; 
     if(pressKey==13){
     	return false;
     }
    }
    
}

// 入力画面系で、コメント欄以外でKeyEventを呼ぶ
function disableEnter() {
	var parentForm = document.getElementById("parentForm");
	var elements = parentForm.elements;
	
		
	for (var i = 0; i < elements.length; i++) {
		if (elements[i].name != 'comment') {
    		elements[i].onkeypress = KeyEvent;
    	}
	}

}

//tabIndex
function setSpecificTabIndex() {
	var form = document.getElementById("parentForm");
	var skpLength = form.skpCheckbox.length;
	for(var i=0; i<skpLength; i++){
		form.skpCheckbox.tabIndex = i+1;
	}
	document.getElementById("score_button").tabIndex = skpLength + 1;
	form.bookmark.tabIndex = skpLength + 2;
	form.comment.tabIndex = skpLength + 3;
	if (document.getElementById("back_button1") != null) {
		document.getElementById("back_button1").tabIndex = skpLength + 4;
	}
	if (document.getElementById("back_button2") != null) {
		document.getElementById("back_button2").tabIndex = skpLength + 5;
	}
}

// CBがEnabledの入力・履歴確認画面表示用
function loadSpecificFunctions() {
	document.getElementById('checkbox[0]').focus();
	//disableEnter();
	setSpecificTabIndex();
}

//tabIndex
function setTabIndex() {
	var form = document.getElementById("parentForm");
	var skpLength = form.skpCheckbox.length;
	for(var i=0; i<skpLength; i++){
		form.skpCheckbox.tabIndex = i+1;
	}
	document.getElementById("score_button").tabIndex = skpLength + 1;
	document.getElementById("hold_button").tabIndex = skpLength + 2;
	form.bookmark.tabIndex = skpLength + 3;
	form.comment.tabIndex = skpLength + 4;
	if (document.getElementById("back_button1") != null) {
		document.getElementById("back_button1").tabIndex = skpLength + 5;
	}
	if (document.getElementById("back_button2") != null) {
		document.getElementById("back_button2").tabIndex = skpLength + 6;
	}
}

// CBがEnabledの入力・履歴確認画面表示用
function loadFunctions() {
	document.getElementById('checkbox[0]').focus();
	//disableEnter();
	setTabIndex();
}

function setManualData() {
	if (!ManualDialog || ManualDialog.closed) {
		document.getElementById("parentForm").manualDialog.value = "";
	} else {
		document.getElementById("parentForm").manualDialog.value = ("dialogWidth:" + ManualDialog.dialogWidth + ";dialogHeight:" + ManualDialog.dialogHeight + ";dialogLeft:" + (ManualDialog.screenLeft-4) + ";dialogTop:" + (ManualDialog.screenTop-23));
	}
}

function openManual(dialog_url) {
	var manualDialog = document.getElementById("parentForm").manualDialog.value;
	if (manualDialog) {
		//document.getElementById("parentForm").comment.value = manualDialog;
		var prm = new DialogParam("Dialog");
		ManualDialog = showModelessDialog(dialog_url,prm,manualDialog + ";edge:sunken;help:no;resizable:1;status:no");
	} else {
		return false;
	}
}

function dispManualData() {
	if (!ManualDialog || ManualDialog.closed) {
		document.getElementById("parentForm").comment.value = "";
	} else {
		document.getElementById("parentForm").comment.value = ("dialogWidth:" + ManualDialog.dialogWidth + ";dialogHeight:" + ManualDialog.dialogHeight + ";dialogLeft:" + (ManualDialog.screenLeft-4) + ";dialogTop:" + (ManualDialog.screenTop-23));
	}
}