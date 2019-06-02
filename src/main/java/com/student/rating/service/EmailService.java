package com.student.rating.service;

import com.student.rating.dto.ActionRatingDTO;

/**
 * Created by Тарас on 09.06.2018.
 */
public interface EmailService {
    void sendMail (ActionRatingDTO actionRatingDTO);
}
