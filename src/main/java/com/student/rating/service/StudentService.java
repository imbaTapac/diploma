package com.student.rating.service;

import com.student.rating.dto.UserDTO;
import com.student.rating.entity.Student;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Тарас on 01.03.2018.
 */
public interface StudentService {
    Student save(Student student);

    void addToSession(HttpSession session);

    Student saveFromDTO(Student student, UserDTO userDTO);

    Student findByUsername(String username);

    Student findById(Long id);

    List<Student> findAll();

    List<Student> findAllStudentsByGroupId();

    List<Student> findStudentRatings();

    Student update(Student student);

    Student createNewStudentFromLDAPAttribute(Attributes attributes) throws NamingException;
}
