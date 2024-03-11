package Model;

/**
 * Aceasta clasa reprezinta modelul de date pentru produse
 * @author Curta Tudor
 */

public class Product {
    private int id;
    private String name;
    private int quantity;
    private float price;

    public Product()
    {
        this.id=0;
        this.name=null;
        this.quantity=0;
        this.price=0;
    }

    /**
     * Constructorul cu parametrii al clasei
     * @param id reprezinta id-ul produsului
     * @param name reprezinta numele produsului
     * @param quantity reprezinta cantitatea
     * @param price reprezinta pretul unui singur produs
     */
    public Product(int id,String name,int quantity,float price)
    {
        this.id=id;
        this.name=name;
        this.quantity=quantity;
        this.price=price;
    }

    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public int getQuantity() { return this.quantity; }
    public float getPrice() { return this.price; }

    public void setId(int id){ this.id=id; }
    public void setName(String name) { this.name=name; }
    public void setQuantity(int quantity) { this.quantity=quantity; }
    public void setPrice(float price) { this.price=price; }

}
