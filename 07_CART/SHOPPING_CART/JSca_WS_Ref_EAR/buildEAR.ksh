#!/bin/sh

source "C:\bea\wls103\currManoj.1\wlserver_10.3\server\bin\setWLSEnv.sh"
ant -buildfile Z:/sandbox/dev/src1032/wls/tools/system_tests/automation/tests/JScaLite/src/JSca_WS_Ref_EAR/build.xml clean
ant -buildfile Z:/sandbox/dev/src1032/wls/tools/system_tests/automation/tests/JScaLite/src/JSca_WS_Ref_EAR/build.xml init
ant -buildfile Z:/sandbox/dev/src1032/wls/tools/system_tests/automation/tests/JScaLite/src/JSca_WS_Ref_EAR/build.xml build-service
read prompt?'Press any key to continue .....'

