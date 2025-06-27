package labwork1.console;

import java.util.List;
import java.util.Scanner;

public class ScenarioRunner {
    private final Context context;

    public ScenarioRunner(Context context) {
        this.context = context;
    }

    public void Run() {
        Scanner in = new Scanner(System.in);

        List<Scenario> scenarioList = context.stateScenarioMapping.get(context.currentState);
        int scenarioNumber = 0;
        for (Scenario scenario : scenarioList) {
            ++scenarioNumber;
            System.out.println(scenarioNumber + " " + scenario.getName());
        }
        System.out.print("Enter command number: ");
        int variant = in.nextInt();
        scenarioList.get(variant - 1).Run(context);
    }
}
