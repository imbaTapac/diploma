package com.student.rating.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class UserDTO implements Serializable {

    @JsonProperty("course")
    private String course;
	@JsonProperty("specialtyName")
	private String specialtyName;
    @JsonProperty("groupName")
    private String groupName;
    @JsonProperty("roleCode")
    private String roleCode = "";
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("password")
    private String password;
    @JsonProperty("confirmPassword")
    private String confirmPassword;

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

	public String getSpecialtyName() {
		return specialtyName;
	}

	public void setSpecialtyName(String specialtyName) {
		this.specialtyName = specialtyName;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

	@Override
	public String toString() {
		return "UserDTO{" +
				"course='" + course + '\'' +
				", specialtyName='" + specialtyName + '\'' +
				", groupName='" + groupName + '\'' +
				", roleCode='" + roleCode + '\'' +
				", phone='" + phone + '\'' +
				", password='" + password + '\'' +
				", confirmPassword='" + confirmPassword + '\'' +
				'}';
	}
}
