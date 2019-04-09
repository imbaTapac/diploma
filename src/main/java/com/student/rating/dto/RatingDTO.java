package com.student.rating.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Тарас on 01.06.2018.
 */
public class RatingDTO implements Serializable{

    @JsonProperty("paragraphId")
    private Long paragraphId;
    @JsonProperty("score")
    private Double score;
    @JsonProperty("comment")
    private String comment;

    public Long getParagraphId() {
        return paragraphId;
    }

    public void setParagraphId(Long paragraphId) {
        this.paragraphId = paragraphId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComent(String comment) {
        this.comment = comment;
    }

	@Override
	public String toString() {
		return "RatingDTO{" +
				"paragraphId=" + paragraphId +
				", score=" + score +
				", comment='" + comment + '\'' +
				'}';
	}
}
