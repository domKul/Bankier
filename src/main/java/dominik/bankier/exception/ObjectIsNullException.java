package dominik.bankier.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ObjectIsNullException extends RuntimeException{
    private final ExceptionMessage exceptionMessage;
}
