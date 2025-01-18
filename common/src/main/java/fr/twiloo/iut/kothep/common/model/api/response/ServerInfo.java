package fr.twiloo.iut.kothep.common.model.api.response;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ServerInfo(
        @NotNull @NotEmpty
        String ip,
        int port) {
}
