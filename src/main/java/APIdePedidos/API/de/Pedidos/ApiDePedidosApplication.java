package APIdePedidos.API.de.Pedidos;

import APIdePedidos.API.de.Pedidos.model.Pedido;
import APIdePedidos.API.de.Pedidos.model.Usuario;
import APIdePedidos.API.de.Pedidos.repository.PedidoRepository;
import APIdePedidos.API.de.Pedidos.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiDePedidosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiDePedidosApplication.class, args);

	}
	@Bean
	CommandLineRunner seed(UsuarioRepository ur, PedidoRepository pr) {
		return args -> {
			Usuario u = new Usuario();
			u.setEmail("joao@email.com");
			u.setNome("João Silva");
			ur.save(u);

			Pedido p1 = new Pedido(); p1.setUsuario(u);
			Pedido p2 = new Pedido(); p2.setUsuario(u);
			pr.save(p1); pr.save(p2);
			System.out.println("Seed OK — usuario id: " + u.getId());
		};

	}

	@Bean
	public CommandLineRunner debug(PedidoRepository pedidoRepo, UsuarioRepository usuarioRepo) {
		return args -> {
			System.out.println("--- RELATÓRIO DE DADOS NO TERMINAL ---");

			long totalUsuarios = usuarioRepo.count();
			long totalPedidos = pedidoRepo.count();

			System.out.println("Usuários cadastrados: " + totalUsuarios);
			System.out.println("Pedidos cadastrados: " + totalPedidos);

			// Se quiser ver os detalhes:
			usuarioRepo.findAll().forEach(u -> System.out.println("User: " + u.getNome()));
			System.out.println("---------------------------------------");
		};
	}

	}
