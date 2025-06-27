package labwork1;

import labwork1.accounts.BaseAccount;
import labwork1.client.Client;
import labwork1.createsettings.CreateBankSettings;
import labwork1.transactions.TransferTransaction;
import labwork1.transactions.transactionresult.TransactionResult;
import labwork1.transactions.transactionresult.SuccessTransactionResult;
import labwork1.valueobjects.Money;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CentralBank {
    private final RepositoryManager repositoryManager;

    public CentralBank(RepositoryManager repositoryManager)
    {
        this.repositoryManager = repositoryManager;
    }

    public void registerBank(CreateBankSettings settings) {
        Bank bank = Bank.builder()
                    .withName(settings.name)
                    .withFixedPercent(settings.fixedPercent)
                    .withDepositPercents(settings.depositPercents)
                    .withTransactionLimitForDoubtfulClients(settings.transactionLimitForDoubtfulClients)
                    .withCommission(settings.commission)
                    .build();

        repositoryManager.bankRepository.addBank(bank);
    }

    public boolean interbankTransfer(
            UUID accountFromId,
            UUID accountToId,
            Money amount
    ) {
        BaseAccount accountFrom = repositoryManager.accountRepository.getAccountById(accountFromId);
        Client clientFrom = accountFrom.client;

        BaseAccount accountTo = repositoryManager.accountRepository.getAccountById(accountToId);
        Client clientTo = accountTo.client;

        if (clientFrom.bank.id == clientTo.bank.id)
            return false; // same bank - through account service

        Bank bankFrom = clientFrom.bank;
        // client in 'black' list - proxy
        if (clientFrom.isClientDoubtful() && amount.value() > bankFrom.getTransactionLimitForDoubtfulClients().value()) {
            return false;
        }

        TransferTransaction transferTransaction = new TransferTransaction(accountFrom, accountTo, amount);
        TransactionResult transactionResult = transferTransaction.execute();
        if (!(transactionResult instanceof SuccessTransactionResult)) {
            return false;
        }

        // add transaction to history
        repositoryManager.accountRepository
                .updateAccounts(Stream.of(accountFrom, accountTo).collect(Collectors.toList()));
        repositoryManager.transferAccountOperationsRepository.addTransaction(transferTransaction);
        return true;
    }
}
