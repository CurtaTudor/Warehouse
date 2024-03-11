package GUI;

import BLL.OrderBLL;
import Model.Orders;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Reprezinta fereastra pentru comenzi.
 * Are ca atribute 5 butoane, 5 textfield-uri pentru input-ul utilizatorului.
 * Un combo box pentru selectia operatiei dorite
 * @author Curta Tudor
 */
public class OrderFrame extends JFrame implements ItemListener{
        private JButton addBtn = new JButton("Add order");
        private JButton updateBtn = new JButton("Update order");
        private JButton deleteBtn = new JButton("Delete order");
        private JButton viewBtn = new JButton("View orders");
        private JButton findBtn = new JButton("Find order");

        private JTextField inputOrderID = new JTextField(15);
        private JTextField inputClientID = new JTextField(15);
        private JTextField inputProductID = new JTextField(15);
        private JTextField inputOrderQuantity = new JTextField(15);
        private JComboBox comboBox;

    /**
     * Constructorul clasei are rolul de a creea panel-urile si de a le conecta
     */
    public OrderFrame() {

        this.addListener(new AddOrderListener());
        this.viewListener(new AddViewListener());
        this.findListener(new AddFindListener());
        this.updateListener(new AddUpdateListener());
        this.deleteListener(new AddDeleteListener());

        JPanel titlePanel = new JPanel();
        JLabel title = new JLabel("Order Screen");
        titlePanel.add(title);
        titlePanel.setLayout(new FlowLayout());

        String[] operations = {"Add", "Update", "Delete", "Find"};
        comboBox = new JComboBox(operations);
        comboBox.addItemListener(this);
        JPanel operationsPanel = new JPanel();
        operationsPanel.add(comboBox);

        JPanel idPanel = new JPanel();
        JLabel idLabel = new JLabel("ID: ");
        idPanel.add(idLabel);
        idPanel.add(inputOrderID);
        inputOrderID.setEditable(false);
        idPanel.setLayout(new FlowLayout());

        JPanel clientPanel = new JPanel();
        JLabel clientLabel = new JLabel("Client: ");
        clientPanel.add(clientLabel);
        clientPanel.add(inputClientID);
        clientPanel.setLayout(new FlowLayout());

        JPanel productPanel = new JPanel();
        JLabel productLabel = new JLabel("Product: ");
        productPanel.add(productLabel);
        productPanel.add(inputProductID);
        productPanel.setLayout(new FlowLayout());

        JPanel quantityPanel = new JPanel();
        JLabel quantityLabel = new JLabel("Quantity: ");
        quantityPanel.add(quantityLabel);
        quantityPanel.add(inputOrderQuantity);
        quantityPanel.setLayout(new FlowLayout());

        JPanel btnPanel = new JPanel();
        btnPanel.add(addBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(findBtn);
        btnPanel.add(viewBtn);
        btnPanel.setLayout(new FlowLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.add(titlePanel);
        contentPanel.add(operationsPanel);
        contentPanel.add(idPanel);
        contentPanel.add(clientPanel);
        contentPanel.add(productPanel);
        contentPanel.add(quantityPanel);
        contentPanel.add(btnPanel);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        this.setTitle("Orders");
        this.setContentPane(contentPanel);
        this.setSize(600, 350);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Are rolul de a modifica abilitatea utilizatorului de a edita textfield-urile
     * In functie de ce e selectat in combo box
     * @param e the event to be processed
     */

    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == comboBox) {
            if (Objects.equals(comboBox.getSelectedItem(), "Add")) {
                inputOrderID.setEditable(false);
                inputClientID.setEditable(true);
                inputProductID.setEditable(true);
                inputOrderQuantity.setEditable(true);
            } else {
                if(Objects.equals(comboBox.getSelectedItem(), "Delete")) {
                    inputOrderID.setEditable(true);
                    inputClientID.setEditable(false);
                    inputProductID.setEditable(false);
                    inputOrderQuantity.setEditable(false);
                }else{
                    if(Objects.equals(comboBox.getSelectedItem(), "Update")) {
                        inputOrderID.setEditable(true);
                        inputClientID.setEditable(true);
                        inputProductID.setEditable(true);
                        inputOrderQuantity.setEditable(true);
                    }else{
                        inputOrderID.setEditable(true);
                        inputClientID.setEditable(false);
                        inputProductID.setEditable(false);
                        inputOrderQuantity.setEditable(false);
                    }
                }
            }
        }
        inputOrderID.setText("");
        inputClientID.setText("");
        inputProductID.setText("");
        inputOrderQuantity.setText("");
    }

    void addListener(ActionListener aal) { addBtn.addActionListener(aal); }
    void updateListener(ActionListener aul) { updateBtn.addActionListener(aul);}

    void deleteListener(ActionListener adl) { deleteBtn.addActionListener(adl);}

    void viewListener(ActionListener avl) { viewBtn.addActionListener(avl);}
    void findListener(ActionListener afl) { findBtn.addActionListener(afl);}

    void showError(String errorMessage) { JOptionPane.showMessageDialog(this,errorMessage); }
    void operationSuccess(String message) { JOptionPane.showMessageDialog(this,message); }
    /**
     * Adauga o noua comanda in baza de data dupa apasarea butonului Add
     * Se intampla doar daca in combo box este selectat "Add"
     */
    class AddOrderListener implements ActionListener{
        public void actionPerformed(ActionEvent e)
        {
            if(Objects.equals(comboBox.getSelectedItem(), "Add")) {
                try {
                    String client = inputClientID.getText();
                    String product = inputProductID.getText();
                    String quantity = inputOrderQuantity.getText();
                    int clientConv = Integer.parseInt(client);
                    int productConv = Integer.parseInt(product);
                    int quantityConv = Integer.parseInt(quantity);
                    Orders order = new Orders(0,clientConv,productConv,quantityConv,0);
                    OrderBLL current=new OrderBLL();
                    current.insert(order);
                    operationSuccess("Order inserted!");
                } catch (NullPointerException | NumberFormatException | NoSuchElementException ex)
                {
                    showError(ex.getMessage());
                }
            }
        }
    }
    /**
     * Deschide o noua fereastra in care este afisata baza de date dupa apasarea butonului View
     */
    class AddViewListener implements ActionListener{
        public void actionPerformed(ActionEvent e)
        {
            JFrame newFrame=new JFrame();
            OrderBLL current=new OrderBLL();
            JTable table= null;
            try {
                table = current.createTable(current.viewOrders());
                newFrame.setTitle("Orders database");
                JPanel panel=new JPanel();
                JScrollPane sp=new JScrollPane(table);
                panel.add(sp);
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                newFrame.setContentPane(panel);
                newFrame.setVisible(true);
                newFrame.setSize(600, 350);
                newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    /**
     * Deschide o noua fereastra in care sunt afisate datele unui client cu un anumit id
     * Se intampla doar daca in combo box este selectat "Find"
     */
    class AddFindListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if (Objects.equals(comboBox.getSelectedItem(), "Find")) {
                JFrame newFrame = new JFrame();
                OrderBLL current = new OrderBLL();
                JTable table = null;
                try {
                    String id = inputOrderID.getText();
                    int idConv = Integer.parseInt(id);
                    table = current.createTable(current.findOrderById(idConv));
                    newFrame.setTitle("Orders database");
                    JPanel panel = new JPanel();
                    JScrollPane sp = new JScrollPane(table);
                    panel.add(sp);
                    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                    newFrame.setContentPane(panel);
                    newFrame.setVisible(true);
                    newFrame.setSize(600, 350);
                    newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                } catch (NullPointerException | NumberFormatException | IllegalAccessException | NoSuchElementException ex) {
                    showError(ex.getMessage());
                }
            }
        }
    }

    /**
     * Actualizeaza datele unei comenzi din baza de date dupa apasarea butonului Update
     * Se intampla doar daca in combo box este selectat "Update"
     */
    class AddUpdateListener implements ActionListener{
        public void actionPerformed(ActionEvent e)
        {
            if(Objects.equals(comboBox.getSelectedItem(), "Update")) {
                try {
                    String client = inputClientID.getText();
                    String product = inputProductID.getText();
                    String quantity = inputOrderQuantity.getText();
                    String id = inputOrderID.getText();
                    int idConv = Integer.parseInt(id);
                    int clientConv = Integer.parseInt(client);
                    int productConv = Integer.parseInt(product);
                    int quantityConv = Integer.parseInt(quantity);
                    Orders order = new Orders(idConv, clientConv, productConv, quantityConv, 0);
                    OrderBLL current = new OrderBLL();
                    current.update(order, idConv);
                    operationSuccess("Order updated!");
                } catch (NullPointerException | NumberFormatException | NoSuchElementException ex)
                {
                    showError(ex.getMessage());
                }
            }
        }
    }
    /**
     * Sterge o comanda cu un anumit id din baza de date dupa apasarea butonului Delete
     * Se intampla doar daca in combo box este selectat "Delete"
     */
    class AddDeleteListener implements ActionListener{
        public void actionPerformed(ActionEvent e)
        {
            if(Objects.equals(comboBox.getSelectedItem(), "Delete")) {
                try {
                    String id = inputOrderID.getText();
                    int idConv = Integer.parseInt(id);
                    OrderBLL current = new OrderBLL();
                    current.delete(idConv);
                    operationSuccess("Order deleted!");
                }catch(NullPointerException | NumberFormatException | NoSuchElementException ex)
                {
                    showError(ex.getMessage());
                }
            }
        }
    }

}
