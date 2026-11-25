package MachineFiles;

import StateFiles.*;
import ChainFiles.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class VendingMachine {

    private StateOfVendingMachine state;
    private SnackDispenseHandler snackDispenser;

    private final Map<String, Snack> snacks = new LinkedHashMap<>();

    private String selectedSnack;
    private double insertedMoney;
    private double refundedMoney;
    private double change;
    private String lastDispensed;

    public VendingMachine() {
        state = new IdleState();
    }

    public void addSnack(Snack snack) {
        snacks.put(snack.getName(), snack);
    }

    public Snack getSnack(String snackName) {
        return snacks.get(snackName);
    }

    public void setSnackDispenser(
            SnackDispenseHandler snackDispenser) {
        this.snackDispenser = snackDispenser;
    }

    public SnackDispenseHandler getSnackDispenser() {
        return snackDispenser;
    }

    public void selectSnack(String snackName) {
        state.selectSnack(this, snackName);
    }

    public void insertMoney(double amount) {
        state.insertMoney(this, amount);
    }

    public void dispenseSnack() {
        state.dispenseSnack(this);
    }

    public void setState(StateOfVendingMachine state) {
        this.state = state;
    }

    public StateOfVendingMachine getState() {
        return state;
    }

    public void setSelectedSnack(String snackName) {
        selectedSnack = snackName;
    }

    public String getSelectedSnack() {
        return selectedSnack;
    }

    public void setInsertedMoney(double amount) {
        insertedMoney = amount;
    }

    public void addInsertedMoney(double amount) {
        insertedMoney += amount;
    }

    public double getInsertedMoney() {
        return insertedMoney;
    }

    public void setRefundedMoney(double amount) {
        refundedMoney = amount;
    }

    public double getRefundedMoney() {
        return refundedMoney;
    }

    public void setChange(double amount) {
        change = amount;
    }

    public double getChange() {
        return change;
    }

    public void setLastDispensed(String snackName) {
        lastDispensed = snackName;
    }

    public String getLastDispensed() {
        return lastDispensed;
    }

}
