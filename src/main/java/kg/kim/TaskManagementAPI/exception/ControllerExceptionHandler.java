package kg.kim.TaskManagementAPI.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(NotValidException.class)
    public ResponseEntity<String> handleNotValidException(NotValidException exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();


        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
