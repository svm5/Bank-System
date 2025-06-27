package labwork1.console;

public class ReturnToCentralBankScenario implements Scenario {

    public String getName() {
        return "Return to central bank";
    }

    public void Run(Context context) {
        context.currentBank = null;
        context.currentState = State.CENTRALBANK;
    }
}
