package labwork1.accounts;

import labwork1.accounts.operationresults.OperationResult;
import labwork1.accounts.operationresults.SuccessOperationResult;
import labwork1.client.Client;
import labwork1.valueobjects.Money;

import java.util.UUID;

/**
 * The basic implementation of the methods that accounts should support
 */
public abstract class BaseAccount implements Account {
    public UUID id;
    public final Client client;
    protected double balance;

    protected BaseAccount(UUID id, Client client, double balance) {
        this.id = id;
        this.client = client;
        this.balance = balance;
    }

    BaseAccount(Client client, double balance) {
        this.id = UUID.randomUUID();

        this.client = client;
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public UUID getClientId() {
        return client.id;
    }

    public void setBalance(double balance) { this.balance = balance; }

    public OperationResult replenish(Money amount) {
        balance += amount.value();
        return new SuccessOperationResult();
    }

    public OperationResult withdraw(Money amount) {
        balance -= amount.value();
        return new SuccessOperationResult();
    }
}
