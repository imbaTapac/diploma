package com.student.rating.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.student.rating.entity.Group;

public class GroupDTO implements Serializable {

	@JsonProperty("groupName")
	private String name;

	@JsonProperty("students")
	private List<StudentDTO> students;

	public GroupDTO(Group group,List<StudentDTO> students){
		this.name = group.getName();
		this.students = students;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<StudentDTO> getStudents() {
		return students;
	}

	public void setStudents(List<StudentDTO> students) {
		this.students = students;
	}

	@Override
	public String toString() {
		return "GroupDTO{" +
				"name='" + name + '\'' +
				", students=" + students +
				'}';
	}
}
