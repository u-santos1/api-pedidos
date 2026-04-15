package APIdePedidos.API.de.Pedidos.controller;

import APIdePedidos.API.de.Pedidos.dto.UsuarioComPedidosDTO;
import APIdePedidos.API.de.Pedidos.dto.UsuarioDTO;
import APIdePedidos.API.de.Pedidos.dto.UsuarioRequest;
import APIdePedidos.API.de.Pedidos.repository.UsuarioRepository;
import APIdePedidos.API.de.Pedidos.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> criarUsuario(@Valid @RequestBody UsuarioRequest data){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.criarUsuario(data));
    }


    @GetMapping("/{id}/pedidos")
    public ResponseEntity<UsuarioComPedidosDTO> listaDeUmUsuario(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.listarPedidosDoUsuario(id));
    }
}
