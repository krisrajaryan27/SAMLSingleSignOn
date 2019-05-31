<script type="text/javascript">
var selectMonthFrom=null;
var selectYearFrom=null;
var selectMonthTo=null;
var selectYearTo=null;
</script>
<table class="tabinput">
	<tr>
		<td class="firstColumn">
			<bean:message key="report.label.from" /> :
		</td>
		
		<td>
			<script type="text/javascript">
			selectMonthFrom = new SelectBox(optMonths,'','images/btn_dropdown.gif',{namesonly:false, width:'80px', size:20});
			document.write(selectMonthFrom.getHtml());
			selectMonthFrom.init();
			</script>
		</td>
		<td>
			<script type="text/javascript">
			selectYearFrom = new SelectBox(optYears,'','images/btn_dropdown.gif',{namesonly:false, width:'50px', size:20});
			document.write(selectYearFrom.getHtml());
			selectYearFrom.init();
			</script>
		</td>
		<td></td>
		<td></td>
		<td>
			<bean:message key="report.label.to" /> :
		</td>
		<td>
			<script type="text/javascript">
			selectMonthTo = new SelectBox(optMonths,g_curr_month,'images/btn_dropdown.gif',{namesonly:false, width:'80px', size:20});
			document.write(selectMonthTo.getHtml());
			selectMonthTo.init();
			</script>
		</td>
		<td>
			<script type="text/javascript">
			selectYearTo = new SelectBox(optYears,'','images/btn_dropdown.gif',{namesonly:false, width:'50px', size:20});
			document.write(selectYearTo.getHtml());
			selectYearTo.init();
			</script>
		</td>  
	</tr> 
</table>   
