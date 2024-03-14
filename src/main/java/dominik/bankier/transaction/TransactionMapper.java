package dominik.bankier.transaction;

import dominik.bankier.account.dto.query.SimpleAccountQueryDto;
import dominik.bankier.transaction.dto.TransactionCreateDto;
import org.springframework.stereotype.Component;

@Component
class TransactionMapper {

    Transaction mapFromDtoToTransaction(TransactionCreateDto createDto){
        return new Transaction(new SimpleAccountQueryDto(),
                new SimpleAccountQueryDto(),
                createDto.title(),
                createDto.amount());
    }
}
