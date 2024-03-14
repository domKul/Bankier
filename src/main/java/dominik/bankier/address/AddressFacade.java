package dominik.bankier.address;

import dominik.bankier.address.dto.AddressCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressFacade {

    private final AddressService addressService;

    public void createAddressWithClient(AddressCreateDto addressCreateDto, final long clientId){
            addressService.createAddress(addressCreateDto,clientId);
    }

}
