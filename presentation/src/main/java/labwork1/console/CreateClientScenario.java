package labwork1.console;

import labwork1.createsettings.CreateClientSettings;
import labwork1.valueobjects.PassportData;

import java.util.Scanner;

public class CreateClientScenario implements Scenario {
    public String getName() {
        return "Create client";
    }

    public void Run(Context context) {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter client name: ");
        String name = in.nextLine();

        System.out.print("Enter client surname: ");
        String surname = in.nextLine();

        System.out.print("Enter client address: ");
        String address = in.nextLine();
        if (address.isEmpty()) {
            address = null;
        }

        System.out.print("Enter client passport data. Enter passport series");
        String strPassportSeries = in.nextLine();

        System.out.print("Enter passport number: ");
        String strPassportNumber = in.nextLine();

        PassportData passportData = null;
        if (!strPassportSeries.isEmpty() && !strPassportNumber.isEmpty()) {
            int series, number;
            try {
                series = Integer.parseInt(strPassportSeries);
            } catch (NumberFormatException e) {
                System.out.println("Invalid passport series");
                return;
            }
            try {
                number = Integer.parseInt(strPassportNumber);
            } catch (NumberFormatException e) {
                System.out.println("Invalid passport number");
                return;
            }
            passportData = new PassportData(series, number);
        }

        System.out.print("Here " + name + " " + surname);
        CreateClientSettings createClientSettings = new CreateClientSettings(
                name,
                surname,
                address,
                passportData
        );

        context.clientService.createClient(context.currentBank, createClientSettings);
    }
}
