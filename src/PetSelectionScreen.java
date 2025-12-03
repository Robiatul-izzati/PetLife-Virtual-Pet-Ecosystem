import javax.swing.*;
import java.awt.*;

public class PetSelectionScreen extends JFrame {

    public PetSelectionScreen() {
        setTitle("Pilih Hewan Peliharaan");
        setSize(420, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new GradientPanel());
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Pilih Hewan Peliharaan", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        JPanel cards = new JPanel(new GridLayout(3, 1, 12, 12));
        cards.setOpaque(false);
        cards.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        RoundedButton catBtn = new RoundedButton("Kucing");
        RoundedButton chickenBtn = new RoundedButton("Ayam");
        RoundedButton ferretBtn = new RoundedButton("Musang");

        catBtn.addActionListener(e -> choose(new CatPet()));
        chickenBtn.addActionListener(e -> choose(new ChickenPet()));
        ferretBtn.addActionListener(e -> choose(new MusangPet()));

        cards.add(catBtn);
        cards.add(chickenBtn);
        cards.add(ferretBtn);

        add(cards, BorderLayout.CENTER);
        setVisible(true);
    }

    private void choose(VirtualPet pet) {
        new MainWindow(pet, new ActivityDAO(Database.getConnection()));
        dispose();
    }
}