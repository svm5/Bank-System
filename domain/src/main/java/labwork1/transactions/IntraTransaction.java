package labwork1.transactions;

import labwork1.accounts.BaseAccount;
import labwork1.valueobjects.Money;

public abstract class IntraTransaction extends BaseTransaction {
    protected final BaseAccount account;

    public IntraTransaction(TransactionType type, Money amount, BaseAccount account) {
        super(type, amount);
        this.account = account;
    }
}
