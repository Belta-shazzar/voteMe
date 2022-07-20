package com.shazzar.voteme.entity.role;

public enum AppUserPermission {
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    EVENT_READ("event:read"),
    EVENT_WRITE("event:write"),
    POSITION_READ("position:read"),
    POSITION_WRITE("position:write");

    private final String permission;

    AppUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
