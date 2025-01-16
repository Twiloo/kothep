package fr.twiloo.iut.kothep.common.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterUser(
        @NotNull @NotEmpty @Email
        String email,
        @NotNull @NotEmpty @Size(min = 5, max = 30)
        String pseudo,
        @NotNull @NotEmpty @Size(min = 8, max = 100)
        String password) {
}
