<% 
response.setContentType("text/xml"); 
if(request.getAttribute("xmlFile") !=null){
out.write((String)request.getAttribute("xmlFile"));	
}
%>