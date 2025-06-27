package labwork1.console;

import labwork1.valueobjects.Money;

import java.util.Scanner;

public class ReplenishScenario implements Scenario {
    public String getName() {
        return "Replenish account";
    }

    public void Run(Context context) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter amount to replenish: ");
        double amount = in.nextDouble();

        context.accountService.replenishMoney(context.currentAccount.id, new Money(amount));

        System.out.println("Account replenished! New balance: " + context.accountService.getBalance(context.currentAccount.id));
    }
}
