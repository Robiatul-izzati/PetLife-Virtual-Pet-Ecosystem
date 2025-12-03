public class CatPet extends VirtualPet {
    private final AnimationPack anim;

    public CatPet() {
        anim = new AnimationPack();
        anim.idle  = "../assets/cat/normal.jpeg";
        anim.eat   = "../assets/cat/eat.jpeg";
        anim.sleep = "../assets/cat/sleep.jpeg";
        anim.play  = "../assets/cat/play.jpeg";
    }

    @Override
    public String getSpecies() { return "Kucing"; }

    @Override
    public AnimationPack getAnimations() { return anim; }
}
