package dominik.bankier.client;

import dominik.bankier.address.query.AddressCreateDto;
import dominik.bankier.client.query.ClientCreateDto;
import dominik.bankier.client.query.ClientFindDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ClientMapperTest {

    @Autowired
    private ClientMapper clientMapper;
    private AddressCreateDto addressCreateDto ;
    private ClientCreateDto clientCreateDto ;
    private Client client;
    private Client client1;
    private Client client2;

    @BeforeEach
    void setUp() {
        addressCreateDto = new AddressCreateDto("Streetname", "Cityname", "countryname");
        clientCreateDto = new ClientCreateDto("Firstname","Lastname","email@email.com",addressCreateDto);
        client = new Client("Firstname","Lastname","email@email.com");
        client1 = new Client("Firstname1","Lastname1","email1@email.com");
        client2 = new Client("Firstname2","Lastname2","emai2@email.com");
    }

    @Test
    void shouldMapToClient(){
        //Given

        //When
        Client mappedToClient = clientMapper.mapToClient(clientCreateDto);
        //Then
        assertEquals(Client.class,mappedToClient.getClass());
        assertEquals(clientCreateDto.firstname(),mappedToClient.getFirstName());
        assertEquals(clientCreateDto.lastName(),mappedToClient.getLastName());
    }

    @Test
    void shouldMapToClientFindDto(){
        //Given

        //When
        ClientFindDto clientFindDto = clientMapper.mapToClientFind(client);
        //Then
        assertNotNull(clientFindDto);
        assertEquals(ClientFindDto.class,clientFindDto.getClass());
        assertEquals(0,clientFindDto.getAccountsList().size());
        assertEquals(0,clientFindDto.getAddresses().size());
        assertEquals(ClientStatusList.ACTIVE.getStatus(),clientFindDto.getStatus());
    }

    @Test
    void shouldMapListOfClients(){
        //Given
        List<Client> clients = List.of(client, client1, client2);
        //When
        List<ClientFindDto> clientFindDtos = clientMapper.mapToListClientFind(clients);
        //Then
        assertNotNull(clientFindDtos);
        assertEquals(clients.size(),clientFindDtos.size());
        assertEquals(ClientFindDto.class,clientFindDtos.get(0).getClass());
        assertEquals(ClientFindDto.class,clientFindDtos.get(1).getClass());
        assertEquals(ClientFindDto.class,clientFindDtos.get(2).getClass());
    }
}
