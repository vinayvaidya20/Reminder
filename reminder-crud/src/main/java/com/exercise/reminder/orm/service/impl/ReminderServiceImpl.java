package com.exercise.reminder.orm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exercise.reminder.orm.constants.ReminderOrmConstants;
import com.exercise.reminder.orm.entity.ManageReminderResponse;
import com.exercise.reminder.orm.entity.ReminderRequest;
import com.exercise.reminder.orm.entity.ReminderResponse;
import com.exercise.reminder.orm.repository.ReminderRepository;
import com.exercise.reminder.orm.service.ReminderService;

import org.apache.commons.collections4.CollectionUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class ReminderServiceImpl.
 * @author Vinay Vaidya
 */
@Transactional
@Service
public class ReminderServiceImpl implements ReminderService {

	/** The reminder repository. */
	@Autowired
	private ReminderRepository reminderRepository;

	/** The status. */
	private HttpStatus status = HttpStatus.OK;

	/** The Constant logger. */
	static final Logger logger = Logger.getLogger(ReminderServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exercise.reminder.orm.service.ReminderService#getAllReminders(java.
	 * lang.String)
	 */
	@Override
	public ResponseEntity<ReminderResponse> getAllReminders(String name) {
		ReminderResponse reminderResponse = new ReminderResponse();
		List<ReminderRequest> reminderList = new ArrayList<>();
		try {

			List<ReminderRequest> reminder = (List<ReminderRequest>) reminderRepository.findAll();

			if (CollectionUtils.isNotEmpty(reminder))

				reminderList = reminder.stream().filter(r -> r.getName().equals(name)).collect(Collectors.toList());

			reminderResponse.setReminderList(reminderList);
			reminderResponse.setCode(ReminderOrmConstants.ORM_SUCCESS_CODE);
			reminderResponse.setType(ReminderOrmConstants.ORM_SUCCESS_TYPE);

		} catch (DataAccessException e) {
			reminderResponse.setCode(ReminderOrmConstants.ORM_FAILURE_CODE);
			reminderResponse.setType(ReminderOrmConstants.ORM_FAILURE_TYPE);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.debug(e);
		} catch (Exception e) {
			reminderResponse.setCode(ReminderOrmConstants.ORM_FAILURE_CODE);
			reminderResponse.setType(ReminderOrmConstants.ORM_FAILURE_TYPE);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.debug(e);
		}
		return new ResponseEntity<>(reminderResponse, status);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exercise.reminder.orm.service.ReminderService#persitReminder(com.
	 * exercise.reminder.orm.entity.ReminderRequest)
	 */
	@Override
	public ResponseEntity<ManageReminderResponse> persitReminder(ReminderRequest reminder) {

		ManageReminderResponse manageReminderResponse = new ManageReminderResponse();
		try {
			reminderRepository.save(reminder);
			manageReminderResponse.setCode(ReminderOrmConstants.ORM_SUCCESS_CODE);
			manageReminderResponse.setType(ReminderOrmConstants.ORM_SUCCESS_TYPE);
		} catch (DataAccessException e) {
			manageReminderResponse.setCode(ReminderOrmConstants.ORM_FAILURE_CODE);
			manageReminderResponse.setType(ReminderOrmConstants.ORM_FAILURE_TYPE);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.debug(e);
		} catch (Exception e) {
			manageReminderResponse.setCode(ReminderOrmConstants.ORM_FAILURE_CODE);
			manageReminderResponse.setType(ReminderOrmConstants.ORM_FAILURE_TYPE);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.debug(e);
		}

		return new ResponseEntity<>(manageReminderResponse, status);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exercise.reminder.orm.service.ReminderService#deleteReminder(long)
	 */
	@Override
	public ResponseEntity<ManageReminderResponse> deleteReminder(long id) {
		ManageReminderResponse manageReminderResponse = new ManageReminderResponse();
		try {
			if(reminderRepository.findOne(id) != null){
			reminderRepository.delete(id);
			manageReminderResponse.setCode(ReminderOrmConstants.ORM_SUCCESS_CODE);
			manageReminderResponse.setType(ReminderOrmConstants.ORM_SUCCESS_TYPE);
			}
			else
			{
				manageReminderResponse.setCode(ReminderOrmConstants.ORM_DATA_FAILURE_CODE);
				manageReminderResponse.setType(ReminderOrmConstants.ORM_SUCCESS_TYPE);
			}

		} catch (DataAccessException e) {
			manageReminderResponse.setCode(ReminderOrmConstants.ORM_FAILURE_CODE);
			manageReminderResponse.setType(ReminderOrmConstants.ORM_FAILURE_TYPE);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.debug(e);
		} catch (Exception e) {
			manageReminderResponse.setCode(ReminderOrmConstants.ORM_FAILURE_CODE);
			manageReminderResponse.setType(ReminderOrmConstants.ORM_FAILURE_TYPE);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.debug(e);
		}
		return new ResponseEntity<>(manageReminderResponse, status);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exercise.reminder.orm.service.ReminderService#updateReminder(com.
	 * exercise.reminder.orm.entity.ReminderRequest)
	 */
	@Override
	public ResponseEntity<ManageReminderResponse> updateReminder(ReminderRequest reminder) {

		ManageReminderResponse manageReminderResponse = new ManageReminderResponse();
		try {
			int i = reminderRepository.updateReminder(reminder.getDescription(), reminder.getId(), reminder.getName(),
					reminder.getStatus(), reminder.getDueDate());
			
			if(i ==1)
			{
				manageReminderResponse.setCode(ReminderOrmConstants.ORM_SUCCESS_CODE);
				manageReminderResponse.setType(ReminderOrmConstants.ORM_SUCCESS_TYPE);	
			}else{
				manageReminderResponse.setCode(ReminderOrmConstants.ORM_DATA_FAILURE_CODE);
				manageReminderResponse.setType(ReminderOrmConstants.ORM_SUCCESS_TYPE);
			}

		} catch (DataAccessException e) {
			manageReminderResponse.setCode(ReminderOrmConstants.ORM_FAILURE_CODE);
			manageReminderResponse.setType(ReminderOrmConstants.ORM_FAILURE_TYPE);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.debug(e);
		} catch (Exception e) {
			manageReminderResponse.setCode(ReminderOrmConstants.ORM_FAILURE_CODE);
			manageReminderResponse.setType(ReminderOrmConstants.ORM_FAILURE_TYPE);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.debug(e);
		}

		return new ResponseEntity<>(manageReminderResponse, status);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exercise.reminder.orm.service.ReminderService#getReminderById(long)
	 */
	@Override
	public ResponseEntity<ReminderResponse> getReminderById(long id) {
		ReminderResponse reminderResponse = new ReminderResponse();
		try {
			reminderResponse.setReminderList(reminderRepository.getReminderById(id));
			reminderResponse.setCode(ReminderOrmConstants.ORM_SUCCESS_CODE);
			reminderResponse.setType(ReminderOrmConstants.ORM_SUCCESS_TYPE);
		} catch (DataAccessException e) {
			reminderResponse.setCode(ReminderOrmConstants.ORM_FAILURE_CODE);
			reminderResponse.setType(ReminderOrmConstants.ORM_FAILURE_TYPE);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.debug(e);
		} catch (Exception e) {
			reminderResponse.setCode(ReminderOrmConstants.ORM_FAILURE_CODE);
			reminderResponse.setType(ReminderOrmConstants.ORM_FAILURE_TYPE);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.debug(e);
		}
		return new ResponseEntity<>(reminderResponse, status);
	}

}
