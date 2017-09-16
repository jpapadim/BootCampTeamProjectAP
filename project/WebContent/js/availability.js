function addAdvancedSchedule(){
				for(var i=1;i<6;i++){
					if(document.getElementById('schedule'+i).style.display=="none"){
						document.getElementById('schedule'+i).style.display="block";
						document.getElementById('numSchedules').value=String(i);
						if(i==5)document.getElementById('addAdvScheduleButton').style.display="none";
						document.getElementById('removeAdvScheduleButton').style.display="block";				
						break;
					}
				}
			}
            function removeAdvancedSchedule(){
            	for(var i=5;i>0;i--){
					if(document.getElementById('schedule'+i).style.display!="none"){
						document.getElementById('schedule'+(i)).style.display="none";
						document.getElementById('numSchedules').value=String(i-1);
						if(i==1)document.getElementById('removeAdvScheduleButton').style.display="none";
						document.getElementById('addAdvScheduleButton').style.display="block";
						break;
					}
				}
			}
            function setAdvancedSchedule(){
            	var numOfScOpen=parseInt(document.getElementById('numSchedules').value);
            	document.getElementById('removeAdvScheduleButton').style.display="block";
            	document.getElementById('addAdvScheduleButton').style.display="block";
            	if(numOfScOpen==0)document.getElementById('removeAdvScheduleButton').style.display="none";
            	if(numOfScOpen==5)document.getElementById('addAdvScheduleButton').style.display="none";
				for(var i=1;i<6;i++){
					if(i<=numOfScOpen) {document.getElementById('schedule'+i).style.display="block";}
					else {document.getElementById('schedule'+i).style.display="none";}
				}
			}
            
            function checkboxListenerDay(num) {
        		if (document.getElementById('alldayCheck' + num).checked == true) {
        			//document.getElementById('allmonthCheck'+num).checked=false;
        			document.getElementById('endday' + num).selectedIndex = 0;
        			document.getElementById('endday' + num).disabled = true;
        			document.getElementById('startday' + num).selectedIndex = 0;
        			document.getElementById('startday' + num).disabled = true;
        			document.getElementById('startday' + num).options[0].disabled=false;
        			document.getElementById('endday' + num).options[0].disabled=false;
        		} else {
        			//document.getElementById('allmonthCheck'+num).checked=true;
        			if(document.getElementById('endday' + num).selectedIndex==0)
        			document.getElementById('endday' + num).selectedIndex = 1;
        			document.getElementById('endday' + num).disabled = false;
        			if(document.getElementById('startday' + num).selectedIndex==0)
        			document.getElementById('startday' + num).selectedIndex = 1;
        			document.getElementById('startday' + num).disabled = false;
        			document.getElementById('startday' + num).options[0].disabled=true;
        			document.getElementById('endday' + num).options[0].disabled=true;
        		}

        	}
        	function checkboxListenerMonth(num) {
        		if (document.getElementById('allmonthCheck' + num).checked == true) {
        			//document.getElementById('allmonthCheck'+num).checked=false;
        			document.getElementById('endmonth' + num).selectedIndex = 0;
        			document.getElementById('endmonth' + num).disabled = true;
        			document.getElementById('startmonth' + num).selectedIndex = 0;
        			document.getElementById('startmonth' + num).disabled = true;
        			document.getElementById('startmonth' + num).options[0].disabled=false;
        			document.getElementById('endmonth' + num).options[0].disabled=false;
        		} else {
        			//document.getElementById('allmonthCheck'+num).checked=true;
        			if(document.getElementById('endmonth' + num).selectedIndex==0)
        			document.getElementById('endmonth' + num).selectedIndex = 1;
        			document.getElementById('endmonth' + num).disabled = false;
        			if(document.getElementById('startmonth' + num).selectedIndex==0)
        			document.getElementById('startmonth' + num).selectedIndex = 1;
        			document.getElementById('startmonth' + num).disabled = false;
        			document.getElementById('startmonth' + num).options[0].disabled=true;
        			document.getElementById('endmonth' + num).options[0].disabled=true;
        		}

        	}
        	function checkboxListenerTime(num) {
        		if (document.getElementById('alltimeCheck' + num).checked == true) {
        			//document.getElementById('allmonthCheck'+num).checked=false;
        			document.getElementById('endtime' + num).value = 'All Hours';
        			document.getElementById('endtime' + num).disabled = true;
        			document.getElementById('starttime' + num).value = 'All Hours';
        			document.getElementById('starttime' + num).disabled = true;
        		} else {
        			//document.getElementById('allmonthCheck'+num).checked=true;
        			if(document.getElementById('endtime' + num).value=='All Hours')
        			document.getElementById('endtime' + num).value = '';
        			document.getElementById('endtime' + num).disabled = false;
        			if(document.getElementById('starttime' + num).value=='All Hours')
        			document.getElementById('starttime' + num).value = '';
        			document.getElementById('starttime' + num).disabled = false;
        		}
        	}

        	$(function() {
				$('#datetimepicker1').datetimepicker();
				$('#datetimepicker2').datetimepicker({
					useCurrent : false
				//Important! 
				});
				$("#datetimepicker1").on(
						"dp.change",
						function(e) {
							$('#datetimepicker2').data(
									"DateTimePicker").minDate(
									e.date);
						});
				$("#datetimepicker2").on(
						"dp.change",
						function(e) {
							$('#datetimepicker1').data(
									"DateTimePicker").maxDate(
									e.date);
						});

			});