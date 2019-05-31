<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="js/scripta/lib/prototype.js"></script>
</head>
<body>
<s:form action="manageOfferGeneration" method="POST">
<s:hidden name="applicantId" />
<s:hidden name="modifyOffer" />
	<div class="contentDivPop" style="width: 350px;">
		<%@include file="../common/errordiv.jspf" %>
		<div class="vpTop" style="margin-bottom: 1px;" >
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="vpTopTab">
		      <tr>
		        <td>
		        	<strong id="VP_TITLE" class="Grey">
		            		<s:property value="applicantTitle"  />
		   			</strong>
		 		</td>
		      </tr>
		    </table>
		</div>
		<%if ("1".equals((String)request.getAttribute("isOfferGenerationStep"))){ %>
		<div class="outerDiv" style="border-top:1px;">
			<div class="popupBody" >
				<table class="tblPop" width="100%" id="manageOffer" >
					<tr>
						<td>
							<img src="images/radiobutton.gif" id="generateNewOffer" class='nextActionRadioImg' style="cursor:pointer;margin-bottom:-1px;"/>&nbsp;
							<s:label key="generate_offer_sheet.label.generate_new_offer" />
						</td>
					</tr>
					<tr>
						<td>
							<img src="images/radiobutton.gif" id="modifyExistingOffer" class='nextActionRadioImg' style="cursor:pointer;margin-bottom:-1px;"/>&nbsp;
							<s:label key="generate_offer_sheet.label.modify_existing_offer" />
						</td>
					</tr>
					<tr>
						<td>
							<img src="images/radiobutton.gif" id="exportOffer" class='nextActionRadioImg' style="cursor:pointer;margin-bottom:-1px;"/>&nbsp;
							<s:label key="generate_offer_sheet.label.print_offer" />
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="navBtn" style="float: right;margin-top: 10px;">
			<a href="#" style="width:60px;margin-right:5px;" class="active" onclick="javascript: nextAction();return false;"><span class="rightC"></span><span class="leftC"></span><s:text name="common.next"></s:text> </a>
			<a href="#" style="width:60px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><s:text name="common.cancel"/></a>
		</div>
		<%}else{ %>
			<div class="outerDiv" style="border-top:1px;">
			<div class="popupBody" >
				<table class="tblPop" width="100%" id="manageOffer" >
					<tr>
						<td>
							<s:label key="generate_offer_sheet.label.not_in_offer_generation_stage" />
						</td>
					</tr>
				</table>
			</div>
		</div>
		<%} %>
	</div>
</s:form>
</body>
<script type="text/javascript">
var checkedRadioImg = 'images/checkedradiobutton.gif';
var radioImg = 'images/radiobutton.gif';
var disabledRadioImg = 'images/radio_chk0_dis.gif';
var isOfferAlreadyGenerated = <s:property value="#request[\'isOfferAlreadyGenerated\']" />;

function nextAction(){
	if($('generateNewOffer').src.indexOf(checkedRadioImg)!=-1){
		document.manageOfferGeneration.action='manageApplicantOfferDetails.action';
		document.manageOfferGeneration.modifyOffer.value=false;
		document.manageOfferGeneration.submit();	
	}else if($('modifyExistingOffer').src.indexOf(checkedRadioImg)!=-1){
		document.manageOfferGeneration.action='manageApplicantOfferDetails.action';
		document.manageOfferGeneration.modifyOffer.value=true;
		document.manageOfferGeneration.submit();
	}else if($('exportOffer').src.indexOf(checkedRadioImg)!=-1){
		document.manageOfferGeneration.action='exportOffer.action';
		document.manageOfferGeneration.submit();	
	}
	return false;
}

Event.observe(window, "load", function() {	
	window.top.setPopTitle('<s:text name="generate_offer_sheet.label.generate_offer" />');
	if(isOfferAlreadyGenerated){
		$('exportOffer').src=checkedRadioImg;		
	}else{
		$('modifyExistingOffer').src=disabledRadioImg;
		$('exportOffer').src=disabledRadioImg;
		$('generateNewOffer').src=checkedRadioImg;
	}
	$$('#manageOffer img.nextActionRadioImg').invoke('observe', 'click', onChangeManageOfferRadBtn.bindAsEventListener(this));
});

function onChangeManageOfferRadBtn(event){
	var parentNode = Event.element(event);
	if(parentNode.src.indexOf(disabledRadioImg)==-1){
		parentNode.src=checkedRadioImg;
		$$('#manageOffer img.nextActionRadioImg').each(function(node) {
			if(parentNode.id!=node.id && node.src.indexOf(disabledRadioImg)==-1)
				node.src=radioImg;
		});
	}
}
</script>
</html>