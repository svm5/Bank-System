package labwork1.console;

import labwork1.valueobjects.Money;

import java.util.Scanner;

public class WithdrawScenario implements Scenario {
    public String getName() {
        return "Withdraw money";
    }

    public void Run(Context context) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter amount to withdraw: ");
        double amount = in.nextDouble();

        context.accountService.withdrawMoney(context.currentAccount.id, new Money(amount));

        System.out.println("New balance: " + context.accountService.getBalance(context.currentAccount.id));
    }
}
