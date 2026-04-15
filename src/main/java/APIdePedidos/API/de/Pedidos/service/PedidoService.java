package APIdePedidos.API.de.Pedidos.service;

import APIdePedidos.API.de.Pedidos.dto.PedidoDTO;
import APIdePedidos.API.de.Pedidos.dto.UsuarioDTO;
import APIdePedidos.API.de.Pedidos.infra.PedidoNaoEncotradoException;
import APIdePedidos.API.de.Pedidos.model.Pedido;
import APIdePedidos.API.de.Pedidos.model.Usuario;
import APIdePedidos.API.de.Pedidos.repository.PedidoRepository;
import APIdePedidos.API.de.Pedidos.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public PedidoDTO criar(Long usuarioId){
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario nao encontrado"));

        Pedido pedido = new Pedido();

        pedido.setUsuario(usuario);
        pedido.getItens().forEach(item -> item.setPedido(pedido));
        Pedido salvar = pedidoRepository.save(pedido);
        return PedidoDTO.dto(salvar);
    }

    @Transactional
    public Pedido salvar(Pedido pedido){
        if(pedido.getItens() != null){
            pedido.getItens().forEach(itemPedido -> itemPedido.setPedido(pedido));
        }
        return pedidoRepository.save(pedido);
    }

    public List<PedidoDTO> listarPorUsuario(Long usuarioId){
        return pedidoRepository.buscarComItens(usuarioId).stream()
                .map(PedidoDTO::dto).toList();
    }

    @Transactional
    public int cancelarPendentes(Long usuarioId){
        return pedidoRepository.cancelarPendentesPorUsuario(usuarioId);
    }

    public PedidoDTO listarPorPedido(Long pedidoId){
        Pedido pedido = pedidoRepository.listarDePedido(pedidoId)
                .orElseThrow(()-> new PedidoNaoEncotradoException("Pedido nao encontrado"));
        return PedidoDTO.dto(pedido);
    }


}
