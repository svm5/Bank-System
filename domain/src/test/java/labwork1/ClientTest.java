package labwork1;

import labwork1.argumentproviders.BankProvider;
import labwork1.client.Client;
import labwork1.valueobjects.PassportData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

public class ClientTest {
    @ParameterizedTest
    @ArgumentsSource(BankProvider.class)
    public void createDoubtfulClientTest(Bank bank) {
        Client client = Client.builder().withBank(bank).withName("Sveta").withSurname("Smirnova").build();
        Assertions.assertTrue(client.isClientDoubtful());
    }

    @ParameterizedTest
    @ArgumentsSource(BankProvider.class)
    public void createVerifiedClientTest(Bank bank) {
        Client client = Client.builder()
                .withBank(bank)
                .withName("Sveta")
                .withSurname("Smirnova")
                .withAddress("ITMO")
                .withPassportData(new PassportData(1234, 123456))
                .build();
        Assertions.assertFalse(client.isClientDoubtful());
    }

    @ParameterizedTest
    @ArgumentsSource(BankProvider.class)
    public void makeClientVerifiedTest(Bank bank) {
        Client client = Client.builder().withBank(bank).withName("Sveta").withSurname("Smirnova").build();
        Assertions.assertTrue(client.isClientDoubtful());

        client.setAddress("ITMO");
        client.setPassportData(new PassportData(1234, 123456));
        Assertions.assertFalse(client.isClientDoubtful());
    }
}
