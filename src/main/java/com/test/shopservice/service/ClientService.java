package com.test.shopservice.service;

import com.test.shopservice.dto.ClientDto;
import com.test.shopservice.entity.Client;
import com.test.shopservice.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Client getClient(Integer id) {

        return this.clientRepository.findById(id).orElse(null);
    }

    public List<Client> listProduct() {

        return this.clientRepository.findAll();
    }

    public Client createClient(ClientDto clientDto) {

        return this.clientRepository.save(modelMapper.map(clientDto, Client.class));
    }
}
