package student_rating.service;

import student_rating.DTO.RejectRatingDTO;
import student_rating.entity.Student;

/**
 * Created by Тарас on 09.06.2018.
 */
public interface EmailService {
    void sendMail (RejectRatingDTO rejectRatingDTO);
}
