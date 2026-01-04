/*
 *
 * Copyright (c) 2002 Sun Microsystems, Inc. All rights reserved.
 *
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 *
 */

package samples.ejb.bmp.robean.ejb;

public class PKString implements java.io.Serializable {
    public String pkString = null;

    public PKString() {
    }

    public PKString(String pkString) {
        this.pkString = pkString;
    }

    public String getPK() {
        return pkString;
    }

    public int hashCode() {
        return pkString.hashCode();
    }

    public boolean equals(Object other) {
        if (other instanceof PKString) {
            return ((PKString) other).pkString.equals(pkString);
        }
        return false;
    }
}
