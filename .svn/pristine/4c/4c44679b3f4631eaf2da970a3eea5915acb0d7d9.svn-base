@echo off
set install_dir=D:\Projects\talentpool\Branches\v_3_5_0
set orig_cp=%classpath%
set classpath=%classpath%;%install_dir%\ui\WEB-INF\lib\axis.jar
set classpath=%classpath%;%install_dir%\ui\WEB-INF\lib\jaxrpc.jar
set classpath=%classpath%;%install_dir%\ui\WEB-INF\lib\commons-discovery-0.2.jar
set classpath=%classpath%;%install_dir%\ui\WEB-INF\lib\commons-logging-api.jar
set classpath=%classpath%;%install_dir%\ui\WEB-INF\lib\log4j-1.2.13.jar
set classpath=%classpath%;%install_dir%\ui\WEB-INF\lib\saaj.jar
set classpath=%classpath%;%install_dir%\ui\WEB-INF\lib\wsdl4j-1.5.1.jar
set classpath=%classpath%;D:\Projects\talentpool\Branches\v_3_5_0\ui\WEB-INF\classes
@echo on
java org.apache.axis.client.AdminClient -lhttp://localhost:8080/tb350/servlet/AxisServlet -h localhost -p 8080 D:\Projects\talentpool\Branches\v_3_5_0\build\com\talentPool\services\deploy.wsdd
set classpath=%orig_cp%
