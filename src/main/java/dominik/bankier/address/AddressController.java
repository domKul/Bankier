package dominik.bankier.address;

import dominik.bankier.address.query.AddressCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("{addressId}")
    ResponseEntity<AddressFindDto> findAddress(@PathVariable long addressId){
        AddressFindDto addressByGivenId = addressService.findAddressByGivenId(addressId);
        return ResponseEntity.ok(addressByGivenId);
    }

    @PostMapping("create/{clientId}")
    ResponseEntity<Void>addAddressToClient(@RequestBody AddressCreateDto addressCreateDto, @PathVariable     long clientId){
        addressService.createAddress(addressCreateDto,clientId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
