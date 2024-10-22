package com.peppercoin.common.persistence;

import com.peppercoin.common.exception.PpcnError;
import com.peppercoin.common.platform.EjbUtil;
import com.peppercoin.common.query.Query;
import com.peppercoin.common.query.QueryResultSet;
import com.peppercoin.common.query.impl.QueryResultsImpl;
import com.peppercoin.common.query.impl.ScrollableQueryResultsImpl;
import com.peppercoin.common.util.CreditCard;
import com.peppercoin.common.PpcnDate;
import com.peppercoin.common.PpcnLogger;
import com.peppercoin.common.PpcnTime;
import com.peppercoin.common.PpcnLogger.Level;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.rmi.PortableRemoteObject;
import net.sf.hibernate.Criteria;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.cfg.Configuration;
import net.sf.hibernate.expression.Expression;
import net.sf.hibernate.type.BigDecimalType;
import net.sf.hibernate.type.BooleanType;
import net.sf.hibernate.type.IntegerType;
import net.sf.hibernate.type.LongType;
import net.sf.hibernate.type.StringType;
import net.sf.hibernate.type.Type;

public class DataAccessTemplate {
	private static final PpcnLogger logger;
	private static final Map factories;
	private static final String DEFAULT_DS = "";
	private static final Class[] CLASSES_CACHED;
	private SessionFactory sessionFactory = null;
	private Session hSession = null;

	public DataAccessTemplate() {
		this.init("");

		try {
			this.hSession = this.sessionFactory.openSession();
		} catch (HibernateException var2) {
			throw new PpcnError("DataAccessTemplate()", var2);
		}
	}

	public DataAccessTemplate(String datasource) {
		this.init(datasource);

		try {
			this.hSession = this.sessionFactory.openSession();
		} catch (HibernateException var3) {
			throw new PpcnError("DataAccessTemplate(" + datasource + ")", var3);
		}
	}

	public void setDataSource(String datasource) {
		this.close();
		this.init(datasource);

		try {
			this.hSession = this.sessionFactory.openSession();
		} catch (HibernateException var3) {
			throw new PpcnError("DataAccessTemplate(" + datasource + ")", var3);
		}
	}

	private synchronized void init(String datasource) {
		if (factories.containsKey(datasource)) {
			this.sessionFactory = (SessionFactory) factories.get(datasource);
		} else {
			this.sessionFactory = createFactory(datasource);
		}
	}

	private static SessionFactory createFactory(String key) {
		SessionFactory factory = null;
		if (!key.equals("")) {
			key = "-" + key;
		}

		Object objref = EjbUtil.lookup("java:/hibernate/HibernateFactory" + key);

		try {
			if (objref != null) {
				factory = (SessionFactory) PortableRemoteObject.narrow(objref,net.sf.hibernate.SessionFactory.class);
			} else {
				Configuration cfg = new Configuration();
				cfg.configure(new File("C:\\hibernate.cfg.xml"));
				factory = cfg.buildSessionFactory();
			}
		} catch (Exception var4) {
			throw new RuntimeException("Big Exception building SessionFactory: " + var4.getMessage(), var4);
		}

		factories.put(key, factory);
		return factory;
	}

	public Object getSession() {
		return this.hSession;
	}

	public PersistentObject get(Class entityClass, Serializable id) {
		try {
			return (PersistentObject) this.hSession.get(entityClass, id);
		} catch (HibernateException var4) {
			this.close();
			throw new PpcnError("System Error: Hibernate exception:", var4);
		}
	}

	public PersistentObject getOrNull(Class entityClass, Serializable id) {
		try {
			return (PersistentObject) this.hSession.get(entityClass, id);
		} catch (HibernateException var4) {
			return null;
		}
	}

	public List getAll(Class entityClass) {
		try {
			return this.hSession.find("from " + entityClass.getName());
		} catch (HibernateException var3) {
			throw new PpcnError("System Error: Hibernate exception:", var3);
		}
	}

	public List getAll(String query) {
		try {
			return this.hSession.find(query);
		} catch (HibernateException var3) {
			throw new PpcnError("System Error: Hibernate exception:", var3);
		}
	}

	public List getAll(String query, Object[] args) {
		try {
			Type[] types = new Type[args.length];
			Object[] nargs = new Object[args.length];

			for (int i = 0; i < args.length; ++i) {
				types[i] = this.guessType(args[i]);
				nargs[i] = this.typeRemap(args[i]);
			}

			return this.hSession.find(query, nargs, types);
		} catch (HibernateException var6) {
			throw new PpcnError("System Error: Hibernate exception:", var6);
		}
	}

	private Object typeRemap(Object obj) {
		if (obj == null) {
			return obj;
		} else if (obj.getClass()
				.equals(com.peppercoin.common.util.PpcnTime.class)) {
			return ((PpcnTime) obj).toDbString();
		} else {
			return obj.getClass().equals(com.peppercoin.common.util.PpcnDate.class) ? ((PpcnDate) obj).toDbString() : obj;
		}
	}

	private Type guessType(Object obj) {
		if (obj == null) {
			return new StringType();
		} else if (obj.getClass()
				.equals(java.lang.String.class)) {
			return new StringType();
		} else if (obj.getClass()
				.equals(java.lang.Integer.class)) {
			return new IntegerType();
		} else if (obj.getClass()
				.equals(java.lang.Long.class)) {
			return new LongType();
		} else if (obj.getClass()
				.equals(java.lang.Boolean.class)) {
			return new BooleanType();
		} else if (obj.getClass()
				.equals(java.math.BigDecimal.class)) {
			return new BigDecimalType();
		} else if (obj.getClass()
				.equals(com.peppercoin.common.util.PpcnTime.class)) {
			return new StringType();
		} else if (obj.getClass()
				.equals(com.peppercoin.common.util.PpcnDate.class)) {
			return new StringType();
		} else if (obj instanceof CreditCard) {
			return Hibernate.entity(com.peppercoin.common.util.CreditCard.class);
		} else {
			throw new PpcnError("guessType for " + obj.getClass().getName() + ": can't map to a Hibernate type");
		}
	}

	public List getAllByStringProps(String query, String[] args) {
		try {
			Type[] types = new Type[args.length];

			for (int i = 0; i < args.length; ++i) {
				types[i] = new StringType();
			}

			return this.hSession.find(query, args, types);
		} catch (HibernateException var5) {
			throw new PpcnError("System Error: Hibernate exception:", var5);
		}
	}

	public void save(PersistentObject entity) {
		try {
			this.hSession.save(entity);
			this.hSession.flush();
		} catch (HibernateException var3) {
			throw new PpcnError("System Error: Hibernate exception:", var3);
		}
	}

	public void delete(PersistentObject entity) {
		try {
			this.hSession.delete(entity);
			this.hSession.flush();
		} catch (HibernateException var3) {
			throw new PpcnError("System Error: Hibernate exception:", var3);
		}
	}

	public void saveOrUpdate(PersistentObject entity) {
		try {
			this.hSession.saveOrUpdate(entity);
			this.hSession.flush();
		} catch (HibernateException var3) {
			throw new PpcnError("System Error: Hibernate exception:", var3);
		}
	}

	public List sqlQuery(String sql, String returnAlias, Class returnClass) {
		try {
			return this.hSession.createSQLQuery(sql, returnAlias, returnClass).list();
		} catch (HibernateException var5) {
			throw new PpcnError("System Error: Hibernate exception:", var5);
		}
	}

	public QueryResultSet runQuery(Query query) {
		Object result = null;

		try {
			net.sf.hibernate.Query hQ = null;
			if (query.useNativeSQL()) {
				hQ = this.hSession.createSQLQuery(query.getQueryDef(), query.getAliases(), query.getClasses());
			} else {
				hQ = this.hSession.createQuery(query.getQueryDef());
				List vals = query.getValues();
				if (vals != null) {
					Iterator iter = vals.iterator();

					for (int i = 0; iter.hasNext(); ++i) {
						Object value = iter.next();
						Type type = this.guessType(value);
						hQ.setParameter(i, value, type);
					}
				}
			}

			logger.log("runQuery", hQ.toString(), Level.DEBUG);
			if (query.useScrollableResult()) {
				result = new ScrollableQueryResultsImpl(hQ.scroll(), hQ.list().size());
			} else {
				result = new QueryResultsImpl(hQ.list());
			}

			return (QueryResultSet) result;
		} catch (HibernateException var9) {
			throw new PpcnError("System Error: Hibernate exception:", var9);
		}
	}

	public List queryByCriteria(Class queryClass, Object[] props, Object[] values) {
		try {
			Criteria crit = this.hSession.createCriteria(queryClass);

			for (int i = 0; i < props.length; ++i) {
				crit.add(Expression.eq((String) props[i], values[i]));
			}

			return crit.list();
		} catch (HibernateException var6) {
			throw new PpcnError("System Error: Hibernate exception:", var6);
		}
	}

	public void close() {
		try {
			if (this.hSession != null) {
				this.hSession.flush();
				this.hSession.close();
			}

			this.hSession = null;
		} catch (HibernateException var2) {
			throw new PpcnError("System Error: Hibernate exception:", var2);
		}
	}

	public static synchronized void clearSecondLevelCache() {
		Iterator dataSourceIterator = factories.keySet().iterator();

		while (dataSourceIterator.hasNext()) {
			String nextDataSource = (String) dataSourceIterator.next();
			SessionFactory nextFactory = (SessionFactory) factories.get(nextDataSource);
			clearSecondLevelCache(nextFactory);
		}

	}

	private static void clearSecondLevelCache(SessionFactory sessionFactory) {
		for (int iClass = 0; iClass < CLASSES_CACHED.length; ++iClass) {
			try {
				sessionFactory.evict(CLASSES_CACHED[iClass]);
			} catch (HibernateException var3) {
				logger.log("clearSecondLevelCache",
						"Could not evict: " + CLASSES_CACHED[iClass] + "  Exception: " + var3, Level.WARN);
			}
		}

	}

	static {
		logger = new PpcnLogger(com.peppercoin.common.persistence.DataAccessTemplate.class.getName());
		factories = new HashMap();
		CLASSES_CACHED = new Class[]{com.peppercoin.common.ref.Merchant.class, com.peppercoin.common.auth.PpcnUser.class};
	}
}