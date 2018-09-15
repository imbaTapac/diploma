package student_rating.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import student_rating.DTO.RejectRatingDTO;
import student_rating.entity.Student;
import student_rating.repository.StudentRepository;


/**
 * Created by Тарас on 09.06.2018.
 */
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;
    private final StudentRepository studentRepository;
    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender,StudentRepository studentRepository) {
        this.emailSender = mailSender;
        this.studentRepository = studentRepository;
    }


    @Override
    public void sendMail(RejectRatingDTO rejectRatingDTO) {
        Student to = studentRepository.findById(rejectRatingDTO.getStudentId());
        String studentName = SecurityContextHolder.getContext().getAuthentication().getName();
        Student from = studentRepository.findByUsername(studentName);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Відхилення рейтингу");
        message.setTo(to.getEmail());
        message.setFrom(from.getEmail());
        message.setText(rejectRatingDTO.getComment());
        emailSender.send(message);
    }
}
