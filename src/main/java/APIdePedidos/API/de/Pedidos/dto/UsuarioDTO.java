package APIdePedidos.API.de.Pedidos.dto;

import APIdePedidos.API.de.Pedidos.model.Usuario;

public record UsuarioDTO(Long id, String nome, String email) {

    public static UsuarioDTO dto(Usuario usuario){
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail()
        );
    }
}
