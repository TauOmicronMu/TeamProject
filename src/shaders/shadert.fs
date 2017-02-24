# version 400 core

in vec2 texCoord;
out vec4 out_Color;
uniform sampler2D texture;

void main()
{
	out_Color = texture2D(texture, texCoord);
}