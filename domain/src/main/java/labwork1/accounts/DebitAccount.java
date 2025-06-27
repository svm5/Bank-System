package labwork1.accounts;

import labwork1.accounts.operationresults.FailureOperationResult;
import labwork1.accounts.operationresults.OperationResult;
import labwork1.accounts.operationresults.SuccessOperationResult;
import labwork1.accounts.operationresults.errors.NotEnoughMoneyError;
import labwork1.client.Client;
import labwork1.valueobjects.Money;
import labwork1.valueobjects.Percent;

import java.util.UUID;

/**
 * The type of account that you can use to make replenishment, withdrawals, and transfers, but the balance should not become negative.
 */
public class DebitAccount extends BaseAccount {
    private final Percent fixedPercent;
    private Money chargedInterest;

    public DebitAccount(Client client, double balance) {
        super(client, balance);

        this.fixedPercent = client.bank.getFixedPercent();
        this.chargedInterest = new Money(0);
    }

    private DebitAccount(UUID id, Client client, double balance) {
        super(id, client, balance);

        this.fixedPercent = client.bank.getFixedPercent();
        this.chargedInterest = new Money(0);
    }

    public AccountTypes getAccountType() {
        return AccountTypes.DEBIT;
    }

    /**
     * Debit account restrictions - the balance should not become negative, so if there is not enough money in the balance, an error will be returned.
     * @param amount - how much should the balance be decreased bys
     */
    @Override
    public OperationResult withdraw(Money amount) {
        if (balance < amount.value())
            return new FailureOperationResult(new NotEnoughMoneyError());

        balance -= amount.value();
        return new SuccessOperationResult();
    }

    @Override
    public DebitAccount getCopy() {
        return new DebitAccount(id, client, balance);
    }

    public void chargeInterest() {
        chargedInterest = new Money(chargedInterest.value() + balance * fixedPercent.value() / (365 * 100));
    }

    public void payInterest() {
        balance += chargedInterest.value();
        chargedInterest = new Money(0);
    }
}
