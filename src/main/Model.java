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
public class Model {

    private int draw_count;
    private int vbo_id;
    private int vao_id;
    private DoubleBuffer buffer;

    /**
     * Creates the buffers and puts the data inside
     *
     * @param vertices the edges of the pinball
     */
    Model(double[] vertices) {
        draw_count = vertices.length / 3;
      
        buffer = BufferUtils.createDoubleBuffer(vertices.length);
        buffer.put(vertices);
        buffer.flip();

        vao_id = glGenVertexArrays();
        glBindVertexArray(vao_id);

        vbo_id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo_id);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_DYNAMIC_DRAW);

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

    private void coolDraw() {
        glDrawArrays(GL_LINE_LOOP, 0, draw_count);
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
    void render(double[] vertices, boolean outline) {
        long st;
        boolean debug = false;
        long tst = System.currentTimeMillis();

        st = System.currentTimeMillis();
        if(debug) System.out.print("buffer.clear() : ");
        buffer.clear();
        if(debug) System.out.println(System.currentTimeMillis() - st);

        st = System.currentTimeMillis();
        if(debug) System.out.print("buffer.put(vertices) : ");
        buffer.put(vertices);
        if(debug) System.out.println(System.currentTimeMillis() - st);

        st = System.currentTimeMillis();
        if(debug) System.out.print("buffer.flip() : ");
        buffer.flip();
        if(debug)  System.out.println(System.currentTimeMillis() - st);

        st = System.currentTimeMillis();
        if(debug) System.out.print("bind() : ");
        bind();
        if(debug) System.out.println(System.currentTimeMillis() - st);

        st = System.currentTimeMillis();
        if(debug) System.out.print("glBufferSubData(GL_ARRAY_BUFFER, 0, buffer) : ");
        glBufferSubData(GL_ARRAY_BUFFER, 0, buffer);
        if(debug) System.out.println(System.currentTimeMillis() - st);

        st = System.currentTimeMillis();
        if(debug) System.out.print("glVertexPointer(3, GL_DOUBLE, 0, toFloatArray(vertices)) : ");
        glVertexPointer(3, GL_DOUBLE, 0, toFloatArray(vertices));
        if(debug) System.out.println(System.currentTimeMillis() - st);

        st = System.currentTimeMillis();
        if(debug) System.out.print("draw() : ");

        if (outline) coolDraw();
        else draw();

        if(debug) System.out.println(System.currentTimeMillis() - st);

        st = System.currentTimeMillis();
        if(debug) System.out.print("glBindBuffer(GL_ARRAY_BUFFER, 0) : ");
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        if(debug) System.out.println(System.currentTimeMillis() - st);

        st = System.currentTimeMillis();
        if(debug) System.out.print("glBindVertexArray(0) : ");
        glBindVertexArray(0);
        if(debug) System.out.println(System.currentTimeMillis() - st);

        st = System.currentTimeMillis();
        if(debug) System.out.print("glDisableVertexArribArray(0) : ");
        glDisableVertexAttribArray(0);
        if(debug) System.out.println(System.currentTimeMillis() - st);

        if(debug) System.out.println("TOTAL TIME : " + (System.currentTimeMillis() - tst));
    }

}
