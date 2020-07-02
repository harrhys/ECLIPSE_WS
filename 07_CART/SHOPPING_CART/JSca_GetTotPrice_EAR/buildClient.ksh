#!/bin/sh

source "C:\bea\src1032\currNight\weblogic\server\bin\setWLSEnv.sh"
ant -buildfile Z:/sandbox/dev/src1032/wls/tools/system_tests/automation/tests/JScaLite/src/JSca_GetTotPrice_EAR/build.xml clean-client
ant -buildfile Z:/sandbox/dev/src1032/wls/tools/system_tests/automation/tests/JScaLite/src/JSca_GetTotPrice_EAR/build.xml build-client
read prompt?'Press any key to continue .....'

