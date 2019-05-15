package com.student.rating.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.student.rating.entity.Rating;

/**
 * Created by Тарас on 31.05.2018.
 */
@Repository
@PersistenceContext
public interface RatingRepository extends JpaRepository<Rating, Long> {
	/**
	 * For ROLE {@link com.student.rating.entity.Role#HEAD_OF_GROUP} AND {@link com.student.rating.entity.Role#HEAD_OF_SO}
	 *
	 * @param studentId
	 * @param stageOfApprove
	 * @return
	 */
	@Query(value = "SELECT r FROM Rating r WHERE r.student.id = :studentId AND r.stageOfApprove = :stageOfApprove AND r.date BETWEEN :startMonth AND :endMonth ORDER BY r.paragraph.id ASC")
	List<Rating> findAllRatingsByIdStudentAndStageOfApproveIsEqual(@Param("studentId") Long studentId, @Param("stageOfApprove") Integer stageOfApprove,
	                                                               @Param("startMonth") Date startMonth, @Param("endMonth") Date endMonth);

	/**
	 * Retrieves student rating in scope month.
	 *
	 * @param studentId
	 * @param startMonth
	 * @param endMonth
	 * @return
	 */
	List<Rating> findAllRatingsByStudentIdAndDateBetween(Long studentId, Date startMonth, Date endMonth);


	@Query(value = "SELECT r FROM Rating r WHERE r.student.id = :studentId AND r.stageOfApprove >= 0 AND r.date BETWEEN :startMonth AND :endMonth ORDER BY r.paragraph.id ASC")
	List<Rating> findAllNotDeclinedStudentRatingsByDateBetween(@Param("studentId") Long studentId, @Param("startMonth") Date startMonth, @Param("endMonth") Date endMonth);

	@Modifying
	@Query("UPDATE Rating r SET r.stageOfApprove = :stage WHERE r.id = :id")
	void changeStudentRatingApproveStage(@Param("stage") Integer stage, @Param("id") Long id);

	@Query(value = "SELECT r FROM Rating r WHERE r.stageOfApprove = :stageOfApprove")
	List<Rating>findAllRatingsByStageOfApprove(@Param("stageOfApprove") Integer stageOfApprove);

	@Query(value = "SELECT r FROM Rating r WHERE r.student.id = :studentId AND r.stageOfApprove = :stageOfApprove")
	List<Rating>findAllRatingsByStudentIdAndStageOfApprove(@Param("studentId")Long studentId,@Param("stageOfApprove") Integer stageOfApprove);
}
