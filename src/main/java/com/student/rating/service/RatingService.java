package com.student.rating.service;

import com.student.rating.dto.RatingDTO;
import com.student.rating.entity.Rating;

import java.util.List;

/**
 * Created by Тарас on 06.03.2018.
 */

public interface RatingService {
    List<Rating> save(List<RatingDTO> ratingDTO);
    List<Rating> findAllRatingsByIdStudentAndStageOfApproveGreaterThan (Long studentId);
    List<Rating> approveRating(Long studentId);
}
