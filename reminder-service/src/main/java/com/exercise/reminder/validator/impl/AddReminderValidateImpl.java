package com.exercise.reminder.validator.impl;

import org.apache.commons.lang.StringUtils;

import com.exercise.reminder.constants.ReminderConstants;
import com.exercise.reminder.entity.ReminderRequest;
import com.exercise.reminder.exception.ExceptionCodes;
import com.exercise.reminder.exception.ExceptionTypes;
import com.exercise.reminder.exception.ReminderException;
import com.exercise.reminder.util.DateUtil;
import com.exercise.reminder.validator.ReminderValidator;

// TODO: Auto-generated Javadoc
/**
 * The Class AddReminderValidateImpl. This will append all the validation together.So that all the errors are provide in one call
 * @author Vinay Vaidya
 */
public class AddReminderValidateImpl implements ReminderValidator {

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
		if (StringUtils.isEmpty(request.getName())) {
			invalidFields.append("Please enter name. ");
		}
		if (request.getId() !=0) {
			invalidFields.append("Please do not enter Id. ");
		}
		if (StringUtils.isEmpty(request.getDescription())) {
			invalidFields.append("Please enter description. ");
		}
		if (StringUtils.isEmpty(request.getStatus())) {
			invalidFields.append("Please enter status. ");
		}
		if (!StringUtils.isEmpty(request.getStatus()) &&  !(ReminderConstants.Status.NOTDONE.name().equals(request.getStatus()) || ReminderConstants.Status.DONE.name().equals(request.getStatus()))) {
			invalidFields.append("Please enter valid status NOTDONE or DONE.");
		}
		if (request.getDueDate() != null && StringUtils.isNotEmpty(request.getDueDate().toString())
				&& DateUtil.checkPastDate(request.getDueDate())) {
			invalidFields.append("Please enter valid date.");
		}
		if (request.getDueDate() == null || StringUtils.isEmpty(request.getDueDate().toString())) {
			invalidFields.append("Please enter date.");
		}
		if (!invalidFields.toString().isEmpty()) {

			throw new ReminderException(ExceptionTypes.INVALID, ExceptionCodes.INVALIDREQUEST,
					invalidFields.toString());
		}

		return true;
	}

}
