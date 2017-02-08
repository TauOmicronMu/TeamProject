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
        int ref2 = engine.createTrack("meow.wav");
        engine.loopTrack(ref);
        for(int i = 0; i < 20; i++) {
            sleep(250);
            engine.playTrack(ref2);
        }
        while(true);
    }
}
