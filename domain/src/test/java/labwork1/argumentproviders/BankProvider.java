package labwork1.argumentproviders;

import labwork1.Bank;
import labwork1.valueobjects.Money;
import labwork1.valueobjects.Percent;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class BankProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        Bank bank = Bank.builder()
                .withName("Java")
                .withFixedPercent(new Percent(10))
                .addDepositPercents(new Money(10000), new Percent(1.3))
                .withTransactionLimitForDoubtfulClients(new Money(50000))
                .withCommission(new Money(100))
                .build();

        return Stream.of(
                Arguments.of(bank)
        );
    }
}
