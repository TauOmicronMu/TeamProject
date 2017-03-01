package main;

import org.lwjgl.BufferUtils;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * The model class
 *
 * @author Ella
 */
class Model {

    private int draw_count;
    private int vbo_id;
    private int vao_id;

    /**
     * Creates the buffers and puts the data inside
     *
     * @param vertices the edges of the pinball
     */
    Model(double[] vertices) {
        draw_count = vertices.length / 3;

        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(vertices.length);
        buffer.put(vertices);
        buffer.flip();

        vao_id = glGenVertexArrays();
        glBindVertexArray(vao_id);

        vbo_id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo_id);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_DOUBLE, false, 0, 0);
        glEnableVertexAttribArray(0);


        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

    }

    private float[] toFloatArray(double[] doubles) {
        float[] floatArray = new float[doubles.length];
        for (int i = 0 ; i < doubles.length; i++)
        {
            floatArray[i] = (float) doubles[i];
        }
        return floatArray;
    }

    /**
     * Draws the circle from many traingle fans
     */
    private void draw() {
        glDrawArrays(GL_TRIANGLE_FAN, 0, draw_count);
    }

    /**
     * Binds the buffers, allows them to be active
     */
    private void bind() {
        glBindVertexArray(vao_id);
        glBindBuffer(GL_ARRAY_BUFFER, vbo_id);
    }

    /**
     * Draws the objects and then unbinds everything!
     *
     * @param vertices
     */
    void render(double[] vertices) {
        bind();
        glVertexPointer(3, GL_DOUBLE, 0, toFloatArray(vertices));

        draw();

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        glDisableVertexAttribArray(0);
    }

}
