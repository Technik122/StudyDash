package de.gruppe1.studydash.configurations;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import de.gruppe1.studydash.dtos.ErrorDto;

import de.gruppe1.studydash.exceptions.AppException;
import de.gruppe1.studydash.exceptions.JwtAuthException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {

    // App-Exceptions
    @ExceptionHandler(value = { AppException.class })
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(AppException ex) {
        return ResponseEntity.status(ex.getHttpStatus())
                .body(new ErrorDto(ex.getMessage()));
    }
    // TokenExpiredException, die nicht vom JwtAuthFilter abgefangen wurde
   @ExceptionHandler(value = { TokenExpiredException.class})
   @ResponseBody
    public ResponseEntity<ErrorDto> handleTokenExpiredException(TokenExpiredException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorDto("Anmeldetoken ist abgelaufen. Bitte erneut einloggen."));
    }

    // JWTVerificationException, die nicht vom JwtAuthFilter abgefangen wurde
    @ExceptionHandler(value = { JWTVerificationException.class})
    @ResponseBody
    public ResponseEntity<ErrorDto> handleJWTVerificationException(JWTVerificationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorDto("Anmeldetoken ist fehlerhaft. Bitte einloggen."));
    }

    // JwtAuthException mit spezifischer Behandlung für TokenExpiredException und JWTVerificationException
    // in JwtAuthFilter
    @ExceptionHandler(value = { JwtAuthException.class })
    @ResponseBody
    public ResponseEntity<ErrorDto> handleJwtAuthException(JwtAuthException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof TokenExpiredException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorDto("Anmeldetoken ist abgelaufen. Bitte erneut einloggen."));
        } else if (cause instanceof JWTVerificationException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorDto("Anmeldetoken ist fehlerhaft. Bitte einloggen."));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorDto("Ein interner Fehler ist aufgetreten."));
        }
    }
}
