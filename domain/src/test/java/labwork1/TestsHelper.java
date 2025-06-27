package labwork1;

import labwork1.client.Client;
import labwork1.valueobjects.Money;
import labwork1.valueobjects.PassportData;
import labwork1.valueobjects.Percent;

public class TestsHelper {
    public static Client getVerifiedClient() {
        return Client.builder()
                .withBank(getBank())
                .withName("Sveta")
                .withSurname("Smirnova")
                .withAddress("ITMO")
                .withPassportData(new PassportData(1234, 123456))
                .build();
    }

    public static Client getDoubtfulClient() {
        return Client.builder()
                .withBank(getBank())
                .withName("Sveta")
                .withSurname("Smirnova")
                .build();
    }

    public static Bank getBank() {
        return Bank.builder()
                .withName("Java")
                .withFixedPercent(new Percent(3.65))
                .addDepositPercents(new Money(10000), new Percent(7.3))
                .withTransactionLimitForDoubtfulClients(new Money(3000))
                .withCommission(new Money(100))
                .build();
    }
}
