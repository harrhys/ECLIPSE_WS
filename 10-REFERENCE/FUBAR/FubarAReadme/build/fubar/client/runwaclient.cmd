echo off

set WEBLOGIC_ROOT=C:\bea
set WEBLOGIC_LIB=%WEBLOGIC_ROOT%\weblogic92\server\lib
set JAVA_BIN=%WEBLOGIC_ROOT%\jrockit90_150_10\bin
set PROTOCOL_HOST_AND_PORT=http://localhost:80
REM set PROTOCOL_HOST_AND_PORT=https://localhost:9002

REM Usage: [protocol://host:port] [iterations] [client sleep time] [client id] [async flag] [publish flag] [server sleep time]

%JAVA_BIN%\java -classpath fubarcommon.jar;waclientimpl.jar com.weblogic.sample.clientapp.FubarWebAppClient %PROTOCOL_HOST_AND_PORT% 1 100 1 false false 100 

pause

