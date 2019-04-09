package com.student.rating.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Тарас on 03.04.2018.
 */
@Entity
@Table(name = "okr")
public class OKR implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_okr")
    private Long id;

    @Column(columnDefinition = "NVARCHAR(10)")
    private String name;

    @OneToMany
    @JoinColumn(name = "id_okr")
    private List<Student> student = new ArrayList<>();

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

    public List<Student> getStudent() {
        return student;
    }

    public void setStudent(List<Student> student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "OKR{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
