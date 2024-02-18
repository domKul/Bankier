package dominik.bankier.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter@AllArgsConstructor
public class NotFoundException extends RuntimeException{

    private final ExceptionMessage exceptionMessage;
}
