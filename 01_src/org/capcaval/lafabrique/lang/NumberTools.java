package org.capcaval.lafabrique.lang;

public class NumberTools {

	public static boolean isOdd(int nb) {
		// nb and 0x01
		return (nb & 0x01)==1;
	}

	public static boolean isEven(int nb) {
		// inverse of odd
		return ! isOdd(nb);
	}

}
