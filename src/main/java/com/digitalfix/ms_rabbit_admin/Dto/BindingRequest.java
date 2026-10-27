package com.digitalfix.msrabbitadmin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BindingRequest(

    @NotBlank(message = "La cola es obligatoria")
    String queue,

    @NotBlank(message = "El exchange es obligatorio")
    String exchange,

    @NotNull(message = "La routing key no puede ser null")
    String routingKey

) {}