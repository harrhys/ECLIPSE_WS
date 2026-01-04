#!/bin/bash
export LD_LIBRARY_PATH=/space/orbacus4.1/lib
export TOPDIR=./samples/rmi_iiop/cpp/ejb/SunOS5.8_DBG.OBJ
#$TOPDIR/client -ORBInitRef NameService=corbaloc:iiop:<host>.<domain>:<port>/NameService
$TOPDIR/client -ORBInitRef NameService=corbaloc:iiop:lilly.india.sun.com:3700/NameService
