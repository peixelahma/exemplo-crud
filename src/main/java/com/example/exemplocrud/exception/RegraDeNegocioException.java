package com.example.exemplocrud.exception;

/**
 * Lançada quando uma regra de negócio é violada.
 */
public class RegraDeNegocioException extends RuntimeException {

    public RegraDeNegocioException(String message) {
        super(message);
    }
}