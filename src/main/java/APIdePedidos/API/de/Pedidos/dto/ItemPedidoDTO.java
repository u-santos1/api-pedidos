package APIdePedidos.API.de.Pedidos.dto;

import APIdePedidos.API.de.Pedidos.model.ItemPedido;

import java.math.BigDecimal;

public record ItemPedidoDTO(Long id, String nome, Integer quantidade, BigDecimal preco) {

    public static ItemPedidoDTO dto(ItemPedido itemPedido){
        return new ItemPedidoDTO(
                itemPedido.getId(),
                itemPedido.getNome(),
                itemPedido.getQuantidade(),
                itemPedido.getPreco()
        );
    }
}
