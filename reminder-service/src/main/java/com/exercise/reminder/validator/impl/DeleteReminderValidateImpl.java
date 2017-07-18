package com.exercise.reminder.validator.impl;

import org.apache.commons.lang.StringUtils;

import com.exercise.reminder.entity.ReminderRequest;
import com.exercise.reminder.exception.ExceptionCodes;
import com.exercise.reminder.exception.ExceptionTypes;
import com.exercise.reminder.exception.ReminderException;
import com.exercise.reminder.validator.ReminderValidator;


// TODO: Auto-generated Javadoc
/**
 * The Class DeleteReminderValidateImpl.This will append all the validation together.So that all the errors are provide in one call
 * @author Vinay Vaidya
 */
public class DeleteReminderValidateImpl implements ReminderValidator {

	/* (non-Javadoc)
	 * @see com.exercise.reminder.validator.ReminderValidator#validate(com.exercise.reminder.entity.ReminderRequest)
	 */
	@Override
	public boolean validate(ReminderRequest request) throws ReminderException {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.exercise.reminder.validator.ReminderValidator#validate(long)
	 */
	@Override
	public boolean validate(long id) throws ReminderException {
		StringBuilder invalidFields =  new StringBuilder();
		if(StringUtils.isEmpty(String.valueOf(id)))
		{
			invalidFields.append("Please enter id. ");
		}
        if (!invalidFields.toString().isEmpty()) {

            throw new ReminderException(
            		ExceptionTypes.INVALID,ExceptionCodes.INVALIDREQUEST,
            		invalidFields.toString());
        }
        
        return true;
	}

}
