package labwork1.transactions;

import labwork1.accounts.Account;
import labwork1.accounts.BaseAccount;
import labwork1.transactions.transactionresult.FailureTransactionResult;
import labwork1.transactions.transactionresult.TransactionResult;
import labwork1.transactions.transactionresult.SuccessTransactionResult;
import labwork1.transactions.transactionresult.reasons.AgainRollbackTransaction;
import labwork1.valueobjects.Money;

public class TransferTransaction extends BaseTransaction {
    private final BaseAccount accountTransferFrom;
    private final BaseAccount accountTransferTo;
    private Account backupFrom;
    private Account backupTo;

    public TransferTransaction(BaseAccount from, BaseAccount to, Money amount) {
        super(TransactionType.TRANSFER, amount);

        this.accountTransferFrom = from;
        this.accountTransferTo = to;
    }

    public TransactionResult execute() {
        backupFrom = accountTransferFrom.getCopy();
        backupTo = accountTransferTo.getCopy();

        WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction(accountTransferFrom, amount);
        ReplenishmentTransaction replenishmentTransaction = new ReplenishmentTransaction(accountTransferTo, amount);

        TransactionResult withdrawResult = withdrawalTransaction.execute();
        if (withdrawResult instanceof FailureTransactionResult) {
            wasRollbacked = true;
            return withdrawResult;
        }

        TransactionResult replenishResult = replenishmentTransaction.execute();
        if (replenishResult instanceof FailureTransactionResult) {
            withdrawalTransaction.rollback(); // !!!
            wasRollbacked = true;
            return replenishResult;
        }

        return new SuccessTransactionResult();
    }

    public TransactionResult rollback() {
        if (wasRollbacked)
            return new FailureTransactionResult(new AgainRollbackTransaction());

        accountTransferFrom.setBalance(backupFrom.getBalance());
        accountTransferTo.setBalance(backupTo.getBalance());
        wasRollbacked = true;

        return new SuccessTransactionResult();
    }
}
