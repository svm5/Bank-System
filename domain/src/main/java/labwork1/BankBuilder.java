package labwork1;

import labwork1.notification.BaseEventManager;
import labwork1.notification.EventManager;
import labwork1.notification.thirdpartymessengers.SupportedMessengers;
import labwork1.valueobjects.Money;
import labwork1.valueobjects.Percent;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A class for step-by-step bank creation
 */
public class BankBuilder {
    private String name;
    private Percent fixedPercent; // add map percent
    private AbstractMap<Money, Percent> depositPercents;
    private Money transactionLimitForDoubtfulClients;
    private Money commission;
    private EventManager eventManager;

    BankBuilder() {
        depositPercents = new HashMap<>();
        this.eventManager = new BaseEventManager(
                Stream.of(SupportedMessengers.CONSOLE, SupportedMessengers.ADDRESS).collect(Collectors.toList()));
    }

    public BankBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public BankBuilder withFixedPercent(Percent fixedPercent) {
        this.fixedPercent = fixedPercent;
        return this;
    }

    public BankBuilder addDepositPercents(Money limit, Percent percent) {
        this.depositPercents.put(limit, percent);
        return this;
    }

    public BankBuilder withDepositPercents(AbstractMap<Money, Percent> depositPercents) {
        this.depositPercents = depositPercents;
        return this;
    }

    public BankBuilder withTransactionLimitForDoubtfulClients(Money limit) {
        this.transactionLimitForDoubtfulClients = limit;
        return this;
    }

    public BankBuilder withCommission(Money commission) {
        this.commission = commission;
        return this;
    }

    public BankBuilder withEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
        return this;
    }

    public Bank build() throws NullPointerException {
        if (name == null) {
            throw new NullPointerException("Name must not be null");
        }

        if (fixedPercent == null) {
            throw new NullPointerException("Fixed percent must not be null");
        }

        if (depositPercents == null || depositPercents.isEmpty()) {
            throw new NullPointerException("Deposit percent must not be null");
        }

        if (transactionLimitForDoubtfulClients == null) {
            throw new NullPointerException("Transaction limit for doubtful clients must not be null");
        }

        if (commission == null) {
            throw new NullPointerException("Commission must not be null");
        }

        return new Bank(
                name,
                fixedPercent,
                depositPercents,
                transactionLimitForDoubtfulClients,
                commission,
                eventManager
        );
    }
}
