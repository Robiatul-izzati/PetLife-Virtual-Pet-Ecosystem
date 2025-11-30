import java.util.EventListener;

public interface PetListener extends EventListener {
    // Dipanggil ketika atribut numerik (stats) berubah (View Stats)
    void onPetUpdated(VirtualPet pet);
    
    // Dipanggil ketika state (animasi) berubah (View Animation)
    void onStateChanged(PetState state);
}