package student_rating.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Тарас on 07.03.2018.
 */
@Entity
@Table(name = "paraghaph")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
public class Paragraph implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paragraph")
    private long id;

    @Column(columnDefinition = "NVARCHAR(512)")
    String name;

    @Column(columnDefinition = "NVARCHAR(6)")
    String score;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_subblock")
    public Subblock subblock;


    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "id_paragraph")
    public List<Rating> ratings;

    public Paragraph() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {this.id = id;}

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

    @Override
    public String toString() {
        return "Paragraph{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", score='" + score + '\'' +
                '}';
    }
}
