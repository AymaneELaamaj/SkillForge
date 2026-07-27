package com.skillforge.learning.handler;

import com.skillforge.learning.dto.ErrorResponse;
import com.skillforge.learning.exception.AssessmentNotFoundException;
import com.skillforge.learning.exception.IdentityServiceUnavailableException;
import com.skillforge.learning.exception.SkillNotFoundException;
import com.skillforge.learning.exception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

        
        @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, 
            HttpServletRequest request) {
            
        // On récupère toutes les erreurs de validation et on les assemble en une seule chaîne.
        // Exemple de résultat : "name: ne doit pas être vide, email: doit être bien formé"
         Map<String, String> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .collect(Collectors.toMap(
                    FieldError::getField,
                    FieldError::getDefaultMessage,
                    (first, second) -> first
            ));

    ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "VALIDATION_ERROR",
            "Validation failed",
            errors,
            request.getRequestURI()
    );
        
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

   @ExceptionHandler({
        SkillNotFoundException.class, 
        UserNotFoundException.class, 
        AssessmentNotFoundException.class 
    })
    public ResponseEntity<ErrorResponse> handleNotFoundExceptions(
            RuntimeException ex, 
            HttpServletRequest request) {
            
        // Détermination propre du code d'erreur métier
        String errorCode = "RESOURCE_NOT_FOUND";
        if (ex instanceof SkillNotFoundException) {
            errorCode = "SKILL_NOT_FOUND";
        } else if (ex instanceof UserNotFoundException) {
            errorCode = "USER_NOT_FOUND";
        } else if (ex instanceof AssessmentNotFoundException) {
            errorCode = "ASSESSMENT_NOT_FOUND";
        }
            
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                errorCode,
                ex.getMessage(),
                null,
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IdentityServiceUnavailableException.class)
    public ResponseEntity<ErrorResponse> handleIdentityServiceUnavailable(
            IdentityServiceUnavailableException ex, 
            HttpServletRequest request) {
            
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.SERVICE_UNAVAILABLE.value(), // Code 503
                "IDENTITY_SERVICE_UNAVAILABLE",
                ex.getMessage(),
                null,
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, 
            HttpServletRequest request) {
            
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "BAD_REQUEST",
                ex.getMessage(),
                null,
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex, 
            HttpServletRequest request) {
            
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_SERVER_ERROR",
                "Une erreur interne s'est produite dans le service d'apprentissage.",
                null,
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}