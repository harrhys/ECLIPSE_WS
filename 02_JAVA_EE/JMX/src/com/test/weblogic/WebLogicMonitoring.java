package com.test.weblogic;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Hashtable;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.naming.Context;

public class WebLogicMonitoring {
	private static MBeanServerConnection connection;
	private static JMXConnector connector;
	private static final ObjectName service;
	static {
		try {
			service = new ObjectName(
					"com.bea:Name=DomainRuntimeService,Type=weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean");
		} catch (MalformedObjectNameException e) {
			throw new AssertionError(e.getMessage());
		}
	}

	public static void initConnection(String hostname, String portString,
			String username, String password) throws IOException,
			MalformedURLException {
		String protocol = "t3";
		Integer portInteger = Integer.valueOf(portString);
		int port = portInteger.intValue();
		String jndiroot = "/jndi/";
		String mserver = "weblogic.management.mbeanservers.domainruntime";
		JMXServiceURL serviceURL = new JMXServiceURL(protocol, hostname, port,
				jndiroot + mserver);
		Hashtable h = new Hashtable();
		h.put(Context.SECURITY_PRINCIPAL, username);
		h.put(Context.SECURITY_CREDENTIALS, password);
		h.put(JMXConnectorFactory.PROTOCOL_PROVIDER_PACKAGES,
				"weblogic.management.remote");
		connector = JMXConnectorFactory.connect(serviceURL, h);
		connection = connector.getMBeanServerConnection();
	}

	public static ObjectName[] getServerRuntimes() throws Exception {
		return (ObjectName[]) connection
				.getAttribute(service, "ServerRuntimes");
	}

	public void printNameAndState() throws Exception {
		ObjectName[] serverRT = getServerRuntimes();
		System.out.println(" Server   State");
		System.out.println("            ………………………………………..\n");
		int length = (int) serverRT.length;
		for (int i = 0; i < length; i++) {
			String name = (String) connection.getAttribute(serverRT[i], "Name");
			String state = (String) connection.getAttribute(serverRT[i],
					"State");
			System.out.println(name + " : " + state);
		}
	}

	public void threadPoolRuntime() throws Exception {
		ObjectName[] serverRT = getServerRuntimes();
		int length = (int) serverRT.length;
		for (int i = 0; i < length; i++) {
			String name = (String) connection.getAttribute(serverRT[i], "Name");
			ObjectName threadRT = (ObjectName) connection.getAttribute(
					serverRT[i], "ThreadPoolRuntime");
			System.out.println("\n……………….<" + name
					+ " :.. ThreadPoolRuntime>…………….");
			System.out.println("CompletedRequestCount        : "
					+ connection
							.getAttribute(threadRT, "CompletedRequestCount"));
			System.out.println("ExecuteThreadTotalCount      : "
					+ connection.getAttribute(threadRT,
							"ExecuteThreadTotalCount"));
			System.out.println("ExecuteThreadIdleCount       : "
					+ connection.getAttribute(threadRT,
							"ExecuteThreadIdleCount"));
			System.out.println("HealthState                  : "
					+ connection.getAttribute(threadRT, "HealthState"));
			System.out.println("HoggingThreadCount           : "
					+ connection.getAttribute(threadRT, "HoggingThreadCount"));
			System.out.println("PendingUserRequestCount      : "
					+ connection.getAttribute(threadRT,
							"PendingUserRequestCount"));
			System.out.println("QueueLength                  : "
					+ connection.getAttribute(threadRT, "QueueLength"));
			System.out.println("SharedCapacityForWorkManagers: "
					+ connection.getAttribute(threadRT,
							"SharedCapacityForWorkManagers"));
			System.out.println("StandbyThreadCount           : "
					+ connection.getAttribute(threadRT, "StandbyThreadCount"));
			System.out.println("Suspended                    : "
					+ connection.getAttribute(threadRT, "Suspended"));
			System.out.println("Throughput                   : "
					+ connection.getAttribute(threadRT, "Throughput"));
			System.out.println("………………………………………………………………\n");
		}
	}

	public void getJvmRuntime() throws Exception {
		ObjectName[] serverRT = getServerRuntimes();
		int length = (int) serverRT.length;
		for (int i = 0; i < length; i++) {
			String name = (String) connection.getAttribute(serverRT[i], "Name");
			ObjectName jvmRT = (ObjectName) connection.getAttribute(
					serverRT[i], "JVMRuntime");
			System.out.println("\n……………..<" + name + " : .JVMRuntime>…………….");
			System.out.println("HeapFreeCurrent            :"
					+ connection.getAttribute(jvmRT, "HeapFreeCurrent"));
			System.out.println("HeapFreePercent            :"
					+ connection.getAttribute(jvmRT, "HeapFreePercent"));
			System.out.println("HeapSizeCurrent            :"
					+ connection.getAttribute(jvmRT, "HeapSizeCurrent"));
			System.out.println("HeapSizeMax                :"
					+ connection.getAttribute(jvmRT, "HeapSizeMax"));
			System.out.println("JavaVendor                 :"
					+ connection.getAttribute(jvmRT, "JavaVendor"));
			System.out.println("JavaVersion                :"
					+ connection.getAttribute(jvmRT, "JavaVersion"));
			System.out.println("Uptime                     :"
					+ connection.getAttribute(jvmRT, "Uptime"));
			System.out.println("………………………………………………………………\n");
		}
	}

	// ——————————————
	public void getJmsRuntime() throws Exception {
		ObjectName[] serverRT = getServerRuntimes();
		int length = (int) serverRT.length;
		for (int i = 0; i < length; i++) {
			String name = (String) connection.getAttribute(serverRT[i], "Name");
			System.out.println("\n\t ——– name = " + name);
			ObjectName jmsRuntime = (ObjectName) connection.getAttribute(
					serverRT[i], "JMSRuntime");
			ObjectName jmsServers[] = (ObjectName[]) connection.getAttribute(
					jmsRuntime, "JMSServers");
			System.out.println("\n\t ——–Total Targeted JmsServers = "
					+ jmsServers.length);
			for (int y = 0; y < jmsServers.length; y++) {
				System.out.println("\n\t JMSServer Name: "
						+ connection.getAttribute(jmsServers[y], "Name"));
				ObjectName[] destRT = (ObjectName[]) connection.getAttribute(
						jmsServers[y], "Destinations");
				int appLength = (int) destRT.length;
				for (int x = 0; x < appLength; x++) {
					System.out.println("\n……………….<"
							+ name
							+ ": JMSServerRuntime>"
							+ (String) connection.getAttribute(destRT[x],
									"Name") + "……….");
					System.out.println("MessagesCurrentCount     .  : "
							+ connection.getAttribute(destRT[x],
									"MessagesCurrentCount"));
					System.out.println("MessagesPendingCount       : "
							+ connection.getAttribute(destRT[x],
									"MessagesPendingCount"));
					System.out.println("MessagesHighCount           : "
							+ connection.getAttribute(destRT[x],
									"MessagesHighCount"));
					System.out.println("MessagesReceivedCount       : "
							+ connection.getAttribute(destRT[x],
									"MessagesReceivedCount"));
					System.out.println("…………………………………………………………………..\n");
				}
			}
		}
	}

	// ——————————————
	public void getJdbcRuntime() throws Exception {
		ObjectName[] serverRT = getServerRuntimes();
		int length = (int) serverRT.length;
		for (int i = 0; i < length; i++) {
			String name = (String) connection.getAttribute(serverRT[i], "Name");
			ObjectName[] appRT = (ObjectName[]) connection.getAttribute(
					new ObjectName("com.bea:Name=" + name + ",ServerRuntime="
							+ name + ",Location=" + name
							+ ",Type=JDBCServiceRuntime"),
					"JDBCDataSourceRuntimeMBeans");
			int appLength = (int) appRT.length;
			for (int x = 0; x < appLength; x++) {
				System.out.println("\n……..           .<" + name
						+ "   : JDBCDataSourceRuntimeMBeans>"
						+ (String) connection.getAttribute(appRT[x], "Name")
						+ "……….");
				System.out.println("ActiveConnectionsCurrentCount	: "
						+ connection.getAttribute(appRT[x],
								"ActiveConnectionsCurrentCount"));
				System.out.println("ActiveConnectionsAverageCount	: "
						+ connection.getAttribute(appRT[x],
								"ActiveConnectionsAverageCount"));
				System.out.println("ActiveConnectionsAverageCount	: "
						+ connection.getAttribute(appRT[x],
								"ActiveConnectionsAverageCount"));
				System.out.println("ConnectionsTotalCount        	: "
						+ connection.getAttribute(appRT[x],
								"ConnectionsTotalCount"));
				System.out.println("CurrCapacity                    : "
						+ connection.getAttribute(appRT[x], "CurrCapacity"));
				System.out.println("CurrCapacityHighCount        	: "
						+ connection.getAttribute(appRT[x],
								"CurrCapacityHighCount"));
				System.out.println("HighestNumAvailable          	: "
						+ connection.getAttribute(appRT[x],
								"HighestNumAvailable"));
				System.out.println("HighestNumAvailable          	: "
						+ connection.getAttribute(appRT[x],
								"HighestNumAvailable"));
				System.out.println("LeakedConnectionCount        	: "
						+ connection.getAttribute(appRT[x],
								"LeakedConnectionCount"));
				System.out.println("WaitSecondsHighCount         : "
						+ connection.getAttribute(appRT[x],
								"WaitSecondsHighCount"));
				System.out.println("WaitingForConnectionCurrentCount: "
						+ connection.getAttribute(appRT[x],
								"WaitingForConnectionCurrentCount"));
				System.out.println("WaitingForConnectionFailureTotal: "
						+ connection.getAttribute(appRT[x],
								"WaitingForConnectionFailureTotal"));
				System.out.println("WaitingForConnectionTotal    	: "
						+ connection.getAttribute(appRT[x],
								"WaitingForConnectionTotal"));
				System.out.println("WaitingForConnectionHighCount	: "
						+ connection.getAttribute(appRT[x],
								"WaitingForConnectionHighCount"));
				System.out.println("…………………………………………………………………..\n");
			}
		}
	}

	public void getServletData() throws Exception {
		ObjectName[] serverRT = getServerRuntimes();
		int length = (int) serverRT.length;
		for (int i = 0; i < length; i++) {
			ObjectName[] appRT = (ObjectName[]) connection.getAttribute(
					serverRT[i], "ApplicationRuntimes");
			int appLength = (int) appRT.length;
			for (int x = 0; x < appLength; x++) {
				System.out.println("Application name: "
						+ (String) connection.getAttribute(appRT[x], "Name"));
				ObjectName[] compRT = (ObjectName[]) connection.getAttribute(
						appRT[x], "ComponentRuntimes");
				int compLength = (int) compRT.length;
				for (int y = 0; y < compLength; y++) {
					System.out.println("  Component name: "
							+ (String) connection.getAttribute(compRT[y],
									"Name"));
					String componentType = (String) connection.getAttribute(
							compRT[y], "Type");
					System.out.println(componentType.toString());
					if (componentType.toString().equals(
							"WebAppComponentRuntime")) {
						ObjectName[] servletRTs = (ObjectName[]) connection
								.getAttribute(compRT[y], "Servlets");
						int servletLength = (int) servletRTs.length;
						for (int z = 0; z < servletLength; z++) {
							System.out.println("    Servlet name: "
									+ (String) connection.getAttribute(
											servletRTs[z], "Name"));
							System.out.println("       Servlet context path: "
									+ (String) connection.getAttribute(
											servletRTs[z], "ContextPath"));
							System.out
									.println("       Invocation Total Count : "
											+ (Object) connection.getAttribute(
													servletRTs[z],
													"InvocationTotalCount"));
						}
					}
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		
		/*if (args.length < 4) {
			System.out
					.println("<Usage>: java WebLogicMonitoring  adm-host  adm-port adm-username adm-password");
			System.exit(0);
		}
		String hostname = args[0];
		String portString = args[1];
		String username = args[2];
		String password = args[3];*/
		
		String hostname = "localhost";
		String portString = "7001";
		String username = "weblogic";
		String password = "weblogic";
		
		
		WebLogicMonitoring s = new WebLogicMonitoring();
		initConnection(hostname, portString, username, password);
		s.printNameAndState();
		s.threadPoolRuntime();
		s.getJvmRuntime();
		s.getJdbcRuntime();
		s.getJmsRuntime();
		s.getServletData();
		connector.close();
	}
}
