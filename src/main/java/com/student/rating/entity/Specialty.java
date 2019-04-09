package com.student.rating.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Тарас on 03.04.2018.
 */
@Entity
@Table(name="specialty")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
public class Specialty implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_specialty")
    private Long id;

    @Column(columnDefinition = "NVARCHAR(64)")
    private String name;

    @Column(columnDefinition = "NVARCHAR(28)")
    private String code;

    @ManyToOne
    @JoinColumn(name="id_faculty")
    private Faculty faculty;

    @OneToMany
    @JoinColumn(name = "id_specialty")
    private List<Student> students = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	@Override
	public String toString() {
		return "Specialty{" +
				"id=" + id +
				", name='" + name + '\'' +
				", code='" + code + '\'' +
				", faculty=" + faculty +
				'}';
	}
}

