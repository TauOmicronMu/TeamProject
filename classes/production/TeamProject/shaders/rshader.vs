#version 400 core

in vec3 vertices;

out vec3 colour;

void main()
{
	gl_Position = vec4(vertices, 1.0);
	colour = vec3(vertices.x+0.5,1.0,vertices.y+0.5);
}