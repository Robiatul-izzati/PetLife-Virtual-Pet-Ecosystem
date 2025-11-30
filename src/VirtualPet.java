import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public abstract class VirtualPet {

    protected int hunger;
    protected int energy;
    protected int mood;
    protected PetState state;
    // LISTENER: Sesuai Class Diagram
    private final List<PetListener> listeners = new ArrayList<>();

    public VirtualPet() {
        this.hunger = 50;
        this.energy = 50;
        this.mood = 50;
        this.state = PetState.IDLE;
    }
    
    // LISTENER: Sesuai Class Diagram
    public void addListener(PetListener listener) {
        this.listeners.add(listener);
    }

    // LISTENER: Memicu pembaruan ke GUI dan DAO
    public void notifyListeners() {
        for (PetListener listener : listeners) {
            listener.onPetUpdated(this); 
            listener.onStateChanged(this.state); 
        }
    }

    // Aksi Player
    public void feed() {
        hunger = Math.max(0, hunger - 20);
        mood = Math.min(100, mood + 5);
        state = PetState.EAT;
        notifyListeners(); 
    }

    public void sleep() {
        energy = Math.min(100, energy + 30);
        state = PetState.SLEEP;
        notifyListeners(); 
    }

    public void play() {
        mood = Math.min(100, mood + 20);
        hunger = Math.min(100, hunger + 10);
        energy = Math.max(0, energy - 10);
        state = PetState.PLAY;
        notifyListeners(); 
    }

    // Decay dipanggil oleh PetThread (Automatic Status Decrease)
    public void decay() {
        hunger = Math.min(100, hunger + 2);
        energy = Math.max(0, energy - 1);
        mood = Math.max(0, mood - 1);
        notifyListeners(); 
    }
    
    // Getters
    public int getHunger() { return hunger; }
    public int getEnergy() { return energy; }
    public int getMood() { return mood; }
    public PetState getState() { return state; }
}
