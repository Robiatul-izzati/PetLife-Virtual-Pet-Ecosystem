public class CatPet extends VirtualPet {
    private final AnimationPack anim;

    public CatPet() {
        this.anim = new AnimationPack(
            "assets/cat/bg.jpeg",
            "assets/cat/normal.png",
            "assets/cat/eat.png", 
            "assets/cat/sleep.png", 
            "assets/cat/play.png"
        );
    }

    @Override
    public String getSpecies() { return "Kucing"; }

    @Override
    public AnimationPack getAnimations() { return anim; }
}