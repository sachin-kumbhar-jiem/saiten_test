$(document).ready(function(){
	
	$("#dailyReportDownload").click(function(){ 
		$("#dataNotFound").empty();   
		if($("#rating_progress").is(':checked') 
				|| $("#analysis_report").is(':checked') 
				|| $("#summDiscpAnalyRpt").is(':checked')){
			$("#rtgProgRpt").valid();
		    return true;
		} else {   
			$("#rtgProgRpt").valid(); 
			return false;			
		}	
	});
	
	$('#endHours').change(function () {
		 $("#endMinutes").valid(); 
	   }
    );
	
	$('#startDate').change(function () {
	      $("#startDate").valid();
		}
	);

	$('#endDate').change(function () {
    	 $("#endDate").valid();
    	 $("#endMinutes").valid(); 
       }
	);
	
	$("#rating_progress").click(function(){
		$("#rtgProgRpt").valid();
	});
	
	$("#analysis_report").click(function(){
		$("#rtgProgRpt").valid();
	});
	
	$("#summDiscpAnalyRpt").click(function(){
		$("#rtgProgRpt").valid();  
	});
	
	$.validator.addMethod("startDateCheck", startDateCheck, "");
	$.validator.addMethod("endDateCheck", endDateCheck, "");
	$.validator.addMethod("dateCompare", dateCompare, "");
	$.validator.addMethod("timeCheck", timeCheck, ""); 
	$.validator.addMethod("startAndCurrentDateCheck", startAndCurrentDateCheck, "");
	$.validator.addMethod("endAndCurrentDateCheck", endAndCurrentDateCheck, "");
	$.validator.addMethod("downloadReport", downloadReport, "");
	
    // validation implementation will go here. 
	    
	$("#dailyScoreReportForm").validate({  
		rules: {	
			"downloadInfo.startDate" : { 
      				startDateCheck: true,
      				startAndCurrentDateCheck:true
    		},
    		"downloadInfo.endDate":{
    			endDateCheck:true,
    			dateCompare:true,
    			endAndCurrentDateCheck:true
    		},
    		"downloadInfo.endMinutes":{
    			timeCheck :true
    		},
    		"rtgProgRpt":{
    			downloadReport :true
    		}
	    }, 
	    messages: {
	    	"downloadInfo.startDate" : {
	            	startDateCheck:START_DATE_REQUIRED,
	            	startAndCurrentDateCheck:START_DATE_IS_GREATER_THAN_CURRENT_DATE
	        }, 
	        "downloadInfo.endDate":{
	        	endDateCheck :END_DATE_REQUIRED,
	        	dateCompare:START_AND_END_DATE_COMPARISON, 
	        	endAndCurrentDateCheck:END_DATE_IS_GREATER_THAN_CURRENT_DATE
	        },
	        "downloadInfo.endMinutes":{
	        	timeCheck:TIME_COMPARISON
	        },
	        "rtgProgRpt":{
	        	downloadReport:REPORT_CANNOT_BE_DOWNLOADED
    		}
	    }	    
	});  	
}); 

function resetDate() {
	var sDate = document.getElementById("startDate");
	var eDate = document.getElementById("endDate");
	sDate.value = '';
	eDate.value = '';
} 

function commonTimeLogic(){
	var sHours = document.getElementById("startHours").value;
	var sMinutes = document.getElementById("startMinutes").value;
	var eHours = document.getElementById("endHours").value;
	var eMinutes = document.getElementById("endMinutes").value;	
	var startTime = (parseInt(sHours) * 60) + parseInt(sMinutes);
	var endTime = (parseInt(eHours) * 60) + parseInt(eMinutes);
	var diff = endTime - startTime;
	if (diff <= 0) { 
		return false;
	}else{
		return true;
	} 
} 

function timeCheck() { 
	var startDate=document.getElementById("startDate").value;
	var endDate=document.getElementById("endDate").value;  
	var sDate=new Date(startDate);
	var eDate=new Date(endDate); 
	var dailyReports=document.getElementById("reports");
	var summDiscpAnalyRpt = document.getElementById("summDiscpAnalyRpt");
	
	if(dailyReports.checked==true){
		return commonTimeLogic();
	}
	if(summDiscpAnalyRpt.checked==true){
		if(sDate.getTime() == eDate.getTime()){ 
			return commonTimeLogic();
		}else{
			return true; 
		}
	}
}  

function startAndCurrentDateCheck(){ 
	var startDate = document.getElementById('startDate').value;
	var sDate=new Date(startDate);
	var currentDate=new Date();
		if(sDate-currentDate===0){
			return true;
		}else if(sDate-currentDate > 0){
			return false
		}else if(sDate-currentDate < 0){ 
			return true;
		}else{
			return false;
		}
} 

function endAndCurrentDateCheck(){
	var endDate=document.getElementById('endDate').value; 
	var summDiscpAnalyRpt = document.getElementById("summDiscpAnalyRpt");
	var eDate=new Date(endDate);   
	var currentDate=new Date(); 
	
	if(summDiscpAnalyRpt.checked == true){
		if(eDate-currentDate === 0){
				return true;
		}else if(eDate-currentDate > 0){
				return false;
		}else if(eDate-currentDate < 0){
				return true;
		}else{
		  		return false;
		} 
    }
} 

function startDateCheck() {
	var startDate = document.getElementById('startDate').value;
	var sDate = new Date(startDate); 	
	if (startDate == '') {
		return false;
	} else { 
		return true;
	}
} 

function endDateCheck(){
	var endDate=document.getElementById('endDate').value; 
	var eDate=new Date(endDate); 
	var summDiscpAnalyRpt = document.getElementById("summDiscpAnalyRpt");
	if(summDiscpAnalyRpt.checked==true){
		if(endDate==''){
			return false;
		}else{
			return true;
		} 		
    }else{
	   return false;
    }
} 

function dateCompare(){
	var endDate=document.getElementById('endDate').value; 
	var startDate = document.getElementById('startDate').value;
	var sDate=new Date(startDate);
	var eDate=new Date(endDate); 
	var summDiscpAnalyRpt = document.getElementById("summDiscpAnalyRpt");
	if(summDiscpAnalyRpt.checked==true){
		if(sDate > eDate){
			return false;
		}else{
			return true;
		}
	}else{
		return false;
	}
}   

function downloadReport(dailyScoreReportForm){ 
	
	var reports = document.getElementById("reports"); 
	var RatingProgRpt=document.getElementById("rating_progress");  
	var DiscpAnalyRpt=document.getElementById("analysis_report"); 
	var summDiscpAnalyRpt = document.getElementById("summDiscpAnalyRpt");
	 
	if(reports.checked==true){
		 if(RatingProgRpt.checked==true || DiscpAnalyRpt.checked==true){
			 return true;
		 }else{
			 return false;
		 }
	} 
	if(summDiscpAnalyRpt.checked==true){
		 return true;
	}
}

function dailyReports() {
	var reports = document.getElementById("reports");
	if (reports.checked == true) { 
		
		$("#ratingProgRpt").find("input[type='checkbox']").prop("checked",true);  
		$("#discpAnalysisReport").find("input[type='checkbox']").prop("checked",true);  
		
		$('#ratingProgRpt :input').attr('disabled', false);  
		$('#discpAnalysisReport :input').attr('disabled', false);     
		document.getElementById("endDate").disabled = true;   	
	}
} 

function summaryReports() {
	var summDiscpAnalyRpt = document.getElementById("summDiscpAnalyRpt");
	if (summDiscpAnalyRpt.checked == true) { 
		
		$('#ratingProgRpt :input').attr('disabled', true);  
		$('#discpAnalysisReport :input').attr('disabled', true);  
		document.getElementById("endDate").disabled = false; 
		
		$('#ratingProgRpt').find('input[type=checkbox]:checked').removeAttr('checked');  
		$('#discpAnalysisReport').find('input[type=checkbox]:checked').removeAttr('checked');  	  	
	}
} 