package dominik.bankier.client;

import dominik.bankier.address.AddressFacade;
import dominik.bankier.client.query.ClientCreateDto;
import dominik.bankier.client.query.ClientFindDto;
import dominik.bankier.client.query.ClientUpdateDto;
import dominik.bankier.exception.AlreadyExistException;
import dominik.bankier.exception.ExceptionMessage;
import dominik.bankier.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final AddressFacade addressFacade;
    private final int NUMBER_OF_VALIDATION = 3;


    void createClient(ClientCreateDto clientCreateDto) {
        if (clientRepository.existsClientByEmailAndFirstNameAndLastName(clientCreateDto.email(),
                clientCreateDto.firstname(), clientCreateDto.lastName())) {
            throw new AlreadyExistException(ExceptionMessage.CLIENT_ALREADY_EXIST);
        }
        Client clientToSave = clientMapper.mapToClient(clientCreateDto);
        Client save = clientRepository.save(clientToSave);
        addressFacade.createAddressWithClient(clientCreateDto.simpleAddressQueryDto(), save.getClientId());
        log.info("Client added with id " + save.getClientId());
    }

    ClientFindDto findClientById(final long clientId) {
        Client client = getClient(clientId);
        return clientMapper.mapToClientFind(client);
    }

    List<ClientFindDto> findAllClients() {
        List<Client> allClients = clientRepository.findAll();
        if (allClients.isEmpty()) {
            log.info("List of Clients are empty");
            return new ArrayList<>();
        }
        List<ClientFindDto> clientFindDto = clientMapper.mapToListClientFind(allClients);
        log.info(allClients.size() + " Clients in DB");
        return clientFindDto;
    }

    public ClientFindDto patchClientInfo(final long clientId, ClientUpdateDto clientUpdateDto) {
        if (clientUpdateDto == null ) {
            throw new IllegalArgumentException("Client update data cannot be null");
        }
        return clientRepository.findById(clientId)
                .map(client -> {
                    boolean validField = areFieldsDifferent(clientUpdateDto, client);
                    Optional.ofNullable(clientUpdateDto.firstname())
                            .ifPresent(firstName -> {
                                if (!Objects.equals(firstName, client.getFirstName())) {
                                    client.setFirstName(firstName);
                                }
                            });
                    Optional.ofNullable(clientUpdateDto.lastName())
                            .ifPresent(lastName -> {
                                if (!Objects.equals(lastName, client.getLastName())) {
                                    client.setLastName(lastName);
                                }
                            });
                    Optional.ofNullable(clientUpdateDto.email())
                            .ifPresent(email -> {
                                if (!Objects.equals(email, client.getEmail())) {
                                    client.setEmail(email);
                                }
                            });
                    if (validField){
                        Client savedClient = clientRepository.save(client);
                        log.info("Client patched");
                        return clientMapper.mapToClientFind(savedClient);
                    }
                    log.info("Client are not updated");
                    return clientMapper.mapToClientFind(client);
                })
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND));
    }

    private boolean areFieldsDifferent(ClientUpdateDto clientUpdateDto, Client client){
        int i = 0;
        if(clientUpdateDto.firstname() != null && clientUpdateDto.firstname().equals(client.getFirstName())){
            i++;
        }
        if(clientUpdateDto.lastName() != null && clientUpdateDto.lastName().equals(client.getLastName())){
            i++;
        }
        if(clientUpdateDto.email() != null && clientUpdateDto.email().equals(client.getEmail())){
            i++;
        }
        return i < NUMBER_OF_VALIDATION;
    }

    void deleteClient(final long clientId) {
        Client client = getClient(clientId);
        clientRepository.delete(client);
        log.info("Client deleted with id " + client.getClientId());
    }

    void changeToInactive(final long clientId) {
        Client client = getClient(clientId);
        if (client.getStatus().equals(ClientStatusList.INACTIVE)) {
            throw new NotFoundException(ExceptionMessage.ALREADY_INACTIVE);
        }
        client.setStatus(ClientStatusList.INACTIVE);
        clientRepository.save(client);
    }


    private Client getClient(long clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND));
    }
}
