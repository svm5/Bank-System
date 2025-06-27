package labwork1.client;

import labwork1.Bank;

/**
 * Intermediate builder to necessarily specify bank where client want to register
 */
public class ClientBankBuilder {
    public ClientNameBuilder withBank(Bank bank) {
        if (bank == null)
            throw new NullPointerException("Bank must not be null");

        return new ClientNameBuilder(bank);
    }
}
