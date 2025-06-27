package labwork1.repositories;

import labwork1.transactions.IntraTransaction;

public interface IntraAccountTransactionsRepository {
    public void addTransaction(IntraTransaction transaction);
}
