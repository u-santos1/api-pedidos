package APIdePedidos.API.de.Pedidos.repository;


import APIdePedidos.API.de.Pedidos.model.Pedido;
import APIdePedidos.API.de.Pedidos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByNomeContainingIgnoreCase(String nome);
    boolean existsByEmail(String email);


    @Query("SELECT DISTINCT u FROM Usuario u LEFT JOIN FETCH u.pedidos WHERE u.id = :id")
    Optional<Usuario> findByIdComPedidos(@Param("id") Long id);
}

