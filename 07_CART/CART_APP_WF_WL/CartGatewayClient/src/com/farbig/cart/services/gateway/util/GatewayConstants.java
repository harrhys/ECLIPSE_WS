/*
 * Confidential, proprietary and unpublished source code of Obopay, Inc.
 * Copyright Obopay, Inc. and its licensors.  All rights reserved.
 */
package com.farbig.cart.services.gateway.util;

/**
 * Interface used to maintain the constants used in service gateway and related operations.
 */
public interface GatewayConstants
{

    /**
     * Message parameter - service name constant.
     */
    final String MESSAGE_PARAMETER_SERVICE_NAME = "SN";

    /**
     * Message parameter - source service name constant.
     */
    final String MESSAGE_PARAMETER_SOURCE_SERVICE_NAME = "SSN";

    /**
     * Message parameter - method name constant.
     */
    final String MESSAGE_PARAMETER_METHOD_NAME = "MN";

    /**
     * Message parameter - method object constant.
     */
    final String MESSAGE_PARAMETER_METHOD_OBJECT = "MO";

    /**
     * Message parameter - method return constant.
     */
    final String MESSAGE_PARAMETER_METHOD_RETURN = "RV";

    /**
     * Message parameter - method parameter type prefix constant.
     */
    final String MESSAGE_PARAMETER_METHOD_PARAMETER_TYPE_PREFIX = "PT_";

    /**
     * Message parameter - method parameter value prefix constant.
     */
    final String MESSAGE_PARAMETER_METHOD_PARAMETER_VALUE_PREFIX = "PV_";

    /**
     * Message parameter - parameter exception constant.
     */
    final String MESSAGE_PARAMETER_EXCEPTION = "PEX_";

    /**
     * Message parameter - params constant.
     */
    final String MESSAGE_PARAMETER_PARAMS = "mpp_";

    /**
     * Message parameter - port number constant.
     */
    final String MESSAGE_PARAMETER_PORT_NUMBER = "PORT";

    /**
     * Message parameter - version constant.
     */
    final String MESSAGE_PARAMETER_VERSION = "VER";

    /**
     * Unknown service name constant.
     */
    final String UNKNOWN_SERVICENAME = "UK_SN";

    /**
     * Unknown method name constant.
     */
    final String UNKNOWN_METHODNAME = "UK_MN";

	final String BEAN_TYPE = "BEAN_TYPE";

    /**
     * Unknown operation constant.
     */
    public static final String UNKNOWN_OPERATION = "UNKNOWN_OPERATION";

    /**
     * ServiceHelper configuration section name constant.
     */
    public static final String CONFIGURATION_SECTION_NAME_SERVICES =
            "com.ewp.core.service.ServiceHelper.Services";

    /**
     * Queue JNDI identifier constant.
     */
    public static final String TAG_QUEUE_JNDI = "queueJNDI";

    /**
     * Queue connection factory JNDI identifier constant.
     */
    public static final String TAG_CONNECTION_FACTORY_JNDI = "queueConnectionFactoryJNDI";

    /**
     * Async helper configuration section name constant.
     */
    public static final String SECTION_ASYNCHELPER = "com.ewp.core.asynchelper";

    /**
     * Default EJB types constant.
     */
    public static final String TAG_DEFAULT_EJBTYPE = "defaultEJBType";

    /**
     * Implementing class name identifier constant.
     */
    public static final String TAG_IMPLEMENTATION_CLASSNAME = "implementationClassName";

    /**
     * Async distribution queue type configuration section name constant.
     */
    public static final String SECTION_ASYNC_QUEUE_TYPES = "com.obopay.core.async.QueueTypes";

    /**
     * Provider url identifier constant.
     */
    public static final String TAG_PROVIDER_URL = "providerURL";

    /**
     * Global default queue type identifier constant.
     */
    public static final String TAG_GLOBAL_DEFAULT_QUEUETYPE = "default";

    /**
     * Default queue type identifier constant.
     */
    public static final String TAG_DEFAULT_QUEUETYPE = "defaultQueueType";

    /**
     * Method's EJB type identifier constant.
     */
    public static final String TAG_METHOD_TYPE_EJB_TYPE_VALUE = "EJBType";

    /**
     * Method's queue type identifier constant.
     */
    public static final String TAG_METHOD_TYPE_QUEUE_TYPE_VALUE = "QueueType";

    /**
     * Service helper configuration section name constant.
     */
    public static final String SECTION_SERVICES = "com.ewp.core.service.ServiceHelper.Services";

    /**
     * CMTRequired configuration name constant.
     */
    public static final String TAG_CMTREQUIRED_JNDI = "com.ewp.ejb.GatewayCMTRequiredRemoteHome";

    /**
     * BMT configuration name constant.
     */
    public static final String TAG_BMT_JNDI = "com.ewp.ejb.GatewayBMTRemoteHome";

    /**
     * EJB service proxy connection configuration section name constant.
     */
    public static final String CONNECTION_INFO_PER_EJB_TYPES =
            "com.ewp.core.client.ServiceProxy.Connection.ConfigBy.EJBTypes";

    /**
     * EJB types configuration section name constant.
     */
    public static final String SECTION_EJB_TYPES = "com.ewp.core.client.ServiceProxy.EJBTypes";

    public static final String JMS_QUEUE_HELPERS ="JMSQueueHelpers"; 
    
    public static final String DEFAULT_HELPER ="defaultHelper";
    
    /*
     * Constants required for ZDR changes
     */    
    public static final String EJB_CONNECTION_ATTRIBUTES_SEC = "com.ewp.core.client.ServiceProxy.Connection.Attributes";
    
    // EJB Work Rejection exception handling configurations
    public static final String WORK_REJ_SLEEP_INTERVAL = "ejb.home.lookup.workrejection.sleeptime";
    public static final String WORK_REJ_RETRY_ATTEMPTS = "ejb.home.lookup.workrejection.retryattempts";
        
    // EJB JNDI additional properties
    public static final String EJB_CONN_ATTR_RELAX_VERISON_LOOKUP = "weblogic.jndi.relaxVersionLookup";
    public static final String EJB_CONN_ATTR_ALLOW_GLOB_RES_LOOKUP = "weblogic.jndi.allowGlobalResourceLookup";
    public static final String EJB_CONN_ATTR_ALLOW_EXT_APP_LOOKUP = "weblogic.jndi.allowExternalAppLookup";    
    

    /**
     * Configuration section name for Asynchronous JMS Logger, used to maintain configurations
     * related to async logging.
     */
    public static final String ASYNC_LOGGER_CONFIG = "AsyncLoggerConfig";
}
