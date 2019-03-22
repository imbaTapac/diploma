package com.student.rating.service.impl;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.student.rating.dto.UserDTO;
import com.student.rating.entity.Group;
import com.student.rating.entity.Role;
import com.student.rating.entity.Student;
import com.student.rating.repository.StudentRepository;
import com.student.rating.service.StudentService;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;
import static com.student.rating.constants.Constants.*;
import static com.student.rating.utility.StaticDataEngine.OKR_LIST;

/**
 * Created by Тарас on 01.03.2018.
 */
@Service
public class StudentServiceImpl implements StudentService {

    private static final Logger LOG = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository studentRepository;
    private final ShaPasswordEncoder passwordEncoder;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, ShaPasswordEncoder shaPasswordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = shaPasswordEncoder;
    }

    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student saveFromDTO(Student student, UserDTO userDTO) {
        student.setCourse(parseInt(userDTO.getCourse()));
        student.setPhone(userDTO.getPhone());
        student.setPassword(passwordEncoder.encodePassword(userDTO.getPassword(), null));
        return studentRepository.save(student);
    }

    @Override
    public void addToSession(HttpSession session) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findByUsername(userName);
        LOG.debug("Add student {} to session",student);
        session.setAttribute("student",student);
        LOG.debug("Student successfully added to session");
    }

    @Override
    public Student update(Student student) {
        return studentRepository.saveAndFlush(student);
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
        List<Student> students = new ArrayList<>();
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
    public List<Student> findStudentRatings() {
        String studentName = SecurityContextHolder.getContext().getAuthentication().getName();
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        List<Student> students = new ArrayList<>();
        DateTime currentDate = new DateTime();
        DateTime monthStart = currentDate.dayOfMonth().withMinimumValue().withTimeAtStartOfDay();
        DateTime monthEnd = currentDate.dayOfMonth().withMaximumValue().withTimeAtStartOfDay();
        if (role.equalsIgnoreCase("[ROLE_HEAD_OF_GROUP]")) {
            Student student = studentRepository.findByUsername(studentName);
            Group group = student.getGroup();
            students = studentRepository.findAllStudentsByGroupIdAndRatingsStageOfApprove(group.getId(), 0,monthStart.toDate(),monthEnd.toDate());
        }
        if (role.equalsIgnoreCase("[ROLE_HEAD_OF_SO]")) {
            students = studentRepository.findAllStudentsRatingsStageOfApprove(1,monthStart.toDate(),monthEnd.toDate());
        }
        return students;
    }

    @Override
    public Student createNewStudentFromLDAPAttribute(Attributes attributes) throws NamingException {
        Student student = new Student();

        if (attributes.get(ID_ATTRIBUTE) != null) {
            student.setId(Long.parseLong(attributes.get(ID_ATTRIBUTE).get().toString()));
        }

        if (attributes.get(EMAIL_ATTRIBUTE) != null) {
            student.setEmail(attributes.get(EMAIL_ATTRIBUTE).get().toString());
        }

        if (attributes.get(USERNAME_ATTRIBUTE) != null) {
            student.setUsername(attributes.get(USERNAME_ATTRIBUTE).get().toString());
        }

        if (attributes.get(OKR_ATTRIBUTE) != null) {
            String okr = attributes.get(OKR_ATTRIBUTE).get().toString();
            boolean isBachelor = okr.equals("Бакалавр");
            boolean isMagister = okr.equals("Магістр");
            if (isBachelor) {
                student.setOkr(OKR_LIST.get(0));
            }
            if (isMagister) {
                student.setOkr(OKR_LIST.get(1));
            }
        }

        if (attributes.get(STUDENT_FULL_NAME_ATTRIBUTE) != null) {
            String[] studentFullName = attributes.get(STUDENT_FULL_NAME_ATTRIBUTE).get().toString().split("\\s");
            student.setStudentSurname(studentFullName[0]);
            student.setStudentName(studentFullName[1]);
        }

        if (attributes.get(ROLE_ATTRIBUTE) != null) {
            String role = attributes.get(ROLE_ATTRIBUTE).get().toString();
            LOG.debug("Role is {}", role);
            if (role.equals(ORGANIZATIONAL_PERSON)) {
                student.setRole(Role.ADMINISTRATOR);
            } else {
                student.setRole(Role.STUDENT);
            }
        }

        LOG.debug("Result student is [{}]", student);
        return student;
    }
}
