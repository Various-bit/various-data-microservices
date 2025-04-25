package org.example.scrap.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.UUID;

@Entity
@Table(name="site_order")
@Getter
@Setter
public class SiteOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="uuid")
    private UUID uuid;

    @Column(name="email")
    private String email;

    @Column(name="describe")
    private String describe;

    @Column(name="link")
    private String link;

    @Column(name="create_date")
    private Date createDate;

    @Column(name="user_uuid")
    private UUID userUuid;

    public SiteOrder() {}

}
