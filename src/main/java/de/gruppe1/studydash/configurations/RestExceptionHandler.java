package de.gruppe1.studydash.configurations;

import com.auth0.jwt.exceptions.TokenExpiredException;
import de.gruppe1.studydash.dtos.ErrorDto;

import de.gruppe1.studydash.exceptions.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = { AppException.class })
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(AppException ex) {
        return ResponseEntity.status(ex.getHttpStatus())
                .body(new ErrorDto(ex.getMessage()));
    }

   @ExceptionHandler(value = { TokenExpiredException.class})
   @ResponseBody
    public ResponseEntity<ErrorDto> handleTokenExpiredException(TokenExpiredException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorDto("Token has expired. Please log in again."));
    }
}
