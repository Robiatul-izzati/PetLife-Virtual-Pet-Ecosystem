public interface PetListener {

    void onPetUpdated(VirtualPet pet, int before, int after, String action);
    
}
