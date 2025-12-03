public class MusangPet extends VirtualPet {
    private final AnimationPack anim;

    public MusangPet() {
        anim = new AnimationPack();
        anim.idle  = "../assets/ferret/normal.jpeg";
        anim.eat   = "../assets/ferret/eat.jpeg";
        anim.sleep = "../assets/ferret/sleep.jpeg";
        anim.play  = "../assets/ferret/play.jpeg";
    }

    @Override
    public String getSpecies() { return "Musang"; }

    @Override
    public AnimationPack getAnimations() { return anim; }
}
