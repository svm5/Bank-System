package labwork1.transactions;

import labwork1.transactions.transactionresult.TransactionResult;

public interface Transaction {
    TransactionResult execute();
    TransactionResult rollback();
}
