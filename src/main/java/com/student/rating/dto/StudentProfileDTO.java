package com.student.rating.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class StudentProfileDTO implements Serializable {
    @JsonProperty("studentName")
    private String studentName;

    @JsonProperty("studentSurname")
    private String studentSurname;

    @JsonProperty("facultyName")
    private String facultyName;

    @JsonProperty("specialtyName")
    private String specialtyName;

    @JsonProperty("okrName")
    private String okrName;

    @JsonProperty("groupName")
    private String groupName;

    @JsonProperty("course")
    private String course;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("email")
    private String email;

    @JsonProperty("isRatingFilled")
    private boolean ratingFilled;

    public StudentProfileDTO() {
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

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getSpecialtyName() {
        return specialtyName;
    }

    public void setSpecialtyName(String specialtyName) {
        this.specialtyName = specialtyName;
    }

    public String getOkrName() {
        return okrName;
    }

    public void setOkrName(String okrName) {
        this.okrName = okrName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isRatingFilled() {
        return ratingFilled;
    }

    public void setRatingFilled(boolean ratingFilled) {
        this.ratingFilled = ratingFilled;
    }

    @Override
    public String toString() {
        return "StudentProfileDTO{" +
                "studentName='" + studentName + '\'' +
                ", studentSurname='" + studentSurname + '\'' +
                ", facultyName='" + facultyName + '\'' +
                ", specialtyName='" + specialtyName + '\'' +
                ", okrName='" + okrName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", course='" + course + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", isRatingFilled=" + ratingFilled +
                '}';
    }
}
