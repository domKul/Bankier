package dominik.bankier.address;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
class AddressController {

    private final AddressService addressService;

    @DeleteMapping("{addressId}")
    ResponseEntity<Void>deleteAddressById(@PathVariable long addressId){
        addressService.deleteAddress(addressId);
        return ResponseEntity.accepted().build();
    }
}
