package APIdePedidos.API.de.Pedidos.infra;

public class PedidoNaoEncotradoException extends RuntimeException {
    public PedidoNaoEncotradoException(String message) {
        super(message);
    }
}
