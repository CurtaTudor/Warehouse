package BLL;

import DAO.ProductDAO;
import Model.Product;

import javax.swing.*;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Reprezinta logica programului pentru date de tip Product
 * @author Curta Tudor
 */

public class ProductBLL {
    private ProductDAO productDAO;
    public ProductBLL()
    {
        this.productDAO=new ProductDAO();
    }
    /**
     * Returneaza o lista de produse cu un anumit id dat ca si parametru,dupa apelarea query-urilor sql
     * @param id id-ul dupa care se face selectia
     * @return lista de produse cu id-ul cautat
     */
    public List<Product> findProductById(int id) {
        List<Product> list = productDAO.findID(id);
        if (list.isEmpty()) {
            throw new NoSuchElementException("The product with id = " + id + " was not found!");
        }
        return list;
    }

    /**
     * Returneaza o lista de produse ce se afla in baza de date, dupa apelarea query-urilor sql
     * @return Lista de produse ce se afla in baza de date
     */
    public List<Product> viewProducts()
    {
        return productDAO.selectAll();
    }

    /**
     * Insereaza in baza de date un produs dat ca si parametru
     * @param product produsul ce urmeaza sa fie inserat
     */

    public void insert(Product product) {
        productDAO.insert(product);
    }

    /**
     * Actualizeaza in baza de date un produs dat prin id cu un produs dat ca si parametru
     * @param product produsul cu care se actualizeaza
     * @param id id-ul produsului ce urmeaza sa fie actualizat
     */
    public void update(Product product,int id)
    {
        List<Product> list = productDAO.findID(id);
        if (list.isEmpty()) {
            throw new NoSuchElementException("The product with id = " + id + " was not found!");
        }
        productDAO.update(product,id);
    }

    /**
     * Sterge din baza de date un produs cu un anumit id,dat ca si parametru
     * @param id id-ul produsului ce urmeaza sa fie sters.
     */

    public void delete(int id)
    {
        List<Product> list = productDAO.findID(id);
        if (list.isEmpty()) {
            throw new NoSuchElementException("The product with id = " + id + " was not found!");
        }
        productDAO.delete(id);
    }

    public JTable createTable(List<Product> list) throws IllegalAccessException {
        return productDAO.createTable(list);
    }
}
