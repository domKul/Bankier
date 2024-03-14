package dominik.bankier.account;

import dominik.bankier.account.dto.AccountCreateDto;
import dominik.bankier.account.dto.AccountFindDto;
import dominik.bankier.account.dto.query.SimpleAccountQueryDto;
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

    AccountFindDto mapToAccountFindDto(Account account){
        return new AccountFindDto(account.getAccountId(),
                account.getAccountNumber(),
                account.getClient_id(),
                account.getBalance(),
                account.getCurrency(),
                account.getCreationDate(),
                account.getTransactionsTo(),
                account.getTransactionsFrom()
                );
    }

    SimpleAccountQueryDto mapToSimpleAccountQueryDto(Account account){
        return new SimpleAccountQueryDto(account.getAccountId()
                ,account.getAccountNumber(),
                account.getBalance(),
                account.getCurrency());
    }
}
