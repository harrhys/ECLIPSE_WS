Quickstart notes:
	- Ensure Weblogic is located at: C:/bea/weblogic92
	- Ensure Eclipse workspace is located at: C:/eclipse3.3.1/workspace
	- Ensure Eclipse BEA_WEBLOGIC_JAR classpath variable is defined (e.g. C:/bea/weblogic92/server/lib/weblogic.jar)
	- Ensure Eclipse BEA_WEBSERVICES_JAR classpath variable is defined (e.g. C:/bea/weblogic92/server/lib/webservices.jar)
	- Unzip fubar.zip directly into the Eclipse workspace directory
	- Import all "Fubar*" projects into the Eclipse workspace
	- Run the ANT build.xml in FubarAReadme/build
	- 5 resulting server EAR files will then be available in FubarAReadme/build/fubar/server
	- 3 resulting client .cmd files (with supporting jars) will then be available in FubarAReadme/build/fubar/client
	
EJB, WebApp and WebSvc client application usage notes:
	[ip:port -or- filler] - ip and port address of the WebApp/WebSvc server for the WebApp/WebSvc client -or- NA filler for EJB client
		Note: for the EJB client the WL server connect information is set via the following J2EE properties:
			-Djava.naming.provider.url=t3://localhost:80
			-Djava.naming.factory.initial=weblogic.jndi.WLInitialContextFactory 
	[iterations] - number of iterations that should be performed
	[client sleep time] - time (in milliseconds, not randomized) that client should sleep between iterations
	[client id] - unique string id for "this" client
	[async flag] - if true then EJB will put to Queue
	[publish flag] - if true then EJB will put to Topic
	[server sleep time] - time (in milliseconds, not randomized) that sleep should sleep within a request

Entry points:
- Servlet:
	Active: http://localhost:80/fubar
	Admin: https://localhost:9002/fubar (OPTIONAL)
	Example URL Request:
		http://localhost/fubar?&clientID=client123&asyncFlag=false&publishFlag=false&sleepTime=0&reqNumber=1234
- WebService:
	Active: http://localhost:80/FubarWSApp/Series1
	Admin: https://localhost:9002/FubarWSApp/Series1 (OPTIONAL)
	Example WebService SOAP Request:
		<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
		  <soapenv:Header />
		    <soapenv:Body>
		    <sampleMethod xmlns="http://www.obopay.com" xmlns:java="java:com.weblogic.sample.common">
		      <inputData>
		        <java:clientID>client123</java:clientID>
		        <java:asyncFlag>false</java:asyncFlag>
		        <java:publishFlag>false</java:publishFlag>
		        <java:sleepTime>0</java:sleepTime>
		        <java:reqNumber>1234</java:reqNumber>
		      </inputData>
		    </sampleMethod>
		  </soapenv:Body>
		</soapenv:Envelope>

Weblogic server configuration:
- For the following, access the Weblogic admin console via:
	http://localhost:xxxx/console

- For setting up domain to allow admin requests (OPTIONAL)
	- select-domain->Configuration->General->Enable Administration Port
	- See:
		http://download.oracle.com/docs/cd/E13222_01/wls/docs92/ConsoleHelp/taskhelp/domainconfig/EnableTheDomainwideAdministrationPort.html
		http://localhost:xxxx/console-help/doc/en-us/com/bea/wlserver/core/index.html
			How Do I...
				Configure the Weblogic Server environment
					Configure network connections
						Configure the domain-dide administration port

- For the Queue
	- Services->Messaging->JMS Servers
	- JMS Server
		- FubarQueueServer
			- Name FubarQueueServer
			- Next->Target->Finish
			- Activate Changes
	- Services->Messaging->JMS Modules
	- JMS Modules
		- FubarQueueModule
			- Name: FubarQueueModule
			- Target
			- Yes add resources->Finish
			- New->Connection Factory->Next
				- Name: FubarQueueFactory
				- JNDI Name: com.weblogic.sample.mdbapp.FubarQueueFactory
				- Target->Finish
			- New->Queue->Next
				- Name: FubarQueue
				- JNDI Name: com.weblogic.sample.mdbapp.FubarQueue
				- Next
				- Create a New Subdeployment
					- Name: FubarQueueSD
					- Ok
				- Target->FubarQueueServer
				- Finish

- For the Topic
	- Services->Messaging->JMS Servers
	- JMS Server
		- FubarTopicServer
			- Name FubarTopicServer
			- Next->Target->Finish
			- Activate Changes
	- Services->Messaging->JMS Modules
	- JMS Modules
		- FubarTopicModule
			- Name: FubarTopicModule
			- Target
			- Yes add resources->Finish
			- New->Connection Factory->Next
				- Name: FubarTopicFactory
				- JNDI Name: com.weblogic.sample.mdbapp.FubarTopicFactory
				- Target->Finish
			- New->Topic->Next
				- Name: FubarTopic
				- JNDI Name: com.weblogic.sample.mdbapp.FubarTopic
				- Next
				- Create a New Subdeployment
					- Name: FubarTopicSD
					- Ok
				- Target->FubarTopicServer
				- Finish
