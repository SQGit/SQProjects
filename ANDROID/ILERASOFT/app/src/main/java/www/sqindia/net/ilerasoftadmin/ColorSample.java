package www.sqindia.net.ilerasoftadmin;


import android.content.res.Resources;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that holds predefined color integer arrays (e.g.
 * ColorTemplate.VORDIPLOM_COLORS) and convenience methods for loading colors
 * from resources.
 *
 * @author Philipp Jahoda
 */
public class ColorSample {
   public static final int Color_1       = 0xFFF44336;
   public static final int Color_2      = 0xFFEF5350;
   public static final int Color_3        = 0xFFE57373;
   public static final int Color_4      = 0xFFEF9A9A;
   public static final int Color_5       = 0xFFFFCDD2;
    public static final int Color_6       = 0xFF26A69A;
    public static final int Color_7      = 0xFF4DB6AC;
    public static final int Color_8        = 0xFF80CBC4;
    public static final int Color_9      = 0xFFB2DFDB;
    public static final int Color_10       = 0xFF009688;
    public static final int Color_11       = 0xFFFF9800;
    public static final int Color_12      = 0xFFFFA726;
    public static final int Color_13        = 0xFFFFB74D;
    public static final int Color_14      = 0xFFFFCC80;
    public static final int Color_141      = 0xFFFFE0B2;
    public static final int Color_15       = 0xFF03A9F4;
    public static final int Color_16       = 0xFF29B6F6;
    public static final int Color_17      = 0xFF4FC3F7;
    public static final int Color_18        = 0xFF81D4FA;
    public static final int Color_19      = 0xFFB3E5FC;
    public static final int Color_20       = 0xFFE1F5FE;

    public static final int Color_red   = 0xFFF44336;
    public static final int Color_teal      = 0xFF009688;
    public static final int Color_orange       = 0xFFFF9800;
    public static final int Color_blue       = 0xFF03A9F4;

    public static final int Color_cyan      = 0xFF00BCD4;
    public static final int Color_lblue       = 0xFF03A9F4;
    public static final int Color_22      = 0xFF8BC34A;
    public static final int Color_23        = 0xFF80DEEA;
    public static final int Color_24      = 0xFFCDDC39;



   public static final int MAGENTA     = 0xFFFF00FF;
   public static final int TRANSPARENT = 0;

    public static final int COLOR_NONE = -1;


    public static final int COLOR_SKIP = -2;


    public static final int[] JOYFUL_COLORS =new int[]
            {
            Color_red,Color_teal,Color_orange,Color_blue,Color_cyan
            };
    public static final int[] PINK_COLORS = new int[]
            {
            Color_1, Color_2, Color_3, Color_4,Color_5
            };

    public static final int[] RED_COLORS = new int[]{
            Color_1, Color_2, Color_3, Color_4,Color_5

    };
    public static final int[] TEAL_COLORS = {
            Color_6, Color_7, Color_8, Color_9,Color_10

    };
    public static final int[] ORANGE_COLORS = {
            Color_11, Color_12, Color_13, Color_14,Color_141

    };
    public static final int[] BLUE_COLORS = {
            Color_16, Color_17, Color_18, Color_19,Color_20

    };


    public static final int[] CYAN_COLORS = {
            Color_cyan, Color_22, Color_teal, Color_24

    };



    /**
     * Returns the Android ICS holo blue light color.
     *
     * @return
     */
    public static int getHoloBlue() {
        return Color.rgb(51, 181, 229);
    }

    /**
     * turn an array of resource-colors (contains resource-id integers) into an
     * array list of actual color integers
     *
     * @param r
     * @param colors an integer array of resource id's of colors
     * @return
     */
    public static List<Integer> createColors(Resources r, int[] colors) {

        List<Integer> result = new ArrayList<Integer>();

        for (int i : colors) {
            result.add(r.getColor(i));
        }

        return result;
    }

    /**
     * Turns an array of colors (integer color values) into an ArrayList of
     * colors.
     *
     * @param colors
     * @return
     */
    public static List<Integer> createColors(int[] colors) {

        List<Integer> result = new ArrayList<Integer>();

        for (int i : colors) {
            result.add(i);
        }

        return result;
    }
}
