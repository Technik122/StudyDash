package de.gruppe1.studydash.exceptions;

public class JwtAuthException extends RuntimeException {
    public JwtAuthException(Throwable cause) {
        super(cause);
    }
}