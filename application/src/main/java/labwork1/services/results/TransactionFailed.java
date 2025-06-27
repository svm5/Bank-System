package labwork1.services.results;

import labwork1.transactions.transactionresult.FailureTransactionResult;

public record TransactionFailed(FailureTransactionResult transactionResult) implements FailureAccountOperationReason { }
