import javax.swing.*;
import java.awt.*;

public class DN12 {
    public static void main(String[] args) {
        JFrame okno = new JFrame("PIN koda");
        okno.setLocation(400,400);

        okno.setLayout(new GridLayout(5, 5));
        for (int i = 0; i < 23; i++) {
            // ustvarim gumb in ga po vrsti dodam v mrežo
            JButton gumb = new JButton("Gumb " + i);
            okno.add(gumb);

            // v sredni vrstici v skrajno levem in skrajno desnem polju ne
            // dodam gumba ampak "prazen prostor" (neviden JPanel)
            if (i==9 || i==12)
                okno.add(new JPanel());
        }

        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        okno.pack();
        okno.setVisible(true);
    }
}
