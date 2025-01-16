package fr.twiloo.iut.kothep.repository;

import fr.twiloo.iut.kothep.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByPseudo(String pseudo);

}
