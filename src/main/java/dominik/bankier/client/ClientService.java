package dominik.bankier.client;

import dominik.bankier.address.AddressFacade;
import dominik.bankier.client.dto.ClientCreateDto;
import dominik.bankier.client.dto.query.ClientFindDto;
import dominik.bankier.client.dto.ClientUpdateDto;
import dominik.bankier.exception.AlreadyExistException;
import dominik.bankier.exception.ExceptionMessage;
import dominik.bankier.exception.NotActiveException;
import dominik.bankier.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final AddressFacade addressFacade;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    ClientCreateDto createClient(ClientCreateDto clientCreateDto) {
        if (clientRepository.existsClientByEmailAndFirstNameAndLastName(clientCreateDto.email(),
                clientCreateDto.firstName(), clientCreateDto.lastName())) {
            throw new AlreadyExistException(ExceptionMessage.CLIENT_ALREADY_EXIST);
        }
        Client clientToSave = clientMapper.mapToClient(clientCreateDto);
        Client savedClient = clientRepository.save(clientToSave);
        addressFacade.createAddressWithClient(clientCreateDto.simpleAddressQueryDto(), savedClient.getClientId());
        log.info("Client added with id " + savedClient.getClientId());
        return clientCreateDto;
    }

    ClientFindDto findClientById(final long clientId) {
        Client client = getClient(clientId);
        return clientMapper.mapToClientFind(client);
    }

    private void statusCheck(Client client) {
        if (isClientActiveStatus(client)){
            log.warn("Client not active");
            throw new NotActiveException(ExceptionMessage.CLIENT_NOT_ACTIVE);
        }
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

    @Transactional(propagation = Propagation.REQUIRED)
    public ClientFindDto patchClientInfo(final long clientId, ClientUpdateDto clientUpdateDto) {
        if (clientUpdateDto == null) {
            throw new IllegalArgumentException("Client update data cannot be null");
        }
        return clientRepository.findById(clientId)
                .map(client -> {
                    statusCheck(client);
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
                    if (validField) {
                        log.info("Client patched");
                        return clientMapper.mapToClientFind(client);
                    }
                    log.info("Client are not updated");
                    return clientMapper.mapToClientFind(client);
                })
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND));
    }

    private boolean areFieldsDifferent(ClientUpdateDto clientUpdateDto, Client client) {
        int result = 0;
        PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(clientUpdateDto.getClass());
        if (clientUpdateDto.firstname() != null && clientUpdateDto.firstname().equals(client.getFirstName())) {
            result++;
        }
        if (clientUpdateDto.lastName() != null && clientUpdateDto.lastName().equals(client.getLastName())) {
            result++;
        }
        if (clientUpdateDto.email() != null && clientUpdateDto.email().equals(client.getEmail())) {
            result++;
        }
        return result < propertyDescriptors.length - 1;
    }

    boolean isClientActiveStatus(Client client){
        return !client.getStatus().equals(ClientStatusList.ACTIVE);
    }

    @Transactional
    void changeStatusOfClient(final long clientId, ClientStatusList clientStatusList) {
        Client client = getClient(clientId);
        statusChange(client,clientStatusList);
        log.info("Status change");
    }

    void statusChange(Client client, ClientStatusList clientStatusList) {
        client.setStatus(ClientStatusList.ACTIVE);
        if (clientStatusList != null) {
            client.setStatus(clientStatusList);
        }
    }

    private Client getClient(final long clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND));
    }
}
