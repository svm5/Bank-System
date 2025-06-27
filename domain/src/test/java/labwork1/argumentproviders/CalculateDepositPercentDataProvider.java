package labwork1.argumentproviders;

import labwork1.valueobjects.Money;
import labwork1.valueobjects.Percent;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class CalculateDepositPercentDataProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(
                Arguments.of(new Money(50), new Percent(0)),
                Arguments.of(new Money(100), new Percent(3)),
                Arguments.of(new Money(187), new Percent(3)),
                Arguments.of(new Money(280), new Percent(5)),
                Arguments.of(new Money(900), new Percent(7))
        );
    }
}