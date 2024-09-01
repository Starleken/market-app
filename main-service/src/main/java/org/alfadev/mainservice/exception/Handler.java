package org.alfadev.mainservice.exception;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import static org.alfadev.mainservice.utils.ExceptionUtils.toError;

@ControllerAdvice
public class Handler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Error> handler(EntityNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(toError(ex), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalActionException.class)
    public ResponseEntity<Error> handler(IllegalActionException ex, WebRequest request) {
        return new ResponseEntity<>(toError(ex), HttpStatus.BAD_REQUEST);
    }
}
