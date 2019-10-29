package com.student.rating.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.student.rating.dto.ErrorInfoDTO;
import com.student.rating.exception.StudentRatingBaseException;

@ControllerAdvice
public class StudentRatingExceptionHandler {
	@ExceptionHandler({StudentRatingBaseException.class})
	@ResponseBody
	public ResponseEntity<ErrorInfoDTO> urlException(StudentRatingBaseException e) {
		ErrorInfoDTO errorInfoDTO = new ErrorInfoDTO(e);
		return ResponseEntity
				.status(e.getStatusCode())
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(errorInfoDTO);
	}
}
