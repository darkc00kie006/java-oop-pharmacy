package pharmacy;

//inheritance - ma kwa nila ang name, price halin sa product + product type sa enum
public class PrescriptionDrug extends Product {
    public PrescriptionDrug(String name, double price) {
        super(name, price, ProductType.PRESCRIPTION_DRUG);
    }

    @Override
    public String getCategory() {
        return "Prescription Drug";
    }
}
