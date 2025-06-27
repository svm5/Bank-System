package labwork1.createsettings;

import labwork1.valueobjects.PassportData;

public class CreateClientSettings {
    public final String name;
    public final String surname;
    public final String address;
    public final PassportData passportData;

    public CreateClientSettings(String name, String surname, String address, PassportData passportData) {
        if (!validate(name, surname))
            throw new NullPointerException("Name and surname are required");

        this.name = name;
        this.surname = surname;
        this.address = address;
        this.passportData = passportData;
    }

    public boolean validate(String name, String surname) {
        return name != null && surname != null;
    }
}
