package dominik.bankier.transaction;

import dominik.bankier.transaction.dto.query.SimpleTransactionQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Transactional
    void createTransaction(SimpleTransactionQueryDto simpleTransactionQueryDto) {

        transactionRepository.save(new Transaction(simpleTransactionQueryDto.getAccountFrom(),
                simpleTransactionQueryDto.getAccountTo(),
                simpleTransactionQueryDto.getTitle(),
                simpleTransactionQueryDto.getAmount()));

    }


}
