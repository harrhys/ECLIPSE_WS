/*
 *
 * Copyright 2002 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 * 
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * 
 * - Redistribution in binary form must reproduce the above
 *   copyright notice, this list of conditions and the following
 *   disclaimer in the documentation and/or other materials
 *   provided with the distribution.
 * 
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 * 
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY
 * DAMAGES OR LIABILITIES SUFFERED BY LICENSEE AS A RESULT OF OR
 * RELATING TO USE, MODIFICATION OR DISTRIBUTION OF THIS SOFTWARE OR
 * ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE
 * FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT,
 * SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF
 * THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS
 * BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that this software is not designed, licensed or
 * intended for use in the design, construction, operation or
 * maintenance of any nuclear facility.
 * 
 */

package com.sun.cb;

import java.io.Serializable;
import java.math.BigDecimal;

public class RetailPriceItem implements Serializable {

    private String coffeeName;
    private BigDecimal wholesalePricePerPound;
    private BigDecimal retailPricePerPound;
    private String distributor;

    public RetailPriceItem() {

        this.coffeeName = null;
        this.wholesalePricePerPound = new BigDecimal("0.00");
        this.retailPricePerPound = new BigDecimal("0.00");
        this.distributor = null;
    }

    public RetailPriceItem(String coffeeName, BigDecimal wholesalePricePerPound, BigDecimal retailPricePerPound, String distributor) {

        this.coffeeName = coffeeName;
        this.wholesalePricePerPound = wholesalePricePerPound;
        this.retailPricePerPound = retailPricePerPound;
        this.distributor = distributor;
    }

    public String getCoffeeName() {
        return coffeeName;
    }

    public void setCoffeeName(String coffeeName) {
        this.coffeeName = coffeeName;
    }

    public BigDecimal getWholesalePricePerPound() {
        return wholesalePricePerPound;
    }

    public BigDecimal getRetailPricePerPound() {
        return retailPricePerPound;
    }

    public void setRetailPricePerPound(BigDecimal retailPricePerPound) {
        this.retailPricePerPound = retailPricePerPound;
    }

    public void setWholesalePricePerPound(BigDecimal wholesalePricePerPound) {
        this.wholesalePricePerPound = wholesalePricePerPound;
    }
    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }
}

