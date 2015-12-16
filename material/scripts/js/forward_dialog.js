// 採点結果確認表示用
function callFormModal(path) {
	var button1 = document.getElementById("score_button");
	var button2 = document.getElementById("hold_button");
	button1.disabled = true;
	button2.disabled = true;
	// フォームを取得
	var form = document.getElementById("parentForm");
	
	// ダイアログにフォームを渡す
	var prm = new DialogParam("Dialog");
	prm.parentForm = form;
	prm.srcButton = window.event.srcElement.name;
	// ダイアログを開く
	var returnData = showModalDialog(path,prm,"center:yes;status:false;center:1;dialogWidth:300px;dialogHeight:226px;edge:sunken;help:no;resizable:no;status:no;scroll:no;");
	// ×ボタンで閉じられた際のエラー回避
	if(returnData == null) {
		button1.disabled = false;
		button2.disabled = false;
		return false;
	}
	if(returnData[0] == "error") {
		document.body.className = "";
		document.body.innerHTML = returnData[1];
	} else if(returnData[0]){
		if(typeof(form.manualDialog)!="undefined") {
			setManualData();
		}
		// submitボタンでアクション起動
		document.getElementById("nextButton").click();
	} else {
		button1.disabled = false;
		button2.disabled = false;
	}	
}

// 保留確認ダイアログ表示用
function callFormModal_hold(path) {
	var button1 = document.getElementById("score_button");
	var button2 = document.getElementById("hold_button");
	button1.disabled = true;
	button2.disabled = true;
	// フォームを取得
	var form = document.getElementById("parentForm");
	
	// ダイアログにフォームを渡す
	var prm = new DialogParam("Dialog");
	prm.parentForm = form;
	prm.buttonName = window.event.srcElement.name;

	// ダイアログを開く
	var returnData = showModalDialog(path,prm,"center:yes;status:false;center:1;dialogWidth:300px;dialogHeight:230px;edge:sunken;help:no;resizable:yes;status:no;scroll:no;");
	
	// ×ボタンで閉じられた際のエラー回避
	if(returnData == null) {
		button1.disabled = false;
		button2.disabled = false;
		return false;
	}
	if(returnData[0] == "error") {
		document.body.className = "";
		document.body.innerHTML = returnData[1];
	} else if(returnData[0]){
		if(typeof(form.manualDialog)!="undefined") {
			setManualData();
		}
		// submitボタンでアクション起動
		form.nextButton.click();
	} else {
		button1.disabled = false;
		button2.disabled = false;
		// 戻るボタンが押下された場合、コメントを更新
		form.comment.value = returnData[1];
	}
}

// 採点結果確認ダイアログ初期化
function initDialog() {
	// フォームを取得
	var dialogForm = document.getElementById("dialogForm");
	
	// 親画面からのパラメータを取得
	var arg = dialogArguments;
	
	// ダイアログのフォームに親画面のフォームの値を代入
	dialogForm.questionSheetNum.value = arg.parentForm.questionSheetNum.value;
	dialogForm.scoredTotal.value = arg.parentForm.scoredTotal.value;
	dialogForm.comment.value = arg.parentForm.comment.value;
	dialogForm.bookmark.value = arg.parentForm.bookmark.checked;
	dialogForm.questionId.value = arg.parentForm.questionId.value;
	dialogForm.buttonName.value = arg.parentForm.buttonName.value;
	dialogForm.authSeq.value = arg.parentForm.authSeq.value;
	dialogForm.answerSeq.value = arg.parentForm.answerSeq.value;
	dialogForm.scoringSample.value = arg.parentForm.scoringSample.value;
	dialogForm.answerSample.value = arg.parentForm.answerSample.value;
	// チェックボックス(配列)の状態を文字列に変換し、フォームに代入
	var state = "";
	if(typeof(arg.parentForm.skpCheckbox.length) == "undefined") {
		state = state + arg.parentForm.skpCheckbox.checked + ",";
	} else {
		for(var i=0; i < arg.parentForm.skpCheckbox.length; i++){
			state = state + arg.parentForm.skpCheckbox[i].checked + ",";
		}
	}
	dialogForm.skpState.value = state;
	
	// Action起動
	dialogForm.submit();
}

//保留ダイアログ初期化
function initHoldDialog() {
	// フォームを取得
	var dialogForm = document.getElementById("dialogForm");
	
	// 親画面からのパラメータを取得
	var arg = dialogArguments;
	
	// ダイアログのフォームに親画面のフォームの値を代入
	dialogForm.questionSheetNum.value = arg.parentForm.questionSheetNum.value;
	dialogForm.scoredTotal.value = arg.parentForm.scoredTotal.value;
	dialogForm.comment.value = arg.parentForm.comment.value;
	dialogForm.bookmark.value = arg.parentForm.bookmark.checked;
	dialogForm.questionId.value = arg.parentForm.questionId.value;
	dialogForm.buttonName.value = arg.parentForm.buttonName.value;
	dialogForm.authSeq.value = arg.parentForm.authSeq.value;
	dialogForm.answerSeq.value = arg.parentForm.answerSeq.value;
	dialogForm.scoringSample.value = arg.parentForm.scoringSample.value;
	dialogForm.answerSample.value = arg.parentForm.answerSample.value;

	if(typeof(arg.parentForm.selectedHold) == "undefined") {
		dialogForm.selectedHold.value = 0;
	} else {
		dialogForm.selectedHold.value = arg.parentForm.selectedHold.value;
	}
	// チェックボックス(配列)の状態を文字列に変換し、フォームに代入
	var state = "";
	if(typeof(arg.parentForm.skpCheckbox.length) == "undefined") {
		state = state + arg.parentForm.skpCheckbox.checked + ",";
	} else {
		for(var i=0; i < arg.parentForm.skpCheckbox.length; i++){
			state = state + arg.parentForm.skpCheckbox[i].checked + ",";
		}
	}
	dialogForm.skpState.value = state;
	
	// Action起動
	dialogForm.submit();
}



// 確認結果確認ダイアログ初期化
function initConfirmDialog() {
	// フォームを取得
	var dialogForm = document.getElementById("dialogForm");
	
	// 親画面からのパラメータを取得
	var arg = dialogArguments;
			
	// ダイアログのフォームに親画面のフォームの値を代入
	dialogForm.questionSheetNum.value = arg.parentForm.questionSheetNum.value;
	dialogForm.scoredTotal.value = arg.parentForm.scoredTotal.value;
	dialogForm.comment.value = arg.parentForm.comment.value;
	dialogForm.bookmark.value = arg.parentForm.bookmark.checked;
	dialogForm.firstType.value = arg.parentForm.firstType.value;
	dialogForm.firstTrueFalse.value = arg.parentForm.firstTrueFalse.value;
	dialogForm.questionId.value = arg.parentForm.questionId.value;
	dialogForm.buttonName.value = arg.parentForm.buttonName.value;
	dialogForm.authSeq.value = arg.parentForm.authSeq.value;
	dialogForm.answerSeq.value = arg.parentForm.answerSeq.value;
	dialogForm.scoringSample.value = arg.parentForm.scoringSample.value;
	dialogForm.answerSample.value = arg.parentForm.answerSample.value;
	dialogForm.srcButton.value = arg.srcButton;
	// チェックボックス(配列)の状態を文字列に変換し、フォームに代入
	var state = "";
		if(typeof(arg.parentForm.skpCheckbox.length) == "undefined") {
		state = state + arg.parentForm.skpCheckbox.checked + ",";
	} else {
		for(var i=0; i < arg.parentForm.skpCheckbox.length; i++){
			state=state + arg.parentForm.skpCheckbox[i].checked + ",";
		}
	}
	dialogForm.skpState.value = state;
	
	// Action起動
	dialogForm.submit();
}

// 採点更新用ダイアログ初期化
function initUpdateDialog() {
	// フォームを取得
	var dialogForm = document.getElementById("dialogForm");
	// 親画面からのパラメータを取得
	var arg = dialogArguments;
	dialogForm.scoringSeq.value = arg.parentForm.scoringSeq.value;
	initDialog();
}

// 保留更新用ダイアログ初期化
function initUpdateHoldDialog() {
	// フォームを取得
	var dialogForm = document.getElementById("dialogForm");
	// 親画面からのパラメータを取得
	var arg = dialogArguments;
	dialogForm.scoringSeq.value = arg.parentForm.scoringSeq.value;
	initHoldDialog();
}

// 確認更新用ダイアログ初期化
function initUpdateConfirmDialog() {
	// フォームを取得
	var dialogForm = document.getElementById("dialogForm");
	// 親画面からのパラメータを取得
	var arg = dialogArguments;
	dialogForm.scoringSeq.value = arg.parentForm.scoringSeq.value;
	initConfirmDialog();
}


// Submit後、採点結果確認・保留確認ダイアログを閉じる
function closeDialog(registered) {
	// 戻り値用配列を用意
	var returnData = new Array(1);
	
	// 登録ボタンか戻るボタンかを引数で指定
	returnData[0] = registered;
	
	// 戻り値を代入し、ダイアログを閉じる
	returnValue = returnData;
	self.window.close();
}

// 戻るボタン(保留確認ダイアログ用、コメントを戻す)
function closeHoldDialog() {
	var returnData = new Array(2);
	returnData[0] = false;
	returnData[1] = document.getElementById("dialogForm").comment.value;
	returnValue = returnData;
	self.window.close();
}

// エラー画面がダイアログの場合の処理
function checkDialog() {
	if(typeof(dialogArguments) != "undefined") {
		var returnData = new Array(2);
		returnData[0] = "error";
		returnData[1] = document.body.innerHTML;
		returnValue = returnData;
		self.window.close();
	}
}