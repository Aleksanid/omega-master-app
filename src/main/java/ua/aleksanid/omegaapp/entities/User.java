package ua.aleksanid.omegaapp.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "APP_USER")
public class User {
    @Id
    @SequenceGenerator(name = "user_id_gen", sequenceName = "user_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "user_id_gen")
    private Long id;

    @Column(unique = true, length = 64)
    private String login;

    @Column(length = 512)
    private String password;
}
