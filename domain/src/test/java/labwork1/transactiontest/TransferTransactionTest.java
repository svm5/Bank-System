package labwork1.transactiontest;

import labwork1.accounts.DebitAccount;
import labwork1.transactions.TransferTransaction;
import labwork1.transactions.transactionresult.FailureTransactionResult;
import labwork1.transactions.transactionresult.TransactionResult;
import labwork1.transactions.transactionresult.SuccessTransactionResult;
import labwork1.valueobjects.Money;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static labwork1.TestsHelper.getVerifiedClient;

public class TransferTransactionTest {
    @Test
    public void executeTest() {
        DebitAccount firstAccount = new DebitAccount(getVerifiedClient(), 3000);
        DebitAccount secondAccount = new DebitAccount(getVerifiedClient(), 2000);

        TransferTransaction transaction = new TransferTransaction(firstAccount, secondAccount, new Money(1500));
        TransactionResult result = transaction.execute();

        Assertions.assertInstanceOf(SuccessTransactionResult.class, result);
        Assertions.assertEquals(1500, firstAccount.getBalance());
        Assertions.assertEquals(3500, secondAccount.getBalance());
    }

    @Test
    public void executeFailureTest() {
        DebitAccount firstAccount = new DebitAccount(getVerifiedClient(), 3000);
        DebitAccount secondAccount = new DebitAccount(getVerifiedClient(), 2000);

        TransferTransaction transaction = new TransferTransaction(firstAccount, secondAccount, new Money(5000));
        TransactionResult result = transaction.execute();

        Assertions.assertInstanceOf(FailureTransactionResult.class, result);
        Assertions.assertEquals(3000, firstAccount.getBalance());
        Assertions.assertEquals(2000, secondAccount.getBalance());
    }

    @Test
    public void rollbackTest() {
        DebitAccount firstAccount = new DebitAccount(getVerifiedClient(), 3000);
        DebitAccount secondAccount = new DebitAccount(getVerifiedClient(), 2000);

        TransferTransaction transaction = new TransferTransaction(firstAccount, secondAccount, new Money(100));
        transaction.execute();
        TransactionResult result = transaction.rollback();

        Assertions.assertInstanceOf(SuccessTransactionResult.class, result);
        Assertions.assertEquals(3000, firstAccount.getBalance());
        Assertions.assertEquals(2000, secondAccount.getBalance());
    }
}
