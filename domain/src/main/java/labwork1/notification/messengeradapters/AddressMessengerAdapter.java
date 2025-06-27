package labwork1.notification.messengeradapters;

import labwork1.client.Client;
import labwork1.notification.thirdpartymessengers.AddressMessenger;

public class AddressMessengerAdapter implements Messenger {
    private final AddressMessenger messenger;
    private final Client client;

    public AddressMessengerAdapter(Client client) {
        this.client = client;
        this.messenger = new AddressMessenger();
    }

    public void sendMessage(String message) {
        messenger.deliverMessage(client.getAddress(), message);
    }
}
