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


/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package com.sun.cb;

import java.util.*;
import java.text.SimpleDateFormat;

/**
 * This class contains helper methods for dealing with
 * Date objects.
 */

public final class DateHelper {

    public static final Date getDate(int year, int month, int day,
        int hour, int minute) {

        // returns a Date with the specified time elements

        Calendar cal = new GregorianCalendar(year, intToCalendarMonth(month), 
            day, hour, minute);

        return cal.getTime();

    } // getDate

    public static final Date getDate(int year, int month, int day) {

        // returns a Date with the specified time elements,
        // with the hour and minutes both set to 0 (midnight)

        Calendar cal = new GregorianCalendar(year, intToCalendarMonth(month), 
            day);

        return cal.getTime();

    } // getDate

    static public final Date addDays(Date target, int days) {
        
        // returns a Date that is the sum of the target Date
        // and the specified number of days;
        // to subtract days from the target Date, the days
        // argument should be negative

        long msPerDay = 1000 * 60 * 60 * 24;
        long msTarget = target.getTime();
        long msSum = msTarget + (msPerDay * days);
        Date result = new Date();
        result.setTime(msSum);
        return result;
    } // addDays


    static public int dayDiff(Date first, Date second) {

        // returns the difference, in days, between the first
        // and second Date arguments

        long msPerDay = 1000 * 60 * 60 * 24;
        long diff = (first.getTime() / msPerDay) - (second.getTime() / msPerDay);
        Long convertLong = new Long(diff);
        return convertLong.intValue();
    } // dayDiff
 

    static public int getYear(Date date) {

        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    } // getYear 

    static public int getMonth(Date date) {

        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        int calendarMonth = cal.get(Calendar.MONTH);
        return calendarMonthToInt(calendarMonth);
    } // getMonth

    static public int getDay(Date date) {

        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    } // getDay

    static public int getHour(Date date) {

        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(Calendar.HOUR_OF_DAY);
    } // geHour

    static public int getMinute(Date date) {

        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(Calendar.MINUTE);
    } // geMinute

    private static int calendarMonthToInt(int calendarMonth) {

        if (calendarMonth == Calendar.JANUARY)
            return 1;
        else if (calendarMonth == Calendar.FEBRUARY)
            return 2;
        else if (calendarMonth == Calendar.MARCH)
            return 3;
        else if (calendarMonth == Calendar.APRIL)
            return 4;
        else if (calendarMonth == Calendar.MAY)
            return 5;
        else if (calendarMonth == Calendar.JUNE)
            return 6;
        else if (calendarMonth == Calendar.JULY)
            return 7;
        else if (calendarMonth == Calendar.AUGUST)
            return 8;
        else if (calendarMonth == Calendar.SEPTEMBER)
            return 9;
        else if (calendarMonth == Calendar.OCTOBER)
            return 10;
        else if (calendarMonth == Calendar.NOVEMBER)
            return 11;
        else if (calendarMonth == Calendar.DECEMBER)
            return 12;
        else
            return 1;

    } // calendarMonthToInt

    public static String format(Date date, String pattern) {

        // returns a String representation of the date argument,
        // formatted according to the pattern argument, which
        // has the same syntax as the argument of the SimpleDateFormat
        // class

        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);

    } // format


    private static int intToCalendarMonth(int month) {

        if (month == 1)
           return Calendar.JANUARY;
        else if (month == 2)
           return Calendar.FEBRUARY;
        else if (month == 3)
           return Calendar.MARCH;
        else if (month == 4)
           return Calendar.APRIL;
        else if (month == 5)
           return Calendar.MAY;
        else if (month == 6)
           return Calendar.JUNE;
        else if (month == 7)
           return Calendar.JULY;
        else if (month == 8)
           return Calendar.AUGUST;
        else if (month == 9)
           return Calendar.SEPTEMBER;
        else if (month == 10)
           return Calendar.OCTOBER;
        else if (month == 11)
           return Calendar.NOVEMBER;
        else if (month == 12)
           return Calendar.DECEMBER;
        else
           return Calendar.JANUARY;

    } // intToCalendarMonth


} // class 
