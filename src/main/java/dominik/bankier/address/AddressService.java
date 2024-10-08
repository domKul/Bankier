package dominik.bankier.address;

import dominik.bankier.address.dto.AddressCreateDto;
import dominik.bankier.exception.AddressSaveException;
import dominik.bankier.exception.AlreadyExistException;
import dominik.bankier.exception.ExceptionMessage;
import dominik.bankier.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void createAddress(AddressCreateDto addressCreateDto, long client_id) {
        try {
            boolean addressExists = isAddressExistForClient(client_id, addressCreateDto.streetName());
            if (!addressExists) {
                Address address = addressMapper.mapToAddress(addressCreateDto, client_id);
                addressRepository.save(address);
                log.info("Address created");
            }
        } catch (DataAccessException e) {
            log.error("An error occurred while saving");
            throw new AddressSaveException(ExceptionMessage.SAVE_PROBLEM);
        }
    }

    boolean isAddressExistForClient(long clientId, String streetName) {
        boolean exists = addressRepository.existsAddressByClient_idAndStreetName(clientId, streetName);
        if (exists) {
            log.info("Address is exist");
            throw new AlreadyExistException(ExceptionMessage.ALREADY_EXIST);
        }
        return false;
    }

    AddressFindDto findAddressByGivenId(final long addressId) {
        Address addressById = getAddressById(addressId);
        log.info("Address found with id " + addressById.getAddressId());
        return addressMapper.mapToAddressDtoFind(addressById);
    }

    @Transactional
    void deleteAddress(final long addressId) {
        Address address = getAddressById(addressId);
        addressRepository.delete(address);
        log.info("Address deleted");
    }

    private Address getAddressById(long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND));
    }
}
