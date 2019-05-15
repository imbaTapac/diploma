package com.student.rating.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Тарас on 16.06.2018.
 */

public class AvgReportByGroupDTO implements Serializable {

    @JsonProperty("number")
    private Integer number;
    @JsonProperty("groupName")
    private String groupName;
    @JsonProperty("score")
    private Double score = 0.0;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "AvgReportByGroupDTO{" +
                "number=" + number +
                ", groupName='" + groupName + '\'' +
                ", score=" + score +
                '}';
    }
}

