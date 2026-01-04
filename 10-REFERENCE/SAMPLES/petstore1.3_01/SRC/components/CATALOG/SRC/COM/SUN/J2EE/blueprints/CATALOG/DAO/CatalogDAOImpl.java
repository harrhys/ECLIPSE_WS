/*
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistribution in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in
 *   the documentation and/or other materials provided with the
 *   distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN
 * OR ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR
 * FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR
 * PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF
 * LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE SOFTWARE,
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that Software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of
 * any nuclear facility.
 */

package com.sun.j2ee.blueprints.catalog.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.*;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.naming.NamingException;

import com.sun.j2ee.blueprints.catalog.util.JNDINames;
import com.sun.j2ee.blueprints.catalog.model.Page;
import com.sun.j2ee.blueprints.catalog.model.Category;
import com.sun.j2ee.blueprints.catalog.model.Product;
import com.sun.j2ee.blueprints.catalog.model.Item;
import com.sun.j2ee.blueprints.catalog.util.DatabaseNames;
import com.sun.j2ee.blueprints.catalog.exceptions.CatalogDAOSysException;

import com.sun.j2ee.blueprints.util.tracer.Debug;

/**
 * This class implements CatalogDAO for oracle, sybase and cloudscape DBs.
 * This class encapsulates all the SQL calls made by Catalog EJB.
 * This layer maps the relational data stored in the database to
 * the objects needed by Catalog EJB.
*/
public class CatalogDAOImpl implements CatalogDAO {

    // Helper methods

    protected static DataSource getDataSource()
        throws CatalogDAOSysException {
        try {
            InitialContext ic = new InitialContext();
            return (DataSource) ic.lookup(JNDINames.CATALOG_DATASOURCE);
        }
        catch (NamingException ne) {
            throw new CatalogDAOSysException("NamingException while looking "
                                             + "up DB context : "
                                             + ne.getMessage());
        }
    }

    // Business methods

    public Category getCategory(String categoryID, Locale l)
        throws CatalogDAOSysException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Category ret = null;

        try {
            c = getDataSource().getConnection();

            /* Commented out for AS7SE : This SQL won't work on Oracle
            ps = c.prepareStatement("select name, descn "
                                    + "from (category a join "
                                    + "category_details b on "
                                    + "a.catid=b.catid) "
                                    + "where locale = ? "
                                    + "and a.catid = ?",
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
            */
           //The new SQL for Oracle 
            ps = c.prepareStatement("select a.catid , b.name , b.descn , b.locale from category a , category_details b where a.catid = b.catid "
                                    + "where b.locale = ? "
                                    + "and  a.catid = ?",
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
            
            ps.setString(1, l.toString());
            ps.setString(2, categoryID);
            rs = ps.executeQuery();
            if (rs.first()) {
              // XXX
              /*
                ret = new Category(rs.getString(1).trim(),
                                   rs.getString(2),
                                   rs.getString(3));
                                   */
                ret = new Category(categoryID,
                                   rs.getString(1),
                                   rs.getString(2));
            }
            rs.close();
            ps.close();

            c.close();
            return ret;
        }
        catch (SQLException se) {
            throw new CatalogDAOSysException("SQLException-01: "
                                             + se.getMessage());
        }
     }

    public Page getCategories(int start, int count, Locale l)
        throws CatalogDAOSysException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Page ret = null;

        try {
            c = getDataSource().getConnection();
            // XXX
            /*
            // Count.
            ps = c.prepareStatement("select COUNT(*) "
                                    + "from (category a join "
                                    + "category_details b on "
                                    + "a.catid=b.catid) "
                                    + "where locale = ?",
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, l.toString());
            rs = ps.executeQuery();
            rs.first();
            int total = rs.getInt(1);
            rs.close();
            ps.close();
            */

            // Select.
            //commented out for AS7SE This sql is not working in Oracle:
            /*
            ps = c.prepareStatement("SELECT a.catid, name, descn "
                                    + "from (category a join "
                                    + "category_details b on "
                                    + "a.catid=b.catid) "
                                    + "where locale = ? "
                                    + "order by name",
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
                                    */
            
            //The new SQL for Oracle
            ps = c.prepareStatement("select a.catid,b.name,b.descn,b.locale from category a , category_details b where a.catid=b.catid and b.locale=?",
                                    //+"b.locale='en_US'",
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
             
            ps.setString(1, l.toString());
            rs = ps.executeQuery();//
            // XXX
            /*
            if (start >= 0 && start < total) {
                List items = new ArrayList();
                rs.absolute(start+1);
                do {
                    items.add(new Category(rs.getString(1).trim(),
                                           rs.getString(2),
                                           rs.getString(3)));
                } while (rs.next() && (--count > 0));
                ret = new Page(items, start, total);
            }
            */
            if (start >= 0 && rs.absolute(start+1)) {
                boolean hasNext = false;
                List items = new ArrayList();
                do {
                    items.add(new Category(rs.getString(1).trim(),
                                           rs.getString(2),
                                           rs.getString(3)));
                } while ((hasNext = rs.next()) && (--count > 0));
                ret = new Page(items, start, hasNext);
            }
            else {
                ret = Page.EMPTY_PAGE;
            }

            rs.close();
            ps.close();

            c.close();
            return ret;
        }
        catch (SQLException se) {
          se.printStackTrace(System.err);
            throw new CatalogDAOSysException("SQLException -02: "
                                             + se.getMessage());
        }
    }

    public Product getProduct(String productID, Locale l)
        throws CatalogDAOSysException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Product ret = null;

        try {
            c = getDataSource().getConnection();

            // XXX
            /*
            ps = c.prepareStatement("select a.productid, name, descn "
                                    + "from (product a join "
                                    + "product_details b on "
                                    + "a.productid=b.productid) "
                                    + "where locale = ? "
                                    + "and a.productid = ? ",
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
                                    */
            /* This SQL wont't work on Oracle; Commented out for AS7SE
            ps = c.prepareStatement("select name, descn "
                                    + "from (product a join "
                                    + "product_details b on "
                                    + "a.productid=b.productid) "
                                    + "where locale = ? "
                                    + "and a.productid = ? ",
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
            */
            //The new sql for Oracle
            
            ps = c.prepareStatement("select a.productid , b.name , b.descn , b.locale from product a , product_details b where a.productid = b.productid "
                                    + "where b.locale = ? "
                                    + "and a.productid = ? ",
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
            
            ps.setString(1, l.toString());
            ps.setString(2, productID);
            rs = ps.executeQuery();
            if (rs.first()) {
              // XXX
              /*
                ret = new Product(rs.getString(1).trim(),
                                  rs.getString(2),
                                  rs.getString(3));
                                  */
                ret = new Product(productID,
                                  rs.getString(1),
                                  rs.getString(2));
            }
            rs.close();
            ps.close();

            c.close();
            return ret;
        }
        catch (SQLException se) {
            throw new CatalogDAOSysException("SQLException -03: "
                                             + se.getMessage());
        }
    }

    public Page getProducts(String categoryID, int start,
                            int count, Locale l)
        throws CatalogDAOSysException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Page ret = null;

        try {
            c = getDataSource().getConnection();

            // XXX
            /*
            // Count.
            ps = c.prepareStatement("select COUNT(*) "
                                    + "from (product a join "
                                    + "product_details b on "
                                    + "a.productid=b.productid) "
                                    + "where locale = ? "
                                    + "and a.catid = ? ",
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, l.toString());
            ps.setString(2, categoryID);
            rs = ps.executeQuery();
            rs.first();
            int total = rs.getInt(1);
            rs.close();
            ps.close();
            */

            // Select.
            /*This SQL won't work on Oracle; Commented ou for AS7SE
            ps = c.prepareStatement("select a.productid, name, descn "
                                    + "from (product a join "
                                    + "product_details b on "
                                    + "a.productid=b.productid) "
                                    + "where locale = ? "
                                    + "and a.catid = ? "
                                    + "order by name",
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
            */
            //The new sql for Oracle; AS7SE
            ps=c.prepareStatement("select a.productid , a.catid , b.name , b.descn , b.locale from product a , product_details b where a.productid = b.productid and "
                                   +"locale = ? and catid = ? order by name ", 
                                  ResultSet.TYPE_SCROLL_INSENSITIVE,
                                  ResultSet.CONCUR_READ_ONLY
                                  );
            ps.setString(1, l.toString());
            ps.setString(2, categoryID);
            rs = ps.executeQuery();
            // XXX
            /*
            if (start >= 0 && start < total) {
                List items = new ArrayList();
                rs.absolute(start+1);
                do {
                    items.add(new Product(rs.getString(1).trim(),
                                          rs.getString(2).trim(),
                                          rs.getString(3).trim()));
                } while (rs.next() && (--count > 0));
                ret = new Page(items, start, total);
            }
            */
            if (start >= 0 && rs.absolute(start+1)) {
                boolean hasNext = false;
                List items = new ArrayList();
                do {
                    items.add(new Product(rs.getString(1).trim(),
                                          rs.getString(2).trim(),
                                          rs.getString(3).trim()));
                } while ((hasNext = rs.next()) && (--count > 0));
                ret = new Page(items, start, hasNext);
            }
            else {
                ret = Page.EMPTY_PAGE;
            }

            rs.close();
            ps.close();

            c.close();
            return ret;
        }
        catch (SQLException se) {
            throw new CatalogDAOSysException("SQLException -04: "
                                             + se.getMessage());
        }
    }

    public Item getItem(String itemID, Locale l)
        throws CatalogDAOSysException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Item ret = null;

        try {
            c = getDataSource().getConnection();

            // XXX
            /*
            ps = c.prepareStatement("select catid, a.productid, name, "
                                    + "a.itemid, b.image, b.descn, attr1, "
                                    + "attr2, attr3, attr4, attr5, "
                                    + "listprice, unitcost "
                                    + "from (((item a join item_details b "
                                    + "on a.itemid=b.itemid) join "
                                    + "product_details c on "
                                    + "a.productid=c.productid) join "
                                    + "product d on "
                                    + "d.productid=c.productid and b.locale = c.locale) "
                                    + "where b.locale = ? and "
                                    + "a.itemid = ?",
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
                                    */
            //The new SQL for Oracle
                                    ps = c.prepareStatement("select catid, a.productid , c.name , "
                                    + "b.image , b.descn , b.attr1 , "
                                    + "b.attr2 , b.attr3, b.attr4 , b.attr5 , "
                                    + "b.listprice, b.unitcost "
                                    + "from item a , item_details b , "
                                    + "product_details c , product d "
                                    + "where b.locale = ? and "
                                    + "a.itemid = ? and "
                                    + "a.itemid = b.itemid and "
                                    + "a.productid = c.productid and "
                                    + "d.productid = c.productid and b.locale = c.locale ",
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
                        
            ps.setString(1, l.toString());
            ps.setString(2, itemID);
            rs = ps.executeQuery();
            if (rs.first()) {
                int i = 1;
                // XXX
                /*
                ret = new Item(rs.getString(i++).trim(),
                               rs.getString(i++).trim(),
                               rs.getString(i++),
                               rs.getString(i++).trim(),
                               rs.getString(i++).trim(),
                               rs.getString(i++),
                               rs.getString(i++),
                               rs.getString(i++),
                               rs.getString(i++),
                               rs.getString(i++),
                               rs.getString(i++),
                               rs.getDouble(i++),
                               rs.getDouble(i++));
                               */
                ret = new Item(rs.getString(i++).trim(),
                               rs.getString(i++).trim(),
                               rs.getString(i++),
                               itemID,
                               rs.getString(i++).trim(),
                               rs.getString(i++),
                               rs.getString(i++),
                               rs.getString(i++),
                               rs.getString(i++),
                               rs.getString(i++),
                               rs.getString(i++),
                               rs.getDouble(i++),
                               rs.getDouble(i++));
            }
            rs.close();
            ps.close();

            c.close();
            return ret;
        }
        catch (SQLException se) {
            throw new CatalogDAOSysException("SQLException -05: "
                                             + se.getMessage());
        }
    }

    public Page getItems(String productID, int start, int count, Locale l)
        throws CatalogDAOSysException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Page ret = null;

        try {
            c = getDataSource().getConnection();

            // XXX
            /*
            // Count.
            ps = c.prepareStatement("select COUNT(*) "
                                    + "from (((item a join item_details b "
                                    + "on a.itemid=b.itemid) join "
                                    + "product_details c on "
                                    + "a.productid=c.productid) join "
                                    + "product d on "
                                    + "d.productid=c.productid and "
                                    + "b.locale = c.locale) "
                                    + "where b.locale = ? and "
                                    + "a.productid = ?",
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, l.toString());
            ps.setString(2, productID);

            rs = ps.executeQuery();
            rs.first();
            int total = rs.getInt(1);
            rs.close();
            ps.close();
            */

            // Select.
            // XXX
            /*
            ps = c.prepareStatement("select catid, a.productid, name, "
                                    + "a.itemid, b.image, b.descn, attr1, "
                                    + "attr2, attr3, attr4, attr5, "
                                    + "listprice, unitcost "
                                    + "from (((item a join item_details b "
                                    + "on a.itemid=b.itemid) join "
                                    + "product_details c on "
                                    + "a.productid=c.productid) join "
                                    + "product d on "
                                    + "d.productid=c.productid and "
                                    + "b.locale = c.locale) "
                                    + "where b.locale = ? and "
                                    + "a.productid = ?",
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
                                    */
                                    /* commented out for AS7SE
            ps = c.prepareStatement("select catid, name, "
                                    + "a.itemid, b.image, b.descn, attr1, "
                                    + "attr2, attr3, attr4, attr5, "
                                    + "listprice, unitcost "
                                    + "from (((item a join item_details b "
                                    + "on a.itemid=b.itemid) join "
                                    + "product_details c on "
                                    + "a.productid=c.productid) join "
                                    + "product d on "
                                    + "d.productid=c.productid and "
                                    + "b.locale = c.locale) "
                                    + "where b.locale = ? and "
                                    + "a.productid = ?",
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
                                    */
            //The new SQL for Oracle
            ps = c.prepareStatement("select catid, name, "
                                    + "a.itemid, b.image, b.descn, attr1, "
                                    + "attr2, attr3, attr4, attr5, "
                                    + "listprice, unitcost "
                                    + "from item a, item_details b, "
                                    + "product_details c, product d "
                                    + "where b.locale = ? and "
                                    + "a.productid = ? and "
                                    + "a.itemid = b.itemid and "
                                    + "a.productid = c.productid and "
                                    + "d.productid = c.productid and b.locale = c.locale ",
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
                                    
if (productID == null)
   productID = "FI-SW-01";
            ps.setString(1, l.toString());
            ps.setString(2, productID);

            rs = ps.executeQuery();
            
            // XXX            
            /*
            if (start >= 0 && start < total) {
                List items = new ArrayList();
                rs.absolute(start+1);
                do {
                    int i = 1;
                    items.add(new Item(rs.getString(i++).trim(),
                                       rs.getString(i++).trim(),
                                       rs.getString(i++),
                                       rs.getString(i++).trim(),
                                       rs.getString(i++).trim(),
                                       rs.getString(i++),
                                       rs.getString(i++),
                                       rs.getString(i++),
                                       rs.getString(i++),
                                       rs.getString(i++),
                                       rs.getString(i++),
                                       rs.getDouble(i++),
                                       rs.getDouble(i++)));
                } while (rs.next() && (--count > 0));
                ret = new Page(items, start, total);
            }
            */
            if (start >= 0 && rs.absolute(start+1)) {
                boolean hasNext = false;
                List items = new ArrayList();
                do {
                    int i = 1;
                    items.add(new Item(productID,
                                       rs.getString(i++).trim(),
                                       rs.getString(i++),
                                       rs.getString(i++).trim(),
                                       rs.getString(i++).trim(),
                                       rs.getString(i++),
                                       rs.getString(i++),
                                       rs.getString(i++),
                                       rs.getString(i++),
                                       rs.getString(i++),
                                       rs.getString(i++),
                                       rs.getDouble(i++),
                                       rs.getDouble(i++)));
                } while ((hasNext = rs.next()) && (--count > 0));
                ret = new Page(items, start, hasNext);
            }
            else {
                ret = Page.EMPTY_PAGE;
            }

            rs.close();
            ps.close();

            c.close();
            return ret;
        }
        catch (SQLException se) {
            throw new CatalogDAOSysException("SQLException -06: "
                                             + se.getMessage());
        }
    }

    public Page searchItems(String searchQuery, int start,
                            int count, Locale l)
        throws CatalogDAOSysException {
        Collection keywords = new HashSet();
        StringTokenizer st = new StringTokenizer(searchQuery);
        while (st.hasMoreTokens()) {
            keywords.add(st.nextToken());
        }

        if (keywords.isEmpty()) {
            return Page.EMPTY_PAGE;
        }

        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Page ret = null;

        try {
            c = getDataSource().getConnection();

            Iterator it;
            int i;
            StringBuffer sb = new StringBuffer();

            // XXX
            /*
            sb.append("( select e.productid from ");
            sb.append("(product e join product_details f on ");
            sb.append("e.productid=f.productid) ");

            int keywordsSize = keywords.size();
            if (keywordsSize > 0) {
                sb.append("where ( ( lower(f.name) like ? ");
                for (i = 1; i != keywordsSize; i++) {
                    sb.append("OR lower(f.name) like ? ");
                }
                sb.append(") OR ( lower(e.catid) like ? ");
                for (i = 1; i != keywordsSize; i++) {
                    sb.append("OR lower(e.catid) like ? ");
                }
                sb.append(") )");
            }

            sb.append(")");
            */

            // XXX
            /*
            // Count.
            ps = c.prepareStatement("select COUNT(*) "
                                    + "from (((item a join item_details b "
                                    + "on a.itemid=b.itemid) join "
                                    + "product_details c on "
                                    + "a.productid=c.productid) join "
                                    + "product d on "
                                    + "d.productid=c.productid and "
                                    + "b.locale = c.locale) "
                                    + "where b.locale = ? "
                                    + "and a.productid in " + sb.toString(),
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, l.toString());

            // The two loops are necessary because of the way the
            // query was constructed.
            i = 2;
            for (it = keywords.iterator(); it.hasNext(); i++) {
                String keyword = ((String) it.next()).toLowerCase();
                ps.setString(i, "%" + keyword + "%");
            }
            for (it = keywords.iterator(); it.hasNext(); i++) {
                String keyword = ((String) it.next()).toLowerCase();
                ps.setString(i, "%" + keyword + "%");
            }

            rs = ps.executeQuery();
            rs.first();
            int total = rs.getInt(1);
            rs.close();
            ps.close();
            */

            // Select.

            /*
            sb.append("select catid, a.productid, name, "
                      + "a.itemid, b.image, b.descn, attr1, "
                      + "attr2, attr3, attr4, attr5, "
                      + "listprice, unitcost "
                      + "from (((item a join item_details b "
                      + "on a.itemid=b.itemid) join "
                      + "product_details c on "
                      + "a.productid=c.productid) join "
                      + "product d on "
                      + "d.productid=c.productid and "
                      + "b.locale = c.locale) "
                      + "where b.locale = ? ");

            */
            //The new SQL for Oracle
            sb.append("select catid , a.productid , name , "
                      + "a.itemid , b.image , b.descn , attr1 , "
                      + "attr2 , attr3 , attr4 , attr5 , "
                      + "listprice , unitcost "
                      + "from item a , item_details b , "
                      + "product_details c , product d where "
                      + "a.itemid = b.itemid and "
                      + "a.productid = c.productid and "
                      + "d.productid = c.productid and b.locale = c.locale "
                      + " and b.locale = ? ");
                      
                      
                      
        
            int keywordsSize = keywords.size();
            if (keywordsSize > 0) {
                sb.append(" and ( ( lower(name) like ? ");
                for (i = 1; i != keywordsSize; i++) {
                    sb.append("or lower(name) like ? ");
                }
                sb.append(") or ( lower(catid) like ? ");
                for (i = 1; i != keywordsSize; i++) {
                    sb.append("or lower(catid) like ? ");
                }
                sb.append(") )");
            }

            System.err.println(sb.toString());

            ps = c.prepareStatement(sb.toString(),
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, l.toString());

            // The two loops are necessary because of the way the
            // query was constructed.
            i = 2;
            for (it = keywords.iterator(); it.hasNext(); i++) {
                String keyword = ((String) it.next()).toLowerCase();
                ps.setString(i, "%" + keyword + "%");
            }
            for (it = keywords.iterator(); it.hasNext(); i++) {
                String keyword = ((String) it.next()).toLowerCase();
                ps.setString(i, "%" + keyword + "%");
            }

            rs = ps.executeQuery();
            // XXX
            /*
            if (start >= 0 && start < total) {
                List items = new ArrayList();
                rs.absolute(start+1);
                do {
                    i = 1;
                    items.add(new Item(rs.getString(i++).trim(),
                                       rs.getString(i++).trim(),
                                       rs.getString(i++),
                                       rs.getString(i++).trim(),
                                       rs.getString(i++).trim(),
                                       rs.getString(i++),
                                       rs.getString(i++),
                                       rs.getString(i++),
                                       rs.getString(i++),
                                       rs.getString(i++),
                                       rs.getString(i++),
                                       rs.getDouble(i++),
                                       rs.getDouble(i++)));
                } while (rs.next() && (--count > 0));
                ret = new Page(items, start, total);
            }
            */
            if (start >= 0 && rs.absolute(start+1)) {
              boolean hasNext = false;
              List items = new ArrayList();
              do {
                i = 1;
                items.add(new Item(rs.getString(i++).trim(),
                                   rs.getString(i++).trim(),
                                   rs.getString(i++),
                                   rs.getString(i++).trim(),
                                   rs.getString(i++).trim(),
                                   rs.getString(i++),
                                   rs.getString(i++),
                                   rs.getString(i++),
                                   rs.getString(i++),
                                   rs.getString(i++),
                                   rs.getString(i++),
                                   rs.getDouble(i++),
                                   rs.getDouble(i++)));
                } while ((hasNext = rs.next()) && (--count > 0));
                ret = new Page(items, start, hasNext);
            }
            else {
                ret = Page.EMPTY_PAGE;
            }

            rs.close();
            ps.close();

            c.close();
            return ret;
        }
        catch (SQLException se) {
            throw new CatalogDAOSysException("SQLException -07 : "
                                             + se.getMessage());
        }
    }
}

