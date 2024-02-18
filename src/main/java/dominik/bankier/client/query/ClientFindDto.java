package dominik.bankier.client.query;

import dominik.bankier.account.query.SimpleAccountQueryDto;
import dominik.bankier.address.query.SimpleAddressQueryDto;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;
@Getter
@Builder
public class ClientFindDto {

    private long clientId;
    private String firstName;
    private String lastName;
    private String email;
    private String status;
    private Set<SimpleAddressQueryDto> addresses;
    private Set<SimpleAccountQueryDto> accountsList;
}
