package APIdePedidos.API.de.Pedidos.controller;

import APIdePedidos.API.de.Pedidos.dto.PedidoDTO;
import APIdePedidos.API.de.Pedidos.dto.PrecoPopularDTO;
import APIdePedidos.API.de.Pedidos.model.Pedido;
import APIdePedidos.API.de.Pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController{

    private final PedidoService pedidoService;

    @PostMapping("/usuario/{id}")
    public ResponseEntity<PedidoDTO> criar(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pedidoService.criar(id));
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<PedidoDTO>> listar(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.listarPorUsuario(id));
    }
    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> listarPedido(@PathVariable Long id){
        return ResponseEntity.ok(pedidoService.listarPorPedido(id));
    }
    @GetMapping("/precos-populares")
    public ResponseEntity<List<PrecoPopularDTO>> getPrecoPopulares(){
        var relatorio = pedidoService.listarPrecoPopulares();
        return ResponseEntity.ok(relatorio);
    }

    @DeleteMapping("/usuario/{id}/cancelar-pendentes")
    public ResponseEntity<String> cancelar(@PathVariable Long id) {
        int qtd = pedidoService.cancelarPendentes(id);
        return ResponseEntity.ok(qtd + " pedidos cancelados");
    }

}
