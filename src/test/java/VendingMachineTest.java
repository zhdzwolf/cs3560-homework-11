import ChainFiles.*;
import MachineFiles.*;
import StateFiles.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VendingMachineTest {

    private VendingMachine machine;

    @BeforeEach
    public void setUp() {

        machine = new VendingMachine();

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
    }

    // Snack tests

    @Test
    public void testSnackName() {
        assertEquals("Coke", machine.getSnack("Coke").getName());
    }

    @Test
    public void testSnackPrice() {
        assertEquals(2.00, machine.getSnack("Coke").getPrice());
    }

    @Test
    public void testSnackInitialQuantity() {
        assertEquals(5, machine.getSnack("Coke").getQuantity());
    }

    @Test
    public void testDecreaseQuantity() {
        Snack snack = machine.getSnack("Coke");
        snack.decreaseQuantity();

        assertEquals(4, snack.getQuantity());
    }

    @Test
    public void testQuantityCannotGoBelowZero() {
        Snack snack = new Snack("Coke", 2.00, 0);
        snack.decreaseQuantity();

        assertEquals(0, snack.getQuantity());
    }

    // Initial state tests

    @Test
    public void testInitialStateIsIdle() {
        assertInstanceOf(IdleState.class, machine.getState());
    }

    @Test
    public void testSnackSelection() {
        machine.selectSnack("Coke");

        assertEquals("Coke", machine.getSelectedSnack());
    }

    @Test
    public void testSelectionChangesState() {
        machine.selectSnack("Coke");

        assertInstanceOf(
                WaitingForMoneyState.class, machine.getState());
    }

    @Test
    public void testInvalidSnackSelection() {
        assertThrows(
                IllegalArgumentException.class,
                () -> machine.selectSnack("Unknown"));
    }

    // Money insertion tests

    @Test
    public void testMoneyInsertion() {
        machine.selectSnack("Coke");
        machine.insertMoney(1.00);

        assertEquals(1.00, machine.getInsertedMoney());
    }

    @Test
    public void testMultipleMoneyInsertions() {
        machine.selectSnack("Coke");
        machine.insertMoney(1.00);
        machine.insertMoney(0.50);

        assertEquals(1.50, machine.getInsertedMoney());
    }

    @Test
    public void testInsufficientMoneyKeepsWaitingState() {
        machine.selectSnack("Coke");
        machine.insertMoney(1.00);

        assertInstanceOf(
                WaitingForMoneyState.class, machine.getState());
    }

    @Test
    public void testZeroMoneyIsRejected() {
        machine.selectSnack("Coke");

        assertThrows(
                IllegalArgumentException.class,
                () -> machine.insertMoney(0));
    }

    // Dispensing tests

    @Test
    public void testSuccessfulCokeDispensing() {
        machine.selectSnack("Coke");
        machine.insertMoney(2.00);

        assertEquals("Coke", machine.getLastDispensed());
    }

    @Test
    public void testQuantityDecreasesAfterDispensing() {
        machine.selectSnack("Coke");
        machine.insertMoney(2.00);

        assertEquals(4, machine.getSnack("Coke").getQuantity());
    }

    @Test
    public void testChangeIsReturned() {
        machine.selectSnack("Coke");
        machine.insertMoney(3.00);

        assertEquals(1.00, machine.getChange());
    }

    @Test
    public void testMachineReturnsToIdleAfterDispensing() {
        machine.selectSnack("Coke");
        machine.insertMoney(2.00);

        assertInstanceOf(IdleState.class, machine.getState());
    }

    @Test
    public void testInsertedMoneyResetsAfterTransaction() {
        machine.selectSnack("Coke");
        machine.insertMoney(2.00);

        assertEquals(0.0, machine.getInsertedMoney());
    }

    // Out-of-stock tests

    @Test
    public void testSnickersQuantityReachesZero() {
        machine.selectSnack("Snickers");
        machine.insertMoney(1.50);

        assertEquals(0, machine.getSnack("Snickers").getQuantity());
    }

    @Test
    public void testOutOfStockSnackReturnsMoney() {
        machine.selectSnack("Snickers");
        machine.insertMoney(1.50);

        machine.selectSnack("Snickers");
        machine.insertMoney(2.00);

        assertEquals(2.00, machine.getRefundedMoney());
    }

    @Test
    public void testOutOfStockSnackIsNotDispensed() {
        machine.selectSnack("Snickers");
        machine.insertMoney(1.50);

        machine.setLastDispensed("None");

        machine.selectSnack("Snickers");
        machine.insertMoney(2.00);

        assertEquals("None", machine.getLastDispensed());
    }

    @Test
    public void testMachineReturnsToIdleAfterRefund() {
        machine.selectSnack("Snickers");
        machine.insertMoney(1.50);

        machine.selectSnack("Snickers");
        machine.insertMoney(2.00);

        assertInstanceOf(IdleState.class, machine.getState());
    }

    // Chain of Responsibility tests

    @Test
    public void testFirstHandlerIsCoke() {
        assertInstanceOf(
                CokeHandler.class, machine.getSnackDispenser());
    }

    @Test
    public void testSecondHandlerIsPepsi() {
        assertInstanceOf(
                PepsiHandler.class,
                machine.getSnackDispenser().nextHandler);
    }

    @Test
    public void testLastHandlerIsSnickers() {
        SnackDispenseHandler handler =
                machine.getSnackDispenser();

        while (handler.nextHandler != null) {
            handler = handler.nextHandler;
        }

        assertInstanceOf(SnickersHandler.class, handler);
    }

    @Test
    public void testSnackDispenserCanBeReplaced() {
        SnackDispenseHandler handler = new SnickersHandler();
        machine.setSnackDispenser(handler);

        assertSame(handler, machine.getSnackDispenser());
    }
}
