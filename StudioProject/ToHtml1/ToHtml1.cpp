// ToHtml1.cpp : Defines the entry point for the DLL application.
//

#include "stdafx.h"
#include <jni.h>

#include "com_talentPool_jni_WordJNI.h"
#include "CPPConverter.h"

BOOL APIENTRY DllMain( HANDLE hModule, 
                       DWORD  ul_reason_for_call, 
                       LPVOID lpReserved
					 )
{
    return TRUE;
}

/* Following function name will change depending on the Java
 * package used */

JNIEXPORT jboolean JNICALL Java_com_talentPool_jni_WordJNI_convertWordToHtml
(JNIEnv *env, jclass cls, jstring wordFile, jstring htmlFile) {
             /* interface pointer */ 
             /* "this" pointer */ 
		/* argument #1: Word file name */ 
       /* argument #2: Html file name */  
     const char *cppWordFile = env->GetStringUTFChars(wordFile, 0); 
     const char *cppHtmlFile = env->GetStringUTFChars(htmlFile, 0); 
	 CPPConverter conv;
	 
	 int status = conv.dotNetWordToHtml (cppWordFile, cppHtmlFile);
     env->ReleaseStringUTFChars(wordFile, cppWordFile); 
     env->ReleaseStringUTFChars(htmlFile, cppHtmlFile); 
	 jboolean retVal = (status == 0) ? JNI_FALSE : JNI_TRUE;
     return retVal;
} 

