package labwork1.transactions;

import labwork1.valueobjects.Money;

import java.util.Date;
import java.util.UUID;

public abstract class BaseTransaction implements Transaction {
    public UUID id;
    protected final TransactionType type;
    protected final Money amount;
    protected Date date;
    protected boolean wasRollbacked;

    public BaseTransaction(TransactionType type, Money amount) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.amount = amount;
    }
}
