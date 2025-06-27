package labwork1.notification;

import labwork1.client.Client;
import labwork1.notification.thirdpartymessengers.SupportedMessengers;

public interface EventManager {
    void subscribe(SupportedMessengers messenger, Client client);
    void unsubscribe(SupportedMessengers messenger, Client client);
    void notify(String message);
}
