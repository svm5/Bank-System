package labwork1.console;

public class ReturnToClientScenario implements Scenario {
    public String getName() {
        return "Return to client menu";
    }

    public void Run(Context context) {
        context.currentAccount = null;
        context.currentState = State.CLIENTSELECTED;
    }
}
