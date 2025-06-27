package labwork1.console;

import labwork1.valueobjects.Money;

import java.util.Calendar;
import java.util.Scanner;

public class CreateAccountScenario implements Scenario {

    public String getName() {
        return "Create account";
    }

    public void Run(Context context) {
        System.out.println("1 - debit account");
        System.out.println("2 - deposit account");
        System.out.println("3 - credit account");
        System.out.print("Enter account type: ");
        Scanner in = new Scanner(System.in);
        int type = in.nextInt();
        switch (type) {
            case 1:
                context.accountService.createDebitAccount(context.currentClient.id);
                System.out.println("Debit account was created");
                break;
            case 2:
                System.out.print("Enter deposit amount: ");
                double depositAmount = in.nextDouble();

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, 2027);
                calendar.set(Calendar.MONTH, 6);
                calendar.set(Calendar.DAY_OF_MONTH, 12);

                context.accountService.createDepositAccount(context.currentClient.id, new Money(depositAmount), calendar.getTime());
                System.out.println("Deposit account was created");
                break;
            case 3:
                System.out.print("Enter credit amount: ");
                double creditAmount = in.nextDouble();

                context.accountService.createCreditAccount(context.currentClient.id, new Money(creditAmount));
                System.out.println("Credit account was created");
                break;
            default:
                System.out.println("Invalid account type");
                break;
        }
    }
}
