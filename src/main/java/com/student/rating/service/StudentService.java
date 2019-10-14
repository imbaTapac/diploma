package com.student.rating.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.student.rating.dto.StudentProfileDTO;
import com.student.rating.dto.UserDTO;
import com.student.rating.entity.Student;

/**
 * Created by Тарас on 01.03.2018.
 */
public interface StudentService {
	Student save(Student student);

	void addToSession(HttpSession session);

	Student saveFromDTO(UserDTO userDTO);

	Student findByUsername(String username);

	Student findById(Long id);

	/**
	 * Retrieves current Student
	 * @return
	 */
	Student getCurrentUser();

	List<Student> findAll();

	/**
	 * Retrieves student by student groupId if role is {@link com.student.rating.entity.Role#HEAD_OF_GROUP} or
	 * retrieves all students if role is {@link com.student.rating.entity.Role#HEAD_OF_SO}
	 * @param student
	 * @return
	 */
	List<Student> findAllStudentsByStudentGroupId(Student student);

	List<Student> findStudentRatings();

	Student update(Student student);

	boolean searchInLDAP(String userName, String email);

	Student createNewStudentFromLDAPAttribute();

	StudentProfileDTO getStudentProfile();
}
