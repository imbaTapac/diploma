package student_rating.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Тарас on 05.03.2018.
 */
@Entity
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id_rating;

    @Column
    String score;

    @Column
    boolean ischecked;

    @Temporal(TemporalType.DATE)
    @Column
    Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_student")
    public Student student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_paragraph")
    public Paragraph paragraph;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Paragraph getParagraph() {
        return paragraph;
    }

    public void setParagraph(Paragraph paragraph) {
        this.paragraph = paragraph;
    }

    public long getId_rating() {
        return id_rating;
    }

    public void setId_rating(long id_rating) {
        this.id_rating = id_rating;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public boolean ischecked() {
        return ischecked;
    }

    public void setIschecked(boolean ischecked) {
        this.ischecked = ischecked;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
