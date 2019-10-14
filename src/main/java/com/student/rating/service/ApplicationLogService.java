package com.student.rating.service;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.student.rating.entity.log.ApplicationLog;
import com.student.rating.entity.log.LogType;

public interface ApplicationLogService {
	/**
	 * Method for retrieving page of system logs with or w\o date parameters.
	 *
	 * @param startDate date from
	 * @param endDate date to
	 * @param pageable pagination parameter
	 * @return page of {@link ApplicationLog}
	 */
	Page<ApplicationLog> getApplicationLogs(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

	/**
	 * Method for exporting system logs to zip archive
	 *
	 * @param startDate date from
	 * @param endDate date to
	 * @param logCategory log category
	 * @return Resource with zip archive
	 */
	//Resource exportLogsToZip(LocalDateTime startDate, LocalDateTime endDate, LogCategory logCategory);

	/**
	 * Method for exporting system logs to csv file
	 *
	 * @param startDate date from
	 * @param endDate date to
	 * @param logCategory log category
	 */
	//Resource exportLogsToCsv(LocalDateTime startDate, LocalDateTime endDate, LogCategory logCategory);

	/**
	 * Add system log
	 *
	 * @param applicationLog system log record
	 */
	void saveSystemLog(ApplicationLog applicationLog);

	/**
	 * Method for deleting system logs in some date period
	 *
	 * @param startDate date from
	 * @param endDate date to
	 * @param logCategory log category
	 */
	//void deleteLogs(LocalDateTime startDate, LocalDateTime endDate, LogCategory logCategory);

	/**
	 * Method for deleting system log by id
	 *
	 * @param logId id of system log
	 */
	void deleteLogById(String logId);

	/**
	 * Add system log for Success
	 *
	 * @param userId id of the User
	 * @param logType type of the event
	 * @param description additional description in addition to Event's description
	 * @param addIpAddress true if IP address needs to be logged
	 */
	void addSystemLogSuccess(String userId, LogType logType, String description, boolean addIpAddress);

	/**
	 * Add system log for Failure
	 *
	 * @param userId id of the User
	 * @param logType type of the event
	 * @param description description in addition to Event's description
	 * @param addIpAddress true if IP address needs to be logged
	 */
	void addSystemLogFailure(String userId, LogType logType, String description, boolean addIpAddress);

	/**
	 * Retrieves last user successful login record by username.
	 *
	 * @param username {@link com.student.rating.entity.Student} name/alias of user.
	 * @return {@link ApplicationLog} log record if exists, {@literal null} otherwise.
	 */
	ApplicationLog getLastUserSuccessfulLogin(String username);

	/**
	 * Retrieves records related to specified login name which are newer that specified data.
	 *
	 * @param login {@link String} user login.
	 * @param fromDate {@link LocalDateTime} datetime to search from.
	 * @return {@link List} of {@link ApplicationLog} logs.
	 */
	List<ApplicationLog> getLastUserLoginFailuresFromData(String login, LocalDateTime fromDate);
}
