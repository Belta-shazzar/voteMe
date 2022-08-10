package com.shazzar.voteme.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ElectionEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String eventName;

    @Column(unique = true)
    private String token;
    private LocalDateTime dateCreated;
    private LocalDateTime commenceDate;
    private LocalDateTime endDate;

    @OneToMany(mappedBy = "event",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    private Set<User> users;

    @OneToMany(mappedBy = "event",
            fetch = FetchType.EAGER,
            cascade = CascadeType.REMOVE)
    private Set<Position> positions;

    @OneToMany(mappedBy = "event",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    private Set<Candidate> candidates;

    public ElectionEvent(String eventName) {
        this.eventName = eventName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ElectionEvent that = (ElectionEvent) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "eventName = " + eventName + ", " +
                "token = " + token + ", " +
                "dateCreated = " + dateCreated + ", " +
                "commenceDate = " + commenceDate + ", " +
                "endDate = " + endDate + ")";
    }
}
