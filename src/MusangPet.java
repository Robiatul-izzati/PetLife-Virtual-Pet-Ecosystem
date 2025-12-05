public class MusangPet extends VirtualPet {
    private final AnimationPack anim;

    public MusangPet() {
        this.anim = new AnimationPack(
            "assets/ferret/bg.jpeg",
            "assets/ferret/normal.png",
            "assets/ferret/eat.png", 
            "assets/ferret/sleep.png", 
            "assets/ferret/play.png"
        );
    }

    @Override
    public String getSpecies() { return "Musang"; }

    @Override
    public AnimationPack getAnimations() { return anim; }
}