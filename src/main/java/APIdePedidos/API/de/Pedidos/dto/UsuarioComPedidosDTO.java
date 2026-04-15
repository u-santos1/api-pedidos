package APIdePedidos.API.de.Pedidos.dto;

import APIdePedidos.API.de.Pedidos.model.Pedido;
import APIdePedidos.API.de.Pedidos.model.Usuario;

import java.util.List;

public record UsuarioComPedidosDTO(Long id, String nome, String email, List<PedidoDTO> pedidos) {
    public static UsuarioComPedidosDTO dto(Usuario u) {
        return new UsuarioComPedidosDTO(
                u.getId(), u.getNome(), u.getEmail(),
                u.getPedidos().stream().map(PedidoDTO::dto).toList()
        );
    }
}
