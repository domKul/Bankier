package dominik.bankier.account;

import dominik.bankier.account.query.AccountCreateDto;
import dominik.bankier.client.ClientFacade;
import dominik.bankier.client.query.ClientFindDto;
import dominik.bankier.exception.ExceptionMessage;
import dominik.bankier.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
class AccountService {

    private final AccountRepository accountRepository;
    private final ClientFacade clientFacade;
    private final AccountMapper accountMapper;

    void createAccount(AccountCreateDto accountCreateDto){
        ClientFindDto clientFindDto = clientFacade.retrieveClientById(accountCreateDto.getClientId());
        Account account = accountMapper.mapToAccount(accountCreateDto);
        account.setAccountNumber(generateAccountNumber(account.getCurrency(),clientFindDto));
        accountRepository.save(account);
        log.info("Account created with");
    }

    void deleteAccount(final long accountId){
        accountRepository.findById(accountId)
                .orElseThrow(()->new NotFoundException(ExceptionMessage.NOT_FOUND));
        log.info("Account deleted");
    }
    private static String generateAccountNumber(CurrencyList currencyList, ClientFindDto clientFindDto) {
        Random random = new Random();
        long randomLong = random.nextLong();
        String randomDigits = String.format("%016d", Math.abs(randomLong));
        return currencyList.getCurrency() + "00" + clientFindDto.getClientId() + randomDigits;
    }
}
