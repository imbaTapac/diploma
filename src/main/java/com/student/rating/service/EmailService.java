package com.student.rating.service;

import com.student.rating.dto.RejectRatingDTO;

/**
 * Created by Тарас on 09.06.2018.
 */
public interface EmailService {
    void sendMail (RejectRatingDTO rejectRatingDTO);
}
