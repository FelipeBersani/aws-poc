package awspock.poc.sqs.example.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class HandlerException {

    @ExceptionHandler(HeroeNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> heroeNotFoundException(HeroeNotFoundException heroeNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
          .body(ErrorMessage.builder()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .errorMessage(heroeNotFoundException.getMessage())
            .timestamp(LocalDateTime.now().toString())
            .build());
    }
}

