package student_rating.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import student_rating.entity.Group;
import student_rating.entity.Student;
import student_rating.repository.RoleRepository;
import student_rating.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Тарас on 01.03.2018.
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ShaPasswordEncoder shaPasswordEncoder;

    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student findByUsername(String username) {
        return studentRepository.findByUsername(username);
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public List<Student> findAllStudentsByGroupId() {
        String studentName = SecurityContextHolder.getContext().getAuthentication().getName();
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        List<Student> students = new ArrayList();
        if (role.equalsIgnoreCase("[ROLE_HEAD_OF_GROUP]")) {
            Student student = studentRepository.findByUsername(studentName);
            Group group = student.getGroup();
            students = studentRepository.findAllStudentsByGroupId(group.getId());
        }
        if (role.equalsIgnoreCase("[ROLE_HEAD_OF_SO]")) {
            students = studentRepository.findAll();
        }
        return students;
    }

    @Override
    public List<Student> findAllStudentsByGroupIdAndStageOfApprove() {
        String studentName = SecurityContextHolder.getContext().getAuthentication().getName();
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        List<Student> students = new ArrayList();
        if (role.equalsIgnoreCase("[ROLE_HEAD_OF_GROUP]")) {
            Student student = studentRepository.findByUsername(studentName);
            Group group = student.getGroup();
            students = studentRepository.findAllStudentsByGroupIdAndRatingsStageOfApprove(group.getId(),0);
        }
        if (role.equalsIgnoreCase("[ROLE_HEAD_OF_SO]")) {
            students = studentRepository.findAllStudentsRatingsStageOfApprove(1);
        }
        return students;
    }
}
