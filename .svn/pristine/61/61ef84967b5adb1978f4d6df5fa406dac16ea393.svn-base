|--------------------------------------------------------------------------|
|			  TalentPool Installer                                         |
|--------------------------------------------------------------------------|
	The purpose of this document is to describe the steps to be followed to
create the installer for the TalentPool application.    					

1) Download NSIS (Nullsoft scriptable install system) from page 
   http://nsis.sourceforge.net/Main_Page. Install NSIS on your machine.
2) Checkout the TalentPool source code from repository.
3) Modify the following properties into 
   TalentPool/build/build_talentpool.properties as described.
   app.home -> The location where the TalentPool source code is checked 
               out from repository.
   catalina.home -> The location where the application server Tomcat is 
                    installed on your machine.
   jdk.home -> The location where Java SDK is installed on your machine.
   deploy.home -> Any existing path on your machine. Generally its 
                  app.home/Latest_Build
4) Execute the target all in Ant Task TalentPool/build/build_talentpool.xml
   It will provide you following files into at location pointed to by 
   deploy.home.
   - TalentPool.war
   - installDBScript.sql
   - tables_mysql_quartz.sql
   - talentpool.properties
   - quartz.properties
   - log4j.properties
   - setup.ico
   - uninstall.ico
   - talentpool.ico
5) Move the above three files to folder TalentPool/Installer.
6) Goto TalentPool/Installer through Windows Explorer. Right click on
   talentpool.nsis and choose Compile NSIS Script. This will provide 
   you with talentpool.exe, the installer program from setting up the 
   TalentPool application, in the same directory.