#version 400 core

in vec4 vertices;
in vec4 s_vNormal;
in vec2 s_vTexCoord;

out vec2 texCoord;

uniform mat4 p;
uniform mat4 mv;

void main()
{
	gl_Position = p*mv*vertices;
	texCoord = s_vTexCoord;
}