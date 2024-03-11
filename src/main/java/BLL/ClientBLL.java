package BLL;

import DAO.ClientDAO;
import Model.Client;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Reprezinta logica programului pentru date de tip Client
 * @author Curta Tudor
 */
public class ClientBLL {

    private ClientDAO clientDAO;

    public ClientBLL()
    {
        this.clientDAO=new ClientDAO();
    }

    /**
     * Returneaza o lista de clienti cu un anumit id dat ca si parametru,dupa apelarea query-urilor sql
     * @param id id-ul dupa care se face selectia
     * @return lista de clienti cu id-ul cautat
     * @throws NoSuchElementException in cazul in care nu exista client cu id-ul cautat
     */
    public List<Client> findClientById(int id) throws NoSuchElementException {
        List<Client> list = clientDAO.findID(id);
        if (list.isEmpty()) {
            throw new NoSuchElementException("The client with id = " + id + " was not found!");
        }
        return list;
    }

    /**
     * Returneaza o lista de clienti ce se afla in baza de date, dupa apelarea query-urilor sql
     * @return Lista de clienti ce se afla in baza de date
     */
    public List<Client> viewClients()
    {
        return clientDAO.selectAll();
    }

    /**
     * Insereaza in baza de date un client dat ca si parametru
     * @param client clientul ce urmeaza sa fie inserat
     */

    public void insert(Client client) {
        clientDAO.insert(client);
    }

    /**
     * Actualizeaza in baza de date un client dat prin id cu un client dat ca si parametru
     * @param client clientul cu care se actualizeaza
     * @param id id-ul clientului ce urmeaza sa fie actualizat
     */

    public void update(Client client,int id)
    {
        List<Client> list = clientDAO.findID(id);
        if(list.isEmpty()){
            throw new NoSuchElementException("The client with id = " + id + " was not found!");
        }
        clientDAO.update(client,id);
    }

    /**
     * Sterge din baza de date un client cu un anumit id,dat ca si parametru
     * @param id id-ul clientului ce urmeaza sa fie sters.
     */
    public void delete(int id)
    {
        List<Client> list = clientDAO.findID(id);
        if(list.isEmpty()){
            throw new NoSuchElementException("The client with id = " + id + " was not found!");
        }
        clientDAO.delete(id);
    }

    public JTable createTable(List<Client> list) throws IllegalAccessException {
        return clientDAO.createTable(list);
    }


}
