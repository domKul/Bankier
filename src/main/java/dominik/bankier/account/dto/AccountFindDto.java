package dominik.bankier.account.dto;

import dominik.bankier.account.CurrencyList;
import dominik.bankier.transaction.dto.query.SimpleTransactionQueryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@AllArgsConstructor
public class AccountFindDto {

    private long accountId;
    private String accountNumber;
    private long client_id;
    private BigDecimal balance;
    private CurrencyList currency;
    private LocalDate creationDate;
    private Set<SimpleTransactionQueryDto> transactionsTo;
    private Set<SimpleTransactionQueryDto> transactionsFrom;
}
