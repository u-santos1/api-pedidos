package APIdePedidos.API.de.Pedidos.repository;

import APIdePedidos.API.de.Pedidos.model.Pedido;
import APIdePedidos.API.de.Pedidos.model.StatusPedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByUsuarioIdOrderByCriadoEmDesc(Long usuarioId);
    List<Pedido> findByStatus(StatusPedido status);

    // Se o parâmetro se chama 'id', na query usamos :id
    @Query("SELECT DISTINCT p FROM Pedido p LEFT JOIN FETCH p.itens WHERE p.usuario.id = :id")
    List<Pedido> buscarComItens(@Param("id") Long usuarioId);

    @Modifying
    @Transactional
    @Query("UPDATE Pedido p SET p.status = 'CANCELADO' WHERE p.usuario.id = :id AND p.status = 'PENDENTE'")
    int cancelarPendentesPorUsuario(@Param("id")Long usuarioId);


    @Query("SELECT DISTINCT p FROM Pedido p LEFT JOIN FETCH p.itens WHERE p.id = :id")
    Optional<Pedido> listarDePedido(@Param("id")Long pedidoId);


}
