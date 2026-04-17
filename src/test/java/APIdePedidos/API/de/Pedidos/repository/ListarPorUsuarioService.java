package APIdePedidos.API.de.Pedidos.repository;



import APIdePedidos.API.de.Pedidos.dto.PedidoDTO;
import APIdePedidos.API.de.Pedidos.model.ItemPedido;
import APIdePedidos.API.de.Pedidos.model.Pedido;
import APIdePedidos.API.de.Pedidos.model.StatusPedido;
import APIdePedidos.API.de.Pedidos.model.Usuario;
import APIdePedidos.API.de.Pedidos.service.PedidoService;
import net.bytebuddy.build.ToStringPlugin;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.setRemoveAssertJRelatedElementsFromStackTrace;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListarPorUsuarioService {

    @Mock
    PedidoRepository pedidoRepository;

    @Mock
    UsuarioRepository usuarioRepository;

    @InjectMocks
    PedidoService pedidoService;

    @Test
    void deveRetornarPedidosComItens(){
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("joao");
        usuario.setEmail("joao@email.com");

        ItemPedido item = new ItemPedido();
        item.setId(1L);

        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setUsuario(usuario);
        pedido.setStatus(StatusPedido.PENDENTE);
        pedido.adicionarItem(item);

        when(pedidoRepository.buscarComItens(1L))
                .thenReturn(List.of(pedido));

        var resultado = pedidoService.listarPorUsuario(1L);

        // 6. Verifica com assertEquals
        assertEquals(1, resultado.size());

        // Verifica se o mapeamento para DTO funcionou (assumindo que PedidoDTO tem esses getters)
        var primeiroPedido = resultado.get(0);
        assertEquals(1L, primeiroPedido.id());
        assertEquals(StatusPedido.PENDENTE, primeiroPedido.statusPedido());

        // Garante que o repositório foi chamado exatamente uma vez com o ID correto
        verify(pedidoRepository, times(1)).buscarComItens(1L);


    }
    @Test
    void deveCancelarPentendetes(){

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("joao");
        usuario.setEmail("joao@email.com");

        ItemPedido itens = new ItemPedido();
        itens.setId(1L);

        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setUsuario(usuario);
        pedido.setStatus(StatusPedido.PENDENTE);
        pedido.adicionarItem(itens);

        when(pedidoRepository.cancelarPendentesPorUsuario(1L))
                .thenReturn(2);

        var resultado = pedidoService.cancelarPendentes(1L);

        assertEquals(2, resultado);
        verify(pedidoRepository).cancelarPendentesPorUsuario(1L);
    }



}
