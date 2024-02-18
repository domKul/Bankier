package dominik.bankier.address.query;

import jakarta.validation.constraints.NotNull;

public record AddressCreateDto(@NotNull(message = "Street name required")
                               String streetName,
                               @NotNull(message = "City name required")
                               String city,
                               @NotNull(message = "Country name required")
                               String country) {
}
