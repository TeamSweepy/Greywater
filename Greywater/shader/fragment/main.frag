#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

uniform vec2 resolution;

varying LOWP vec4 vColor;
varying vec2 vTexCoord;

uniform sampler2D u_texture;

const float RADIUS = 0.75;
const float SOFTNESS = 0.45;

void main() {

    vec4 texColor = texture2D(u_texture, vTexCoord);
    vec2 position = (gl_FragCoord.xy / resolution.xy) - vec2(0.5);
    //position.x *= resolution.x / resolution.y;

    float len = length(position);

    float vignette = smoothstep(RADIUS, RADIUS-SOFTNESS, len);

    texColor.rgb *= vignette;

    gl_FragColor = texColor;

}