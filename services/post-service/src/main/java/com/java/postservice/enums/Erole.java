package com.java.postservice.enums;

import lombok.Getter;

@Getter
public enum Erole {
    END_USER("End user"),
    GUEST("Guest"),
    ADMINISTRATOR("Administrator");

    private String roleName;

    Erole(String roleName) {
        this.roleName = roleName;
    }

}
