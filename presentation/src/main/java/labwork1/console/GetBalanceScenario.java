package labwork1.console;

public class GetBalanceScenario implements Scenario {
    public String getName() {
        return "Get balance";
    }

    public void Run(Context context) {
        System.out.println("Current balance: " + context.accountService.getBalance(context.currentAccount.id));
    }
}
