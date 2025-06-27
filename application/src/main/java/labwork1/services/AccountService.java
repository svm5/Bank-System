package labwork1.services;

import labwork1.RepositoryManager;
import labwork1.accounts.*;
import labwork1.client.Client;
import labwork1.services.results.*;
import labwork1.transactions.ReplenishmentTransaction;
import labwork1.transactions.TransferTransaction;
import labwork1.transactions.WithdrawalTransaction;
import labwork1.transactions.transactionresult.FailureTransactionResult;
import labwork1.transactions.transactionresult.TransactionResult;
import labwork1.transactions.transactionresult.SuccessTransactionResult;
import labwork1.valueobjects.Money;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AccountService {
    private final RepositoryManager repositoryManager;

    public AccountService(RepositoryManager repositoryManager)
    {
        this.repositoryManager = repositoryManager;
    }

    public void createDebitAccount(UUID clientId) {
        Client client = repositoryManager.clientRepository.getClientById(clientId);
        DebitAccount debitAccount = new DebitAccount(client, 0);
        repositoryManager.accountRepository.addAccount(debitAccount);
    }

    public void createDepositAccount(UUID clientId, Money amount, Date expirationDate) {
        Client client = repositoryManager.clientRepository.getClientById(clientId);
        DepositAccount depositAccount = new DepositAccount(client, amount, expirationDate);
        repositoryManager.accountRepository.addAccount(depositAccount);
    }

    public void createCreditAccount(UUID clientId, Money amount) {
        Client client = repositoryManager.clientRepository.getClientById(clientId);
        CreditAccount creditAccount = new CreditAccount(client, amount.value());
        repositoryManager.accountRepository.addAccount(creditAccount);
    }

    public double getBalance(UUID accountId) {
        BaseAccount account = repositoryManager.accountRepository.getAccountById(accountId);
        return account.getBalance();
    }

    public AccountOperationResult replenishMoney(UUID accountId, Money amount) {
        BaseAccount account = repositoryManager.accountRepository.getAccountById(accountId);
        Client client = account.client;

        if (client.isClientDoubtful() && isLimitExceededForDoubtfulClients(client, amount)) {
            return new FailureAccountOperationResult(new LimitForDoubtfulClientsExceeded());
        }

        ReplenishmentTransaction replenishmentTransaction = new ReplenishmentTransaction(account, amount);
        TransactionResult transactionResult = replenishmentTransaction.execute();
        if (!(transactionResult instanceof SuccessTransactionResult)) {
            FailureTransactionResult failureTransactionResult = (FailureTransactionResult) transactionResult;
            return new FailureAccountOperationResult(new TransactionFailed(failureTransactionResult));
        }

        repositoryManager.accountRepository.updateAccount(account);
        repositoryManager.oneAccountOperationsRepository.addTransaction(replenishmentTransaction);

        return new SuccessAccountOperationResult();
    }

    public AccountOperationResult withdrawMoney(UUID accountId, Money amount) {
        BaseAccount account = repositoryManager.accountRepository.getAccountById(accountId);
        Client client = account.client;

        if (client.isClientDoubtful() && isLimitExceededForDoubtfulClients(client, amount)) {
            return new FailureAccountOperationResult(new LimitForDoubtfulClientsExceeded());
        }

        WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction(account, amount);
        TransactionResult transactionResult = withdrawalTransaction.execute();
        if (!(transactionResult instanceof SuccessTransactionResult)) {
            FailureTransactionResult failureTransactionResult = (FailureTransactionResult) transactionResult;
            return new FailureAccountOperationResult(new TransactionFailed(failureTransactionResult));
        }

        repositoryManager.accountRepository.updateAccount(account);
        repositoryManager.oneAccountOperationsRepository.addTransaction(withdrawalTransaction);

        return new SuccessAccountOperationResult();
    }

    public AccountOperationResult transferMoney(UUID accountFromId, UUID accountToId, Money amount) {
        BaseAccount accountFrom = repositoryManager.accountRepository.getAccountById(accountFromId);
        Client clientFrom = accountFrom.client;

        BaseAccount accountTo = repositoryManager.accountRepository.getAccountById(accountToId);
        Client clientTo = accountTo.client;

        if (clientFrom.bank.id != clientTo.bank.id) {
            return new FailureAccountOperationResult(new BankTransferTransactionIsNotAllowed());
        }

        if (clientFrom.isClientDoubtful() && isLimitExceededForDoubtfulClients(clientFrom, amount)) {
            return new FailureAccountOperationResult(new LimitForDoubtfulClientsExceeded());
        }

        TransferTransaction transferTransaction = new TransferTransaction(accountFrom, accountTo, amount);
        TransactionResult transactionResult = transferTransaction.execute();
        if (!(transactionResult instanceof SuccessTransactionResult)) {
            FailureTransactionResult failureTransactionResult = (FailureTransactionResult) transactionResult;
            return new FailureAccountOperationResult(new TransactionFailed(failureTransactionResult));
        }

        repositoryManager.accountRepository
                .updateAccounts(Stream.of(accountFrom, accountTo).collect(Collectors.toList()));
        repositoryManager.transferAccountOperationsRepository.addTransaction(transferTransaction);
        return new SuccessAccountOperationResult();
    }

    public double predictBalance(UUID accountId, int daysAmount) {
        if (daysAmount < 1)
            throw new IllegalArgumentException("Days amount must be greater than 0");

        BaseAccount account = repositoryManager.accountRepository.getAccountById(accountId);
        Account accountCopy = account.getCopy();
        while (daysAmount > 30) {
            for (int i = 0; i < 30; i++) {
                accountCopy.chargeInterest();
            }
            accountCopy.payInterest();
            daysAmount -= 30;
        }

        return accountCopy.getBalance();
    }

    private boolean isLimitExceededForDoubtfulClients(Client client, Money amount) {
        return amount.value() > client.bank.getTransactionLimitForDoubtfulClients().value();
    }
}
