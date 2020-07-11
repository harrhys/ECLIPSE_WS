package courses.hibernate.service;

import courses.hibernate.dao.EBillDAO;
import courses.hibernate.vo.EBill;

/**
 * Service layer for EBill
 */
public class EBillService {
	EBillDAO ebillDAO = new EBillDAO();

	/**
	 * Create a new ebill or update an existing one
	 * 
	 * @param ebill
	 *            ebill to be persisted
	 */
	public void saveOrUpdateEBill(EBill ebill) {
		ebillDAO.saveOrUpdateEBill(ebill);
	}

	/**
	 * Retrieve an ebill
	 * 
	 * @param ebillId
	 *            identifier of the ebill to be retrieved
	 * @return ebill represented by the identifier provided
	 */
	public EBill getEBill(long ebillId) {
		return ebillDAO.getEBill(ebillId);
	}

	/**
	 * Delete ebill from data store
	 * 
	 * @param ebill
	 *            ebill to be deleted
	 */
	public void deleteEBill(EBill ebill) {
		ebillDAO.deleteEBill(ebill);
	}

}
