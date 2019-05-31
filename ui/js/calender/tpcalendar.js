function Calendar(name,year,month) {
	this.calendarString = null;
    this.calendar = null;
	this.Month = Today.getMonth();
    this.Year = Today.Year;  
    this.ThisDate = 0;
    this.weekHeadYear = new Array("Su","Mo","Tu","We","Th","Fr","Sa"),
    this.weekHeadMonth = new Array("Sun","Mon","Tue","Wed","Thu","Fri","Sat");
    this.monthNames = new Array("Jan","Feb","Mar","Apr","May","Jun","Jul", 
                                "Aug","Sep","Oct","Nov","Dec");
    this.fullMonthNames = new Array("January","February","March","April","May","June","July", 
                                "August","September","October","November","December");
    this.firstDay = 0;
	this.selectedWeek = 0;
	this.index = 0;
	// This array is for purpose of sending request to get appointments.
	// Every time the up/ down arrow key pressed, the entry for currently 
	// selected week is placed into this array. Before sending request to get 
	// appointments, it is checked that the last entry matches the selected week.
	// If so, request is sent.
	this.weeksArray = new Array();
	
	this.init = Calendar.init;	
    this.monthLen = Calendar.monthLen;
    this.monthName = Calendar.monthName;
    this.fullMonthName = Calendar.fullMonthName;
    this.setMonthNames = Calendar.setMonthNames;
    this.setYearWeek = Calendar.setYearWeek;
    this.setMonthWeek = Calendar.setMonthWeek;
    this.setMonth = Calendar.setMonth;
    this.setYear = Calendar.setYear;
	
	this.getFormattedYearString = Calendar.getFormattedYearString;
	this.getDayOfSelectedWeek = Calendar.getDayOfSelectedWeek;
	
    if (!window.calendars)  window.calendars = new Array();
    this.name = name  || "Calendar"+ window.calendars.length;
    window.calendars[this.name] = this;
    window.calendars[window.calendars.length] = this;
    
    this.setMonth(month,year);
}

Calendar.monthLen = function(month, year) {
    var len = ((month % 7) % 2 == 1)?  30 : 31;
    if (month == 1)  len = (year % 4 == 0)?  29 : 28;
    return len;
}

Calendar.monthName = function(month) {
    return this.monthNames[month];
}

Calendar.fullMonthName = function(month) {
    return this.fullMonthNames[month];
}

Calendar.setMonthNames = function() {
    var args = Calendar.setMonthNames.arguments;
    for (var i = 0; i<args.length; i++) 
      this.monthNames[i] = args[i];
}

Calendar.setYearWeek = function(su,mo,tu,we,th,fr,sa) {
    var args = Calendar.setYearWeek.arguments;
    for (var i = 0; i<args.length; i++) 
      this.weekHeadYear[i] = args[i];
}

Calendar.setMonthWeek = function(su,mo,tu,we,th,fr,sa) {
    var args = Calendar.setMonthWeek.arguments;
    for (var i = 0; i<args.length; i++) 
      this.weekHeadMonth[i] = args[i];      
}

Calendar.setMonth = function(month, year) {
    if ((month == 0) || month) { 
      if (typeof(month) == 'number' && (month >= 0 && month < 12)) 
          this.Month = month;			
      else if (month == 'today') {
          this.Year = Today.Year;
          this.Month = Today.getMonth();
      }
      else if (month == 'prev') {
          if (--this.Month == -1) {
              this.Month = 11;
              --this.Year;
          }
      }
      else if (month == 'next') {
          if (++this.Month == 12) {
              this.Month = 0;
              ++this.Year;
          }
      }
      else if (month != 'now') 
          alert('Unknown month: '+ month);
    }
    if (typeof(year) != 'undefined')  this.setYear(year);
    this.ThisDate = (this.Month == Today.getMonth() && this.Year == Today.Year)?  Today.getDate() : 0;
}

Calendar.setYear = function(year) {
    if (typeof(year) != 'undefined') {
      if (typeof(year) == 'number') {
          this.Year = (year < 100 && year >= 0)?  year + 1900 : year;
      }
      else if (year == 'today')
          this.Year = Today.Year;
      else if (year == 'prev')
          --this.Year;
      else if (year == 'next')
          ++this.Year;
      else if (year != 'now') 
          alert('Unknown year: '+ year);
    }
    this.ThisDate = 0;
}

Calendar.getFormattedYearString = function(year) {
	return year.toString().substring(2, 4);
}

Calendar.getDayOfSelectedWeek = function(dayNumber) {
	val = (this.selectedWeek * 7);
	return this.calendar[val + parseInt(dayNumber)];
}

Calendar.init = function() {	
	this.calendar = new Array(784);
	
	//this.setMonth("today", "now");
	this.setMonth(this.Month, this.Year);
    var first = new Date(this.Year,this.Month,1);
    var day = 0;
    var lastDate = this.monthLen(this.Month, this.Year);
	day = first.getDay();
	var padding = 0;
    //if (day != this.firstDay) {
		padding = ((day+7-this.firstDay)%7) - 1;
		//padding = 1;
	//}	
	var currentDate;
	if (Today.getFullYear() == this.Year && Today.getMonth() == this.Month) {
		currentDate = Today;
	} else {
		var currentDate =new Date(this.Year,this.Month,1);
	}	
	var dd = currentDate.getDate();
	var mm = currentDate.getMonth();
	var yyyy = currentDate.getFullYear();
	
	// Current month
	var indx = 364 + padding - 1;
    for (date=1; date <= lastDate; date++) {
        this.calendar[indx + date] = date + '-' + (mm + 1) + '-' + yyyy;     
		if (date == dd) {
			this.selectedWeek = ((indx + date) - ((indx + date) % 7))/7;
		}
    }
	
	// Previous months
	var prevMonth = mm;
	var prevMonthYear = yyyy;
	for (var indx = (364 + padding -1); indx >= 0;) {
		prevMonth = ((prevMonth - 1) == -1) ? 11 : (prevMonth -1);
		prevMonthYear = (prevMonth == 11) ? (prevMonthYear - 1) : prevMonthYear;

		month_len = this.monthLen(prevMonth, prevMonthYear);
		for (date = month_len; date >= 1; date--) {
			this.calendar[indx--] = date + '-' + (prevMonth + 1) + '-' + prevMonthYear;        
			if (indx < 0) {
				break;
			}
		}
	}

	// Next months
	var nextMonth = mm;
	var nextMonthYear = yyyy;
	for (var indx = (364 + padding + lastDate); indx < 784;) {
		nextMonth = ((nextMonth + 1) == 12) ? 0 : (nextMonth + 1);
		nextMonthYear = (nextMonth == 0) ? (nextMonthYear + 1) : nextMonthYear;

		month_len = this.monthLen(nextMonth, nextMonthYear);
		for (date = 1; date <= month_len; date++) {
			this.calendar[indx++] = date + '-' + (nextMonth + 1) + '-' + nextMonthYear;        
			if (indx >= 784) {
				break;
			}
		}
	}
	this.calendarString = this.calendar.toString();
}

if (!Today) var Today = new Date();
if (!Today.Year) {
    Today.Year = Today.getFullYear();
    //if (navigator.appName.indexOf("Netscape") != -1 || (Today.Year < 100 && Today.Year >= 0)) 
      //  Today.Year += 1900;
}

function scroll(cal, obj, dir) {	
	if (obj != null) {
		rows = obj.rows;
		if (rows) {
			for (var i = 0; i < rows.length; i++) {
				row = rows[i];
				cells = row.cells;
				if (cells && cells.length > 0) {	
					class_name = cells[0].className;						
					if (class_name == 'TPCell') {
						rowId = row.id;
						if (rowId == '-2') {
							if (dir == 'down') {
								cal.index-=7;
							}
						} else if (rowId == '5') {
							if (dir == 'up') {
								cal.index+=7;
							}
						} else {
							// do Nothing.
						}
					}
				}
			}
		}
	} else if (dir != null) {			
		if (cal.index > 27 && dir == 'down') {
			cal.index-=28;
		} else if (cal.index < 728 && dir == 'up') {	
			cal.index+=28;
		} else {
			return;
		}
	} else {
		if (cal.selectedWeek > 1) {
			cal.index = ((cal.selectedWeek - 2) * 7);
		} else {
			cal.index = 0;
		}
	}
	cal.weeksArray[cal.weeksArray.length] = cal.selectedWeek;
	refreshTPCalendar(cal);
	return false;
}

function refreshTPCalendar(cal) {
	var curr_dt = new Date();
	var curr_date = curr_dt.getDate() + '-' + (curr_dt.getMonth() + 1) + '-' + curr_dt.getFullYear();
	indx = cal.index;	
	
	// Clear Month and Year values on scroll bar.
	for (var k = -1; k < 5; k++) {
		scrollbarElem = document.getElementById(k + '7');
		if (scrollbarElem) {
			scrollbarElem.innerHTML = '';
		}
	}

	for (var i = 0; i < 8; i++)	{	
		tempI1 = i - 2;
		tempI2 = i - 1;
		for (var j = 0; j < 7; j++) {
			dt = cal.calendar[indx++];			
			parts = dt.split('-');
			elem = document.getElementById((i - 2) + '' + j);
			fontWt = (dt == curr_date) ? 'bold' : 'normal';
			if (elem) {
				class_name = '';
				if ((((indx -1) - ((indx -1) % 7)) / 7) == cal.selectedWeek) {
					class_name = 'TPCell';
				} else {
					class_name = (parts[1] % 2 == 0) ? 'Even' : 'Odd';
				}			
				elem.innerHTML = parts[0];
				elem.className = class_name;
				elem.style.fontWeight = fontWt;				
			}
			
			// Write Month on scroll bar.
			class_name = (parts[1] % 2 == 0) ? 'ScrollElemEven' : 'ScrollElemOdd';
			scrollbarElem = document.getElementById(tempI1 + '' + 7);
			if (scrollbarElem) {
				scrollbarElem.className = class_name;
				if (parts[0] == '8' && tempI1 != -2 && tempI1 != 5) {
					scrollbarElem.innerHTML = cal.monthName(parts[1] - 1);
				}
				
			}
			
			//Write Year on scroll bar.
			if (tempI2 != -2 && tempI2 != 5) {					
				scrollbarElem = document.getElementById(tempI2 + '' + 7);					
				if (scrollbarElem) {
					if (parts[0] == '8') {	
						scrollbarElem.innerHTML = cal.getFormattedYearString(parts[2]);			 
					}						
				}
			}						
		}
	}
	return false;
}

function mouseClickEvent(cal, dest) {	
	cal.selectedWeek = ((cal.index - (cal.index % 7)) / 7) + dest;
	refreshTPCalendar(cal);
	cal.weeksArray[cal.weeksArray.length] = cal.selectedWeek;
	updateRightPanel(cal);
	return false;
}

function appointmentDateChanged(selectedWeek) {
	cal.selectedWeek = selectedWeek;
	refreshTPCalendar(cal);
	cal.weeksArray[cal.weeksArray.length] = cal.selectedWeek;
	updateRightPanel(cal);
	return false;
}

function keyDownEvent(dir, cal, obj) {
	if(dir == 'down') {		
		if (cal.selectedWeek == 0) {
			// do nothing
			return;
		} else {
			cal.selectedWeek-=1;
		}			
	} else if (dir == 'up') {		
		if (cal.selectedWeek == 111) {
			// do nothing
			return;
		} else {
			cal.selectedWeek+=1;
		}
	}
	scroll(cal, obj, dir);			
	window.setTimeout('updateRightPanel(cal)',50);
}

function updateRightPanel(cal) {
	var curr_dt = new Date();
	var curr_date = curr_dt.getDate() + '-' + (curr_dt.getMonth() + 1) + '-' + curr_dt.getFullYear();
	
	var monthYear = '';
	var currentWeekDays = '';
	for (var i = 0; i < 8; i++) {
		dt = cal.calendar[eval(cal.selectedWeek * 7) + i];		
		if (dt) {
			parts = dt.split('-');
			if (currentWeekDays.length > 0) {
				currentWeekDays += ',';
			}
			currentWeekDays += parts[2];
			currentWeekDays += '-' + parts[1];
			currentWeekDays += '-' + parts[0];
			if (i == 7) {
				continue;
			}
			elem = document.getElementById('day' + i);
			elem2 = document.getElementById('day00' + i);
			elem3 = document.getElementById('day000' + i);
			class_name = (parts[1] % 2 == 0) ? 'Even' : 'Odd';
			if (elem) {
				if ((dt == curr_date)) {
					elem.innerHTML = '<strong>' + parts[0] + '</strong>';			
					fontWt = 'bold';
				} else {
					elem.innerHTML = parts[0];			
					fontWt = 'normal';
				}
				elem3.style.fontWeight = fontWt;
				//elem.className = class_name;
			}
			if (elem2) {
				var curr_class = elem2.className;
				var tempParts = curr_class.split(' ');				
				if (tempParts.length == 2) {
					class_name += ' '  + tempParts[1];
				}
				elem2.className = 'appointments' + class_name;
			}
			temp = cal.fullMonthName(parts[1] - 1) + ' ' + parts[2];
			if (monthYear == '') {
				monthYear = temp;
			} else if (monthYear == temp) {
				// do Nothing.
			} else if (monthYear.indexOf('/') == -1) {
				/*if (parts[2] == monthYear.substring(monthYear.length - 4, monthYear.length)) {
					monthYear = monthYear.substring(0, monthYear.length - 4);
				} else {
					monthYear = monthYear;
				}*/
				monthYear = monthYear.substring(0, monthYear.length - 4);
				monthYear += '/' + temp;
				//monthYear = monthYear.substring(0, monthYear.length - 4) + ' / ' + temp;
			}
		}
	}
	var mo = monthYear.substring(0, monthYear.length - 4);
	var yr = monthYear.substring(monthYear.length - 4, monthYear.length);
	document.getElementById('monthYear').innerHTML = '<strong>' + mo + '</strong>' + ' ' + yr;
	updateCalendarForm(currentWeekDays);
	
	//fetchAppointmentsForCurrentWeek();
	
	if (cal.weeksArray[cal.weeksArray.length - 1] == cal.selectedWeek) {
		cal.weeksArray = new Array();
		fetchAppointmentsForCurrentWeek(null);
	}
}

function updateCalendarForm(currentWeekDays) {
	document.calendarForm.currentWeekDays.value = currentWeekDays;
}

function updateAppointments(request){
    if(!checkSessionExpiry(request)) {
    	return;
    }
	var xmlFile = request.responseXML;
	var root = xmlFile.getElementsByTagName("week")[0];
	var days = root.getElementsByTagName("day");
	for (var i = 0; i < days.length; i++) {
		var day = days[i];
		var tbl = document.getElementById('day0' + i);
		if (tbl) {
			rows = tbl.rows;
			if (rows && rows.length > 0) {
				for (var j = rows.length - 5; j >= 0; j--) {
					tbl.deleteRow(j);
				}
			}
		}		
		appointments = day.getElementsByTagName("appointment");
		if (appointments && appointments.length > 0) {
			for (var j = 0; j < appointments.length; j++) {
				appointment = appointments[j];
				newRow = tbl.insertRow(j);
				
				var appointmentId = appointment.getAttribute("id");
				var isFullyEditable = appointment.getAttribute("isFullyEditable");
				var applicantId = getSingleElement(appointment,"applicantId","");
				var applicantName = getSingleElement(appointment,"name","");
				var path = getSingleElement(appointment,"path","");
				var interviewer = getSingleElement(appointment,"interviewer","");
				var from = getSingleElement(appointment,"from","");
				var to = getSingleElement(appointment,"to","");
				var subject = getSingleElement(appointment,"subject","");
				var position = getSingleElement(appointment,"position","");
				var status = getSingleElement(appointment,"status","");
				var type = getSingleElement(appointment,"type","");
				
				var cell = newRow.insertCell(0);
				if (j == (appointments.length - 1)) {
					cell.className = "containerNoBorder";	
				} else {
					cell.className = "container";
				}
				cell.title = getCellTitle(applicantName, subject, interviewer, from, to, status);
				cell.innerHTML = getInnerTextOfCell(appointmentId, applicantId, applicantName, subject, from, to, i, isFullyEditable, path, type);
			}
		}
	}
}

function getInnerTextOfCell(appointmentId, applicantId, applicantName, position, from, to, dayNumber, isFullyEditable, path, type) {	
	var text = '';
	if(type == 'appointment') {
		text += '<table class=appointment id=appt_' + appointmentId + '_' + dayNumber + '_' + isFullyEditable + '_' + applicantId + '_' + path + ' onclick="javascript: showEditAppointmentScreen(event,' + appointmentId + ',' + dayNumber + ',' + isFullyEditable + '); stopEventPropagation(event);">';		
	} else {
		text += '<table class=appointment style="background:#FAFAD2;" id=reminder_' + appointmentId + '_' + applicantId  + ' onclick="javascript: onClickReminder(' + appointmentId + ',' + applicantId +');stopEventPropagation(event);">';	
	}	
	
	durationVal = '';
	if (from.length > 0) {
		durationVal += from;		
	}
	
	if (from.length > 0 && to.length > 0) {
		durationVal += ' to ';
	}
	if (to.length > 0) {
		durationVal += to;	
	}
	if (applicantName != '') {
		if (appointmentId == null) {
			// do Nothing
		} else {
			//applicantName = '<a href="#" class="green" onclick="javascript: showEditAppointmentScreen(' + appointmentId + ',' + dayNumber + ',' + isFullyEditable + '); stopEventPropagation();">' + applicantName + '</a>';
			applicantName = '<a href="#" class="green" onclick="javascript: viewApplicantDetails(event,' + applicantId + '); stopEventPropagation(event);">' + applicantName + '</a>';
		}
	}
	
	if(applicantName.trim() != '') {
		text += '<tr><td>' + applicantName + '</td></tr>';
	}
	if(type == 'appointment') {
		text += '<tr><td>' + position + '</td></tr>';
	} else {
		text += '<tr><td>' + trimLength(position, 28) + '</td></tr>';
	}
	
	text += '<tr><td>' + durationVal + '</td></tr>';
	
	text += '</table>';
	return text;
}

function trimLength(str, len) {
	if (str.length > len) {
		str = str.substring(0, (len - 3)) + '...';
	}
	return str;
}

function appendStrings(str1, str2, separator) {
	if (str1.length > 0 && str2.length > 0) {
		str1 += separator;
	}
	str1 += str2;
	return str1;
} 

function getCellTitle(applicantName, subject, interviewer, from, to, status) {
	text = '';
	durationVal = '';
	
	if (from.length > 0) {
		durationVal += from;		
	}
	if (from.length > 0 && to.length > 0) {
		durationVal += ' to ';
	}
	if (to.length > 0) {
		durationVal += to;
	}
	
	text = appendStrings(text, unescapeHTML(applicantName), '\n');
	text = appendStrings(text, unescapeHTML(subject), '\n');
	text = appendStrings(text, unescapeHTML(interviewer), '\n');
	text = appendStrings(text, durationVal, '\n');
	text = appendStrings(text, unescapeHTML(status), '\n');
	
	return text;
}

function getSingleElement(parent,tagName,defVal){
	try {
		return parent.getElementsByTagName(tagName)[0].firstChild.nodeValue;
	} catch( myError ) {}
	return defVal;
}

function getTime(dtStr) {
	parts = dtStr.split(' ');
	if (parts && parts.length == 2) {
		time = parts[1].split(':');
	} else {
		return '';
	}
	if (time && time.length >= 2) {
		var curr_hour = time[0];
		var curr_min = time[1];	
	} else {
		return '';
	}
	
	var a_p = "";	
	if (curr_hour < 12) {
		a_p = "AM";
	} else {
		a_p = "PM";
   	}
	if (curr_hour == 0) {
		curr_hour = 12;
   	}
	if (curr_hour > 12) {
		curr_hour = curr_hour - 12;
   	}
	if (curr_min.length == 1) {
	   curr_min = "0" + curr_min;
   	}
   	return (curr_hour + ":" + curr_min + " " + a_p);
}

function unescapeHTML(html) {
	var htmlNode = document.createElement("DIV");
	htmlNode.innerHTML = html;
	if(htmlNode.innerText !== undefined)
	return htmlNode.innerText; // IE
	return htmlNode.textContent; // FF
} 