package APIdePedidos.API.de.Pedidos.service;

import APIdePedidos.API.de.Pedidos.dto.PedidoDTO;
import APIdePedidos.API.de.Pedidos.dto.UsuarioComPedidosDTO;
import APIdePedidos.API.de.Pedidos.dto.UsuarioDTO;
import APIdePedidos.API.de.Pedidos.dto.UsuarioRequest;
import APIdePedidos.API.de.Pedidos.infra.EmailJaCadastradoException;
import APIdePedidos.API.de.Pedidos.model.Usuario;
import APIdePedidos.API.de.Pedidos.repository.PedidoRepository;
import APIdePedidos.API.de.Pedidos.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PedidoRepository pedidoRepository;

    @Transactional
    public UsuarioDTO criarUsuario(UsuarioRequest data){
        if (usuarioRepository.existsByEmail(data.email())){
            log.error("Tentativa de cadastro com email ja existente: {}", data.email());
            throw new EmailJaCadastradoException("Este email ja esta cadastrado no sistema");
        }
        Usuario usuario = new Usuario();
        usuario.setEmail(data.email());
        usuario.setNome(data.nome());

        var salvo = usuarioRepository.save(usuario);

        return UsuarioDTO.dto(salvo);
    }

    public UsuarioComPedidosDTO listarPedidosDoUsuario(Long usuarioLogadoId){
        Usuario usuario = usuarioRepository.findByIdComPedidos(usuarioLogadoId)
                .orElseThrow(()-> new EntityNotFoundException("Usuario nao encontrado"));

        return UsuarioComPedidosDTO.dto(usuario);}

}
