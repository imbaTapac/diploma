package com.student.rating.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Тарас on 10.06.2018.
 */
public class RejectRatingDTO implements Serializable {

    private Long studentId;
    private String comment;

    @JsonProperty("studentId")
    public Long getStudentId() {
        return studentId;
    }

    @JsonProperty("studentId")
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    @JsonProperty("comment")
    public String getComment() {
        return comment;
    }

    @JsonProperty("comment")
    public void setComment(String comment) {
        this.comment = comment;
    }
}
