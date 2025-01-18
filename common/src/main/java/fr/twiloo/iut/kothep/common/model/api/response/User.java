package fr.twiloo.iut.kothep.common.model.api.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record User(
        long id,
        @NotNull @NotEmpty @Email
        String email,
        @NotNull @NotEmpty
        String pseudo,
        int elo) {
}
