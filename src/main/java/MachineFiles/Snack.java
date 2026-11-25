package MachineFiles;

public class Snack {

    private final String name;
    private final double price;
    private int quantity;

    public Snack(String name, double price, int quantity) {
        if (name == null || name.isBlank()
        || price < 0 || quantity < 0) {
            throw new IllegalArgumentException(
                    "Invalid snack parameters");
        }

        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void decreaseQuantity() {
        if (quantity > 0) {
            quantity--;
        }
    }

}
