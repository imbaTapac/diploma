package student_rating.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Тарас on 05.03.2018.
 */
@Entity
@Table(name = "rating")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
public class Rating implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rating")
    private Long id;

    @Column
    private Double score;

    @Column(columnDefinition = "INTEGER(1)",name = "stage_of_approve")
    private int stageOfApprove;

    @Column(columnDefinition = "VARCHAR(256)")
    private String comment;

    @Temporal(TemporalType.DATE)
    @Column
    private Date date;


    @ManyToOne
    @JoinColumn(name = "id_student")
    public Student student;


    @ManyToOne
    @JoinColumn(name = "id_paragraph")
    public Paragraph paragraph;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public int getStageOfApprove() {
        return stageOfApprove;
    }

    public void setStageOfApprove(int stageOfApprove) {
        this.stageOfApprove = stageOfApprove;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

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

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", score=" + score +
                ", stageOfApprove=" + stageOfApprove +
                ", comment='" + comment + '\'' +
                ", date=" + date +
                ", student=" + student +
                ", paragraph=" + paragraph +
                '}';
    }
}
