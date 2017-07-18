package com.exercise.reminder.orm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.reminder.orm.entity.ManageReminderResponse;
import com.exercise.reminder.orm.entity.ReminderRequest;
import com.exercise.reminder.orm.entity.ReminderResponse;
import com.exercise.reminder.orm.exception.ReminderException;
import com.exercise.reminder.orm.service.ReminderService;

// TODO: Auto-generated Javadoc
/**
 * The Class ReminderAppController.
 * @author Vinay Vaidya
 */
@RestController
@RequestMapping("/private/reminders")
public class ReminderAppController {

	/** The reminder service. */
	@Autowired
	private ReminderService reminderService;

	/**
	 * Gets the all reminder.
	 *
	 * @param name the name
	 * @return the all reminder
	 * @throws ReminderException the reminder exception
	 */
	@RequestMapping("/{name}")
	public ResponseEntity<ReminderResponse> getAllReminder(@PathVariable String name) {
		return reminderService.getAllReminders(name);
	}

	/**
	 * Adds the reminder.
	 *
	 * @param reminder the reminder
	 * @return the response entity
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/")
	public ResponseEntity<ManageReminderResponse> addReminder(@RequestBody ReminderRequest reminder) {
		// add validation
		return reminderService.persitReminder(reminder);
	}

	/**
	 * Update reminder.
	 *
	 * @param reminder the reminder
	 * @return the response entity
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/")
	public ResponseEntity<ManageReminderResponse> updateReminder(@RequestBody ReminderRequest reminder) {
		// add validation
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
		// add validation
		return reminderService.deleteReminder(id);

	}

	/*
	 * @RequestMapping(method = RequestMethod.GET, value = "/{id}") public
	 * ResponseEntity<ReminderResponse> getRemiderById(@PathVariable long id) {
	 * // add validation return reminderService.getReminderById(id);
	 * 
	 * }
	 */
}
