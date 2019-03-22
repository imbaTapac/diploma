package com.student.rating.service.impl;

import com.student.rating.dto.RejectRatingDTO;
import com.student.rating.entity.Student;
import com.student.rating.repository.StudentRepository;
import com.student.rating.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


/**
 * Created by Тарас on 09.06.2018.
 */
@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger LOG = LoggerFactory.getLogger(EmailServiceImpl.class);
    private final JavaMailSender emailSender;
    private final StudentRepository studentRepository;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender, StudentRepository studentRepository) {
        this.emailSender = mailSender;
        this.studentRepository = studentRepository;
    }


    @Override
    public void sendMail(RejectRatingDTO rejectRatingDTO) {
        LOG.debug("Sending email to student with id {}", rejectRatingDTO.getStudentId());
        Student to = studentRepository.findById(rejectRatingDTO.getStudentId());
        String studentName = SecurityContextHolder.getContext().getAuthentication().getName();
        Student from = studentRepository.findByUsername(studentName);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Відхилення рейтингу");
        message.setTo(to.getEmail());
        message.setFrom(from.getEmail());
        message.setText(rejectRatingDTO.getComment());
        emailSender.send(message);
        LOG.debug("Email was successfully send to receiver {} from {}", to.getStudentName(), from.getStudentName());
    }
}
