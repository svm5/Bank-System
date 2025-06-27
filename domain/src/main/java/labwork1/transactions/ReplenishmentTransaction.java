package labwork1.transactions;

import labwork1.accounts.Account;
import labwork1.accounts.BaseAccount;
import labwork1.accounts.operationresults.FailureOperationResult;
import labwork1.accounts.operationresults.OperationResult;
import labwork1.accounts.operationresults.SuccessOperationResult;
import labwork1.transactions.transactionresult.FailureTransactionResult;
import labwork1.transactions.transactionresult.TransactionResult;
import labwork1.transactions.transactionresult.SuccessTransactionResult;
import labwork1.transactions.transactionresult.reasons.AgainRollbackTransaction;
import labwork1.transactions.transactionresult.reasons.OperationFailed;
import labwork1.valueobjects.Money;

import java.util.Date;


public class ReplenishmentTransaction extends IntraTransaction {
    private Account backup;

    public ReplenishmentTransaction(BaseAccount account, Money amount) {
        super(TransactionType.REPLENISHMENT, amount, account);
    }

    public TransactionResult execute() {
        date = new Date();

        backup = account.getCopy();
        OperationResult result = account.replenish(amount);
        if (result instanceof SuccessOperationResult)
            return new SuccessTransactionResult();

        wasRollbacked = true;
        FailureOperationResult failureResult = (FailureOperationResult) result;
        return new FailureTransactionResult(new OperationFailed(failureResult));
    }

    public TransactionResult rollback() {
        if (wasRollbacked)
            return new FailureTransactionResult(new AgainRollbackTransaction());

        account.setBalance(backup.getBalance());
        wasRollbacked = true;
        return new SuccessTransactionResult();
    }
}
