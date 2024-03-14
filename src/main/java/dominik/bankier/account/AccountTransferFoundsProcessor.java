package dominik.bankier.account;

import dominik.bankier.exception.ExceptionMessage;
import dominik.bankier.exception.NotEnoughFundsException;
import dominik.bankier.exception.NotFoundException;
import dominik.bankier.transaction.dto.TransactionCreateDto;
import dominik.bankier.transaction.TransactionFacade;
import dominik.bankier.transaction.dto.query.SimpleTransactionQueryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
class AccountTransferFoundsProcessor {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final TransactionFacade transactionFacade;

    @Transactional
    TransactionCreateDto transferFounds(TransactionCreateDto transactionCreateDto) {
        List<Account> accounts = getAccountsForTransfer(transactionCreateDto.from(), transactionCreateDto.to());
        if (accounts.size() != 2 || transactionCreateDto.from().equals(transactionCreateDto.to())) {
            log.warn("Some accounts are not found");
            throw new NotFoundException(ExceptionMessage.NOT_FOUND);
        }
        Account accountFrom = accounts.stream()
                .filter(a -> a.getAccountNumber().equals(transactionCreateDto.from()))
                .findFirst()
                .orElseThrow();
        Account accountTo = accounts.stream()
                .filter(a -> a.getAccountNumber().equals(transactionCreateDto.to()))
                .findFirst()
                .orElseThrow();
        if (accountFrom.getBalance().compareTo(transactionCreateDto.amount()) < 0) {
            throw new NotEnoughFundsException(ExceptionMessage.INSUFFICIENT_FOUNDS);
        }
        accountFrom.setBalance(accountFrom.getBalance().subtract(transactionCreateDto.amount()));
        accountTo.setBalance(accountTo.getBalance().add(transactionCreateDto.amount()));
        SimpleTransactionQueryDto simpleTransactionQueryDto = new SimpleTransactionQueryDto(accountMapper.mapToSimpleAccountQueryDto(accountFrom),
                accountMapper.mapToSimpleAccountQueryDto(accountTo), transactionCreateDto.title(),
                transactionCreateDto.amount());
        transactionFacade.createTransaction(simpleTransactionQueryDto);
        accountRepository.saveAll(Arrays.asList(accountFrom, accountTo));
        log.info("Founds transfer success");
        return transactionCreateDto;
    }

    private List<Account> getAccountsForTransfer(String from, String to) {
        return accountRepository.findByAccountNumberIn(Arrays.asList(from, to));
    }
}
