package dominik.bankier.exception.handler;

import dominik.bankier.exception.AlreadyExistException;
import dominik.bankier.exception.NotActiveException;
import dominik.bankier.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RestControllerAdvice
class GlobalHttpExceptionHandler {

    @ExceptionHandler(AlreadyExistException.class)
    ResponseEntity<ErrorMessageWithStatus> handleAlreadyExistException(AlreadyExistException ex){
        ErrorMessageWithStatus errorMessageWithStatus = new ErrorMessageWithStatus(ex.getExceptionMessage().getMessage(),
                HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessageWithStatus);
    }

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<ErrorMessageWithStatus> handleNotFoundException(NotFoundException ex){
        ErrorMessageWithStatus errorMessageWithStatus = new ErrorMessageWithStatus(ex.getExceptionMessage().getMessage(),
                HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessageWithStatus);
    }

    @ExceptionHandler(NotActiveException.class)
    ResponseEntity<ErrorMessageWithStatus> handleNotActiveException(NotActiveException ex){
        ErrorMessageWithStatus errorMessageWithStatus = new ErrorMessageWithStatus(ex.getExceptionMessage().getMessage(),
                HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessageWithStatus);
    }

    @ExceptionHandler(IllegalAccessException.class)
    ResponseEntity<ErrorMessageWithStatus> handleIllegalAccessException(IllegalAccessException ex){
        ErrorMessageWithStatus errorMessageWithStatus = new ErrorMessageWithStatus(ex.getMessage(),
                HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessageWithStatus);
    }
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    ResponseEntity<ErrorMessageWithStatus> handleSQLIntegrityConstraintViolationException(
            SQLIntegrityConstraintViolationException ex){
        ErrorMessageWithStatus errorMessageWithStatus = new ErrorMessageWithStatus(ex.getMessage().substring(0,34),
                HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessageWithStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorMessage> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors()
                    .stream().map(FieldError::getDefaultMessage)
                    .toList();
            ErrorMessage errorMessage = new ErrorMessage(errors.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        } else {
            String defaultMessage = "Validation error";
            ErrorMessage errorMessage = new ErrorMessage(defaultMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
    }
}
