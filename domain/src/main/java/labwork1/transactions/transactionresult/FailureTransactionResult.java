package labwork1.transactions.transactionresult;

import labwork1.transactions.transactionresult.reasons.Reason;

public record FailureTransactionResult(Reason reason) implements TransactionResult { }
