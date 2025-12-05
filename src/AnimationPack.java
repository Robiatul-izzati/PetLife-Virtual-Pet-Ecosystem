public class AnimationPack {

    private final String bgPath;
    private final String idlePath; 
    private final String eatPath;  
    private final String sleepPath; 
    private final String playPath;  

    public AnimationPack(String bg, String idle, String eat, String sleep, String play) {
        this.bgPath = bg;
        this.idlePath = idle;
        this.eatPath = eat;
        this.sleepPath = sleep;
        this.playPath = play;
    }

    public String getBackgroundPath() {
        return bgPath;
    }
    
    public String getForState(PetState state) {
        switch (state) {
            case EATING:
                return eatPath;
            case SLEEPING:
                return sleepPath;
            case PLAYING:
                return playPath;
            case NORMAL:
            default:
                return idlePath;
        }
    }
}