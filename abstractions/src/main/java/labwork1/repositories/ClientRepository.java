package labwork1.repositories;

import labwork1.client.Client;

import java.util.Collection;
import java.util.UUID;

public interface ClientRepository {
    void addClient(Client client);
    Client getClientById(UUID id);
    void updateClient(Client client);
    Collection<Client> getAllClients();
}
