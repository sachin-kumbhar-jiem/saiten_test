var popup=null;
var width=0;
var height=0;
var actualWidth= 0;
var actualHeight= 0;
var divWidth= 445;
var divHeight= 525;
var heightWidthRatio=0;
var increaseBy= 25;
var increamentSeq = 0;

$(window).bind("load", function() {
	if(!(questionType == '3' || questionType == '4' )){
		setAnswerImageSize();
	}
});

function setImageToDefaultSize(){
	calculateActualWidthHeight();

	$('#answerImage').css('width',actualWidth);
	$('#answerImage').css('height',actualHeight);

	increamentSeq = 0;
}

function setAnswerImageSize(){
	calculateActualWidthHeight();
	
	$('#answerImage').css('width',actualWidth);
	$('#answerImage').css('height',actualHeight);
}
function enlargeImage(){
	calculateActualWidthHeight();
	
	width=$('#answerImage').width();
	height=$('#answerImage').height();
	
	$('#answerImage').css('width',width+increaseBy);
	$('#answerImage').css('height',(width+increaseBy)/heightWidthRatio);
	increamentSeq = increamentSeq + 1;
}

//ç”»åƒ�æ‹¡å¤§ãƒ€ã‚¤ã‚¢ãƒ­ã‚°ãƒ»åŽŸç¥¨æ‹¡å¤§ãƒ€ã‚¤ã‚¢ãƒ­ã‚° | getWidth()ã�®çµ�æžœã‚’ã‚‚ã�¨ã�«ç”»åƒ�ãƒ»ãƒ€ã‚¤ã‚¢ãƒ­ã‚°ã‚’ç¸®å°�ã�™ã‚‹
function dropImage(){
	calculateActualWidthHeight();	
	
	width=$('#answerImage').width();
	height=$('#answerImage').height();
	var resizeWidth = width-increaseBy;
	var resizeHeight = (width-increaseBy)/heightWidthRatio;
	
	if(increamentSeq > 0){
		$('#answerImage').css('width',width-increaseBy);
		$('#answerImage').css('height',(width-increaseBy)/heightWidthRatio);
		increamentSeq = increamentSeq - 1;
	}
}
function calculateActualWidthHeight(){

	var naturalWidth = document.getElementById($('#answerImage').attr("id")).naturalWidth;
	var naturalHeight = document.getElementById($('#answerImage').attr("id")).naturalHeight;
	
	var tempHeight = (divWidth*naturalHeight)/naturalWidth;
	
	var tempWidth = (naturalWidth*divHeight)/naturalHeight;
	
	if((naturalWidth>divWidth)||(naturalHeight>divHeight)){
		if((naturalWidth>divWidth)&&(naturalHeight>divHeight)){
			if(!(tempHeight>divHeight)){
				actualWidth=divWidth;
				actualHeight= tempHeight;
			}else if(!(tempWidth>divWidth)){
				actualHeight=divHeight;
				actualWidth=tempWidth;
			}else {
				actualWidth=divWidth;
				actualHeight= tempHeight;
			}
		}else if(naturalWidth>divWidth){
			actualWidth=divWidth;
			actualHeight= tempHeight;
		}else if(naturalHeight>divHeight){
			actualHeight=divHeight;
			actualWidth=tempWidth;
		}
	}else {
		actualWidth= naturalWidth;
		actualHeight= naturalHeight;
	}
	
	heightWidthRatio = actualWidth/actualHeight;
}

function openPaperDialog(){
    settings = 'height=550,width=700,left=20,top=20,resizable=yes,scrollbars=yes,toolbar=no,menubar=yes,location=no,directories=no, status=yes';
    popup= window.open("answerPaperPopup.action", "popUpWindow", settings);
}