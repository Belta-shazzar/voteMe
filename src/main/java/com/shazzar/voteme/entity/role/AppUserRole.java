package com.shazzar.voteme.entity.role;



import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.shazzar.voteme.entity.role.AppUserPermission.*;

public enum AppUserRole {
    USER(Sets.newHashSet(USER_READ, USER_WRITE, POSITION_READ)),
    CANDIDATE(Sets.newHashSet(USER_READ, USER_WRITE, POSITION_READ)),
    ADMIN(Sets.newHashSet(USER_READ, USER_WRITE, POSITION_READ, POSITION_WRITE,EVENT_READ, EVENT_WRITE));

    private final Set<AppUserPermission> permissions;

    AppUserRole(Set<AppUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<AppUserPermission> getPermissions() {
        return permissions;
    }

    public Set<? extends GrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        permissions.add(new SimpleGrantedAuthority("Role_" + this.name()));
        return permissions;
    }

}
