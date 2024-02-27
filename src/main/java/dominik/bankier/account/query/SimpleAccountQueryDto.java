package dominik.bankier.account.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dominik.bankier.account.CurrencyList;
import dominik.bankier.transaction.Transaction;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    private LocalDate creationDate;
    @OneToMany(mappedBy = "accountFrom")
    @JsonIgnore
    private Set<Transaction> transactionsFrom;
    @OneToMany(mappedBy = "accountTo")
    @JsonIgnore
    private Set<Transaction> transactionsTo;

    public SimpleAccountQueryDto( BigDecimal balance, CurrencyList currency) {
        this.balance = balance;
        this.currency = currency;
        this.creationDate = LocalDate.now();
    }

}
