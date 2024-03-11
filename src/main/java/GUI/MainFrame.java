package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Aceasta clasa reprezinta fereastra principala a aplicatiei.
 * Are ca si atribute 3 butoane, pentru clienti, produse si comenzi.
 * @author Curta Tudor
 */

public class MainFrame extends JFrame {
    private JButton clientBtn=new JButton("Client");
    private JButton productBtn=new JButton("Product");
    private JButton orderBtn=new JButton("Orders");

    /**
     * Constructorul ferestrei are rolul de a instantia atributele ferestrei si de a o face vizibila
     * Fereastra este impartita in 2 panel-uri, unul cu titlul si celalalt cu butoanele
     */
    public MainFrame()
    {
        JPanel titlePanel=new JPanel();
        JLabel title=new JLabel("Main Screen");
        titlePanel.add(title);
        title.setLayout(new FlowLayout());

        JPanel panelBtn=new JPanel();
        panelBtn.add(clientBtn);
        panelBtn.add(productBtn);
        panelBtn.add(orderBtn);
        panelBtn.setLayout(new FlowLayout());

        JPanel rezPanel=new JPanel();
        rezPanel.add(titlePanel);
        rezPanel.add(panelBtn);
        rezPanel.setLayout(new BoxLayout(rezPanel,BoxLayout.Y_AXIS));

        this.setContentPane(rezPanel);
        this.setSize(600,350);
        this.setTitle("Main Frame");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Asigura interactiunea cu butonul de clienti
     * @param sal
     */
    void addClientListener(ActionListener sal) { clientBtn.addActionListener(sal); }

    /**
     * Asigura interactiunea cu butonul de produse
     * @param apl
     */
    void addProductListener(ActionListener apl) { productBtn.addActionListener(apl); }

    /**
     * Asigura interactiunea cu butonul de comenzi
     * @param aol
     */
    void addOrderListener(ActionListener aol) { orderBtn.addActionListener(aol); }
}
