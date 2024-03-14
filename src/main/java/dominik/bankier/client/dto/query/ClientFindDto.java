package dominik.bankier.client.dto.query;

import dominik.bankier.account.dto.query.SimpleAccountQueryDto;
import dominik.bankier.address.dto.query.SimpleAddressQueryDto;
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
