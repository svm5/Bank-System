package labwork1.transactiontest;

import labwork1.accounts.DebitAccount;
import labwork1.transactions.ReplenishmentTransaction;
import labwork1.transactions.transactionresult.FailureTransactionResult;
import labwork1.transactions.transactionresult.TransactionResult;
import labwork1.transactions.transactionresult.SuccessTransactionResult;
import labwork1.transactions.transactionresult.reasons.AgainRollbackTransaction;
import labwork1.valueobjects.Money;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static labwork1.TestsHelper.getVerifiedClient;

public class ReplenishmentTransactionTest {
    @Test
    public void executeSuccessfullyTest() {
        DebitAccount debitAccount = new DebitAccount(getVerifiedClient(), 200);
        ReplenishmentTransaction replenishmentTransaction = new ReplenishmentTransaction(debitAccount, new Money(300));

        TransactionResult transactionResult = replenishmentTransaction.execute();

        Assertions.assertInstanceOf(SuccessTransactionResult.class, transactionResult);
        Assertions.assertEquals(500, debitAccount.getBalance());
    }

    @Test
    public void rollbackTest() {
        DebitAccount debitAccount = new DebitAccount(getVerifiedClient(), 200);
        ReplenishmentTransaction replenishmentTransaction = new ReplenishmentTransaction(debitAccount, new Money(300));

        replenishmentTransaction.execute();
        TransactionResult transactionResult = replenishmentTransaction.rollback();

        Assertions.assertInstanceOf(SuccessTransactionResult.class, transactionResult);
        Assertions.assertEquals(200, debitAccount.getBalance());
    }

    @Test
    public void rollbackTwiceTest() {
        DebitAccount debitAccount = new DebitAccount(getVerifiedClient(), 200);
        ReplenishmentTransaction replenishmentTransaction = new ReplenishmentTransaction(debitAccount, new Money(300));

        replenishmentTransaction.execute();
        replenishmentTransaction.rollback();
        TransactionResult transactionResult = replenishmentTransaction.rollback();

        Assertions.assertInstanceOf(FailureTransactionResult.class, transactionResult);
        Assertions.assertEquals(200, debitAccount.getBalance());

        FailureTransactionResult failureTransactionResult = (FailureTransactionResult) transactionResult;
        Assertions.assertInstanceOf(AgainRollbackTransaction.class, failureTransactionResult.reason());
    }
}
