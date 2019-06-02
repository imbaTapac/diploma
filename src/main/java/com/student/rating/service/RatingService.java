package com.student.rating.service;

import com.student.rating.dto.ActionRatingDTO;
import com.student.rating.dto.RatingDTO;
import com.student.rating.entity.Rating;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

/**
 * Created by Тарас on 06.03.2018.
 */

public interface RatingService {
    List<Rating> save(List<RatingDTO> ratingDTO);
    List<Rating> findAllRatingsByIdStudentAndStageOfApproveGreaterThan (Long studentId);
    List<Rating> approveRating(ActionRatingDTO actionRatingDTO);

	/**
	 * Retrieves all student ratings in scope month
	 * @param session
	 * @param date
	 * @return
	 */
	List<Rating> findAllMonthStudentRatings(HttpSession session, Date date);
}
