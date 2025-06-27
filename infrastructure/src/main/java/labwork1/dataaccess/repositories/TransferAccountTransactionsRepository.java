package labwork1.dataaccess.repositories;

import labwork1.transactions.TransferTransaction;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.UUID;

public class TransferAccountTransactionsRepository implements labwork1.repositories.TransferAccountTransactionsRepository {
    AbstractMap<UUID, TransferTransaction> transferTransactions = new HashMap<UUID, TransferTransaction>();

    public void addTransaction(TransferTransaction transaction) {
        transferTransactions.put(transaction.id, transaction);
    }
}
