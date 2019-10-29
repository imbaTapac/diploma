package com.student.rating.exception;

public class DuplicateRatingException extends StudentRatingBaseException {

    private final int status;

    public DuplicateRatingException(int status,String message) {
        super(status,message);
        this.status = status;
    }

	public DuplicateRatingException(int status, String message,Throwable cause){
		super(status,message,cause);
		this.status = status;
	}

    public int getStatus() {
        return status;
    }
}
