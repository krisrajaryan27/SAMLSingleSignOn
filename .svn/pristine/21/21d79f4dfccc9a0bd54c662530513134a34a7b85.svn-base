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

:: Change directory
cd /D %MYSQL_PATH%\bin
mysql %MYSQL_USER% %MYSQL_PASS% -e "drop database talentpool"; 
mysql %MYSQL_USER% %MYSQL_PASS% -e "create database talentpool";
mysql %MYSQL_USER% %MYSQL_PASS% talentpool<%BKUP_PATH%\talentpool.sql

MKDIR %TALENTPOOL_PATH%\tomcat\webapps\TalentPool
MKDIR %TALENTPOOL_PATH%\tomcat\webapps\TalentPool\attachments
MKDIR %TALENTPOOL_PATH%\tomcat\webapps\TalentPool\documents
MKDIR %TALENTPOOL_PATH%\reports
MKDIR %TALENTPOOL_PATH%\templates
MKDIR %TALENTPOOL_PATH%\config

::xcopy %BKUP_PATH%\attachments %TALENTPOOL_PATH%\tomcat\webapps\TalentPool\attachments /E /K /Y
::xcopy %BKUP_PATH%\documents %TALENTPOOL_PATH%\tomcat\webapps\TalentPool\documents /E /K /Y
copy /Y %BKUP_PATH%\TalentPool.war %TALENTPOOL_PATH%\tomcat\webapps\
xcopy %BKUP_PATH%\reports %TALENTPOOL_PATH%\reports  /E /K /Y
xcopy %BKUP_PATH%\templates %TALENTPOOL_PATH%\templates  /E /K /Y
xcopy %BKUP_PATH%\config %TALENTPOOL_PATH%\config /E /K /Y



