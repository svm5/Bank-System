package labwork1.client;

import labwork1.Bank;

/**
 * Intermediate builder to necessarily specify client name
 */
public class ClientNameBuilder {
    private Bank bank;

    ClientNameBuilder(Bank bank) {
        this.bank = bank;
    }

    public ClientSurnameBuilder withName(String name){
        if (name == null)
            throw new NullPointerException("Client name must not be null");

        return new ClientSurnameBuilder(bank, name);
    }
}
