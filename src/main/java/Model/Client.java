package Model;

/**
 * Aceasta clasa reprezinta modelul de date pentru clienti.
 * @author Curta Tudor
 */

public class Client {
    private int id;
    private String name;
    private String address;
    private String email;
    private int age;

    public Client()
    {
        this.id=0;
        this.name=null;
        this.address=null;
        this.email=null;
        this.age=0;
    }

    /**
     * Constructorul cu parametrii al clasei.
     * @param id reprezinta id-ul clientului
     * @param name reprezinta numele clientului
     * @param address reprezinta adresa clientului
     * @param email reprezeinta email-ul clientului
     * @param age reprezinta varsta clientului
     */
    public Client(int id,String name,String address,String email,int age)
    {
        this.id=id;
        this.name=name;
        this.address=address;
        this.email=email;
        this.age=age;
    }

    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public String getAddress() { return this.address; }
    public String getEmail() { return this.email; }
    public int getAge() { return this.age; }

    public void setId(int id){ this.id=id; }
    public void setName(String name) { this.name=name; }
    public void setAddress(String address) { this.address=address; }
    public void setEmail(String email) { this.email=email; }
    public void setAge(int age){ this.age=age; }

}
