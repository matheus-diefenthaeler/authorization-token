package br.com.letscode.matheus.authenticate.controler.exceptions;

import br.com.letscode.matheus.authenticate.service.exceptions.PermissionDeniedException;
import br.com.letscode.matheus.authenticate.service.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    HttpStatus status = HttpStatus.I_AM_A_TEAPOT;

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(
            UserNotFoundException e, HttpServletRequest request) {
        status = HttpStatus.NOT_FOUND;
        var error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("User not found!");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(PermissionDeniedException.class)
    public ResponseEntity<StandardError> permissionDenied(
            PermissionDeniedException e, HttpServletRequest request) {
        status = HttpStatus.BAD_REQUEST;
        var error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Permission denied!");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

}
