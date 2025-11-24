public class AnimationPack {

    private String normalGIF;
    private String eatGIF;
    private String sleepGIF;
    private String playGIF;

    public AnimationPack(String normal, String eat, String sleep, String play) {
        this.normalGIF = normal;
        this.eatGIF = eat;
        this.sleepGIF = sleep;
        this.playGIF = play;
    }

    public String getForState(PetState state) {
        switch (state) {
            case EAT:
                return eatGIF;
            case SLEEP:
                return sleepGIF;
            case PLAY:
                return playGIF;
            default:
                return normalGIF;
        }
    }
}
