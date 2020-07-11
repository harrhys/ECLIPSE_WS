package courses.hibernate.dao;

import javax.persistence.EntityManager;

import courses.hibernate.util.JPAUtil;
import courses.hibernate.vo.EBiller;

/**
 * Data Access Object for EBiller
 */
public class EBillerDAO {

	/**
	 * Create a new ebiller or update an existing one
	 * 
	 * @param ebiller
	 *            ebiller to be persisted
	 */
	public void saveOrUpdateEBiller(EBiller ebiller) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.persist(ebiller);
	}

	/**
	 * Retrieve an ebiller from the data store
	 * 
	 * @param ebillerId
	 *            identifier of the ebiller to be retrieved
	 * @return ebiller represented by the identifier provided
	 */
	public EBiller getEBiller(long ebillerId) {
		EntityManager manager = JPAUtil.getEntityManager();
		EBiller ebiller = (EBiller) manager.find(EBiller.class, ebillerId);
		return ebiller;
	}

	/**
	 * Delete ebiller from data store
	 * 
	 * @param ebiller
	 *            ebiller to be deleted
	 */
	public void deleteEBiller(EBiller ebiller) {
		EntityManager manager = JPAUtil.getEntityManager();
		ebiller = manager.getReference(EBiller.class, ebiller.getEbillerId());
		manager.remove(ebiller);
	}
}
