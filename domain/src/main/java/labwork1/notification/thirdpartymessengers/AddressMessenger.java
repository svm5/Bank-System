package labwork1.notification.thirdpartymessengers;

public class AddressMessenger {
    public void deliverMessage(String address, String message) {
        System.out.println("Message text: " + message);
        System.out.println("Message was delivered by address: " + address);
    }
}
