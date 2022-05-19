package com.test.shopservice.controller;

import com.test.shopservice.dto.ClientDto;
import com.test.shopservice.entity.Client;
import com.test.shopservice.exception.CustomBadRequestException;
import com.test.shopservice.exception.CustomNotFoundException;
import com.test.shopservice.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping(value = "clients")
public class ClientController {

    private final ClientService clientService;

    private final ErrorLog errorLog = (httpStatus, httpMethod, message) -> {
        String msg = httpMethod.name() + " : " + httpStatus.name() + " : " + httpStatus.value() + " : " + message;
        Logger logger = LogManager.getLogger("client-log");
        logger.error(msg);
    };

    @GetMapping(value = "/{id}")
    public ResponseEntity<Client> getClient(@PathVariable("id") Integer id) {

        Client client = this.clientService.getClient(id);

        if (client == null) {

            String MESSAGE_NOT_FOUND = "Cliente no encontrado";
            errorLog.register(HttpStatus.NOT_FOUND, HttpMethod.GET, MESSAGE_NOT_FOUND);
            throw new CustomNotFoundException(MESSAGE_NOT_FOUND);
        }

        return ResponseEntity.ok(client);
    }

    @GetMapping
    public ResponseEntity<List<Client>> listCLient() {

        return ResponseEntity.ok(this.clientService.listProduct());
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@Valid @RequestBody ClientDto clientDto, BindingResult result) {

        if (result.hasErrors()) {

            errorLog.register(HttpStatus.BAD_REQUEST, HttpMethod.POST, this.toStringMessage(result));
            throw new CustomBadRequestException(this.toStringMessage(result));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(this.clientService.createClient(clientDto));

    }

    private String toStringMessage(BindingResult result) {

        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(err -> {
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());

        return errors.toString();
    }

}
