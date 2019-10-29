package com.student.rating.service;

import java.util.List;

/**
 * @since 0.8
 */
public interface SpecialtyService {
	/**
	 * Retrieves specialty names for student faculty
	 * Attribute get by {@link com.student.rating.constants.Constants#FACULTY_ATTRIBUTE}
	 * @return
	 */
	List<String> getAllSpecialtyNamesByFacultyName();
}
