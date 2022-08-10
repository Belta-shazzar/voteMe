package com.shazzar.voteme.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class  Candidate {
    @Id
    @Column(name = "candidateId", nullable = false)
    private Long id;
    @Column(nullable = false)
    private String candidateFullName;
    
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "positionId", nullable = false)
    private Position position;
    
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "election_event_id",
            referencedColumnName = "id", nullable = false)
    private ElectionEvent event;
    
    @ManyToMany
    @JoinTable(name="candidate_vote",
            joinColumns=
            @JoinColumn(name="CANDIDATE_ID", referencedColumnName="candidateId"),
            inverseJoinColumns=
            @JoinColumn(name="VOTER_ID", referencedColumnName="id")
    )
    private Set<User> voters;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Candidate candidate = (Candidate) o;
        return id != null && Objects.equals(id, candidate.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "candidateFullName = " + candidateFullName + ", " +
                "position = " + position + ", " +
                "event = " + event + ")";
    }
}
