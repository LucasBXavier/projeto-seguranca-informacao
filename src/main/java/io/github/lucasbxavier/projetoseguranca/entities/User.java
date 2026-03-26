package io.github.lucasbxavier.projetoseguranca.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;
    @PrePersist
    public void onCreate() {
        this.createdAt = OffsetDateTime.now(ZoneOffset.UTC);
    }
}
