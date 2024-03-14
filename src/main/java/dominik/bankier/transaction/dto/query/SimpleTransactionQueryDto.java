package dominik.bankier.transaction.dto.query;

import dominik.bankier.account.dto.query.SimpleAccountQueryDto;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
@Table(name = "transactions")
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class SimpleTransactionQueryDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "from_account_id")
    private SimpleAccountQueryDto accountFrom;
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "to_account_id")
    private SimpleAccountQueryDto accountTo;
    private String title;
    private BigDecimal amount;
    private LocalDateTime transactionDate;

    public SimpleTransactionQueryDto(SimpleAccountQueryDto accountFrom, SimpleAccountQueryDto accountTo,
                                     String title, BigDecimal amount) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.title = title;
        this.amount = amount;
        this.transactionDate = LocalDateTime.now();
    }
}
