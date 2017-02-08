import org.lwjgl.BufferUtils;
import org.lwjgl.openal.*;
import org.lwjgl.util.WaveData;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

/**
 * Object to handle the audio of a game.
 */
public class AudioEngine {

    /** Holds the device and context through which to play the audio. */
    long device;
    long context;

    /** Holds the information required for the listener. */
    private FloatBuffer listenerPos;
    private FloatBuffer listenerVel;
    private FloatBuffer listenerOri;

    /** Holds all of the tracks given to the AudioEngine. */
    private ArrayList<Track> tracks = new ArrayList<Track>();

    /**
     * Object to handle individual audio tracks.
     */
    private class Track {
        private IntBuffer buffer;
        private IntBuffer source;

        private FloatBuffer sourcePos;
        private FloatBuffer sourceVel;

        private float pitch;
        private float gain;

        /**
         * Load in the data from the disk to OpenAL as a buffer, and create a source
         * to play our buffer.
         * @param filepath The path to the .wav file to load.
         * @return Returns AL10.AL_TRUE on success, and otherwise returns AL10.AL_FALSE.
         */
        private int loadALData(String filepath) {
            /** Load the wav data into the buffer */
            AL10.alGenBuffers(buffer);
            if(AL10.alGetError() != AL10.AL_NO_ERROR) return AL10.AL_FALSE;

            /** Load the wave file from the classpath */
            WaveData waveFile = WaveData.create(filepath);

            /** Set up the buffer with the format (etc.) of the wav file.*/
            AL10.alBufferData(buffer.get(0), waveFile.format, waveFile.data, waveFile.samplerate);
            waveFile.dispose();

            /** Bind the buffer to the corresponding source */
            AL10.alGenSources(source);
            if(AL10.alGetError() != AL10.AL_NO_ERROR) return AL10.AL_FALSE;

            /** Configure the AL data to do what we want. */
            AL10.alSourcei (source.get(0), AL10.AL_BUFFER,   buffer.get(0));
            AL10.alSourcef (source.get(0), AL10.AL_PITCH,    pitch);
            AL10.alSourcef (source.get(0), AL10.AL_GAIN,     gain);
            AL10.alSourcefv(source.get(0), AL10.AL_POSITION, sourcePos);
            AL10.alSourcefv(source.get(0), AL10.AL_VELOCITY, sourceVel);

            /** Do a final error check, return AL10.AL_TRUE if everything worked. */
            if(AL10.alGetError() == AL10.AL_NO_ERROR) return AL10.AL_TRUE;

            /** Otherwise, return AL10.AL_FALSE because something went wrong.*/
            return AL10.AL_FALSE;
        }

        private void play() {
            AL10.alSourcePlay(source.get());
            // TODO: Thread this!!!
            boolean x = true;
            while(x);
            killALData();
        }

        /**
         * Free the memory that we have allocated to the buffer and sources
         * for this track.
         */
        private void killALData() {
            AL10.alDeleteSources(source);
            AL10.alDeleteBuffers(buffer);
        }

        /**
         * (Use this as the default!)
         * Creates a new Track from the wav file at filepath, with a static source at the default location (0,0,0)
         * and default gain (1.0f) and pitch (1.0f).
         * @param filepath The path to the wav file to load in.
         */
        public Track(String filepath) {
            buffer = BufferUtils.createIntBuffer(1);
            source = BufferUtils.createIntBuffer(1);

            /**
             * Set default values for the source, gain and pitch:
             *     sourcePos : 0.0f, 0.0f, 0.0f
             *     sourceVel : 0.0f, 0.0f, 0.0f
             *     gain      : 1.0f
             *     pitch     : 1.0f
             */
            sourcePos = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f}).rewind();
            sourceVel = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f}).rewind();
            gain = 1.0f;
            pitch = 1.0f;

            loadALData(filepath);
        }

        /**
         * (Probably don't use this unless you have a really specific reason!)
         * Creates a new Track from the wav file at filepath, with custom source position, source velocity,
         * gain and pitch.
         * @param filepath The path to the wav file to load in.
         * @param sourcePos Custom position of the source - (Float, Float, Float).
         * @param sourceVel Custom velocity of the source - (Float, Float, Float).
         * @param gain Custom gain of the track.
         * @param pitch Custom pitch of the track.
         */
        public Track(String filepath, FloatBuffer sourcePos, FloatBuffer sourceVel, Float gain, Float pitch) {
            buffer = BufferUtils.createIntBuffer(1);
            source = BufferUtils.createIntBuffer(1);

            this.sourcePos = sourcePos;
            this.sourceVel = sourceVel;
            this.gain = gain;
            this.pitch = pitch;

            loadALData(filepath);
        }
    }

    /**
     * Tell OpenAL to use our position, velocity and orientation
     * for the listener.
     */
    private void setListenerValues() {
        AL10.alListenerfv(AL10.AL_POSITION, listenerPos);
        AL10.alListenerfv(AL10.AL_VELOCITY, listenerVel);
        AL10.alListenerfv(AL10.AL_ORIENTATION, listenerOri);
    }

    /**
     * (Use this as the default!)
     * Create an AudioEngine with a static listener at the default location (0,0,0).
     */
    public AudioEngine() {
        setDeviceAndContext();

        /**
         * Set default values for the listener:
         *     listenerPos : 0.0f, 0.0f, 0.0f
         *     listenerVel : 0.0f, 0.0f, 0.0f
         *     listenerOri : 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f
         */
        listenerPos = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[] {0.0f, 0.0f, 0.0f}).rewind();
        listenerVel = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] {0.0f, 0.0f, 0.0f}).rewind();
        listenerOri =
                (FloatBuffer)BufferUtils.createFloatBuffer(6).put(new float[] {0.0f,0.0f, -1.0f, 0.0f, 1.0f, 0.0f}).rewind();

        setListenerValues();
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
        setDeviceAndContext();

        this.listenerPos = listenerPos;
        this.listenerVel = listenerVel;
        this.listenerOri = listenerOri;

        setListenerValues();
    }

    /**
     * Create a Track in the audio engine with the wav file at filepath.
     * @param filepath The path to the wav file to create a Track with.
     */
    public int createTrack(String filepath) {
        /** Create a new Track and add it to tracks.*/
        Track track = new Track(filepath);
        tracks.add(track);

        /** Return the size of tracks as a reference to the track. */
        return tracks.size();
    }

    /**
     * Set the device and context for OpenAL to use for the audio.
     */
    private void setDeviceAndContext() {
        this.device = ALC10.alcOpenDevice((ByteBuffer) null);
        ALCCapabilities deviceCaps = ALC.createCapabilities(device);

        this.context = ALC10.alcCreateContext(device, (IntBuffer) null);
        ALC10.alcMakeContextCurrent(context);
        AL.createCapabilities(deviceCaps);
    }

    /**
     * Plays the Track with reference, ref.
     * @param ref The reference to the Track to play.
     */
    public void playTrack(int ref) {
        /** Get the track from the list of Tracks and play it. */
        Track track = (Track) tracks.get(ref);
        track.play();
    }
}
