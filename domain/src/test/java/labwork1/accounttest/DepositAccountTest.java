package labwork1.accounttest;

import labwork1.accounts.DepositAccount;
import labwork1.accounts.operationresults.FailureOperationResult;
import labwork1.accounts.operationresults.OperationResult;
import labwork1.accounts.operationresults.SuccessOperationResult;
import labwork1.accounts.operationresults.errors.OperationIsNotAllowedNowError;
import labwork1.valueobjects.Money;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static labwork1.TestsHelper.getVerifiedClient;

public class DepositAccountTest {
    @Test
    public void replenishMoneySuccessfullyTest() {
        DepositAccount  depositAccount = new DepositAccount(getVerifiedClient(),new Money(100), createDate(2028, 1, 15));
        OperationResult operationResult = depositAccount.replenish(new Money(500));

        double result = depositAccount.getBalance();

        Assertions.assertInstanceOf(SuccessOperationResult.class, operationResult);
        Assertions.assertEquals(600, result);
    }

    @Test
    public void withdrawMoneySuccessfullyTest() {
        DepositAccount  depositAccount = new DepositAccount(getVerifiedClient(),new Money(100), createDate(2023, 1, 15));
        OperationResult operationResult = depositAccount.withdraw(new Money(30));

        double result = depositAccount.getBalance();

        Assertions.assertInstanceOf(SuccessOperationResult.class, operationResult);
        Assertions.assertEquals(70, result);
    }

    @Test
    public void withdrawMoneyOperationIsNotAllowedNowErrorTest() {
        DepositAccount  depositAccount = new DepositAccount(getVerifiedClient(),new Money(100), createDate(2028, 1, 15));
        OperationResult operationResult = depositAccount.withdraw(new Money(30));

        double result = depositAccount.getBalance();

        Assertions.assertInstanceOf(FailureOperationResult.class, operationResult);
        Assertions.assertEquals(100, result);
        FailureOperationResult failureOperationResult = (FailureOperationResult) operationResult;
        Assertions.assertInstanceOf(OperationIsNotAllowedNowError.class, failureOperationResult.error());
    }

    @Test
    public void payInterestTest() {
        DepositAccount depositAccount = new DepositAccount(getVerifiedClient(), new Money(1000000), createDate(2028, 1, 15));

        for (int i = 0; i < 3; ++i) {
            depositAccount.chargeInterest();
        }
        depositAccount.payInterest();

        double balance = depositAccount.getBalance();

        Assertions.assertEquals(1000600, balance);
    }

    private Date createDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        return calendar.getTime();
    }
}
