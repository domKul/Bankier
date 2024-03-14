package dominik.bankier.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotEnoughFundsException extends RuntimeException{

    private ExceptionMessage exceptionMessage;
}
