echo off

set WEBLOGIC_ROOT=C:\bea
set WEBLOGIC_LIB=%WEBLOGIC_ROOT%\weblogic92\server\lib
set JAVA_BIN=%WEBLOGIC_ROOT%\jrockit90_150_10\bin
set PROTOCOL_HOST_AND_PORT=t3://localhost:80
REM set PROTOCOL_HOST_AND_PORT=t3s://localhost:9002

REM Usage: [filler] [iterations] [client sleep time] [client id] [async flag] [publish flag] [server sleep time]

%JAVA_BIN%\java -classpath ejbclientimpl.jar;fubarBMTEJBClientJ2SE.jar;%WEBLOGIC_LIB%\weblogic.jar  -Djava.naming.provider.url=%PROTOCOL_HOST_AND_PORT% -Djava.naming.factory.initial=weblogic.jndi.WLInitialContextFactory -Dweblogic.security.TrustKeyStore=DemoTrust com.weblogic.sample.clientapp.FubarBMTEJBAppClient filler 1 100 1 false false 100 

pause

