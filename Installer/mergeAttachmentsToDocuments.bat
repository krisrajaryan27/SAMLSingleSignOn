:: Author - Pallavi
:: SET TALENTPOOL_PATH="C:\Program Files\TalentPool" --> path to talentpool install dir

:: SET ECHO OFF ON THE SCREEN
@ECHO ON

::SET TALENTPOOL_PATH="C:\Program Files\TalentPool"

SET TALENTPOOL_PATH="@TALENTPOOL_PATH@"

xcopy %TALENTPOOL_PATH%\attachments %TALENTPOOL_PATH%\documents /E /K /Y


