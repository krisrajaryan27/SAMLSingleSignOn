; ---------------------------------------
; Talentpool Setup Script
; ---------------------------------------
!include "TalentPoolInstaller.properties"
!include "utils.inc"

; ---------------------------------------
;Header Files
; ---------------------------------------
!include "MUI.nsh"
!include "Sections.nsh"

; ---------------------------------------
;Icon for installer
; ---------------------------------------
!define MUI_ICON ${SETUP_ICON}
!define MUI_UNICON ${UNINSTALL_ICON}

!define MUI_WELCOMEFINISHPAGE_BITMAP ${TALENTPOOL_BITMAP}
!define MUI_UNWELCOMEFINISHPAGE_BITMAP ${TALENTPOOL_BITMAP}

; ---------------------------------------
; The name of the installer
; ---------------------------------------
Name ${PRODUCT_NAME}

; ---------------------------------------
; The file to write
; ---------------------------------------
OutFile ${SETUP_FILE}

; ---------------------------------------
; The default installation directory
; ---------------------------------------
InstallDir $PROGRAMFILES\${PRODUCT_NAME}


!define MUI_ABORTWARNING
!define MUI_FINISHPAGE_NOAUTOCLOSE

; ---------------------------------------
;Pages
; ---------------------------------------
!insertmacro MUI_PAGE_WELCOME
!insertmacro MUI_PAGE_LICENSE ${LICENSE_FILE}
Page custom installOrUpgrade
; define pre function
!define MUI_PAGE_CUSTOMFUNCTION_PRE skipCondition
!insertmacro MUI_PAGE_DIRECTORY
Page custom InstallationOptions
Page custom CustomPagePort
Page custom readDBInfo
!insertmacro MUI_PAGE_INSTFILES
!insertmacro MUI_PAGE_FINISH

!insertmacro MUI_UNPAGE_WELCOME
!insertmacro MUI_UNPAGE_CONFIRM
!insertmacro MUI_UNPAGE_INSTFILES
!insertmacro MUI_UNPAGE_FINISH

; ---------------------------------------
;Languages
; ---------------------------------------
!insertmacro MUI_LANGUAGE "English"

; ---------------------------------------
;Unzip files
; ---------------------------------------
!include "zipdll.nsh"

;----------------------------------------
;Reverse files
;----------------------------------------
ReserveFile "installOrUpgrade.ini"
ReserveFile "installationInfo.ini"
ReserveFile "ioPort.ini"
ReserveFile "dbInfo.ini"

;----------------------------------------
;Variables
;----------------------------------------
Var port_number
Var mySQLDecision
Var dbUser
Var dbPassword
Var dbPassword1
Var mySQLInstallLocation
Var java_home
Var machine_name
Var company_name
Var installOrUpgrade
Var userVersion

Var uMajorVersion
Var uMinorVersion
Var uServiceVersion

Var iMajorVersion
Var iMinorVersion
Var iServiceVersion

Var allScriptFile	
Var scriptFile	
Var createUser	
; ---------------------------------------
; The stuff to install
; ---------------------------------------
Section "" 
	StrCmp $installOrUpgrade "" +1 +2
	StrCpy $userVersion "v1.0.-1"
	Call setVersion

	StrCmp $iMajorVersion $uMajorVersion +1 +5
	StrCmp $iMinorVersion $uMinorVersion +1 +4
	StrCmp $iServiceVersion $uServiceVersion +1 +3
	MessageBox MB_OK "Updated version of talentpool found."
	goto lblNext	
	StrCmp $installOrUpgrade "" +1 +3
	Call installApp
	goto lblNext
	Call upgradeApp	
	lblNext:	
	
	Call generateLogFile
SectionEnd ; end the section

Function setVersion	
	Push "$userVersion"
	Push "v" ;needs to be replaced
	Push "" ;will replace characters
	Call StrReplace
	Pop $0	
	
	Push "." ; divider char
  	Push $0 ; input string
	Call GetLastPart
	Pop $R1 ; last part
	Pop $R0 ; first part
		
	StrCpy $uServiceVersion $R1
	
	Push "." ; divider char
  	Push $R0 ; input string
	Call GetLastPart
	Pop $R1 ; last part
	Pop $R0 ; first part
		
	StrCpy $uMinorVersion $R1
	StrCpy $uMajorVersion $R0
	
	Push "${VERSION}"
	Push "v" ;needs to be replaced
	Push "" ;will replace characters
	Call StrReplace
	Pop $0	
	
	Push "." ; divider char
  	Push $0 ; input string
	Call GetLastPart
	Pop $R1 ; last part
	Pop $R0 ; first part
		
	StrCpy $iServiceVersion $R1
	
	Push "." ; divider char
  	Push $R0 ; input string
	Call GetLastPart
	Pop $R1 ; last part
	Pop $R0 ; first part
		
	StrCpy $iMinorVersion $R1
	StrCpy $iMajorVersion $R0
FunctionEnd

Function generateLogFile
	; dump logs to a file
	IfFileExists "$INSTDIR\${INSTALL_LOG}" +1
		Delete "$INSTDIR\${INSTALL_LOG}"
	StrCpy $0 "$INSTDIR\${INSTALL_LOG}"
	Push $0
	Call DumpLog
FunctionEnd

Function installApp
	StrCmp $mySQLDecision "1" continue
	StrCmp $mySQLDecision "0" end
	continue:	
	SetOutPath "$INSTDIR"
	File "${APP_SERVER_FILE_PATH}" 	
	; ---------------------------------------
	; Install MySQL
	; ---------------------------------------
	Call installMySQL
	; ---------------------------------------
	; Install Java Runtime Environment
	; ---------------------------------------
	Call installJRE
	; ---------------------------------------
	; Install Tomcat
	; ---------------------------------------
	Call installTomcat

	;deploy application
	SetOutPath "$INSTDIR\tomcat\webapps"
	File "TalentPool.war" 
	SetOutPath "$INSTDIR"
	CreateDirectory "$INSTDIR\config"
	SetOutPath "$INSTDIR\config"
	File "TalentPool.properties"
	File "quartz.properties"
	File "log4j.properties"

	Push "$INSTDIR\tomcat\webapps"
	Push "\" ;needs to be replaced
	Push "/" ;will replace wrong characters
	Call StrReplace
	Pop $0
	
	Push "@application.path@"
	Push $0
	Push all
	Push all
	Push "$INSTDIR\config\TalentPool.properties"
	Call AdvReplaceInFile

	Push "$INSTDIR"
	Push "\" ;needs to be replaced
	Push "/" ;will replace wrong characters
	Call StrReplace
	Pop $0
	
	Push "@installation.path@"
	Push $0
	Push all
	Push all
	Push "$INSTDIR\config\TalentPool.properties"
	Call AdvReplaceInFile
	
	Push "@db.username@"
	Push $dbUser
	Push all
	Push all
	Push "$INSTDIR\config\TalentPool.properties"
	Call AdvReplaceInFile

	Push "@db.password@"
	Push $dbPassword
	Push all
	Push all
	Push "$INSTDIR\config\TalentPool.properties"
	Call AdvReplaceInFile
	
	CreateDirectory "$INSTDIR\license"
	
	Push "$INSTDIR\license\License.lic"
	Push "\" ;needs to be replaced
	Push "/" ;will replace wrong characters
	Call StrReplace
	Pop $0
	
	Push "@license.file.path@"
	Push $0
	Push all
	Push all
	Push "$INSTDIR\config\TalentPool.properties"
	Call AdvReplaceInFile
	
	SetOutPath $INSTDIR
	File .\startTalentPoolService.bat
	File .\stopTalentPoolService.bat
	File /r .\${REPORTS_DIR}
	File /r .\${JNI_DIR}
	File /r .\${HELP_DIR}
	File /r .\${DOCUMENTS_DIR}
	File /r .\${BENCHMARK_DIR}
	File /r .\${TEMPLATES_DIR}
	File /r .\${ICONS_DIR}
	File /r .\${PLUGIN_DIR}
	File .\desktop_icon.ico
	
	Push "@service_name@"
	Push ${TALENTPOOL_SERVICE}
	Push all
	Push all
	Push "$INSTDIR\startTalentPoolService.bat"
	Call AdvReplaceInFile
	
	Push "@service_name@"
	Push ${TALENTPOOL_SERVICE}
	Push all
	Push all
	Push "$INSTDIR\stopTalentPoolService.bat"
	Call AdvReplaceInFile
	
	; Tell the compiler to write an uninstaller and to look for a "Uninstall" section
	WriteUninstaller $INSTDIR\Uninstall.exe
	
	ReadRegStr $machine_name HKLM "System\CurrentControlSet\Control\ComputerName\ActiveComputerName" "ComputerName" 
	
	Push "@system.name@"
	Push $machine_name
	Push all
	Push all
	Push "$INSTDIR\config\TalentPool.properties"
	Call AdvReplaceInFile
	
	Push "@application.port@"
	Push $port_number
	Push all
	Push all
	Push "$INSTDIR\config\TalentPool.properties"
	Call AdvReplaceInFile
	
	File /r ".\${BACKUP_DIR}"
	
	Push "@MYSQL_PATH@"
	Push $mySQLInstallLocation
	Push all
	Push all
	Push "$INSTDIR\backup\backup.bat"
	Call AdvReplaceInFile
	
	Push "@TALENTPOOL_PATH@"
	Push $INSTDIR
	Push all
	Push all
	Push "$INSTDIR\backup\backup.bat"
	Call AdvReplaceInFile
	
	Push "@MYSQL_USER@"
	Push "-u$dbUser"
	Push all
	Push all
	Push "$INSTDIR\backup\backup.bat"
	Call AdvReplaceInFile
	
	Push "@MYSQL_PASS@"
	StrCmp $dbPassword "" +3 +1
	Push "-p$dbPassword"
	goto lbl11
	Push ""
	lbl11:
	Push all
	Push all
	Push "$INSTDIR\backup\backup.bat"
	Call AdvReplaceInFile
	
	; Encrypt DB credential before puting them in to application property file for version 3.8.0
	Call encryptApplicationProperties	
	
	; Now create shortcuts
	CreateDirectory "$SMPROGRAMS\${PRODUCT_NAME}"
	CreateShortCut "$SMPROGRAMS\${PRODUCT_NAME}\Start ${PRODUCT_NAME} Service.lnk" "$INSTDIR\startTalentPoolService.bat"
	CreateShortCut "$SMPROGRAMS\${PRODUCT_NAME}\Launch ${PRODUCT_NAME}.lnk" "http://$machine_name:$port_number/${PRODUCT_NAME}" "" "$INSTDIR\desktop_icon.ico"
	CreateShortCut "$SMPROGRAMS\${PRODUCT_NAME}\Stop ${PRODUCT_NAME} Service.lnk" "$INSTDIR\stopTalentPoolService.bat"
	CreateShortCut "$SMPROGRAMS\${PRODUCT_NAME}\Uninstall.lnk" "$INSTDIR\Uninstall.exe"
	CreateShortCut "$DESKTOP\Launch ${PRODUCT_NAME}.lnk" "http://$machine_name:$port_number/${PRODUCT_NAME}" "" "$INSTDIR\desktop_icon.ico"
	
	; Create registry keys for Add/Remove programs in control panel.
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${PRODUCT_NAME}" "DisplayName"\
	"${PRODUCT_NAME} (remove only)"

	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${PRODUCT_NAME}" "UninstallString" \
	"$INSTDIR\Uninstall.exe"	
	
	WriteRegStr HKLM "SOFTWARE\${PRODUCT_NAME}" "Version"\
	"${VERSION}"
	WriteRegStr HKLM "SOFTWARE\${PRODUCT_NAME}" "InstallDir"\
	"$INSTDIR"
	
	SetOutPath "$INSTDIR"
	File "installationKeyGenerator.jar"
	Call generateInstallationKey
	
	; Create demodata with new version	
	Call copyDemodataPool
	
	sleep 3000
	Delete "$INSTDIR\installationKeyGenerator.jar"
	
	Call installTVendorApp
	Call installTEmployeeApp
	Call installTWebsiteApp
	
	nsExec::ExecToLog 'NET START ${TALENTPOOL_SERVICE}'
	sleep 3000
	end:
FunctionEnd

Function upgradeApp
	nsExec::ExecToLog 'NET STOP ${TALENTPOOL_SERVICE}' $1
	sleep 3000
	
	nsExec::ExecToLog 'NET START ${SERVICE_NAME}' $1
	sleep 3000
	
	StrCpy $INSTDIR $installOrUpgrade
	
	ReadRegStr $mySQLInstallLocation HKLM "SOFTWARE\MySQL AB\MySQL Server 5.0" "Location"
	
	Call getDBUser
	Call getDBPassword
		
	IntCmp $uMajorVersion "3" +1 ResumeNext decrypt	
	IntCmp $uMinorVersion "7" ResumeNext ResumeNext decrypt
	decrypt:	
	Call decryptProperties
	
	ResumeNext:
	
	SetOutPath "$INSTDIR"
	File "${BACKUP_UTIL}" 
	File "${RESTORE_UTIL}" 
	File "${MERGE_ATT_TO_DOCS_UTIL}" 
	
	Call takeBackUp
	
	IntCmp $uMajorVersion "3" +1 +3 TomcatUpgraded
	IntCmp $uMinorVersion "8" +1 +2 TomcatUpgraded
	IntCmp $uServiceVersion "0" TomcatUpgraded +1 TomcatUpgraded
	
	; ---------------------------------------
	; Uninstall Tomcat 5.0
	; ---------------------------------------
	Call uninstallTomact
	; ---------------------------------------
	; Install Tomcat 5.5
	; ---------------------------------------
	SetOutPath "$INSTDIR"
	File "${APP_SERVER_FILE_PATH}" 	
	
	Call installTomcat
	
	Call updateApplicationProperties
	
	TomcatUpgraded:
	
	IfFileExists "$INSTDIR\tomcat\webapps\TalentPool.war" +1 +2
		Delete "$INSTDIR\tomcat\webapps\TalentPool.war"	
	
	; Copy the war file
	SetOutPath "$INSTDIR\tomcat\webapps"
	File "TalentPool.war" 
	
	; Start: do if version prior to 2.0
	StrCmp "1" $uMajorVersion +1 +5
	Rename "$INSTDIR\tomcat\webapps\${PRODUCT_NAME}\documents" "$INSTDIR\documents"
	Rename "$INSTDIR\tomcat\webapps\${PRODUCT_NAME}\attachments" "$INSTDIR\attachments"
	Call mergeAttachmentsToDocuments
	RMDIR /r "$INSTDIR\attachments"
	; End: do if version prior to 2.0
	
	RMDIR /r "$INSTDIR\tomcat\webapps\${PRODUCT_NAME}"
	
	; Execute the migration scripts
	SetOutPath "$INSTDIR"
	RMDIR /r "$INSTDIR\${DB_SCRIPT_DIR}"
	File /r .\${DB_SCRIPT_DIR}	
	SetOutPath "$mySQLInstallLocation"
	File /r .\${DB_SCRIPT_DIR}		
	Call executeScripts	
	sleep 3000
	RMDIR /r "$mySQLInstallLocation\${DB_SCRIPT_DIR}"
		
	; Copy the benchmark folder. Do not override if benchmark folder alredy present
	SetOutPath "$INSTDIR"
	SetOverwrite off
	File /r .\${BENCHMARK_DIR}
		
	; Copy the templates. Do not override if template alredy present
	SetOutPath "$INSTDIR"
	SetOverwrite off
	File /r .\${TEMPLATES_DIR}
	
	SetOutPath "$INSTDIR"
	SetOverwrite off
	File /r .\${ICONS_DIR}
	
	; Copy the reports folder as it is. Override the existing reports
	SetOverwrite on
	RMDir /r "$INSTDIR\${REPORTS_DIR}"
	SetOutPath "$INSTDIR"
	File /r .\${REPORTS_DIR}
	
	; Copy the plugins folder as it is. Override the existing plugins
	SetOverwrite on
	RMDir /r "$INSTDIR\${PLUGIN_DIR}"
	SetOutPath "$INSTDIR"
	File /r .\${PLUGIN_DIR}
	
	SetOverwrite on
	RMDir /r "$INSTDIR\${HELP_DIR}"
	SetOutPath "$INSTDIR"
	File /r .\${HELP_DIR}
	
	SetOverwrite on
	SetOutPath "$INSTDIR"
	RMDir /r "$INSTDIR\${BACKUP_DIR}"
	File /r ".\${BACKUP_DIR}"
	
	Push "@MYSQL_PATH@"
	Push $mySQLInstallLocation
	Push all
	Push all
	Push "$INSTDIR\backup\backup.bat"
	Call AdvReplaceInFile
	
	Push "@TALENTPOOL_PATH@"
	Push $INSTDIR
	Push all
	Push all
	Push "$INSTDIR\backup\backup.bat"
	Call AdvReplaceInFile
	
	Push "@MYSQL_USER@"
	Push "-u$dbUser"
	Push all
	Push all
	Push "$INSTDIR\backup\backup.bat"
	Call AdvReplaceInFile
	
	Push "@MYSQL_PASS@"
	StrCmp $dbPassword "" +3 +1
	Push "-p$dbPassword"
	goto lbl11
	Push ""
	lbl11:
	Push all
	Push all
	Push "$INSTDIR\backup\backup.bat"
	Call AdvReplaceInFile
	
		
	SetOverwrite on
	SetOutPath "$INSTDIR\config"
	File "quartz.properties"
	File "log4j.properties"
	SetOverwrite on
	SetOutPath "$INSTDIR"
	File /r .\${JNI_DIR}
	
	; Modify the application property file
	Call updateApplicationProperties
	
	Call copyDemodataPool


	; Tell the compiler to write an uninstaller and to look for a "Uninstall" section
	WriteUninstaller $INSTDIR\Uninstall.exe	
	
	sleep 3000
	nsExec::ExecToLog 'NET START ${TALENTPOOL_SERVICE}' $1
	sleep 10000
	
	;RMDir /r "$INSTDIR\tomcat\webapps\${PRODUCT_NAME}\documents"
	;RMDir /r "$INSTDIR\tomcat\webapps\${PRODUCT_NAME}\attachments"

	;Rename "$INSTDIR\documents" "$INSTDIR\tomcat\webapps\${PRODUCT_NAME}\documents"
	;Rename "$INSTDIR\attachments" "$INSTDIR\tomcat\webapps\${PRODUCT_NAME}\attachments"
	WriteRegStr HKLM "SOFTWARE\${PRODUCT_NAME}" "Version"\
	"${VERSION}"
FunctionEnd

Function encryptApplicationProperties	
	SetOutPath $INSTDIR
	File ${PROPERTIES_MERGER}
	File ${TALENTPOOL_PROPERTIES}
	
	; Get Java Home attribute
	ReadRegStr $java_home HKLM "SOFTWARE\JavaSoft\Java Runtime Environment\${REQUIRED_JRE_VERSION}" "JavaHome"
	
	Exec '$java_home\bin\javaw.exe -jar "$INSTDIR\${PROPERTIES_MERGER}" "$INSTDIR\${CONFIG_DIR}\${TALENTPOOL_PROPERTIES}" "$INSTDIR\${TALENTPOOL_PROPERTIES}" "0"'
	sleep 3000
	Delete "$INSTDIR\${PROPERTIES_MERGER}"
	Delete "$INSTDIR\${TALENTPOOL_PROPERTIES}"
FunctionEnd

Function updateApplicationProperties	
	SetOutPath $INSTDIR
	File ${PROPERTIES_MERGER}
	File ${TALENTPOOL_PROPERTIES}
	
	; Get Java Home attribute
	ReadRegStr $java_home HKLM "SOFTWARE\JavaSoft\Java Runtime Environment\${REQUIRED_JRE_VERSION}" "JavaHome"
	
	Exec '$java_home\bin\javaw.exe -jar "$INSTDIR\${PROPERTIES_MERGER}" "$INSTDIR\${CONFIG_DIR}\${TALENTPOOL_PROPERTIES}" "$INSTDIR\${TALENTPOOL_PROPERTIES}" "1"'
	sleep 3000
	Delete "$INSTDIR\${PROPERTIES_MERGER}"
	Delete "$INSTDIR\${TALENTPOOL_PROPERTIES}"
FunctionEnd

Function decryptProperties	
	SetOutPath $INSTDIR
	File ${DBUTIL}
	
	StrCpy $dbPassword1 $dbPassword
	
	StrCpy $1 '"'
	StrCpy $2 '\"'
	Push "$dbUser"
	Push $1 ;needs to be replaced
	Push $2 ;will replace characters
	Call StrReplace
	Pop $dbUser	
	
	StrCpy $1 '"'
	StrCpy $2 '\"'
	Push "$dbPassword"
	Push $1 ;needs to be replaced
	Push $2 ;will replace characters
	Call StrReplace
	Pop $dbPassword
	
	;MessageBox MB_OK $dbUser
	;MessageBox MB_OK $dbPassword
	
	; Get Java Home attribute
	ReadRegStr $java_home HKLM "SOFTWARE\JavaSoft\Java Runtime Environment\${REQUIRED_JRE_VERSION}" "JavaHome"
	Exec '$java_home\bin\javaw.exe -jar "$INSTDIR\${DBUTIL}" "$dbUser" "$dbPassword" "$INSTDIR\tmp.txt"'
	sleep 3000
	Delete "$INSTDIR\${DBUTIL}"
	
	ClearErrors
	FileOpen $0 "$INSTDIR\tmp.txt" r
	IfErrors done
	FileRead $0 $dbUser
	FileRead $0 $dbPassword
	FileClose $0	
	done:	
	
	StrCmp $dbPassword1 $dbPassword +1 +2
	StrCpy $dbPassword ""
	
	Push $dbUser
	Call Trim
	Pop $dbUser
	
	Push $dbPassword
	Call Trim
	Pop $dbPassword
	
	;MessageBox MB_OK $dbUser
	;MessageBox MB_OK $dbPassword
	
	Delete "$INSTDIR\tmp.txt"
FunctionEnd

Function takeBackUp
	Push "@MYSQL_PATH@"
	Push $mySQLInstallLocation
	Push all
	Push all
	Push "$INSTDIR\${BACKUP_UTIL}"
	Call AdvReplaceInFile
	
	Push "@MYSQL_USER@"
	Push "-u$dbUser"
	Push all
	Push all
	Push "$INSTDIR\${BACKUP_UTIL}"
	Call AdvReplaceInFile
	
	Push "@MYSQL_PASS@"
	StrCmp $dbPassword "" +1 +3
	Push ""
	Goto next
	push "-p$dbPassword"
	next:
	Push all
	Push all
	Push "$INSTDIR\${BACKUP_UTIL}"
	Call AdvReplaceInFile
	
	Push "@TALENTPOOL_PATH@"
	Push $INSTDIR
	Push all
	Push all
	Push "$INSTDIR\${BACKUP_UTIL}"
	Call AdvReplaceInFile
	
	nsExec::ExecToLog "$INSTDIR\${BACKUP_UTIL}"
FunctionEnd

Function mergeAttachmentsToDocuments
	Push "@TALENTPOOL_PATH@"
	Push $INSTDIR
	Push all
	Push all
	Push "$INSTDIR\${MERGE_ATT_TO_DOCS_UTIL}"
	Call AdvReplaceInFile
	
	nsExec::ExecToLog "$INSTDIR\${MERGE_ATT_TO_DOCS_UTIL}"
FunctionEnd

Function restoreApp
	Push "@MYSQL_PATH@"
	Push $mySQLInstallLocation
	Push all
	Push all
	Push "$INSTDIR\${RESTORE_UTIL}"
	Call AdvReplaceInFile
	
	Push "@MYSQL_USER@"
	Push $dbUser
	Push all
	Push all
	Push "$INSTDIR\${RESTORE_UTIL}"
	Call AdvReplaceInFile
	
	Push "@MYSQL_PASS@"
	Push $dbPassword
	Push all
	Push all
	Push "$INSTDIR\${RESTORE_UTIL}"
	Call AdvReplaceInFile
	
	Push "@TALENTPOOL_PATH@"
	Push $INSTDIR
	Push all
	Push all
	Push "$INSTDIR\${RESTORE_UTIL}"
	Call AdvReplaceInFile
FunctionEnd

Function getDBUser
	ClearErrors
	FileOpen $0 "$INSTDIR\${CONFIG_DIR}\${TALENTPOOL_PROPERTIES}" r
	IfErrors done
	loop:
		FileRead $0 $1
	  StrCmp $1 "" done
	  
	  ; Check if $1 contains db username property
	  Push "=" ; divider char
		Push $1 ; input string
		Call GetLastPart
		Pop $R1 ; last part
		Pop $R0 ; first part
		
		StrCmp $R0 ${DB_USERNAME_PROPERTY} +1 +3
		StrCpy $dbUser $R1
		Goto done	  
	  Goto loop
	done:
	FileClose $0	
	
	Push $dbUser
	Call Trim
	Pop $dbUser
FunctionEnd

Function getDBPassword
	ClearErrors
	FileOpen $0 "$INSTDIR\${CONFIG_DIR}\${TALENTPOOL_PROPERTIES}" r
	IfErrors done
	loop:
		FileRead $0 $1
	  StrCmp $1 "" done
	  
	  ; Check if $1 contains db username property
	  Push "=" ; divider char
		Push $1 ; input string
		Call GetLastPart
		Pop $R1 ; last part
		Pop $R0 ; first part
		
		StrCmp $R0 ${DB_PASSWORD_PROPERTY} +1 +3
		StrCpy $dbPassword $R1
		Goto done	  
	  Goto loop
	done:
	FileClose $0	
	
	Push $dbPassword
	Call Trim
	Pop $dbPassword
FunctionEnd

Function executeScripts
	SetOutPath "$mySQLInstallLocation\bin"
		
	Loop:
		StrCmp "$uMajorVersion.$uMinorVersion.$uServiceVersion" "$iMajorVersion.$iMinorVersion.$iServiceVersion" +1 +2
		Goto Done
		IntOp $uServiceVersion $uServiceVersion + 1		
		;MessageBox MB_OK $uMajorVersion.$uMinorVersion.$uServiceVersion
		StrCpy $allScriptFile "$INSTDIR\${DB_SCRIPT_DIR}\V_$uMajorVersion_$uMinorVersion_$uServiceVersion\allScripts.sql"
		IfFileExists $allScriptFile +1 +2
  		Call runScript
		StrCmp $uServiceVersion "13" +1 +3
		StrCpy $uServiceVersion "0"
		Goto Loop1
		Goto Loop
	Loop1:
		IntOp $uMinorVersion $uMinorVersion + 1			
		;MessageBox MB_OK $uMajorVersion.$uMinorVersion.$uServiceVersion
		StrCpy $allScriptFile "$INSTDIR\${DB_SCRIPT_DIR}\V_$uMajorVersion_$uMinorVersion_$uServiceVersion\allScripts.sql"
		IfFileExists $allScriptFile +1 +2
  		Call runScript
		StrCmp "$uMajorVersion.$uMinorVersion.$uServiceVersion" "$iMajorVersion.$iMinorVersion.$iServiceVersion" +1 +2
		Goto Done
		StrCmp $uMinorVersion "13" +1 +3
		StrCpy $uMinorVersion "0"
		Goto Loop2
		Goto Loop
	Loop2:
		IntOp $uMajorVersion $uMajorVersion + 1			
		;MessageBox MB_OK $uMajorVersion.$uMinorVersion.$uServiceVersion
		StrCpy $allScriptFile "$INSTDIR\${DB_SCRIPT_DIR}\V_$uMajorVersion_$uMinorVersion_$uServiceVersion\allScripts.sql"
		IfFileExists $allScriptFile +1 +2
  		Call runScript
		StrCmp "$uMajorVersion.$uMinorVersion.$uServiceVersion" "$iMajorVersion.$iMinorVersion.$iServiceVersion" +1 +2
		Goto Done
		Goto Loop
	Done:
	
	Pop $0
	StrCmp $0 "0" +2 +1
	MessageBox MB_OK "Unable to create/modify TalentPool Database. Please create/modify manually."
FunctionEnd

Function runScript	
	StrCmp $dbPassword "" +1 +3
	nsExec::ExecToLog 'cmd /C mysql.exe -u$dbUser < "$allScriptFile"'
	Goto end
	nsExec::ExecToLog 'cmd /C mysql.exe -u$dbUser -p$dbPassword < "$allScriptFile"'
	end:
FunctionEnd

Function createDBUser	
	StrCmp $dbPassword "" +1 +3
	nsExec::ExecToLog 'cmd /C mysql.exe -u$dbUser < "$scriptFile"'
	Goto end
	nsExec::ExecToLog 'cmd /C mysql.exe -u$dbUser -p$dbPassword < "$scriptFile"'
	end:
FunctionEnd
	
Function generateInstallationKey	
	!insertmacro MUI_INSTALLOPTIONS_READ $company_name "installationInfo.ini" "Field 2" "State"
	Call GetLocalTime 
	Pop $R0 ;Variable (for day) 
	Pop $R1 ;Variable (for month) 
	Pop $R2 ;Variable (for year) 
	Exec '$java_home\bin\javaw.exe -jar "$INSTDIR\${INSTALLATION_KEY_GENERATOR}" "$company_name" "${PRODUCT_NAME}" "${VERSION}" "$R0/$R1/$R2"'
FunctionEnd

Function GetLocalTime
 
  ; Prepare variables
  Push $0
  Push $1
  Push $2
  Push $3
  Push $4
  Push $5
  Push $6
 
  ; Call GetLocalTime API from Kernel32.dll
  System::Call '*(&i2, &i2, &i2, &i2, &i2, &i2, &i2, &i2) i .r0'
  System::Call 'kernel32::GetLocalTime(i) i(r0)'
  System::Call '*$0(&i2, &i2, &i2, &i2, &i2, &i2, &i2, &i2)i \
  (.r4, .r5, .r3, .r6, .r2, .r1, .r0,)'
 
  ; Day of week: convert to name
  StrCmp $3 0 0 +3
    StrCpy $3 Sunday
      Goto WeekNameEnd
  StrCmp $3 1 0 +3
    StrCpy $3 Monday
      Goto WeekNameEnd
  StrCmp $3 2 0 +3
    StrCpy $3 Tuesday
      Goto WeekNameEnd
  StrCmp $3 3 0 +3
    StrCpy $3 Wednesday
      Goto WeekNameEnd
  StrCmp $3 4 0 +3
    StrCpy $3 Thursday
      Goto WeekNameEnd
  StrCmp $3 5 0 +3
    StrCpy $3 Friday
      Goto WeekNameEnd
  StrCmp $3 6 0 +2
    StrCpy $3 Saturday
  WeekNameEnd:
 
  ; Minute: convert to 2 digits format
	IntCmp $1 9 0 0 +2
	  StrCpy $1 '0$1'
 
  ; Second: convert to 2 digits format
	IntCmp $0 9 0 0 +2
	  StrCpy $0 '0$0'
 
  ; Return to user
  Exch $6
  Exch
  Exch $5
  Exch
  Exch 2
  Exch $4
  Exch 2
  Exch 3
  Exch $3
  Exch 3
  Exch 4
  Exch $2
  Exch 4
  Exch 5
  Exch $1
  Exch 5
  Exch 6
  Exch $0
  Exch 6
 
FunctionEnd

Function .onInit
	ReadRegStr $installOrUpgrade HKLM "SOFTWARE\${PRODUCT_NAME}" "InstallDir" 
	ReadRegStr $userVersion HKLM "SOFTWARE\${PRODUCT_NAME}" "Version" 
	;Extract InstallOptions INI files
	!insertmacro MUI_INSTALLOPTIONS_EXTRACT "installOrUpgrade.ini"
	!insertmacro MUI_INSTALLOPTIONS_EXTRACT "installationInfo.ini"
	!insertmacro MUI_INSTALLOPTIONS_EXTRACT "ioPort.ini"
	!insertmacro MUI_INSTALLOPTIONS_EXTRACT "dbInfo.ini"
FunctionEnd

Function CustomPagePort
	Call skipCondition
	!insertmacro MUI_HEADER_TEXT "Port Selection" "Choose the port for Tomcat to run."
	!insertmacro MUI_INSTALLOPTIONS_DISPLAY "ioPort.ini"
FunctionEnd

Function InstallationOptions
	Call skipCondition
	again:
	!insertmacro MUI_HEADER_TEXT "Installation Information" ""
	!insertmacro MUI_INSTALLOPTIONS_DISPLAY "installationInfo.ini"
	!insertmacro MUI_INSTALLOPTIONS_READ $company_name "installationInfo.ini" "Field 2" "State"
	StrCmp $company_name "" +1 +3
	MessageBox MB_OK "Please Enter Installation Information."
	Goto again
	NextPage:
FunctionEnd

Function installOrUpgrade	
	StrCmp $installOrUpgrade "" +1 +4
	!insertmacro MUI_INSTALLOPTIONS_WRITE "installOrUpgrade.ini" "Field 1" "State" "1"
	!insertmacro MUI_INSTALLOPTIONS_WRITE "installOrUpgrade.ini" "Field 3" "State" "0"
	goto lbl111	
	!insertmacro MUI_INSTALLOPTIONS_WRITE "installOrUpgrade.ini" "Field 1" "State" "0"
	!insertmacro MUI_INSTALLOPTIONS_WRITE "installOrUpgrade.ini" "Field 3" "State" "1"
	lbl111:	
	; Read registry to detect the installation of Talentpool
	!insertmacro MUI_HEADER_TEXT "Install or Upgrade" ""
	!insertmacro MUI_INSTALLOPTIONS_DISPLAY "installOrUpgrade.ini"
FunctionEnd

Function skipCondition
	StrCmp $installOrUpgrade "" +2 +1
	Abort
	lbl:
FunctionEnd

; ---------------------------------------
; This function installs 
; Java Runtime Environment.
; ---------------------------------------
Function installJRE
	; Check for JRE
	IfFileExists $SYSDIR\javaw.exe label1
	Goto installjre

	label1:
	IfFileExists $SYSDIR\java.exe label3
	Goto installjre

	label3:
	; First get the installed version (if any)
	StrCpy $0 "SOFTWARE\JavaSoft\Java Runtime Environment\${REQUIRED_JRE_VERSION}"
	; Get Java Home attribute
	ReadRegStr $2 HKLM $0 "JavaHome"

	StrCmp $2 "" installjre
	Goto jrepresent

	installjre:
	DetailPrint "Launching JRE setup"
	File /oname=$TEMP\jre_setup.exe "${JRE_FILE}"
	ExecWait '"$TEMP\jre_setup.exe" /s /v$\"/qn ADDLOCAL=ALL REBOOT=Suppress /L C:\setup.log$\"' $0
	Delete $TEMP\jre_setup.exe

	StrCmp $0 "1" jrepresent 0
	DetailPrint "Java Setup finished with $0"

	StrCpy $0 "SOFTWARE\JavaSoft\Java Runtime Environment\${REQUIRED_JRE_VERSION}"
	; Get Java Home attribute
	ReadRegStr $2 HKLM $0 "JavaHome"

	jrepresent:	
	StrCpy $java_home $2
FunctionEnd

; ---------------------------------------
; This function installs Tomcat.
; ---------------------------------------
Function installTomcat
	!insertmacro MUI_INSTALLOPTIONS_READ $port_number "ioPort.ini" "Field 2" "State"

	ZipDLL::extractall "$INSTDIR\${APP_SERVER_FILE_ZIP}" "$INSTDIR\"
	Rename "$INSTDIR\${APP_SERVER_FILE}" "$INSTDIR\tomcat"
	
	Delete "$INSTDIR\${APP_SERVER_FILE_ZIP}" 	


	;Change the port
	Push 8080                                                               #text to be replaced
	Push $port_number                                                       #replace with
	Push all                                                                #replace all occurrences
	Push all                                                                #replace all occurrences
	Push "$INSTDIR\tomcat\conf\server.xml"                                  #file to replace in
	Call AdvReplaceInFile                                                   #call find and replace function
	
	;To Start tomcat as windows service
	SetOutPath "$INSTDIR"
	File ".\Java Service Wrapper\tools.jar"

	SetOutPath "$INSTDIR\tomcat\bin"
	File ".\Java Service Wrapper\bin\*.*"

	SetOutPath "$INSTDIR\tomcat\common\lib"
	File ".\Java Service Wrapper\lib\*.*"

	SetOutPath "$INSTDIR\tomcat\log"
	File ".\Java Service Wrapper\log\*.*"

	SetOutPath "$INSTDIR\tomcat\conf"
	File ".\Java Service Wrapper\conf\*.*"

	ReadRegStr $java_home HKLM "SOFTWARE\JavaSoft\Java Runtime Environment\${REQUIRED_JRE_VERSION}" "JavaHome"
		
	;MessageBox MB_OK $java_home
	
	Push "@java.exe@"
	Push $java_home\bin\java.exe
	Push all
	Push all
	Push "$INSTDIR\tomcat\conf\wrapper.conf"
	Call AdvReplaceInFile

	Push "@service_name@"
	Push ${TALENTPOOL_SERVICE}
	Push all
	Push all
	Push "$INSTDIR\tomcat\conf\wrapper.conf"
	Call AdvReplaceInFile
	
	Push @tools.jar@                                                             
	Push $INSTDIR\tools.jar                                                       
	Push all                                                                
	Push all                                                                
	Push "$INSTDIR\tomcat\conf\wrapper.conf"                                  
	Call AdvReplaceInFile                                                   

	Push @config.dir@                                                           
	Push $INSTDIR\config                                                    
	Push all                                                                
	Push all                                                                
	Push "$INSTDIR\tomcat\conf\wrapper.conf"                                  
	Call AdvReplaceInFile 
	
	Push "@java.heap.space@"
	Push ${JAVA_HEAP_SPACE}                                                 
	Push all                                                                
	Push all                                                                
	Push "$INSTDIR\tomcat\conf\wrapper.conf"                                  
	Call AdvReplaceInFile 

	nsExec::ExecToLog "$INSTDIR\tomcat\bin\InstallTomcat-NT.bat"
FunctionEnd

Function installMySQL
	;File ".\Java Service Wrapper\lib\*.*"
	CreateDirectory "$INSTDIR\mysql"
	SetOutPath "$INSTDIR"
	File "${MYSQL_CREATE_USER_SQL}" 
	File /r ".\dbscripts"
	SetOutPath "$INSTDIR\mysql"
	StrCmp $mySQLDecision "" installMySQL
	Goto mysqlPresent
	
	installMySQL:	
	File /oname=$TEMP\mysql_setup.exe ${MYSQL_SETUP}

	ExecWait 'msiexec /i "$TEMP\mysql_setup.exe" /qn INSTALLDIR="$INSTDIR\mysql"'
	Delete $TEMP\mysql_setup.exe

	StrCpy $dbUser "root"
	StrCpy $dbPassword ""
	StrCpy $mySQLInstallLocation "$INSTDIR\mysql\"
	StrCpy $createUser ""
	Goto end	
	
	mysqlPresent:	
	; read username, password information.
	!insertmacro MUI_INSTALLOPTIONS_READ $dbUser "dbInfo.ini" "Field 2" "State"
	!insertmacro MUI_INSTALLOPTIONS_READ $dbPassword "dbInfo.ini" "Field 4" "State"
	StrCpy $createUser "1"
	end:
	
	
	SetOutPath $mySQLInstallLocation
	File "tp.ini"

	Push @basedir@                                                          #text to be replaced
	Push $mySQLInstallLocation                                               #replace with
	Push all                                                                #replace all occurrences
	Push all                                                                #replace all occurrences
	Push "$mySQLInstallLocation\tp.ini"						#file to replace in
	Call AdvReplaceInFile                                                   #call find and replace function

	Push @datadir@                                                          #text to be replaced
	Push $mySQLInstallLocation\data                                                #replace with
	Push all                                                                #replace all occurrences
	Push all                                                                #replace all occurrences
	Push "$mySQLInstallLocation\tp.ini"						#file to replace in
	Call AdvReplaceInFile                                                   #call find and replace function
	
	SetOutPath "$INSTDIR\mysql\bin"
	nsExec::Exec 'mysqld.exe --install ${SERVICE_NAME} --defaults-file="$INSTDIR\mysql\tp.ini"'
	nsExec::ExecToLog 'NET START ${SERVICE_NAME}' $1
	sleep 3000

	;run below line only if mysql is not present
	StrCmp $createUser "" +2 +1
	Goto last
	
	Push "@db.username@"
	Push ${DB_USERNAME}
	Push all
	Push all
	Push "$INSTDIR\${MYSQL_CREATE_USER_SQL}"
	Call AdvReplaceInFile

	Push "@db.password@"
	Push ${DB_PASSWORD}
	Push all
	Push all
	Push "$INSTDIR\${MYSQL_CREATE_USER_SQL}"
	Call AdvReplaceInFile

	SetOutPath "$mySQLInstallLocation\bin"
	StrCpy $scriptFile "$INSTDIR\${MYSQL_CREATE_USER_SQL}"
	;MessageBox MB_OK $dbPassword
	IfFileExists $scriptFile +1 +3
	Call createDBUser
	
	StrCpy $dbUser "root"
	StrCpy $dbPassword ${DB_PASSWORD}
	
	nsExec::ExecToLog 'NET STOP ${SERVICE_NAME}' $1
	sleep 3000
	nsExec::ExecToLog 'NET START ${SERVICE_NAME}' $1
	sleep 3000
	
	last:

	SetOutPath "$mySQLInstallLocation"
	
	File /r ".\dbscripts"	
	Call executeScripts	
	
	SetOutPath "$INSTDIR"
FunctionEnd

Function readDBInfo
	Call skipCondition
	StrCpy $0 "SOFTWARE\MySQL AB\MySQL Server 5.0"
	StrCpy $1 ""
	; Get Version
	ReadRegStr $2 HKLM $0 "Version"
	StrCmp $2 $1 end
	MessageBox MB_YESNO|MB_ICONQUESTION "MySQL is already present. Do you want to use same instance?" IDYES useSameInstance
	StrCpy $mySQLDecision "0"
	MessageBox MB_OK "Please uninstall MySQL and again install the product TalentPool."
	Goto end
	useSameInstance:
	StrCpy $mySQLDecision "1"
	ReadRegStr $mySQLInstallLocation HKLM $0 "Location"		
	
	again:
	!insertmacro MUI_HEADER_TEXT "Database information" ""
	!insertmacro MUI_INSTALLOPTIONS_DISPLAY "dbInfo.ini"  
	!insertmacro MUI_INSTALLOPTIONS_READ $dbUser "dbInfo.ini" "Field 2" "State"
	!insertmacro MUI_INSTALLOPTIONS_READ $dbPassword "dbInfo.ini" "Field 4" "State"
	
	SetOutPath "$mySQLInstallLocation\bin"
	StrCmp $dbPassword "" +1 +3
	nsExec::ExecToLog 'cmd /C mysql.exe -u$dbUser -e "select now()"'
	Goto end1
	nsExec::ExecToLog 'cmd /C mysql.exe -u$dbUser -p$dbPassword -e "select now()"'
	end1:
	Pop $0
	StrCmp $0 "0" +3 +1
	MessageBox MB_OK "Invalid Username and/or Password."
	Goto again	
	end:

FunctionEnd

Function uninstallTomact
	; remove all the files and folders for tomact 5.0
	nsExec::ExecToLog "$INSTDIR\stopTalentPoolService.bat"
	nsExec::ExecToLog "$INSTDIR\tomcat\bin\UninstallTomcat-NT.bat"
	RMDIR /r "$INSTDIR\tomcat"
	RMDir /r /REBOOTOK "$SMPROGRAMS\Apache Tomcat 5.0"
	DeleteRegKey HKEY_LOCAL_MACHINE "SOFTWARE\Apache Software Foundation\Tomcat\5.0"
FunctionEnd

; The uninstall section
Section "Uninstall"
	SetOutPath $TEMP
	; remove all the files and folders
	nsExec::ExecToLog "$INSTDIR\stopTalentPoolService.bat"
	nsExec::ExecToLog "$INSTDIR\tomcat\bin\UninstallTomcat-NT.bat"
	RMDIR /r "$INSTDIR\tomcat"
	RMDir /r /REBOOTOK "$SMPROGRAMS\Apache Tomcat 5.0"
	DeleteRegKey HKEY_LOCAL_MACHINE "SOFTWARE\Apache Software Foundation\Tomcat\5.0"
		
	RMDIR /r "$INSTDIR\config"
	
	StrCpy $0 "SOFTWARE\MySQL AB\MySQL Server 5.0"
	ReadRegStr $mySQLInstallLocation HKLM $0 "Location"

	MessageBox MB_YESNO|MB_ICONQUESTION "Do you want to uninstall MySQL?" IDYES uninstallMySQL
	;RMDir /r /REBOOTOK "$mySQLInstallLocation\data\talentpool"
	Goto label

	uninstallMySQL:	
	File /oname=$TEMP\mysql_setup.exe ${MYSQL_SETUP}

	ExecWait 'msiexec /x "$TEMP\mysql_setup.exe" /qn'
	Delete $TEMP\mysql_setup.exe
	RMDir /r /REBOOTOK "$mySQLInstallLocation"

	label:	
	Delete "$INSTDIR\*.*"
	; Delete if empty
	RMDir /r "$INSTDIR\${JNI_DIR}"
	RMDir /r "$INSTDIR\${HELP_DIR}"
	RMDir /r "$INSTDIR\${REPORTS_DIR}"
	RMDir /r "$INSTDIR\${BENCHMARK_DIR}"
	RMDir /r "$INSTDIR\${TEMPLATES_DIR}"
	RMDir /r "$INSTDIR\${CONFIG_DIR}"
	RMDir /r "$INSTDIR\${LOG_DIR}"
	RMDir /r "$INSTDIR\${BACKUP_DIR}"
	RMDir /r "$INSTDIR\${DB_SCRIPT_DIR}"
	RMDir /r "$INSTDIR\${LICENSE_FILE_DIR}"	
	RMDir /r "$INSTDIR\${ICONS_DIR}"
	RMDir /r "$INSTDIR\${PLUGIN_DIR}"
	
	; Demodata removal while uninstallation
	RMDir /r "$INSTDIR\${DEMODATA_DIR}"	
	
	; tvendor removal while uninstallation
	RMDir /r "$INSTDIR\tvendor"	
	
	; temployee removal while uninstallation
	RMDir /r "$INSTDIR\temployee"	
	
	; temployee removal while uninstallation
	RMDir /r "$INSTDIR\twebsite"	
	
	RMDir $INSTDIR

	; now remove all the startmenu links
	Delete "$SMPROGRAMS\${PRODUCT_NAME}\Start ${PRODUCT_NAME} Service.lnk"
	Delete "$SMPROGRAMS\${PRODUCT_NAME}\Launch ${PRODUCT_NAME}.lnk"
	Delete "$SMPROGRAMS\${PRODUCT_NAME}\Stop ${PRODUCT_NAME} Service.lnk"
	Delete "$SMPROGRAMS\${PRODUCT_NAME}\Uninstall.lnk"	
	RMDIR "$SMPROGRAMS\${PRODUCT_NAME}"

	Delete "$DESKTOP\Launch ${PRODUCT_NAME}.lnk"
	
	; Now delete registry keys
	DeleteRegKey HKEY_LOCAL_MACHINE "SOFTWARE\${PRODUCT_NAME}"
	DeleteRegKey HKEY_LOCAL_MACHINE "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\${PRODUCT_NAME}"
	
SectionEnd

; Demodata for TalentPool
Function copyDemodataPool

	SetOverwrite on
	RMDir /r "$INSTDIR\${DEMODATA_DIR}"	
	
	SetOutPath $INSTDIR	
	File /r /x *.svn .\${DEMODATA_DIR}
	
	Push "@MYSQL_PATH@"
	Push $mySQLInstallLocation
	Push all
	Push all
	Push "$INSTDIR\demodata\restoreenterprise.bat"
	Call AdvReplaceInFile
	
	Push "@TALENTPOOL_PATH@"
	Push $INSTDIR
	Push all
	Push all
	Push "$INSTDIR\demodata\restoreenterprise.bat"
	Call AdvReplaceInFile
	
	Push "@MYSQL_USER@"
	Push "-u$dbUser"
	Push all
	Push all
	Push "$INSTDIR\demodata\restoreenterprise.bat"
	Call AdvReplaceInFile
	
	Push "@MYSQL_PASS@"
	StrCmp $dbPassword "" +3 +1
	Push "-p$dbPassword"
	goto lbl12
	Push ""
	lbl12:
	Push all
	Push all
	Push "$INSTDIR\demodata\restoreenterprise.bat"
	Call AdvReplaceInFile	
	
	Push "@MYSQL_PATH@"
	Push $mySQLInstallLocation
	Push all
	Push all
	Push "$INSTDIR\demodata\restoreprofessional.bat"
	Call AdvReplaceInFile
	
	Push "@TALENTPOOL_PATH@"
	Push $INSTDIR
	Push all
	Push all
	Push "$INSTDIR\demodata\restoreprofessional.bat"
	Call AdvReplaceInFile
	
	Push "@MYSQL_USER@"
	Push "-u$dbUser"
	Push all
	Push all
	Push "$INSTDIR\demodata\restoreprofessional.bat"
	Call AdvReplaceInFile
	
	Push "@MYSQL_PASS@"
	StrCmp $dbPassword "" +3 +1
	Push "-p$dbPassword"
	goto lbl13
	Push ""
	lbl13:
	Push all
	Push all
	Push "$INSTDIR\demodata\restoreprofessional.bat"
	Call AdvReplaceInFile
	
	Push "@MYSQL_PATH@"
	Push $mySQLInstallLocation
	Push all
	Push all
	Push "$INSTDIR\demodata\restorebasic.bat"
	Call AdvReplaceInFile
	
	Push "@TALENTPOOL_PATH@"
	Push $INSTDIR
	Push all
	Push all
	Push "$INSTDIR\demodata\restorebasic.bat"
	Call AdvReplaceInFile
	
	Push "@MYSQL_USER@"
	Push "-u$dbUser"
	Push all
	Push all
	Push "$INSTDIR\demodata\restorebasic.bat"
	Call AdvReplaceInFile
	
	Push "@MYSQL_PASS@"
	StrCmp $dbPassword "" +3 +1
	Push "-p$dbPassword"
	goto lbl14
	Push ""
	lbl14:
	Push all
	Push all
	Push "$INSTDIR\demodata\restorebasic.bat"
	Call AdvReplaceInFile
	

FunctionEnd	
; Demodata for TalentPool

;Start: Clubbing tvendor with talentpool
Function installTVendorApp
	
	;deploy application
	CreateDirectory "$INSTDIR\tvendor\config"
	SetOutPath "$INSTDIR\tvendor\config"
	File "tvendor\tvendor.properties"
	File "tvendor\log4j.properties"
	File "tvendor\smtp.properties"
	SetOutPath "$INSTDIR\tomcat\webapps"
	File "tvendor\tvendor.war" 

	; Changes in tvendor.properties
	
	Push "$INSTDIR\tomcat\webapps"
	Push "\" ;needs to be replaced
	Push "/" ;will replace wrong characters
	Call StrReplace
	Pop $0
	
	Push "@application.path@"
	Push $0
	Push all
	Push all
	Push "$INSTDIR\tvendor\config\tvendor.properties"
	Call AdvReplaceInFile

	Push "$INSTDIR\tvendor"
	Push "\" ;needs to be replaced
	Push "/" ;will replace wrong characters
	Call StrReplace
	Pop $0
	
	Push "@installation.path@"
	Push $0
	Push all
	Push all
	Push "$INSTDIR\tvendor\config\tvendor.properties"
	Call AdvReplaceInFile



	Push "http://$machine_name:$port_number/${PRODUCT_NAME}"
	Push "\" ;needs to be replaced
	Push "/" ;will replace wrong characters
	Call StrReplace
	Pop $0


	Push "@TalentPool.url@"
	Push $0
	Push all
	Push all
	Push "$INSTDIR\tvendor\config\tvendor.properties"
	Call AdvReplaceInFile

	;changes in wrapper.conf file
	Push @vendor.config.dir@                                                           
	Push $INSTDIR\tvendor\config                                                    
	Push all                                                                
	Push all                                                                
	Push "$INSTDIR\tomcat\conf\wrapper.conf"                                  
	Call AdvReplaceInFile	
	
FunctionEnd

Function upgradeTVendorApp
	
	; tvendor removal while upgrading
	RMDir /r  "$INSTDIR\tomcat\webapps\tvendor.war"	
	RMDIR /r "$INSTDIR\tomcat\webapps\tvendor"
	RMDIR /r "$INSTDIR\tomcat\work\Catalina\localhost\tvendor"
	RMDir /r "$INSTDIR\tvendor"		
	
	;deploy application
	CreateDirectory "$INSTDIR\tvendor\config"
	SetOutPath "$INSTDIR\tvendor\config"
	File "tvendor\tvendor.properties"
	File "tvendor\log4j.properties"
	File "tvendor\smtp.properties"
	SetOutPath "$INSTDIR\tomcat\webapps"
	File "tvendor\tvendor.war" 

	; Changes in tvendor.properties

	Push "$INSTDIR\tomcat\webapps"
	Push "\" ;needs to be replaced
	Push "/" ;will replace wrong characters
	Call StrReplace
	Pop $0

	Push "@application.path@"
	Push $0
	Push all
	Push all
	Push "$INSTDIR\tvendor\config\tvendor.properties"
	Call AdvReplaceInFile

	Push "$INSTDIR\tvendor"
	Push "\" ;needs to be replaced
	Push "/" ;will replace wrong characters
	Call StrReplace
	Pop $0

	Push "@installation.path@"
	Push $0
	Push all
	Push all
	Push "$INSTDIR\tvendor\config\tvendor.properties"
	Call AdvReplaceInFile


	Push "http://$machine_name:$port_number/${PRODUCT_NAME}"
	Push "\" ;needs to be replaced
	Push "/" ;will replace wrong characters
	Call StrReplace
	Pop $0


	Push "@TalentPool.url@"
	Push $0
	Push all
	Push all
	Push "$INSTDIR\tvendor\config\tvendor.properties"
	Call AdvReplaceInFile
	
	
FunctionEnd
;End: Clubbing tvendor with talentpool



;Start: Clubbing temployee with talentpool

Function installTEmployeeApp
	
	;deploy application
	SetOutPath "$INSTDIR"
	File /r .\temployee\${TEMPLATES_DIR}
	CreateDirectory "$INSTDIR\temployee\config"
	SetOutPath "$INSTDIR\temployee\config"
	File "temployee\temployee.properties"
	File "temployee\log4j.properties"
	SetOutPath "$INSTDIR\tomcat\webapps"
	File "temployee\temployee.war" 
	

	; Changes in temployee.properties
	
	Push "$INSTDIR\tomcat\webapps"
	Push "\" ;needs to be replaced
	Push "/" ;will replace wrong characters
	Call StrReplace
	Pop $0
	
	Push "@application.path@"
	Push $0
	Push all
	Push all
	Push "$INSTDIR\temployee\config\temployee.properties"
	Call AdvReplaceInFile

	Push "$INSTDIR\temployee"
	Push "\" ;needs to be replaced
	Push "/" ;will replace wrong characters
	Call StrReplace
	Pop $0
	
	Push "@installation.path@"
	Push $0
	Push all
	Push all
	Push "$INSTDIR\temployee\config\temployee.properties"
	Call AdvReplaceInFile
	
	Push "http://$machine_name:$port_number/${PRODUCT_NAME}"
	Push "\" ;needs to be replaced
	Push "/" ;will replace wrong characters
	Call StrReplace
	Pop $0

	Push "@TalentPool.url@"
	Push $0
	Push all
	Push all
	Push "$INSTDIR\temployee\config\temployee.properties"
	Call AdvReplaceInFile

	;changes in wrapper.conf file
	Push @employee.config.dir@                                                           
	Push $INSTDIR\temployee\config                                                    
	Push all                                                                
	Push all                                                                
	Push "$INSTDIR\tomcat\conf\wrapper.conf"                                  
	Call AdvReplaceInFile	
	
	
FunctionEnd

Function upgradeTEmployeeApp
	
	; temployee removal while upgrading
	RMDir /r  "$INSTDIR\tomcat\webapps\temployee.war"	
	RMDIR /r "$INSTDIR\tomcat\webapps\temployee"
	RMDIR /r "$INSTDIR\tomcat\work\Catalina\localhost\temployee"
	RMDir /r "$INSTDIR\temployee"		
	
	;deploy application
	SetOutPath "$INSTDIR"
	File /r .\temployee\${TEMPLATES_DIR}
	CreateDirectory "$INSTDIR\temployee\config"
	SetOutPath "$INSTDIR\temployee\config"
	File "temployee\temployee.properties"
	File "temployee\log4j.properties"
	SetOutPath "$INSTDIR\tomcat\webapps"
	File "temployee\temployee.war" 


	; Changes in temployee.properties

	Push "$INSTDIR\tomcat\webapps"
	Push "\" ;needs to be replaced
	Push "/" ;will replace wrong characters
	Call StrReplace
	Pop $0

	Push "@application.path@"
	Push $0
	Push all
	Push all
	Push "$INSTDIR\temployee\config\temployee.properties"
	Call AdvReplaceInFile

	Push "$INSTDIR\temployee"
	Push "\" ;needs to be replaced
	Push "/" ;will replace wrong characters
	Call StrReplace
	Pop $0

	Push "@installation.path@"
	Push $0
	Push all
	Push all
	Push "$INSTDIR\temployee\config\temployee.properties"
	Call AdvReplaceInFile

	Push "http://$machine_name:$port_number/${PRODUCT_NAME}"
	Push "\" ;needs to be replaced
	Push "/" ;will replace wrong characters
	Call StrReplace
	Pop $0

	Push "@TalentPool.url@"
	Push $0
	Push all
	Push all
	Push "$INSTDIR\temployee\config\temployee.properties"
	Call AdvReplaceInFile	
	
FunctionEnd

;End: Clubbing temployee with talentpool

;Start: Clubbing twebsite with talentpool
Function installTWebsiteApp
	
	;deploy application
	CreateDirectory "$INSTDIR\twebsite\config"
	SetOutPath "$INSTDIR\twebsite\config"
	File "twebsite\twebsite.properties"
	File "twebsite\log4j.properties"
	File "twebsite\smtp.properties"
	SetOutPath "$INSTDIR\tomcat\webapps"
	File "twebsite\twebsite.war" 

	; Changes in twebsite.properties
	
	Push "$INSTDIR\tomcat\webapps"
	Push "\" ;needs to be replaced
	Push "/" ;will replace wrong characters
	Call StrReplace
	Pop $0
	
	Push "@application.path@"
	Push $0
	Push all
	Push all
	Push "$INSTDIR\twebsite\config\twebsite.properties"
	Call AdvReplaceInFile

	Push "$INSTDIR\twebsite"
	Push "\" ;needs to be replaced
	Push "/" ;will replace wrong characters
	Call StrReplace
	Pop $0
	
	Push "@installation.path@"
	Push $0
	Push all
	Push all
	Push "$INSTDIR\twebsite\config\twebsite.properties"
	Call AdvReplaceInFile



	Push "http://$machine_name:$port_number/${PRODUCT_NAME}"
	Push "\" ;needs to be replaced
	Push "/" ;will replace wrong characters
	Call StrReplace
	Pop $0


	Push "@TalentPool.url@"
	Push $0
	Push all
	Push all
	Push "$INSTDIR\twebsite\config\twebsite.properties"
	Call AdvReplaceInFile

	;changes in wrapper.conf file
	Push @website.config.dir@                                                           
	Push $INSTDIR\twebsite\config                                                    
	Push all                                                                
	Push all                                                                
	Push "$INSTDIR\tomcat\conf\wrapper.conf"                                  
	Call AdvReplaceInFile	
	
FunctionEnd

Function upgradeTWebsiteApp
	
	; twebsite removal while upgrading
	RMDir /r  "$INSTDIR\tomcat\webapps\twebsite.war"	
	RMDIR /r "$INSTDIR\tomcat\webapps\twebsite"
	RMDIR /r "$INSTDIR\tomcat\work\Catalina\localhost\twebsite"
	RMDir /r "$INSTDIR\twebsite"		
	
	;deploy application
	CreateDirectory "$INSTDIR\twebsite\config"
	SetOutPath "$INSTDIR\twebsite\config"
	File "twebsite\twebsite.properties"
	File "twebsite\log4j.properties"
	File "twebsite\smtp.properties"
	SetOutPath "$INSTDIR\tomcat\webapps"
	File "twebsite\twebsite.war" 

	; Changes in twebsite.properties

	Push "$INSTDIR\tomcat\webapps"
	Push "\" ;needs to be replaced
	Push "/" ;will replace wrong characters
	Call StrReplace
	Pop $0

	Push "@application.path@"
	Push $0
	Push all
	Push all
	Push "$INSTDIR\twebsite\config\twebsite.properties"
	Call AdvReplaceInFile

	Push "$INSTDIR\twebsite"
	Push "\" ;needs to be replaced
	Push "/" ;will replace wrong characters
	Call StrReplace
	Pop $0

	Push "@installation.path@"
	Push $0
	Push all
	Push all
	Push "$INSTDIR\twebsite\config\twebsite.properties"
	Call AdvReplaceInFile


	Push "http://$machine_name:$port_number/${PRODUCT_NAME}"
	Push "\" ;needs to be replaced
	Push "/" ;will replace wrong characters
	Call StrReplace
	Pop $0


	Push "@TalentPool.url@"
	Push $0
	Push all
	Push all
	Push "$INSTDIR\twebsite\config\twebsite.properties"
	Call AdvReplaceInFile
	
	
FunctionEnd
;End: Clubbing twebsite with talentpool