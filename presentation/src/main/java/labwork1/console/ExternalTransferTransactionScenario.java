package labwork1.console;

import labwork1.Bank;
import labwork1.accounts.BaseAccount;
import labwork1.client.Client;
import labwork1.valueobjects.Money;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class ExternalTransferTransactionScenario implements Scenario {
    public String getName() {
        return "External Transfer";
    }

    public void Run(Context context) {
        Collection<Bank> banks = context.repositoryManager.bankRepository.getAllBanks();
        Collection<Client> clients = context.repositoryManager.clientRepository.getAllClients();
        Collection<BaseAccount> accounts = context.repositoryManager.accountRepository.getAccounts();

        int bankNumber = 0;
        for (Bank bank : banks) {
            ++bankNumber;
            System.out.println(bankNumber + bank.getName());
        }

        System.out.println("Enter transfer FROM bank number: ");
        Scanner in = new Scanner(System.in);
        int selectedBankFromNumber = in.nextInt();
        Bank bankFrom = banks.stream().toList().get(selectedBankFromNumber - 1);

        List<Client> clientsBankFrom = clients.stream().filter(client -> client.bank.id == bankFrom.id).toList();
        System.out.println("!!! " + clientsBankFrom.size());
        int clientNumber = 0;
        for (Client client : clientsBankFrom) {
            ++clientNumber;
            System.out.println(clientNumber + ": " + client.getName());
        }
        System.out.println("Enter client number: ");
        int selectedClientFromNumber = in.nextInt();
        Client clientFrom = clientsBankFrom.get(selectedClientFromNumber - 1);

        List<BaseAccount> accountsClientFrom = accounts.stream().filter(account -> account.client.id == clientFrom.id).toList();
        int accountNumber = 0;
        for (BaseAccount account : accountsClientFrom) {
            ++accountNumber;
            System.out.println(accountNumber + ": " + account.getAccountType() + " account with balance " + account.getBalance());
        }
        System.out.println("Enter account number: ");
        int selectedAccountFromNumber = in.nextInt();
        BaseAccount accountFrom = accountsClientFrom.get(selectedAccountFromNumber - 1);

        bankNumber = 0;
        for (Bank bank : banks) {
            ++bankNumber;
            System.out.println(bankNumber + bank.getName());
        }
        System.out.println("Enter transfer TO bank number: ");
        int selectedBankToNumber = in.nextInt();
        Bank bankTo = banks.stream().toList().get(selectedBankToNumber - 1);

        if (bankFrom.id == bankTo.id) {
            System.out.println("Banks must be different");
            return;
        }

        List<Client> clientsBankTo = clients.stream().filter(client -> client.bank.id == bankTo.id).toList();
        clientNumber = 0;
        for (Client client : clientsBankTo) {
            ++clientNumber;
            System.out.println(clientNumber + ": " + client.getName());
        }
        System.out.println("Enter client number: ");
        int selectedClientToNumber = in.nextInt();
        Client clientTo = clientsBankTo.get(selectedClientToNumber - 1);

        List<BaseAccount> accountsClientTo = accounts.stream().filter(account -> account.client.id == clientTo.id).toList();
        accountNumber = 0;
        for (BaseAccount account : accountsClientTo) {
            ++accountNumber;
            System.out.println(accountNumber + ": " + account.getAccountType() + " account with balance " + account.getBalance());
        }
        System.out.println("Enter account number: ");
        int selectedAccountToNumber = in.nextInt();
        BaseAccount accountTo = accountsClientTo.get(selectedAccountToNumber - 1);

        System.out.print("Enter amount to be transferred: ");
        double amountToTransfer = in.nextDouble();

        context.centralBank.interbankTransfer(accountFrom.id, accountTo.id, new Money(amountToTransfer));
    }
}
