package dominik.bankier.transaction;

import dominik.bankier.transaction.dto.query.SimpleTransactionQueryDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionFacade {
    private final TransactionService transactionService;

    public void createTransaction(@Valid SimpleTransactionQueryDto simpleAccountQueryDto){
        transactionService.createTransaction(simpleAccountQueryDto);
    }
}
