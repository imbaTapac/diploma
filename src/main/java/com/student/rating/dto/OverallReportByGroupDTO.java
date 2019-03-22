package com.student.rating.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Тарас on 17.06.2018.
 */

public class OverallReportByGroupDTO implements Serializable {

    @JsonProperty("idStudent")
    private Long idStudent;
    @JsonProperty("studentSurname")
    private String studentSurname;
    @JsonProperty("studentName")
    private String studentName;
    @JsonProperty("groupName")
    private String groupName;
    @JsonProperty("blockName")
    private String blockName;
    @JsonProperty("subblockName")
    private String subblockName;
    @JsonProperty("paragraphName")
    private String paragraphName;
    @JsonProperty("score")
    private Double score;

    public Long getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(Long idStudent) {
        this.idStudent = idStudent;
    }

    public String getStudentSurname() {
        return studentSurname;
    }

    public void setStudentSurname(String studentSurname) {
        this.studentSurname = studentSurname;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getSubblockName() {
        return subblockName;
    }

    public void setSubblockName(String subblockName) {
        this.subblockName = subblockName;
    }

    public String getParagraphName() {
        return paragraphName;
    }

    public void setParagraphName(String paragraphName) {
        this.paragraphName = paragraphName;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "OverallReportByGroupDTO{" +
                "idStudent=" + idStudent +
                ", studentSurname='" + studentSurname + '\'' +
                ", studentName='" + studentName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", blockName='" + blockName + '\'' +
                ", subblockName='" + subblockName + '\'' +
                ", paragraphName='" + paragraphName + '\'' +
                ", score=" + score +
                '}';
    }
}
