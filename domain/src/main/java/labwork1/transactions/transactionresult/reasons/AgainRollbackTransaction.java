package labwork1.transactions.transactionresult.reasons;

public record AgainRollbackTransaction() implements Reason {
    public String getReason() {
        return "Transaction has already been rolled back";
    }
}
