package StateFiles;

import MachineFiles.*;

public class IdleState implements StateOfVendingMachine {

    @Override
    public void selectSnack(VendingMachine machine, String snackName) {
        if (machine.getSnack(snackName) == null) {
            throw new IllegalArgumentException("Snack not found");
        }

        machine.setSelectedSnack(snackName);
        machine.setInsertedMoney(0);
        machine.setState(new WaitingForMoneyState());

        System.out.println("Snack selected: " + snackName);
        System.out.println("Please insert money.");
    }

    @Override
    public void insertMoney(VendingMachine machine, double amount) {
        throw new IllegalStateException("Please select a snack first.");
    }

    @Override
    public void dispenseSnack(VendingMachine machine) {
        throw new IllegalStateException("Please select a snack and insert money first.");
    }

}
