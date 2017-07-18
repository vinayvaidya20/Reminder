package com.exercise.reminder.orm.service;

import org.springframework.http.ResponseEntity;

import com.exercise.reminder.orm.entity.ManageReminderResponse;
import com.exercise.reminder.orm.entity.ReminderRequest;
import com.exercise.reminder.orm.entity.ReminderResponse;

// TODO: Auto-generated Javadoc
/**
 * The Interface ReminderService.
 * @author Vinay Vaidya
 */
public interface ReminderService {

	/**
	 * Gets the all reminders.
	 *
	 * @param name the name
	 * @return the all reminders
	 */
	ResponseEntity<ReminderResponse> getAllReminders(String name);
	
	/**
	 * Gets the reminder by id.
	 *
	 * @param id the id
	 * @return the reminder by id
	 */
	ResponseEntity<ReminderResponse> getReminderById(long id);

	/**
	 * Persit reminder.
	 *
	 * @param reminder the reminder
	 * @return the response entity
	 */
	ResponseEntity<ManageReminderResponse> persitReminder(ReminderRequest reminder);
	
	/**
	 * Update reminder.
	 *
	 * @param reminder the reminder
	 * @return the response entity
	 */
	ResponseEntity<ManageReminderResponse> updateReminder(ReminderRequest reminder);

	/**
	 * Delete reminder.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	ResponseEntity<ManageReminderResponse> deleteReminder(long id);
	
}
