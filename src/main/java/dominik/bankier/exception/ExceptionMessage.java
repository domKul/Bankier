package dominik.bankier.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessage {

    NOT_FOUND("Entity not found"),
    ALREADY_INACTIVE("Already Inactive"),
    VALUE_IN_OBJECT_ARE_EQUAL("Values are equal"),
    CLIENT_ALREADY_EXIST("Client already registered");
    private final String message;
}
