 $(function() {
			 	 $("a[title]").tooltip({position: "bottom left"});
		}); 
		 $(document).ready(
			        function()  {
			             // 100 = HEIGHT
			            stripFirstColumn();
			            fixHeights();
			            scrolify($('#dataTable'),2150);
			            $("#ladderDiv").attr('scrollLeft', $("#ladderDiv").attr('scrollWidth'));    
			        }
			    );
		 function fixHeights() {
			    // change heights:
			    var curRow =1;
			    
			    
			    $('#data tr').each(function(i){
			        // get heights
			       var c1 = $('#nameTable tr:nth-child('+curRow+')').height();    // column 1
			        var c2 = $(this).height();    // column 2
			        var maxHeight = Math.max(c1,c2);
			      
			        
			        if(curRow ==1){
			       if(DAILY_STATUS_REPORT_COMMON_TITLE=='JP'){
			          $('#nameTable tr th').height(34);
			         }
			        else{
				        $('#nameTable tr th').height(49);
			        } 
			        //	 $('#nameTable tr th').height(headerHeight);
			       // $('#dataTable tr:nth-child(0)').height(maxHeight);
			        $(this).children('tr td').height(maxHeight);
			        }
			        else{
			        // set heights
			        $('#nameTable tr:nth-child('+curRow+')').height(maxHeight);
			        $('#dataTable tr:nth-child('+curRow+')').height(maxHeight);
			        $(this).children('td:first').height(maxHeight);
			        }
			        curRow++;
			    });

			    if ($.browser.msie)
			        $('#ladderDiv').height($('#ladderDiv').height()+18);
			}
		 function stripFirstColumn() {                
			    // pull out first column:
			    var nt = $('<table id="nameTable" cellpadding="1"  style="width:100%" ></table>');
			    var dataTable = $('<table id="dataTable" cellpadding="1"  style="width:100%;"></table>');
			    var dataRowTdFinal="";
			    var dataRowTh="";
			    dataRowTdFinal="";
			    var runOnlyOnce=0;
			    var arrayTitle;
			    var parentCol=0;
			    var childCol=0;
			   if($('#qtype').val()==3 || $('#qtype').val()==4){
				   parentCol=9;
				   childCol=7;
				   arrayTitle=[DAILY_STATUS_REPORT_COMMON_TITLE_FIRST_TIME_SCORING,DAILY_STATUS_REPORT_COMMON_TITLE_SECOND_TIME_SCORING,DAILY_STATUS_REPORT_COMMON_TITLE_PENDING_SCORING,DAILY_STATUS_REPORT_COMMON_TITLE_MISMATCH_SCORING,DAILY_STATUS_REPORT_COMMON_TITLE_INSPECTION_SCORING,DAILY_STATUS_REPORT_COMMON_TITLE_DENY_SCORING,DAILY_STATUS_REPORT_COMMON_TITLE_NOGRADE,DAILY_STATUS_REPORT_COMMON_TITLE_SAMPLING_SCORING,DAILY_STATUS_REPORT_COMMON_TITLE_FORCED_SCORING/*,DAILY_STATUS_REPORT_COMMON_TITLE_BATCH_SCORING*/];
			   }else{
				   parentCol=11;
				   childCol=9;
				   arrayTitle=[DAILY_STATUS_REPORT_COMMON_TITLE_FIRST_TIME_SCORING,DAILY_STATUS_REPORT_COMMON_TITLE_SECOND_TIME_SCORING,DAILY_STATUS_REPORT_COMMON_TITLE_CHEKING_SCORING,DAILY_STATUS_REPORT_COMMON_TITLE_PENDING_SCORING,DAILY_STATUS_REPORT_COMMON_TITLE_MISMATCH_SCORING,DAILY_STATUS_REPORT_COMMON_TITLE_INSPECTION_SCORING,DAILY_STATUS_REPORT_COMMON_TITLE_OUTOFBOUNDARY,DAILY_STATUS_REPORT_COMMON_TITLE_DENY_SCORING,DAILY_STATUS_REPORT_COMMON_TITLE_NOGRADE,DAILY_STATUS_REPORT_COMMON_TITLE_SAMPLING_SCORING,DAILY_STATUS_REPORT_COMMON_TITLE_FORCED_SCORING/*,DAILY_STATUS_REPORT_COMMON_TITLE_BATCH_SCORING*/];
			   }
			    for(o=0;o<parentCol;o++){
			    	 var dataRowTd="";
		 $('#data tr').each(function(i)
			    {
			         if(i==0){
			        	 if(runOnlyOnce==0){
			        	 var myRow="";
			        	 myRow=('<tr><th style="color:'+$(this).children('th:first').css('color')+';height:46px">'+$(this).children('th:first').html()+'</th>');
			        	 $(this).children('th:first').remove();
			        	 myRow=myRow+('<th style="color:'+$(this).children('th:first').css('color')+'">'+$(this).children('th:first').html()+'</th>');
			        	 $(this).children('th:first').remove();
			        	 myRow=myRow+('<th style="color:'+$(this).children('th:first').css('color')+'">'+$(this).children('th:first').html()+'</th>');
			        	 $(this).children('th:first').remove();	
			        	 myRow=myRow+('<th style="color:'+$(this).children('th:first').css('color')+'">'+$(this).children('th:first').html()+'</th></tr>');
			        	 $(this).children('th:first').remove();	
			        	 nt.append(myRow);
			        	 runOnlyOnce++;
			        	 }
			
			        	 dataRowTh="<tr>";
			        	 //for(che=0;che<2;che++){			        
			        	 dataRowTh=('<td ><table style="width:100%"><tr><td style="color:'+$(this).children('th:first').css('color')+';text-align: center;background-color:#436593;font-weight:bold;left-padding:2%;right-padding:2%;">'+arrayTitle[o]+'</td></tr>');
			        	 dataRowTh=dataRowTh+('<tr><td><table style="width:100%"><tr><td style="color:'+$(this).children('th:first').css('color')+';width:30%;text-align: center;background-color:#436593;font-weight:bold;left-padding:2%;right-padding:2%;">'+$(this).children('th:first').html()+'</td>');    
			        	 $(this).children('th:first').remove();
			        	 dataRowTh=dataRowTh+('<td style="color:'+$(this).children('th:first').css('color')+';width:30%;text-align: center;background-color:#436593;font-weight:bold;left-padding:2%;right-padding:2%;">'+$(this).children('th:first').html()+'</td>');  
			        	 $(this).children('th:first').remove();
			        	 if(o<childCol){
			        	 dataRowTh=dataRowTh+('<td style="color:'+$(this).children('th:first').css('color')+';width:30%;text-align: center;background-color:#436593;font-weight:bold;left-padding:2%;right-padding:2%;">'+$(this).children('th:first').html()+'</td></tr>');
			        	 $(this).children('th:first').remove();
			        	 }/*else if (o==11) {
			        		 dataRowTh=dataRowTh+('<td style="color:'+$(this).children('th:first').css('color')+';width:30%;text-align: center;background-color:#436593;font-weight:bold;left-padding:2%;right-padding:2%;">'+$(this).children('th:first').html()+'</td>');
				        	 $(this).children('th:first').remove();
				        	 dataRowTh=dataRowTh+('<td style="color:'+$(this).children('th:first').css('color')+';width:30%;text-align: center;background-color:#436593;font-weight:bold;left-padding:2%;right-padding:2%;">'+$(this).children('th:first').html()+'</td>');
				        	 $(this).children('th:first').remove();
				        	 dataRowTh=dataRowTh+('<td style="color:'+$(this).children('th:first').css('color')+';width:30%;text-align: center;background-color:#436593;font-weight:bold;left-padding:2%;right-padding:2%;">'+$(this).children('th:first').html()+'</td>');
				        	 $(this).children('th:first').remove();
				        	 dataRowTh=dataRowTh+('<td style="color:'+$(this).children('th:first').css('color')+';width:30%;text-align: center;background-color:#436593;font-weight:bold;left-padding:2%;right-padding:2%;">'+$(this).children('th:first').html()+'</td>');
				        	 $(this).children('th:first').remove();
				        	 dataRowTh=dataRowTh+('<td style="color:'+$(this).children('th:first').css('color')+';width:30%;text-align: center;background-color:#436593;font-weight:bold;left-padding:2%;right-padding:2%;">'+$(this).children('th:first').html()+'</td></tr>');
				        	 $(this).children('th:first').remove();
						}*/
			        	 //}
			        	 dataRowTh=dataRowTh+"</tr>";
			        	
			        	 //dataTable.append(dataRowTh);
			         } 
			         else{
			        	 if(runOnlyOnce==1){
			        	 var myRowTd="";
			        	 if(i%2==0){
			        		 myRowTd='<tr style="background-color: #eee;">';
			        	 }
			        	 else{
			        		 myRowTd='<tr>'; 
			        	 }
			        myRowTd=myRowTd+('<td style="color:'+$(this).children('td:first').css('color')+'">'+$(this).children('td:first').html()+'</td>');
			        $(this).children('td:first').remove();
			        myRowTd=myRowTd+('<td style="color:'+$(this).children('td:first').css('color')+'">'+$(this).children('td:first').html()+'</td>');
			        $(this).children('td:first').remove();
			        myRowTd=myRowTd+('<td style="color:'+$(this).children('td:first').css('color')+'">'+$(this).children('td:first').html()+'</td>');
			        $(this).children('td:first').remove();
			        myRowTd=myRowTd+('<td style="color:'+$(this).children('td:first').css('color')+'">'+$(this).children('td:first').html()+'</td></tr>');
			        $(this).children('td:first').remove();
			        nt.append(myRowTd);		
			      
			        	 }
			        
			        	 if(i%2==0){
			        		 dataRowTd=dataRowTd+'<tr style="background-color: #eee;">';
			        	 }
			        	 else{
			        		 dataRowTd=dataRowTd+'<tr>'; 
			        	 }
			       
			       // for(che=0;che<2;che++){
			   	
	           //	  dataRowTd=('<td><table><tr><td style="color:'+$(this).children('th:first').css('color')+'">First Time Scoring</td></tr>');
	        	 dataRowTd=dataRowTd+('<td style="color:'+$(this).children('td:first').css('color')+';width:30%;text-align: center;">'+$(this).children('td:first').html()+'</td>');    
	        	 $(this).children('td:first').remove();
	        	 dataRowTd=dataRowTd+('<td style="color:'+$(this).children('td:first').css('color')+';width:30%;text-align: center;">'+$(this).children('td:first').html()+'</td>');  
	        	 $(this).children('td:first').remove();
	        	 if(o<childCol){
	        	 dataRowTd=dataRowTd+('<td style="color:'+$(this).children('td:first').css('color')+';width:30%;text-align: center;">'+$(this).children('td:first').html()+'</td>');
	        	 $(this).children('td:first').remove();
	        	 }/*else if (o==11) {
	        		 dataRowTd=dataRowTd+('<td style="color:'+$(this).children('td:first').css('color')+';width:30%;text-align: center;">'+$(this).children('td:first').html()+'</td>');
		        	 $(this).children('td:first').remove();
		        	 dataRowTd=dataRowTd+('<td style="color:'+$(this).children('td:first').css('color')+';width:30%;text-align: center;">'+$(this).children('td:first').html()+'</td>');
		        	 $(this).children('td:first').remove();
		        	 dataRowTd=dataRowTd+('<td style="color:'+$(this).children('td:first').css('color')+';width:30%;text-align: center;">'+$(this).children('td:first').html()+'</td>');
		        	 $(this).children('td:first').remove();
		        	 dataRowTd=dataRowTd+('<td style="color:'+$(this).children('td:first').css('color')+';width:30%;text-align: center;">'+$(this).children('td:first').html()+'</td>');
		        	 $(this).children('td:first').remove();
		        	 dataRowTd=dataRowTd+('<td style="color:'+$(this).children('td:first').css('color')+';width:30%;text-align: center;">'+$(this).children('td:first').html()+'</td>');
		        	 $(this).children('td:first').remove();
				}*/
	        	
			       // }
			        dataRowTd=dataRowTd+"</tr>";
			       
			       
			         }
			       
			    });
		  runOnlyOnce++;
		 dataRowTdFinal=dataRowTdFinal+dataRowTh+dataRowTd;
		 dataRowTdFinal=dataRowTdFinal+"</table></td></tr></table></td> ";
			    }
			    dataRowTdFinal=dataRowTdFinal+" </table></td>";
		 
		  dataTable.append(dataRowTdFinal);
			   
			    // remove original first column
		         var newSpan1=$('span.pagebanner').clone();		     
		         var newSpan2=$('span.pagelinks').clone();		      
			    (newSpan1).appendTo('#nameTableSpan1');			
			    (newSpan2).appendTo('#nameTableSpan1');	
			 
			    
			    $('#ladderDiv span').remove();
			    nt.appendTo('#nameTableSpan');		    
			    dataTable.appendTo('#dataTableSpan');	
			    $('#nameTable th').css('background-color','#436593');
			    $('#nameTable th').css('font-weight','bold');
			    $('#dataTable th').css('background-color','#436593');
			    $('#dataTable th').css('font-weight','bold');
			}		
		 
		 function scrolify(tblAsJQueryObject, width){
		        var oTbl = tblAsJQueryObject;
		        var oTblDiv = $("<div/>");
		        oTblDiv.css('width', width);
		       // oTblDiv.css('overflow-x','scroll');             
		        oTbl.wrap(oTblDiv); 
		    }
		 
		 $(document).ready(function(){
			$('a').click(function(e){
				var href = $(this).attr('href');
				if(href.indexOf("dailyStatusQuestionDetails.action") >= 0){
					var curPageNum = $.urlParam('d-49216-p');
					if(curPageNum != null){
						e.preventDefault();
						href = href + "&pageNum="+curPageNum;
						window.location.href = href;
					}
				}
			}); 
			$.urlParam = function(name){
			    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
			    if (results==null){
			       return null;
			    }
			    else{
			       return results[1] || null;
			    }
			}
		});