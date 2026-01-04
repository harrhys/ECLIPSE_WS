#!/bin/bash
export IASINSTALLDIR=/space/sias7
export JAVA_HOME=$IASINSTALLDIR/jdk
export RMIC=$JAVA_HOME/bin/rmic

# UnComment the lines below, to generate IDL's from the Bean Definitions
#$RMIC -classpath ../../assemble/jar/BookCartAppEjb.jar:$IASINSTALLDIR/appserv/lib/appserv-ext.jar -idl samples.rmi_iiop.cpp.ejb.CartHome
#$RMIC -classpath ../../assemble/jar/BookCartAppEjb.jar:$IASINSTALLDIR/appserv/lib/appserv-ext.jar -idl samples.rmi_iiop.cpp.ejb.Cart
#$RMIC -classpath ../../assemble/jar/BookCartAppEjb.jar:$IASINSTALLDIR/appserv/lib/appserv-ext.jar -idl samples.rmi_iiop.cpp.ejb.BookException
#$RMIC -classpath ../../assemble/jar/BookCartAppEjb.jar:$IASINSTALLDIR/appserv/lib/appserv-ext.jar -idl samples.rmi_iiop.cpp.ejb.InterfaceTestClass
