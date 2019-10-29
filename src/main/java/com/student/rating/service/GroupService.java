package com.student.rating.service;

import java.util.List;

import com.student.rating.dto.GroupDTO;

/**
 * @since 0.7
 */
public interface GroupService {
	/**
	 * Retrieves list of group with their students
	 * Only for {@link com.student.rating.entity.Role#HEAD_OF_GROUP} and {@link com.student.rating.entity.Role#HEAD_OF_SO}
	 * @return
	 */
	List<GroupDTO> findAllGroupsWithStudents();

	/**
	 * Retrieves list of group name available to faculty
	 * @return
	 */
	List<String> getAllFacultyGroupNamesByFacultyName();

}
