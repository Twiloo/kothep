package fr.twiloo.iut.kothep.common.model.dto.response;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ServerInfo(
        @NotNull @NotEmpty
        String ip,
        int port) {
}
