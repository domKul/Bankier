package dominik.bankier.account;

import dominik.bankier.account.query.AccountCreateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
class AccountController {

    private final AccountService accountService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> createAccount( @RequestBody @Valid AccountCreateDto accountCreateDto){
         accountService.createAccount(accountCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("{accountId}")
    ResponseEntity<Void>deleteAccount(@PathVariable long accountId){
        accountService.deleteAccount(accountId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("{accountId}")
    ResponseEntity<AccountFindDto>findAccountById(@PathVariable long accountId){
        AccountFindDto accountInformationById = accountService.findAccountInformationById(accountId);
        return ResponseEntity.ok(accountInformationById);
    }
}

