package dominik.bankier.account;

import dominik.bankier.transaction.dto.TransactionCreateDto;
import dominik.bankier.transaction.dto.query.SimpleTransactionQueryDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfers")
@RequiredArgsConstructor
class TransferFoundsController {

    private final AccountTransferFoundsProcessor accountTransferFoundsProcessor;

    @PatchMapping
    TransactionCreateDto transfer(@RequestBody @Valid TransactionCreateDto transactionCreateDto){
        return accountTransferFoundsProcessor.transferFounds(transactionCreateDto);
    }
}
