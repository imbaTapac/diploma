package student_rating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import student_rating.entity.Student;

import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Тарас on 31.05.2018.
 */
@Repository
@PersistenceContext
public interface StudentRepository extends JpaRepository<Student, Long> {
    /**
     *
     * @param username
     * @return
     */
    Student findByUsername(String username);

    /**
     *
     * @param id
     * @return
     */
    Student findById(Long id);

    /**
     *
     * @param groupId
     * @return
     */
    List<Student> findAllStudentsByGroupId(Long groupId);

    /**
     * TO FIND ALL STUDENTS IN GROUP BY GROUP ID AND STAGE OF APPROVE
     *
     * @param groupId
     * @param stageOfApprove
     * @return
     */
    @Query(value = "select s from Student s JOIN s.ratings r where s.group.id = :groupId AND r.stageOfApprove = :stageOfApprove GROUP BY s.id")
    List<Student> findAllStudentsByGroupIdAndRatingsStageOfApprove(@Param("groupId") Long groupId, @Param("stageOfApprove") Integer stageOfApprove);

    @Query(value = "select s from Student s JOIN s.ratings r where r.stageOfApprove = :stageOfApprove GROUP BY s.id")
    List<Student> findAllStudentsRatingsStageOfApprove(@Param("stageOfApprove") Integer stageOfApprove);


}
