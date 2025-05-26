package pharmacy;

// interface - gna define sng product class ang mga method nga halin sa sellable
public abstract class Product implements Sellable {
    private String name;
    private double price;
    private ProductType type;

    public Product(String name, double price, ProductType type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public ProductType getType() {
        return type;
    }

    public abstract String getCategory();
}
