package dominik.bankier.transaction.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransactionCreateDto(
        @NotNull
        String from,
        @NotNull
        String to,
        @NotNull
        String title,
        @NotNull
        BigDecimal amount
) {
}
