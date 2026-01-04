rem set these three environment variables appropriately
set JAVA_HOME=C:\j2sdk1.4.0
set RMIC=$JAVA_HOME\bin\rmic
set IASINSTALLDIR=C:\s1as7

rem UnComment the lines below, to generate IDL's from the Bean Definitions
rem $RMIC -classpath ..\..\..\assemble\BookCartAppEjb.jar:%IASINSTALLDIR%\appserv\lib\appserv-ext.jar -idl samples.rmi_iiop.cpp.ejb.CartHome
rem $RMIC -classpath ..\..\..\assemble\BookCartAppEjb.jar:%IASINSTALLDIR%\appser
v\lib\appserv-ext.jar -idl samples.rmi_iiop.cpp.ejb.Cart
rem $RMIC -classpath ..\..\..\assemble\BookCartAppEjb.jar:%IASINSTALLDIR%\appser
v\lib\appserv-ext.jar -idl samples.rmi_iiop.cpp.ejb.BookException
rem $RMIC -classpath ..\..\..\assemble\BookCartAppEjb.jar:%IASINSTALLDIR%\appser
v\lib\appserv-ext.jar -idl samples.rmi_iiop.cpp.ejb.InterfaceTestClass

