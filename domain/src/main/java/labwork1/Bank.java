package labwork1;

import labwork1.notification.EventManager;
import labwork1.valueobjects.Money;
import labwork1.valueobjects.Percent;

import java.util.*;

/**
 * A class for storing information about a bank
 */
public class Bank {
    public UUID id;
    private String name;
    private Percent fixedPercent;
    private AbstractMap<Money, Percent> depositPercents;
    private Money transactionLimitForDoubtfulClients;
    private Money commission;
    public final EventManager eventManager;

    /**
     *
     * @param name - name of the bank
     * @param fixedPercent - the percentage of accrual on the debit account balance
     * @param depositPercents - to determine the percentage for the deposit account based on the initial amount
     * @param transactionLimitForDoubtfulClients - operation limit for clients without address/passport data
     * @param commission - fixed amount for transactions with credit accounts if the balance is negative
     * @param manager - manager for notifications about updates
     */
    Bank(String name,
                Percent fixedPercent,
                AbstractMap<Money, Percent> depositPercents,
                Money transactionLimitForDoubtfulClients,
                Money commission,
                EventManager manager) {
        id = UUID.randomUUID();

        this.eventManager = manager;
        this.name = name;
        this.fixedPercent = fixedPercent;
        this.depositPercents = depositPercents;

        if (!this.depositPercents.containsKey(new Money(0))) {
            this.depositPercents.put(new Money(0), new Percent(0));
        }

        this.transactionLimitForDoubtfulClients = transactionLimitForDoubtfulClients;
        this.commission = commission;
    }

    public Money getCommission() {
        return commission;
    }

    public String getName() {
        return name;
    }

    public void setCommission(Money commission) {
        this.commission = commission;
    }

    public void setFixedPercent(Percent fixedPercent) {
        this.fixedPercent = fixedPercent;
    }

    public Percent getFixedPercent() {
        return fixedPercent;
    }

    public void setDepositPercents(AbstractMap<Money, Percent> depositPercents) {
        this.depositPercents = depositPercents;
    }

    public Money getTransactionLimitForDoubtfulClients() {
        return transactionLimitForDoubtfulClients;
    }

    public void setTransactionLimitForDoubtfulClients(Money transactionLimitForDoubleClients) {
        this.transactionLimitForDoubtfulClients = transactionLimitForDoubleClients;
    }

    /**
     * Algorithm of calculation
     * 1 - search elements where key(money amount) less than passed amount
     * 2 - return last of searched elements
     * @param amount - the amount of money that the client deposits
     * @return the percentage of the balance for the initial amount
     */
    public Percent calculateDepositPercent(Money amount) {
        return depositPercents
                .entrySet()
                .stream()
                .filter(e -> e.getKey().value() <= amount.value())
                .reduce((first, second) -> second).get().getValue();
    }

    public static BankBuilder builder() {
        return new BankBuilder();
    }
}
