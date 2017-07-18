package com.exercise.reminder.orm.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exercise.reminder.orm.entity.ReminderRequest;

// TODO: Auto-generated Javadoc
/**
 * The Interface ReminderRepository is the Spring data JPA.
 * @author Vinay Vaidya
 */
@Repository
public interface ReminderRepository extends JpaRepository<ReminderRequest, Long> {

	/**
	 * Update reminder.This method is for the paramters which are provided needs to update those only,it will not null the other parameters.
	 *
	 * @param description
	 *            the description
	 * @param Id
	 *            the id
	 * @param name
	 *            the name
	 * @param status
	 *            the status
	 * @param dueDate
	 *            the due date
	 * @return the int
	 */
	@Modifying
	@Query("UPDATE Reminder c SET c.description = coalesce(:description, c.description), c.name = coalesce(:name, c.name), c.status = coalesce(:status, c.status), c.dueDate = coalesce(:dueDate, c.dueDate) WHERE c.Id = :Id")
	int updateReminder(@Param("description") String description, @Param("Id") long Id, @Param("name") String name,
			@Param("status") String status, @Param("dueDate") Date dueDate);

	/**
	 * Gets the reminder by id.
	 *
	 * @param Id
	 *            the id
	 * @return the reminder by id
	 */
	@Modifying
	@Query(value = "SELECT c from Reminder c WHERE c.Id = :Id")
	List<ReminderRequest> getReminderById(@Param("Id") long Id);

}
