package dominik.bankier.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotActiveException extends RuntimeException{
    private ExceptionMessage exceptionMessage;

}
