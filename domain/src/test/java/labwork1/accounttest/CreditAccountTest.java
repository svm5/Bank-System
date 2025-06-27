package labwork1.accounttest;

import labwork1.accounts.CreditAccount;
import labwork1.accounts.operationresults.OperationResult;
import labwork1.accounts.operationresults.SuccessOperationResult;
import labwork1.valueobjects.Money;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static labwork1.TestsHelper.getVerifiedClient;

public class CreditAccountTest {
    @Test
    public void replenishTest() {
        CreditAccount creditAccount = new CreditAccount(getVerifiedClient(), 10000);
        OperationResult operationResult = creditAccount.replenish(new Money(1000));

        double balance = creditAccount.getBalance();

        Assertions.assertInstanceOf(SuccessOperationResult.class, operationResult);
        Assertions.assertEquals(11000, balance);
    }

    @Test
    public void WithdrawWithPositiveBalanceTest() {
        CreditAccount creditAccount = new CreditAccount(getVerifiedClient(), 10000);
        OperationResult operationResult = creditAccount.withdraw(new Money(1000));

        double balance = creditAccount.getBalance();

        Assertions.assertInstanceOf(SuccessOperationResult.class, operationResult);
        Assertions.assertEquals(9000, balance);
    }

    @Test
    public void WithdrawWithNegativeBalanceTest() {
        CreditAccount creditAccount = new CreditAccount(getVerifiedClient(), -1000);
        OperationResult operationResult = creditAccount.withdraw(new Money(1000));

        double balance = creditAccount.getBalance();

        Assertions.assertInstanceOf(SuccessOperationResult.class, operationResult);
        Assertions.assertEquals(-2100, balance); // with commission value 100
    }
}
