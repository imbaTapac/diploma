package com.student.rating.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Тарас on 10.06.2018.
 */
public class ActionRatingDTO implements Serializable {

	@JsonProperty("studentId")
    private Long studentId;
	@JsonProperty("ratingsId")
    private List<Long> ratingsId = new ArrayList<>();
	@JsonProperty("comment")
    private String comment;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

	public List<Long> getRatingsId() {
		return ratingsId;
	}

	public void setRatingsId(List<Long> ratingsId) {
		this.ratingsId = ratingsId;
	}

	public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

	@Override
	public String toString() {
		return "ActionRatingDTO{" +
				"studentId=" + studentId +
				", ratingsId=" + ratingsId +
				", comment='" + comment + '\'' +
				'}';
	}
}
