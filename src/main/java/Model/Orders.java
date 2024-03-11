package Model;

/**
 * Aceasta clasa reprezinta modelul de date pentru comenzi
   @author Curta Tudor
 */

public class Orders {
    private int id;
    private int client;
    private int product;
    private int quantity;
    private float total;

    public Orders()
    {
        this.id=0;
        this.client=0;
        this.product=0;
        this.quantity=0;
        this.total=0;
    }

    /**
     * Constructorul cu parametrii al clasei
     * @param id reprezinta id-ul comenzii
     * @param client reprezinta id-ul clientului
     * @param product reprezinta id-ul produsului
     * @param quantity reprezinta cantitatea
     * @param total reprezinta suma totala de plata
     */
    public Orders(int id, int client, int product, int quantity, float total)
    {
        this.id=id;
        this.client=client;
        this.product=product;
        this.quantity=quantity;
        this.total=total;
    }

    public int getId() { return this.id; }
    public int getClient() { return this.client; }
    public int getProduct() { return this.product; }
    public int getQuantity() { return this.quantity; }
    public float getTotal() { return this.total; }

    public void setId(int id){ this.id=id; }
    public void setClient(int client) { this.client=client; }
    public void setProduct(int product) { this.product=product; }
    public void setQuantity(int quantity) { this.quantity=quantity; }
    public void setTotal(float total){ this.total=total; }

}
