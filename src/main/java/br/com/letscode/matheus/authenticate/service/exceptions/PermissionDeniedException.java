package br.com.letscode.matheus.authenticate.service.exceptions;

public class PermissionDeniedException extends RuntimeException{
    public PermissionDeniedException(String msg){
        super(msg);
    }
}
