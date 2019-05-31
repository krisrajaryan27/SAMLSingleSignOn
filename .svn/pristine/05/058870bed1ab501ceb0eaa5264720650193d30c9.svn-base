:: ............................................................
::
:: File Name         : init_ant.bat
:: Description       : kick off ant build for ToutVirtual.
::
:: Usage             : see Notes below
:: 
:: ............................................................

set ANT_HOME=D:/Env/apache-ant-1.7.0RC1
set JAVA_HOME=D:/Env/jdk1.6.0

echo off
set oldCP=%CLASS_PATH%

:set CLASS_PATH=
:set PATH=
set PATH=%PATH%;%ANT_HOME%\bin;%JAVA_HOME%\bin

:set eDate=%DATE:~10%.%DATE:~4,2%.%DATE:~7,2%
eDate = %DATE%
set antLog=-logfile %1.build_talentpool.%eDate%.log
SET ANT_OPTS=-Xmx256m

ant -buildfile build_talentpool.xml -verbose %antLog% %1

set CLASS_PATH=%oldCP%
set PATH=%oldPath%
echo on

:: ............................................................
::
:: Notes              
::   1. enter correct paths for ANT_HOME and JAVA_HOME.
::   2. a build log is written to the current directory.
::      review the log file for errors, since they do not 
::      appear on screen.
::   3. the target is listed as the final input parameter on 
::      the command line which invokes ant, i.e., 
::      i.e., "ant -buildfile... all".
::      these are the available targets in build_talentpool.xml:
::
::      target name  description
::      "all"        depends="clean,compile,jar,dist"
::                   forces a complete recompile.
::                   
::      "clean"      deletes "build" and "dist" directories.
::
::      "compile"    depends="prepare", Compile Java sources.
::
::      "jar"        to jar only.
::
::      "deploy"     depends="compile,jar".
::                   copies application files to the locations
::                   the servlet container is running from,
::                   as specified under "deploy.home" in 
::                   build_talentpool.properties.
::
::       "dist"      organize files for installer then zip.
::                   the location where zip files created
::                   is specified under "dist.home" in 
::                   build_talentpool.properties.
::       
::       "javadoc"   depends="compile", Create Javadoc.
::
::       "prepare"   create the "build" and "install" 
::                   directories, and copy the static contents
::                   of your web application to it.
::   
::    4. refer to the build_talentpool.xml for more details.
:: ............................................................
