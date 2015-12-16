
var popup=null;
var width=0;
var height=0;
var actualWidth= 0;
var actualHeight= 0;
var divWidth= 675;
var divHeight= 450;
var heightWidthRatio=0;
var increaseBy= 25;

$(window).bind("load", function() {
	setAnswerPaperImageSize();
});

function enlargePaperImage(){
	
	calculateAnswerPaperActualWidthHeight();
	
	width=$('#answerPaper').width();
	height=$('#answerPaper').height();
	
	$('#answerPaper').css('width',width+increaseBy);
	$('#answerPaper').css('height',(width+increaseBy)/heightWidthRatio);
}

//ç”»åƒ�æ‹¡å¤§ãƒ€ã‚¤ã‚¢ãƒ­ã‚°ãƒ»åŽŸç¥¨æ‹¡å¤§ãƒ€ã‚¤ã‚¢ãƒ­ã‚° | getWidth()ã�®çµ�æžœã‚’ã‚‚ã�¨ã�«ç”»åƒ�ãƒ»ãƒ€ã‚¤ã‚¢ãƒ­ã‚°ã‚’ç¸®å°�ã�™ã‚‹
function dropPaperImage(){

	calculateAnswerPaperActualWidthHeight();	
	
	width=$('#answerPaper').width();
	height=$('#answerPaper').height();
	var resizeWidth = width-increaseBy;
	var resizeHeight = (width-increaseBy)/heightWidthRatio;
	
	if((resizeWidth>=actualWidth) || (resizeHeight>=actualHeight)){
		$('#answerPaper').css('width',width-increaseBy);
		$('#answerPaper').css('height',(width-increaseBy)/heightWidthRatio);
	}
}

function setAnswerPaperImageSize(){

	calculateAnswerPaperActualWidthHeight();
	
	$('#answerPaper').css('width',actualWidth);
	$('#answerPaper').css('height',actualHeight);
}

function calculateAnswerPaperActualWidthHeight(){

	var naturalWidth = document.getElementById($('#answerPaper').attr("id")).naturalWidth;
	var naturalHeight = document.getElementById($('#answerPaper').attr("id")).naturalHeight;
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
	}else{
		actualWidth=naturalWidth;
		actualHeight=naturalHeight;
	}
	
	heightWidthRatio = actualWidth/actualHeight;
}

function loadFrontImage(imageUrl){
	$("#answerPaper").attr("src",imageUrl);
}

function loadBackImage(imageUrl){
	$("#answerPaper").attr("src",imageUrl);
}