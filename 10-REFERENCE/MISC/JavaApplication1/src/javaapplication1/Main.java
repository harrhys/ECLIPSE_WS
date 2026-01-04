/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplication1;

import java.math.BigDecimal;

/**
 *
 * @author somasundaram
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("A");
        ValidEventFeeType a = ValidEventFeeType.FEE_TYPE_CODE_ENROLLMENT;
        ValidEventFeeType b = ValidEventFeeType.FEE_TYPE_CODE_ENROLLMENT;
        ValidEventFeeType c = ValidEventFeeType.FEE_TYPE_CODE_MONTHLY;
       // System.out.println(a.getFeeType());
        //System.out.println(a.getBool());
        //System.out.println(a);
        System.out.println(a==b);
       // System.out.println(a==c);
        System.out.println(a.equals(b));
        //System.out.println(a.equals(c));
        String s = "1";
        BigDecimal s1 = new BigDecimal(s);
    }
}
enum ValidEventFeeType  {
	//validEventFeeTypeCodes
	FEE_TYPE_CODE_ENROLLMENT("ENRLMNT", false),
	FEE_TYPE_CODE_ENROLLMENT_SPNRD("ENRLMNT.SPNRD", false),
	FEE_TYPE_CODE_MONTHLY("MONTHLY", true),
	FEE_TYPE_CODE_MONTHLY_SPNRD("MONTHLY.SPNRED", false),
	FEE_TYPE_CODE_CLOSEACCT("CLOSEACT", true),
	FEE_TYPE_CODE_CLOSEACCT_SPNRD("CLOSEACT.SPNRED", false);

	private String feeType = null;
        private boolean bool = true;

	private ValidEventFeeType(String feeType, boolean bool) {
		this.feeType = feeType;
                this.bool = bool;
	}
	//public boolean equals(ValidEventFeeType feeType) {
	//	return super.equals((ValidEventFeeType) feeType);
	//}

	public String getFeeType() {
		return feeType;
	}
	public boolean getBool() {
		return bool;
	}
        public ValidEventFeeType getValidEventFeeType(String str) {
            return
        }
}
