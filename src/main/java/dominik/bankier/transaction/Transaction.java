package dominik.bankier.transaction;

import dominik.bankier.account.query.SimpleAccountQueryDto;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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






}
