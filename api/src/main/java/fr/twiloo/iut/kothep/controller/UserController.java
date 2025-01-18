package fr.twiloo.iut.kothep.controller;

import fr.twiloo.iut.kothep.common.model.api.request.RegisterUser;
import fr.twiloo.iut.kothep.common.model.api.response.User;
import fr.twiloo.iut.kothep.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/user")
@Tag(name = "Users", description = "Endpoints related to user login and registration")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail(
            @PathVariable("email")
            @NotNull @NotEmpty @Email
            String email) {
        return userService.getUserByEmail(email)
                .map(user -> new ResponseEntity<>(user.toDTO(), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @RequestBody
            @Valid
            RegisterUser registerUser) {
        try {
            return userService.createFromRequest(registerUser)
                    .map(user -> new ResponseEntity<>(user.toDTO(), HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (InvalidPropertyException e) {
            return new ResponseEntity<>(List.of(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
