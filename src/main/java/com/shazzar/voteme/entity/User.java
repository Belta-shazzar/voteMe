package com.shazzar.voteme.entity;


import com.shazzar.voteme.entity.role.AppUserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    @Column(unique = true)
    private String email;
    private String password; //test = password
    @Enumerated(EnumType.STRING)
    private AppUserRole role;

    @ManyToMany(mappedBy = "voters")
    private Set<Candidate> votedCandidates;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "election_event_id",
            referencedColumnName = "id", 
            nullable = false)
    private ElectionEvent event;
}
