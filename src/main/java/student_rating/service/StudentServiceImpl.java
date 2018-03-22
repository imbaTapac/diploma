package student_rating.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import student_rating.DAO.RoleDAO;
import student_rating.DAO.StudentDAO;
import student_rating.entity.Student;

/**
 * Created by Тарас on 01.03.2018.
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentDAO studentDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private ShaPasswordEncoder shaPasswordEncoder;

    @Override
    public void save(Student student) {

    }

    @Override
    public Student findByUsername(String username) {
        return studentDAO.findByUsername(username);
    }
}
