package labwork1.repositories;

import labwork1.transactions.TransferTransaction;

public interface TransferAccountTransactionsRepository {
    public void addTransaction(TransferTransaction transaction);
}
