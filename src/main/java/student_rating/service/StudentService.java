package student_rating.service;

import student_rating.entity.Student;

/**
 * Created by Тарас on 01.03.2018.
 */
public interface StudentService {
    void save(Student student);
    Student findByUsername(String username);
}
