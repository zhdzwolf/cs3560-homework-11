package StateFiles;

import MachineFiles.*;

public class WaitingForMoneyState implements StateOfVendingMachine {

    @Override
    public void selectSnack(VendingMachine machine, String snackName) {
        throw new IllegalArgumentException("Complete the current transaction before selecting a new snack.");
    }

    @Override
    public void insertMoney(VendingMachine machine, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Inserted amount must be greater than zero.");
        }

        machine.addInsertedMoney(amount);

        Snack snack = machine.getSnack(
                machine.getSelectedSnack());

        System.out.println("Money inserted: $" + amount);

        if (machine.getInsertedMoney() >= snack.getPrice()) {
            machine.dispenseSnack();
        } else {
            System.out.println("Insufficient funds. Please insert more money.");
            System.out.println("Remaining amount: $" + (snack.getPrice() - machine.getInsertedMoney()));
        }
    }

    @Override
    public void dispenseSnack(VendingMachine machine) {
        Snack snack = machine.getSnack(machine.getSelectedSnack());

        if (machine.getInsertedMoney() < snack.getPrice()) {
            throw new IllegalArgumentException("Insufficient funds. Please insert more money.");
        }

        machine.setState(new DispensingSnackState());
        machine.dispenseSnack();
    }

}
