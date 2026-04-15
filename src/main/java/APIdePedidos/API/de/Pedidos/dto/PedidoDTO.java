package APIdePedidos.API.de.Pedidos.dto;

import APIdePedidos.API.de.Pedidos.model.Pedido;
import APIdePedidos.API.de.Pedidos.model.StatusPedido;

import java.time.LocalDateTime;
import java.util.Locale;

public record PedidoDTO(
        Long id,
        String usuarioNome,
        String usuarioEmail,
        StatusPedido statusPedido,
        LocalDateTime craidoEm
) {
    public static PedidoDTO dto(Pedido pedido){
        return new PedidoDTO(
                pedido.getId(),
                pedido.getUsuario().getNome(),
                pedido.getUsuario().getEmail(),
                pedido.getStatus(),
                pedido.getCriadoEm()
        );
    }
}
