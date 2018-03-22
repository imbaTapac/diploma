package student_rating.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student_rating.entity.Roles;
import student_rating.entity.Student;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Тарас on 01.03.2018.
 */
@Service
public class StudentDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private StudentService studentService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentService.findByUsername(username);

        if(student==null){
            throw new UsernameNotFoundException(username + " not found");
        }
        // разрешение на вход
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        // запихиваем все роли в разрешения на вход
        for (Roles role : student.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(student.getUsername(), student.getPassword(),grantedAuthorities);

        return userDetails;

    }
}
