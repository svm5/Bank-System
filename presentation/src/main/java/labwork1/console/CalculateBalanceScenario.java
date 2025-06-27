package labwork1.console;

import java.util.Scanner;

public class CalculateBalanceScenario implements Scenario {
    public String getName() {
        return "Calculate balance in future";
    }

    public void Run(Context context) {
        System.out.print("Enter days amount: ");
        Scanner in = new Scanner(System.in);
        int days = in.nextInt();

        double balance = context.accountService.predictBalance(context.currentAccount.id, days);
        System.out.println("Balance in " + days + " days: " + balance);
    }
}
