package student_rating.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Тарас on 03.04.2018.
 */
@Entity
@Table(name="okr")
public class OKR implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id_okr;

    @Column(columnDefinition = "NVARCHAR(10)")
    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_okr")
    public List<Student> student;

    public OKR() {
    }

    public long getId_okr() {
        return id_okr;
    }

    public void setId_okr(long id_okr) {
        this.id_okr = id_okr;
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

}
