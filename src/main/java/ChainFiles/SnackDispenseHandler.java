package ChainFiles;

import MachineFiles.*;

public abstract class SnackDispenseHandler {

    public SnackDispenseHandler nextHandler;
    private final String snackName;

    public SnackDispenseHandler(String snackName) {
        this.snackName = snackName;
    }

    public void setNextHandler(SnackDispenseHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public boolean dispense(VendingMachine machine, String requestedSnack) {
        if (snackName.equalsIgnoreCase(requestedSnack)) {
            Snack snack = machine.getSnack(snackName);

            if (snack != null && snack.getQuantity() > 0) {
                snack.decreaseQuantity();
                System.out.println(snackName + " dispensed.");
                return true;
            }

            System.out.println(snackName + " is out of stock.");
            return false;
        }

        if (nextHandler != null) {
            return nextHandler.dispense(machine, requestedSnack);
        }

        return false;
    }

}
