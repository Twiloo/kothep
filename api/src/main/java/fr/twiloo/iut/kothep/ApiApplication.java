package fr.twiloo.iut.kothep;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootApplication(scanBasePackageClasses = ApiApplication.class, exclude = {UserDetailsServiceAutoConfiguration.class})
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Configuration
    @EnableWebSecurity
    public static class SecurityConfig {
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            return http
                    .csrf(AbstractHttpConfigurer::disable)
                    .httpBasic(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(request -> request.anyRequest().permitAll())
                    .build();        }
    }

    @RestControllerAdvice
    public static class GlobalExceptionHandler {
        // Handle validation exception for bean validation
        @ExceptionHandler(ConstraintViolationException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ResponseEntity<List<String>> handleValidationExceptions(ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
            List<String> errorMessages = violations.stream()
                    .map(violation -> {
                        // Récupérer le chemin de la propriété (par exemple "getUserByEmail.arg0")
                        String propertyPath = violation.getPropertyPath().toString();
                        String message = violation.getMessage();
                        return String.format("Le champ '%s' %s", propertyPath, message);
                    })
                    .collect(Collectors.toList());

            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }

        // Handle MethodArgumentNotValidException for validation failures (from @Validated annotations)
        @ExceptionHandler(org.springframework.validation.BindException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ResponseEntity<List<String>> handleBindException(BindException ex) {
            List<String> errorMessages = ex.getBindingResult().getAllErrors().stream()
                    .map(error -> {
                        // Récupérer le nom des attributs (par exemple "email")
                        String field = ((FieldError) error).getField();
                        // Créer un message explicite avec le nom de l'attribut
                        return String.format("Le champ '%s' %s", field, error.getDefaultMessage());
                    })
                    .collect(Collectors.toList());

            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }
    }
}
