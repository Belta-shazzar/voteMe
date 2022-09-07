package com.shazzar.voteme.entity;


import com.shazzar.voteme.entity.role.AppUserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
//    @Email(regexp = "[a-z]*@gmail.com",
//            message = "entered email not valid")
    private String email;

    @Column(nullable = false)
    private String password; //test = password //[a-zA-Z0-9]{8}

    @Enumerated(EnumType.STRING)
    private AppUserRole role;

    @ManyToMany(mappedBy = "voters", cascade = CascadeType.MERGE)
    private Set<Candidate> votedCandidates;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "election_event_id",
            referencedColumnName = "id",
            nullable = false)
    private ElectionEvent event;
}
