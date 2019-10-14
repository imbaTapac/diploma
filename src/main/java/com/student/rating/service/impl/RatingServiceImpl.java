package com.student.rating.service.impl;

import com.student.rating.dto.ActionRatingDTO;
import com.student.rating.dto.RatingDTO;
import com.student.rating.entity.Paragraph;
import com.student.rating.entity.Rating;
import com.student.rating.entity.Student;
import com.student.rating.exception.DuplicateRatingException;
import com.student.rating.exception.StudentRatingBaseException;
import com.student.rating.repository.ParagraphRepository;
import com.student.rating.repository.RatingRepository;
import com.student.rating.service.RatingService;
import com.student.rating.service.StudentService;
import com.student.rating.utils.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.student.rating.constants.Constants.*;
import static com.student.rating.entity.Role.HEAD_OF_GROUP;
import static com.student.rating.entity.Role.HEAD_OF_SO;
import static java.util.Objects.isNull;

/**
 * Created by Тарас on 01.06.2018.
 */
@Service
public class RatingServiceImpl implements RatingService {

    private static final Logger LOG = LoggerFactory.getLogger(RatingServiceImpl.class);

    private final RatingRepository ratingRepository;
    private final StudentService studentService;
    private final ParagraphRepository paragraphRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository, StudentService studentService, ParagraphRepository paragraphRepository) {
        this.ratingRepository = ratingRepository;
        this.studentService = studentService;
        this.paragraphRepository = paragraphRepository;
    }

    @Override
    public List<Rating> save(List<RatingDTO> ratingDTO) {
        List<Rating> ratings = new ArrayList<>();
        Student student = studentService.getCurrentUser();
        LOG.debug("Student {} trying to save rating", student.getName());
        Date monthStart = DateTimeUtils.getCurrentMonthStart();
        Date monthEnd = DateTimeUtils.getCurrentMonthEnd();
        List<Rating> currentMonthRatings = ratingRepository.findAllNotDeclinedStudentRatingsByDateBetween(student.getId(), monthStart, monthEnd);
        LOG.debug("Ratings that student have {}.\n Ratings that student want to save {}", currentMonthRatings, ratingDTO);

        List<Rating> duplicated = currentMonthRatings.stream()
                .filter(r -> ratingDTO.stream()
                        .anyMatch(rd ->
                                r.getParagraph().getId().equals(rd.getParagraphId())))
                .collect(Collectors.toList());

        List<Paragraph> paragraphs = duplicated.stream()
                .map(Rating::getParagraph).collect(Collectors.toList());

        if (!duplicated.isEmpty()) {
            LOG.error("Duplicated rating was founded by student {}.\n {}", student.getUsername(), paragraphs);
            throw new DuplicateRatingException(404, "Duplicated rating was found");
        }
        for (RatingDTO studentRating : ratingDTO) {
            Paragraph paragraph = paragraphRepository.findById(studentRating.getParagraphId())
                    .orElseThrow(() -> new StudentRatingBaseException(404, "Unknown paragraph id"));
            if ((studentRating.getScore() == Double.parseDouble(paragraph.getScore()))
                    || paragraph.getName().endsWith("*")) {
                Rating rating = new Rating();
                rating.setDate(new Date());
                rating.setStageOfApprove(UNAPPROVED);
                rating.setScore(studentRating.getScore());
                rating.setComment(studentRating.getComment());
                rating.setParagraph(paragraph);
                rating.setStudent(student);
                ratings.add(rating);
            } else {
                LOG.error("Student [{}] tried to save rating [{}] with score [{}] but original score is [{}]", student.getName(), paragraph.getName(), studentRating.getScore(), paragraph.getScore());
                throw new StudentRatingBaseException(404, String.format("Paragraph %s doest have such score %s", paragraph.getName(), studentRating.getScore()));
            }
        }
        return ratingRepository.saveAll(ratings);
    }

    @Override
    public List<Rating> findAllRatingsByIdStudentAndStageOfApproveGreaterThan(Long studentId) {
        List<Rating> ratings = new ArrayList<>();
        Student currentStudent = studentService.getCurrentUser();

        Date monthStart = DateTimeUtils.getCurrentMonthStart();
        Date monthEnd = DateTimeUtils.getCurrentMonthEnd();

        if (currentStudent.getRole() == HEAD_OF_GROUP) {
            ratings = ratingRepository.findAllRatingsByIdStudentAndStageOfApproveIsEqual(studentId, UNAPPROVED, monthStart, monthEnd);
        }
        if (currentStudent.getRole() == HEAD_OF_SO) {
            ratings = ratingRepository.findAllRatingsByIdStudentAndStageOfApproveIsEqual(studentId, APPROVED_BY_HEAD_OF_GROUP, monthStart, monthEnd);
        }
        return ratings;
    }

    @Override
    @Transactional
    public List<Rating> approveRating(ActionRatingDTO actionRatingDTO) {
        List<Rating> ratings = new ArrayList<>();
        Student currentStudent = studentService.getCurrentUser();
        if (currentStudent.getRole() == HEAD_OF_GROUP) {
            ratings = approve(actionRatingDTO, APPROVED_BY_HEAD_OF_GROUP);
        }
        if (currentStudent.getRole() == HEAD_OF_SO) {
            ratings = approve(actionRatingDTO, APPROVED_BY_HEAD_OF_SO);
        }

        return ratings;
    }

    @Override
    public List<Rating> findAllMonthStudentRatings(Date date) {
        Student student = studentService.getCurrentUser();
        if (isNull(student)) {
            LOG.error("No student in session");
            throw new StudentRatingBaseException(404, "No student in session");
        } else {
            Date monthStart = DateTimeUtils.getMonthStartByDate(date);
            Date monthEnd = DateTimeUtils.getMonthEndByDate(date);
            return ratingRepository.findAllRatingsByStudentIdAndDateBetween(student.getId(), monthStart, monthEnd);
        }
    }

    private List<Rating> approve(ActionRatingDTO actionRatingDTO, Integer stageOfApprove) {
        actionRatingDTO.getRatingsId()
                .forEach(id -> ratingRepository.changeStudentRatingApproveStage(stageOfApprove, id));
        return actionRatingDTO.getRatingsId().stream()
                .map(id -> ratingRepository.findById(id).orElseThrow(() -> new StudentRatingBaseException(404, "Wrong rating id"))).collect(Collectors.toList());
    }
}
