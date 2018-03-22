package student_rating.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Тарас on 07.03.2018.
 */
@Entity
@Table(name = "paraghaph")
public class Paragraph implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id_paragraph;

    @Column
    String name;

    @Column
    String score;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_subblock")
    public Subblock subblock;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_paragraph")
    public List<Rating> ratings;

    public Paragraph() {
    }

    public long getId_paragraph() {
        return id_paragraph;
    }

    public void setId_paragraph(long id_paragraph) {this.id_paragraph = id_paragraph;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Subblock getSubblock() {
        return subblock;
    }

    public void setSubblock(Subblock subblock) {
        this.subblock = subblock;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
}
