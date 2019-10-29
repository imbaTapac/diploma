package com.student.rating.entity.log;

/**
 * @since 1.0
 */
public enum LogType {
	LOGIN("User Login Success", "User Login Failed"),
	LOGOUT("User Logout Success", "User Logout Failed"),
	SIGN_UP("User Signup Success", "User Signup Failed"),
	RATING_FILL("User Fill Rating Success", "User Fill Rating Failed (Duplicated)");

	private String message;
	private String failMessage;

	LogType(String message, String failMessage) {
		this.message = message;
		this.failMessage = failMessage;
	}

	public String getMessage() {
		return message;
	}

	public String getFailMessage() {
		return failMessage;
	}
}
