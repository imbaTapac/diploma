package com.student.rating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.student.rating.entity.Student;

import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    Optional<Student> findById(Long id);

    /**
     *
     * @param groupId
     * @return
     */
    List<Student> findAllStudentsByGroupId(Long groupId);

    /**
     * TO FIND ALL STUDENTS IN GROUP BY GROUP ID AND STAGE OF APPROVE IN SCOPE MONTH
     *
     * @param groupId
     * @param stageOfApprove
     * @param monthStart
     * @param monthEnd
     * @return
     */
    @Query(value = "SELECT s FROM Student s JOIN s.ratings r WHERE s.group.id = :groupId AND r.stageOfApprove = :stageOfApprove AND r.date BETWEEN :monthStart AND :monthEnd GROUP BY s.id")
    List<Student> findAllStudentsByGroupIdAndRatingsStageOfApprove(@Param("groupId") Long groupId, @Param("stageOfApprove") Integer stageOfApprove,@Param("monthStart") Date monthStart, @Param("monthEnd") Date monthEnd);

    /**
     *
     * @param stageOfApprove
     * @param monthStart
     * @param monthEnd
     * @return
     */
    @Query(value = "SELECT s FROM Student s JOIN s.ratings r WHERE r.stageOfApprove = :stageOfApprove AND r.date BETWEEN :monthStart AND :monthEnd GROUP BY s.id")
    List<Student> findAllStudentsRatingsStageOfApprove(@Param("stageOfApprove") Integer stageOfApprove,@Param("monthStart") Date monthStart, @Param("monthEnd") Date monthEnd);

    /**
     * Retrieves student with their ratings in month scope month.
     * For role {@link com.student.rating.entity.Role#HEAD_OF_GROUP,com.student.rating.entity.Role#HEAD_OF_SO}
     * @param id
     * @param startMonth
     * @param endMonth
     * @return
     */
    @Deprecated
    @Query(value = "SELECT DISTINCT s FROM Student s LEFT JOIN FETCH s.ratings r WHERE s.group.id = :id AND (r.student.id = s.id AND r.date BETWEEN :startMonth AND :endMonth OR r IS NULL) ORDER BY s.id")
    List<Student> findAllStudentsInGroupWithRatings(@Param("id") Long id , @Param("startMonth") Date startMonth, @Param("endMonth") Date endMonth);
}
