package StudentRating.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Тарас on 19.02.2018.
 */
@Entity(name = "student_rating")
public class StudentRating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long id;
    @Column
    private Date fill_month;
    @Column
    private Date date;
    @Column
    private double sum_of_scores;

    public StudentRating(Date fill_month, Date date, double sum_of_scores) {
        this.fill_month = fill_month;
        this.date = date;
        this.sum_of_scores = sum_of_scores;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFill_month() {
        return fill_month;
    }

    public void setFill_month(Date fill_month) {
        this.fill_month = fill_month;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getSum_of_scores() {
        return sum_of_scores;
    }

    public void setSum_of_scores(double sum_of_scores) {
        this.sum_of_scores = sum_of_scores;
    }
}


