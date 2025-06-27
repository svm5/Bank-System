package labwork1.services;

import labwork1.Bank;
import labwork1.RepositoryManager;
import labwork1.client.Client;
import labwork1.createsettings.CreateClientSettings;
import labwork1.notification.thirdpartymessengers.SupportedMessengers;
import labwork1.valueobjects.PassportData;

import java.util.UUID;

public class ClientService {
    private final RepositoryManager repositoryManager;

    public ClientService(RepositoryManager repositoryManager)
    {
        this.repositoryManager = repositoryManager;
    }

    public void createClient(Bank bank, CreateClientSettings settings) {
        Client newClient = Client.builder()
                .withBank(bank)
                .withName(settings.name)
                .withSurname(settings.surname)
                .withAddress(settings.address)
                .withPassportData(settings.passportData)
                .build();
        repositoryManager.clientRepository.addClient(newClient);
    }

    public void subscribeNotifications(UUID clientId, SupportedMessengers messenger) {
        Client client = repositoryManager.clientRepository.getClientById(clientId);
        client.bank.eventManager.subscribe(messenger, client);
    }

    public void unsubscribeNotifications(UUID clientId, SupportedMessengers messenger) {
        Client client = repositoryManager.clientRepository.getClientById(clientId);
        client.bank.eventManager.unsubscribe(messenger, client);
    }

    public void setAddress(UUID clientId, String address) {
        Client client = repositoryManager.clientRepository.getClientById(clientId);
        client.setAddress(address);
        repositoryManager.clientRepository.updateClient(client);
    }

    public void setPassportData(UUID clientId, PassportData passportData) {
        Client client = repositoryManager.clientRepository.getClientById(clientId);
        client.setPassportData(passportData);
        repositoryManager.clientRepository.updateClient(client);
    }
}
