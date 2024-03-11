package BLL;

import DAO.ClientDAO;
import DAO.OrderDAO;
import DAO.ProductDAO;
import Model.Client;
import Model.Orders;
import Model.Product;

import javax.swing.*;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Reprezinta logica programului pentru date de tip Orders
 * @author Curta Tudor
 */
public class OrderBLL {
    private OrderDAO orderDAO;
    private ClientDAO clientDAO;
    private ProductDAO productDAO;
    public OrderBLL() {
        this.orderDAO=new OrderDAO();
        this.clientDAO=new ClientDAO();
        this.productDAO=new ProductDAO();
    }

    /**
     * Returneaza o lista de comenzi cu un anumit id dat ca si parametru,dupa apelarea query-urilor sql
     * @param id id-ul dupa care se face selectia
     * @return lista de comenzi cu id-ul cautat
     * @throws NoSuchElementException in cazul in care nu exista comenzi cu id-ul cautat
     */

    public List<Orders> findOrderById(int id) throws NoSuchElementException {
        List<Orders> list = orderDAO.findID(id);
        if (list.isEmpty()) {
            throw new NoSuchElementException("The order with id = " + id + " was not found!");
        }
        return list;
    }

    /**
     * Returneaza o lista de comenzi ce se afla in baza de date, dupa apelarea query-urilor sql
     * @return Lista de comenzi ce se afla in baza de date
     */
    public List<Orders> viewOrders()
    {
        return orderDAO.selectAll();
    }

    /**
     * Insereaza in baza de date o comanda data ca si parametru
     * @param order comanda ce urmeaza sa fie inserata
     */

    public void insert(Orders order) throws NoSuchElementException{
        int clientID=order.getClient();
        int productID=order.getProduct();

        List<Client> listClients = clientDAO.findID(clientID);
        if(listClients.isEmpty()){
            throw new NoSuchElementException("The client with id = " + clientID + " was not found!");
        }

        List<Product> listProducts = productDAO.findID(productID);
        if(listProducts.isEmpty()){
            throw new NoSuchElementException("The product with id = " + productID + " was not found!");
        }

        int currentQuantity=listProducts.get(0).getQuantity();
        if(currentQuantity>=order.getQuantity()){
            float pricePerItem = listProducts.get(0).getPrice();
            float totalPrice=pricePerItem*order.getQuantity();
            order.setTotal(totalPrice);

            Product productUpdated=new Product(listProducts.get(0).getId(),listProducts.get(0).getName(),currentQuantity-order.getQuantity(),listProducts.get(0).getPrice());
            productDAO.update(productUpdated,productID);
            orderDAO.insert(order);
        }else{
            throw new NoSuchElementException("There is not enough stock of product with id = " + productID);
        }
    }

    /**
     * Actualizeaza in baza de date o comanda data prin id cu o comanda data ca si parametru
     * @param order comanda cu care se actualizeaza
     * @param id id-ul comenzii ce urmeaza sa fie actualizata
     */
    public void update(Orders order, int id)
    {
        List<Orders> list = orderDAO.findID(id);
        if(list.isEmpty()){
            throw new NoSuchElementException("The order with id = " + id + " was not found!");
        }
        int clientID = order.getClient();
        int productID = order.getProduct();
        int quantity = order.getQuantity();

        List<Client> listClients = clientDAO.findID(clientID);
        if(listClients.isEmpty()){
            throw new NoSuchElementException("The client with id = " + clientID + " was not found!");
        }

        List<Product> listProducts = productDAO.findID(productID);
        if(listProducts.isEmpty()){
            throw new NoSuchElementException("The product with id = " + productID + " was not found!");
        }

        float total = listProducts.get(0).getPrice() * quantity;
        order.setTotal(total);
        orderDAO.update(order,id);
    }

    /**
     * Sterge din baza de date o comanda cu un anumit id,dat ca si parametru
     * @param id id-ul comenzii ce urmeaza sa fie stersa.
     */
    public void delete(int id)
    {
        List<Orders> list = orderDAO.findID(id);
        if(list.isEmpty()){
            throw new NoSuchElementException("The order with id = " + id + " was not found!");
        }
        orderDAO.delete(id);
    }

    public JTable createTable(List<Orders> list) throws IllegalAccessException {
        return orderDAO.createTable(list);
    }

}
