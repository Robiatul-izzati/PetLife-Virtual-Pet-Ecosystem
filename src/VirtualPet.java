import java.util.*;

public abstract class VirtualPet {
    protected int hunger = 50;
    protected int energy = 50;
    protected int mood   = 50;

    protected PetState state = PetState.NORMAL;

    private final List<PetListener> listeners = new ArrayList<>();

    public abstract String getSpecies();
    public abstract AnimationPack getAnimations();

    public synchronized void feed() {
        int before = hunger;
        hunger = Math.min(100, hunger + 25);
        state = PetState.EATING;
        notifyListeners(before, hunger, "FEED");
    }

    public synchronized void play() {
        int before = mood;
        mood = Math.min(100, mood + 25);
        state = PetState.PLAYING;
        notifyListeners(before, mood, "PLAY");
    }

    public synchronized void sleep() {
        int before = energy;
        energy = Math.min(100, energy + 25);
        state = PetState.SLEEPING;
        notifyListeners(before, energy, "SLEEP");
    }

    public synchronized void decay() {
        int before = hunger;
        hunger = Math.max(0, hunger - 1);
        energy = Math.max(0, energy - 1);
        mood   = Math.max(0, mood - 1);

        if (state != PetState.NORMAL)
            state = PetState.NORMAL;

        notifyListeners(before, hunger, "DECAY");
    }

    public synchronized void resetState() {
        if (state != PetState.NORMAL) {
            state = PetState.NORMAL;
            notifyListeners(0, 0, "RESET_STATE");
        }
    }

    private void notifyListeners(int before, int after, String action) {
        for (PetListener l : listeners)
            l.onPetUpdated(this, before, after, action);
    }

    public void addListener(PetListener l) { listeners.add(l); }

    public int getHunger() { 
        return hunger;
    }
    public int getEnergy() {
        return energy;
    }
    public int getMood()   {
        return mood;
    }
    public PetState getState() {
        return state;
    }
}
