package labwork1.client;

import labwork1.Bank;
import labwork1.valueobjects.PassportData;

/**
 * Builder to create client and specify extra properties(address and passport data)
 */
public class ClientBuilder {
    private final Bank bank;
    private final String name;
    private final String surname;
    private String address;
    private PassportData passportData;

    ClientBuilder(Bank bank, String name, String surname) {
        this.bank = bank;
        this.name = name;
        this.surname = surname;
    }

    public ClientBuilder withAddress(String address) {
        this.address = address;
        return this;
    }

    public ClientBuilder withPassportData(PassportData passportData) {
        this.passportData = passportData;
        return this;
    }

    public Client build()
    {
        return new Client(bank, name, surname, address, passportData);
    }
}
