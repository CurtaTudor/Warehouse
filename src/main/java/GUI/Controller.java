package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Are rolul de a face conexiunea dintre butoane si interfata principala
 * @author Curta Tudor
 */
public class Controller {
    private MainFrame frame;

    public Controller(MainFrame frame)
    {
        this.frame=frame;
        frame.addClientListener(new ClientListener());
        frame.addProductListener(new ProductListener());
        frame.addOrderListener(new OrderListener());
    }

    /**
     * Are rolul de a creea fereastra pentru clienti dupa apasarea butonului
     */
    class ClientListener implements ActionListener {
        public void actionPerformed(ActionEvent e)
        {
            ClientFrame cframe=new ClientFrame();
            cframe.setVisible(true);
        }
    }

    /**
     * Are rolul de a creea fereastra pentru produse dupa apasarea butonului
     */
    class ProductListener implements ActionListener{
        public void actionPerformed(ActionEvent e)
        {
            ProductFrame pframe=new ProductFrame();
            pframe.setVisible(true);
        }
    }

    /**
     * Are rolul de a creea fereastra pentru comenzi dupa apasarea butonului
     */
    class OrderListener implements ActionListener{
        public void actionPerformed(ActionEvent e)
        {
            OrderFrame oframe=new OrderFrame();
            oframe.setVisible(true);
        }
    }
}
