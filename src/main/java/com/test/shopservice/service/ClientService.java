package com.test.shopservice.service;

import com.test.shopservice.entity.Client;
import com.test.shopservice.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public Client getClient(Integer id) {

        return this.clientRepository.findById(id).orElse(null);
    }

    public List<Client> listProduct() {

        return this.clientRepository.findAll();
    }

    public Client createClient(Client client) {

        return this.clientRepository.save(client);
    }
}
