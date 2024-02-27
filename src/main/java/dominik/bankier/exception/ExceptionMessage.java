package dominik.bankier.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessage {

    NOT_FOUND("Entity not found"),
    SAVE_PROBLEM("An error occurred while saving"),
    ALREADY_EXIST("Object already exist"),
    ALREADY_INACTIVE("Already inactive"),
    VALUE_IN_OBJECT_ARE_EQUAL("Values are equal"),
    OBJECT_IS_NULL("GIven object is null"),
    CLIENT_ALREADY_EXIST("Client already registered");
    private final String message;
}
