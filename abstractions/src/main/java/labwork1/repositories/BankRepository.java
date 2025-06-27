package labwork1.repositories;

import labwork1.Bank;

import java.util.Collection;
import java.util.UUID;

public interface BankRepository {
    void addBank(Bank bank);
    Bank getBankById(UUID id);
    void updateBank(Bank bank);
    Collection<Bank> getAllBanks();
}
