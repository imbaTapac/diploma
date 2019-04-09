package com.student.rating.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Created by Тарас on 19.02.2018.
 */
@Entity
@Table(name = "student")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
public class Student implements Serializable {
	@Id
	@Column(name = "id_student", nullable = false)
	private Long id;

	@Column(columnDefinition = "NVARCHAR(12)")
	private String username;

	@Column
	private String password;

	@Column(name = "student_name", columnDefinition = "NVARCHAR(56)")
	private String studentName;

	@Column(name = "student_surname", columnDefinition = "NVARCHAR(56)")
	private String studentSurname;

	@Column(name = "course", columnDefinition = "INTEGER(1)")
	private Integer course;

	@Column(columnDefinition = "NVARCHAR(12)")
	private String phone;

	@Column(columnDefinition = "NVARCHAR(128)")
	private String email;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, columnDefinition = "NVARCHAR(14)")
	private Role role;

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Rating> ratings = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "id_okr")
	private OKR okr;

	@ManyToOne
	@JoinColumn(name = "id_group")
	private Group group;

	@ManyToOne
	@JoinColumn(name = "id_specialty")
	private Specialty specialty;

	public Student() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Integer getCourse() {
		return course;
	}

	public void setCourse(Integer course) {
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	public OKR getOkr() {
		return okr;
	}

	public void setOkr(OKR okr) {
		this.okr = okr;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Specialty getSpecialty() {
		return specialty;
	}

	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}

	public String getName() {
		return this.studentName + " " + this.studentSurname;
	}

	public boolean isRatingFilled() {
		return this.ratings.isEmpty();
	}


	@Override
	public String toString() {
		return "Student{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", role='" + role + '\'' +
				", studentName='" + studentName + '\'' +
				", studentSurname='" + studentSurname + '\'' +
				", specialty='" + specialty + '\'' +
				", course=" + course +
				", phone='" + phone + '\'' +
				", email='" + email + '\'' +
				", okr=" + okr +
				", group=" + group +
				'}';
	}
}
