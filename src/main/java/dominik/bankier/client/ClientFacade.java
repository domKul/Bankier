package dominik.bankier.client;

import dominik.bankier.client.dto.query.ClientFindDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientFacade {

    private final ClientService clientService;
    @Getter
    ClientStatusList clientStatusList;

    public ClientFindDto retrieveClientById(final long clientId){
        return clientService.findClientById(clientId);
    }

}
