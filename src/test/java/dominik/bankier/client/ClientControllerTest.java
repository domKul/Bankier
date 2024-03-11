package dominik.bankier.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import dominik.bankier.address.query.AddressCreateDto;
import dominik.bankier.client.query.ClientCreateDto;
import dominik.bankier.client.query.ClientFindDto;
import dominik.bankier.client.query.ClientUpdateDto;
import dominik.bankier.exception.ExceptionMessage;
import dominik.bankier.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig
@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private ClientController clientController;
    @MockBean
    private ClientService clientService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void mockCheck() {
        assertNotNull(mockMvc);
    }

    @Test
    void shouldCreateClientAndReturnStatus201() throws Exception {
        //Given
        AddressCreateDto addressCreateDto = new AddressCreateDto("Streetname", "Cityname", "countryname");
        ClientCreateDto clientCreateDto1 = new ClientCreateDto("Firstname", "Lastname", "email@email.com", addressCreateDto);
        when(clientService.createClient(clientCreateDto1)).thenReturn(clientCreateDto1);
        //When
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/clients")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(clientCreateDto1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value(clientCreateDto1.firstName()))
                .andExpect(jsonPath("$.email").value(clientCreateDto1.email()))
                .andExpect(jsonPath("$.lastName").value(clientCreateDto1.lastName()));

        //Then
        verify(clientService).createClient(clientCreateDto1);
    }

    @Test
    void shouldRetrieveClientByGivenId() throws Exception {
        //Given
        ClientFindDto clientToFind = ClientFindDto.builder()
                .clientId(1L)
                .firstName("firstnameFind")
                .lastName("lastnameFind")
                .email("email@email.com")
                .status(ClientStatusList.ACTIVE.getStatus())
                .accountsList(new HashSet<>())
                .addresses(new HashSet<>())
                .build();
        when(clientService.findClientById(clientToFind.getClientId())).thenReturn(clientToFind);
        //When

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/clients/" + clientToFind.getClientId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(clientToFind.getFirstName()))
                .andExpect(jsonPath("$.status").value(clientToFind.getStatus()))
                .andExpect(jsonPath("$.lastName").value(clientToFind.getLastName()));
        //Then
        verify(clientService, times(1)).findClientById(clientToFind.getClientId());
    }

    @Test
    void shouldRetrieveAllClientsFromDB() throws Exception {
        //Given
        ClientFindDto clientToFind1 = ClientFindDto.builder()
                .clientId(1L)
                .firstName("firstnameFind1")
                .lastName("lastnameFind1")
                .email("email@email.com1")
                .status(ClientStatusList.ACTIVE.getStatus())
                .accountsList(new HashSet<>())
                .addresses(new HashSet<>())
                .build();
        ClientFindDto clientToFind2 = ClientFindDto.builder()
                .clientId(1L)
                .firstName("firstnameFind2")
                .lastName("lastnameFind2")
                .email("email@email.com2")
                .status(ClientStatusList.ACTIVE.getStatus())
                .accountsList(new HashSet<>())
                .addresses(new HashSet<>())
                .build();
        when(clientService.findAllClients()).thenReturn(List.of(clientToFind1, clientToFind2));
        //When
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/clients")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$.[0].firstName").value("firstnameFind1"))
                .andExpect(jsonPath("$.[1].firstName").value("firstnameFind2"));
        //Then
        verify(clientService, times(1)).findAllClients();
    }

//    @Test
//    void shouldDeleteClientByGivenId() throws Exception {
//        //Given
//        long clientId = 123L;
//        doNothing().when(clientService).suspendClient(clientId);
//        //When
//        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/clients/" + clientId))
//                .andExpect(status().isAccepted());
//        //Then
//        verify(clientService, times(1)).suspendClient(clientId);
//    }

    @Test
    public void shouldUpdateClientSuccessfully() throws Exception {
        //Given
        ClientUpdateDto clientUpdateDto = new ClientUpdateDto("updateFirstname", "updateLastName", "updateEmail");
        ClientFindDto expected = ClientFindDto.builder()
                .clientId(1L)
                .firstName(clientUpdateDto.firstname())
                .lastName(clientUpdateDto.lastName())
                .email(clientUpdateDto.email())
                .status(ClientStatusList.ACTIVE.getStatus())
                .accountsList(new HashSet<>())
                .addresses(new HashSet<>())
                .build();
        when(clientService.patchClientInfo(expected.getClientId(), clientUpdateDto)).thenReturn(expected);
        //When
        mockMvc.perform(MockMvcRequestBuilders.patch("/v1/clients/" + 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientUpdateDto)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.firstName").value(expected.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(expected.getLastName()));
        //Then
        verify(clientService).patchClientInfo(1L, clientUpdateDto);
    }

    @Test
    public void testStatusChangeToInactive_Success() throws Exception {
        //Given
        long clientId = 1L;
        ClientStatusList inactive = ClientStatusList.INACTIVE;
        doNothing().when(clientService).changeStatusOfClient(clientId,inactive);
        //When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/v1/clients/status/{clientId}",clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inactive)))
                .andReturn();
        //Then
        assertEquals(HttpStatus.ACCEPTED.value(), result.getResponse().getStatus());
        verify(clientService).changeStatusOfClient(clientId,inactive);
    }

    @Test
    public void testStatusChangeToInactiveWithWrongClientId() throws Exception {
        //Given
        long clientId = 1L;
        ClientStatusList inactive = ClientStatusList.INACTIVE;
        doThrow(new NotFoundException(ExceptionMessage.NOT_FOUND)).when(clientService).changeStatusOfClient(clientId,null);
        //When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/v1/clients/status/{clientId}", clientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        //Then
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        verify(clientService).changeStatusOfClient(clientId,null);
    }




}
