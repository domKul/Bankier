package dominik.bankier.address;

import dominik.bankier.address.query.AddressCreateDto;
import dominik.bankier.exception.AlreadyExistException;
import dominik.bankier.exception.ExceptionMessage;
import dominik.bankier.exception.NotActiveException;
import dominik.bankier.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

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
        address = new Address("street", "city", "countr", 132L);
        addressCreateDto = new AddressCreateDto("streetCreate", "cityCreate", "countrCreate");
        addressFindDto = new AddressFindDto("street", "city", "countr", 132L);
    }

    @Test
    void shouldCreateAddressIfIsNotExistWitSameClient() {
        //Given
        long clientId = 132L;
        when(addressRepository.existsAddressByClient_idAndStreetName(clientId,
                addressCreateDto.streetName())).thenReturn(false);
        when(addressMapper.mapToAddress(addressCreateDto, clientId)).thenReturn(address);
        //When
        addressService.createAddress(addressCreateDto, clientId);
        //Then
        verify(addressRepository, times(1)).save(address);
        verify(addressRepository, times(1))
                .existsAddressByClient_idAndStreetName(clientId, addressCreateDto.streetName());
        verify(addressMapper, times(1)).mapToAddress(addressCreateDto, clientId);
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
                () -> addressService.isAddressExistForClient(clientId, addressCreateDto.streetName()));
        //Then
        assertEquals(ExceptionMessage.ALREADY_EXIST, alreadyExistException.getExceptionMessage());
    }

    @Test
    void shouldFindAddressByGivenIdSuccessfully(){
        //Given
        long addressId = 123333L;
        when(addressRepository.findById(addressId)).thenReturn(Optional.ofNullable(address));
        when(addressMapper.mapToAddressDtoFind(address)).thenReturn(addressFindDto);
        //When
        AddressFindDto addressFind = addressService.findAddressByGivenId(addressId);
        //Then
        assertNotNull(addressFind);
        verify(addressRepository,times(1)).findById(addressId);
    }

    @Test
    void shouldThrowExceptionDuringAddressFindByWrongId(){
        //Given
        long wrongId = 123123L;
       //When
        NotFoundException notActiveException = assertThrows(NotFoundException.class,
                () -> addressService.findAddressByGivenId(wrongId));
        //Then
        assertEquals(ExceptionMessage.NOT_FOUND.getMessage(),notActiveException.getExceptionMessage().getMessage());
    }

    @Test
    void shouldDeleteAddressSuccessfully(){
        //Given
        long addressId = 1L;
        when(addressRepository.findById(addressId)).thenReturn(Optional.ofNullable(address));
        doNothing().when(addressRepository).delete(address);
        //When
        addressService.deleteAddress(addressId);
        //Then
        verify(addressRepository,times(1)).findById(addressId);
        verify(addressRepository,times(1)).delete(address);
    }
}
