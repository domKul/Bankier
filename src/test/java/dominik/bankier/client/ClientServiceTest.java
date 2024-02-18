package dominik.bankier.client;

import dominik.bankier.address.AddressFacade;
import dominik.bankier.address.query.AddressCreateDto;
import dominik.bankier.client.query.ClientCreateDto;
import dominik.bankier.client.query.ClientFindDto;
import dominik.bankier.exception.AlreadyExistException;
import dominik.bankier.exception.ExceptionMessage;
import dominik.bankier.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ClientMapper clientMapper;
    @Mock
    private AddressFacade addressFacade;
    private ClientCreateDto clientCreateDto1;
    private ClientFindDto clientFindDto;
    private ClientFindDto clientFindDto1;
    private ClientFindDto clientFindDto2;
    private AddressCreateDto addressCreateDto;
    private Client client;
    private Client client1;
    private Client client2;

    @BeforeEach
    void setUp() {
        addressCreateDto = new AddressCreateDto("Streetname", "Cityname", "countryname");
        clientCreateDto1 = new ClientCreateDto("Firstname","Lastname","email@email.com",addressCreateDto);
        clientFindDto = ClientFindDto.builder()
                .clientId(1L)
                .firstName("firstnameFind")
                .lastName("lastnameFind")
                .email("email@email.com")
                .status(ClientStatusList.ACTIVE.getStatus())
                .accountsList(new HashSet<>())
                .addresses(new HashSet<>())
                .build();
        clientFindDto1 = ClientFindDto.builder()
                .clientId(2L)
                .firstName("firstnameFind1")
                .lastName("lastnameFind1")
                .email("email@email.com1")
                .status(ClientStatusList.ACTIVE.getStatus())
                .accountsList(new HashSet<>())
                .addresses(new HashSet<>())
                .build();
        clientFindDto2 = ClientFindDto.builder()
                .clientId(3L)
                .firstName("firstnameFind2")
                .lastName("lastnameFind2")
                .email("email@email.com2")
                .status(ClientStatusList.ACTIVE.getStatus())
                .accountsList(new HashSet<>())
                .addresses(new HashSet<>())
                .build();
        client = new Client("Firstname","Lastname","email@email.com");
        client1 = new Client("Firstname1","Lastname1","email1@email.com");
        client2 = new Client("Firstname2","Lastname2","emai2@email.com");
    }


    @Test
    void shouldThrowAlreadyExistExceptionWhenClientExists() {
        // Given
        when(clientRepository.existsClientByEmailAndFirstNameAndLastName(clientCreateDto1.email(),
                clientCreateDto1.firstname(), clientCreateDto1.lastName())).thenReturn(true);

        // When
        Assertions.assertThrows(AlreadyExistException.class, () -> clientService.createClient(clientCreateDto1));
        //Then
        verifyNoInteractions(clientMapper, addressFacade);
    }

    @Test
    void shouldCreateClient(){
        //Given
        when(clientMapper.mapToClient(clientCreateDto1)).thenReturn(client);
        when(clientRepository.save(client)).thenReturn(client);
        doNothing().when(addressFacade).createAddressWithClient(addressCreateDto,client.getClientId());
        //When
        clientService.createClient(clientCreateDto1);
        //Then
        verify(clientMapper,times(1)).mapToClient(clientCreateDto1);
        verify(clientRepository,times(1)).save(client);
        verify(addressFacade,times(1)).createAddressWithClient(addressCreateDto,client.getClientId());
    }

    @Test
    void shouldFindClientById(){
        //Given
        when(clientRepository.findById(client.getClientId())).thenReturn(Optional.ofNullable(client));
        when(clientMapper.mapToClientFind(client)).thenReturn(clientFindDto);
        //When
        ClientFindDto clientById = clientService.findClientById(client.getClientId());
        //Then
        assertNotNull(clientById);
        assertEquals(ClientFindDto.class,clientById.getClass());
        assertEquals(1L,clientById.getClientId());
        assertEquals(0,clientById.getAccountsList().size());
        assertEquals(0,clientById.getAddresses().size());
        verify(clientRepository,times(1)).findById(client.getClientId());
        verify(clientMapper,times(1)).mapToClientFind(client);
    }

    @Test
    void shouldThrowExceptionWhenWrongIdPassed(){
        //Given
        //When
        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> clientService.findClientById(123));
        //Then
        assertEquals(ExceptionMessage.NOT_FOUND,notFoundException.getExceptionMessage());
    }

    @Test
    void shouldFindAllClients(){
        //Given
        List<Client> clients = List.of(client, client1, client2);
        when(clientRepository.findAll()).thenReturn(clients);
        when(clientMapper.mapToListClientFind(clients))
                .thenReturn(List.of(clientFindDto,clientFindDto1,clientFindDto2));
        //When
        List<ClientFindDto> allClients = clientService.findAllClients();
        //Then
        assertEquals(clients.size(),allClients.size());
    }
}
