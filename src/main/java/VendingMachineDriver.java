import MachineFiles.*;
import ChainFiles.*;
import StateFiles.*;

public class VendingMachineDriver {

    public static void main(String[] args) {

        VendingMachine machine = new VendingMachine();

        machine.addSnack(new Snack("Coke", 2.00, 5));
        machine.addSnack(new Snack("Pepsi", 2.00, 5));
        machine.addSnack(new Snack("Cheetos", 1.50, 5));
        machine.addSnack(new Snack("Doritos", 1.75, 5));
        machine.addSnack(new Snack("KitKat", 1.25, 5));
        machine.addSnack(new Snack("Snickers", 1.50, 1));

        CokeHandler coke = new CokeHandler();
        PepsiHandler pepsi = new PepsiHandler();
        CheetosHandler cheetos = new CheetosHandler();
        DoritosHandler doritos = new DoritosHandler();
        KitKatHandler kitkat = new KitKatHandler();
        SnickersHandler snickers = new SnickersHandler();

        coke.setNextHandler(pepsi);
        pepsi.setNextHandler(cheetos);
        cheetos.setNextHandler(doritos);
        doritos.setNextHandler(kitkat);
        kitkat.setNextHandler(snickers);

        machine.setSnackDispenser(coke);

        System.out.println("Welcome to the Vending Machine!");

        System.out.println("\nTransaction 1: Coke");
        machine.selectSnack("Coke");
        machine.insertMoney(2.00);

        System.out.println("\nTransaction 2: Pepsi");
        machine.selectSnack("Pepsi");
        machine.insertMoney(3.00);

        System.out.println("\nTransaction 3: Cheetos");
        machine.selectSnack("Cheetos");
        machine.insertMoney(1.00);
        machine.insertMoney(0.50);

        System.out.println("\nTransaction 4: Doritos");
        machine.selectSnack("Doritos");
        machine.insertMoney(2.00);

        System.out.println("\nTransaction 5: KitKat");
        machine.selectSnack("KitKat");
        machine.insertMoney(1.25);

        System.out.println("\nTransaction 6: Snickers");
        machine.selectSnack("Snickers");
        machine.insertMoney(2.00);

        System.out.println("\nTransaction 7: Snickers");
        machine.selectSnack("Snickers");
        machine.insertMoney(2.00);

        System.out.println("\nDemo complete.");

    }

}
