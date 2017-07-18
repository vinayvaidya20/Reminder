package com.exercise.reminder.entity;

import java.text.ParseException;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

// TODO: Auto-generated Javadoc
/**
 * The Class ReminderRequest.
 * @author Vinay Vaidya
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)

public class ReminderRequest {


	/** The Id. */
	private long Id;
	
	/** The name. */
	private String name;
	
	/** The description. */
	private String description;
	
	/** The due date. */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy", timezone="America/Chicago")
	private Date dueDate;
	
	/** The status. */
	private String status;
	
	/** The due date string. */
	private String dueDateString;

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Gets the due date.
	 *
	 * @return the due date
	 */
	public Date getDueDate() {
		return dueDate;
	}
	
	/**
	 * Sets the due date.
	 *
	 * @param dueDate the new due date
	 * @throws ParseException the parse exception
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
		/**
		 * Gets the due date string.
		 *
		 * @return the due date string
		 */
		public String getDueDateString() {
			return dueDateString;
		}

		/**
		 * Sets the due date string.
		 *
		 * @param dueDateString the new due date string
		 */
		public void setDueDateString(String dueDateString) {
			this.dueDateString = dueDateString;
		}

		/**
		 * Gets the id.
		 *
		 * @return the id
		 */
		public long getId() {
			return Id;
		}

		/**
		 * Sets the id.
		 *
		 * @param id the new id
		 */
		public void setId(long id) {
			Id = id;
		}


}
