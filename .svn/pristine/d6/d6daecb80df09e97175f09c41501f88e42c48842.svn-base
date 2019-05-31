var criteriaOpt = Class.create();
criteriaOpt.prototype ={
	initialize: function(criteriaId, criteriaText) {
		this.criteriaId=criteriaId;
		this.criteriaText=criteriaText;
	}
}
var criteriaPane =  Class.create();
criteriaPane.prototype ={
	initialize: function(panelId) {
		this.criterias=new Array();
		this.panelId=panelId;
	},
	add: function(opt){
		var tot=this.criterias.length;
		var exists=false;
		for(i=0;i<tot;i++){
			if(this.criterias[i].criteriaId==opt.criteriaId){
				exists=true;
				if(this.criterias[i].criteriaText!=opt.criteriaText){
					exists=false;
					this.criterias = this.criterias.without(this.criterias[i]);
					break;
				}
			}
		}
		if(opt.criteriaText!='' && !exists){
			this.criterias[this.criterias.length]=opt;
		}
	},
	refreshCriteria: function(){
		var tot=this.criterias.length;
		var str='';
		if(tot>0){
			str= "<table cellpadding=\"2\" cellspacing=\"2\">";
			for(i=0;i<tot;i++){
				str+="<tr>";
				str+="<td>";
				str+="<img src=\"images/ico_delete.gif\" onclick=\"deleteCriteria('"+ this.criterias[i].criteriaId +"');\" title=\"Undo this filter\" style=\"cursor:pointer;\"/>";
				str+="</td>";
				str+="<td>"+this.criterias[i].criteriaText.escapeHTML();
				str+="</td>";
				str+="</tr>";
			}
			str+="</table>";
		}
		$(this.panelId).innerHTML=str;
		if(str!=''){
			Element.show(this.panelId);
		}else{
			Element.hide(this.panelId);
		}
	},
	clearAll: function(){
		this.criterias=new Array();
	}
}
