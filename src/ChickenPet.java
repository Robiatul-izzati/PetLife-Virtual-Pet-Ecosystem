public class ChickenPet extends VirtualPet {
    private final AnimationPack anim;

    public ChickenPet() {
        anim = new AnimationPack();
        anim.idle  = "../assets/chicken/normal.jpeg";
        anim.eat   = "../assets/chicken/eat.jpeg";
        anim.sleep = "../assets/chicken/sleep.jpeg";
        anim.play  = "../assets/chicken/play.jpeg";
    }

    @Override
    public String getSpecies() { return "Ayam"; }

    @Override
    public AnimationPack getAnimations() { return anim; }
}
