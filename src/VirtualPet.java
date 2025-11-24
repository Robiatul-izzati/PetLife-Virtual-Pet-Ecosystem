public abstract class VirtualPet {

    protected int hunger;
    protected int energy;
    protected int mood;
    protected PetState state;

    public VirtualPet() {
        this.hunger = 50;
        this.energy = 50;
        this.mood = 50;
        this.state = PetState.IDLE;
    }

    public void feed() {
        hunger = Math.max(0, hunger - 20);
        mood += 5;
        state = PetState.EAT;
    }

    public void sleep() {
        energy = Math.min(100, energy + 30);
        state = PetState.SLEEP;
    }

    public void play() {
        mood = Math.min(100, mood + 20);
        hunger += 10;
        energy -= 10;
        state = PetState.PLAY;
    }

    public void decay() {
        hunger += 2;
        energy -= 1;
        mood -= 1;
    }

    public PetState getState() {
        return state;
    }
}
