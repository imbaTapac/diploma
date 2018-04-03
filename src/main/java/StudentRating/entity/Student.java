package StudentRating.entity;

import javax.persistence.*;

/**
 * Created by Тарас on 19.02.2018.
 */
@Entity(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long id;
    @Column
    private String login;
    @Column
    private String username;
    @Column
    private String student_name;
    @Column
    private String student_surname;
    @Column
    private String faculty;
    @Column
    private String phone;

    public Student() {
    }

    public Student(String login, String username, String student_name, String student_surname, String faculty, String phone) {
        this.login = login;
        this.username = username;
        this.student_name = student_name;
        this.student_surname = student_surname;
        this.faculty = faculty;
        this.phone = phone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_surname() {
        return student_surname;
    }

    public void setStudent_surname(String student_surname) {
        this.student_surname = student_surname;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
