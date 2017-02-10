/**
 * Created by tom on 08/02/17.
 */
public class AudioEngineTest {

    public static void sleep(int n) {
        try {
            Thread.sleep(n);
        } catch(Exception e) {}
    }
    public static void main(String[] args) {
        AudioEngine engine = new AudioEngine();
        int ref = engine.createTrack("meow.wav");
        engine.playTrack(ref);
        engine.loopTrack(ref);
        sleep(5000);
        engine.stopTrack(ref);
    }
}
