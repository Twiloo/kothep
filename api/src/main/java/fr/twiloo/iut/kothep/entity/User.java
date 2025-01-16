package fr.twiloo.iut.kothep.entity;

import fr.twiloo.iut.kothep.Config;
import fr.twiloo.iut.kothep.entity.listener.UserListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

@Entity
@Table(name = "users")
@Validated
@EntityListeners(UserListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private long id;

    @NotNull @NotEmpty @Email
    @Column(unique = true)
    private String email;

    @NotNull @NotEmpty @Size(min = 5, max = 30)
    private String pseudo;

    @NotNull @NotEmpty
    private String password;

    private int elo;

    public User() { }

    public User(String email, String pseudo, String password) {
        this.email = email;
        this.pseudo = pseudo;
        this.password = password;
        this.elo = (int) Config.BASE_ELO.value;
    }

    public fr.twiloo.iut.kothep.common.model.dto.response.User toDTO() {
        return new fr.twiloo.iut.kothep.common.model.dto.response.User(this.getId(), this.getEmail(), this.getPseudo(), this.getElo());
    }

    public long getId() {
        return id;
    }

    public User setId(long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPseudo() {
        return pseudo;
    }

    public User setPseudo(String pseudo) {
        this.pseudo = pseudo;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public int getElo() {
        return elo;
    }

    public User setElo(int elo) {
        this.elo = elo;
        return this;
    }
}
