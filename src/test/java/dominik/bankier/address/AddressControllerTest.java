package dominik.bankier.address;

import com.fasterxml.jackson.databind.ObjectMapper;
import dominik.bankier.address.query.AddressCreateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig
@WebMvcTest(AddressController.class)
class AddressControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AddressService addressService;

    @Test
    void mockTest(){
        assertNotNull(mockMvc);
    }


    @Test
    void shouldCreateNewAddress() throws Exception {
        //Given
        long clientId = 121321L;
        AddressCreateDto addressCreateDto = new AddressCreateDto("streetCreate", "cityCreate", "countrCreate");
        //When
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/addresses/create/{clientId}", clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addressCreateDto)))
                .andReturn();
        //Then
        assertEquals(HttpStatus.CREATED.value(),mvcResult.getResponse().getStatus());
        verify(addressService).createAddress(addressCreateDto,clientId);
    }

    @Test
    void shouldFindAddressByGivenId() throws Exception {
        //Given
        long addresId = 12L;
        AddressFindDto addressFindDto = new AddressFindDto("street12", "city12", "countr12", 132L);
       when(addressService.findAddressByGivenId(addresId)).thenReturn(addressFindDto);
        //When
        mockMvc.perform(MockMvcRequestBuilders.get("/addresses/{clientId}", addresId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.streetName").value("street12"))
                .andExpect(jsonPath("$.city").value("city12"))
                .andExpect(jsonPath("$.client_id").value(132L))
                .andExpect(jsonPath("$.country").value("countr12"));
        //Then
        verify(addressService).findAddressByGivenId(addresId);
    }

    @Test
    void shouldDeleteAddressByGivenId() throws Exception {
        //Given
        long clientId = 121321L;
        //When
         mockMvc.perform(MockMvcRequestBuilders.delete("/addresses/{clientId}", clientId))
                .andExpect(status().isAccepted());
        //Then
        verify(addressService).deleteAddress(clientId);

    }
}
