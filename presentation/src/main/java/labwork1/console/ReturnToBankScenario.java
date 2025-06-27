package labwork1.console;

public class ReturnToBankScenario implements Scenario {
    public String getName() {
        return "Return to bank menu";
    }

    public void Run(Context context) {
        context.currentClient = null;
        context.currentState = State.BANKSELECTED;
    }
}
