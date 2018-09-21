#define PROCESSING_TEXTURE_SHADER

uniform mat4 transform;
uniform mat4 texMatrix;

attribute vec4 position;
attribute vec4 color;
attribute vec3 normal;
attribute vec2 texCoord;

varying vec4 vertColor;
varying vec4 vertTexCoord;

uniform sampler2D texture;

void main() {
    vertColor = color;
    vertTexCoord = texMatrix * vec4(texCoord, 1.0, 1.0);

    vec4 texColor = texture2D(texture, vertTexCoord.xy);
    float intensity = texColor.r * 0.3 + texColor.g * 0.6 + texColor.b * 0.1;

    vec4 pos = position;
    pos.xyz += normal * intensity * 100;

    gl_Position = transform * pos;
}
