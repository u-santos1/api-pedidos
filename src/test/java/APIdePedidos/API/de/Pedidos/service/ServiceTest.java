package APIdePedidos.API.de.Pedidos.service;

import APIdePedidos.API.de.Pedidos.model.ItemPedido;
import APIdePedidos.API.de.Pedidos.model.Pedido;
import APIdePedidos.API.de.Pedidos.model.StatusPedido;
import APIdePedidos.API.de.Pedidos.model.Usuario;
import APIdePedidos.API.de.Pedidos.repository.PedidoRepository;
import APIdePedidos.API.de.Pedidos.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @Mock
    PedidoRepository pedidoRepository;

    @Mock
    UsuarioRepository usuarioRepository;

    @InjectMocks
    PedidoService pedidoService;




    @Test
    void deveCriarPedidos(){
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
        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        when(pedidoRepository.save(any(Pedido.class)))
                .thenReturn(pedido);

        var resultado = pedidoService.criar(1L);
        assertEquals(1L, resultado.id());
        assertEquals("joao", resultado.usuarioNome());
        assertEquals("joao@email.com", resultado.usuarioEmail());
        assertEquals(StatusPedido.PENDENTE, resultado.statusPedido());
        verify(pedidoRepository).save(any(Pedido.class));
    }
}
