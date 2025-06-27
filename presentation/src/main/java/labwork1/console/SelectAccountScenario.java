package labwork1.console;

import labwork1.accounts.BaseAccount;

import java.util.List;
import java.util.Scanner;

public class SelectAccountScenario implements Scenario {
    public String getName() {
        return "Select account";
    }

    public void Run(Context context) {
        List<BaseAccount> accounts = context.repositoryManager.accountRepository
                .getAccounts().stream().filter(account -> account.client.id == context.currentClient.id).toList();
        int accountNumber = 0;
        for (BaseAccount account : accounts) {
            ++accountNumber;
            System.out.println(accountNumber + ": "
                    + account.getAccountType().name() + " account with balance " + account.getBalance());
        }

        System.out.println("Enter account number: ");
        Scanner scan = new Scanner(System.in);
        int selectedAccountNumber = scan.nextInt();

        context.currentAccount = accounts.stream().toList().get(selectedAccountNumber - 1);
        State nextState = context.nextScenario.get(this);
        if (nextState != null) {
            context.currentState = nextState;
        }
    }
}
