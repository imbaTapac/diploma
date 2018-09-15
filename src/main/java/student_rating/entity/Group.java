package student_rating.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Тарас on 17.04.2018.
 */
@Entity
@Table(name="`group`")
public class Group implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_group")
    private long id;

    @Column(name = "group_name",columnDefinition = "NVARCHAR(20)")
    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_group")
    private List<Student> student;

    @ManyToOne
    @JoinColumn(name="id_faculty")
    private Faculty faculty;

    public Group() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    @Override
    public String toString() {
        return "Group{" +
                "faculty=" + faculty +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}

