package student_rating.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import student_rating.entity.Student;

/**
 * Created by Тарас on 01.03.2018.
 */
public interface StudentDAO extends JpaRepository<Student, Long> {
        Student findByUsername(String username);
}
