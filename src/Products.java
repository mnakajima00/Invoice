import java.util.HashMap;

public class Products {
    //Products
    private HashMap<String, Double> services;
    public int numCounter;
    public Products(){
        services = new HashMap<String, Double>();

        //Setting up products
        services.put("Consultation", 30.0);
        services.put("Special Acupuncture Treatment", 80.0);
        services.put("Medical Machine Therapy", 30.0);
        services.put("Tapping Treatment", 25.0);

        //Initializing the counter
        numCounter = services.size()+1;
    }

    public Double getProductPrice(String product){
        return services.get(product);
    }
}
