package com.test.weblogic;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.management.Attribute;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.security.service.JMXResource;

public class ChangeDataSourceProperties
  {
    private String hostName;
    private String hostPortNumber;
    private String consoleUserName;
    private String consolePassword;
    private ObjectName domainMBean;
    private ObjectName configMgrMBean;
    private static MBeanServerConnection mBeanServerConnection;
    private static JMXConnector connector;

    public ChangeDataSourceProperties(String hostName, String hostPortNumber, String consoleUserName, String consolePassword) throws NamingException, IOException
    {
        this.hostName = hostName;
        this.hostPortNumber = hostPortNumber;
        this.consoleUserName = consoleUserName;
        this.consolePassword = consolePassword;
    }

    public void closeConnections() throws IOException {
        connector.close();
    }

    public void rotateAccount(String dataSourceName, String jdbcNewUserName, String jdbcNewPassword, String jdbcUrl) {

        try {
              System.out.println("Changing Account Details for DataSource " + dataSourceName);
              ObjectName jdbcSystemResourceMBean = getJDBCSystemResourceMBean(domainMBean, dataSourceName);
              System.out.println("\n\t jdbcSystemResourceMBean = "+jdbcSystemResourceMBean);
              if (jdbcSystemResourceMBean == null) {
                  throw new Exception("DataSource " + dataSourceName + " not found.");
              }

              ObjectName jdbcDriverParamsBean = getJDBCDriverParamsBean(jdbcSystemResourceMBean);
              System.out.println("\n\t jdbcDriverParamsBean = "+jdbcDriverParamsBean);
              changeUserId(jdbcDriverParamsBean, jdbcNewUserName);

              changePassword(jdbcDriverParamsBean, jdbcNewPassword);
              ObjectName[] targetObjectName = (ObjectName[]) getObjectName(jdbcSystemResourceMBean, "Targets");

              System.out.println("Total Targets are "+targetObjectName.length);
              for(int i=0; i<targetObjectName.length; i++)
                {
                    ObjectName targetName = targetObjectName[i];
                    mBeanServerConnection.invoke(jdbcSystemResourceMBean, "removeTarget", new Object[]{targetName}, new String[]{"javax.management.ObjectName"});
                    mBeanServerConnection.invoke(jdbcSystemResourceMBean, "addTarget", new Object[]{targetName}, new String[]{"javax.management.ObjectName"});
                }
            } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAllDataSources()
    {
        try {
               mBeanServerConnection = getDomainRuntimeServiceMBean();
               getDomainConfigurationMBean();
               ObjectName[] jdbcSystemResourceMBeans = getJDBCSystemResourceMBean(configMgrMBean);

               for (int i = 0; i < jdbcSystemResourceMBeans.length; i++)
                  {
                     ObjectName jdbcSystemResourceMBean = jdbcSystemResourceMBeans[i];
                     ObjectName jdbcDriverParamsBean = getJDBCDriverParamsBean(jdbcSystemResourceMBean);
                     ObjectName jdbcPropertiesBean = getJDBCSystemProperties(jdbcDriverParamsBean);

                     ObjectName jdbcUserPropertyBean = getJDBCUserPropertyBean(jdbcPropertiesBean);
                     String presentUserName = (String) getObjectName(jdbcUserPropertyBean, "Value");
                     String password = (String) getObjectName(jdbcDriverParamsBean, "Password");
                     System.out.print(" The User is "+presentUserName+" \t Password: "+password+"\n\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ObjectName getConfigManagerMBean() throws MalformedObjectNameException, NullPointerException, AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException, IOException
    {
        String configObjectNameStr = "com.bea:Name=EditService,Type=weblogic.management.mbeanservers.edit.EditServiceMBean";
        ObjectName configObjectName = new ObjectName(configObjectNameStr);
        return (ObjectName) getObjectName(configObjectName, "ConfigurationManager");
    }

    private MBeanServerConnection getMBeanServerConnection() throws NamingException, IOException
    {
        String mserver = "weblogic.management.mbeanservers.edit";
        return getMBeanServerConnection(mserver);
    }

    private MBeanServerConnection getDomainRuntimeServiceMBean() throws NamingException, IOException
    {
        String mserver = "weblogic.management.mbeanservers.domainruntime";
        MBeanServerConnection con=getMBeanServerConnection(mserver);;
		return con;
    }

    private MBeanServerConnection getMBeanServerConnection(String mserverName) throws IOException
    {
        String jndiroot = "/jndi/";
        JMXServiceURL jmxServiceURL = new JMXServiceURL("t3", hostName, Integer.valueOf(hostPortNumber), jndiroot + mserverName);
        Map credentialsMap = new HashMap();
        credentialsMap.put(Context.SECURITY_PRINCIPAL, consoleUserName);
        credentialsMap.put(Context.SECURITY_CREDENTIALS, consolePassword);
        credentialsMap.put(JMXConnectorFactory.PROTOCOL_PROVIDER_PACKAGES, "weblogic.management.remote");
        //credentialsMap.put("jmx.remote.x.request.waiting.timeout", new Long(100000));
        connector = JMXConnectorFactory.connect(jmxServiceURL, credentialsMap);
        return connector.getMBeanServerConnection();
    }

    private void getMBeanInfo(ObjectName objectName)
    {
        try
        {
            MBeanInfo configMBean = mBeanServerConnection.getMBeanInfo(objectName);
            MBeanAttributeInfo[] info = configMBean.getAttributes();
            for (int i = 0; i < info.length; i++)
            {
                MBeanAttributeInfo attInfo = info[i];
                //System.out.println("attInfo: " + attInfo.getName() + " \t " + attInfo.getDescription());
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void changePassword(ObjectName jdbcDriverParamsBean, String jdbcNewPassword) throws AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException, IOException, InvalidAttributeValueException
    {
        System.out.println("\n\n\tChanging Jdbc Password To...... New Password: "+jdbcNewPassword);
        Attribute passwordAttribute = new Attribute("Password", jdbcNewPassword);
		//System.out.println("\n\n\tPassword: "+passwordAttribute);
		//System.out.println("\n\n\tPassword: "+passwordAttribute.getName());
		//System.out.println("\n\n\tPassword: "+passwordAttribute.getValue());
		mBeanServerConnection.setAttribute(jdbcDriverParamsBean, passwordAttribute);
    }

    private void changeJDBCUrl(ObjectName jdbcDriverParamsBean, String jdbcUrl) throws AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException, IOException, InvalidAttributeValueException
    {
         String presentJdbcUrl = (String) getObjectName(jdbcDriverParamsBean, "Url");;
         System.out.println("\n\tChanging JDBC URL Present URL : "+presentJdbcUrl+" New URL: "+jdbcUrl);
         Attribute jdbcUrlAttribute = new Attribute("Url", jdbcUrl);
         mBeanServerConnection.setAttribute(jdbcDriverParamsBean, jdbcUrlAttribute);
    }

    private void changeUserId(ObjectName jdbcDriverParamsBean, String jdbcNewUserName) throws AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException, IOException, InvalidAttributeValueException, MalformedObjectNameException, NullPointerException, InstanceAlreadyExistsException, NotCompliantMBeanException
    {
        ObjectName jdbcPropertiesBean = getJDBCSystemProperties(jdbcDriverParamsBean);
        ObjectName jdbcUserPropertyBean = getJDBCUserPropertyBean(jdbcPropertiesBean);
        String jdbcUserPropertyBeanValue = (String) getObjectName(jdbcUserPropertyBean, "Value");
        System.out.println("\n\tChanging User Name Present UserName: " + jdbcUserPropertyBeanValue + " New UserName : " + jdbcNewUserName);
        Attribute jdbcUserAttribute = new Attribute("Value", jdbcNewUserName);
        mBeanServerConnection.setAttribute(jdbcUserPropertyBean, jdbcUserAttribute);
    }

    private Object getObjectName(ObjectName objectName, String attributeName) throws AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException, IOException
    {
        return mBeanServerConnection.getAttribute(objectName, attributeName);
    }

    private ObjectName getJDBCSystemResourceMBean(ObjectName configMgrMBean, String dataSourceName) throws AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException, IOException
    {
        ObjectName jdbcSystemResourceMBean = null;
        ObjectName[] jdbcSystemResourceMBeans = getJDBCSystemResourceMBean(configMgrMBean);
        for (int i = 0; i < jdbcSystemResourceMBeans.length; i++)
           {
              ObjectName presentJDBCSystemResourceMBean = jdbcSystemResourceMBeans[i];
              String dsSourceName = (String) getObjectName(presentJDBCSystemResourceMBean, "Name");
              if (dataSourceName.equalsIgnoreCase(dsSourceName))
                {
                   jdbcSystemResourceMBean = presentJDBCSystemResourceMBean;
                   break;
                }
           }
        return jdbcSystemResourceMBean;
    }

    private ObjectName[] getJDBCSystemResourceMBean(ObjectName configMgrMBean) throws AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException, IOException
    {
        return (ObjectName[]) getObjectName(configMgrMBean, "JDBCSystemResources");
    }

    private ObjectName getJDBCDriverParamsBean(ObjectName jdbcSystemResourceMBean) throws AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException, IOException, IntrospectionException
    {
        ObjectName jdbcDataSourceBean = (ObjectName) getObjectName(jdbcSystemResourceMBean, "JDBCResource");
        return (ObjectName) getObjectName(jdbcDataSourceBean, "JDBCDriverParams");
    }

    private ObjectName getJDBCSystemProperties(ObjectName objectName) throws AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException, IOException
    {
        return (ObjectName) getObjectName(objectName, "Properties");
    }

    private ObjectName getJDBCUserPropertyBean(ObjectName jdbcPropertiesBean) throws AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException, IOException, MalformedObjectNameException, NullPointerException, InstanceAlreadyExistsException, NotCompliantMBeanException
    {
        ObjectName jdbcPropertyBean = null;
        ObjectName[] jdbcPropertyBeans = (ObjectName[]) getObjectName(jdbcPropertiesBean, "Properties");
        for (int j = 0; j < jdbcPropertyBeans.length; j++)
          {
            jdbcPropertyBean = jdbcPropertyBeans[j];
            String jdbcPropertyName = (String) getObjectName(jdbcPropertyBean, "Name");
            if ("user".equalsIgnoreCase(jdbcPropertyName))
              {
                break;
              }
          }

        if(jdbcPropertyBean == null)
          {
            jdbcPropertyBean = (ObjectName) mBeanServerConnection.invoke(jdbcPropertiesBean, "createProperty", new Object[]{"user"}, new String[]{"java.lang.String"});
          }
        return jdbcPropertyBean;
    }

    public void startEditSession() throws MalformedObjectNameException, NullPointerException, AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException, IOException, NamingException
    {
        mBeanServerConnection = getMBeanServerConnection();
        configMgrMBean = getConfigManagerMBean();
        domainMBean = (ObjectName) mBeanServerConnection.invoke(configMgrMBean, "startEdit", new Object[]{new Integer(60000), new Integer(120000)}, new String[]{"java.lang.Integer", "java.lang.Integer"});
     }

    private void getDomainConfigurationMBean() throws MalformedObjectNameException, NullPointerException, AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException, IOException
    {
        String configObjectNameStr = "com.bea:Name=DomainRuntimeService,Type=weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean";
        ObjectName configObjectName = new ObjectName(configObjectNameStr);
        configMgrMBean = (ObjectName) getObjectName(configObjectName, "DomainConfiguration");
    }

    public void activateSession() throws MalformedObjectNameException, NullPointerException, AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException, IOException
    {
        mBeanServerConnection.invoke(configMgrMBean, "save", null, null);
        ObjectName task = (ObjectName) mBeanServerConnection.invoke(configMgrMBean, "activate", new Object[]{new Long(120000)}, new String[]{"java.lang.Long"});

        //getMBeanInfo(task);

    }

    public void saveSession() throws MalformedObjectNameException, NullPointerException, AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException, IOException {

        mBeanServerConnection.invoke(configMgrMBean, "save", null, null);
    }

    public void listUnactivated() throws Exception
    {
        Object[] list = (Object[])mBeanServerConnection.getAttribute(configMgrMBean,"UnactivatedChanges");
        int length = (int) list.length;
        System.out.println("Unactivated changes: " + length);
        for (int i = 0; i < length; i++)
          {
            System.out.println("Unactivated changes: " + list[i].toString());
          }
    }

    public static void main(String ar[]) throws Exception
        {
				String hostName="localhost";
				String hostPortNumber="7001";
				String consoleUserName="weblogic";
				String consolePassword="weblogic";

 	            ChangeDataSourceProperties ref=new ChangeDataSourceProperties(hostName,hostPortNumber,consoleUserName,consolePassword);
				MBeanServerConnection conn=ref.getMBeanServerConnection("weblogic.management.mbeanservers.domainruntime");

                String newDataSourceUserName="scott";
                String newDataSourcePassword="tiger";
                String dataSourceName="TestDS";
                String dataSourceURL="jdbc:pointbase:server://localhost:9092/demo";

                ref.startEditSession();
				ref.rotateAccount(dataSourceName,newDataSourceUserName, newDataSourcePassword,dataSourceURL);
                System.out.println("\n\n\t Rotated Account...In AdminConsole Check the DataSource-->ConnectionPool Properties...");

				ref.saveSession();
				ref.activateSession();
	     }

}