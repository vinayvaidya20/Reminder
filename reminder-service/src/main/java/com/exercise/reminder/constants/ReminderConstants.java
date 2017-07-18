package com.exercise.reminder.constants;

// TODO: Auto-generated Javadoc
/**
 * The Interface ReminderConstants.
 * @author Vinay Vaidya
 */
public class ReminderConstants {

	/** The Constant CRUD_ENDPOINT. */
	public static final String CRUD_ENDPOINT = "crudService.baseEndpoint";

	/** The Constant REMINDER_SUCCESS_CODE. */
	public static final String REMINDER_SUCCESS_CODE = "200";

	/** The Constant REMINDER_SUCCESS_TYPE. */
	public static final String REMINDER_SUCCESS_TYPE = "Success";
	
	/** The Constant REMINDER_CREATED_CODE. */
	public static final String REMINDER_CREATED_CODE = "201";

	/** The Constant REMINDER_SUCCESS_TYPE. */
	public static final String REMINDER_CREATED_TYPE = "Created";

	/** The Constant REMINDER_FAILURE_CODE. */
	public static final String REMINDER_FAILURE_CODE = "500";

	/** The Constant REMINDER_FAILURE_TYPE. */
	public static final String REMINDER_FAILURE_TYPE = "Failed";

	/** The Constant PENDING_REMINDER_MESSAGE. */
	public static final String PENDING_REMINDER_MESSAGE = "Following are your Reminders.";

	/** The Constant NO_PENDING_REMINDER_MESSAGE. */
	public static final String NO_PENDING_REMINDER_MESSAGE = "You don't have any Reminders.";

	/** The Constant DELETE_REMINDER_SUCCESS_MESSAGE. */
	public static final String DELETE_REMINDER_SUCCESS_MESSAGE = "Reminder deleted successfully.";

	/** The Constant DELETE_REMINDER_FAILURE_MESSAGE. */
	public static final String DELETE_REMINDER_FAILURE_MESSAGE = "Reminder could not be deleted.";

	/** The Constant UPDATE_REMINDER_SUCCESS_MESSAGE. */
	public static final String UPDATE_REMINDER_SUCCESS_MESSAGE = "Reminder updated successfully.";

	/** The Constant UPDATE_REMINDER_FAILURE_MESSAGE. */
	public static final String UPDATE_REMINDER_FAILURE_MESSAGE = "Reminder could not be updated.";

	/** The Constant GET_REMINDER_FAILURE_MESSAGE. */
	public static final String GET_REMINDER_FAILURE_MESSAGE = "Reminder could not be retrieved.";

	/** The Constant ADD_REMINDER_SUCCESS_MESSAGE. */
	public static final String ADD_REMINDER_SUCCESS_MESSAGE = "Reminder addition successful.";

	/** The Constant ADD_REMINDER_FAILURE_MESSAGE. */
	public static final String ADD_REMINDER_FAILURE_MESSAGE = "Reminder could not be added.";

	/** The Constant GET_REMINDER_BYID_SUCCESS_MESSAGE. */
	public static final String GET_REMINDER_BYID_SUCCESS_MESSAGE = "Following is your Reminder";

	/** The Constant GET_REMINDER_BYID_FAILURE_MESSAGE. */
	public static final String GET_REMINDER_BYID_FAILURE_MESSAGE = "You don't have any Reminders with the id = ";

	/** The Constant REMINDER_VALIDATION_FAILURE_CODE. */
	public static final String REMINDER_VALIDATION_FAILURE_CODE = "400";

	/** The Constant REMINDER_VALIDATION_FAILURE_TYPE. */
	public static final String REMINDER_VALIDATION_FAILURE_TYPE = "Bad Request";

	/** The Constant ORM_SUCCESS_CODE. */
	public static final String ORM_SUCCESS_CODE = "200";

	/** The Constant ORM_FAILURE_CODE. */
	public static final String ORM_FAILURE_CODE = "500";

	public static final String ORM_DATA_FAILURE_CODE = "400";

	public static final String DELETE_REMINDER_DATA_FAILURE_MESSAGE = "No records found";
	
	public static final String UPDATE_REMINDER_DATA_FAILURE_MESSAGE = "No records found";
	
	public static final String REMINDERS = "reminders";
	
	public static final String SELF = "self";
	
	public static enum Status {

		/** The NOTDONE. */
		NOTDONE,
		
		/** The DONE. */
		DONE

	}

	private ReminderConstants() {
	}

}
