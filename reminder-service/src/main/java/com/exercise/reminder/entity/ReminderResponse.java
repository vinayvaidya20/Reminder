package com.exercise.reminder.entity;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

// TODO: Auto-generated Javadoc
/**
 * The Class ReminderResponse.
 * @author Vinay Vaidya
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class ReminderResponse {
	
	/** The type. */
	private String type;
	
	/** The code. */
	private String code;
	
	/** The message. */
	private String message;
	
	/** The reminder list. */
	private List<ReminderRequest> reminderList = null;
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * Gets the reminder list.
	 *
	 * @return the reminder list
	 */
	public List<ReminderRequest> getReminderList() {
		return reminderList;
	}
	
	/**
	 * Sets the reminder list.
	 *
	 * @param reminderList the new reminder list
	 */
	public void setReminderList(List<ReminderRequest> reminderList) {
		this.reminderList = reminderList;
	}

}
