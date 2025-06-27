package labwork1.console;

import labwork1.client.Client;

import java.util.List;
import java.util.Scanner;

public class SelectClientScenario implements Scenario {

    public String getName() {
        return "Select client";
    }

    public void Run(Context context) {
        List<Client> clients = context.repositoryManager.clientRepository
                .getAllClients().stream().filter(client -> client.bank.id == context.currentBank.id).toList();
        int clientNumber = 0;
        for (Client client : clients) {
            ++clientNumber;
            System.out.println(clientNumber + ": " + client.getName());
        }

        System.out.println("Enter client number: ");
        Scanner scan = new Scanner(System.in);
        int selectedClientNumber = scan.nextInt();

        context.currentClient = clients.stream().toList().get(selectedClientNumber - 1);
        State nextState = context.nextScenario.get(this);
        if (nextState != null) {
            context.currentState = nextState;
        }
    }
}
