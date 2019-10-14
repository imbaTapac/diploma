package com.student.rating.service.impl;

import com.student.rating.entity.Student;
import com.student.rating.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Set;

/**
 * Created by Тарас on 01.03.2018.
 */
@Service
public class StudentDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(StudentDetailsServiceImpl.class);
    private final StudentService studentService;

    @Autowired
    public StudentDetailsServiceImpl(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        Student student = studentService.findByUsername(username);
        if (student == null) {
            LOG.error("User {} not found in local DB", username);
            throw new UsernameNotFoundException(username + " not found in local DB");
        }

        // разрешение на вход
        Set<GrantedAuthority> studentRole = Collections.singleton(
                new SimpleGrantedAuthority(student.getRole().getAuthority())
        );
        // запихиваем все роль в разрешения на вход

        return new User(student.getUsername(), student.getPassword(), studentRole);
    }
}
