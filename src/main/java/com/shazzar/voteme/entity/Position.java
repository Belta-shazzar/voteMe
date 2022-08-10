package com.shazzar.voteme.entity;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String positionTitle;
    @ManyToOne
    private ElectionEvent event;

    @OneToOne
    private User holder;

    @OneToMany(mappedBy = "position", fetch = FetchType.EAGER)
    private Set<Candidate> aspirants;

    public Position(String positionTitle, ElectionEvent event) {
        this.positionTitle = positionTitle;
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Position position = (Position) o;
        return id != null && Objects.equals(id, position.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
