#define PROCESSING_TEXTURE_SHADER

#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

uniform sampler2D texture;

varying vec4 vertColor;
varying vec4 vertTexCoord;

float getIntensity(vec4 color) {
    return color.r * 0.3 + color.g * 0.6 + color.b * 0.1;
}

void main() {
    // get intensity of surrounding coordinates
    float sum = 0;
    float intensity = 0;
    vec2 neighbor;
    vec4 neighborColor;

    neighbor = vec2(vertTexCoord.x + 1/256.0, vertTexCoord.y);
    neighborColor = texture2D(texture, neighbor.xy);
    intensity = getIntensity(neighborColor);
    sum += intensity;

    neighbor = vec2(vertTexCoord.x - 1/256.0, vertTexCoord.y);
    neighborColor = texture2D(texture, neighbor.xy);
    intensity = getIntensity(neighborColor);
    sum += intensity;

    neighbor = vec2(vertTexCoord.x, vertTexCoord.y + 1/256.0);
    neighborColor = texture2D(texture, neighbor.xy);
    intensity = getIntensity(neighborColor);
    sum += intensity;

    neighbor = vec2(vertTexCoord.x, vertTexCoord.y - 1/256.0);
    neighborColor = texture2D(texture, neighbor.xy);
    intensity = getIntensity(neighborColor);
    sum += intensity;

    // get sum - 4 * main intensity
    vec4 diffuseColor = texture2D(texture, vertTexCoord.xy);
    sum -= (4 * getIntensity(diffuseColor));

    // scale L value (in case it's too dark)
    sum *= 5;

    gl_FragColor = vec4(sum, sum, sum, 1.0);
}
