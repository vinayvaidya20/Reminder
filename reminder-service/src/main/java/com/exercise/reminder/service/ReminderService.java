package com.exercise.reminder.service;

import java.util.List;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.exercise.reminder.constants.ReminderConstants;
import com.exercise.reminder.entity.ManageReminderResponse;
import com.exercise.reminder.entity.ReminderRequest;
import com.exercise.reminder.entity.ReminderResponse;
import com.exercise.reminder.exception.ReminderException;
import com.exercise.reminder.util.DateUtil;
import com.exercise.reminder.util.PropertiesLoader;
import com.exercise.reminder.validator.ReminderValidator;
import com.exercise.reminder.validator.impl.AddReminderValidateImpl;
import com.exercise.reminder.validator.impl.DeleteReminderValidateImpl;
import com.exercise.reminder.validator.impl.UpdateReminderValidateImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class ReminderService.
 * @author Vinay Vaidya
 */
@Service
public class ReminderService {

	/** The rest template. */
	@Autowired
	private RestTemplate restTemplate;


	/**
	 * Gets the reminder from the CRUD service.
	 *
	 * @param dueDate the due date
	 * @param status the status
	 * @param name the name
	 * @return the reminder
	 * 
	 */
	public ResponseEntity<ReminderResponse> getReminder(String dueDate, String status, String name) {
		ReminderResponse reminderResponse = new ReminderResponse();
		ResponseEntity<ReminderResponse> entity = null;
		HttpStatus httpStatus = HttpStatus.OK;
		try {

			String fetchAllReminderURL = PropertiesLoader.getProperty(ReminderConstants.CRUD_ENDPOINT) + name;
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
			httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());

			HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
			entity = restTemplate.exchange(fetchAllReminderURL, HttpMethod.GET, httpEntity, ReminderResponse.class);

			if (ReminderConstants.ORM_SUCCESS_CODE.equals(entity.getBody().getCode())) {

				applyFilter(dueDate, status, reminderResponse, entity);

			} else if (ReminderConstants.ORM_FAILURE_CODE.equals(entity.getBody().getCode())) {
				reminderResponse.setCode(ReminderConstants.REMINDER_FAILURE_CODE);
				reminderResponse.setType(ReminderConstants.REMINDER_FAILURE_TYPE);
				reminderResponse.setMessage(ReminderConstants.GET_REMINDER_FAILURE_MESSAGE);
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			}

		} catch (RestClientException e) {
			reminderResponse.setCode(ReminderConstants.REMINDER_FAILURE_CODE);
			reminderResponse.setType(ReminderConstants.REMINDER_FAILURE_TYPE);
			reminderResponse.setMessage(ReminderConstants.GET_REMINDER_FAILURE_MESSAGE);
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>(reminderResponse, httpStatus);

	}

	/**
	 * Apply filter logic on the basis of due date and status.
	 *
	 * @param dueDate the due date
	 * @param status the status
	 * @param reminderResponse the reminder response
	 * @param entity the entity
	 */
	private void applyFilter(String dueDate, String status, ReminderResponse reminderResponse,
			ResponseEntity<ReminderResponse> entity) {
		List<ReminderRequest> reminder = entity.getBody().getReminderList();

		if (CollectionUtils.isNotEmpty(reminder)) {

			List<ReminderRequest> reminderList = reminder;
			
			if (StringUtils.isNotEmpty(dueDate)) {

				reminderList = reminder.stream().filter(r -> (DateUtil.dateConvertor(r.getDueDate()).equals(dueDate)))
						.collect(Collectors.toList());
			}
			if (StringUtils.isNotEmpty(status)) {
				reminderList = reminderList.stream().filter(r -> (r.getStatus().equals(status)))
						.collect(Collectors.toList());
			}

			reminderResponse.setMessage(ReminderConstants.NO_PENDING_REMINDER_MESSAGE);
			
			if (CollectionUtils.isNotEmpty(reminderList)) {

				for (ReminderRequest reminderRequest : reminderList) {

					reminderRequest.setDueDateString(reminderRequest.getDueDate() != null
							? DateUtil.dateConvertor(reminderRequest.getDueDate()) : null);
					reminderRequest.setDueDate(null);

				}
				reminderResponse.setReminderList(reminderList);
				reminderResponse.setMessage(ReminderConstants.PENDING_REMINDER_MESSAGE);
			}

			reminderResponse.setCode(ReminderConstants.REMINDER_SUCCESS_CODE);
			reminderResponse.setType(ReminderConstants.REMINDER_SUCCESS_TYPE);

		} else {
			reminderResponse.setCode(ReminderConstants.REMINDER_SUCCESS_CODE);
			reminderResponse.setType(ReminderConstants.REMINDER_SUCCESS_TYPE);
			reminderResponse.setMessage(ReminderConstants.NO_PENDING_REMINDER_MESSAGE);
		}
	}

	/**
	 * Adds the reminder.
	 *
	 * @param reminder the reminder
	 * @return the response entity
	 */
	public ResponseEntity<ManageReminderResponse> addReminder(ReminderRequest reminder) {
		ManageReminderResponse manageReminderResponse = new ManageReminderResponse();
		ResponseEntity<ManageReminderResponse> entity = null;
		HttpStatus status = HttpStatus.CREATED;
		boolean validationStatus = false;

		ReminderValidator addReminderValidator = new AddReminderValidateImpl();
		try {
			validationStatus = addReminderValidator.validate(reminder);
		} catch (ReminderException e1) {

			manageReminderResponse.setCode(ReminderConstants.REMINDER_VALIDATION_FAILURE_CODE);
			manageReminderResponse.setType(ReminderConstants.REMINDER_VALIDATION_FAILURE_TYPE);
			manageReminderResponse.setMessage(e1.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}

		if (validationStatus) {

			try {
				String fetchAllReminderURL = PropertiesLoader.getProperty(ReminderConstants.CRUD_ENDPOINT);
				HttpHeaders httpHeaders = new HttpHeaders();
				httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
				httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());

				HttpEntity<Object> httpEntity = new HttpEntity<>(reminder, httpHeaders);
				entity = restTemplate.exchange(fetchAllReminderURL, HttpMethod.POST, httpEntity,
						ManageReminderResponse.class);
				if (ReminderConstants.ORM_SUCCESS_CODE.equals(entity.getBody().getCode())) {
					manageReminderResponse.setCode(ReminderConstants.REMINDER_CREATED_CODE);
					manageReminderResponse.setType(ReminderConstants.REMINDER_CREATED_TYPE);
					manageReminderResponse.setMessage(ReminderConstants.ADD_REMINDER_SUCCESS_MESSAGE);
					Link link = linkTo(ReminderService.class).slash(ReminderConstants.REMINDERS).slash(reminder.getName()).withSelfRel();
					
					manageReminderResponse.addLink(link.getHref(), ReminderConstants.SELF);

				} else if (ReminderConstants.ORM_FAILURE_CODE.equals(entity.getBody().getCode())) {
					manageReminderResponse.setCode(ReminderConstants.REMINDER_FAILURE_CODE);
					manageReminderResponse.setType(ReminderConstants.REMINDER_FAILURE_TYPE);
					manageReminderResponse.setMessage(ReminderConstants.ADD_REMINDER_FAILURE_MESSAGE);
					status = HttpStatus.INTERNAL_SERVER_ERROR;
				}

			} catch (RestClientException e) {
				manageReminderResponse.setCode(ReminderConstants.REMINDER_FAILURE_CODE);
				manageReminderResponse.setType(ReminderConstants.REMINDER_FAILURE_TYPE);
				manageReminderResponse.setMessage(ReminderConstants.ADD_REMINDER_FAILURE_MESSAGE);
				status = HttpStatus.INTERNAL_SERVER_ERROR;

			}
		}
		return new ResponseEntity<>(manageReminderResponse, status);
	}

	/**
	 * Update reminder.
	 *
	 * @param reminder the reminder
	 * @return the response entity
	 */
	public ResponseEntity<ManageReminderResponse> updateReminder(ReminderRequest reminder) {
		ManageReminderResponse manageReminderResponse = new ManageReminderResponse();
		ResponseEntity<ManageReminderResponse> entity = null;
		HttpStatus status = HttpStatus.OK;
		boolean validationStatus = false;

		ReminderValidator updateReminderValidator = new UpdateReminderValidateImpl();
		try {
			validationStatus = updateReminderValidator.validate(reminder);
		} catch (ReminderException e1) {

			manageReminderResponse.setCode(ReminderConstants.REMINDER_VALIDATION_FAILURE_CODE);
			manageReminderResponse.setType(ReminderConstants.REMINDER_VALIDATION_FAILURE_TYPE);
			manageReminderResponse.setMessage(e1.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}

		if (validationStatus) {
			try {
				String fetchAllReminderURL = PropertiesLoader.getProperty(ReminderConstants.CRUD_ENDPOINT);
				HttpHeaders httpHeaders = new HttpHeaders();
				httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
				httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());

				HttpEntity<Object> httpEntity = new HttpEntity<>(reminder, httpHeaders);
				entity = restTemplate.exchange(fetchAllReminderURL, HttpMethod.PUT, httpEntity,
						ManageReminderResponse.class);

				if (ReminderConstants.ORM_SUCCESS_CODE.equals(entity.getBody().getCode())) {
					manageReminderResponse.setCode(ReminderConstants.REMINDER_SUCCESS_CODE);
					manageReminderResponse.setType(ReminderConstants.REMINDER_SUCCESS_TYPE);
					manageReminderResponse.setMessage(ReminderConstants.UPDATE_REMINDER_SUCCESS_MESSAGE);
					Link link = linkTo(ReminderService.class).slash("reminders").slash(reminder.getName()).withSelfRel();
					manageReminderResponse.addLink(link.getHref(), "self");

				} else if (ReminderConstants.ORM_FAILURE_CODE.equals(entity.getBody().getCode())) {
					manageReminderResponse.setCode(ReminderConstants.REMINDER_FAILURE_CODE);
					manageReminderResponse.setType(ReminderConstants.REMINDER_FAILURE_TYPE);
					manageReminderResponse.setMessage(ReminderConstants.UPDATE_REMINDER_FAILURE_MESSAGE);
					status = HttpStatus.INTERNAL_SERVER_ERROR;
				}
				else if (ReminderConstants.ORM_DATA_FAILURE_CODE.equals(entity.getBody().getCode())) {
					manageReminderResponse.setCode(ReminderConstants.REMINDER_VALIDATION_FAILURE_CODE);
					manageReminderResponse.setType(ReminderConstants.REMINDER_FAILURE_TYPE);
					manageReminderResponse.setMessage(ReminderConstants.UPDATE_REMINDER_DATA_FAILURE_MESSAGE);
					status = HttpStatus.BAD_REQUEST;
				}

			} catch (RestClientException e) {
				manageReminderResponse.setCode(ReminderConstants.REMINDER_FAILURE_CODE);
				manageReminderResponse.setType(ReminderConstants.REMINDER_FAILURE_TYPE);
				manageReminderResponse.setMessage(ReminderConstants.UPDATE_REMINDER_FAILURE_MESSAGE);
				status = HttpStatus.INTERNAL_SERVER_ERROR;

			}

		}
		return new ResponseEntity<>(manageReminderResponse, status);
	}

	/**
	 * Delete reminder.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	public ResponseEntity<ManageReminderResponse> deleteReminder(long id) {
		HttpStatus status = HttpStatus.OK;
		ManageReminderResponse manageReminderResponse = new ManageReminderResponse();
		ResponseEntity<ManageReminderResponse> entity = null;
		boolean validationStatus = false;

		ReminderValidator deleteReminderValidator = new DeleteReminderValidateImpl();
		try {
			validationStatus = deleteReminderValidator.validate(id);
		} catch (ReminderException e1) {

			manageReminderResponse.setCode(ReminderConstants.REMINDER_VALIDATION_FAILURE_CODE);
			manageReminderResponse.setType(ReminderConstants.REMINDER_VALIDATION_FAILURE_TYPE);
			manageReminderResponse.setMessage(e1.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}

		if (validationStatus)

		{

			try {
				String fetchAllReminderURL = PropertiesLoader.getProperty(ReminderConstants.CRUD_ENDPOINT) + id;
				HttpHeaders httpHeaders = new HttpHeaders();
				httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
				httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());

				HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
				entity = restTemplate.exchange(fetchAllReminderURL, HttpMethod.DELETE, httpEntity,
						ManageReminderResponse.class);

				if (ReminderConstants.ORM_SUCCESS_CODE.equals(entity.getBody().getCode())) {
					manageReminderResponse.setCode(ReminderConstants.REMINDER_SUCCESS_CODE);
					manageReminderResponse.setType(ReminderConstants.REMINDER_SUCCESS_TYPE);
					manageReminderResponse.setMessage(ReminderConstants.DELETE_REMINDER_SUCCESS_MESSAGE);

				} else if (ReminderConstants.ORM_FAILURE_CODE.equals(entity.getBody().getCode())) {
					manageReminderResponse.setCode(ReminderConstants.REMINDER_FAILURE_CODE);
					manageReminderResponse.setType(ReminderConstants.REMINDER_FAILURE_TYPE);
					manageReminderResponse.setMessage(ReminderConstants.DELETE_REMINDER_FAILURE_MESSAGE);
					status = HttpStatus.INTERNAL_SERVER_ERROR;
				}
				else if (ReminderConstants.ORM_DATA_FAILURE_CODE.equals(entity.getBody().getCode())) {
					manageReminderResponse.setCode(ReminderConstants.REMINDER_VALIDATION_FAILURE_CODE);
					manageReminderResponse.setType(ReminderConstants.REMINDER_FAILURE_TYPE);
					manageReminderResponse.setMessage(ReminderConstants.DELETE_REMINDER_DATA_FAILURE_MESSAGE);
					status = HttpStatus.BAD_REQUEST;
				}
				

			} catch (RestClientException e) {
				manageReminderResponse.setCode(ReminderConstants.REMINDER_FAILURE_CODE);
				manageReminderResponse.setType(ReminderConstants.REMINDER_FAILURE_TYPE);
				manageReminderResponse.setMessage(ReminderConstants.DELETE_REMINDER_FAILURE_MESSAGE);
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}

		}
		return new ResponseEntity<>(manageReminderResponse, status);
	}

	/**
	 * Gets the reminder by id.
	 *
	 * @param id the id
	 * @return the reminder by id
	 */
	public ResponseEntity<ReminderResponse> getReminderById(long id) {
		HttpStatus status = HttpStatus.OK;
		ReminderResponse reminderResponse = new ReminderResponse();
		ResponseEntity<ReminderResponse> entity = null;

		try {
			String fetchAllReminderURL = PropertiesLoader.getProperty(ReminderConstants.CRUD_ENDPOINT) + id;
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
			httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());

			HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
			entity = restTemplate.exchange(fetchAllReminderURL, HttpMethod.GET, httpEntity, ReminderResponse.class);

			if (ReminderConstants.ORM_SUCCESS_CODE.equals(entity.getBody().getCode())) {
				reminderResponse.setCode(ReminderConstants.REMINDER_SUCCESS_CODE);
				reminderResponse.setType(ReminderConstants.REMINDER_SUCCESS_TYPE);

				if (entity.getBody().getReminderList() == null) {
					reminderResponse.setMessage(ReminderConstants.GET_REMINDER_BYID_FAILURE_MESSAGE + id);
				} else if (entity.getBody().getReminderList().size() == 1) {
					reminderResponse.setMessage(ReminderConstants.GET_REMINDER_BYID_SUCCESS_MESSAGE);
				}

			} else if (ReminderConstants.ORM_FAILURE_CODE.equals(entity.getBody().getCode())) {
				reminderResponse.setCode(ReminderConstants.REMINDER_FAILURE_CODE);
				reminderResponse.setType(ReminderConstants.REMINDER_FAILURE_TYPE);
				reminderResponse.setMessage(ReminderConstants.GET_REMINDER_FAILURE_MESSAGE);
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}

		} catch (RestClientException e) {
			reminderResponse.setCode(ReminderConstants.REMINDER_FAILURE_CODE);
			reminderResponse.setType(ReminderConstants.REMINDER_FAILURE_TYPE);
			reminderResponse.setMessage(ReminderConstants.GET_REMINDER_FAILURE_MESSAGE);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(reminderResponse, status);
	}
}
