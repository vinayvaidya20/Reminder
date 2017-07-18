package com.exercise.reminder.service;

import static org.junit.Assert.assertEquals;
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
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.exercise.reminder.entity.ManageReminderResponse;
import com.exercise.reminder.entity.ReminderRequest;
import com.exercise.reminder.entity.ReminderResponse;
import com.exercise.reminder.util.PropertiesLoader;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PropertiesLoader.class)
public class TestReminderService {
	@InjectMocks
	ReminderService reminderService;

	@Mock
	RestTemplate restTemplate;

	@Mock
	ControllerLinkBuilder controllerLinkBuilder;

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

		Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.<HttpMethod> any(),
				Mockito.<HttpEntity<?>> any(), Mockito.<Class<ReminderResponse>> any()))
				.thenReturn(reminderResponseEntity);

		ResponseEntity<ReminderResponse> Response = reminderService.getReminder("04/14/2017", "DONE", "Test");
		Response.getBody().getReminderList().size();
		assertEquals(1, Response.getBody().getReminderList().size());
		assertEquals(1, Response.getBody().getReminderList().get(0).getId());
	}
	
	@Test
	   public void testGetReminderException() throws ParseException{
		
	      doThrow(new RestClientException(""))
	         .when(restTemplate).exchange(Mockito.anyString(), Mockito.<HttpMethod> any(),
	 				Mockito.<HttpEntity<?>> any(), Mockito.<Class<ReminderResponse>> any());
	      ResponseEntity<ReminderResponse> Response = reminderService.getReminder("04/14/2017", "DONE", "Test");
	      assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, Response.getStatusCode());
	   }
	

	@Test
	public void testAddReminder() throws Exception {

		ReminderRequest reminderRequest = createReminderRequest3();

		ManageReminderResponse manageReminderResponse = new ManageReminderResponse();
		manageReminderResponse.setCode("500");
		manageReminderResponse.setMessage("Reminder Not Added");
		manageReminderResponse.setType("Failed");
		manageReminderResponse.addLink("http://localhost:8080/reminders/Test", "self");
		ResponseEntity<ManageReminderResponse> reminderResponseEntity = new ResponseEntity<ManageReminderResponse>(
				manageReminderResponse, HttpStatus.OK);
	
		Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.<HttpMethod> any(),
				Mockito.<HttpEntity<?>> any(), Mockito.<Class<ManageReminderResponse>> any()))
				.thenReturn(reminderResponseEntity);
	

		ResponseEntity<ManageReminderResponse> Response = reminderService.addReminder(reminderRequest);
		assertEquals("500", Response.getBody().getCode());
	}
	@Test
	public void testUpdateReminder() throws Exception {

		ReminderRequest reminderRequest = createReminderRequest3();

		ManageReminderResponse manageReminderResponse = new ManageReminderResponse();
		manageReminderResponse.setCode("500");
		manageReminderResponse.setMessage("Reminder Not Added");
		manageReminderResponse.setType("Failed");
		manageReminderResponse.addLink("http://localhost:8080/reminders/Test", "self");
		ResponseEntity<ManageReminderResponse> reminderResponseEntity = new ResponseEntity<ManageReminderResponse>(
				manageReminderResponse, HttpStatus.OK);
	
		Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.<HttpMethod> any(),
				Mockito.<HttpEntity<?>> any(), Mockito.<Class<ManageReminderResponse>> any()))
				.thenReturn(reminderResponseEntity);
	

		ResponseEntity<ManageReminderResponse> Response = reminderService.updateReminder(reminderRequest);
		assertEquals("500", Response.getBody().getCode());
	}
	
	
	@Test
	public void testDeleteReminder() throws Exception {

		ManageReminderResponse manageReminderResponse = new ManageReminderResponse();
		manageReminderResponse.setCode("500");
		manageReminderResponse.setMessage("Reminder Not Added");
		manageReminderResponse.setType("Failed");
		manageReminderResponse.addLink("http://localhost:8080/reminders/Test", "self");
		ResponseEntity<ManageReminderResponse> reminderResponseEntity = new ResponseEntity<ManageReminderResponse>(
				manageReminderResponse, HttpStatus.OK);
	
		Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.<HttpMethod> any(),
				Mockito.<HttpEntity<?>> any(), Mockito.<Class<ManageReminderResponse>> any()))
				.thenReturn(reminderResponseEntity);
	

		ResponseEntity<ManageReminderResponse> Response = reminderService.deleteReminder(1);
		assertEquals("500", Response.getBody().getCode());
	}

	private ReminderResponse createReminderResponse(List<ReminderRequest> reminderList) {
		ReminderResponse reminderResponse = new ReminderResponse();
		reminderResponse.setReminderList(reminderList);
		reminderResponse.setMessage("Reminder Success");
		reminderResponse.setType("SUCCESS");
		reminderResponse.setCode("200");
		return reminderResponse;
	}

	private ReminderRequest createReminderRequest1() throws ParseException {
		ReminderRequest reminderRequest = new ReminderRequest();
		reminderRequest.setName("Test");
		reminderRequest.setDescription("Test Description");
		reminderRequest.setStatus("DONE");
		reminderRequest.setId(1);
		reminderRequest.setDueDateString("2017-04-14");

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
		reminderRequest.setDueDateString("2017-04-14");

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date inputDate = sdf.parse("04/14/2017");
		reminderRequest.setDueDate(inputDate);

		return reminderRequest;
	}

	private ReminderRequest createReminderRequest3() throws ParseException {
		ReminderRequest reminderRequest = new ReminderRequest();
		reminderRequest.setName("Test");
		reminderRequest.setDescription("Test Description should filter out because of date");
		reminderRequest.setStatus("DONE");
		reminderRequest.setId(3);
		reminderRequest.setDueDateString("2018-04-14");

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date inputDate = sdf.parse("04/14/2018");
		reminderRequest.setDueDate(inputDate);

		return reminderRequest;
	}
	

}
