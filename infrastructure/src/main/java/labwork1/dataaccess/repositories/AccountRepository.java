package labwork1.dataaccess.repositories;

import labwork1.accounts.BaseAccount;

import java.util.*;

public class AccountRepository implements labwork1.repositories.AccountRepository {
    private AbstractMap<UUID, BaseAccount> accounts = new HashMap<UUID, BaseAccount>();

    public void addAccount(BaseAccount account) {
        accounts.put(account.id, account);
    }

    public BaseAccount getAccountById(UUID id) {
        return accounts.get(id);
    }

    public void updateAccount(BaseAccount account) {
        accounts.put(account.id, account);
    }

    public void updateAccounts(Collection<BaseAccount> accounts) {
        accounts.forEach(this::updateAccount);
    }

    public Collection<BaseAccount> getAccounts() {
        return accounts.values();
    }
}
