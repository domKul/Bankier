package dominik.bankier.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface AccountRepository extends JpaRepository<Account,Long> {

    List<Account>findByAccountNumberIn(List<String> numbers);
    Optional<Account> findByAccountNumber(String number);
}
