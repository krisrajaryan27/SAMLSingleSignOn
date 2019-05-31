#include "stdafx.h"
#include "CPPConverter.h"
#import "C:\\tempo\\ToHtml1\\Converter.dll" 
using namespace Converter; 


int CPPConverter::dotNetWordToHtml (const char *c1, const char *c2) {
	HRESULT hresult;
	CLSID clsid;
	CoInitialize(NULL);
	hresult=CLSIDFromProgID(OLESTR("converter.clsConverter"), &clsid);
	_clsConverter *t; 
	hresult=CoCreateInstance(clsid,NULL,CLSCTX_INPROC_SERVER,__uuidof(_clsConverter),(LPVOID *) &t);
	if(FAILED(hresult))
	{
		return 0;
	}
	
	t->convertWordToHtml (c1,c2);  
	t->Release();  
	CoUninitialize(); 
	



	//Converter:: *pTest = new Converter::_clsConverter();
	//HRESULT hres = pTest.CreateInstance  ( __uuidof(clsConverter) );
	//pTest->wordToXML("a","b");
	
	//Converter::_clsConverter *ptr ;//= new Converter::_clsConverter();
	//HRESULT hres = ptr.CreateInstance  ( __uuidof(Converter.clsConverter) );
	//ptr->convertWordToHtml("c:\\distinct\\x\\ATT31148.doc","c:\\distinct\\x\\1.html");
	//ptr->Release();
	//ptr->convertWordToHtml(
	//ptr->Release();

	return 1;
}

void main(){
	CPPConverter con;
	con.dotNetWordToHtml("c:\\distinct\\x\\ATT31148.doc","c:\\distinct\\x\\1.html");

}
