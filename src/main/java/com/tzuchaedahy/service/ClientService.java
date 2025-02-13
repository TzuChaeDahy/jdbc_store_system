package com.tzuchaedahy.service;

import com.tzuchaedahy.domain.Client;
import com.tzuchaedahy.repository.ClientDao;

public class ClientService {
    private ClientDao clientDao;

    public ClientService() {
        this.clientDao = new ClientDao();
    }

    public void registerClient(Client client) throws RuntimeException {
        this.clientDao.save(client);
    }
}
