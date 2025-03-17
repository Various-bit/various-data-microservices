package org.example.authorization.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name="users")
public class User {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="username")
    private String username;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="uuid")
    private UUID uuid;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.uuid = UUID.randomUUID();
    }

    public User() {}
}
