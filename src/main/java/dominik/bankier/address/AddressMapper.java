package dominik.bankier.address;

import dominik.bankier.address.dto.AddressCreateDto;
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
    AddressFindDto mapToAddressDtoFind(Address address){
        return new AddressFindDto(address.getStreetName(),
                address.getCity(),
                address.getCountry(),
                address.getClient_id());
    }
}
