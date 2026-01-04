/**
 * Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
 */

package samples.packaging.pkging.lib;

/**
 * This class contains the arithmetic functions which are used by the packaging beans and servlets.
 * This class along with DateLibrary are used to demo the addition of utility classes into
 * a SunONE application package.
 *
 * @see samples.packaging.pkging.lib.DateLibrary
 */
public class Calculator {

    /**
     * Returns the result by multiplying two doubles.
     * @param num1 first input
     * @param num2 second input
     * @return double result of the multiplication.
     */
    public double getProduct(double num1, double num2) {
        return (num1*num2);
    }

    /**
     * Returns the result of a division.
     * @param num1 first input, numerator
     * @param num2 second input, denominator
     * @return double result of the division.
     */
    public double getDivision(double num1, double num2) throws Exception {
        if (num2 == 0) throw new Exception("Cannot divide by zero");
        return (num1/num2);
    }

    /**
     * Returns the result of an addition.
     * @param num1 first input
     * @param num2 second input
     * @return double result of the addition.
     */
    public double getSum(double num1, double num2) {
        return (num1 + num2);
    }

    /**
     * Returns of value of the first argument raised to the power of the second argument.
     * @param num1 first input
     * @param num2 second input
     * @return double result of the first argument raised to the power of the second argument.
     */
    public double getPower(double num1, double num2) {
        return java.lang.Math.pow(num1, num2);
    }

    /**
     * Returns the result of a subtraction.
     * @param num1 first input
     * @param num2 second input
     * @return double result of the subtraction.
     */
    public double getDifference(double num1, double num2) {
        return (num1 - num2);
    }
}
