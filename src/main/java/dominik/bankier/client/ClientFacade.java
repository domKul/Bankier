package dominik.bankier.client;

import dominik.bankier.client.query.ClientFindDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientFacade {

    private final ClientService clientService;

    public ClientFindDto retrieveClientById(final long clientId){
        return clientService.findClientById(clientId);
    }
}
