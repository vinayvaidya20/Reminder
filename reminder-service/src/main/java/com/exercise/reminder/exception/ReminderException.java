package com.exercise.reminder.exception;

import org.springframework.http.HttpStatus;
import java.util.Objects;


// TODO: Auto-generated Javadoc
/**
 * The Custom Reminder Exception :ReminderException.
 * @author Vinay Vaidya
 */
public class ReminderException extends Exception{
	
	 /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The type. */
	private ExceptionTypes type = ExceptionTypes.INVALID;
	
	/** The code. */
	private ExceptionCodes code;
	
	/** The http status. */
	private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	 
	 /**
 	 * Instantiates a new reminder exception.
 	 *
 	 * @param type the type
 	 * @param code the code
 	 * @param message the message
 	 */
 	public ReminderException(ExceptionTypes type, ExceptionCodes code, String message){
		 super(message);
	        if (Objects.nonNull(type)) {
	            this.type = type;
	        }
	        this.code = code;
	        deriveHttpStatus(code);
	 }

	 /**
 	 * Instantiates a new reminder exception.
 	 *
 	 * @param code the code
 	 * @param message the message
 	 */
 	public ReminderException(ExceptionCodes code, String message) {
	        this(null, code, message);
	    }
	 
	 /**
 	 * Derive http status.
 	 *
 	 * @param code the code
 	 */
 	private void deriveHttpStatus(ExceptionCodes code) {
	        switch (code) {
	        case NOT_FOUND:
	            this.setHttpStatus(HttpStatus.NOT_FOUND);
	            break;
	        case REMINDERNOTFOUND:
	            this.setHttpStatus(HttpStatus.NOT_FOUND);
	            break;    
	        case INVALIDFORMAT:
	        case INVALIDREQUEST:
	            this.setHttpStatus(HttpStatus.BAD_REQUEST);
	            break;
	        case SERVERUNAVAILABLE:
	        default:
	            this.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	  
  	/**
  	 * Gets the type.
  	 *
  	 * @return the type
  	 */
  	public ExceptionTypes getType() {
	        return type;
	    }

	    /**
    	 * Sets the type.
    	 *
    	 * @param type the new type
    	 */
    	public void setType(ExceptionTypes type) {
	        this.type = type;
	    }

	    /**
    	 * Gets the code.
    	 *
    	 * @return the code
    	 */
    	public ExceptionCodes getCode() {
	        return code;
	    }

	    /**
    	 * Sets the code.
    	 *
    	 * @param code the new code
    	 */
    	public void setCode(ExceptionCodes code) {
	        this.code = code;
	    }
	    
    	/**
    	 * Gets the http status.
    	 *
    	 * @return the http status
    	 */
    	public HttpStatus getHttpStatus() {
	        return httpStatus;
	    }

	    /**
    	 * Sets the http status.
    	 *
    	 * @param httpStatus the new http status
    	 */
    	public void setHttpStatus(HttpStatus httpStatus) {
	        this.httpStatus = httpStatus;
	    }
	 
}
