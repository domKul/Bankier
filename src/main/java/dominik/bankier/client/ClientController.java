package dominik.bankier.client;

import dominik.bankier.client.query.ClientCreateDto;
import dominik.bankier.client.query.ClientFindDto;
import dominik.bankier.client.query.ClientUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/clients")
@RequiredArgsConstructor
 class ClientController {

    private final ClientService clientService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ClientCreateDto>addClient(@RequestBody @Valid ClientCreateDto clientCreateDto){
        ClientCreateDto client = clientService.createClient(clientCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(client);
    }

    @GetMapping("{clientId}")
    ResponseEntity<ClientFindDto>findClientById(@PathVariable long clientId){
        ClientFindDto client = clientService.findClientById(clientId);
        return ResponseEntity.status(HttpStatus.OK).body(client);
    }

    @GetMapping
    ResponseEntity<List<ClientFindDto>>findAllClients(){
        List<ClientFindDto> allClients = clientService.findAllClients();
        return ResponseEntity.status(HttpStatus.OK).body(allClients);
    }

    @PatchMapping("{clientId}")
    ResponseEntity<ClientFindDto>updateClient(@PathVariable long clientId,
                                              @RequestBody ClientUpdateDto clientCreateDto){
        ClientFindDto clientFindDto = clientService.patchClientInfo(clientId, clientCreateDto);
        return ResponseEntity.accepted().body(clientFindDto);
    }
    @DeleteMapping("{clientId}")
    ResponseEntity<Void>deleteClient(@PathVariable long clientId){
        clientService.deleteClient(clientId);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping("/status/{clientId}")
    ResponseEntity<Void>statusChange(@PathVariable long clientId){
        clientService.changeToInactive(clientId);
        return ResponseEntity.accepted().build();
    }
}
