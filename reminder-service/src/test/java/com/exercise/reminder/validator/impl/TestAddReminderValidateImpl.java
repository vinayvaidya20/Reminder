package com.exercise.reminder.validator.impl;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.AssertTrue;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.exercise.reminder.constants.ReminderConstants;
import com.exercise.reminder.controller.ReminderAppController;
import com.exercise.reminder.entity.ReminderRequest;
import com.exercise.reminder.exception.ExceptionCodes;
import com.exercise.reminder.exception.ExceptionTypes;
import com.exercise.reminder.exception.ReminderException;
import com.exercise.reminder.service.ReminderService;
import com.exercise.reminder.util.DateUtil;
import com.exercise.reminder.validator.ReminderValidator;
@RunWith(MockitoJUnitRunner.class)
public class TestAddReminderValidateImpl {
	
	
	@InjectMocks
	AddReminderValidateImpl reminderValidator;

	@Test
	public void testValidate() throws ReminderException, ParseException {
		boolean flag =reminderValidator.validate(createReminderRequest());
		assertTrue(flag);

	}
	
	  @Test(expected = ReminderException.class)
	public void testValidateException() throws ReminderException, ParseException {
		reminderValidator.validate(new ReminderRequest());

	}
	
	private ReminderRequest createReminderRequest() throws ParseException {
		ReminderRequest reminderRequest = new ReminderRequest();
		reminderRequest.setName("Test");
		reminderRequest.setDescription("Test Description");
		reminderRequest.setStatus("DONE");
		reminderRequest.setId(1);
		reminderRequest.setDueDateString("2222-04-14");

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date inputDate = sdf.parse("04/14/2222");
		reminderRequest.setDueDate(inputDate);

		return reminderRequest;
	}

}
