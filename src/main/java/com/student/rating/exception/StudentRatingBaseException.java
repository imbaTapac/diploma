package com.student.rating.exception;

public class StudentRatingBaseException extends RuntimeException{

	private final int status;

	public StudentRatingBaseException(int status,String message) {
		super(message);
		this.status = status;
	}
}
