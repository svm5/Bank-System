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

public class WithdrawalTransaction extends IntraTransaction {
    private Account backup;

    public WithdrawalTransaction(BaseAccount account, Money amount) {
        super(TransactionType.WITHDRAWAL, amount, account);
    }

    public TransactionResult execute() {
        backup = account.getCopy();
        OperationResult result = account.withdraw(amount);
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
