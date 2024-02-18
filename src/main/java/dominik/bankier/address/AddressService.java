package dominik.bankier.address;

import dominik.bankier.address.query.AddressCreateDto;
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
        Address address = addressMapper.mapToAddress(addressCreateDto,client_id);
        addressRepository.save(address);
        log.info("Address created");

    }

    void deleteAddress(final long addressId){
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND));
        addressRepository.delete(address);
        log.info("Address deleted");
    }
}
