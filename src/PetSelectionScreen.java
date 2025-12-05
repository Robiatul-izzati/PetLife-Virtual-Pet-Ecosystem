import javax.swing.*;
import java.awt.*;
import java.sql.Connection; 

public class PetSelectionScreen extends JFrame {

    public PetSelectionScreen() {
        setTitle("Pet Selection");
        setSize(420, 550); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setContentPane(new GradientPanel());
        setLayout(new BorderLayout());

        // --- TITLE ---
        JLabel title = new JLabel("Select Pet", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 30));
        title.setForeground(new Color(50, 50, 50)); 
        title.setBorder(BorderFactory.createEmptyBorder(40, 10, 20, 10));
        add(title, BorderLayout.NORTH);

        // --- BUTTONS ---
        JPanel cards = new JPanel(new GridLayout(3, 1, 20, 20)); 
        cards.setOpaque(false);
        cards.setBorder(BorderFactory.createEmptyBorder(10, 50, 50, 50)); 

        // Menggunakan RoundedButton
        RoundedButton catBtn = new RoundedButton("Cat");
        catBtn.setFont(new Font("SansSerif", Font.BOLD, 20));
        RoundedButton chickenBtn = new RoundedButton("Chicken");
        chickenBtn.setFont(new Font("SansSerif", Font.BOLD, 20));
        RoundedButton ferretBtn = new RoundedButton("Ferret");
        ferretBtn.setFont(new Font("SansSerif", Font.BOLD, 20));

        // Action Listeners
        catBtn.addActionListener(e -> choose(new CatPet()));
        chickenBtn.addActionListener(e -> choose(new ChickenPet()));
        ferretBtn.addActionListener(e -> choose(new MusangPet()));

        cards.add(catBtn);
        cards.add(chickenBtn);
        cards.add(ferretBtn);

        add(cards, BorderLayout.CENTER);
        
        setResizable(false);
        setVisible(true);
    }

    private void choose(VirtualPet pet) {
        Connection conn = Database.getConnection();

        if (conn == null) {
            JOptionPane.showMessageDialog(
                this, 
                "Gagal terhubung ke Database. Pastikan server MySQL (XAMPP/WAMP) sudah berjalan.", 
                "Kesalahan Koneksi Database", 
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        
        ActivityDAO actDao = new ActivityDAO(conn);
        new MainWindow(pet, actDao);
        dispose();
    }
}