package labwork1.console;

import labwork1.accounts.BaseAccount;
import labwork1.services.results.AccountOperationResult;
import labwork1.services.results.SuccessAccountOperationResult;
import labwork1.valueobjects.Money;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class InternalTransferScenario implements Scenario {

    public String getName() {
        return "Transfer money";
    }

    public void Run(Context context) {
        Collection<BaseAccount> allAccounts = context.repositoryManager.accountRepository.getAccounts();
        List<BaseAccount> accountsFromSameBank = allAccounts
                .stream()
                .filter(account -> account.client.bank == context.currentBank && account.id != context.currentAccount.id)
                .toList();

        int accountNumber = 0;
        for (BaseAccount account : accountsFromSameBank) {
            ++accountNumber;
            System.out.println(accountNumber + ": "
                    + account.getAccountType().name() + " account with balance " + account.getBalance());
        }

        System.out.println("Enter account number: ");
        Scanner scan = new Scanner(System.in);
        int selectedAccountNumber = scan.nextInt();

        System.out.println("Enter transfer amount: ");
        double transferAmount = scan.nextDouble();

        BaseAccount to = accountsFromSameBank.get(selectedAccountNumber - 1);
        AccountOperationResult result = context.accountService.transferMoney(context.currentAccount.id, to.id, new Money(transferAmount));
        if (result instanceof SuccessAccountOperationResult) {
            System.out.println("Transfer successful");
        }
        else {
            System.out.println("Transfer failed");
        }
    }
}
