package courses.hibernate.dao;

import javax.persistence.EntityManager;

import courses.hibernate.util.JPAUtil;
import courses.hibernate.vo.EBill;

/**
 * Data Access Object for EBill
 */
public class EBillDAO {

	/**
	 * Create a new ebill or update an existing one
	 * 
	 * @param ebill
	 *            ebill to be persisted
	 */
	public void saveOrUpdateEBill(EBill ebill) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.persist(ebill);
	}

	/**
	 * Retrieve an ebill from the data store
	 * 
	 * @param ebillId
	 *            identifier of the ebill to be retrieved
	 * @return ebill represented by the identifier provided
	 */
	public EBill getEBill(long ebillId) {
		EntityManager manager = JPAUtil.getEntityManager();
		return (EBill) manager.find(EBill.class, ebillId);
	}

	/**
	 * Delete ebill from data store
	 * 
	 * @param ebill
	 *            ebill to be deleted
	 */
	public void deleteEBill(EBill ebill) {
		EntityManager manager = JPAUtil.getEntityManager();
		ebill = manager.getReference(EBill.class, ebill.getEbillId());
		manager.remove(ebill);
	}
}
