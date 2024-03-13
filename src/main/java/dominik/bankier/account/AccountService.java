package dominik.bankier.account;

import dominik.bankier.account.query.AccountCreateDto;
import dominik.bankier.client.ClientFacade;
import dominik.bankier.client.ClientStatusList;
import dominik.bankier.client.query.ClientFindDto;
import dominik.bankier.exception.ExceptionMessage;
import dominik.bankier.exception.NotActiveException;
import dominik.bankier.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
class AccountService {

    private final AccountRepository accountRepository;
    private final ClientFacade clientFacade;
    private final AccountMapper accountMapper;


    @Transactional
    void createAccount(AccountCreateDto accountCreateDto){
        ClientFindDto clientFindDto = clientFacade.retrieveClientById(accountCreateDto.getClientId());
        if(!Objects.equals(clientFindDto.getStatus(), ClientStatusList.ACTIVE.getStatus())){
            log.warn("Client status are not active");
            throw new NotActiveException(ExceptionMessage.CLIENT_NOT_ACTIVE);
        }
        Account account = accountMapper.mapToAccount(accountCreateDto);
        account.setAccountNumber(generateAccountNumber(account.getCurrency(),clientFindDto));
        Account savingAccount = accountRepository.save(account);
        log.info("Account created with id " + savingAccount.getAccountId());
    }

    AccountFindDto findAccountInformationById(long accountId){
        Account accountById = findAccountById(accountId);
        log.info("Account found");
        return accountMapper.mapToAccountFindDto(accountById);
    }

    void deleteAccount(final long accountId){
        Account accountById = findAccountById(accountId);
        accountRepository.delete(accountById);
        log.info("Account deleted");
    }

    private Account findAccountById(long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(()->new NotFoundException(ExceptionMessage.NOT_FOUND));
    }

    private static String generateAccountNumber(CurrencyList currencyList, ClientFindDto clientFindDto) {
        Random random = new Random();
        long randomLong = random.nextLong();
        String randomDigits = String.format("%016d", Math.abs(randomLong));
        return currencyList.getCurrency() + "00" + clientFindDto.getClientId() + randomDigits;
    }
}
