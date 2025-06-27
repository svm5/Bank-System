package labwork1;

import labwork1.argumentproviders.CalculateDepositPercentDataProvider;
import labwork1.valueobjects.Money;
import labwork1.valueobjects.Percent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.AbstractMap;
import java.util.HashMap;

public class BankTest {
    @Test
    public void createBankSuccessfullyTest() {
        BankBuilder builder = Bank.builder()
                .withName("Java")
                .withFixedPercent(new Percent(5))
                .addDepositPercents(new Money(100000), new Percent(3))
                .withTransactionLimitForDoubtfulClients(new Money(50000))
                .withCommission(new Money(100));
        Assertions.assertDoesNotThrow(() -> builder.build());
    }

    @Test
    public void createBankFailureTest() {
        BankBuilder builder = Bank.builder().withName("Java").withFixedPercent(new Percent(5));
        Assertions.assertThrows(NullPointerException.class, () -> builder.build());
    }

    @ParameterizedTest
    @ArgumentsSource(CalculateDepositPercentDataProvider.class)
    public void calculateDepositPercentTest(Money amount, Percent expectedPercent) {
        AbstractMap<Money, Percent> map = new HashMap<>() {{
            put(new Money(100), new Percent(3));
            put(new Money(200), new Percent(5));
            put(new Money(500), new Percent(7));
        }};

        Bank bank = Bank.builder()
                .withName("Java")
                .withFixedPercent(new Percent(5))
                .withDepositPercents(map)
                .withTransactionLimitForDoubtfulClients(new Money(50000))
                .withCommission(new Money(100)).build();

        Percent percent = bank.calculateDepositPercent(amount);
        Assertions.assertEquals(percent, expectedPercent);
    }
}

