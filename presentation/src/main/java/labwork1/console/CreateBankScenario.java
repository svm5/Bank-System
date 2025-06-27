package labwork1.console;

import java.util.HashMap;
import java.util.Scanner;
import labwork1.createsettings.CreateBankSettings;
import labwork1.valueobjects.Money;
import labwork1.valueobjects.Percent;

public class CreateBankScenario implements Scenario {
    public String getName() {
        return "Create bank";
    }

    public void Run(Context context) {
        // name, fixed percent, deposit percents, transaction limit, commission
        Scanner in = new Scanner(System.in);

        // ask bank name
        System.out.print("Enter bank name: ");
        String bankName = in.nextLine();

        // ask bank percent for debit account
        System.out.print("Enter debit account percent: ");
        double percent = in.nextDouble();

        // ask deposit percents
        HashMap<Money, Percent> depositPercents = new HashMap<>();
        boolean flagRun = true;
        while (flagRun) {
            System.out.print("Enter account percent(press '-1' for end):");
            System.out.print("Enter limit: ");
            double limit = in.nextDouble();
            if (limit == -1) {
                flagRun = false;
                break;
            }
            System.out.print("Enter percent for this limit: ");
            double currentPercent = in.nextDouble();
            depositPercents.put(new Money(limit), new Percent(currentPercent));
        }

        System.out.print("Enter transaction limit for doubtful clients: ");
        double limitForDoubtfulClients = in.nextDouble();

        System.out.print("Enter commission for credit accounts: ");
        double commission = in.nextDouble();

        CreateBankSettings settings = CreateBankSettings
                .builder()
                .withName(bankName)
                .withFixedPercent(new Percent(percent))
                .withDepositPercents(depositPercents)
                .withTransactionLimit(new Money(limitForDoubtfulClients))
                .withCommission(new Money(commission))
                .build();

        context.centralBank.registerBank(settings);
        State nextState = context.nextScenario.get(this);
        if (nextState != null) {
            context.currentState = nextState;
        }
    }
}
