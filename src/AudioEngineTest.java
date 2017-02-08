/**
 * Created by tom on 08/02/17.
 */
public class AudioEngineTest {
    public static void main(String[] args) {
        AudioEngine engine = new AudioEngine();
        int ref = engine.createTrack("meow.wav");
        engine.playTrack(ref);
    }
}
