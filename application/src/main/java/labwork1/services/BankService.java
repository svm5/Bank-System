package labwork1.services;

import labwork1.Bank;
import labwork1.RepositoryManager;
import labwork1.valueobjects.Money;
import labwork1.valueobjects.Percent;

import java.util.AbstractMap;
import java.util.UUID;

public class BankService {
    private final RepositoryManager repositoryManager;

    public BankService(RepositoryManager repositoryManager) {
        this.repositoryManager = repositoryManager;
    }

    public void updateCommission(UUID bankId, Money commission) {
        Bank bank = repositoryManager.bankRepository.getBankById(bankId);
        bank.setCommission(commission);
        repositoryManager.bankRepository.updateBank(bank);
        bank.eventManager.notify("Bank " + bank.id + " update: new commission: " + commission);
    }

    public void updateFixedPercent(UUID bankId, Percent newPercent) {
        Bank bank = repositoryManager.bankRepository.getBankById(bankId);
        bank.setFixedPercent(newPercent);
        repositoryManager.bankRepository.updateBank(bank);
        bank.eventManager.notify("Bank " + bank.id + " update: new percent: " + newPercent.value());
    }

    public void updateDepositPercents(UUID bankId, AbstractMap<Money, Percent> depositPercents) {
        Bank bank = repositoryManager.bankRepository.getBankById(bankId);
        bank.setDepositPercents(depositPercents);
        repositoryManager.bankRepository.updateBank(bank);
        bank.eventManager.notify("Bank " + bank.id + " update: new deposit percents");
    }

    public void updateTransactionLimit(UUID bankId, Money transactionLimit) {
        Bank bank = repositoryManager.bankRepository.getBankById(bankId);
        bank.setTransactionLimitForDoubtfulClients(transactionLimit);
        repositoryManager.bankRepository.updateBank(bank);
        bank.eventManager.notify("Bank " + bank.id + " update: new transaction limit: " + transactionLimit.value());
    }
}
