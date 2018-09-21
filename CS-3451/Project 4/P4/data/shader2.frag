#define PROCESSING_COLOR_SHADER

#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

varying vec4 vertColor;
varying vec4 vertTexCoord;

vec2 squareFoil(vec2 coordinates) {
    float x = pow(coordinates.x,2) - pow(coordinates.y, 2);
    float y = 2 * (coordinates.x * coordinates.y);
    return vec2(x, y);
}

void main() {
    int iteration = 0;

    vec2 z = vec2(0.0, 0.0);
    float cx = vertTexCoord.x * 3 - 2.1;
    float cy = vertTexCoord.y * 3 - 1.5;
    vec2 c = vec2(cx, cy);

    while (iteration < 20 && sqrt(pow(z.x,2) + pow(z.y,2)) <= 2) {
        z = squareFoil(z) + c;
        iteration++;
    }

    // if iteration is greater than 20, color white
    if (iteration == 20) {
        gl_FragColor = vec4(1.0, 1.0, 1.0, 1.0);
    } else {
        gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);
    }
}
