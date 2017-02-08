import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Object to handle the audio of a game.
 */
public class AudioEngine {
    /* Hold the information required for the listener.*/
    private FloatBuffer listenerPos;
    private FloatBuffer listenerVel;
    private FloatBuffer listenerOri;

    private Track[] tracks;

    private class Track {
        private IntBuffer buffer;
        private IntBuffer source;

        public Track(String filepath) {
            
        }
    }

    /**
     * (Use this as the default!)
     * Create an AudioEngine with a static listener at the default location (0,0,0).
     */
    public AudioEngine() {
        listenerPos = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[] {0.0f, 0.0f, 0.0f}).rewind();
        listenerVel = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] {0.0f, 0.0f, 0.0f}).rewind();
        listenerOri =
                (FloatBuffer)BufferUtils.createFloatBuffer(6).put(new float[] {0.0f,0.0f, -1.0f, 0.0f, 1.0f, 0.0f}).rewind();
    }

    /**
     * (Probably don't use this unless you have a really specific reason!)
     * Create an AudioEngine with custom position, velocity and orientation for the listener.
     * @param listenerPos Custom position of the listener - (Float, Float, Float)
     * @param listenerVel Custom velocity of the listener - (Float, Float, Float)
     * @param listenerOri Custom orientation of the listener - (Float, Float, Float, Float, Float, Float)
     *                    First three floats - "at"; Second three Floats - "up".
     */
    public AudioEngine(FloatBuffer listenerPos, FloatBuffer listenerVel, FloatBuffer listenerOri) {

    }
}
