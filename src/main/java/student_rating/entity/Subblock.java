package student_rating.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Тарас on 07.03.2018.
 */
@Entity
@Table(name = "subblock")
public class Subblock implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id_subblock;

    @Column
    String name;

    @ManyToOne
    @JoinColumn(name = "id_block")
    private Block block;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "id_subblock")
    public List<Paragraph> paragraph= new ArrayList<>();

    public Subblock() {
    }

    public long getId_subblock() {
        return id_subblock;
    }

    public void setId_subblock(long id_subblock) {
        this.id_subblock = id_subblock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public List<Paragraph> getParagraph() {
        return paragraph;
    }

    public void setParagraph(List<Paragraph> paragraph) {
        this.paragraph = paragraph;
    }
}
