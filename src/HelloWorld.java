import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.*;
import org.lwjgl.util.WaveData;


public class HelloWorld {
    /* Stores the sound data & information about how the sound should be played */
    IntBuffer buffer = BufferUtils.createIntBuffer(1); // We use buffers to hold sound data :D

    /* The source will be where the sound will come from */
    IntBuffer source = BufferUtils.createIntBuffer(1); // Sources are points in space that emit sound.
    /* This is the position of our source*/
    FloatBuffer sourcePos = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f}).rewind();
    /* This is the velocity of our source */
    FloatBuffer sourceVel = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] {0.0f, 0.0f, 0.0f}).rewind();

    /* The listener represents where the user is */
    /* Position of the listener */
    FloatBuffer listenerPos = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] {0.0f, 0.0f, 0.0f}).rewind();
    /* Velocity of the listener */
    FloatBuffer listenerVel = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] {0.0f, 0.0f, 0.0f}).rewind();
    /* Orientation of the listener (3x "at", 3x "up") */
    FloatBuffer listenerOri =
            (FloatBuffer)BufferUtils.createFloatBuffer(6).put(new float[] {0.0f,0.0f, -1.0f, 0.0f, 1.0f, 0.0f}).rewind();

    /*
     * This loads in our data from the disk and sends the data into OpenAL
     * as a buffer. A source is then also created to play our buffer.
     */
    int loadALData(String filepath) {
        // Load the wav data into our buffer :)
        AL10.alGenBuffers(buffer);

        if(AL10.alGetError() != AL10.AL_NO_ERROR) return AL10.AL_FALSE;

        WaveData waveFile = WaveData.create(filepath);
        AL10.alBufferData(buffer.get(0), waveFile.format, waveFile.data, waveFile.samplerate);
        waveFile.dispose(); // :( Poor waveFile :(

        // Bind the buffer with the source.
        AL10.alGenSources(source);

        if(AL10.alGetError() != AL10.AL_NO_ERROR) return AL10.AL_FALSE;

        AL10.alSourcei(source.get(0), AL10.AL_BUFFER, buffer.get(0));
        AL10.alSourcef(source.get(0), AL10.AL_PITCH, 1.0f);
        AL10.alSourcef(source.get(0), AL10.AL_GAIN, 1.0f);
        AL10.alSourcefv(source.get(0), AL10.AL_POSITION, sourcePos);
        AL10.alSourcefv(source.get(0), AL10.AL_VELOCITY, sourceVel);

        if(AL10.alGetError() == AL10.AL_NO_ERROR) return AL10.AL_TRUE;
        return AL10.AL_FALSE;
    }

    /*
     * Tell OpenAL to use our data for the Listener.
     */
    void setListenerValues() {
        AL10.alListenerfv(AL10.AL_POSITION, listenerPos);
        AL10.alListenerfv(AL10.AL_VELOCITY, listenerVel);
        AL10.alListenerfv(AL10.AL_ORIENTATION, listenerOri);
    }

    /*
     * Free the memory that we have allocated to the buffers
     * and sources.
     */
    void killALData() {
        AL10.alDeleteSources(source);
        AL10.alDeleteBuffers(buffer);
    }

    void execute(String filepath) {
        long device;
        long context;
        try {
            device = ALC10.alcOpenDevice((ByteBuffer) null);
            ALCCapabilities deviceCaps = ALC.createCapabilities(device);
            context = ALC10.alcCreateContext(device, (IntBuffer) null);
            ALC10.alcMakeContextCurrent(context);
            AL.createCapabilities(deviceCaps);

            if(loadALData(filepath) == AL10.AL_FALSE) {
                System.err.println("Error loading data");
                return;
            }

            setListenerValues();

            System.out.println("Hey look I'm gonna meow");
            boolean x = true;
            AL10.alSourcePlay(source.get(0));
            while(x) {};
            System.out.println("<\\meow>");

            killALData();
            // AL.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // TODO: Work out why the fuck this doesn't work if I use the one in ../res ._.
        new HelloWorld().execute("meow.wav");
    }
}