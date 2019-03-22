package com.student.rating.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Тарас on 07.03.2018.
 */
@Entity
@Table(name = "subblock")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
public class Subblock implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_subblock")
    private Long id;

    @Column(columnDefinition = "NVARCHAR(128)")
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_block")
    private Block block;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,orphanRemoval = true)
    @JoinColumn(name = "id_subblock")
    private List<Paragraph> paragraph = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
