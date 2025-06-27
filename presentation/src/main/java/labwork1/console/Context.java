package labwork1.console;

import labwork1.Bank;
import labwork1.CentralBank;
import labwork1.RepositoryManager;
import labwork1.accounts.BaseAccount;
import labwork1.client.Client;
import labwork1.services.AccountService;
import labwork1.services.BankService;
import labwork1.services.ClientService;

import java.util.AbstractMap;
import java.util.List;

public class Context {
    public Bank currentBank;
    public Client currentClient;
    public BaseAccount currentAccount;
    public State currentState = State.CENTRALBANK;

    public final RepositoryManager repositoryManager;
    public final CentralBank centralBank;
    public final BankService bankService;
    public final ClientService clientService;
    public final AccountService accountService;
    public final AbstractMap<State, List<Scenario>> stateScenarioMapping;
    public final AbstractMap<Scenario, State> nextScenario;

    public Context(CentralBank centralBank,
                   RepositoryManager repositoryManager,
                   BankService bankService,
                   ClientService clientService,
                   AccountService accountService,
                   AbstractMap<State, List<Scenario>> stateScenarioMapping,
                   AbstractMap<Scenario, State> nextScenario) {
        this.centralBank = centralBank;
        this.bankService = bankService;
        this.repositoryManager = repositoryManager;
        this.clientService = clientService;
        this.accountService = accountService;
        this.stateScenarioMapping = stateScenarioMapping;
        this.nextScenario = nextScenario;
    }
}
