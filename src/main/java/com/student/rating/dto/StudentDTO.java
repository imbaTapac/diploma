package com.student.rating.dto;

import static com.student.rating.constants.Constants.APPROVED_BY_HEAD_OF_GROUP;
import static com.student.rating.constants.Constants.APPROVED_BY_HEAD_OF_SO;
import static com.student.rating.constants.Constants.DECLINED;
import static com.student.rating.constants.Constants.UNAPPROVED;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.student.rating.entity.Rating;

public class StudentDTO implements Serializable {

	@JsonProperty("studentName")
	private String studentName;

	@JsonProperty("studentSurname")
	private String studentSurname;

	@JsonProperty("phone")
	private String phone;

	@JsonProperty("ratingStatus")
	private int ratingStatus;

	private transient List<Rating> ratings = new ArrayList<>();

	public StudentDTO() {
		//default constructor
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentSurname() {
		return studentSurname;
	}

	public void setStudentSurname(String studentSurname) {
		this.studentSurname = studentSurname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getRatingStatus() {
		return ratingStatus;
	}

	public void setRatingStatus(int ratingStatus) {
		this.ratingStatus = ratingStatus;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	public void filterRatings() {
		if(ratings.isEmpty()) {
			ratingStatus = -2;
		} else if(this.ratings.stream().allMatch(rating -> rating.getStageOfApprove().equals(DECLINED))) {
			this.ratingStatus = DECLINED;
		} else if(this.ratings.stream().anyMatch(rating -> rating.getStageOfApprove().equals(UNAPPROVED))) {
			this.ratingStatus = UNAPPROVED;
		} else if(this.ratings.stream().anyMatch(rating -> rating.getStageOfApprove().equals(APPROVED_BY_HEAD_OF_GROUP))
			&& this.ratings.stream().noneMatch(rating -> rating.getStageOfApprove().equals(UNAPPROVED))) {
			this.ratingStatus = APPROVED_BY_HEAD_OF_GROUP;
		} else if(this.ratings.stream().anyMatch(rating -> rating.getStageOfApprove().equals(APPROVED_BY_HEAD_OF_SO))
				&& this.ratings.stream().noneMatch(rating -> rating.getStageOfApprove().equals(APPROVED_BY_HEAD_OF_GROUP))) {
			this.ratingStatus = APPROVED_BY_HEAD_OF_SO;
		}
	}

	@Override
	public String toString() {
		return "StudentDTO{" +
				"studentName='" + studentName + '\'' +
				", studentSurname='" + studentSurname + '\'' +
				", phone='" + phone + '\'' +
				", ratingStatus=" + ratingStatus +
				'}';
	}
}
