package dominik.bankier.address;

import dominik.bankier.address.query.AddressCreateDto;
import dominik.bankier.exception.AlreadyExistException;
import dominik.bankier.exception.ExceptionMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    @InjectMocks
    private AddressService addressService;
    @Mock
    private AddressMapper addressMapper;
    @Mock
    private AddressRepository addressRepository;

    private AddressCreateDto addressCreateDto;
    private AddressFindDto addressFindDto;
    private Address address;

    @BeforeEach
    void setUp() {
        address = new Address("street","city","countr",132L);
        addressCreateDto = new AddressCreateDto("streetCreate","cityCreate","countrCreate");
        addressFindDto = new AddressFindDto("street","city","countr",132L);
    }

    @Test
    void shouldCreateAddressIfIsNotExistWitSameClient(){
        //Given
        long clientId = 132L;
        when(addressRepository.existsAddressByClient_idAndStreetName(clientId,
                addressCreateDto.streetName())).thenReturn(false);
        when(addressMapper.mapToAddress(addressCreateDto,clientId)).thenReturn(address);
        //When
        addressService.createAddress(addressCreateDto,clientId);
        //Then
        verify(addressRepository,times(1)).save(address);
        verify(addressRepository,times(1))
                .existsAddressByClient_idAndStreetName(clientId,addressCreateDto.streetName());
        verify(addressMapper,times(1)).mapToAddress(addressCreateDto,clientId);
    }

    @Test
    void shouldThrowExceptionIfAddressAlreadyExistsWithClient() {
        // Given
        long clientId = 132L;
        when(addressRepository.existsAddressByClient_idAndStreetName(clientId, addressCreateDto.streetName()))
                .thenReturn(true);
        // When
        AlreadyExistException alreadyExistException = assertThrows(
                AlreadyExistException.class,
                () -> addressService.isAddressExistForClient(clientId, addressCreateDto.streetName())
        );
        //Then
        assertEquals(ExceptionMessage.ALREADY_EXIST, alreadyExistException.getExceptionMessage());
    }




}
