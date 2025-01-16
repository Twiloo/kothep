package fr.twiloo.iut.kothep.entity.listener;

import fr.twiloo.iut.kothep.PasswordEncoder;
import fr.twiloo.iut.kothep.entity.User;
import jakarta.persistence.PrePersist;

public class UserListener {

    @PrePersist
    public void prePersist(User user) {
        user.setPassword(PasswordEncoder.hashPassword(user.getPassword()));
    }

}
