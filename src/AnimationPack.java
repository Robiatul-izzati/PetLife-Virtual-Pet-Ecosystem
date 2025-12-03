public class AnimationPack {
    public String idle;
    public String eat;
    public String sleep;
    public String play;

    public String getForState(PetState state) {
        if (state == null) return idle;

        return switch (state) {
            case EATING -> eat != null ? eat : idle;
            case SLEEPING -> sleep != null ? sleep : idle;
            case PLAYING -> play != null ? play : idle;
            default -> idle;
        };
    }
}
