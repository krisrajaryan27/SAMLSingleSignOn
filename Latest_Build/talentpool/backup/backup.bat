:: Author - Shivprasad
:: only following variables needs to set
:: SET MYSQL_PATH="D:\Env\MySQL\MySQL Server 5.0"  --> path tp bin folder
:: SET TALENTPOOL_PATH="C:\Program Files\TalentPool" --> path to talentpool install dir
:: SET MYSQL_USER=-uroot --> please do not add doublw quote and add -u in front of user name
:: SET MYSQL_PASS=-pxxxx --> please add -p in front of password and If no password then keep it blank without -p

:: SET ECHO OFF ON THE SCREEN
@ECHO OFF

SET MYSQL_PATH="@MYSQL_PATH@"
SET MYSQL_USER=@MYSQL_USER@
SET MYSQL_PASS=@MYSQL_PASS@
SET TALENTPOOL_PATH="@TALENTPOOL_PATH@"

:: path to backup directory to be provided by user
SET BKUP_PATH=%1

:: MAKE FOLDER WITH CURRENT DATE
FOR /F "TOKENS=1* DELIMS= " %%A IN ('DATE/T') DO SET CDATE=%%B
FOR /F "TOKENS=1,2 eol=/ DELIMS=/ " %%A IN ('DATE/T') DO SET mm=%%B
FOR /F "TOKENS=1,2 DELIMS=/ eol=/" %%A IN ('echo %CDATE%') DO SET dd=%%B
FOR /F "TOKENS=2,3 DELIMS=/ " %%A IN ('echo %CDATE%') DO SET yyyy=%%B
SET DATE_FOLDER=%yyyy%%mm%%dd%

SET BKUP_PATH=%BKUP_PATH%\%DATE_FOLDER%
MKDIR %BKUP_PATH%
MKDIR %BKUP_PATH%\documents

:: copy mysqldump and documents and attachments
%MYSQL_PATH%\bin\mysqldump -R %MYSQL_USER% %MYSQL_PASS% talentpool>%BKUP_PATH%\talentpool.sql
xcopy %TALENTPOOL_PATH%\documents %BKUP_PATH%\documents /E /K /Y /Q

