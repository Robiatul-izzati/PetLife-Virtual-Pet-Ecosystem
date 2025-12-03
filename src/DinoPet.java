public class DinoPet extends VirtualPet {

    private String species;
    private AnimationPack animationPack;

    public DinoPet(String species, AnimationPack pack) {
        super();
        this.species = species;
        this.animationPack = pack;
    }

    public String getSpecies() {
        return species;
    }

    public String getAnimation(PetState state) {
        return animationPack.getForState(state);
    }
}
