import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class MainWindow extends JFrame implements PetListener {

    private final DinoPet pet;
    private final PetDAO petDao;
    private final PetThread petThread;
    
    // UI Components
    private JLabel petImageLabel;
    private JLabel hungerLabel;
    private JLabel energyLabel;
    private JLabel moodLabel;
    
    public MainWindow(DinoPet pet, PetDAO petDao, PetThread petThread) {
        this.pet = pet;
        this.petDao = petDao;
        this.petThread = petThread;
        
        this.pet.addListener(this); // Daftarkan sebagai listener
        
        // Setup Jframe
        setTitle("Virtual Pet App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        initComponents();
        refreshUI();
    }
    
    private void initComponents() {
        // --- 1. Panel Status (bars) ---
        JPanel statusPanel = new JPanel(new FlowLayout());
        hungerLabel = new JLabel("Hunger: 50%");
        energyLabel = new JLabel("Energy: 50%");
        moodLabel = new JLabel("Mood: 50%");
        statusPanel.add(hungerLabel);
        statusPanel.add(energyLabel);
        statusPanel.add(moodLabel);
        add(statusPanel, BorderLayout.NORTH);

        // --- 2. Pet Image (images: GIF Icons) ---
        petImageLabel = new JLabel("Loading Pet...");
        petImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(petImageLabel, BorderLayout.CENTER);
        
        // --- 3. Panel Aksi ---
        JPanel actionPanel = new JPanel(new FlowLayout());
        JButton feedButton = new JButton("Feed");
        JButton playButton = new JButton("Play");
        JButton sleepButton = new JButton("Sleep");
        
        // Action Listeners: Memicu aksi di Model
        feedButton.addActionListener(e -> pet.feed()); 
        playButton.addActionListener(e -> pet.play()); 
        sleepButton.addActionListener(e -> pet.sleep()); 

        actionPanel.add(feedButton);
        actionPanel.add(playButton);
        actionPanel.add(sleepButton);
        add(actionPanel, BorderLayout.SOUTH);
    }

    private void refreshUI() {
        hungerLabel.setText("Hunger: " + pet.getHunger() + "%");
        energyLabel.setText("Energy: " + pet.getEnergy() + "%");
        moodLabel.setText("Mood: " + pet.getMood() + "%");
    }

    // Implementasi PetListener: Dipanggil oleh VirtualPet
    
    // Sesuai UML: onPetUpdated (View Stats + Save to DB)
    @Override
    public void onPetUpdated(VirtualPet pet) {
        refreshUI();
        petDao.savePetState(pet);
    }

    // Sesuai UML: onStateChanged (View Animation)
    @Override
    public void onStateChanged(PetState state) {
        String imagePath = pet.getAnimation(state);
        ImageIcon icon = AnimationLoader.loadGIF(imagePath);

        if (icon != null) {
            petImageLabel.setIcon(icon);
            petImageLabel.setText("");
        } else {
            petImageLabel.setIcon(null);
            petImageLabel.setText("State: " + state.name());
        }
    }
}
