package dominik.bankier.account;

import dominik.bankier.transaction.Transaction;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "accounts")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountId;
    @NotNull
    private String accountNumber;
    @NotNull
    private long client_id;
    @NotNull
    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    private CurrencyList currency;
    private LocalDate creationDate;
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "accountFrom", fetch = FetchType.LAZY)
    private Set<Transaction> transactionsFrom;
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "accountTo", fetch = FetchType.LAZY)
    private Set<Transaction> transactionsTo;

    public Account(long client_id, BigDecimal balance, CurrencyList currency) {
        this.client_id = client_id;
        this.balance = balance;
        this.currency = currency;
        this.creationDate = LocalDate.now();
        this.transactionsTo = new HashSet<>();
        this.transactionsFrom = new HashSet<>();
    }
}
