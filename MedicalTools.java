package pharmacy;

//inheritance - ma kwa nila ang name, price halin sa product + product type sa enum
public class MedicalTools extends Product {
    public MedicalTools(String name, double price) {
        super(name, price, ProductType.MEDICAL_TOOL);
    }

    @Override
    public String getCategory() {
        return "Medical Tools";
    }
}
