package com.test.shopservice.controller;

import com.test.shopservice.dto.ClientDto;
import com.test.shopservice.entity.Client;
import com.test.shopservice.exception.CustomBadRequestException;
import com.test.shopservice.exception.CustomNotFoundException;
import com.test.shopservice.exception.ErrorMessage;
import com.test.shopservice.log.ErrorLog;
import com.test.shopservice.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    private ErrorLog errorLog;

    @Operation(tags = "Servicio Cliente", summary = "Listar Clientes.", description = "Obtiene una lista de todos los clientes.")
    @GetMapping
    public ResponseEntity<List<Client>> listCLient() {
        return ResponseEntity.ok(this.clientService.listProduct());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado", content = {@Content}),
            @ApiResponse(responseCode = "400", description = "Cliente no encontrado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))})})
    @Operation(tags = "Servicio Cliente", summary = "Obtener Cliente por ID.", description = "Obtiene los datos de un cliente correspondiente a un ID dado.")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Client> getClient(@PathVariable("id") Integer id) {

        Client client = this.clientService.getClient(id);
        if (client == null) {
            errorLog.register(HttpStatus.NOT_FOUND, HttpMethod.GET, "Cliente no encontrado","client-log");
            throw new CustomNotFoundException("Cliente no encontrado");
        }

        return ResponseEntity.ok(client);
    }


    @PreAuthorize("hasRole('root')")
    @Operation(tags = "Servicio Cliente", summary = "Crear  Cliente.", description = "Registra un nuevo cliente .")
    @PostMapping
    public ResponseEntity<Client> createClient(@Valid @RequestBody ClientDto clientDto, BindingResult result) {

        if (result.hasErrors()) {
            errorLog.register(HttpStatus.BAD_REQUEST, HttpMethod.POST, this.toStringMessage(result),"client-log");
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
