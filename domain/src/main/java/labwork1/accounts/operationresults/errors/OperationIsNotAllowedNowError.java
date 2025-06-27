package labwork1.accounts.operationresults.errors;

public final class OperationIsNotAllowedNowError implements OperationResultError {
    public String getErrorMessage() {
        return "This operation is not allowed now";
    }
}
