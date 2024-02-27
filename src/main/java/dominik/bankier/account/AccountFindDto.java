package dominik.bankier.account;

import dominik.bankier.transaction.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
class AccountFindDto {

    private long accountId;
    private String accountNumber;
    private long client_id;
    private BigDecimal balance;
    private CurrencyList currency;
    private LocalDate creationDate;
    private List<Transaction> transactions;
}
