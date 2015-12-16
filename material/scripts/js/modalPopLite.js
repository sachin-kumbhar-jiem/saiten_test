/*
* jQuery modalPopLite
* Copyright (c) 2012 Simon Hibbard
* 
* Permission is hereby granted, free of charge, to any person
* obtaining a copy of this software and associated documentation
* files (the "Software"), to deal in the Software without
* restriction, including without limitation the rights to use,
* copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the
* Software is furnished to do so, subject to the following
* conditions:

* The above copyright notice and this permission notice shall be
* included in all copies or substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
* EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
* OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
* NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
* HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
* WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
* FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
* OTHER DEALINGS IN THE SOFTWARE. 
*/

/*
* Version: V1.3.1
* Release: 19-07-2012
* Based on jQuery 1.7.2
*/


(function ($) {
    var popID = 0;
    $.fn.modalPopLite = function (options) {
        var options = $.extend({}, { openButton: "modalPopLite-open-btn", approveButton: "modalPopLite-approve-btn", denyButton: "modalPopLite-deny-btn", closeButton: "modalPopLite-close-btn", isModal: false, callBack: null }, options); //zoomIn: "modalPopLite-zoomIn-btn", zoomOut: "modalPopLite-zoomOut-btn", 

        return this.each(function () {
        	/*var APPROVE = "承認";
            var DENY = "否認";
            var APPROVE_HEADING= "承認しますか？";
            var DENY_HEADING= "否認しますか？";*/
        	popID++;
            var thisPopID = popID;
            var isOpen = false;
            
            
            obj = $(this);
            triggerObj = options.openButton;
            closeObj = options.closeButton;
            approveObj= options.approveButton;
            denyObj= options.denyButton;
            isReallyModel = options.isModal;

            obj.before('<div id="modalPopLite-mask' + thisPopID + '" style="width:100%" class="modalPopLite-mask" />');
            obj.wrap('<div id="modalPopLite-wrapper' + thisPopID + '" style="left: -10000px;" class="modalPopLite-wrapper" />');
            obj.addClass('modalPopLite-child-' + thisPopID);

            $(triggerObj).live("click", function (e) {
            	e.preventDefault();
                selectedButtonId = $(this).attr("id");
               
            	if(((selectedButtonId=='pending' || selectedButtonId=='score') && $('form').valid())){
            		var isSuccess = true;
            		
            		if(selectedButtonId=='score'){
            			$('#score').prop('disabled', true);
            			$("#registerScore").focus();
            			isSuccess = confirmScore();
            		} else if(selectedButtonId=='pending'){
            			$('#pending').prop('disabled', true);
            			$("#registerPending").focus();
            			isSuccess = confirmPending();
            		} 
            		
            		if(isSuccess){
            			if((questionType == '3' || questionType == '4' ) && selectedButtonId=='score'){
            				javascript:location.href='registerScore.action';
            			}else{
            				
            				var winW = $(window).width();
            				var winH = $(window).height();
            				var objW = $('.modalPopLite-child-' + thisPopID).outerWidth();
            				var objH = $('.modalPopLite-child-' + thisPopID).outerHeight();
            				var left = (winW / 2) - (objW / 2);
            				var top = (winH / 2) - (objH / 2);

            				$('#modalPopLite-mask' + thisPopID).css('height', winH + "px");
            				$('#modalPopLite-mask' + thisPopID).fadeTo('slow', 0.6);
            				//$('#modalPopLite-wrapper' + thisPopID).hide();
            				$('#modalPopLite-wrapper' + thisPopID).css({ 'left': left + "px", 'top': top });
            				$('#modalPopLite-wrapper' + thisPopID).fadeIn('slow');
            				isOpen = true;
            			}
            		}
            	}
            	
            	// reset selectedCheckPoints
        		selectedCheckPoints = [];
            });
            
            $(approveObj).live("click", function (e) {
            	e.preventDefault();
                selectedButtonId = $(this).attr("id");
                
            	if(((selectedButtonId=='approve') && $('form').valid())){
            		var isSuccess = true;
            		
            		if(selectedButtonId=='approve'){
            			$('#approve').prop('disabled', true);
            			$("#registerScore").focus();
            			$("#approveOrDeny").val("approve");
            			$("#registerScore").attr('value', APPROVE);
            			$("#scorePopupHeading").attr('value', APPROVE_HEADING);
            			$("#popup-wrapper").css('background-color','#2D2D2D');
            			$("#scorePopupHeading").css('background-color','#2D2D2D');
            			
            			isSuccess = confirmScore();
            		} 
            		
            		if(isSuccess){
            			if((questionType == '3' || questionType == '4' )){
            				javascript:location.href='registerScore.action';
            			}else{
            				var winW = $(window).width();
            				var winH = $(window).height();
            				var objW = $('.modalPopLite-child-' + thisPopID).outerWidth();
            				var objH = $('.modalPopLite-child-' + thisPopID).outerHeight();
            				var left = (winW / 2) - (objW / 2);
            				var top = (winH / 2) - (objH / 2);

            				$('#modalPopLite-mask' + thisPopID).css('height', winH + "px");
            				$('#modalPopLite-mask' + thisPopID).fadeTo('slow', 0.6);
            				//$('#modalPopLite-wrapper' + thisPopID).hide();
            				$('#modalPopLite-wrapper' + thisPopID).css({ 'left': left + "px", 'top': top });
            				$('#modalPopLite-wrapper' + thisPopID).fadeIn('slow');
            				isOpen = true;
            			}
            		}
            	}
            	
            	// reset selectedCheckPoints
        		selectedCheckPoints = [];
            });
            
            $(denyObj).live("click", function (e) {
            	e.preventDefault();
                selectedButtonId = $(this).attr("id");
                
            	if(((selectedButtonId=='deny') && $('form').valid())){
            		var isSuccess = true;
            		
            		if(selectedButtonId=='deny'){
            			$('#deny').prop('disabled', true);
            			$("#registerScore").focus();
            			$("#approveOrDeny").val("deny");
            			$("#registerScore").attr('value', DENY);
            			$("#scorePopupHeading").attr('value', DENY_HEADING);
            			$("#popup-wrapper").css('background-color','red');
            			$("#scorePopupHeading").css('background-color','red');
            			isSuccess = confirmScore();
            			
            		}
            		
            		if(isSuccess){
            			if((questionType == '3' || questionType == '4' )){
            				javascript:location.href='registerScore.action';
            			}else{
            				var winW = $(window).width();
            				var winH = $(window).height();
            				var objW = $('.modalPopLite-child-' + thisPopID).outerWidth();
            				var objH = $('.modalPopLite-child-' + thisPopID).outerHeight();
            				var left = (winW / 2) - (objW / 2);
            				var top = (winH / 2) - (objH / 2);

            				$('#modalPopLite-mask' + thisPopID).css('height', winH + "px");
            				$('#modalPopLite-mask' + thisPopID).fadeTo('slow', 0.6);
            				//$('#modalPopLite-wrapper' + thisPopID).hide();
            				$('#modalPopLite-wrapper' + thisPopID).css({ 'left': left + "px", 'top': top });
            				$('#modalPopLite-wrapper' + thisPopID).fadeIn('slow');
            				isOpen = true;
            			}
            		}
            	}
            	
            	// reset selectedCheckPoints
        		selectedCheckPoints = [];
            });
            
            $(closeObj).live("click", function (e) {
                e.preventDefault();
                $('#modalPopLite-mask' + thisPopID).hide();
                //$('#modalPopLite-wrapper' + thisPopID).hide();
                $('#modalPopLite-wrapper' + thisPopID).css('left', "-10000px");
                isOpen = false;
                if (options.callBack != null) {
                    options.callBack.call(this);
                }
                if(selectedButtonId=='answerImage'){
                	 $('#answerpopup-wrapper').hide();
                }
            });
            
            //if mask is clicked
            if (!isReallyModel) {
                $('#modalPopLite-mask' + thisPopID).click(function (e) {
                    e.preventDefault();
                    $(this).hide();
                    //$('#modalPopLite-wrapper' + thisPopID).hide();
                    $('#modalPopLite-wrapper' + thisPopID).css('left', "-10000px");
                    isOpen = false;
                    if (options.callBack != null) {
                        options.callBack.call(this);
                    }
                });
            }
            $(window).resize(function () {
                if (isOpen) {
                    var winW = $(window).width();
                    var winH = $(window).height();
                    var objW = $('.modalPopLite-child-' + thisPopID).outerWidth();
                    var objH = $('.modalPopLite-child-' + thisPopID).outerHeight();
                    var left = (winW / 2) - (objW / 2);
                    var top = (winH / 2) - (objH / 2);
                    $('#modalPopLite-wrapper' + thisPopID).css({ 'left': left + "px", 'top': top });
                }
            });
        });

    };

    $.fn.modalPopLite.Close = function (id) {
        $('#modalPopLite-mask' + id).hide();
        //$('#modalPopLite-wrapper' + id).hide();
        $('#modalPopLite-wrapper' + thisPopID).css('left', "-10000px");
        if (options.callBack != null) {
            options.callBack.call(this);
        }
    };

    $.fn.modalPopLite.ShowProgress = function () {
        $('<div class="popBox-ajax-progress"></div>').appendTo("body")
    };

    $.fn.modalPopLite.HideProgress = function () {
        $('.popBox-ajax-progress').remove();
    };
}

)(jQuery);
