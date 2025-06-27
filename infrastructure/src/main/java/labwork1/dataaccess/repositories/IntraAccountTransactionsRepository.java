package labwork1.dataaccess.repositories;

import labwork1.transactions.IntraTransaction;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.UUID;

public class IntraAccountTransactionsRepository implements labwork1.repositories.IntraAccountTransactionsRepository {
    AbstractMap<UUID, IntraTransaction> intraTransactions = new HashMap<UUID, IntraTransaction>();

    public void addTransaction(IntraTransaction transaction) {
        intraTransactions.put(transaction.id, transaction);
    }
}
