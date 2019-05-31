function validCustomfields(DEFAULT_SELECT_OPTION, TYPE_DROPDOWN, TYPE_LISTBOX, TYPE_CHECKBOX, TYPE_RADIO, checkmandatory){
	var valid=true;
	for(x=0;x<customFieldsList.length;x++){
		var cData = customFieldsList[x];
		if($(cData.getName())) {
			var val='';
			if(cData.getType()==TYPE_DROPDOWN){
				val= eval(cData.getName()).getSelectedId();
				if(val==DEFAULT_SELECT_OPTION){
					val='';
				}
				$(cData.getName()).value=val;
			}else if(cData.getType()==TYPE_LISTBOX){ 
				val= eval(cData.getName()).getSelectedItems(true,true,'|');
				$(cData.getName()).value=val;
			}else if(cData.getType()==TYPE_CHECKBOX || cData.getType()==TYPE_RADIO){
				val= eval(cData.getName()).getSelectedItems(true,'|');
				$(cData.getName()).value=val;
			}else{
				val=$(cData.getName()).value;
			}
			if(checkmandatory && cData.isRequired()){
				if(val==''){
					valid=false;
					alert(cData.getDisplayName()+' is required');
					if($(cData.getName()) && $(cData.getName()).type!='hidden'){
						$(cData.getName()).focus();
					}
					break;
				}
			}
		}
	}
	return valid;
}