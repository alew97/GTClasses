#define PROCESSING_COLOR_SHADER

#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

varying vec4 vertColor;
varying vec4 vertTexCoord;

void main() {
    gl_FragColor = vec4(0.2, 0.4, 1.0, 0.7);

    for (float x = 1; x <= 3; x++) {
        for (float y = 1; y <= 3; y++) {
            float circleX = x/3 - 1.0/6;
            float circleY = y/3 - 1.0/6;
            float circleR = 0.1;

            float xdist = vertTexCoord.x - circleX;
            float ydist = vertTexCoord.y - circleY;
            if ( pow(xdist, 2) + pow(ydist, 2) <= pow(circleR, 2) ) {
                gl_FragColor = vec4(0.2, 0.4, 1.0, 0);
            }
        }
    }
}
