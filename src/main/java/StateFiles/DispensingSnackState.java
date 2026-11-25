package StateFiles;

import MachineFiles.*;

public class DispensingSnackState implements StateOfVendingMachine {

    @Override
    public void selectSnack(VendingMachine vendingMachine, String snackName) {
        throw new IllegalArgumentException("Please wait, dispensing your snack...");
    }

    @Override
    public void insertMoney(VendingMachine vendingMachine, double amount) {
        throw new IllegalArgumentException("Please wait, dispensing your snack...");
    }

    @Override
    public void dispenseSnack(VendingMachine machine) {
        Snack snack = machine.getSnack(
                machine.getSelectedSnack());

        boolean dispensed = machine.getSnackDispenser()
                .dispense(machine, machine.getSelectedSnack());

        if (dispensed) {
            double change =
                    machine.getInsertedMoney() - snack.getPrice();

            machine.setChange(change);
            machine.setLastDispensed(snack.getName());

            System.out.println("Dispensing: " + snack.getName());
            System.out.println("Change returned: $" + change);
        } else {
            machine.setRefundedMoney(machine.getInsertedMoney());

            System.out.println("Snack unavailable!");
            System.out.println("Refunding: $" + machine.getInsertedMoney());
        }

        machine.setInsertedMoney(0);
        machine.setSelectedSnack(null);
        machine.setState(new IdleState());

        System.out.println("Vending machine is now idle.");
    }

}
