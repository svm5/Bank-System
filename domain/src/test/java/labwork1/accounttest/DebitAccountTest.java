package labwork1.accounttest;

import labwork1.accounts.DebitAccount;
import labwork1.accounts.operationresults.FailureOperationResult;
import labwork1.accounts.operationresults.OperationResult;
import labwork1.accounts.operationresults.SuccessOperationResult;
import labwork1.accounts.operationresults.errors.NotEnoughMoneyError;
import labwork1.valueobjects.Money;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static labwork1.TestsHelper.getVerifiedClient;

public class DebitAccountTest {
    @Test
    public void replenishMoneyTest() {
        DebitAccount debitAccount = new DebitAccount(getVerifiedClient(), 100);
        OperationResult operationResult = debitAccount.replenish(new Money(500));

        double result = debitAccount.getBalance();

        Assertions.assertInstanceOf(SuccessOperationResult.class, operationResult);
        Assertions.assertEquals(600, result);
    }

    @Test
    public void withdrawMoneySuccessfullyTest() {
        DebitAccount debitAccount = new DebitAccount(getVerifiedClient(), 100);
        OperationResult operationResult = debitAccount.withdraw(new Money(30));

        double result = debitAccount.getBalance();

        Assertions.assertInstanceOf(SuccessOperationResult.class, operationResult);
        Assertions.assertEquals(70, result);
    }

    @Test
    public void withdrawMoneyNotEnoughErrorTest() {
        DebitAccount debitAccount = new DebitAccount(getVerifiedClient(), 100);
        OperationResult operationResult = debitAccount.withdraw(new Money(500));

        double result = debitAccount.getBalance();

        Assertions.assertInstanceOf(FailureOperationResult.class, operationResult);
        Assertions.assertEquals(100, result);
        FailureOperationResult failureOperationResult = (FailureOperationResult) operationResult;
        Assertions.assertInstanceOf(NotEnoughMoneyError.class, failureOperationResult.error());
    }

    @Test
    public void payInterestSimpleCaseTest() {
        DebitAccount debitAccount = new DebitAccount(getVerifiedClient(), 1000000);

        for (int i = 0; i < 3; ++i) {
            debitAccount.chargeInterest();
        }
        debitAccount.payInterest();

        double balance = debitAccount.getBalance();

        Assertions.assertEquals(1000300, balance);
    }

    @Test
    public void payInterestComplexCaseTest() {
        DebitAccount debitAccount = new DebitAccount(getVerifiedClient(), 1000000);

        debitAccount.chargeInterest(); // + 100
        debitAccount.replenish(new Money(2000000));
        debitAccount.chargeInterest(); // + 300
        debitAccount.withdraw(new Money(1250000));
        debitAccount.chargeInterest(); // + 175
        debitAccount.payInterest();

        double balance = debitAccount.getBalance();

        Assertions.assertEquals(1750575, balance);
    }
}
