package com.java.userservice.domain;

import com.java.userservice.enums.Erole;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "roles")
@Access(AccessType.FIELD)
@Getter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Erole erole;

    @Column(length = 100)
    private String description;

    public Role() {
    }

    public Role(Erole erole, String description) {
        this.erole = erole;
        this.description = description;
    }
}
