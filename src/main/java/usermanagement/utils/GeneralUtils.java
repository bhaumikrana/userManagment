package usermanagement.utils;

import java.util.Random;

public class GeneralUtils {

	public static String getRandomNumber(int charLength) {
        
		return String.valueOf(charLength < 1 ? 0 : new Random()
                .nextInt((9 * (int) Math.pow(10, charLength - 1)) - 1)
                + (int) Math.pow(10, charLength - 1));
    }
	
}
