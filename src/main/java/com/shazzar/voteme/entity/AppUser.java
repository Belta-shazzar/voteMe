package com.shazzar.voteme.entity;


import com.google.common.collect.Sets;
import com.shazzar.voteme.entity.role.AppUserRole;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
//    @Column(unique = true)
    private String email;
    private String password; //test = password

    @Enumerated(EnumType.STRING)
    private AppUserRole role;
    
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "election_event_id",
            referencedColumnName = "id")
    private ElectionEvent event;
    
//    For candidates only
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "positionId")
    private Position position;
    private Boolean isAccountNonLocked;
    private Boolean isEnabled;

    public AppUser(String fullName, String email,
                   String password, AppUserRole role) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.isAccountNonLocked = true;
        this.isEnabled = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Sets.newHashSet(role.getGrantedAuthorities());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AppUser user = (AppUser) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
