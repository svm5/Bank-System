package labwork1;

import labwork1.repositories.*;

public class RepositoryManager {
    public final ClientRepository clientRepository;
    public final AccountRepository accountRepository;
    public final BankRepository bankRepository;
    public final IntraAccountTransactionsRepository oneAccountOperationsRepository;
    public final TransferAccountTransactionsRepository transferAccountOperationsRepository;

    public RepositoryManager(
            ClientRepository clientRepository,
            AccountRepository accountRepository,
            BankRepository bankRepository,
            IntraAccountTransactionsRepository oneAccountOperationsRepository,
            TransferAccountTransactionsRepository transferAccountOperationsRepository) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
        this.bankRepository = bankRepository;
        this.oneAccountOperationsRepository = oneAccountOperationsRepository;
        this.transferAccountOperationsRepository = transferAccountOperationsRepository;
    }
}
