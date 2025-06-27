package labwork1.client;
import labwork1.Bank;

/**
 * Intermediate builder to necessarily specify client surname
 */
public class ClientSurnameBuilder {
    private Bank bank;
    private final String name;

    ClientSurnameBuilder(Bank bank, String name) {
        this.bank = bank;
        this.name = name;
    }

    public ClientBuilder withSurname(String surname) {
        if (surname == null)
            throw new NullPointerException("Client surname must not be null");

        return new ClientBuilder(bank, name, surname);
    }
}
