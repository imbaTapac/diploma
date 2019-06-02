package com.student.rating.service.impl;

import com.student.rating.dto.ActionRatingDTO;
import com.student.rating.entity.Rating;
import com.student.rating.entity.Student;
import com.student.rating.exception.StudentRatingBaseException;
import com.student.rating.repository.RatingRepository;
import com.student.rating.repository.StudentRepository;
import com.student.rating.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.student.rating.constants.Constants.DECLINED;


/**
 * Created by Тарас on 09.06.2018.
 */
@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger LOG = LoggerFactory.getLogger(EmailServiceImpl.class);
    private final JavaMailSender emailSender;
    private final StudentRepository studentRepository;
    private final RatingRepository ratingRepository;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender, StudentRepository studentRepository, RatingRepository ratingRepository) {
        this.emailSender = mailSender;
        this.studentRepository = studentRepository;
        this.ratingRepository = ratingRepository;
    }

    @Override
    @Transactional
    public void sendMail(ActionRatingDTO actionRatingDTO) {
        LOG.debug("Sending email to student with id {}", actionRatingDTO.getStudentId());
        Student to = studentRepository.findById(actionRatingDTO.getStudentId()).orElseThrow(() -> new StudentRatingBaseException(404, "Wrong student id"));
        String studentName = SecurityContextHolder.getContext().getAuthentication().getName();
        Student from = studentRepository.findByUsername(studentName);
        actionRatingDTO.getRatingsId()
                .forEach(id -> ratingRepository.changeStudentRatingApproveStage(DECLINED, id));
        List<Rating> ratingList = actionRatingDTO.getRatingsId().stream()
                .map(id -> ratingRepository.findById(id).orElseThrow(() -> new StudentRatingBaseException(404, "Wrong rating id"))).collect(Collectors.toList());
        StringBuilder builder = new StringBuilder();
        builder.append("Шановний ")
                .append(to.getName())
                .append(". Було відхилено такі блоки рейтингу:\n");
        ratingList.forEach(rating ->
                builder
                        .append(rating.getParagraph().getName())
                        .append("\nДата заповнення:")
                        .append(rating.getDate())
        );
        builder.append("\nПо причині - ")
                .append(actionRatingDTO.getComment())
                .append("\nВідхилив(ла) студент - ")
                .append(from.getName());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Відхилення рейтингу");
        message.setTo(to.getEmail());
        message.setFrom(from.getEmail());
        message.setText(builder.toString());
        emailSender.send(message);
        LOG.debug("Email was successfully send to receiver {} from {}", to.getStudentName(), from.getStudentName());
    }
}
