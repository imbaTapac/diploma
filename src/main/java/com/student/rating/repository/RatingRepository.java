package com.student.rating.repository;

import com.student.rating.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceContext;
import java.util.Date;
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
     * @param studentId
     * @param stageOfApprove
     * @return
     */
    @Query(value = "Select r from Rating r where r.student.id = :studentId and r.stageOfApprove = :stageOfApprove and r.date between :startMonth and :endMonth order by r.paragraph.id asc ")
    List<Rating> findAllRatingsByIdStudentAndStageOfApproveGreaterThan(@Param("studentId") Long studentId, @Param("stageOfApprove") Integer stageOfApprove,
                                                                       @Param("startMonth") Date startMonth, @Param("endMonth") Date endMonth);

    List<Rating> findAllRatingsByDateBetween(Date startMonth,Date endMonth);
}
