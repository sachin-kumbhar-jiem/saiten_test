function checkForFromValidDay(value, element)
{

	var fromValidYear=value.split("#")[0]
	var fromValidMonth=value.split("#")[1]
	var fromValidDay=value.split("#")[2]
	
	if((fromValidYear!="-1")&&(fromValidMonth!="-1")&&(fromValidDay!="-1")&&(fromValidMonth!="2")){
	var dayobj = new Date(fromValidYear, fromValidMonth-1, fromValidDay)
	if ((dayobj.getMonth()+1!=fromValidMonth)||(dayobj.getDate()!=fromValidDay)||(dayobj.getFullYear()!=fromValidYear))
	{
		return false; 
	}
	else{
		return true; 	
	 }
	}
	else{
		return true;
	}
}
  function checkForToValidDay(value, element)
{

	var toValidYear=value.split("#")[0]
	var toValidMonth=value.split("#")[1]
	var toValidDay=value.split("#")[2]
	
	if(toValidMonth!="2"){
	var dayobj = new Date(toValidYear, toValidMonth-1, toValidDay)
	if ((dayobj.getMonth()+1!=toValidMonth)||(dayobj.getDate()!=toValidDay)||(dayobj.getFullYear()!=toValidYear))
	{
		return false; 
	}
	 else{
		return true; 	
	 }
	}
	else{
		return true;
	}
}

 //Check "toDate should not greater than start date".
  function checkdate(value, element)
  {
  	
	var fromDateStr=value.split("/")[0]
  	var toDateStr=value.split("/")[1]
  		
  	var fromValidYear=fromDateStr.split("#")[0]
  	var fromValidMonth=fromDateStr.split("#")[1]
  	var fromValidDay=fromDateStr.split("#")[2]
	var fromValidHours=fromDateStr.split("#")[3]
	var fromValidMin=fromDateStr.split("#")[4]
  	
  	var toValidYear=toDateStr.split("#")[0]
  	var toValidMonth=toDateStr.split("#")[1]
  	var toValidDay=toDateStr.split("#")[2]
	var toValidHours=toDateStr.split("#")[3]
	var toValidMin=toDateStr.split("#")[4]
  	
  	var fromDate= new Date(fromValidYear, fromValidMonth-1, fromValidDay, fromValidHours, fromValidMin, 00, 00);
  	var toDate= new Date(toValidYear, toValidMonth-1, toValidDay, toValidHours, toValidMin, 00, 00); 
  	
  	if(fromDate>toDate){
  		return false;
  	}
  	else{
  		return true;
  	}
  }
  
  function checkLeapYearFromDate(value, element)
  {
  	
	var fromDateStr=value.split("/")[0]
  	var toDateStr=value.split("/")[1]
  	
  	var fromValidYear=fromDateStr.split("#")[0]
  	var fromValidMonth=fromDateStr.split("#")[1]
  	var fromValidDay=fromDateStr.split("#")[2]
  	
  	var toValidYear=toDateStr.split("#")[0]
  	var toValidMonth=toDateStr.split("#")[1]
  	var toValidDay=toDateStr.split("#")[2]
  	if(isLeapYear(fromValidYear)==false)
  	{
  		if(fromValidMonth=="2" && fromValidDay=="29"){
  			return false;
  		}
  		else{
  			return true;
  		}
  	}
  	else{
  		return true;
  	} 
  }		
    function checkLeapYearToDate(value, element)
  {
 	
    var fromDateStr=value.split("/")[0]
  	var toDateStr=value.split("/")[1]

  	var fromValidYear=fromDateStr.split("#")[0]
  	var fromValidMonth=fromDateStr.split("#")[1]
  	var fromValidDay=fromDateStr.split("#")[2]
  	
  	var toValidYear=toDateStr.split("#")[0]
  	var toValidMonth=toDateStr.split("#")[1]
  	var toValidDay=toDateStr.split("#")[2]
  	if(isLeapYear(toValidYear)==false)
  	{
  		if(toValidMonth=="2" && toValidDay=="29"){
  			return false;
  		}
  		else{
  			return true;
  		}
  	}
  	else{
  		return true;
  	} 
  	
  }		

  	function isLeapYear(year)
  {
  	if(year%400 ==0 || (year%100 != 0 && year%4 == 0)){
        return true;
     }else{
     	  return false;	
     }
  	
  }
  	function checkLeapYearFromDateToDate(value, element)
  {
  	if((checkLeapYearFromDate(value,element)==false)&&(checkLeapYearToDate(value,element)==false)){
  		return false;
  	}
  	else{
  		return true;
  	}
  }
  	
	function checkFromDateFebruaryMonth(value, element)
	{

		var fromDateStr=value.split("/")[0]
		var toDateStr=value.split("/")[1]

		var fromValidYear=fromDateStr.split("#")[0]
		var fromValidMonth=fromDateStr.split("#")[1]
		var fromValidDay=fromDateStr.split("#")[2]
		
		var toValidYear=toDateStr.split("#")[0]
		var toValidMonth=toDateStr.split("#")[1]
		var toValidDay=toDateStr.split("#")[2]

		if(fromValidMonth=="2")
		{
			if(fromValidDay=="30"||fromValidDay=="31"){
				return false;
			}
			else{
				return true;
			}
		}
		else{
			return true;
		} 
				
	}		
		function checkToDateFebruaryMonth(value, element)
	{
		
		var fromDateStr=value.split("/")[0]
		var toDateStr=value.split("/")[1]
		
		var fromValidYear=fromDateStr.split("#")[0]
		var fromValidMonth=fromDateStr.split("#")[1]
		var fromValidDay=fromDateStr.split("#")[2]
		
		var toValidYear=toDateStr.split("#")[0]
		var toValidMonth=toDateStr.split("#")[1]
		var toValidDay=toDateStr.split("#")[2]
		if(toValidMonth=="2")
		{
			if(toValidDay=="30"||toValidDay=="31"){
				return false;
			}
			else{
				return true;
			}
		}
		else{
			return true;
		} 
	}		

		function checkFebruaryMonthFromDateToDate(value, element)
	{
		if((checkFromDateFebruaryMonth(value, element)==false)&&(checkToDateFebruaryMonth(value, element)==false)){
			return false;
		}
		else{
			return true;
		}
	}