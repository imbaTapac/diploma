package student_rating.service;

import student_rating.DTO.RatingDTO;
import student_rating.entity.Rating;

import java.util.List;

/**
 * Created by Тарас on 06.03.2018.
 */

public interface RatingService {
    List<Rating> save(List<RatingDTO> ratingDTO);
    List<Rating> findAllRatingsByIdStudentAndStageOfApproveGreaterThan (Long studentId);
    List<Rating> approveRating(Long studentId);
}
