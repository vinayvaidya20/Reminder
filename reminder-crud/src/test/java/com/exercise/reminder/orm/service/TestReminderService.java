package com.exercise.reminder.orm.service;

import static org.junit.Assert.assertEquals;
import org.powermock.api.mockito.PowerMockito;


import static org.mockito.Mockito.doThrow;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.exercise.reminder.orm.entity.ManageReminderResponse;
import com.exercise.reminder.orm.entity.ReminderRequest;
import com.exercise.reminder.orm.entity.ReminderResponse;
import com.exercise.reminder.orm.repository.ReminderRepository;
import com.exercise.reminder.orm.service.impl.ReminderServiceImpl;
import org.springframework.dao.DataAccessException;
import org.springframework.web.client.RestClientException;
import static org.junit.Assert.*;



@RunWith(MockitoJUnitRunner.class)
public class TestReminderService {
	@InjectMocks
	ReminderServiceImpl reminderService;

	@Mock
	ReminderRepository reminderRepository;


	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void testGetReminder() throws Exception {

		List<ReminderRequest> reminderList = new ArrayList<ReminderRequest>();
		ReminderRequest reminderRequest1 = createReminderRequest1();
		ReminderRequest reminderRequest2 = createReminderRequest2();
		ReminderRequest reminderRequest3 = createReminderRequest3();
		reminderList.add(reminderRequest1);
		reminderList.add(reminderRequest2);
		reminderList.add(reminderRequest3);

		ReminderResponse reminderResponse = createReminderResponse(reminderList);

		ResponseEntity<ReminderResponse> reminderResponseEntity = new ResponseEntity<ReminderResponse>(reminderResponse,
				HttpStatus.OK);

		Mockito.when(reminderRepository.findAll())
				.thenReturn(reminderList);

		ResponseEntity<ReminderResponse> Response = reminderService.getAllReminders("Test");
		Response.getBody().getReminderList().size();
		assertEquals(2, Response.getBody().getReminderList().size());
		
	}
	
	@Test
	public void testAddReminder() throws Exception {

		ReminderRequest reminderRequest = createReminderRequest3();

		ManageReminderResponse manageReminderResponse = new ManageReminderResponse();
		manageReminderResponse.setCode("200");
		manageReminderResponse.setMessage("Reminder persited");
		manageReminderResponse.setType("Success");
		ResponseEntity<ManageReminderResponse> reminderResponseEntity = new ResponseEntity<ManageReminderResponse>(
				manageReminderResponse, HttpStatus.OK);
	
		//Mockito.doNothing().when(reminderRepository).save(Mockito.anyObject());
		Mockito.when(reminderRepository.save(Mockito.any(ReminderRequest.class)))
		.thenReturn(reminderRequest);
		
		ResponseEntity<ManageReminderResponse> Response = reminderService.persitReminder(reminderRequest);
		assertEquals("200", Response.getBody().getCode());
	}

		
	@Test
	public void testDeleteReminder() throws Exception {

		ReminderRequest reminderRequest = createReminderRequest3();

		ManageReminderResponse manageReminderResponse = new ManageReminderResponse();
		manageReminderResponse.setCode("200");
		manageReminderResponse.setMessage("Reminder deleted");
		manageReminderResponse.setType("Success");
		ResponseEntity<ManageReminderResponse> reminderResponseEntity = new ResponseEntity<ManageReminderResponse>(
				manageReminderResponse, HttpStatus.OK);
	
		Mockito.doNothing().when(reminderRepository).delete(Mockito.any(ReminderRequest.class));
		
		ResponseEntity<ManageReminderResponse> Response = reminderService.deleteReminder(1);
		assertEquals("200", Response.getBody().getCode());
	}

	private ReminderResponse createReminderResponse(List<ReminderRequest> reminderList) {
		ReminderResponse reminderResponse = new ReminderResponse();
		reminderResponse.setReminderList(reminderList);
		reminderResponse.setMessage("Reminder Success");
		reminderResponse.setType("SUCCESS");
		reminderResponse.setCode("200");
		return reminderResponse;
	}

	/*
	 * @Test public void addReminder() throws Exception {
	 * 
	 * ManageReminderResponse manageReminderResponse =
	 * createManageReminderResponse(); manageReminderResponse.setMessage(
	 * "Reminder Added"); ResponseEntity<ManageReminderResponse>
	 * reminderResponseEntity = new ResponseEntity<ManageReminderResponse>(
	 * manageReminderResponse, HttpStatus.OK);
	 * 
	 * ReminderRequest reminderRequest = createReminderRequest();
	 * 
	 * Mockito.when(reminderService.addReminder(Mockito.any())).thenReturn(
	 * reminderResponseEntity);
	 * 
	 * ResponseEntity<ManageReminderResponse> Response =
	 * reminderAppController.addReminder(reminderRequest); assertEquals(
	 * "Reminder Added", Response.getBody().getMessage()); }
	 */

	/*
	 * @Test public void updateReminder() throws Exception {
	 * 
	 * ManageReminderResponse manageReminderResponse =
	 * createManageReminderResponse(); manageReminderResponse.setMessage(
	 * "Reminder Updated");
	 * 
	 * ResponseEntity<ManageReminderResponse> reminderResponseEntity = new
	 * ResponseEntity<ManageReminderResponse>( manageReminderResponse,
	 * HttpStatus.OK);
	 * 
	 * ReminderRequest reminderRequest = createReminderRequest();
	 * 
	 * Mockito.when(reminderService.updateReminder(Mockito.any())).thenReturn(
	 * reminderResponseEntity);
	 * 
	 * ResponseEntity<ManageReminderResponse> Response =
	 * reminderAppController.updateReminder(reminderRequest); assertEquals(
	 * "Reminder Updated", Response.getBody().getMessage()); }
	 * 
	 * @Test public void deleteReminder() throws Exception {
	 * 
	 * ManageReminderResponse manageReminderResponse =
	 * createManageReminderResponse(); manageReminderResponse.setMessage(
	 * "Reminder deleted");
	 * 
	 * ResponseEntity<ManageReminderResponse> reminderResponseEntity = new
	 * ResponseEntity<ManageReminderResponse>( manageReminderResponse,
	 * HttpStatus.OK);
	 * Mockito.when(reminderService.deleteReminder(Mockito.anyLong())).
	 * thenReturn(reminderResponseEntity);
	 * 
	 * ResponseEntity<ManageReminderResponse> Response =
	 * reminderAppController.deleteReminder(1); assertEquals("Reminder deleted",
	 * Response.getBody().getMessage()); }
	 */

	private ManageReminderResponse createManageReminderResponse() {
		ManageReminderResponse manageReminderResponse = new ManageReminderResponse();
		manageReminderResponse.setCode("200");
		manageReminderResponse.setType("Success");
		return manageReminderResponse;
	}

	private ReminderRequest createReminderRequest1() throws ParseException {
		ReminderRequest reminderRequest = new ReminderRequest();
		reminderRequest.setName("Test");
		reminderRequest.setDescription("Test Description");
		reminderRequest.setStatus("DONE");
		reminderRequest.setId(1);

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date inputDate = sdf.parse("04/14/2017");
		reminderRequest.setDueDate(inputDate);

		return reminderRequest;
	}

	private ReminderRequest createReminderRequest2() throws ParseException {
		ReminderRequest reminderRequest = new ReminderRequest();
		reminderRequest.setName("Test");
		reminderRequest.setDescription("Test Description should filter out because of status");
		reminderRequest.setStatus("NOTDONE");
		reminderRequest.setId(2);

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date inputDate = sdf.parse("04/14/2017");
		reminderRequest.setDueDate(inputDate);

		return reminderRequest;
	}

	private ReminderRequest createReminderRequest3() throws ParseException {
		ReminderRequest reminderRequest = new ReminderRequest();
		reminderRequest.setName("NotTest");
		reminderRequest.setDescription("Test Description should filter out because of date");
		reminderRequest.setStatus("DONE");
		reminderRequest.setId(3);

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date inputDate = sdf.parse("04/14/2018");
		reminderRequest.setDueDate(inputDate);

		return reminderRequest;
	}
	
	/*
	 * @Test(expected = Exception.class) public void
	 * shouldReturnErrorAccountSummary() {
	 * accountSummaryResponse.setType("error");
	 * Mockito.doReturn(accountSummaryResponse).when(
	 * accountManagementAggregator)
	 * .getAccountSummaryResponse(Mockito.any(AccountSummaryRequest.class),
	 * Mockito.any(), Mockito.any()); HttpServletResponse httpServletResponse =
	 * null; HttpServletRequest httpServletRequest = null;
	 * accountSummaryResponse = acc.getAcctSummaryResponse("US",
	 * "1223343243243", "12312312312", "123123213",
	 * "9b22ccc6-a58b-46c5-bf7a-2ab78f6126b6", httpServletResponse,
	 * httpServletRequest);
	 * 
	 * }
	 */

}
