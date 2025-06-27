package labwork1.console;

import labwork1.Bank;

import java.util.Collection;
import java.util.Scanner;

public class SelectBankScenario implements Scenario {
    public String getName() {
        return "Select Bank";
    }

    public void Run(Context context) {
        Collection<Bank> banks = context.repositoryManager.bankRepository.getAllBanks();
        int bankNumber = 0;
        for (Bank bank : banks) {
            ++bankNumber;
            System.out.println(bankNumber + bank.getName());
        }

        System.out.println("Enter bank number: ");
        Scanner scan = new Scanner(System.in);
        int selectedBankNumber = scan.nextInt();

        context.currentBank = banks.stream().toList().get(selectedBankNumber - 1);
        State nextState = context.nextScenario.get(this);
        if (nextState != null) {
            context.currentState = nextState;
        }
    }
}
