package de.gruppe1.studydash.configurations;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import de.gruppe1.studydash.exceptions.AppException;
import de.gruppe1.studydash.exceptions.JwtAuthException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    // App-Exceptions
    @ExceptionHandler(value = { AppException.class })
    public ResponseEntity<AppException> handleException(AppException ex) {
        return new ResponseEntity<>(ex, ex.getHttpStatus());
    }

    // TokenExpiredException, die nicht vom JwtAuthFilter abgefangen wurde
    @ExceptionHandler(value = { TokenExpiredException.class})
    public ResponseEntity<AppException> handleTokenExpiredException(TokenExpiredException ex) {
        AppException appException = new AppException("Anmeldetoken ist abgelaufen. Bitte erneut einloggen.", HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(appException, appException.getHttpStatus());
    }

    // JWTVerificationException, die nicht vom JwtAuthFilter abgefangen wurde
    @ExceptionHandler(value = { JWTVerificationException.class})
    public ResponseEntity<AppException> handleJWTVerificationException(JWTVerificationException ex) {
        AppException appException = new AppException("Anmeldetoken ist fehlerhaft. Bitte einloggen.", HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(appException, appException.getHttpStatus());
    }

    // JwtAuthException mit spezifischer Behandlung für TokenExpiredException und JWTVerificationException
    // in JwtAuthFilter
    @ExceptionHandler(value = { JwtAuthException.class })
    public ResponseEntity<AppException> handleJwtAuthException(JwtAuthException ex) {
        Throwable cause = ex.getCause();
        AppException appException;
        if (cause instanceof TokenExpiredException) {
            appException = new AppException("Anmeldetoken ist abgelaufen. Bitte erneut einloggen.", HttpStatus.UNAUTHORIZED);
        } else if (cause instanceof JWTVerificationException) {
            appException = new AppException("Anmeldetoken ist fehlerhaft. Bitte einloggen.", HttpStatus.UNAUTHORIZED);
        } else {
            appException = new AppException("Ein interner Fehler ist aufgetreten.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(appException, appException.getHttpStatus());
    }
}