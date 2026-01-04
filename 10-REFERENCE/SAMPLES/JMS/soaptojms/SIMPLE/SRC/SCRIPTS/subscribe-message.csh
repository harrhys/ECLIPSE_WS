#!/bin/csh
setenv IMQ_HOME  $AS_HOME/imq
setenv JAVA_HOME $AS_HOME/jdk
setenv CLASSPATH $IMQ_HOME/lib/jms.jar:$IMQ_HOME/lib/imq.jar:$IMQ_HOME/lib/imqutil.jar:$IMQ_HOME/lib/saaj-api.jar:$IMQ_HOME/lib/jaxm-api.jar:$IMQ_HOME/lib/activation.jar:$IMQ_HOME/lib/imqxm.jar:$AS_HOME/samples/jms/soaptojms/simple/SOAPtoJMSMessageSample.jar:$AS_HOME/samples/jms/soaptojms/simple/build/classes

echo "$JAVA_HOME/bin/java samples.jms.soaptojms.ReceiveSOAPMessageWithJMS TESTTOPIC"
echo ""
$JAVA_HOME/bin/java samples.jms.soaptojms.ReceiveSOAPMessageWithJMS TESTTOPIC 
