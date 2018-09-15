package student_rating.service;

import student_rating.entity.Student;

import java.util.List;

/**
 * Created by Тарас on 01.03.2018.
 */
public interface StudentService {
    Student save(Student student);

    Student findByUsername(String username);

    Student findById(Long id);

    List<Student> findAll();

    List<Student> findAllStudentsByGroupId();

    List<Student> findAllStudentsByGroupIdAndStageOfApprove();

}
