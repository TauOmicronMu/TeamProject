package graphics.shader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.lwjgl.opengl.GL20.*;

/**
 * Allows for different (and cool) colours!
 *
 * @author Ella
 */
public class Shader {

    private int program;
    private int fs;

    /**
     * Uses the shader files to create a shader which can then be used to bring colour
     *
     * @param filename The filename (minus extension) of the shader files.
     */
    public Shader(String filename) {
        program = glCreateProgram();

        int vs = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vs, readFile(filename + ".vs"));
        glCompileShader(vs);
        if (glGetShaderi(vs, GL_COMPILE_STATUS) != 1) {
            System.err.println(glGetShaderInfoLog(vs));
            System.exit(1);
        }


        fs = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fs, readFile(filename + ".fs"));
        glCompileShader(fs);
        if (glGetShaderi(fs, GL_COMPILE_STATUS) != 1) {
            System.err.println(glGetShaderInfoLog(fs));
            System.exit(1);
        }

        glAttachShader(program, vs);
        glAttachShader(program, fs);

        glBindAttribLocation(program, 0, "vertices");

        glLinkProgram(program);
        if (glGetProgrami(program, GL_LINK_STATUS) != 1) {
            System.err.println(glGetProgramInfoLog(program));
            System.exit(1);
        }


        glValidateProgram(program);
        if (glGetProgrami(program, GL_VALIDATE_STATUS) != 1) {
            System.err.println(glGetProgramInfoLog(program));
            System.exit(1);
        }
    }

    /**
     * Allows you to use the shader
     */
    public void bind() {
        glUseProgram(program);
    }

    /**
     * Reads the shader files
     *
     * @param filename the file to be read
     * @return the file contents
     */
    private String readFile(String filename) {
        StringBuilder string = new StringBuilder();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(new File("src/shaders/" + filename)));
            String line;
            while ((line = br.readLine()) != null) {
                string.append(line);
                string.append("\n");
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return string.toString();
    }

    /**
     * Detaches the program ID from the context
     */
    public void stop() {
        glUseProgram(0);
    }

}
