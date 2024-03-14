package dominik.bankier.client;

import dominik.bankier.client.dto.ClientCreateDto;
import dominik.bankier.client.dto.query.ClientFindDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class ClientMapper {

    Client mapToClient(ClientCreateDto clientCreateDto){
        return new Client(clientCreateDto.firstName(),
                clientCreateDto.lastName(),
                clientCreateDto.email());
    }
    ClientFindDto mapToClientFind(Client client){
        return ClientFindDto.builder()
                .clientId(client.getClientId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .email(client.getEmail())
                .status(client.getStatus().getStatus())
                .addresses(client.getAddresses())
                .accountsList(client.getAccountsList())
                .build();
    }

    List<ClientFindDto>mapToListClientFind(List<Client> clients){
        return clients.stream()
                .map(this::mapToClientFind)
                .toList();
    }
}
