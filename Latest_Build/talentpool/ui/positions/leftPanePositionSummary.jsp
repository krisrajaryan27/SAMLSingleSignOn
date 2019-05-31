<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%> 
<%@page import="com.talentPool.positions.constants.PositionConfigurationConstants"%>
<%@page import="com.talentPool.positions.manager.PositionScreenConfigurationManager"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@page import="com.talentPool.common.properties.GlobalConstants"%>
<%@page import="com.talentPool.budget.utils.BudgetUtils"%>
<%@page import="com.talentPool.user.manager.ModuleSet"%>
<%@page import="com.talentPool.search.SearchConstants"%>
<%@page import="com.talentPool.user.UserConstants"%>
<%@page import="com.talentPool.user.dataobject.LastViewedEntity"%>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.talentPool.custom.dataobject.CustomFieldData"%>
<%@page import="com.talentPool.custom.constants.CustomPageConstants"%>
<%@page import="com.talentPool.common.utils.Utils"%>
<%@page import="com.talentPool.applicant.ApplicantConstants"%>
<%@page import="com.talentPool.custom.constants.CustomFieldConstants"%>
<%@page import="com.talentPool.applicant.manager.ImportConfigurationManager"%>
<%@page import="com.talentPool.applicant.constants.ImportConfigurationConstants"%>
<%@page import="com.talentPool.common.properties.GlobalConstants"%>
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@ page import="org.apache.struts.Globals,
								com.talentPool.positions.form.PositionForm,
								com.talentPool.positions.PositionConstants,
								com.talentPool.common.db.SimpleDataObject,
								com.talentPool.budget.BudgetConstants,
								com.talentPool.custom.constants.CustomFieldConstants,
								com.talentPool.masters.constants.MastersConstants,
								com.talentPool.custom.utils.CustomFieldUtils,
								com.talentPool.user.manager.ModuleSet,
								com.talentPool.budget.utils.BudgetUtils,
								com.talentPool.common.properties.TPApplicationProperties,
								com.talentPool.positions.manager.PositionScreenConfigurationManager,
								com.talentPool.positions.constants.PositionConfigurationConstants,
								com.talentPool.positions.utils.PositionUtils,
								com.talentPool.positions.dataobject.PositionFieldData" %>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%><link rel="stylesheet" type="text/css" href="themes/default/CalendarPopup.css"/>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/controls.js" type="text/javascript"></script>
<script language="JavaScript" src="js/calender/CalendarPopup.js"></script>		
<script language="JavaScript" src="js/calender/dateFormatter.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxandradiogroup/checkboxradiogroup.js" type="text/javascript"></script>
<script language="JavaScript" src="js/tpmenu/tpmenu.js" type="text/javascript"></script>
<script language="JavaScript" src="js/tpmenu/tpmenuhandler.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
					
<script language="JavaScript" src="js/customfields/customfield.js"></script>
<script language="JavaScript" src="js/customfields/customfieldvalidator.js"></script>
<script language="JavaScript" type="text/javascript" src="js/tooltip/balloon.config.js"></script>
<script language="JavaScript" type="text/javascript" src="js/tooltip/balloon.js"></script>
<script language="JavaScript" type="text/javascript" src="js/tooltip/box.js"></script>
<script language="JavaScript" type="text/javascript" src="js/tooltip/yahoo-dom-event.js"></script>
<script language="JavaScript" type="text/javascript" src="js/tooltip/tip_ajaxcall.js"></script>
<link rel="stylesheet" type="text/css" href="themes/default/autoComplete.css">
<link rel="stylesheet" type="text/css" href="themes/default/searchTpMenu.css">		
<link rel="stylesheet" type="text/css" href="themes/default/CalendarPopup.css"/>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/controls.js" type="text/javascript"></script>
<script language="JavaScript" src="js/calender/CalendarPopup.js"></script>		
<script language="JavaScript" src="js/calender/dateFormatter.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxandradiogroup/checkboxradiogroup.js" type="text/javascript"></script>
<script language="JavaScript" src="js/tpmenu/tpmenu.js" type="text/javascript"></script>
<script language="JavaScript" src="js/tpmenu/tpmenuhandler.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js">
</script>
<script language="javascript">

var tenthMarksDuration=null;

</script>
<div>
<html:form action="/position">
<html:hidden property="mode" name="positionForm"/>
	<html:hidden property="requisitionerId" name="positionForm"/>
				<html:hidden property="positionOwnerId" name="positionForm"/>
				<html:hidden property="departmentId" name="positionForm"/>
				<html:hidden property="subDepartmentId" name="positionForm"/>
				<html:hidden property="subSubDepartmentId" name="positionForm"/>
				<html:hidden property="sub3DepartmentId" name="positionForm"/>
				<html:hidden property="sub4DepartmentId" name="positionForm"/>
				<html:hidden property="draftId" name="positionForm"/>
				<html:hidden property="draftName" name="positionForm"/>
				<html:hidden property="finishCopyPosition" name="positionForm"/>
				<html:hidden property="budgetItemId" name="positionForm"/>			
				<html:hidden property="gradeId" name="positionForm"/>			
				<html:hidden property="bandId" name="positionForm"/>				
				<html:hidden property="copyFrom" name="positionForm"/>					
				<html:hidden property="jobIndustryCode" name="positionForm"/>
				<html:hidden property="jobFunctionCode" name="positionForm"/>			
				<html:hidden property="jobRoleCode" name="positionForm"/>			
				<html:hidden property="country" name="positionForm"/>				
				<html:hidden property="salaryCurrency" name="positionForm"/>
				<html:hidden property="displaySalary" name="positionForm"/>	
				<html:hidden property="positionId" name="positionForm"/>
				
				<html:hidden property="dir" name="positionForm"/>
			<html:hidden property="step" name="positionForm"/>
			<html:hidden property="positionId" name="positionForm"/>			
			<html:hidden property="locationId" name="positionForm"/>
			<html:hidden property="showCondition" name="positionForm"/>
			<html:hidden property="positionStatus" name="positionForm"/>			
			<html:hidden property="sendPositionChangeNotification" name="positionForm"/>
			<html:hidden property="buId" name="positionForm"/>
			<html:hidden property="costCenterId" name="positionForm"/>
			<html:hidden property="typeOfVacancy" name="positionForm"/>
			<html:hidden property="positionTypeExtInt" name="positionForm"/>
			<html:hidden property="positionClone" name="positionForm"/>
			
	<div style="margin-left:5px; margin-top:5px;width: 195px;background-color: #fdfdfd;border: 1px solid #99CC01; padding: 5px;">
		<table width="100%">
			<tr>
				<td>
					<bean:message key="common.vacancies"/>: 
					<bean:write name="positionForm" property="vacancies" scope="request" />
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="position_summary.unfilled_vacancies"/>: 
					<bean:write name="positionForm" property="currentNoOfOpenings" scope="request" />
				</td>
			</tr>
			<tr>	
				<td>
					<bean:message key="position_summary.days_remaining"/>: 
					<bean:write name="daysRemaining" scope="request" />
				</td>
			</tr>
		</table>
	</div>
	
	
		<div style="margin-left:5px; margin-top:5px;width: 195px;background-color: #fdfdfd;border: 1px solid #99CC01; padding: 5px;">
	<table>
		
	 	    
 
        <tr>
            <td>Tenth Marks&nbsp;&nbsp;:&nbsp;&nbsp;</td></tr>
            <tr><td><html:select
                property="tenthMarksFilter">
                <html:option value="">-- Select --</html:option>
                <html:option value="1">Greater than</html:option>
                <html:option value="2">Equal to </html:option>
                <html:option value="3">Less Than</html:option>
            </html:select><br></td>
            
             <td><html:select
                property="tenthMarks">
                <html:option value="0">- NA-</html:option>
                 <html:option value="1">1</html:option>
                <html:option value="2">2</html:option>
                <html:option value="3">3</html:option>
                <html:option value="4">4</html:option>
                <html:option value="5">5</html:option>
                <html:option value="6">6</html:option>
                <html:option value="7">7</html:option>
                <html:option value="8">8</html:option>
                <html:option value="9">9</html:option>
                <html:option value="10">10</html:option>
                <html:option value="11">11</html:option>
                <html:option value="12">12</html:option>
                <html:option value="13">13</html:option>
                <html:option value="14">14</html:option>
                <html:option value="15">15</html:option>
                <html:option value="16">16</html:option>
                <html:option value="17">17</html:option>
                <html:option value="18">18</html:option>
                <html:option value="19">19</html:option>
                <html:option value="20">20</html:option>
                <html:option value="21">21</html:option>
                <html:option value="22">22</html:option>
                <html:option value="23">23</html:option>
                <html:option value="24">24</html:option>
                <html:option value="25">25</html:option>
                <html:option value="26">26</html:option>
                <html:option value="27">27</html:option>
                <html:option value="28">28</html:option>
                <html:option value="29">29</html:option>
                <html:option value="30">30</html:option>
                <html:option value="31">31</html:option>
                <html:option value="32">32</html:option>
                <html:option value="33">33</html:option>
                <html:option value="34">34</html:option>
                <html:option value="35">35</html:option>
                <html:option value="36">36</html:option>
                <html:option value="37">37</html:option>
                <html:option value="38">38</html:option>
                <html:option value="39">39</html:option>
                <html:option value="40">40</html:option>
                <html:option value="41">41</html:option>
                <html:option value="42">42</html:option>
                <html:option value="43">43</html:option>
                <html:option value="44">44</html:option>
                <html:option value="45">45</html:option>
                <html:option value="46">46</html:option>
                <html:option value="47">47</html:option>
                <html:option value="48">48</html:option>
                <html:option value="49">49</html:option>
                <html:option value="50">50</html:option>
                <html:option value="51">51</html:option>
                <html:option value="52">52</html:option>
                <html:option value="53">53</html:option>
                <html:option value="54">54</html:option>
                <html:option value="55">55</html:option>
                <html:option value="56">56</html:option>
                <html:option value="57">57</html:option>
                <html:option value="58">58</html:option>
                <html:option value="59">59</html:option>
                <html:option value="60">60</html:option>
                <html:option value="61">61</html:option>
                <html:option value="62">62</html:option>
                <html:option value="63">63</html:option>
                <html:option value="64">64</html:option>
                <html:option value="55">55</html:option>
                <html:option value="56">56</html:option>
                <html:option value="57">57</html:option>
                <html:option value="58">58</html:option>
                <html:option value="59">59</html:option>
                <html:option value="60">60</html:option>
                <html:option value="61">61</html:option>
                <html:option value="62">62</html:option>
                <html:option value="63">63</html:option>
                <html:option value="64">64</html:option>
                <html:option value="55">55</html:option>
                <html:option value="56">56</html:option>
                <html:option value="57">57</html:option>
                <html:option value="58">58</html:option>
                <html:option value="59">59</html:option>
                <html:option value="60">60</html:option>
                <html:option value="61">61</html:option>
                <html:option value="62">62</html:option>
                <html:option value="63">63</html:option>
                <html:option value="64">64</html:option>
                <html:option value="65">65</html:option>
                <html:option value="66">66</html:option>
                <html:option value="67">67</html:option>
                <html:option value="68">68</html:option>
                <html:option value="69">69</html:option>
                <html:option value="70">70</html:option>
                <html:option value="71">71</html:option>
                <html:option value="72">72</html:option>
                <html:option value="73">73</html:option>
                <html:option value="74">74</html:option>
                <html:option value="75">75</html:option>
                <html:option value="76">76</html:option>
                <html:option value="77">77</html:option>
                <html:option value="78">78</html:option>
                <html:option value="79">79</html:option>
                <html:option value="80">80</html:option>
                <html:option value="81">81</html:option>
                <html:option value="82">82</html:option>
                <html:option value="83">83</html:option>
                <html:option value="84">84</html:option>
                 <html:option value="85">85</html:option>
                <html:option value="86">86</html:option>
                <html:option value="87">87</html:option>
                <html:option value="88">88</html:option>
                <html:option value="89">89</html:option>
                <html:option value="90">90</html:option>
                <html:option value="91">91</html:option>
                <html:option value="92">92</html:option>
                <html:option value="93">93</html:option>
                <html:option value="94">94</html:option>
                <html:option value="95">95</html:option>
                <html:option value="96">96</html:option>
                <html:option value="97">97</html:option>
                <html:option value="98">98</html:option>
                <html:option value="99">99</html:option>
                <html:option value="100">100</html:option>
            </html:select><br></td> 
            
            </tr>
        
           <tr>
            <td>Twelveth Marks&nbsp;&nbsp;:&nbsp;&nbsp;</td></tr>
            <tr><td><html:select
                property="twelvethMarksFilter">
                <html:option value="">-- Select --</html:option>
                <html:option value="1">Greater than</html:option>
                <html:option value="2">Equal to </html:option>
                <html:option value="3">Less Than</html:option>
            </html:select><br></td>
            
             <td><html:select
                property="twelvethMarks">
                <html:option value="0">- NA-</html:option>
                <html:option value="1">1</html:option>
                <html:option value="2">2</html:option>
                <html:option value="3">3</html:option>
                <html:option value="4">4</html:option>
                <html:option value="5">5</html:option>
                <html:option value="6">6</html:option>
                <html:option value="7">7</html:option>
                <html:option value="8">8</html:option>
                <html:option value="9">9</html:option>
                <html:option value="10">10</html:option>
                <html:option value="11">11</html:option>
                <html:option value="12">12</html:option>
                <html:option value="13">13</html:option>
                <html:option value="14">14</html:option>
                <html:option value="15">15</html:option>
                <html:option value="16">16</html:option>
                <html:option value="17">17</html:option>
                <html:option value="18">18</html:option>
                <html:option value="19">19</html:option>
                <html:option value="20">20</html:option>
                <html:option value="21">21</html:option>
                <html:option value="22">22</html:option>
                <html:option value="23">23</html:option>
                <html:option value="24">24</html:option>
                <html:option value="25">25</html:option>
                <html:option value="26">26</html:option>
                <html:option value="27">27</html:option>
                <html:option value="28">28</html:option>
                <html:option value="29">29</html:option>
                <html:option value="30">30</html:option>
                <html:option value="31">31</html:option>
                <html:option value="32">32</html:option>
                <html:option value="33">33</html:option>
                <html:option value="34">34</html:option>
                <html:option value="35">35</html:option>
                <html:option value="36">36</html:option>
                <html:option value="37">37</html:option>
                <html:option value="38">38</html:option>
                <html:option value="39">39</html:option>
                <html:option value="40">40</html:option>
                <html:option value="41">41</html:option>
                <html:option value="42">42</html:option>
                <html:option value="43">43</html:option>
                <html:option value="44">44</html:option>
                <html:option value="45">45</html:option>
                <html:option value="46">46</html:option>
                <html:option value="47">47</html:option>
                <html:option value="48">48</html:option>
                <html:option value="49">49</html:option>
                <html:option value="50">50</html:option>
                <html:option value="51">51</html:option>
                <html:option value="52">52</html:option>
                <html:option value="53">53</html:option>
                <html:option value="54">54</html:option>
                <html:option value="55">55</html:option>
                <html:option value="56">56</html:option>
                <html:option value="57">57</html:option>
                <html:option value="58">58</html:option>
                <html:option value="59">59</html:option>
                <html:option value="60">60</html:option>
                <html:option value="61">61</html:option>
                <html:option value="62">62</html:option>
                <html:option value="63">63</html:option>
                <html:option value="64">64</html:option>
                <html:option value="55">55</html:option>
                <html:option value="56">56</html:option>
                <html:option value="57">57</html:option>
                <html:option value="58">58</html:option>
                <html:option value="59">59</html:option>
                <html:option value="60">60</html:option>
                <html:option value="61">61</html:option>
                <html:option value="62">62</html:option>
                <html:option value="63">63</html:option>
                <html:option value="64">64</html:option>
                <html:option value="55">55</html:option>
                <html:option value="56">56</html:option>
                <html:option value="57">57</html:option>
                <html:option value="58">58</html:option>
                <html:option value="59">59</html:option>
                <html:option value="60">60</html:option>
                <html:option value="61">61</html:option>
                <html:option value="62">62</html:option>
                <html:option value="63">63</html:option>
                <html:option value="64">64</html:option>
                <html:option value="65">65</html:option>
                <html:option value="66">66</html:option>
                <html:option value="67">67</html:option>
                <html:option value="68">68</html:option>
                <html:option value="69">69</html:option>
                <html:option value="70">70</html:option>
                <html:option value="71">71</html:option>
                <html:option value="72">72</html:option>
                <html:option value="73">73</html:option>
                <html:option value="74">74</html:option>
                <html:option value="75">75</html:option>
                <html:option value="76">76</html:option>
                <html:option value="77">77</html:option>
                <html:option value="78">78</html:option>
                <html:option value="79">79</html:option>
                <html:option value="80">80</html:option>
                <html:option value="81">81</html:option>
                <html:option value="82">82</html:option>
                <html:option value="83">83</html:option>
                <html:option value="84">84</html:option>
                 <html:option value="85">85</html:option>
                <html:option value="86">86</html:option>
                <html:option value="87">87</html:option>
                <html:option value="88">88</html:option>
                <html:option value="89">89</html:option>
                <html:option value="90">90</html:option>
                <html:option value="91">91</html:option>
                <html:option value="92">92</html:option>
                <html:option value="93">93</html:option>
                <html:option value="94">94</html:option>
                <html:option value="95">95</html:option>
                <html:option value="96">96</html:option>
                <html:option value="97">97</html:option>
                <html:option value="98">98</html:option>
                <html:option value="99">99</html:option>
                <html:option value="100">100</html:option>
            </html:select><br></td> 
            
            </tr>
            
                 <tr>
            <td>UG Marks&nbsp;&nbsp;:&nbsp;&nbsp;</td></tr>
            <tr><td><html:select
                property="gradeMarksFilter">
                <html:option value="">-- Select --</html:option>
                <html:option value="1">Greater than</html:option>
                <html:option value="2">Equal to </html:option>
                <html:option value="3">Less Than</html:option>
            </html:select><br></td>
            
             <td><html:select
                property="gradeMarks">
                <html:option value="0">- NA-</html:option>
                 <html:option value="1">1</html:option>
                <html:option value="2">2</html:option>
                <html:option value="3">3</html:option>
                <html:option value="4">4</html:option>
                <html:option value="5">5</html:option>
                <html:option value="6">6</html:option>
                <html:option value="7">7</html:option>
                <html:option value="8">8</html:option>
                <html:option value="9">9</html:option>
                <html:option value="10">10</html:option>
                <html:option value="11">11</html:option>
                <html:option value="12">12</html:option>
                <html:option value="13">13</html:option>
                <html:option value="14">14</html:option>
                <html:option value="15">15</html:option>
                <html:option value="16">16</html:option>
                <html:option value="17">17</html:option>
                <html:option value="18">18</html:option>
                <html:option value="19">19</html:option>
                <html:option value="20">20</html:option>
                <html:option value="21">21</html:option>
                <html:option value="22">22</html:option>
                <html:option value="23">23</html:option>
                <html:option value="24">24</html:option>
                <html:option value="25">25</html:option>
                <html:option value="26">26</html:option>
                <html:option value="27">27</html:option>
                <html:option value="28">28</html:option>
                <html:option value="29">29</html:option>
                <html:option value="30">30</html:option>
                <html:option value="31">31</html:option>
                <html:option value="32">32</html:option>
                <html:option value="33">33</html:option>
                <html:option value="34">34</html:option>
                <html:option value="35">35</html:option>
                <html:option value="36">36</html:option>
                <html:option value="37">37</html:option>
                <html:option value="38">38</html:option>
                <html:option value="39">39</html:option>
                <html:option value="40">40</html:option>
                <html:option value="41">41</html:option>
                <html:option value="42">42</html:option>
                <html:option value="43">43</html:option>
                <html:option value="44">44</html:option>
                <html:option value="45">45</html:option>
                <html:option value="46">46</html:option>
                <html:option value="47">47</html:option>
                <html:option value="48">48</html:option>
                <html:option value="49">49</html:option>
                <html:option value="50">50</html:option>
                <html:option value="51">51</html:option>
                <html:option value="52">52</html:option>
                <html:option value="53">53</html:option>
                <html:option value="54">54</html:option>
                <html:option value="55">55</html:option>
                <html:option value="56">56</html:option>
                <html:option value="57">57</html:option>
                <html:option value="58">58</html:option>
                <html:option value="59">59</html:option>
                <html:option value="60">60</html:option>
                <html:option value="61">61</html:option>
                <html:option value="62">62</html:option>
                <html:option value="63">63</html:option>
                <html:option value="64">64</html:option>
                <html:option value="55">55</html:option>
                <html:option value="56">56</html:option>
                <html:option value="57">57</html:option>
                <html:option value="58">58</html:option>
                <html:option value="59">59</html:option>
                <html:option value="60">60</html:option>
                <html:option value="61">61</html:option>
                <html:option value="62">62</html:option>
                <html:option value="63">63</html:option>
                <html:option value="64">64</html:option>
                <html:option value="55">55</html:option>
                <html:option value="56">56</html:option>
                <html:option value="57">57</html:option>
                <html:option value="58">58</html:option>
                <html:option value="59">59</html:option>
                <html:option value="60">60</html:option>
                <html:option value="61">61</html:option>
                <html:option value="62">62</html:option>
                <html:option value="63">63</html:option>
                <html:option value="64">64</html:option>
                <html:option value="65">65</html:option>
                <html:option value="66">66</html:option>
                <html:option value="67">67</html:option>
                <html:option value="68">68</html:option>
                <html:option value="69">69</html:option>
                <html:option value="70">70</html:option>
                <html:option value="71">71</html:option>
                <html:option value="72">72</html:option>
                <html:option value="73">73</html:option>
                <html:option value="74">74</html:option>
                <html:option value="75">75</html:option>
                <html:option value="76">76</html:option>
                <html:option value="77">77</html:option>
                <html:option value="78">78</html:option>
                <html:option value="79">79</html:option>
                <html:option value="80">80</html:option>
                <html:option value="81">81</html:option>
                <html:option value="82">82</html:option>
                <html:option value="83">83</html:option>
                <html:option value="84">84</html:option>
                 <html:option value="85">85</html:option>
                <html:option value="86">86</html:option>
                <html:option value="87">87</html:option>
                <html:option value="88">88</html:option>
                <html:option value="89">89</html:option>
                <html:option value="90">90</html:option>
                <html:option value="91">91</html:option>
                <html:option value="92">92</html:option>
                <html:option value="93">93</html:option>
                <html:option value="94">94</html:option>
                <html:option value="95">95</html:option>
                <html:option value="96">96</html:option>
                <html:option value="97">97</html:option>
                <html:option value="98">98</html:option>
                <html:option value="99">99</html:option>
                <html:option value="100">100</html:option>
            </html:select><br></td> 
            
            </tr>
            
                 <tr>
            <td>PG Marks&nbsp;&nbsp;:&nbsp;&nbsp;</td></tr>
            <tr><td><html:select
                property="postGradeMarksFilter">
                <html:option value="">-- Select --</html:option>
                <html:option value="1">Greater than</html:option>
                <html:option value="2">Equal to </html:option>
                <html:option value="3">Less Than</html:option>
            </html:select><br></td>
            
             <td><html:select
                property="postGradeMarks">
                <html:option value="0">- NA-</html:option>
                 <html:option value="1">1</html:option>
                <html:option value="2">2</html:option>
                <html:option value="3">3</html:option>
                <html:option value="4">4</html:option>
                <html:option value="5">5</html:option>
                <html:option value="6">6</html:option>
                <html:option value="7">7</html:option>
                <html:option value="8">8</html:option>
                <html:option value="9">9</html:option>
                <html:option value="10">10</html:option>
                <html:option value="11">11</html:option>
                <html:option value="12">12</html:option>
                <html:option value="13">13</html:option>
                <html:option value="14">14</html:option>
                <html:option value="15">15</html:option>
                <html:option value="16">16</html:option>
                <html:option value="17">17</html:option>
                <html:option value="18">18</html:option>
                <html:option value="19">19</html:option>
                <html:option value="20">20</html:option>
                <html:option value="21">21</html:option>
                <html:option value="22">22</html:option>
                <html:option value="23">23</html:option>
                <html:option value="24">24</html:option>
                <html:option value="25">25</html:option>
                <html:option value="26">26</html:option>
                <html:option value="27">27</html:option>
                <html:option value="28">28</html:option>
                <html:option value="29">29</html:option>
                <html:option value="30">30</html:option>
                <html:option value="31">31</html:option>
                <html:option value="32">32</html:option>
                <html:option value="33">33</html:option>
                <html:option value="34">34</html:option>
                <html:option value="35">35</html:option>
                <html:option value="36">36</html:option>
                <html:option value="37">37</html:option>
                <html:option value="38">38</html:option>
                <html:option value="39">39</html:option>
                <html:option value="40">40</html:option>
                <html:option value="41">41</html:option>
                <html:option value="42">42</html:option>
                <html:option value="43">43</html:option>
                <html:option value="44">44</html:option>
                <html:option value="45">45</html:option>
                <html:option value="46">46</html:option>
                <html:option value="47">47</html:option>
                <html:option value="48">48</html:option>
                <html:option value="49">49</html:option>
                <html:option value="50">50</html:option>
                <html:option value="51">51</html:option>
                <html:option value="52">52</html:option>
                <html:option value="53">53</html:option>
                <html:option value="54">54</html:option>
                <html:option value="55">55</html:option>
                <html:option value="56">56</html:option>
                <html:option value="57">57</html:option>
                <html:option value="58">58</html:option>
                <html:option value="59">59</html:option>
                <html:option value="60">60</html:option>
                <html:option value="61">61</html:option>
                <html:option value="62">62</html:option>
                <html:option value="63">63</html:option>
                <html:option value="64">64</html:option>
                <html:option value="55">55</html:option>
                <html:option value="56">56</html:option>
                <html:option value="57">57</html:option>
                <html:option value="58">58</html:option>
                <html:option value="59">59</html:option>
                <html:option value="60">60</html:option>
                <html:option value="61">61</html:option>
                <html:option value="62">62</html:option>
                <html:option value="63">63</html:option>
                <html:option value="64">64</html:option>
                <html:option value="55">55</html:option>
                <html:option value="56">56</html:option>
                <html:option value="57">57</html:option>
                <html:option value="58">58</html:option>
                <html:option value="59">59</html:option>
                <html:option value="60">60</html:option>
                <html:option value="61">61</html:option>
                <html:option value="62">62</html:option>
                <html:option value="63">63</html:option>
                <html:option value="64">64</html:option>
                <html:option value="65">65</html:option>
                <html:option value="66">66</html:option>
                <html:option value="67">67</html:option>
                <html:option value="68">68</html:option>
                <html:option value="69">69</html:option>
                <html:option value="70">70</html:option>
                <html:option value="71">71</html:option>
                <html:option value="72">72</html:option>
                <html:option value="73">73</html:option>
                <html:option value="74">74</html:option>
                <html:option value="75">75</html:option>
                <html:option value="76">76</html:option>
                <html:option value="77">77</html:option>
                <html:option value="78">78</html:option>
                <html:option value="79">79</html:option>
                <html:option value="80">80</html:option>
                <html:option value="81">81</html:option>
                <html:option value="82">82</html:option>
                <html:option value="83">83</html:option>
                <html:option value="84">84</html:option>
                 <html:option value="85">85</html:option>
                <html:option value="86">86</html:option>
                <html:option value="87">87</html:option>
                <html:option value="88">88</html:option>
                <html:option value="89">89</html:option>
                <html:option value="90">90</html:option>
                <html:option value="91">91</html:option>
                <html:option value="92">92</html:option>
                <html:option value="93">93</html:option>
                <html:option value="94">94</html:option>
                <html:option value="95">95</html:option>
                <html:option value="96">96</html:option>
                <html:option value="97">97</html:option>
                <html:option value="98">98</html:option>
                <html:option value="99">99</html:option>
                <html:option value="100">100</html:option>
            </html:select><br></td> 
            
            </tr>
            
                      <tr>
            <td>Age&nbsp;&nbsp;:&nbsp;&nbsp;</td></tr>
            <tr><td><html:select
                property="ageFilter">
                <html:option value="">-- Select --</html:option>
                <html:option value="1">Greater than</html:option>
                <html:option value="2">Equal to </html:option>
                <html:option value="3">Less Than</html:option>
            </html:select><br></td>
            
             <td><html:select
                property="age">
                <html:option value="0">- NA-</html:option>
                 <html:option value="1">1</html:option>
                <html:option value="2">2</html:option>
                <html:option value="3">3</html:option>
                <html:option value="4">4</html:option>
                <html:option value="5">5</html:option>
                <html:option value="6">6</html:option>
                <html:option value="7">7</html:option>
                <html:option value="8">8</html:option>
                <html:option value="9">9</html:option>
                <html:option value="10">10</html:option>
                <html:option value="11">11</html:option>
                <html:option value="12">12</html:option>
                <html:option value="13">13</html:option>
                <html:option value="14">14</html:option>
                <html:option value="15">15</html:option>
                <html:option value="16">16</html:option>
                <html:option value="17">17</html:option>
                <html:option value="18">18</html:option>
                <html:option value="19">19</html:option>
                <html:option value="20">20</html:option>
                <html:option value="21">21</html:option>
                <html:option value="22">22</html:option>
                <html:option value="23">23</html:option>
                <html:option value="24">24</html:option>
                <html:option value="25">25</html:option>
                <html:option value="26">26</html:option>
                <html:option value="27">27</html:option>
                <html:option value="28">28</html:option>
                <html:option value="29">29</html:option>
                <html:option value="30">30</html:option>
                <html:option value="31">31</html:option>
                <html:option value="32">32</html:option>
                <html:option value="33">33</html:option>
                <html:option value="34">34</html:option>
                <html:option value="35">35</html:option>
                <html:option value="36">36</html:option>
                <html:option value="37">37</html:option>
                <html:option value="38">38</html:option>
                <html:option value="39">39</html:option>
                <html:option value="40">40</html:option>
                <html:option value="41">41</html:option>
                <html:option value="42">42</html:option>
                <html:option value="43">43</html:option>
                <html:option value="44">44</html:option>
                <html:option value="45">45</html:option>
                <html:option value="46">46</html:option>
                <html:option value="47">47</html:option>
                <html:option value="48">48</html:option>
                <html:option value="49">49</html:option>
                <html:option value="50">50</html:option>
                <html:option value="51">51</html:option>
                <html:option value="52">52</html:option>
                <html:option value="53">53</html:option>
                <html:option value="54">54</html:option>
                <html:option value="55">55</html:option>
                <html:option value="56">56</html:option>
                <html:option value="57">57</html:option>
                <html:option value="58">58</html:option>
                <html:option value="59">59</html:option>
                <html:option value="60">60</html:option>
                <html:option value="61">61</html:option>
                <html:option value="62">62</html:option>
                <html:option value="63">63</html:option>
                <html:option value="64">64</html:option>
                <html:option value="55">55</html:option>
                <html:option value="56">56</html:option>
                <html:option value="57">57</html:option>
                <html:option value="58">58</html:option>
                <html:option value="59">59</html:option>
                <html:option value="60">60</html:option>
                <html:option value="61">61</html:option>
                <html:option value="62">62</html:option>
                <html:option value="63">63</html:option>
                <html:option value="64">64</html:option>
                <html:option value="55">55</html:option>
                <html:option value="56">56</html:option>
                <html:option value="57">57</html:option>
                <html:option value="58">58</html:option>
                <html:option value="59">59</html:option>
                <html:option value="60">60</html:option>
                <html:option value="61">61</html:option>
                <html:option value="62">62</html:option>
                <html:option value="63">63</html:option>
                <html:option value="64">64</html:option>
                <html:option value="65">65</html:option>
                <html:option value="66">66</html:option>
                <html:option value="67">67</html:option>
                <html:option value="68">68</html:option>
                <html:option value="69">69</html:option>
                <html:option value="70">70</html:option>
                <html:option value="71">71</html:option>
                <html:option value="72">72</html:option>
                <html:option value="73">73</html:option>
                <html:option value="74">74</html:option>
                <html:option value="75">75</html:option>
                <html:option value="76">76</html:option>
                <html:option value="77">77</html:option>
                <html:option value="78">78</html:option>
                <html:option value="79">79</html:option>
                <html:option value="80">80</html:option>
                <html:option value="81">81</html:option>
                <html:option value="82">82</html:option>
                <html:option value="83">83</html:option>
                <html:option value="84">84</html:option>
                 <html:option value="85">85</html:option>
                <html:option value="86">86</html:option>
                <html:option value="87">87</html:option>
                <html:option value="88">88</html:option>
                <html:option value="89">89</html:option>
                <html:option value="90">90</html:option>
                <html:option value="91">91</html:option>
                <html:option value="92">92</html:option>
                <html:option value="93">93</html:option>
                <html:option value="94">94</html:option>
                <html:option value="95">95</html:option>
                <html:option value="96">96</html:option>
                <html:option value="97">97</html:option>
                <html:option value="98">98</html:option>
                <html:option value="99">99</html:option>
                <html:option value="100">100</html:option>
            </html:select><br></td> 
            
            </tr>
            
                      <tr>
            <td>Work Experience&nbsp;&nbsp;:&nbsp;&nbsp;</td></tr>
            <tr><td><html:select
                property="yearOfExperienceFilter">
                <html:option value="">-- Select --</html:option>
                <html:option value="1">Greater than</html:option>
                <html:option value="2">Equal to </html:option>
                <html:option value="3">Less Than</html:option>
            </html:select><br></td>
            
             <td><html:select
                property="yearOfExperience">
                <html:option value="0">- NA-</html:option>
                <html:option value="1">1</html:option>
                <html:option value="2">2</html:option>
                <html:option value="3">3</html:option>
                <html:option value="4">4</html:option>
                <html:option value="5">5</html:option>
                <html:option value="6">6</html:option>
                <html:option value="7">7</html:option>
                <html:option value="8">8</html:option>
                <html:option value="9">9</html:option>
                <html:option value="10">10</html:option>
                <html:option value="11">11</html:option>
                <html:option value="12">12</html:option>
                <html:option value="13">13</html:option>
                <html:option value="14">14</html:option>
                <html:option value="15">15</html:option>
                <html:option value="16">16</html:option>
                <html:option value="17">17</html:option>
                <html:option value="18">18</html:option>
                <html:option value="19">19</html:option>
                <html:option value="20">20</html:option>
                <html:option value="21">21</html:option>
                <html:option value="22">22</html:option>
                <html:option value="23">23</html:option>
                <html:option value="24">24</html:option>
                <html:option value="25">25</html:option>
                <html:option value="26">26</html:option>
                <html:option value="27">27</html:option>
                <html:option value="28">28</html:option>
                <html:option value="29">29</html:option>
                <html:option value="30">30</html:option>
                <html:option value="31">31</html:option>
                <html:option value="32">32</html:option>
                <html:option value="33">33</html:option>
                <html:option value="34">34</html:option>
                <html:option value="35">35</html:option>
                <html:option value="36">36</html:option>
                <html:option value="37">37</html:option>
                <html:option value="38">38</html:option>
                <html:option value="39">39</html:option>
                <html:option value="40">40</html:option>
                <html:option value="41">41</html:option>
                <html:option value="42">42</html:option>
                <html:option value="43">43</html:option>
                <html:option value="44">44</html:option>
                <html:option value="45">45</html:option>
                <html:option value="46">46</html:option>
                <html:option value="47">47</html:option>
                <html:option value="48">48</html:option>
                <html:option value="49">49</html:option>
                <html:option value="50">50</html:option>
                <html:option value="51">51</html:option>
                <html:option value="52">52</html:option>
                <html:option value="53">53</html:option>
                <html:option value="54">54</html:option>
                <html:option value="55">55</html:option>
                <html:option value="56">56</html:option>
                <html:option value="57">57</html:option>
                <html:option value="58">58</html:option>
                <html:option value="59">59</html:option>
                <html:option value="60">60</html:option>
                <html:option value="61">61</html:option>
                <html:option value="62">62</html:option>
                <html:option value="63">63</html:option>
                <html:option value="64">64</html:option>
                <html:option value="55">55</html:option>
                <html:option value="56">56</html:option>
                <html:option value="57">57</html:option>
                <html:option value="58">58</html:option>
                <html:option value="59">59</html:option>
                <html:option value="60">60</html:option>
                <html:option value="61">61</html:option>
                <html:option value="62">62</html:option>
                <html:option value="63">63</html:option>
                <html:option value="64">64</html:option>
                <html:option value="55">55</html:option>
                <html:option value="56">56</html:option>
                <html:option value="57">57</html:option>
                <html:option value="58">58</html:option>
                <html:option value="59">59</html:option>
                <html:option value="60">60</html:option>
                <html:option value="61">61</html:option>
                <html:option value="62">62</html:option>
                <html:option value="63">63</html:option>
                <html:option value="64">64</html:option>
                <html:option value="65">65</html:option>
                <html:option value="66">66</html:option>
                <html:option value="67">67</html:option>
                <html:option value="68">68</html:option>
                <html:option value="69">69</html:option>
                <html:option value="70">70</html:option>
                <html:option value="71">71</html:option>
                <html:option value="72">72</html:option>
                <html:option value="73">73</html:option>
                <html:option value="74">74</html:option>
                <html:option value="75">75</html:option>
                <html:option value="76">76</html:option>
                <html:option value="77">77</html:option>
                <html:option value="78">78</html:option>
                <html:option value="79">79</html:option>
                <html:option value="80">80</html:option>
                <html:option value="81">81</html:option>
                <html:option value="82">82</html:option>
                <html:option value="83">83</html:option>
                <html:option value="84">84</html:option>
                 <html:option value="85">85</html:option>
                <html:option value="86">86</html:option>
                <html:option value="87">87</html:option>
                <html:option value="88">88</html:option>
                <html:option value="89">89</html:option>
                <html:option value="90">90</html:option>
                <html:option value="91">91</html:option>
                <html:option value="92">92</html:option>
                <html:option value="93">93</html:option>
                <html:option value="94">94</html:option>
                <html:option value="95">95</html:option>
                <html:option value="96">96</html:option>
                <html:option value="97">97</html:option>
                <html:option value="98">98</html:option>
                <html:option value="99">99</html:option>
                <html:option value="100">100</html:option>
            </html:select><br></td> 
            
            </tr>
            
            <tr>
            <td>Gap In Academics &nbsp;&nbsp;:&nbsp;&nbsp;</td></tr>
            <tr><td><html:select
                property="gapInAcademics">
                <html:option value="">-- Select --</html:option>
                <html:option value="1">Yes</html:option>
                <html:option value="2">No</html:option>
            </html:select><br></td>
            </tr>
            
              <tr>
            <td>Gender &nbsp;&nbsp;:&nbsp;&nbsp;</td></tr>
            <tr><td><html:select
                property="gender">
                <html:option value="">-- Select --</html:option>
                <html:option value="1">M</html:option>
                <html:option value="2">F</html:option>
                <html:option value="3">Not Discosed</html:option>
            </html:select><br></td>
            </tr>
            
    <tr>
		    <td>
			<div class="navBtn" style="float: left;"><a href="#" style="width:60px;" class="active" onclick="javascript: submitForm(1);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.search"/></a></div>
			</td>
	
           <td>
				<div class="navBtn" style="float: right;">	<a href="#" style="width:60px;" class="active" onclick="reset();return false;"><span class="rightC"></span><span class="leftC"></span>Reset</a></div>
			</td>
      </tr>
	   
	  
	   
		</table>
	</div>
	</html:form>
	<div style="border-bottom: 1px solid rgb(204, 204, 204);">&nbsp;</div>
	<table>
		<tr> 
	      <td><img src="images/spacer.gif" width="1" height="13" /></td> 
	    </tr>
		<tr>	
			<td>
				<b><bean:message key="common.summary" /></b>
			</td>
		</tr>
		<tr>
			<td>
				<table border="0" cellspacing="0" cellpadding="0" class="posinput">
						<tr>
							<td class="label"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1) %></td>
							<td><bean:write name="positionForm" property="department" /></td>
						</tr>
						<tr>
							<td class="label"><bean:message key="position.description.location" />
							</td>
							<td><bean:write name="positionForm" property="locationName" /></td>
						</tr>
						<%if(PositionScreenConfigurationManager.isDescriptionFieldShow(PositionConfigurationConstants.FIELD_POSITION_OWNER)){%>
							<tr>
								<td class="label"><bean:message key="global.position_owner" /></td>
								<td><bean:write name="positionForm" property="positionOwnerName" /></td>
							</tr>
						<%} %>
						<tr>
							<td class="label"><bean:message key="position.description.requested_by" /></td>
							<td><bean:write name="positionForm" property="requisitioner" /></td>
						</tr>
						<tr>
							<td class="label"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_GRADE_LABEL)%></td>
							<td><bean:write name="positionForm" property="gradeName" /></td>
						</tr>
						<tr>
							<td class="label"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_BAND_LABEL)%></td>
							<td><bean:write name="positionForm" property="bandName" /></td>
						</tr>
						 <% if (ModuleSet.isMODULE_BUDGET() && BudgetUtils.isBudgetModuleActive()) { %>			
						<tr>
							<td class="label"><bean:message key="position.description.budget_item" /></td>
							<td><bean:write name="positionForm" property="budgetItemName" /></td>
						</tr>
						<%} %>
				</table>	
			</td>
		</tr>
	</table>
	<br/>
	<div style="border-bottom: 1px solid rgb(204, 204, 204);">&nbsp;</div>
	<table>
		<tr> 
	      <td><img src="images/spacer.gif" width="1" height="13" /></td> 
	    </tr> 
	    <tr> 
	      <td height="20"><strong class="Grey"><bean:message key="leftpane.label.viewed"/></strong></td> 
	    </tr> 
	    <tr> 
	      <td style="line-height:18px;"> 
			<logic:notEmpty name="lastViewedPositions">
				<logic:iterate id="entity" name="lastViewedPositions" type="LastViewedEntity">
					<logic:equal name="entity" property="entityType" value="<%=UserConstants.ENTITY_POSITION%>">
				        <img src="images/ico_star.gif" width="13" height="13" vspace="3" align="absmiddle" /> <a href="#" onclick="javascript: viewPositionSummary('<bean:write name="entity" property="entityId"/>');return false;" class="green"><bean:write name="entity" property="descriptionTrimmed"/></a><br /> 
					</logic:equal>
				</logic:iterate>
			</logic:notEmpty>
		   </td>
		   
	    </tr>
	</table>	
	<script language="javascript" type="text/javascript">

function reset() {
	document.positionForm.tenthMarksFilter.value="";
	document.positionForm.tenthMarks.value="";
	document.positionForm.twelvethMarksFilter.value="";
	document.positionForm.twelvethMarks.value="";
	document.positionForm.gradeMarksFilter.value="";
	document.positionForm.gradeMarks.value="";
	document.positionForm.postGradeMarksFilter.value="";
	document.positionForm.postGradeMarks.value="";
	document.positionForm.ageFilter.value="";
	document.positionForm.age.value="";
	document.positionForm.yearOfExperienceFilter.value="";
	document.positionForm.yearOfExperience.value="";
	document.positionForm.gapInAcademics.value="";
	document.positionForm.gender.value="";
	document.positionForm.submit();
	return true;
	}

function submitForm() {
	document.positionForm.submit();
	return true;
}
</script>
</div>
  




