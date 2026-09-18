package com.cooxupe.libra.dto.preticket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// DTO de entrada, que recebera a unidade da balança e a placa enviadas pelo programa da balanca.

public record PreTicketRequest(

        @NotNull(message = "O código da unidade da balança é obrigatório")
        Long cdUnidadeBalanca,

        @NotBlank(message = "A placa é obrigatória")
        String placa

) {
}