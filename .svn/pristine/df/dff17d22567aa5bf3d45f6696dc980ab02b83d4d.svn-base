   // white balloon with default configuration
   // (see http://www.wormbase.org/wiki/index.php/Balloon_Tooltips)
   var balloon    = new Balloon;
   
   // plain balloon tooltip
   var tooltip  = new Balloon;
   BalloonConfig(tooltip,'GPlain');

   // fading balloon
   var fader = new Balloon;
   BalloonConfig(fader,'GFade');   

   // a plainer popup box
   var box         = new Box;
   BalloonConfig(box,'GBox');

   // a box that fades in/out
   var fadeBox     = new Box;
   BalloonConfig(fadeBox,'GBox');
   fadeBox.bgColor     = 'black';
   fadeBox.fontColor   = 'white';
   fadeBox.borderStyle = 'none';
   fadeBox.delayTime   = 200;
   fadeBox.allowFade   = true;
   fadeBox.fadeIn      = 750;
   fadeBox.fadeOut     = 200;

 

function showAjaxTip(event,id){
	balloon.showTooltip(event,'url:userConf.do?mode=getApplicantToolTip&applicantId='+id,1);
}

function hideToolTip(){
	//UnTip();
}
