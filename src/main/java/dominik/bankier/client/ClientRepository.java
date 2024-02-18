package dominik.bankier.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ClientRepository extends JpaRepository<Client,Long> {
    boolean existsClientByEmailAndFirstNameAndLastName(@NotNull @Email String email,
                                                       @NotNull String firstName,
                                                       @NotNull String lastName);
}
