package com.exercise.reminder.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.exercise.reminder.entity.ManageReminderResponse;
import com.exercise.reminder.entity.ReminderRequest;
import com.exercise.reminder.entity.ReminderResponse;
import com.exercise.reminder.service.ReminderService;

@RunWith(MockitoJUnitRunner.class)
public class TestReminderAppController {
	@InjectMocks
	ReminderAppController reminderAppController;
	@Mock
	ReminderService reminderService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void getReminder() throws Exception {

		List<ReminderRequest> reminderList = new ArrayList<ReminderRequest>();
		ReminderRequest reminderRequest = createReminderRequest();
		reminderList.add(reminderRequest);

		ReminderResponse reminderResponse = createReminderResponse(reminderList);

		ResponseEntity<ReminderResponse> reminderResponseEntity = new ResponseEntity<ReminderResponse>(reminderResponse,
				HttpStatus.OK);

		Mockito.when(reminderService.getReminder(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(reminderResponseEntity);

		ResponseEntity<ReminderResponse> Response = reminderAppController.getReminder("2017-04-13", "NOTDONE", "Test");
		assertEquals("Test", Response.getBody().getReminderList().get(0).getName());
	}

	private ReminderResponse createReminderResponse(List<ReminderRequest> reminderList) {
		ReminderResponse reminderResponse = new ReminderResponse();
		reminderResponse.setReminderList(reminderList);
		reminderResponse.setMessage("Reminder Success");
		reminderResponse.setType("SUCCESS");
		reminderResponse.setCode("200");
		return reminderResponse;
	}

	@Test
	public void addReminder() throws Exception {

		ManageReminderResponse manageReminderResponse = createManageReminderResponse();
		manageReminderResponse.setMessage("Reminder Added");
		ResponseEntity<ManageReminderResponse> reminderResponseEntity = new ResponseEntity<ManageReminderResponse>(
				manageReminderResponse, HttpStatus.OK);

		ReminderRequest reminderRequest = createReminderRequest();

		Mockito.when(reminderService.addReminder(Mockito.any())).thenReturn(reminderResponseEntity);

		ResponseEntity<ManageReminderResponse> Response = reminderAppController.addReminder(reminderRequest);
		assertEquals("Reminder Added", Response.getBody().getMessage());
	}

	@Test
	public void updateReminder() throws Exception {

		ManageReminderResponse manageReminderResponse = createManageReminderResponse();
		manageReminderResponse.setMessage("Reminder Updated");

		ResponseEntity<ManageReminderResponse> reminderResponseEntity = new ResponseEntity<ManageReminderResponse>(
				manageReminderResponse, HttpStatus.OK);

		ReminderRequest reminderRequest = createReminderRequest();

		Mockito.when(reminderService.updateReminder(Mockito.any())).thenReturn(reminderResponseEntity);

		ResponseEntity<ManageReminderResponse> Response = reminderAppController.updateReminder(reminderRequest);
		assertEquals("Reminder Updated", Response.getBody().getMessage());
	}

	@Test
	public void deleteReminder() throws Exception {

		ManageReminderResponse manageReminderResponse = createManageReminderResponse();
		manageReminderResponse.setMessage("Reminder deleted");

		ResponseEntity<ManageReminderResponse> reminderResponseEntity = new ResponseEntity<ManageReminderResponse>(
				manageReminderResponse, HttpStatus.OK);
		Mockito.when(reminderService.deleteReminder(Mockito.anyLong())).thenReturn(reminderResponseEntity);

		ResponseEntity<ManageReminderResponse> Response = reminderAppController.deleteReminder(1);
		assertEquals("Reminder deleted", Response.getBody().getMessage());
	}

	private ManageReminderResponse createManageReminderResponse() {
		ManageReminderResponse manageReminderResponse = new ManageReminderResponse();
		manageReminderResponse.setCode("200");
		manageReminderResponse.setType("Success");
		return manageReminderResponse;
	}

	private ReminderRequest createReminderRequest() {
		ReminderRequest reminderRequest = new ReminderRequest();
		reminderRequest.setName("Test");
		reminderRequest.setDescription("Test Description");
		reminderRequest.setStatus("NOTDONE");
		reminderRequest.setId(1);
		reminderRequest.setDueDateString("2017-04-13");
		return reminderRequest;
	}

}
