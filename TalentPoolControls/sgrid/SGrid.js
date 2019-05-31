// JavaScript Document
var SGrid =  Class.create();

SGrid.prototype ={
	/*oMenus is array of TpMenus*/
	initialize: function(oTableId, options) {
		this.oTableId = oTableId;
		this.options=options;
		this.oTable = $(oTableId);
		this.selectedClass=this.options.selectedClass!=null?this.options.selectedClass:'selected';
		this.addObservers();
		this.lastSelectedRow=null;
		this.onRowDblClickedHandler=false;
		this.onRowSelectHandler=false;
		this.onKeyPressed=false;
	},
	addObservers: function() {
   		//add event handlers to textbox
   		Event.observe(this.oTable, "click", this.onRowClick.bindAsEventListener(this));
   		Event.observe(this.oTable, "dblclick", this.onRowDblClick.bindAsEventListener(this));
   		Event.observe(this.oTable, "keypress", this.OnRowKeyPressed.bindAsEventListener(this));
	},
	getSelectedId: function(){
		return this.lastSelectedRow!=null?this.lastSelectedRow.id:'';
	},
	getUserData: function(selectedId, property){
		var jso = eval('('+$('d'+selectedId).innerHTML+')');
		return( eval('jso.'+property));
	},
	onRowClick: function(event) {
		if(Event.element(event).nodeName=='TD'){
			var element = Event.findElement(event, 'tr');
			if(this.lastSelectedRow!=null){
				Element.removeClassName(this.lastSelectedRow,this.selectedClass);
			}
			Element.addClassName(element,this.selectedClass);
			this.lastSelectedRow=element;
			if(this.onRowSelectHandler){
				eval(this.onRowSelectHandler+"('" + this.getSelectedId() +"')");
			}
		}
	},
	onRowDblClick: function(event) {
		if(this.onRowDblClickedHandler){
			eval(this.onRowDblClickedHandler+"('" + this.getSelectedId() +"')");
		}
	},
	OnRowKeyPressed: function(event) {
		if(this.onKeyPressed){
			eval(this.onKeyPressed+"('" + this.getSelectedId() +"')");
		}
	},
	
	setOnRowDblClickedHandler: function(hnd){
		this.onRowDblClickedHandler = hnd;
	},
	setOnRowSelectHandler: function(hnd){
		this.onRowSelectHandler = hnd;
	},
	setOnKeyPressed: function(hnd){
		this.onKeyPressed = hnd;
	}
	
}