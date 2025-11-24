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
                pet.decay();
                Thread.sleep(2000); // decay setiap 2 detik
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopThread() {
        running = false;
    }
}
