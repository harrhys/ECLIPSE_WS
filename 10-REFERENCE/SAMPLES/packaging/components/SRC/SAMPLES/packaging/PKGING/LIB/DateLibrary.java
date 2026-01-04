/**
 * Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
 */

package samples.packaging.pkging.lib;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * This class contains date manipulation functions which are used by the packaging samples.
 * This class along with Calculator is used to demo the addition of utility classes into a
 * SunONE application package.
 *
 * @see samples.packaging.pkging.lib.Calculator
 */
public class DateLibrary {

    /**
     * Gets the number of days between two calendar dates.
     * @param date1 start date
     * @param date2 end date
     * @return long number of days between start and end dates.
     */
    public long getDaysBetween(Calendar date1, Calendar date2) {

        final long ONE_HOUR = 60 * 60 * 1000L;

        // the earlier one of the dates
        Calendar earlierDate = new GregorianCalendar();
        // the later one of the dates
        Calendar laterDate = new GregorianCalendar();
        // the final result is retured in nDays
        long nDays = 0;

        // get the earlier date
        if (date1.equals(date2)) {
            // same date
            return 0;
        }

        if (date1.before(date2)) {
            earlierDate = date1;
            laterDate = date2;
        } else {
            earlierDate = date2;
            laterDate = date1;
        }


        // the first getTime() returns a Date, the second takes
        // that Date object and returns millisecs since 1/1/70.
        long duration = laterDate.getTime().getTime() - earlierDate.getTime().getTime();

        // Add one hour in case the duration includes a
        // 23 hour Daylight Savings spring forward day.

        nDays = ( duration + ONE_HOUR ) / (24 * ONE_HOUR);
        return nDays;
    }
}
