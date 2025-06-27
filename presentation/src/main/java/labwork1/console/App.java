package labwork1.console;

import labwork1.Bank;
import labwork1.CentralBank;
import labwork1.RepositoryManager;
import labwork1.accounts.CreditAccount;
import labwork1.accounts.DebitAccount;
import labwork1.client.Client;
import labwork1.dataaccess.repositories.*;
import labwork1.services.AccountService;
import labwork1.services.BankService;
import labwork1.services.ClientService;
import labwork1.valueobjects.Money;
import labwork1.valueobjects.PassportData;
import labwork1.valueobjects.Percent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class App {
    public static void main(String[] args) {
        ClientRepository clientRepository = new ClientRepository();
        AccountRepository accountRepository = new AccountRepository();
        BankRepository bankRepository = new BankRepository();
        IntraAccountTransactionsRepository intraAccountTransactionsRepository = new IntraAccountTransactionsRepository();
        TransferAccountTransactionsRepository transferAccountTransactionsRepository = new TransferAccountTransactionsRepository();

        RepositoryManager repositoryManager = new RepositoryManager(
                clientRepository,
                accountRepository,
                bankRepository,
                intraAccountTransactionsRepository,
                transferAccountTransactionsRepository
        );

        BankService bankService = new BankService(repositoryManager);
        ClientService clientService = new ClientService(repositoryManager);
        AccountService accountService = new AccountService(repositoryManager);

        CreateBankScenario createBankScenario = new CreateBankScenario();
        SelectBankScenario selectBankScenario = new SelectBankScenario();
        ExternalTransferTransactionScenario externalTransferTransactionScenario = new ExternalTransferTransactionScenario();
        HashMap<State, List<Scenario>> stateScenarioMapping = new HashMap<>();
        stateScenarioMapping.put(State.CENTRALBANK, new ArrayList<>());
        stateScenarioMapping.get(State.CENTRALBANK).add(createBankScenario);
        stateScenarioMapping.get(State.CENTRALBANK).add(selectBankScenario);
        stateScenarioMapping.get(State.CENTRALBANK).add(externalTransferTransactionScenario);

        CreateClientScenario createClientsScenario = new CreateClientScenario();
        SelectClientScenario selectClientScenario = new SelectClientScenario();
        ReturnToCentralBankScenario returnToCentralBankScenario = new ReturnToCentralBankScenario();
        stateScenarioMapping.put(State.BANKSELECTED, new ArrayList<>());
        stateScenarioMapping.get(State.BANKSELECTED).add(createClientsScenario);
        stateScenarioMapping.get(State.BANKSELECTED).add(selectClientScenario);
        stateScenarioMapping.get(State.BANKSELECTED).add(returnToCentralBankScenario);

        CreateAccountScenario createAccountScenario = new CreateAccountScenario();
        SelectAccountScenario selectAccountScenario = new SelectAccountScenario();
        ReturnToBankScenario returnToBankScenario = new ReturnToBankScenario();
        stateScenarioMapping.put(State.CLIENTSELECTED, new ArrayList<>());
        stateScenarioMapping.get(State.CLIENTSELECTED).add(createAccountScenario);
        stateScenarioMapping.get(State.CLIENTSELECTED).add(selectAccountScenario);
        stateScenarioMapping.get(State.CLIENTSELECTED).add(returnToBankScenario);

        GetBalanceScenario getBalanceScenario = new GetBalanceScenario();
        ReplenishScenario replenishScenario = new ReplenishScenario();
        WithdrawScenario withdrawScenario = new WithdrawScenario();
        InternalTransferScenario internalTransferScenario = new InternalTransferScenario();
        CalculateBalanceScenario calculateBalanceScenario = new CalculateBalanceScenario();
        ReturnToClientScenario returnToClientScenario = new ReturnToClientScenario();
        stateScenarioMapping.put(State.ACCOUNTSELECTED, new ArrayList<>());
        stateScenarioMapping.get(State.ACCOUNTSELECTED).add(getBalanceScenario);
        stateScenarioMapping.get(State.ACCOUNTSELECTED).add(replenishScenario);
        stateScenarioMapping.get(State.ACCOUNTSELECTED).add(withdrawScenario);
        stateScenarioMapping.get(State.ACCOUNTSELECTED).add(internalTransferScenario);
        stateScenarioMapping.get(State.ACCOUNTSELECTED).add(calculateBalanceScenario);
        stateScenarioMapping.get(State.ACCOUNTSELECTED).add(returnToClientScenario);

        HashMap<Scenario, State> nextScenario = new HashMap<>();
        nextScenario.put(selectBankScenario, State.BANKSELECTED);
        nextScenario.put(selectClientScenario, State.CLIENTSELECTED);
        nextScenario.put(selectAccountScenario, State.ACCOUNTSELECTED);

        CentralBank centralBank = new CentralBank(repositoryManager);
        Context context = new Context(
                centralBank,
                repositoryManager,
                bankService,
                clientService,
                accountService,
                stateScenarioMapping,
                nextScenario
                );

        addTestData(context);

        ScenarioRunner scenarioRunner = new ScenarioRunner(context);
        while (true) {
            scenarioRunner.Run();
        }
    }

    private static void addTestData(Context context) {
        Bank bank = Bank.builder()
                .withName("Java")
                .withFixedPercent(new Percent(10))
                .addDepositPercents(new Money(10000), new Percent(1.3))
                .withTransactionLimitForDoubtfulClients(new Money(50000))
                .withCommission(new Money(100))
                .build();

        Bank bank2 = Bank.builder()
                .withName("ABC")
                .withFixedPercent(new Percent(5))
                .addDepositPercents(new Money(10000), new Percent(1.3))
                .addDepositPercents(new Money(100000), new Percent(13))
                .withTransactionLimitForDoubtfulClients(new Money(5000))
                .withCommission(new Money(200))
                .build();

        Client client = Client.builder()
                .withBank(bank)
                .withName("Sveta")
                .withSurname("Smirnova")
                .withAddress("abc")
                .withPassportData(new PassportData(1234, 123456))
                .build();

        Client client2 = Client.builder()
                .withBank(bank2)
                .withName("Name")
                .withSurname("Surname")
                .build();

        DebitAccount debitAccount = new DebitAccount(client, 500);
        DebitAccount debitAccount2 = new DebitAccount(client, 8500);
        CreditAccount creditAccount = new CreditAccount(client2, 50000);

        context.repositoryManager.bankRepository.addBank(bank);
        context.repositoryManager.bankRepository.addBank(bank2);
        context.repositoryManager.clientRepository.addClient(client);
        context.repositoryManager.clientRepository.addClient(client2);
        context.repositoryManager.accountRepository.addAccount(debitAccount);
        context.repositoryManager.accountRepository.addAccount(debitAccount2);
        context.repositoryManager.accountRepository.addAccount(creditAccount);
    }
}
