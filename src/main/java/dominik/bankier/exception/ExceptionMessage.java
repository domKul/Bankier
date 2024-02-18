package dominik.bankier.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessage {

    NOT_FOUND("Entity not found"),
    ALREADY_INACTIVE("Already Inactive"),
    CLIENT_ALREADY_EXIST("Client already registered");
    private final String message;
}
