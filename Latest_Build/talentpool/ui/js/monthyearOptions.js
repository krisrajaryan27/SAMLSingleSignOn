var optMonths = new Array();
var optYears = new Array();
var g_d = new Date();
var g_curr_date = g_d.getDate();
var g_curr_month = g_d.getMonth()+1;
var g_curr_year = g_d.getFullYear();
g_curr_month = (g_curr_month <10)?'0'+g_curr_month : g_curr_month;
function setOptsYears(noOfYrs){
	for(var i=0; i<noOfYrs; i++){
		optYears[i] = new SelectOption(g_curr_year-i,g_curr_year-i);
	}
}
function setOptsMonths(){
	optMonths[0] = new SelectOption('01','January');
	optMonths[1] = new SelectOption('02','February');
	optMonths[2] = new SelectOption('03','March');
	optMonths[3] = new SelectOption('04','April');
	optMonths[4] = new SelectOption('05','May');
	optMonths[5] = new SelectOption('06','June');
	optMonths[6] = new SelectOption('07','July');
	optMonths[7] = new SelectOption('08','August');
	optMonths[8] = new SelectOption('09','September');
	optMonths[9] = new SelectOption('10','October');
	optMonths[10] = new SelectOption('11','November');
	optMonths[11] = new SelectOption('12','December');
}
setOptsYears(10);
setOptsMonths();
