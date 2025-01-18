package fr.twiloo.iut.kothep.service;

import fr.twiloo.iut.kothep.PasswordEncoder;
import fr.twiloo.iut.kothep.common.model.api.request.LoginUser;
import fr.twiloo.iut.kothep.common.model.api.request.RegisterUser;
import fr.twiloo.iut.kothep.entity.User;
import fr.twiloo.iut.kothep.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Validated
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserByPseudo(String pseudo) {
        return userRepository.findByPseudo(pseudo);
    }

    @Transactional
    public Optional<User> createFromRequest(@Valid RegisterUser registerUser) throws InvalidPropertyException {
        if (!isPasswordValid(registerUser.password()))
            throw new InvalidPropertyException(User.class, "password", "Password must comply with requirements (at least 8 characters, one uppercase letter, one lowercase and one digit)");

        if (getUserByEmail(registerUser.email()).isPresent())
            throw new InvalidPropertyException(User.class, "email", "This email address can't be used");

        User user = new User(registerUser.email(), registerUser.pseudo(), registerUser.password());
        userRepository.save(user);
        return Optional.of(user);
    }

    @Transactional
    public User login(@Valid LoginUser loginUser) throws InvalidPropertyException {
        User user = getUserByPseudo(loginUser.pseudo()).orElse(null);
        if (user == null)
            throw new InvalidPropertyException(User.class, "pseudo", "Player doesn't exist");

        if (!PasswordEncoder.matchPassword(loginUser.password(), user.getPassword()))
            throw new InvalidPropertyException(User.class, "password", "Password is incorrect");

        return user;
    }

    private boolean isPasswordValid(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,100}$");
    }

}
