import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow extends JFrame implements PetListener {

    private final VirtualPet pet;
    private final ActivityPanel activityPanel;
    private final ActivityDAO actDao;

    private final JLabel petImage = new JLabel();
    private final JProgressBar hungerBar = new JProgressBar(0, 100);
    private final JProgressBar energyBar = new JProgressBar(0, 100);
    private final JProgressBar moodBar = new JProgressBar(0, 100);

    private Image backgroundImage;

    private PetThread petThread;
    
    private final JDialog logDialog; 

    public MainWindow(VirtualPet p, ActivityDAO actDao) {

        this.pet = p;
        this.actDao = actDao;
        pet.addListener(this);

        setTitle("PetLife - " + pet.getSpecies());
        setSize(900, 560);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
        setContentPane(new GradientPanel());
        setLayout(new BorderLayout(12, 12));

        // Inisialisasi thread dan dialog
        this.petThread = new PetThread(
            pet,
            new PetDAO(Database.getConnection()), 
            actDao
        );
        this.activityPanel = new ActivityPanel(actDao);
        logDialog = new JDialog(this, "Aktivitas Pet", false);
        logDialog.setSize(260, 500);
        logDialog.setLocationRelativeTo(this);
        logDialog.add(new JScrollPane(activityPanel));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitToSelectionScreen();
            }
        });

        // ===== TOP BUTTONS =====
        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        top.setOpaque(false);

        Font buttonFont = new Font("SansSerif", Font.BOLD, 16);
        RoundedButton feed = new RoundedButton("Feed");
        feed.setFont(buttonFont);
        RoundedButton play = new RoundedButton("Play");
        play.setFont(buttonFont);
        RoundedButton sleep = new RoundedButton("Sleep");
        sleep.setFont(buttonFont);

        RoundedButton backBtn = new RoundedButton("← Back");
        backBtn.setFont(buttonFont);
        backBtn.setBaseColor(new Color(200, 60, 90)); 

        RoundedButton logBtn = new RoundedButton("Log");
        logBtn.setFont(buttonFont);
        
        top.add(logBtn);
        top.add(feed);
        top.add(play);
        top.add(sleep);
        top.add(backBtn);
        add(top, BorderLayout.NORTH);

        // ===== CENTER: PET DISPLAY =====
        
        // Muat Gambar Background dari Pet yang aktif
        ImageIcon icon = AnimationLoader.load(pet.getAnimations().getBackgroundPath()); 
        if (icon != null) {
            this.backgroundImage = icon.getImage();
        } else {
            System.err.println("Gagal memuat latar belakang: " + pet.getAnimations().getBackgroundPath());
        }
        
        // Ganti centerPanel dengan JPanel anonim untuk menggambar Background
        JPanel centerPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(
                        backgroundImage, 
                        0, 0, getWidth(), getHeight(), 
                        this
                    );
                }
            }
        };
        centerPanel.setOpaque(false);

        petImage.setHorizontalAlignment(SwingConstants.CENTER);
        petImage.setPreferredSize(new Dimension(420, 420));

        centerPanel.add(petImage, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // ===== BOTTOM: STATUS BARS =====
        JPanel bars = new JPanel(new GridLayout(1, 3, 10, 10));
        bars.setOpaque(false);
        bars.add(labeled("Hunger", hungerBar));
        bars.add(labeled("Energy", energyBar));
        bars.add(labeled("Mood", moodBar));

        add(bars, BorderLayout.SOUTH);

        // ===== ACTION LISTENERS =====
        feed.addActionListener(e -> {
            if (pet.getState() == PetState.SLEEPING) {
                JOptionPane.showMessageDialog(this, pet.getSpecies() + " sedang tidur!", "Tidak Bisa Beraksi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            pet.feed();
            try { actDao.logActivity(pet.getSpecies() + " diberi makan"); } catch (Exception ignore) {}
            activityPanel.reload();
        });

        play.addActionListener(e -> {
            if (pet.getState() == PetState.SLEEPING) {
                JOptionPane.showMessageDialog(this, pet.getSpecies() + " sedang tidur!", "Tidak Bisa Beraksi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            pet.play();
            try { actDao.logActivity(pet.getSpecies() + " diajak bermain"); } catch (Exception ignore) {}
            activityPanel.reload();
        });

        sleep.addActionListener(e -> {
            pet.sleep();
            try { actDao.logActivity(pet.getSpecies() + " tidur"); } catch (Exception ignore) {}
            activityPanel.reload();
        });

        logBtn.addActionListener(e -> {
            activityPanel.reload();
            logDialog.setVisible(true);
        });
        
        backBtn.addActionListener(e -> exitToSelectionScreen());
        
        // ===== STARTUP =====
        
        refreshBars();
        updateAnimation();
        setLocationRelativeTo(null);
        setVisible(true);
        petThread.start();
    }

    // ===== UTILITY PANEL FOR LABEL + PROGRESSBAR =====
    private JPanel labeled(String t, JProgressBar b) {
        // ... (Logika labeled tetap sama)
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        JLabel lbl = new JLabel(t, SwingConstants.CENTER);
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 12f));
        p.add(lbl, BorderLayout.NORTH);
        p.add(b, BorderLayout.CENTER);
        return p;
    }

    private void refreshBars() {
        // ... (Logika refreshBars tetap sama)
        hungerBar.setValue(pet.getHunger());
        energyBar.setValue(pet.getEnergy());
        moodBar.setValue(pet.getMood());
        
        hungerBar.setForeground(pet.getHunger() > 20 ? Color.GREEN.darker() : Color.RED);
        energyBar.setForeground(pet.getEnergy() > 20 ? Color.BLUE.darker() : Color.RED);
        moodBar.setForeground(pet.getMood() > 20 ? new Color(150, 0, 150) : Color.RED);
    }

    private void updateAnimation() {
        String path = pet.getAnimations().getForState(pet.getState());

        ImageIcon icon = AnimationLoader.load(path);
        if (icon != null) {
            petImage.setIcon(icon);
        } else {
            petImage.setIcon(null);
            petImage.setText("Image Error: " + path);
        }
    }
    
    private void exitToSelectionScreen() {
        if (petThread != null) petThread.stopThread();
        dispose();
        new PetSelectionScreen();
    }

    @Override
    public void onPetUpdated(VirtualPet p, int before, int after, String action) {
        SwingUtilities.invokeLater(() -> {
            refreshBars();
            updateAnimation();
            activityPanel.reload();
        });
    }
}
