package student_rating.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by Тарас on 19.02.2018.
 */
@Entity
@Table(name = "student")
@JsonIgnoreProperties(value = {"roles"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_student")
    private long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column(name = "student_name", columnDefinition = "VARCHAR(56)")
    private String studentName;

    @Column(name = "student_surname", columnDefinition = "VARCHAR(56)")
    private String studentSurname;

    @Column(name = "course",columnDefinition = "INTEGER(1)")
    private Integer course;

    @Column(columnDefinition = "VARCHAR(18)")
    private String phone;

    @Column(columnDefinition = "VARCHAR(128)")
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "student_roles", joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> roles;


    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "id_student")
    public List<Rating> ratings;


    @ManyToOne
    @JoinColumn(name = "id_okr")
    private OKR okr;

    @ManyToOne
    @JoinColumn(name = "id_group")
    private Group group;


    public Student() {
    }

    public Student(String username, String password, String studentName, String studentSurname, String phone) {
        this.username = username;
        this.password = password;
        this.studentName = studentName;
        this.studentSurname = studentSurname;
        this.phone = phone;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
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

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", studentName='" + studentName + '\'' +
                ", studentSurname='" + studentSurname + '\'' +
                ", course=" + course +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", okr=" + okr +
                ", group=" + group +
                '}';
    }
}
