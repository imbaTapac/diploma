package com.student.rating.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

public class ResultRatingDTO implements Serializable {

    @JsonProperty("studentId")
    private Long studentId;
    @JsonProperty("userName")
    private String studentUserName;
    @JsonProperty("ratingId")
    private Long ratingId;
    @JsonProperty("paragraphId")
    private Long paragraphId;
    @JsonProperty("paragraphName")
    private String paragraphName;
    @JsonProperty("stageOfApprove")
    private Long stageOfApprove;
    @JsonProperty("date")
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "EET")
    private Date date;
    @JsonProperty("score")
    private Double score;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentUserName() {
        return studentUserName;
    }

    public void setStudentUserName(String studentUserName) {
        this.studentUserName = studentUserName;
    }

    public Long getRatingId() {
        return ratingId;
    }

    public void setRatingId(Long ratingId) {
        this.ratingId = ratingId;
    }

    public Long getParagraphId() {
        return paragraphId;
    }

    public void setParagraphId(Long paragraphId) {
        this.paragraphId = paragraphId;
    }

    public String getParagraphName() {
        return paragraphName;
    }

    public void setParagraphName(String paragraphName) {
        this.paragraphName = paragraphName;
    }

    public Long getStageOfApprove() {
        return stageOfApprove;
    }

    public void setStageOfApprove(Long stageOfApprove) {
        this.stageOfApprove = stageOfApprove;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
