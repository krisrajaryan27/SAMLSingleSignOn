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