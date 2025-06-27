package labwork1.transactiontest;

import labwork1.accounts.CreditAccount;
import labwork1.accounts.DebitAccount;
import labwork1.transactions.WithdrawalTransaction;
import labwork1.transactions.transactionresult.FailureTransactionResult;
import labwork1.transactions.transactionresult.TransactionResult;
import labwork1.transactions.transactionresult.SuccessTransactionResult;
import labwork1.transactions.transactionresult.reasons.OperationFailed;
import labwork1.valueobjects.Money;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static labwork1.TestsHelper.getVerifiedClient;

public class WithdrawalTransactionTest {
    @Test
    public void executeSuccessfullyTest() {
        DebitAccount debitAccount = new DebitAccount(getVerifiedClient(), 500);
        WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction(debitAccount, new Money(300));

        TransactionResult transactionResult = withdrawalTransaction.execute();

        Assertions.assertInstanceOf(SuccessTransactionResult.class, transactionResult);
        Assertions.assertEquals(200, debitAccount.getBalance());
    }

    @Test
    public void executeFailedOperationTest() {
        DebitAccount debitAccount = new DebitAccount(getVerifiedClient(), 500);
        WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction(debitAccount, new Money(30000));

        TransactionResult transactionResult = withdrawalTransaction.execute();

        Assertions.assertInstanceOf(FailureTransactionResult.class, transactionResult);
        Assertions.assertEquals(500, debitAccount.getBalance());
        FailureTransactionResult failureTransactionResult = (FailureTransactionResult) transactionResult;
        Assertions.assertInstanceOf(OperationFailed.class, failureTransactionResult.reason());
    }

    @Test
    public void rollbackTest() {
        CreditAccount creditAccount = new CreditAccount(getVerifiedClient(), 5000);
        WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction(creditAccount, new Money(3000));

        withdrawalTransaction.execute();
        TransactionResult transactionResult = withdrawalTransaction.rollback();

        Assertions.assertInstanceOf(SuccessTransactionResult.class, transactionResult);
        Assertions.assertEquals(5000, creditAccount.getBalance());
    }
}
