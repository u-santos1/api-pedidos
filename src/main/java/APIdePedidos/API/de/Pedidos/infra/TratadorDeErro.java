package APIdePedidos.API.de.Pedidos.infra;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.reflect.Field;
import java.util.List;

@ControllerAdvice
public class TratadorDeErro {
    @ExceptionHandler(PedidoNaoEncotradoException.class)
    public ResponseEntity<DadosErroSimples> tratarPedidoNaoEncontrado(PedidoNaoEncotradoException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DadosErroSimples(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DadosErrosValidacao>> tratarErroValidacao(MethodArgumentNotValidException exception){
        return ResponseEntity.badRequest().body(exception
                .getFieldErrors()
                .stream()
                .map(DadosErrosValidacao::new)
                .toList());
    }

    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<DadosErroSimples> tratarEmailDuplicado(EmailJaCadastradoException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new DadosErroSimples(exception.getMessage()));
    }

private record DadosErrosValidacao(String campo, String mensagem){
    public DadosErrosValidacao(FieldError error){
        this(error.getField(), error.getDefaultMessage());
    }
}
public record DadosErroSimples(String mensagem){}
}
