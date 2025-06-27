package labwork1.accounts.operationresults.errors;

public class NotEnoughMoneyError implements OperationResultError {
    public String getErrorMessage() {
        return "Not enough Money";
    }
}
