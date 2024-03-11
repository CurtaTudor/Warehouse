package GUI;

import BLL.ClientBLL;
import DAO.ClientDAO;
import Model.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.NoSuchElementException;
import java.util.Objects;


/**
 * Reprezinta fereastra pentru clienti
 * Are ca atribute 5 butoane, 5 textfield-uri pentru input-ul utilizatorului
 * Un combo box pentru selectia operatiei dorite
 * @author Curta Tudor
 */
public class ClientFrame extends JFrame implements ItemListener {
    private JButton addBtn = new JButton("Add client");
    private JButton updateBtn = new JButton("Update client");
    private JButton deleteBtn = new JButton("Delete client");
    private JButton viewBtn = new JButton("View clients");
    private JButton findBtn = new JButton("Find client");

    private JTextField inputClientID = new JTextField(15);
    private JTextField inputClientName = new JTextField(15);
    private JTextField inputClientAddress = new JTextField(15);
    private JTextField inputClientEmail = new JTextField(15);
    private JTextField inputClientAge = new JTextField(15);
    private JComboBox comboBox;

    /**
     * Constructorul clasei are rolul de a creea panel-urile si de a le conecta
     */
    public ClientFrame() {

        this.addListener(new AddClientListener());
        this.updateListener(new AddUpdateListener());
        this.deleteListener(new AddDeleteListener());
        this.viewListener(new AddViewListener());
        this.findListener(new AddFindListener());

        JPanel titlePanel = new JPanel();
        JLabel title = new JLabel("Client Screen");
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
        idPanel.add(inputClientID);
        inputClientID.setEditable(false);
        idPanel.setLayout(new FlowLayout());

        JPanel namePanel = new JPanel();
        JLabel nameLabel = new JLabel("Name: ");
        namePanel.add(nameLabel);
        namePanel.add(inputClientName);
        namePanel.setLayout(new FlowLayout());

        JPanel addressPanel = new JPanel();
        JLabel addressLabel = new JLabel("Address: ");
        addressPanel.add(addressLabel);
        addressPanel.add(inputClientAddress);
        addressPanel.setLayout(new FlowLayout());

        JPanel emailPanel = new JPanel();
        JLabel emailLabel = new JLabel("Email: ");
        emailPanel.add(emailLabel);
        emailPanel.add(inputClientEmail);
        emailPanel.setLayout(new FlowLayout());

        JPanel agePanel = new JPanel();
        JLabel ageLabel = new JLabel("Age: ");
        agePanel.add(ageLabel);
        agePanel.add(inputClientAge);
        agePanel.setLayout(new FlowLayout());

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
        contentPanel.add(addressPanel);
        contentPanel.add(emailPanel);
        contentPanel.add(agePanel);
        contentPanel.add(btnPanel);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        this.setTitle("Client");
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
                inputClientID.setEditable(false);
                inputClientName.setEditable(true);
                inputClientAddress.setEditable(true);
                inputClientEmail.setEditable(true);
                inputClientAge.setEditable(true);
            } else {
                if(Objects.equals(comboBox.getSelectedItem(), "Delete")) {
                    inputClientID.setEditable(true);
                    inputClientName.setEditable(false);
                    inputClientAddress.setEditable(false);
                    inputClientEmail.setEditable(false);
                    inputClientAge.setEditable(false);
                }else{
                    if(Objects.equals(comboBox.getSelectedItem(), "Update")) {
                        inputClientID.setEditable(true);
                        inputClientName.setEditable(true);
                        inputClientAddress.setEditable(true);
                        inputClientEmail.setEditable(true);
                        inputClientAge.setEditable(true);
                    }else{
                        inputClientID.setEditable(true);
                        inputClientName.setEditable(false);
                        inputClientAddress.setEditable(false);
                        inputClientEmail.setEditable(false);
                        inputClientAge.setEditable(false);
                    }
                }
            }
        }
        inputClientID.setText("");
        inputClientName.setText("");
        inputClientAddress.setText("");
        inputClientEmail.setText("");
        inputClientAge.setText("");
    }

    void addListener(ActionListener aal) { addBtn.addActionListener(aal); }
    void updateListener(ActionListener aul) { updateBtn.addActionListener(aul);}

    void deleteListener(ActionListener adl) { deleteBtn.addActionListener(adl);}

    void viewListener(ActionListener avl) { viewBtn.addActionListener(avl);}
    void findListener(ActionListener afl) { findBtn.addActionListener(afl);}

    void showError(String errorMessage) { JOptionPane.showMessageDialog(this,errorMessage); }
    void operationSuccess(String message) { JOptionPane.showMessageDialog(this,message); }

    /**
     * Adauga un nou client in baza de data dupa apasarea butonului Add
     * Se intampla doar daca in combo box este selectat "Add"
     */
    class AddClientListener implements ActionListener{
        public void actionPerformed(ActionEvent e)
        {
            if(Objects.equals(comboBox.getSelectedItem(), "Add")) {
                try {
                    String name = inputClientName.getText();
                    String address = inputClientAddress.getText();
                    String email = inputClientEmail.getText();
                    String age = inputClientAge.getText();
                    int ageConv = Integer.parseInt(age);
                    Client client = new Client(0, name, address, email, ageConv);
                    ClientBLL current=new ClientBLL();
                    current.insert(client);
                    operationSuccess("Client inserted!");
                } catch (NullPointerException | NumberFormatException ex)
                {
                    showError(ex.getMessage());
                }
            }
        }
    }

    /**
     * Actualizeaza datele unui client din baza de date dupa apasarea butonului Update
     * Se intampla doar daca in combo box este selectat "Update"
     */
    class AddUpdateListener implements ActionListener{
        public void actionPerformed(ActionEvent e)
        {
            if(Objects.equals(comboBox.getSelectedItem(), "Update")) {
                try {
                    String name = inputClientName.getText();
                    String address = inputClientAddress.getText();
                    String email = inputClientEmail.getText();
                    String age = inputClientAge.getText();
                    String id = inputClientID.getText();
                    int idConv = Integer.parseInt(id);
                    int ageConv = Integer.parseInt(age);
                    Client client = new Client(idConv, name, address, email, ageConv);
                    ClientBLL current = new ClientBLL();
                    current.update(client, idConv);
                    operationSuccess("Client updated!");
                } catch (NullPointerException | NumberFormatException | NoSuchElementException ex)
                {
                    showError(ex.getMessage());
                }
            }
        }
    }

    /**
     * Sterge un client cu un anumit id din baza de date dupa apasarea butonului Delete
     * Se intampla doar daca in combo box este selectat "Delete"
     */
    class AddDeleteListener implements ActionListener{
        public void actionPerformed(ActionEvent e)
        {
            if(Objects.equals(comboBox.getSelectedItem(), "Delete")) {
                try {
                    String id = inputClientID.getText();
                    int idConv = Integer.parseInt(id);
                    ClientBLL current = new ClientBLL();
                    current.delete(idConv);
                    operationSuccess("Client deleted!");
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
            ClientBLL current=new ClientBLL();
            JTable table= null;
            try {
                table = current.createTable(current.viewClients());
                newFrame.setTitle("Client database");
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
                ClientBLL current = new ClientBLL();
                JTable table = null;
                try {
                    String id = inputClientID.getText();
                    int idConv = Integer.parseInt(id);
                    table = current.createTable(current.findClientById(idConv));
                    newFrame.setTitle("Client database");
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

}
