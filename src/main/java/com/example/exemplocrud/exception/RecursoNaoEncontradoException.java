package com.example.exemplocrud.exception;

/**
 * Lançada quando um recurso solicitado não é encontrado no sistema.
 */
public class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String message) {
        super(message);
    }
}