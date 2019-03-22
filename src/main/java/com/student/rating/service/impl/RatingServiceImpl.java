package com.student.rating.service.impl;

import com.student.rating.dto.RatingDTO;
import com.student.rating.entity.Paragraph;
import com.student.rating.entity.Rating;
import com.student.rating.entity.Student;
import com.student.rating.exception.DuplicateRatingException;
import com.student.rating.repository.ParagraphRepository;
import com.student.rating.repository.RatingRepository;
import com.student.rating.repository.StudentRepository;
import com.student.rating.service.RatingService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Тарас on 01.06.2018.
 */
@Service
public class RatingServiceImpl implements RatingService {

    private static final Logger LOG = LoggerFactory.getLogger(RatingServiceImpl.class);

    private final RatingRepository ratingRepository;
    private final StudentRepository studentRepository;
    private final ParagraphRepository paragraphRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository, StudentRepository studentRepository, ParagraphRepository paragraphRepository) {
        this.ratingRepository = ratingRepository;
        this.studentRepository = studentRepository;
        this.paragraphRepository = paragraphRepository;
    }

    @Override
    public List<Rating> save(List<RatingDTO> ratingDTO) {
        List<Rating> ratings = new ArrayList<>();
        String studentName = SecurityContextHolder.getContext().getAuthentication().getName();
        LOG.debug("Student with name {} trying to save rating",studentName);
        Student student = studentRepository.findByUsername(studentName);
        DateTime currentDate = new DateTime();
        DateTime monthStart = currentDate.dayOfMonth().withMinimumValue().withTimeAtStartOfDay();
        DateTime monthEnd = currentDate.dayOfMonth().withMaximumValue().withTimeAtStartOfDay();
        List<Rating> currentMonthRatings = ratingRepository.findAllRatingsByDateBetween(monthStart.toDate(), monthEnd.toDate());
        //TODO : in ver_0.8 clarify message what ratings is duplicated
        boolean isDuplicated = currentMonthRatings.stream()
                .map(r -> r.getParagraph().getId())
                .anyMatch(id -> ratingDTO.stream()
                        .map(RatingDTO::getParagraphId).anyMatch(id::equals));
        if (isDuplicated) {
            LOG.error("Duplicated rating was founded by student {}", student.getUsername());
            throw new DuplicateRatingException(404, "Duplicated rating was found");
        }
        for (RatingDTO studentRating : ratingDTO) {
            Rating rating = new Rating();
            rating.setDate(new Date());
            rating.setStageOfApprove(0);
            rating.setScore(studentRating.getScore());
            rating.setComment(studentRating.getComment());
            Paragraph paragraph = paragraphRepository.findById(studentRating.getParagraphId());
            rating.setParagraph(paragraph);
            rating.setStudent(student);
            ratings.add(rating);
        }
        return ratingRepository.save(ratings);
    }

    @Override
    public List<Rating> findAllRatingsByIdStudentAndStageOfApproveGreaterThan(Long studentId) {
        List<Rating> ratings = new ArrayList<>();
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        DateTime currentDate = new DateTime();
        DateTime monthStart = currentDate.dayOfMonth().withMinimumValue().withTimeAtStartOfDay();
        DateTime monthEnd = currentDate.dayOfMonth().withMaximumValue().withTimeAtStartOfDay();

        if (role.equalsIgnoreCase("[ROLE_HEAD_OF_GROUP]")) {
            ratings = ratingRepository.findAllRatingsByIdStudentAndStageOfApproveGreaterThan(studentId, 0, monthStart.toDate(), monthEnd.toDate());
        }
        if (role.equalsIgnoreCase("[ROLE_HEAD_OF_SO]")) {
            ratings = ratingRepository.findAllRatingsByIdStudentAndStageOfApproveGreaterThan(studentId, 1, monthStart.toDate(), monthEnd.toDate());
        }
        return ratings;
    }

    @Override
    public List<Rating> approveRating(Long studentId) {
        List<Rating> ratings = new ArrayList<>();
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        DateTime currentDate = new DateTime();
        DateTime monthStart = currentDate.dayOfMonth().withMinimumValue().withTimeAtStartOfDay();
        DateTime monthEnd = currentDate.dayOfMonth().withMaximumValue().withTimeAtStartOfDay();

        if (role.equalsIgnoreCase("[ROLE_HEAD_OF_GROUP]")) {
            ratings = ratingRepository.findAllRatingsByIdStudentAndStageOfApproveGreaterThan(studentId, 0, monthStart.toDate(), monthEnd.toDate());
        }
        if (role.equalsIgnoreCase("[ROLE_HEAD_OF_SO]")) {
            ratings = ratingRepository.findAllRatingsByIdStudentAndStageOfApproveGreaterThan(studentId, 1, monthStart.toDate(), monthEnd.toDate());
        }

        for (Rating rating : ratings) {
            rating.setStageOfApprove(rating.getStageOfApprove() + 1);
        }

        return ratingRepository.save(ratings);
    }
}
