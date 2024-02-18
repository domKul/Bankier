package dominik.bankier.account.query;

import dominik.bankier.account.CurrencyList;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AccountCreateDto {

    @NotNull(message = "client ID required")
    private long clientId;
    @Enumerated(EnumType.STRING)
    private CurrencyList currency;
}
