package dominik.bankier.address;

import dominik.bankier.address.query.AddressCreateDto;
import dominik.bankier.exception.AlreadyExistException;
import dominik.bankier.exception.ExceptionMessage;
import dominik.bankier.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
 class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

     void createAddress(AddressCreateDto addressCreateDto, long client_id){
         isAddressExist(client_id, addressCreateDto.streetName());
        Address address = addressMapper.mapToAddress(addressCreateDto,client_id);
        addressRepository.save(address);
        log.info("Address created");
    }

    void isAddressExist(long clientId, String streetName){
         if (addressRepository.existsAddressByClient_idAndStreetName(clientId,streetName)){
             log.info("Address is exist");
             throw new AlreadyExistException(ExceptionMessage.ALREADY_EXIST);
         }
    }

    AddressFindDto findAddressByGivenId(final long addressId){
        Address addressById = getAddressById(addressId);
        log.info("Address found with id " + addressById.getAddressId());
        return addressMapper.mapToAddressDtoFind(addressById);
    }

    void deleteAddress(final long addressId){
        Address address = getAddressById(addressId);
        addressRepository.delete(address);
        log.info("Address deleted");
    }
    private Address getAddressById(long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND));
    }
}
