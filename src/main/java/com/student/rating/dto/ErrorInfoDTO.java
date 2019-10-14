package com.student.rating.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.student.rating.exception.StudentRatingBaseException;

/**
 * since @1.0
 * Common class to retrieve users errors
 */
public class ErrorInfoDTO implements Serializable {

	private int statusCode;
	private String message;

	@JsonFormat(pattern = "E, dd-MM-yyyy HH:mm:ss")
	private LocalDateTime timestamp;

	public ErrorInfoDTO() {
	}

	public ErrorInfoDTO(StudentRatingBaseException e) {
		this.statusCode = e.getStatusCode();
		this.message = e.getMessage();
		this.timestamp = LocalDateTime.now();
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
}
