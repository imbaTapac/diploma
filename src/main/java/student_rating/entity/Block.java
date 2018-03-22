package student_rating.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Тарас on 19.02.2018.
 */
@Entity
@Table(name = "block")
public class Block implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column
    private long id_block;

    @Column
    private String name;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_block")
    private List<Subblock> subblock = new ArrayList<>();


    public Block() {

    }

    public long getId_block() {
        return id_block;
    }

    public void setId_block(long id_block) {
        this.id_block = id_block;
    }

    public Block(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public List<Subblock> getSubblock() {
        return subblock;
    }

    public void setSubblock(List<Subblock> subblock) {
        this.subblock = subblock;
    }
}
