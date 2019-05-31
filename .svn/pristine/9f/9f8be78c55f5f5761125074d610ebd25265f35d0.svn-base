:: Author - Shivprasad
:: only following variables needs to set
:: SET MYSQL_PATH="D:\Env\MySQL\MySQL Server 5.0"  --> path tp bin folder
:: SET TALENTPOOL_PATH="C:\Program Files\TalentPool" --> path to talentpool install dir
:: SET MYSQL_USER=-uroot --> please do not add doublw quote and add -u in front of user name
:: SET MYSQL_PASS=-pxxxx --> please add -p in front of password and If no password then keep it blank without -p

:: SET ECHO OFF ON THE SCREEN
@ECHO ON

::SET MYSQL_PATH="D:\Env\MySQL\MySQL Server 5.0"
::SET MYSQL_USER=-uroot
::SET MYSQL_PASS=-ptp00l
::SET TALENTPOOL_PATH="C:\Program Files\TalentPool"

SET MYSQL_PATH="@MYSQL_PATH@"
SET MYSQL_USER=@MYSQL_USER@
SET MYSQL_PASS=@MYSQL_PASS@
SET TALENTPOOL_PATH="@TALENTPOOL_PATH@"

:: path to backup directory to be provided by user
SET BKUP_PATH= %TALENTPOOL_PATH%\upgrade_bkup
rmdir /S /Q %TALENTPOOL_PATH%\upgrade_bkup

MKDIR %BKUP_PATH%
MKDIR %BKUP_PATH%\documents
MKDIR %BKUP_PATH%\attachments
MKDIR %BKUP_PATH%\reports
MKDIR %BKUP_PATH%\customReports
MKDIR %BKUP_PATH%\templates
MKDIR %BKUP_PATH%\config

:: copy mysqldump and documents and attachments
%MYSQL_PATH%\bin\mysqldump -R %MYSQL_USER% %MYSQL_PASS% talentpool>%BKUP_PATH%\talentpool.sql
xcopy %TALENTPOOL_PATH%\tomcat\webapps\TalentPool\attachments %BKUP_PATH%\attachments /E /K /Y
xcopy %TALENTPOOL_PATH%\tomcat\webapps\TalentPool\documents %BKUP_PATH%\documents /E /K /Y
copy /Y %TALENTPOOL_PATH%\tomcat\webapps\TalentPool.war %BKUP_PATH%\
xcopy %TALENTPOOL_PATH%\reports %BKUP_PATH%\reports /E /K /Y
xcopy %TALENTPOOL_PATH%\customReports %BKUP_PATH%\customReports /E /K /Y
xcopy %TALENTPOOL_PATH%\templates %BKUP_PATH%\templates /E /K /Y
xcopy %TALENTPOOL_PATH%\config %BKUP_PATH%\config /E /K /Y

