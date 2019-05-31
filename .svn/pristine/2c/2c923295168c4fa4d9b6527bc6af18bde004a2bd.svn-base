:: Author - Shantanu Sikdar

SET MYSQL_PATH="@MYSQL_PATH@"
SET MYSQL_USER=@MYSQL_USER@
SET MYSQL_PASS=@MYSQL_PASS@
SET TALENTPOOL_PATH="@TALENTPOOL_PATH@"
SET DEMODATA_PATH= %TALENTPOOL_PATH%\demodata


cd /D %MYSQL_PATH%\bin
mysql %MYSQL_USER% %MYSQL_PASS% -e "drop database talentpool";
mysql %MYSQL_USER% %MYSQL_PASS% -e "create database talentpool";
mysql %MYSQL_USER% %MYSQL_PASS% talentpool<%DEMODATA_PATH%\tpdemo.sql
mysql %MYSQL_USER% %MYSQL_PASS% -e "call talentpool.RESTORE_SNAPSHOT()";
xcopy %DEMODATA_PATH%\demo_documents %TALENTPOOL_PATH%\documents /E /K /Y
xcopy c:\demodata\professional.lic %TALENTPOOL_PATH%\license\license.lic /K /Y

::cd /D "C:\Program Files\TalentPool\mysql\bin"
::mysql -uroot -e "drop database talentpool"; 
::mysql -uroot -e "create database talentpool";
::mysql -uroot talentpool<"c:\demodata\tpdemo.sql"
::mysql -uroot -e "call talentpool.RESTORE_SNAPSHOT()";
::xcopy c:\demodata\documents "C:\Program Files\TalentPool\documents" /E /K /Y
::xcopy c:\demodata\professional.lic "C:\Program Files\TalentPool\license\license.lic" /K /Y




