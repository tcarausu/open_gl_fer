package utility;

import com.jogamp.opengl.GL2;

//import javax.media.opengl.GL2;

import static java.lang.StrictMath.abs;

public class Bresenham_Utility {
    private static float D;

    private static float dx, dy, sx, sy,err,yi, xi,e2;
    public static void plotLineLow(float x0, float y0, float x1, float y1, GL2 gl) {
        dx = x1 - x0;
        dy = y1 - y0;

        yi = 1;
        if (dy < 0) {
            yi = -1;
            dy = -dy;
        }
        D = 2 * dy - dx;
        float y = y0;

        for (int i = (int) x0; i < x1; i++) {
            gl.glVertex2i(i, (int) y);
            if (D > 0) {
                y = y + yi;
                D = D - 2 * dx;
            }
            D = D + 2 * dy;
        }
    }

    public static void plotLineHigh(float x0, float y0, float x1, float y1, GL2 gl) {
        dx = x1 - x0;
        dy = y1 - y0;
        xi = 1;
        if (dx < 0) {
            xi = -1;
            dx = -dx;
        }
        D = 2 * dx - dy;
        float x = x0;

        for (int i = (int) y0; i < y1; i++) {
            gl.glVertex2i((int) x, (int) (float) i);
            if (D > 0) {
                x = x + xi;
                D = D - 2 * dy;
            }
            D = D + 2 * dx;
        }
    }

    public static void plotLine(float x0, float y0, float x1, float y1, GL2 gl) {
        if (abs(y1 - y0) < abs(x1 - x0)) {
            if (x0 > x1) {
                plotLineLow(x1, y1, x0, y0, gl);

            } else plotLineLow(x0, y0, x1, y1, gl);

        } else {
            if (y0 > y1)
                plotLineHigh(x1, y1, x0, y0, gl);
            else
                plotLineHigh(x0, y0, x1, y1, gl);
        }
    }

    public static void plotLineErr(float x0, float y0, float x1, float y1, GL2 gl) {
        dx = abs(x1 - x0);
        sx = x0 < x1 ? 1 : -1;
        dy = -abs(y1 - y0);
        sy = y0 < y1 ? 1 : -1;
        err = dx + dy;  /* error value e_xy */
        while (true) {  /* loop */
            gl.glVertex2i((int) x0, (int) y0);

            if (x0 == x1 && y0 == y1) break;
            e2 = 2 * err;
            if (e2 >= dy) {
                err += dy; /* e_xy+e_x > 0 */
                x0 += sx;
            }
            if (e2 <= dx) { /* e_xy+e_y < 0 */
                err += dx;
                y0 += sy;
            }
        }
    }

}
