package labwork1.notification;

import labwork1.client.Client;
import labwork1.notification.messengeradapters.AddressMessengerAdapter;
import labwork1.notification.messengeradapters.ConsoleMessengerAdapter;
import labwork1.notification.thirdpartymessengers.SupportedMessengers;

import java.util.*;

public class BaseEventManager implements EventManager {
    private AbstractMap<SupportedMessengers, List<Client>> subscribers = new HashMap<>();

    public BaseEventManager(Collection<SupportedMessengers> supportedMessengers) {
        for (SupportedMessengers supportedMessenger : supportedMessengers) {
            subscribers.put(supportedMessenger, new ArrayList<Client>());
        }
    }

    public void subscribe(SupportedMessengers messenger, Client client) {
        subscribers.get(messenger).add(client);
    }

    public void unsubscribe(SupportedMessengers messenger, Client client) {
        subscribers.get(messenger).remove(client);
    }

    public void notifyClientsWithConsoleMessenger(String message) {
        for (Client client: subscribers.get(SupportedMessengers.CONSOLE)) {
            ConsoleMessengerAdapter adapter = new ConsoleMessengerAdapter(client);
            adapter.sendMessage(message);
        }
    }

    public void notifyClientsWithAddressMessenger(String message) {
        for (Client client: subscribers.get(SupportedMessengers.ADDRESS)) {
            AddressMessengerAdapter adapter = new AddressMessengerAdapter(client);
            adapter.sendMessage(message);
        }
    }

    public void notify(String message) {
        notifyClientsWithConsoleMessenger(message);
        notifyClientsWithAddressMessenger(message);
    }
}
