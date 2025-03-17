package org.example.subscribe.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.Calendar;
import java.util.UUID;

@Entity
@Getter
@Setter
public class UserAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "date")
    private Date date;

    @Column(name = "Authority")
    private String authority;

    public UserAuthority(UUID uuid) {
        Calendar cal = Calendar.getInstance();
        this.uuid = uuid;
        this.date = new Date(System.currentTimeMillis());
        this.authority = Authority.USER.name();
    }

    public UserAuthority() {}
}
