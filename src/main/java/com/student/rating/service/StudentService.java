package com.student.rating.service;

import java.util.List;

import javax.servlet.http.HttpSession;

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

	List<Student> findAll();

	List<Student> findAllStudentsByGroupId();

	List<Student> findStudentRatings();

	Student update(Student student);

	boolean searchInLDAP(String userName, String email);

	Student createNewStudentFromLDAPAttribute();
}
