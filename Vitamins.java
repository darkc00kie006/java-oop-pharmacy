package pharmacy;

//inheritance - ma kwa nila ang name, price halin sa product + product type sa enum
public class Vitamins extends Product {
    public Vitamins(String name, double price) {
        super(name, price, ProductType.VITAMINS);
    }

    @Override
    public String getCategory() {
        return "Vitamins";
    }
}
