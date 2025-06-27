package labwork1.accounts;

import labwork1.accounts.operationresults.FailureOperationResult;
import labwork1.accounts.operationresults.OperationResult;
import labwork1.accounts.operationresults.SuccessOperationResult;
import labwork1.accounts.operationresults.errors.OperationIsNotAllowedNowError;
import labwork1.client.Client;
import labwork1.valueobjects.Money;
import labwork1.valueobjects.Percent;

import java.util.Date;
import java.util.UUID;

public class DepositAccount extends BaseAccount {
    private final Date expirationDate;
    private final Percent percent;
    private Money chargedInterest;

    public DepositAccount(Client client, Money depositAmount, Date expirationDate) {
        super(client, depositAmount.value());

        this.expirationDate = expirationDate;
        this.percent = client.bank.calculateDepositPercent(depositAmount);
        this.chargedInterest = new Money(0);
    }

    private DepositAccount(UUID id, Client client, double balance, Date expirationDate, Percent percent) {
        super(id, client, balance);

        this.expirationDate = expirationDate;
        this.percent = percent;
        this.chargedInterest = new Money(0);
    }

    public AccountTypes getAccountType() {
        return AccountTypes.DEPOSIT;
    }

    /**
     * Deposit restrictions - you cannot withdraw money before the expiration date, so if the expiration date is not completed, an error will be returned.
     * @param amount - how much should the balance be decreased by
     */
    @Override
    public OperationResult withdraw(Money amount) {
        Date now = new Date();
        if (now.before(expirationDate)) {
            return new FailureOperationResult(new OperationIsNotAllowedNowError());
        }

        balance -= amount.value();
        return new SuccessOperationResult();
    }

    @Override
    public DepositAccount getCopy()
    {
        return new DepositAccount(id, client, balance, expirationDate, percent);
    }

    public void chargeInterest() {
        chargedInterest = new Money(chargedInterest.value() + balance * percent.value() / (365 * 100));
    }

    public void payInterest() {
        balance += chargedInterest.value();
        chargedInterest = new Money(0);
    }
}
