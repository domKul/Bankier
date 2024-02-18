package dominik.bankier.account;

import dominik.bankier.account.query.AccountCreateDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
class AccountMapper {

    Account mapToAccount(AccountCreateDto accountCreateDto){
        return new Account(accountCreateDto.getClientId(),
                BigDecimal.ZERO,
                accountCreateDto.getCurrency()
                );
    }
}
