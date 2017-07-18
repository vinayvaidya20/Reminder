package com.exercise.reminder.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class DateUtil.
 * @author Vinay Vaidya
 */
public final class DateUtil {
	
	
	/** The Constant logger. */
	static final Logger logger = Logger.getLogger(DateUtil.class);
	
	
	/**
	 * Instantiates a new date util.
	 */
	private DateUtil() {
	}

	/**
	 * Date convertor from java date to string.
	 *
	 * @param date the date
	 * @return the string
	 */
	public static String dateConvertor(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		return dateFormat.format(date);
	}

	/**
	 * Check past date by comparing system current date.
	 * @param date the date
	 * @return true, if successful
	 */
	public static boolean checkPastDate(Date date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			Date inputDate = sdf.parse(sdf.format(date));
			Date currentDate = sdf.parse(sdf.format(Calendar.getInstance().getTime()));

			logger.debug("date1 : " + sdf.format(inputDate));
			logger.debug("date2 : " + sdf.format(currentDate));

			if (inputDate.compareTo(currentDate) < 0) {
				logger.debug("Date1 is before Date2");
				return true;
			}
		} catch (ParseException e) {
			return true;
		}

		return false;
	}
	
}
