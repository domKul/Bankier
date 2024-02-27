package dominik.bankier.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddressSaveException extends RuntimeException{
    private ExceptionMessage exceptionMessage;
}
