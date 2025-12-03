public class PetThread extends Thread {
    private final VirtualPet pet;
    private final PetDAO dao;
    private boolean running = true;

    public PetThread(VirtualPet p, PetDAO d, ActivityDAO a) {
        this.pet = p; this.dao = d;
    }

    @Override
    public void run() {
        while (running) {
            pet.decay();
            try {
                dao.savePetStats(pet);
                //actDao.logActivity("Auto Decay: Hunger=" + pet.getHunger());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
        }
    }

    public void stopThread() { running = false; this.interrupt(); }
}
