package dominik.bankier.account;

import dominik.bankier.account.dto.AccountFindDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountFacade {

    private final AccountService accountService;

    public AccountFindDto retrieveAccountById(long accointId){
        return accountService.findAccountInformationById(accointId);
    }

    public AccountFindDto retrieveAccountByNumber(String  number){
        return accountService.findAccountByNumber(number);
    }

}
