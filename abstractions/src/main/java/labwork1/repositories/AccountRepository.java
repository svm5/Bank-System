package labwork1.repositories;

import labwork1.accounts.BaseAccount;

import java.util.Collection;
import java.util.UUID;

public interface AccountRepository {
    void addAccount(BaseAccount account);
    BaseAccount getAccountById(UUID id);
    void updateAccount(BaseAccount account);
    void updateAccounts(Collection<BaseAccount> accounts);
    Collection<BaseAccount> getAccounts();
}
