package dominik.bankier.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CurrencyList {
    PLN("PLN");

    private final String currency;

}
