package labwork1.transactions.transactionresult.reasons;

import labwork1.accounts.operationresults.FailureOperationResult;

public record OperationFailed(FailureOperationResult result) implements Reason {
    public String getReason() {
        return "Operation failed: " + result().error().getErrorMessage();
    }
}
