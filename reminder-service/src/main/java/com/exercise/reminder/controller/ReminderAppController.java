package com.exercise.reminder.controller;

import java.text.ParseException;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.reminder.entity.ManageReminderResponse;
import com.exercise.reminder.entity.ReminderRequest;
import com.exercise.reminder.entity.ReminderResponse;
import com.exercise.reminder.service.ReminderService;

// TODO: Auto-generated Javadoc
/**
 * The Class ReminderAppController.
 * @author Vinay Vaidya
 */
@RestController
@RequestMapping("/reminders")
public class ReminderAppController {

	/** The reminder service. */
	@Autowired
	private ReminderService reminderService;

	/**
	 * Gets the reminder on the basis of name.
	 *
	 * @param dueDate the due date
	 * @param status the status
	 * @param name the name
	 * @return the reminder
	 * @throws ParseException the parse exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{name}")
	public ResponseEntity<ReminderResponse> getReminder(@QueryParam("dueDate") String dueDate,
			@QueryParam("status") String status, @PathVariable String name) {
		return reminderService.getReminder(dueDate, status, name);
	}

	/**
	 * Adds the reminder.
	 *
	 * @param reminder the reminder
	 * @return the response entity
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/")
	public ResponseEntity<ManageReminderResponse> addReminder(@RequestBody ReminderRequest reminder) {
		return reminderService.addReminder(reminder);
	}

	/**
	 * Update reminder.
	 *
	 * @param reminder the reminder
	 * @return the response entity
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/")
	public ResponseEntity<ManageReminderResponse> updateReminder(@RequestBody ReminderRequest reminder) {
		return reminderService.updateReminder(reminder);
	}

	/**
	 * Delete reminder.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<ManageReminderResponse> deleteReminder(@PathVariable long id) {
		return reminderService.deleteReminder(id);
	}

	/*
	 * @RequestMapping(method=RequestMethod.GET, value="/{id}") public
	 * ResponseEntity<ReminderResponse> getReminderById(@PathVariable long id) {
	 * //add validation ResponseEntity<ReminderResponse> message =
	 * reminderService.getReminderById(id); return message; }
	 */

}
