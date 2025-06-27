package labwork1.dataaccess.repositories;

import labwork1.client.Client;

import java.util.*;

public class ClientRepository implements labwork1.repositories.ClientRepository {
    AbstractMap<UUID, Client> clients = new HashMap<UUID, Client>();

    public void addClient(Client client) {
        clients.put(client.id, client);
    }

    public Client getClientById(UUID id) {
        return clients.get(id);
    }

    public void updateClient(Client client) {
        clients.put(client.id, client);
    }

    public Collection<Client> getAllClients() {
        return clients.values();
    }
}
