package labwork1.accounts;

import labwork1.accounts.operationresults.OperationResult;
import labwork1.valueobjects.Money;

/**
 * An interface that defines what actions all account types should perform
 */
public interface Account {
    /**
     * Info about account type
     * @return Account type as enum
     */
    AccountTypes getAccountType();

    /**
     * Add money to balance
     * @param amount - how much should the balance be increased by
     * @return result of operation - success or failure(with error)
     */
    OperationResult replenish(Money amount);

    /**
     * Remove money from balance
     * @param amount - how much should the balance be decreased by
     * @return result of operation - success or failure(with error)
     */
    OperationResult withdraw(Money amount);

    /**
     * Create copy of account
     * @return copy
     */
    Account getCopy();
    double getBalance();
    void setBalance(double balance);

    /**
     * Charge interest, but do not add to the balance
     */
    void chargeInterest();

    /**
     * Add the accrued money to the balance
     */
    void payInterest();
}
