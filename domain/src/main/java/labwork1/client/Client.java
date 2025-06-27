package labwork1.client;

import labwork1.Bank;
import labwork1.valueobjects.PassportData;

import java.util.UUID;

/**
 * A class for storing information about a client
 */
public class Client {
    public final UUID id;
    public final Bank bank;
    private final String name;
    public final String surname;
    private String address;
    private PassportData passportData;
    private boolean isDoubtful;

    Client(Bank bank, String name, String surname, String address, PassportData passportData) {
        id = UUID.randomUUID();
        this.bank = bank;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.passportData = passportData;
        this.isDoubtful = isClientDoubtful();
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public void setAddress(String address) {
        this.address = address;
        isDoubtful = isClientDoubtful();
    }

    public void setPassportData(PassportData passportData) {
        this.passportData = passportData;
        isDoubtful = isClientDoubtful();
    }

    /**
     *
     * @return Builder to create Client
     */
    public static ClientBankBuilder builder() {
        return new ClientBankBuilder();
    }

    /**
     * Method to check if client doubtful
     * @return 'True' value if client did not specify address or passport data
     */
    public boolean isClientDoubtful() {
        return this.address == null || this.passportData == null;
    }
}
