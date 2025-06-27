package labwork1.accounts.operationresults;

import labwork1.accounts.operationresults.errors.OperationResultError;

public record FailureOperationResult(OperationResultError error) implements OperationResult { }
