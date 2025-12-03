import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame implements PetListener {

    private final VirtualPet pet;
    private final ActivityPanel activityPanel;

    private final JLabel petImage = new JLabel();
    private final JProgressBar hungerBar = new JProgressBar(0, 100);
    private final JProgressBar energyBar = new JProgressBar(0, 100);
    private final JProgressBar moodBar = new JProgressBar(0, 100);

    private Timer actionResetTimer;
    private PetThread petThread;

    public MainWindow(VirtualPet p, ActivityDAO actDao) {

        this.pet = p;
        pet.addListener(this);

        setTitle("PetLife - " + pet.getSpecies());
        setSize(900, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new GradientPanel());
        setLayout(new BorderLayout(12, 12));

        // ===== TOP BUTTONS =====
        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        top.setOpaque(false);

        RoundedButton feed = new RoundedButton("Feed");
        RoundedButton play = new RoundedButton("Play");
        RoundedButton sleep = new RoundedButton("Sleep");

        RoundedButton backBtn = new RoundedButton("← Kembali");
        backBtn.setBackground(new Color(200, 60, 90));

        RoundedButton logBtn = new RoundedButton("Log");
        
        top.add(logBtn);
        top.add(feed);
        top.add(play);
        top.add(sleep);
        top.add(backBtn);

        add(top, BorderLayout.NORTH);

        // ===== CENTER: PET DISPLAY =====
        JPanel centerPanel = new JPanel(new BorderLayout());
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

        activityPanel = new ActivityPanel(actDao);

        // JDialog logDialog = new JDialog(this, "Aktivitas Pet", false);
        // logDialog.setSize(260, 500);
        // logDialog.setLocationRelativeTo(this);
        // logDialog.add(new JScrollPane(activityPanel));

        // ===== BUTTON ACTIONS =====
        feed.addActionListener(e -> {
            pet.feed();
            try { actDao.logActivity("Pet diberi makan"); } catch (Exception ignore) {}
            scheduleReset();
            activityPanel.reload();
        });

        play.addActionListener(e -> {
            pet.play();
            try { actDao.logActivity("Pet diajak bermain"); } catch (Exception ignore) {}
            scheduleReset();
            activityPanel.reload();
        });

        sleep.addActionListener(e -> {
            pet.sleep();
            try { actDao.logActivity("Pet tidur"); } catch (Exception ignore) {}
            scheduleReset();
            activityPanel.reload();
        });

        logBtn.addActionListener(e -> {
            activityPanel.reload(); // refresh isi log
            // logDialog.setVisible(true);
        });

        // ===== BACK BUTTON =====
        backBtn.addActionListener(e -> {
            dispose();
            new PetSelectionScreen();
        });

        // ===== ANIMATION LOOP =====
        new Timer(120, e -> updateAnimation()).start();

        refreshBars();
        setLocationRelativeTo(null);
        setVisible(true);

        petThread = new PetThread(
            pet,
            new PetDAO(Database.getConnection()),
            actDao
        );
        petThread.start();

        backBtn.addActionListener(e -> {
            petThread.stopThread();
            dispose();
            new PetSelectionScreen();
        });
    }

    // ===== UTILITY PANEL FOR LABEL + PROGRESSBAR =====
    private JPanel labeled(String t, JProgressBar b) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        JLabel lbl = new JLabel(t, SwingConstants.CENTER);
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 12f));
        p.add(lbl, BorderLayout.NORTH);
        p.add(b, BorderLayout.CENTER);
        return p;
    }

    private void refreshBars() {
        hungerBar.setValue(pet.getHunger());
        energyBar.setValue(pet.getEnergy());
        moodBar.setValue(pet.getMood());
    }

    private void updateAnimation() {
        String path = pet.getAnimations().getForState(pet.getState());

        // load GIF
        ImageIcon icon = AnimationLoader.load(path);
        petImage.setIcon(icon);
    }

    private void scheduleReset() {
        if (actionResetTimer != null && actionResetTimer.isRunning())
            actionResetTimer.stop();

        actionResetTimer = new Timer(2000, e -> pet.resetState());
        actionResetTimer.setRepeats(false);
        actionResetTimer.start();
    }

    @Override
    public void onPetUpdated(VirtualPet p, int before, int after, String action) {
        SwingUtilities.invokeLater(() -> {
            refreshBars();
            activityPanel.reload();
        });
    }
}