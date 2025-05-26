package pharmacy;

//inheritance - ma kwa nila ang name, price halin sa product + product type sa enum
public class Toiletries extends Product {
    public Toiletries(String name, double price) {
        super(name, price, ProductType.TOILETRY);
    }

    @Override
    public String getCategory() {
        return "Toiletries";
    }
}
