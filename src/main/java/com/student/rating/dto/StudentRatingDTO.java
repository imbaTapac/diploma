package com.student.rating.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StudentRatingDTO implements Serializable {

	@JsonProperty("blockName")
	private String paragraphSubblockBlockName;
	@JsonProperty("subblockName")
	private String paragraphSubblockName;
	@JsonProperty("paragraphName")
	private String paragraphName;
	@JsonProperty("date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone = "EET")
	private Date date;
	@JsonProperty("score")
	private Double score;
	@JsonProperty("comment")
	private String comment;
	@JsonProperty("status")
	private Integer stageOfApprove;

	public String getParagraphSubblockBlockName() {
		return paragraphSubblockBlockName;
	}

	public void setParagraphSubblockBlockName(String paragraphSubblockBlockName) {
		this.paragraphSubblockBlockName = paragraphSubblockBlockName;
	}

	public String getParagraphSubblockName() {
		return paragraphSubblockName;
	}

	public void setParagraphSubblockName(String paragraphSubblockName) {
		this.paragraphSubblockName = paragraphSubblockName;
	}

	public String getParagraphName() {
		return paragraphName;
	}

	public void setParagraphName(String paragraphName) {
		this.paragraphName = paragraphName;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getStageOfApprove() {
		return stageOfApprove;
	}

	public void setStageOfApprove(Integer stageOfApprove) {
		this.stageOfApprove = stageOfApprove;
	}

	@Override
	public String toString() {
		return "StudentRatingDTO{" +
				"paragraphSubblockBlockName='" + paragraphSubblockBlockName + '\'' +
				", paragraphSubblockName='" + paragraphSubblockName + '\'' +
				", paragraphName='" + paragraphName + '\'' +
				", date=" + date +
				", score=" + score +
				", comment='" + comment + '\'' +
				", status='" + stageOfApprove + '\'' +
				'}';
	}
}
