package dominik.bankier.account.dto.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dominik.bankier.account.CurrencyList;
import dominik.bankier.transaction.dto.query.SimpleTransactionQueryDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "accounts")
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class SimpleAccountQueryDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountId;
    @NotNull
    private String accountNumber;
    @NotNull
    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    private CurrencyList currency;
    @OneToMany(mappedBy = "accountFrom")
    @JsonIgnore
    private Set<SimpleTransactionQueryDto> transactionsFrom;
    @OneToMany(mappedBy = "accountTo")
    @JsonIgnore
    private Set<SimpleTransactionQueryDto> transactionsTo;


    public SimpleAccountQueryDto(long accountId, String accountNumber, BigDecimal balance, CurrencyList currency) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.currency = currency;
    }
}
