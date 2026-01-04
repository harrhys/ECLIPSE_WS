/*
 * Copyright 2001, 2002 Sun Microsystems, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 */

//package samples.security.auth.realm;
package samples.security.jdbcrealm;

import java.util.Properties;
import java.util.Vector;
import java.util.HashMap;

import com.sun.enterprise.security.acl.RoleMapper;
import com.sun.enterprise.security.auth.realm.BadRealmException;
import com.sun.enterprise.security.auth.realm.NoSuchUserException;
import com.sun.enterprise.security.auth.realm.NoSuchRealmException;
import com.sun.enterprise.security.auth.realm.InvalidOperationException;

import com.iplanet.ias.security.RealmConfig;
import com.iplanet.ias.security.auth.realm.IASRealm;

/**
 * JDBCRealm for supporting RDBMS authentication.
 */
final public class JDBCRealm extends IASRealm
{
    public static final String AUTH_TYPE = "JDBCRealm";

    Properties _realmProperties = null;
    Vector     _groups = new Vector();

    HashMap _groupCache = new HashMap();
    String  _anyoneRole;
    Vector  _emptyVector;

    public JDBCRealm() {
    }

    /**
     * Initialize a realm with some properties.  This can be used
     * when instantiating realms from their descriptions.  This
     * method may only be called a single time.  
     *
     * @param props Initialization parameters used by this realm.
     * @exception BadRealmException If the configuration parameters
     *     identify a corrupt realm.
     * @exception NoSuchRealmException If the configuration parameters
     *     specify a realm which doesn't exist.
     *
     */
    protected void init(Properties props)
        throws BadRealmException, NoSuchRealmException
    {
        _realmProperties = props;
        String jaasCtx = props.getProperty(IASRealm.JAAS_CONTEXT_PARAM);
        this.setProperty(IASRealm.JAAS_CONTEXT_PARAM, jaasCtx);
        _emptyVector = new Vector();
        _anyoneRole = RoleMapper.getDefaultRole().getName();
    }


    /**
     * Returns a short description of the kind of authentication which is
     * supported by this realm.
     *
     * @return Description of the kind of authentication that is directly
     *     supported by this realm.
     */    
    public String getAuthType()
    {
        return AUTH_TYPE;
    }

    /**
     * Returns the property string for this realm defined in server.xml
     */    
    public String getRealmProperty(String name) {
       return _realmProperties.getProperty(name);
    }


    /**
     * Returns names of all the groups in this particular realm.
     *
     * @return enumeration of group names (strings)
     * @exception BadRealmException if realm data structures are bad
     */
    public java.util.Enumeration getGroupNames()
        throws BadRealmException
    {
        return _groups.elements();
    }

    
    /**
     * Returns the name of all the groups that this user belongs to.
     * @param username Name of the user in this realm whose group listing
     *     is needed.
     * @return Enumeration of group names (strings).
     * @exception InvalidOperationException thrown if the realm does not
     *     support this operation - e.g. Certificate realm does not support
     *     this operation.
     */
    public java.util.Enumeration getGroupNames (String username)
        throws InvalidOperationException, NoSuchUserException
    {
        Vector v = (Vector)_groupCache.get(username);
        if (v == null) {
            return _emptyVector.elements();
        } else {
            return v.elements();
        }
    }


    /**
     * Set group membership info for a user.
     */
    public void setGroupNames(String username, String[] groups)
    {
        Vector v = new Vector(groups.length + 1);
        for (int i=0; i<groups.length; i++) {
            v.add(groups[i]);
        }
        v.add(_anyoneRole);
        _groupCache.put(username, v);
    }



}
