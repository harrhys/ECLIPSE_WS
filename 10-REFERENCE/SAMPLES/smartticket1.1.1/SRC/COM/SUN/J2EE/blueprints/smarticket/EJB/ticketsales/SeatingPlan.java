/*
 * Copyright 1999-2002 Sun Microsystems, Inc. ALL RIGHTS RESERVED
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

package com.sun.j2ee.blueprints.smarticket.ejb.ticketsales;

/**
 * Represents a seating plan for a show.
 *
 * The seating plan is rectangular, with array-style indices.
 * That is, rows are indexed from 0 to r-1 inclusive, where
 * r is the number of rows in the seating plan, and seats are
 * indexed from 0 to s-1 inclusive, where s is the number of
 * seats in the seating plan.
 */
public class SeatingPlan implements java.io.Serializable {

    public static final byte AISLE = 9;
    public static final byte AVAILABLE = 0;
    public static final byte UNAVAILABLE = 1;
    public static final byte BOOKED = 2;

    protected byte[] seats;
    protected int rows;
    protected int rowLength;

    public SeatingPlan() {
    }

    public SeatingPlan(int rowCount, int rowLen) {
	rows = rowCount;
	rowLength = rowLen;
	seats = new byte[rows * rowLength];
    }

    public SeatingPlan(byte[] seating, int rowCount, int rowLen) {
	if (rowCount * rowLen != seating.length) {
	    throw new IllegalArgumentException("rows * seats != length");
	}
	rows = rowCount;
	rowLength = rowLen;
	seats = seating;
    }

    public SeatingPlan(SeatingPlan sp) {
	rows = sp.rows;
	rowLength = sp.rowLength;
	seats = new byte[sp.seats.length];
	System.arraycopy(sp.seats, 0, seats, 0, sp.seats.length);
    }

    public int getRowCount() {
	return rows;
    }

    public int getRowLength() {
	return rowLength;
    }

    public byte[] getSeatData() {
	return seats;
    }

    public boolean isAisle(int r, int s) {

	return getStatusOf(r, s) == AISLE; 
    }

    public boolean isAvailable(int r, int s) { 
        return getStatusOf(r, s) == AVAILABLE; 
    }

    public boolean isBooked(int r, int s) { 
        return getStatusOf(r, s) == BOOKED; 
    }

    public boolean isUnavailable(int r, int s) { 
        return getStatusOf(r, s) == UNAVAILABLE;
    }

    public byte getStatusOf(int r, int s) {
	if (s > rowLength) {
	    throw new IllegalArgumentException("Seat > rowLength");
	}
	return seats[seatIndex(r, s)];
    }

    public void setStatusOf(int r, int s, byte status) {
	if (r > rows || s > rowLength) {
	    throw new IllegalArgumentException("row or seat too large");
	}
	seats[seatIndex(r, s)] = status;
    }

    private int seatIndex(int r, int s) {
	if (r > rows || s > rowLength) {
	    throw new IllegalArgumentException("row or seat too large");
	}
	return r * rowLength + s;
    }
    
    public void setAvailable(int r, int s) {
	seats[seatIndex(r, s)] = AVAILABLE;
    }
     
    public void setBooked(int r, int s) {
	seats[seatIndex(r, s)] = BOOKED;
    }
    
    public void setUnavailable(int r, int s) {
	seats[seatIndex(r, s)] = UNAVAILABLE;
    }

    public boolean hasBooking(int r) {
	int i = r * rowLength;
	int end = i + rowLength;
	for (; i < end; i++) {
	    if (seats[i] == BOOKED) {
		return true;
	    }
	}
	return false;
    }

    public void setRow(int r, String seats) {
        for (int s = 0; s < seats.length(); s++) {
            setStatusOf(r, s, (byte) Byte.parseByte(
		new Character(seats.charAt(s)).toString()));
        }
    }

    public String getRow(int r) {
        StringBuffer sb = new StringBuffer();
        for (int s = 0; s < getRowLength(); s++) {
            sb.append(String.valueOf(new Byte(
		getStatusOf(r, s)).intValue()).charAt(0));
        }
        return sb.toString();
    }
}
