package com.student.rating.service.impl;

import static com.student.rating.constants.Constants.APPROVED_BY_HEAD_OF_GROUP;
import static com.student.rating.constants.Constants.APPROVED_BY_HEAD_OF_SO;
import static com.student.rating.constants.Constants.UNAPROVED;
import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.student.rating.dto.ActionRatingDTO;
import com.student.rating.dto.RatingDTO;
import com.student.rating.entity.Paragraph;
import com.student.rating.entity.Rating;
import com.student.rating.entity.Role;
import com.student.rating.entity.Student;
import com.student.rating.exception.DuplicateRatingException;
import com.student.rating.exception.StudentRatingBaseException;
import com.student.rating.repository.ParagraphRepository;
import com.student.rating.repository.RatingRepository;
import com.student.rating.repository.StudentRepository;
import com.student.rating.service.RatingService;

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
		LOG.debug("Student {} trying to save rating", studentName);
		Student student = studentRepository.findByUsername(studentName);
		DateTime currentDate = new DateTime();
		DateTime monthStart = currentDate.dayOfMonth().withMinimumValue().withTimeAtStartOfDay();
		DateTime monthEnd = currentDate.dayOfMonth().withMaximumValue().withTimeAtStartOfDay();
		List<Rating> currentMonthRatings = ratingRepository.findAllNotDeclinedStudentRatingsByDateBetween(student.getId(), monthStart.toDate(), monthEnd.toDate());
		LOG.debug("Ratings that student have {}.\n Ratings that student want to save {}", currentMonthRatings, ratingDTO);
		//TODO : in ver_0.9 clarify message what ratings is duplicated
		boolean isDuplicated = currentMonthRatings.stream()
				.map(r -> r.getParagraph().getId())
				.anyMatch(id -> ratingDTO.stream()
						.map(RatingDTO::getParagraphId).anyMatch(id::equals));
		if(isDuplicated) {
			LOG.error("Duplicated rating was founded by student {}", student.getUsername());
			throw new DuplicateRatingException(404, "Duplicated rating was found");
		}
		for(RatingDTO studentRating : ratingDTO) {
			Rating rating = new Rating();
			rating.setDate(new Date());
			rating.setStageOfApprove(0);
			rating.setScore(studentRating.getScore());
			rating.setComment(studentRating.getComment());
			Paragraph paragraph = paragraphRepository.findById(studentRating.getParagraphId()).get();
			rating.setParagraph(paragraph);
			rating.setStudent(student);
			ratings.add(rating);
		}
		return ratingRepository.saveAll(ratings);
	}

	@Override
	public List<Rating> findAllRatingsByIdStudentAndStageOfApproveGreaterThan(Long studentId) {
		List<Rating> ratings = new ArrayList<>();
		String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
		DateTime currentDate = new DateTime();
		DateTime monthStart = currentDate.dayOfMonth().withMinimumValue().withTimeAtStartOfDay();
		DateTime monthEnd = currentDate.dayOfMonth().withMaximumValue().withTimeAtStartOfDay();

		if(role.equals(Role.HEAD_OF_GROUP.getFullAuthority())) {
			ratings = ratingRepository.findAllRatingsByIdStudentAndStageOfApproveIsEqual(studentId, UNAPROVED, monthStart.toDate(), monthEnd.toDate());
		}
		if(role.equals(Role.HEAD_OF_SO.getFullAuthority())) {
			ratings = ratingRepository.findAllRatingsByIdStudentAndStageOfApproveIsEqual(studentId, APPROVED_BY_HEAD_OF_GROUP, monthStart.toDate(), monthEnd.toDate());
		}
		return ratings;
	}

	@Override
	@Transactional
	public List<Rating> approveRating(ActionRatingDTO actionRatingDTO) {
		List<Rating> ratings = new ArrayList<>();
		String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
		if(role.equals(Role.HEAD_OF_GROUP.getFullAuthority())) {
			actionRatingDTO.getRatingsId()
					.forEach(id -> ratingRepository.changeStudentRatingApproveStage(APPROVED_BY_HEAD_OF_GROUP, id));
			ratings = actionRatingDTO.getRatingsId().stream()
					.map(id -> ratingRepository.findById(id).orElseThrow(() -> new StudentRatingBaseException(404, "Wrong rating id"))).collect(Collectors.toList());
		}
		if(role.equals(Role.HEAD_OF_SO.getFullAuthority())) {
			actionRatingDTO.getRatingsId()
					.forEach(id -> ratingRepository.changeStudentRatingApproveStage(APPROVED_BY_HEAD_OF_SO, id));
			ratings = actionRatingDTO.getRatingsId().stream()
					.map(id -> ratingRepository.findById(id).orElseThrow(() -> new StudentRatingBaseException(404, "Wrong rating id"))).collect(Collectors.toList());
		}

		return ratings;
	}

	@Override
	public List<Rating> findAllMonthStudentRatings(HttpSession session, Date date) {
		Student student = (Student) session.getAttribute("student");
		if(isNull(student)) {
			LOG.error("No student in session");
			throw new StudentRatingBaseException(404, "No student in session");
		} else {
			DateTime currentDate = new DateTime(date);
			DateTime monthStart = currentDate.dayOfMonth().withMinimumValue().withTimeAtStartOfDay();
			DateTime monthEnd = currentDate.dayOfMonth().withMaximumValue().withTimeAtStartOfDay();
			return ratingRepository.findAllRatingsByStudentIdAndDateBetween(student.getId(), monthStart.toDate(), monthEnd.toDate());
		}
	}
}
