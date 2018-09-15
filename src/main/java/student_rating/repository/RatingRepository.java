package student_rating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import student_rating.entity.Rating;

import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Тарас on 31.05.2018.
 */
@Repository
@PersistenceContext
public interface RatingRepository extends JpaRepository<Rating, Long> {
    /**
     * For ROLE HEAD_OF_GROUP AND HEAD_OF_SO
     *
     * @param StudentId
     * @param StageOfApprove
     * @return
     */
    @Query(value = "Select r from Rating r where r.student.id = :StudentId and r.stageOfApprove = :StageOfApprove order by r.paragraph.id asc ")
    List<Rating> findAllRatingsByIdStudentAndStageOfApproveGreaterThan(@Param("StudentId") Long StudentId, @Param("StageOfApprove") Integer StageOfApprove);

    List<Rating> findByStudentId(Long id);
}
