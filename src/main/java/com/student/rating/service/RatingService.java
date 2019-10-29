package com.student.rating.service;

import java.util.Date;
import java.util.List;


import com.student.rating.dto.ActionRatingDTO;
import com.student.rating.dto.RatingDTO;
import com.student.rating.entity.Rating;

/**
 * Created by Тарас on 06.03.2018.
 */

public interface RatingService {
	List<Rating> save(List<RatingDTO> ratingDTO);

	List<Rating> findAllRatingsByIdStudentAndStageOfApproveGreaterThan(Long studentId);

	List<Rating> approveRating(ActionRatingDTO actionRatingDTO);

	/**
	 * Retrieves all student ratings in scope month
	 *
	 * @param date
	 * @return
	 */
	List<Rating> findAllMonthStudentRatings(Date date);
}
