package labwork1.notification.thirdpartymessengers;

import java.util.UUID;

public class ConsoleMessenger {
    public void sendMessage(UUID clientId, String message) {
        System.out.println("New message for client with id " + clientId);
        System.out.println(message);
    }
}
