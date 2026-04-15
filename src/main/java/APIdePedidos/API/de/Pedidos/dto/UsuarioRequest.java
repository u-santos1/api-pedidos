package APIdePedidos.API.de.Pedidos.dto;

import jakarta.validation.constraints.NotBlank;

public record UsuarioRequest(@NotBlank String email, @NotBlank String nome) {
}
