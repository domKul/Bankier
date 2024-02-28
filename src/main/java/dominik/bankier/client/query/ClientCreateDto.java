package dominik.bankier.client.query;

import dominik.bankier.address.query.AddressCreateDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClientCreateDto(
        @NotNull(message = "firstName required")
        @NotBlank(message = "firstName required")
        String firstName,
        @NotNull(message = "lastName required")
        String lastName,
        @NotNull(message = "email required")
        @Email(message = "wrong email format")
        String email,
        @NotNull(message = "Address required")
        @Valid
        AddressCreateDto simpleAddressQueryDto
) {
}
