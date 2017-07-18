package com.exercise.reminder.validator;

import com.exercise.reminder.entity.ReminderRequest;
import com.exercise.reminder.exception.ReminderException;

// TODO: Auto-generated Javadoc
/**
 * The Interface ReminderValidator.
 * @author Vinay Vaidya
 */
public interface ReminderValidator {
	
	/**
	 * Validate.
	 *
	 * @param request the request
	 * @return true, if successful
	 * @throws ReminderException the reminder exception
	 */
	public boolean validate(ReminderRequest request) throws ReminderException;
	
	/**
	 * Validate.
	 *
	 * @param id the id
	 * @return true, if successful
	 * @throws ReminderException the reminder exception
	 */
	public default boolean validate(long id) throws ReminderException
	{
		return false;
		
	}
}
