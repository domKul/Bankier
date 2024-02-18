package dominik.bankier.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AlreadyExistException extends RuntimeException{
    private final ExceptionMessage exceptionMessage;
}
