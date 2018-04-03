package StudentRating.entity;

import javax.persistence.*;

/**
 * Created by Тарас on 19.02.2018.
 */
@Entity(name = "block")
public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long id;
    @Column
    private String name;

    public Block(String name) {
        this.name = name;
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
}
