public class PetThread extends Thread {

    private VirtualPet pet;
    private boolean running = true;

    public PetThread(VirtualPet pet) {
        this.pet = pet;
    }

    @Override
    public void run() {
        while (running) {
            try {
                pet.decay(); // Memicu notifyListeners() di VirtualPet
                Thread.sleep(2000); // Decay setiap 2 detik
            } catch (InterruptedException e) {
                running = false;
            }
        }
    }

    public void stopThread() {
        running = false;
        this.interrupt(); 
    }
}
