package StudentRating.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Тарас on 19.02.2018.
 */
@Entity(name = "rating_block")
public class RatingBlock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long id;
    @Column
    private String name;
    @Column
    private String how_many_times;
    @Column
    private double scores;

    public RatingBlock(String name, String how_many_times, double scores) {
        this.name = name;
        this.how_many_times = how_many_times;
        this.scores = scores;
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

    public String getHow_many_times() {
        return how_many_times;
    }

    public void setHow_many_times(String how_many_times) {
        this.how_many_times = how_many_times;
    }

    public double getScores() {
        return scores;
    }

    public void setScores(double scores) {
        this.scores = scores;
    }
}
