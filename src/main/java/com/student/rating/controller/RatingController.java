package com.student.rating.controller;

import com.student.rating.dto.ActionRatingDTO;
import com.student.rating.dto.RatingDTO;
import com.student.rating.dto.ResultRatingDTO;
import com.student.rating.entity.Rating;
import com.student.rating.entity.Student;
import com.student.rating.entity.log.LogType;
import com.student.rating.logging.Logged;
import com.student.rating.mapper.RatingObjectDTOMapper;
import com.student.rating.service.EmailService;
import com.student.rating.service.RatingService;
import com.student.rating.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.student.rating.utils.StaticDataEngine.BLOCK_LIST;

@Controller
public class RatingController {
    private final RatingService ratingService;
    private final EmailService emailService;
    private final StudentService studentService;
    private final RatingObjectDTOMapper ratingObjectDTOMapper;
    private List<Rating> ratingList;

    @Autowired
    public RatingController(RatingService ratingService, EmailService emailService, StudentService studentService, RatingObjectDTOMapper ratingObjectDTOMapper) {
        this.ratingService = ratingService;
        this.emailService = emailService;
        this.studentService = studentService;
        this.ratingObjectDTOMapper = ratingObjectDTOMapper;
    }

    @GetMapping(value = "/rating")
    public String rating(Model model) {
        model.addAttribute("rating", BLOCK_LIST);
        return "rating";
    }


    @PostMapping(value = "/rating/save")
    @Logged(LogType.RATING_FILL)
    public ResponseEntity saveRating(@RequestBody List<RatingDTO> ratingDTO) {
        List<Rating> ratings = ratingService.save(ratingDTO);
        List<ResultRatingDTO> resultRatingDTOList = ratingObjectDTOMapper.toDTOs(ratings);
        return ResponseEntity.ok(resultRatingDTOList);
    }

    @Secured({"ROLE_HEAD_OF_GROUP", "ROLE_HEAD_OF_SO", "ROLE_ADMINISTRATOR"})
    @GetMapping(value = "/check-rating")
    public String check(Model model) {
        List<Student> students = studentService.findStudentRatings();
        model.addAttribute("students", students);
        return "check-rating";
    }

    @Secured({"ROLE_HEAD_OF_GROUP", "ROLE_HEAD_OF_SO", "ROLE_ADMINISTRATOR"})
    @PostMapping(value = "/check-rating/{id}")
    public String checkRating(@PathVariable("id") Long studentId) {
        List<Rating> rating = ratingService.findAllRatingsByIdStudentAndStageOfApproveGreaterThan(studentId);
        ratingList = new ArrayList<>();
        ratingList = rating;
        return "redirect:/students-rating";
    }

    @Secured({"ROLE_HEAD_OF_GROUP", "ROLE_HEAD_OF_SO", "ROLE_ADMINISTRATOR"})
    @GetMapping(value = "/students-rating")
    public String studentRating(Model model) {
        model.addAttribute("rating", ratingList);
        return "students-rating";
    }

    @Secured({"ROLE_HEAD_OF_GROUP", "ROLE_HEAD_OF_SO", "ROLE_ADMINISTRATOR"})
    @PostMapping(value = "/students-rating/accept")
    public ResponseEntity<Object> accept(@RequestBody ActionRatingDTO actionRatingDTO) {
        List<Rating> ratings = ratingService.approveRating(actionRatingDTO);
        List<ResultRatingDTO> resultRatingDTO = ratingObjectDTOMapper.toDTOs(ratings);
        return ResponseEntity.ok(resultRatingDTO);
    }

    @Secured({"ROLE_HEAD_OF_GROUP", "ROLE_HEAD_OF_SO", "ROLE_ADMINISTRATOR"})
    @PostMapping(value = "/students-rating/reject")
    @ResponseBody
    public String reject(@RequestBody ActionRatingDTO actionRatingDTO) {
        emailService.sendMail(actionRatingDTO);
        return "students-rating";
    }
}
