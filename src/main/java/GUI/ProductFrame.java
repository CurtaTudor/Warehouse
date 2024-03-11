package GUI;

import BLL.ProductBLL;
import Model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Reprezinta fereastra pentru produse
 * Are ca atribute 5 butoane, 5 textfield-uri pentru input-ul utilizatorului
 * Un combo box pentru selectia operatiei dorite
 * @author Curta Tudor
 */
public class ProductFrame extends JFrame implements ItemListener {

    private JButton addBtn = new JButton("Add product");
    private JButton updateBtn = new JButton("Update product");
    private JButton deleteBtn = new JButton("Delete product");
    private JButton viewBtn = new JButton("View products");
    private JButton findBtn = new JButton("Find products");

    private JTextField inputProductID = new JTextField(15);
    private JTextField inputProductName = new JTextField(15);
    private JTextField inputProductQuantity = new JTextField(15);
    private JTextField inputProductPrice = new JTextField(15);

    private JComboBox comboBox;

    /**
     * Constructorul clasei are rolul de a creea panel-urile si de a le conecta
     */
    public ProductFrame() {

        this.addListener(new AddProductListener());
        this.updateListener(new AddUpdateListener());
        this.viewListener(new AddViewListener());
        this.deleteListener(new AddDeleteListener());
        this.findListener(new AddFindListener());

        JPanel titlePanel = new JPanel();
        JLabel title = new JLabel("Product Screen");
        titlePanel.add(title);
        titlePanel.setLayout(new FlowLayout());

        String[] operations = {"Add", "Update", "Delete","Find"};
        comboBox = new JComboBox(operations);
        comboBox.addItemListener(this);
        JPanel operationsPanel = new JPanel();
        operationsPanel.add(comboBox);

        JPanel idPanel = new JPanel();
        JLabel idLabel = new JLabel("ID: ");
        idPanel.add(idLabel);
        idPanel.add(inputProductID);
        inputProductID.setEditable(false);
        idPanel.setLayout(new FlowLayout());

        JPanel namePanel = new JPanel();
        JLabel nameLabel = new JLabel("Name: ");
        namePanel.add(nameLabel);
        namePanel.add(inputProductName);
        namePanel.setLayout(new FlowLayout());

        JPanel quantityPanel = new JPanel();
        JLabel quantityLabel = new JLabel("Quantity: ");
        quantityPanel.add(quantityLabel);
        quantityPanel.add(inputProductQuantity);
        quantityPanel.setLayout(new FlowLayout());

        JPanel pricePanel = new JPanel();
        JLabel priceLabel = new JLabel("Price: ");
        pricePanel.add(priceLabel);
        pricePanel.add(inputProductPrice);
        pricePanel.setLayout(new FlowLayout());

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
        contentPanel.add(namePanel);
        contentPanel.add(quantityPanel);
        contentPanel.add(pricePanel);
        contentPanel.add(btnPanel);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        this.setTitle("Product");
        this.setContentPane(contentPanel);
        this.setSize(650, 350);
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
                inputProductID.setEditable(false);
                inputProductName.setEditable(true);
                inputProductQuantity.setEditable(true);
                inputProductPrice.setEditable(true);
            } else {
                if (Objects.equals(comboBox.getSelectedItem(), "Delete")) {
                    inputProductID.setEditable(true);
                    inputProductName.setEditable(false);
                    inputProductQuantity.setEditable(false);
                    inputProductPrice.setEditable(false);
                } else {
                    if(Objects.equals(comboBox.getSelectedItem(), "Update")) {
                        inputProductID.setEditable(true);
                        inputProductName.setEditable(true);
                        inputProductQuantity.setEditable(true);
                        inputProductPrice.setEditable(true);
                    }else{
                        inputProductID.setEditable(true);
                        inputProductName.setEditable(false);
                        inputProductQuantity.setEditable(false);
                        inputProductPrice.setEditable(false);
                    }
                }
            }
        }
        inputProductID.setText("");
        inputProductName.setText("");
        inputProductQuantity.setText("");
        inputProductPrice.setText("");
    }

    void addListener(ActionListener aal) { addBtn.addActionListener(aal); }
    void updateListener(ActionListener aul) { updateBtn.addActionListener(aul);}
    void deleteListener(ActionListener adl) { deleteBtn.addActionListener(adl);}
    void viewListener(ActionListener avl) { viewBtn.addActionListener(avl);}
    void findListener(ActionListener afl) { findBtn.addActionListener(afl);}
    void showError(String errorMessage) { JOptionPane.showMessageDialog(this,errorMessage); }
    void operationSuccess(String message) { JOptionPane.showMessageDialog(this,message); }

    /**
     * Adauga un nou produs in baza de data dupa apasarea butonului Add
     * Se intampla doar daca in combo box este selectat "Add"
     */
    class AddProductListener implements ActionListener{
        public void actionPerformed(ActionEvent e)
        {
            if(Objects.equals(comboBox.getSelectedItem(), "Add")) {
                try {
                    String name = inputProductName.getText();
                    String quantity = inputProductQuantity.getText();
                    String price = inputProductPrice.getText();
                    int quantityConv = Integer.parseInt(quantity);
                    float priceConv = Float.parseFloat(price);
                    Product product = new Product(0, name, quantityConv,priceConv);
                    ProductBLL current=new ProductBLL();
                    current.insert(product);
                    operationSuccess("Product inserted!");
                } catch (NullPointerException | NumberFormatException ex)
                {
                    showError(ex.getMessage());
                }
            }
        }
    }

    /**
     * Actualizeaza datele unui produs din baza de date dupa apasarea butonului Update
     * Se intampla doar daca in combo box este selectat "Update"
     */
    class AddUpdateListener implements ActionListener{
        public void actionPerformed(ActionEvent e)
        {
            if(Objects.equals(comboBox.getSelectedItem(), "Update")) {
                try {
                    String name = inputProductName.getText();
                    String quantity = inputProductQuantity.getText();
                    String price = inputProductPrice.getText();
                    String id = inputProductID.getText();
                    int idConv=Integer.parseInt(id);
                    int quantityConv = Integer.parseInt(quantity);
                    float priceConv = Float.parseFloat(price);
                    Product product = new Product(idConv, name, quantityConv,priceConv);
                    ProductBLL current = new ProductBLL();
                    current.update(product, idConv);
                    operationSuccess("Product updated!");
                } catch (NullPointerException | NumberFormatException | NoSuchElementException ex)
                {
                    showError(ex.getMessage());
                }
            }
        }
    }
    /**
     * Sterge un produs cu un anumit id din baza de date dupa apasarea butonului Delete
     * Se intampla doar daca in combo box este selectat "Delete"
     */
    class AddDeleteListener implements ActionListener{
        public void actionPerformed(ActionEvent e)
        {
            if(Objects.equals(comboBox.getSelectedItem(), "Delete")) {
                try {
                    String id = inputProductID.getText();
                    int idConv = Integer.parseInt(id);
                    ProductBLL current = new ProductBLL();
                    current.delete(idConv);
                    operationSuccess("Product deleted!");
                }catch(NullPointerException | NumberFormatException | NoSuchElementException ex)
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
            ProductBLL current=new ProductBLL();
            JTable table= null;
            try {
                table = current.createTable(current.viewProducts());
                newFrame.setTitle("Product database");
                JPanel panel=new JPanel();
                JScrollPane sp=new JScrollPane(table);
                panel.add(sp);
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                newFrame.setContentPane(panel);
                newFrame.setVisible(true);
                newFrame.setSize(500, 350);
                newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * Deschide o noua fereastra in care sunt afisate datele unui produs cu un anumit id
     * Se intampla doar daca in combo box este selectat "Find"
     */
    class AddFindListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if (Objects.equals(comboBox.getSelectedItem(), "Find")) {
                JFrame newFrame = new JFrame();
                ProductBLL current = new ProductBLL();
                JTable table = null;
                try {
                    String id = inputProductID.getText();
                    int idConv = Integer.parseInt(id);
                    table = current.createTable(current.findProductById(idConv));
                    newFrame.setTitle("Product database");
                    JPanel panel = new JPanel();
                    JScrollPane sp = new JScrollPane(table);
                    panel.add(sp);
                    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                    newFrame.setContentPane(panel);
                    newFrame.setVisible(true);
                    newFrame.setSize(600, 350);
                    newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                } catch (NullPointerException | NumberFormatException | IllegalAccessException |
                         NoSuchElementException ex) {
                    showError(ex.getMessage());
                }
            }
        }
    }
}
