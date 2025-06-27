package labwork1.dataaccess.repositories;

import labwork1.Bank;

import java.util.*;

public class BankRepository implements labwork1.repositories.BankRepository {
    private AbstractMap<UUID, Bank> banks = new HashMap<UUID, Bank>();

    public void addBank(Bank bank) {
        banks.put(bank.id, bank);
    }

    public Bank getBankById(UUID id) {
        return banks.get(id);
    }

    public void updateBank(Bank bank) {
        banks.put(bank.id, bank);
    }

    public Collection<Bank> getAllBanks() {
        return banks.values();
    }
}
