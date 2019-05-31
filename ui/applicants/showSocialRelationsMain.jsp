<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<script src="encryption/js/jquery-2.0.3.min.js" type="text/javascript"></script>
<script src="encryption/js/jquery-ui-1.8.2.custom.min.js" type="text/javascript"></script>
<script type="text/javascript" src="js/jquery-corner.js"></script>
<script type="text/javascript">

	var tempVal;
	var prevDiv='#dummy';
	function openProfile(url) {
		window.open(url);
	}
	
	function showDiv(par){
		if(par == '0'){
			$('#lin').show();
			$("#fb").hide();
			$('#facebookTab').attr('class','boxDarkTab');
			$('#linkedinTab').attr('class','boxTab');
			$("#facebookTab").css('cursor','pointer');
			$("#linkedinTab").css('cursor','auto');
		}else{
			$('#fb').show();
			$('#lin').hide();
			$('#linkedinTab').attr('class','boxDarkTab');
			$('#facebookTab').attr('class','boxTab');
			$("#linkedinTab").css('cursor','pointer');
			$("#facebookTab").css('cursor','auto');
		}
	}
	
	window.onload=doonload;
	
	function doonload(){
		showDiv(0);
	}
	

	function animateValue(id){
		var val1 = '#b'+id;
		var val2 = "#t"+id;
		var name= $(val1).text().split("(");
		var results = $(val2).text().split(",");
		$(prevDiv).hide();
	    var div = $('#matchCircle');
	    div.removeAttr('style');
	    div.show();
	    div.animate({left:'298px',height:'150px',width:'150px',lineHeight: '150px',marginTop: '90px',borderRadius: '150px',backgroundColor: '#98bf21',textAlign: 'center'},"slow");
        div.animate({fontSize: '5em'},"slow");
        div.html(results.length);
        $('#circleTitle').html("<h2>"+name[0]+"</h2>");
        var rDiv= $('#resultPeople');
        var str='';
        for(var i=0;i<results.length;i++){
        	str += "<div id=p"+i+" class=\"peopleTags\">"+results[i]+"</div><br/>";
        }
        rDiv.html(str);
			
	}
	
	jQuery(function($) {
		  jQuery(".tags").corner("round 4px").parent().css({'padding':'2px','border': '1px solid #D0E4A3','background-color': '#D0E4A3'}).corner("round 4px");
		  jQuery(".peopleTags").corner("round 4px");
		});
	
</script>
<style type="text/css">
	.top_side 
		  {
		background-color: #FFFFFF;
	    border: 1px solid #D0E4A3;
	    color: #000000;
	    height: 310px;
	    overflow-y: hidden;
	    width: 190px;
		  }
	.bottom_side 
		 {
		 width:815px;
		 height: 295px;
		 float:bottom;
		 background-color: #FFFFFF;
		 border:1px solid #D0E4A3;
		 color: #000000;
		 overflow-y: hidden;
		 }	
	.top_right 
		  {
		 width: 615px; 
		 height: 285px;
		 float: right;
		 background-color:#FFFFFF;
		 border: 1px solid #D0E4A3;
		 color: #000000;
		 overflow-y: scroll;
		  }
	.bottom_middle
		 {
		 width:350px;
		 height: 290px;
		 background-color: #FFFFFF;
		 color: #000000;
		 }
	.bottom_left
		 {
		 width:200px;
		 height: 280px;
		 border: 1px solid #D0E4A3;
		 background-color: #FFFFFF;
		 color: #000000;
		 overflow-y: auto;
		 }
	.bottom_right 
		 {
		 width:246px;
		 height: 283px;
		 float:right;
		 background-color: #FFFFFF;
		 border:1px solid #D0E4A3;
		 color: #000000;
		 overflow-y: auto;
		
		 }
	.tags
		{
		  background:#F2F2F2;
		  border: 1px solid #D0E4A3;
		  color:#669900;
		  height: 200px;
		  width: 200px;
		  text-align: left;
		  font-family: Verdana;
		  font-variant: small-caps;
		  font: medium;
		}
	.circle
		{
		  background:#FFFFFF;
		  border: 2px solid #98bf21;
		  height:50px;
		  width:50px;
		  border-radius:50px;
		  left: 346px;
		  position:absolute;
		  text-align: center;
		  line-height:50px;
		}
		.peopleTags
		{
		  background:#F2F2F2;
		  border: 1px solid #D0E4A3;
		  color:#669900;
		  height: 200px;
		  width: 200px;
		  text-align: left;
		  font-family: Verdana;
		  font-variant: small-caps;
		  font: medium;
		  font-size: 12px;
		}
</style>
</head>

<body>

<logic:empty name="errorMessage" scope="request">
<div>
<table>
	<tr>
		<td>
		<div class="top_side">
		<table>
			<tr>
				<td><div style="color: #669900;"><h2>Candidate:</h2></div></td><td></td>
			</tr>
			<%if(((String) request.getAttribute("linkedInName")) != null) {%>
			<tr>
				<td>
					
					<table>
						<tr>
							<td>
									<logic:equal name="linkedInProfilePicUrl" value="null" scope="request">
										<img src="images/dummy-profile-image.gif" style="display: inline-block;" width="80px" height="80px"/>
									</logic:equal>
									<logic:notEqual name="linkedInProfilePicUrl" value="null" scope="request">
										<img src='<%=request.getAttribute("linkedInProfilePicUrl") %>' style="display: inline-block;" />									
									</logic:notEqual>
								</td>
								<td>
									<table>
										<tr>
											<td>
												<h3>
													<div style="color: #666666;">
													<%=request.getAttribute("linkedInName") %>
													</div>
												</h3>
											</td>
										</tr>
										<tr>
											<td>
												<!-- <img src="images/facebook-icon.gif"> -->
													<a href='#'
													onclick="javascript:openProfile('<%=request.getAttribute("linkedInPublicProfileUrl")%>')">
													<img src="images/linkedIn-icon.gif"></a>
											</td>										
										</tr>
									</table>
								
								
								</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td><div style="color: #666666;"><h3>
								<%=request.getAttribute("linkedInHeadline") %>
								</h3></div>
				</td><td></td>
			</tr>
		<%} %>
		</table>

	</div>
		</td>
		
		<td>
		<table cellspacing="0">
				<tr>
					<td>
				<table cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td>
							<div style="width:160px;" class="boxTab" onclick="showDiv(0);"
								id="linkedinTab"><span class="rightC"></span><span
								class="leftC"></span>LinkedIn Connections
							</div>
						</td>
						<td>
							<div style="width:160px; visibility: hidden;" class="boxDarkTab"
								onclick="showDiv(1);" id="facebookTab"><span class="rightC"></span><span
								class="leftC"></span>Facebook Friends
							</div>
						</td>
					</tr>
				</table>
					
					
					</td>
				</tr>
			<tr>
				<td>
				<div>
				<div id="lin">
				<div class="top_right">
						<%int cnt=0; %>
						<table style="margin-left: 20px; margin-right: 20px">
							<logic:notEmpty name="linkedInPeopleList" scope="request">
								<logic:iterate id="people" name="linkedInPeopleList" scope="request">
									<%if(cnt%2==0){ %>
										<tr>
										<%} %>
											<td>
											<logic:empty name="people" property="pictureUrl">
											<img src="images/dummy-profile-image.gif" style="display: inline-block;" width="50px" height="50px"/>
											</logic:empty>
											<logic:notEmpty name="people" property="pictureUrl">
												<img src='<bean:write name="people" property="pictureUrl"/>' style="display: inline-block;" width="50px" height="50px"/>
											</logic:notEmpty>
											</td>
												<td width="250px">
												<table>
													<tr>
														<td><a href='#'	onclick="javascript:openProfile('<bean:write name="people" property="publicProfileUrl"/>')">
																<bean:write name="people" property="firstName" /> <bean:write name="people" property="lastName" />
														</a></td>
													</tr>
													<tr>
														<td><bean:write name="people" property="headline" /></td>
													</tr>
												</table>
											<%cnt++;%>
											</td>
										<%if(cnt%2==0){ %>
										</div>
										</tr>
										<%} %>
							</logic:iterate>
						</logic:notEmpty>
					</table>
					</div>
					</div>
					<div id="fb">
					<div class="top_right">
					Coming soon
					
					</div>
						
					</div>
					</div>
					</td>
			</tr>
		</table>
		</td>
	</tr>
	</table>
	</div>
</logic:empty>	
<logic:notEmpty name="errorMessage" scope="request">
	<table>
	<tr>
	<td><div class="top_side"></div></td>
	<td>
	<table cellpadding="0" cellspacing="0" border="0">
		<tr>
			<td>
				<div style="width:160px;" class="boxTab" onclick="showDiv(0);"
								id="linkedinTab"><span class="rightC"></span><span
								class="leftC"></span>LinkedIn Connections
				</div>
			</td>
		</tr>
		<tr>
		
			<td>
			<div class="top_right">
				<%=request.getAttribute("errorMessage")%>
			 </div>
			</td>
			
		</tr>
	</table>
	</td>
	</tr>
	</table>
</logic:notEmpty>	
	
	<div class="bottom_side">
	<table>
	<tr>
		<td>
			<div class="bottom_left">
		<table>
			<tr>
				<td>
					<div style="color: #669900;"><h2>Matches:</h2></div>
				</td>
			</tr>
			<logic:iterate id="attrib" name="commonAttribsFromGraph" indexId="indx">
			<tr>
				<td>
					
						
						<div class="tags"> 
						<div id='b<bean:write name="indx"/>' onclick="animateValue('<bean:write name="indx"/>')" style="cursor: pointer;">
						<bean:write name="attrib" property="key"/> &nbsp;
							(<script type="text/javascript">
								var vals = '<bean:write name="attrib" property="value"/>';
								var val = vals.split(",");
								document.write(val.length);
							</script>)
							</div>
							<div id='t<bean:write name="indx"/>' style="display: none;">
								<bean:write name="attrib" property="value"/>
							</div>
							</div>
						
					
				</td>
			</tr>
			</logic:iterate>
		</table>
			</div>			
		</td>
		<td>
		<div class="bottom_middle">
		<div id="circleTitle" style="top: 567px; position: absolute; text-align: center; width: 349px"></div>
			<div style="width: 80px; height: 80px; position: absolute; float: left; overflow: hidden; border-radius:80px; left: 215px">
				<logic:empty name="linkedInProfilePicUrl" scope="request">
					<img src="images/dummy-profile-image.gif" style="display: inline-block;" width="80px" height="80px"/>
				</logic:empty>
				<logic:notEmpty name="linkedInProfilePicUrl" scope="request">
					<logic:equal parameter="linkedInProfilePicUrl" value="null" scope="request">
						<img src="images/dummy-profile-image.gif" style="display: inline-block;" width="80px" height="80px"/>
					</logic:equal>
					<logic:notEqual parameter="linkedInProfilePicUrl" value="null" scope="request">
						<img src='<%=request.getAttribute("linkedInProfilePicUrl") %>' style="display: inline-block;" />									
					</logic:notEqual>
				</logic:notEmpty>					
			</div>
			<div style="margin-top: 4px;position: absolute; left: 293px">
				<svg height="40" width="175">
			  <g fill="none" stroke="grey" stroke-width="4">
			    <path stroke-dasharray="10,10" d="M5 40 l215 0" />
			  </g>
			</svg>
			</div>
			<div id="matchCircle" class="circle" style="margin-top: 13px"></div>
			
			<div  class="circle" style="margin-top: 13px; overflow: hidden;"><img src="images/questionMark.gif" width="54px"/></div>
			<div style="width: 80px; height: 80px; position: absolute; float: right; overflow: hidden; border-radius:80px; left: 460px">
				<img src="images/dummy-profile-image.gif" width="80px"/>
			</div>
			
			
		</div>
		</td>
		<td>
			<div class="bottom_right">
			<table cellspacing="0">
			<tr>
			<td>
				<div style="color: #669900;"><h2>People:</h2></div>
			</td>
			</tr>
				<tr>
				<td align="left" style="padding-left: 10px">
					 <div id="resultPeople">
					 </div>				
				</td>
				</tr>
			</table>
			</div>
		</td>
	</tr>
</table>
	</div>
</body>
</html>