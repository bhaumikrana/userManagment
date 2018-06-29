package usermanagement.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class Utils {

	/**
	 * Get Root URL
	 * @param request
	 * @return
	 */
	public static String getRootURL(HttpServletRequest request) {
		return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
	}
	
	/**
	 * generate 6 digit random number
	 * @return
	 */
	public static int createRandomNumbers() {
		 return (int) Math.floor(Math.random() * (100000 - 100 + 1)) + 100000;
	}
	
	/**
	 * Convert Date to String
	 * @param strDate
	 * @return
	 * @throws ParseException 
	 */
	public static String convertDate(String strDate)  {
		try {
			Date date = new SimpleDateFormat("MM-dd-yyyy").parse(strDate);
			return new SimpleDateFormat("yyyy-MM-dd").format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
 	}
	
	/**
	 * get current date in string 
	 * @return
	 */
	public static String currentStringDate()  {
		return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
	}
	
	/**
	 * convert string date to util date
	 * @param dateStr
	 * @return
	 */
	public static Date getDate(String dateStr) {
		final DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
		try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {                
            return null;
        }
    }
	
	/**
	 * get difference between two datetime
	 * @param dateTime
	 * @return
	 */
	public static long getDifferenceBtwTimeInLong(Date dateTime) {
		long timeDifferenceMilliseconds = new Date().getTime() - dateTime.getTime();
	    long diffMinutes = timeDifferenceMilliseconds / (60 * 1000);
	    return diffMinutes;
	}
	
	public static boolean validateEmail(String email) {
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();		
	}
	
	/**
	 * compare date to current date returns diffrece in String
	 */
	public static String getDifferenceBtwTime(Date dateTime) {
		
		long timeDifferenceMilliseconds = new Date().getTime() - dateTime.getTime();
		long diffSeconds = timeDifferenceMilliseconds / 1000;
	    long diffMinutes = timeDifferenceMilliseconds / (60 * 1000);
	    long diffHours = timeDifferenceMilliseconds / (60 * 60 * 1000);
	    long diffDays = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24);
	    long diffWeeks = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 7);
	    long diffMonths = (long) (timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 30.41666666));
	    long diffYears = (long) (timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 30.41666666 * 12));

	    if (diffSeconds < 1) {
	        return "one sec ago";
	    } else if (diffMinutes < 1) {
	        return diffSeconds + " seconds ago";
	    } else if (diffHours < 1) {
	        return diffMinutes + " minutes ago";
	    } else if (diffDays < 1) {
	        return diffHours + " hours ago";
	    } else if (diffWeeks < 1) {
	        return diffDays + " days ago";
	    } else if (diffMonths < 1) {
	        return diffWeeks + " weeks ago";
	    } else if (diffYears < 1) {
	        return diffMonths + " months ago";
	    } else {
	        return diffYears + " years ago";
	    }
	}
}
