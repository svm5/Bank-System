package labwork1.notification.messengeradapters;

import labwork1.client.Client;
import labwork1.notification.thirdpartymessengers.ConsoleMessenger;

public class ConsoleMessengerAdapter implements Messenger {
    private Client client;
    private final ConsoleMessenger messenger;

    public ConsoleMessengerAdapter(Client client) {
        this.client = client;
        messenger = new ConsoleMessenger();
    }

    public void sendMessage(String message) {
        messenger.sendMessage(client.id, message);
    }
}
