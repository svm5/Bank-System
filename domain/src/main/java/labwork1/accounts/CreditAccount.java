package labwork1.accounts;

import labwork1.accounts.operationresults.OperationResult;
import labwork1.accounts.operationresults.SuccessOperationResult;
import labwork1.client.Client;
import labwork1.valueobjects.Money;

import java.util.UUID;

public class CreditAccount extends BaseAccount {
    public CreditAccount(Client client, double balance) {
        super(client, balance);
    }

    private CreditAccount(UUID id, Client client, double balance) {
        super(id, client, balance);
    }

    public AccountTypes getAccountType() {
        return AccountTypes.CREDIT;
    }

    /**
     * Credit account restrictions - if the balance is negative, a commission is charged
     * @param amount - how much should the balance be decreased by
     */
    @Override
    public OperationResult withdraw(Money amount) {
       if (balance < 0)
       {
           balance -= client.bank.getCommission().value();
       }
       balance -= amount.value();
       return new SuccessOperationResult();
   }

    public CreditAccount getCopy()
    {
        return new CreditAccount(id, client, balance);
    }

    public void chargeInterest() {}

    public void payInterest() {}
}
