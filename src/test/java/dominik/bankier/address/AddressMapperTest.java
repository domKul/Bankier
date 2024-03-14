package dominik.bankier.address;

import dominik.bankier.address.dto.AddressCreateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AddressMapperTest {
    @Autowired
    private AddressMapper addressMapper;


    private AddressCreateDto addressCreateDto;
    private AddressFindDto addressFindDto;
    private Address address;

    @BeforeEach
    void setUp() {
        address = new Address("street", "city", "countr", 132L);
        addressCreateDto = new AddressCreateDto("streetCreate", "cityCreate", "countrCreate");
        addressFindDto = new AddressFindDto("street", "city", "countr", 132L);
    }

    @Test
    void shouldMapToAddress() {
        //Given
        long clientId = 1L;
        //When
        Address mappedAddress = addressMapper.mapToAddress(addressCreateDto, clientId);
        //Then
        assertNotNull(mappedAddress);
        assertEquals(clientId, mappedAddress.getClient_id());
        assertEquals(addressCreateDto.streetName(), mappedAddress.getStreetName());
        assertEquals(addressCreateDto.city(), mappedAddress.getCity());
        assertEquals(addressCreateDto.country(), mappedAddress.getCountry());
    }

    @Test
    void shouldMapToAddressDtoFind() {
        //Given
        //When
        AddressFindDto mappedAddressFindDto = addressMapper.mapToAddressDtoFind(address);
        //Then
        assertNotNull(mappedAddressFindDto);
        assertEquals(address.getClient_id(), mappedAddressFindDto.client_id());
        assertEquals(address.getCity(), mappedAddressFindDto.city());
        assertEquals(address.getStreetName(), mappedAddressFindDto.streetName());
        assertEquals(address.getCountry(), mappedAddressFindDto.country());

    }
}
