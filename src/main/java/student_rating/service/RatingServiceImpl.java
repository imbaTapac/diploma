package student_rating.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import student_rating.DTO.RatingDTO;
import student_rating.entity.Paragraph;
import student_rating.entity.Rating;
import student_rating.entity.Student;
import student_rating.repository.ParagraphRepository;
import student_rating.repository.RatingRepository;
import student_rating.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Тарас on 01.06.2018.
 */
@Service
public class RatingServiceImpl implements RatingService{

    private final RatingRepository ratingRepository;
    private final StudentRepository studentRepository;
    private final ParagraphRepository paragraphRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository, StudentRepository studentRepository,ParagraphRepository paragraphRepository){
        this.ratingRepository = ratingRepository;
        this.studentRepository = studentRepository;
        this.paragraphRepository = paragraphRepository;
    }

    @Override
    public List<Rating> save(List<RatingDTO> ratingDTO) {
        List<Rating> ratings = new ArrayList<>();
        String studentName = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findByUsername(studentName);
        for(RatingDTO studentRating : ratingDTO){
            Rating rating = new Rating();
            rating.setDate(new Date());
            rating.setStageOfApprove(0);
            rating.setScore(studentRating.getScore());
            rating.setComment(studentRating.getComment());
            Paragraph paragraph = paragraphRepository.findOne(studentRating.getParagraphId());
            rating.setParagraph(paragraph);
            rating.setStudent(student);
            ratings.add(rating);
        }
        return ratingRepository.save(ratings);
    }

    @Override
    public List<Rating> findAllRatingsByIdStudentAndStageOfApproveGreaterThan(Long studentId) {
        List<Rating> ratings = new ArrayList();
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (role.equalsIgnoreCase("[ROLE_HEAD_OF_GROUP]")) {
            ratings = ratingRepository.findAllRatingsByIdStudentAndStageOfApproveGreaterThan(studentId,0);
        }
        if (role.equalsIgnoreCase("[ROLE_HEAD_OF_SO]")) {
            ratings = ratingRepository.findAllRatingsByIdStudentAndStageOfApproveGreaterThan(studentId,1);
        }
        for(Rating rating :ratings){
            System.out.println("ID RATING = "+rating.getId());
            System.out.println("PARAGRAPH ID = "+rating.getParagraph().getId());
            System.out.println("PARAGRAPH NAME = "+rating.getParagraph().getName());
            System.out.println("PARAGRAPH SCORE = "+rating.getParagraph().getScore());
        }
        return ratings;
    }

    @Override
    public List<Rating> approveRating(Long studentId) {
        List<Rating> ratings = new ArrayList();
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (role.equalsIgnoreCase("[ROLE_HEAD_OF_GROUP]")) {
            ratings = ratingRepository.findAllRatingsByIdStudentAndStageOfApproveGreaterThan(studentId,0);
        }
        if (role.equalsIgnoreCase("[ROLE_HEAD_OF_SO]")) {
            ratings = ratingRepository.findAllRatingsByIdStudentAndStageOfApproveGreaterThan(studentId,1);
        }

        for(Rating rating : ratings){
            rating.setStageOfApprove(rating.getStageOfApprove()+1);
        }

        return ratingRepository.save(ratings);
    }
}
