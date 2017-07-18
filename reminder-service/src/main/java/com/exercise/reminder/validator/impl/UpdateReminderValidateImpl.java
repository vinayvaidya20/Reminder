package com.exercise.reminder.validator.impl;

import org.apache.commons.lang.StringUtils;

import com.exercise.reminder.entity.ReminderRequest;
import com.exercise.reminder.exception.ExceptionCodes;
import com.exercise.reminder.exception.ExceptionTypes;
import com.exercise.reminder.exception.ReminderException;
import com.exercise.reminder.validator.ReminderValidator;

// TODO: Auto-generated Javadoc
/**
 * The Class UpdateReminderValidateImpl.This will append all the validation together.So that all the errors are provide in one call
 * @author Vinay Vaidya
 */
public class UpdateReminderValidateImpl implements ReminderValidator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exercise.reminder.validator.ReminderValidator#validate(com.exercise.
	 * reminder.entity.ReminderRequest)
	 */
	@Override
	public boolean validate(ReminderRequest request) throws ReminderException {

		StringBuilder invalidFields = new StringBuilder();
		if (request.getId() <=0) {
			invalidFields.append("Please enter vaild id. ");
		}
		if (!invalidFields.toString().isEmpty()) {

			throw new ReminderException(ExceptionTypes.INVALID, ExceptionCodes.INVALIDREQUEST,
					invalidFields.toString());
		}

		return true;
	}

}
