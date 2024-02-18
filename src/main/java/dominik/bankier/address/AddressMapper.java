package dominik.bankier.address;

import dominik.bankier.address.query.AddressCreateDto;
import org.springframework.stereotype.Component;

@Component
 class AddressMapper {

    Address mapToAddress(AddressCreateDto addressCreateDto,long client_id){
        return new Address(
                addressCreateDto.streetName(),
                addressCreateDto.city(),
                addressCreateDto.country(),
                client_id
                );
    }
}
