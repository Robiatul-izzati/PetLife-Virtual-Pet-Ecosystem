public class ChickenPet extends VirtualPet {
    private final AnimationPack anim;

    public ChickenPet() {
        this.anim = new AnimationPack(
            "assets/chicken/bg.jpeg",
            "assets/chicken/normal.png",
            "assets/chicken/eat.png", 
            "assets/chicken/sleep.png", 
            "assets/chicken/play.png"
        );
    }

    @Override
    public String getSpecies() { return "Ayam"; }

    @Override
    public AnimationPack getAnimations() { return anim; }
}