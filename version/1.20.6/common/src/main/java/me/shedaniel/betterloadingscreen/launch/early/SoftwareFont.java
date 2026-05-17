package me.shedaniel.betterloadingscreen.launch.early;

/**
 * Simple 5x7 pixel bitmap font for loading screen text.
 * Renders using fill() calls - no GPU textures needed.
 */
public class SoftwareFont {
    // 5x7 pixel font data for ASCII 32-126
    // Each character is 5 bits wide, 7 rows tall
    private static final String[] FONT_DATA = {
        "     |     |     |     |     |     |     ", // 32 space
        "  #  |  #  |  #  |  #  |     |  #  |     ", // 33 !
        " # # | # # |     |     |     |     |     ", // 34 "
        " # # | # # |#####| # # |#####| # # | # # ", // 35 #
        "  #  | ####| #   | ####|   # | ####|  #  ", // 36 $
        " ##  | #  #|  #  |  #  |  #  | #  #|  ## ", // 37 %
        " #   |#  # |#  # | #   |#  ##|#   #| ##  ", // 38 &
        "  #  |  #  |     |     |     |     |     ", // 39 '
        "   # |  #  | #   | #   | #   |  #  |   # ", // 40 (
        " #   |  #  |   # |   # |   # |  #  | #   ", // 41 )
        "     | # # |  #  |#####|  #  | # # |     ", // 42 *
        "     |  #  |  #  |#####|  #  |  #  |     ", // 43 +
        "     |     |     |     |  #  |  #  | #   ", // 44 ,
        "     |     |     |#####|     |     |     ", // 45 -
        "     |     |     |     |     |  #  |     ", // 46 .
        "    #|   # |  #  | #   |#    |     |     ", // 47 /
        " ### |#   #|#  ##|# # #|##  #|#   #| ### ", // 48 0
        "  #  | ##  |  #  |  #  |  #  |  #  | ### ", // 49 1
        " ### |#   #|    #|  ## | #   |#    |#####", // 50 2
        " ### |#   #|    #|  ## |    #|#   #| ### ", // 51 3
        "   # |  ## | # # |#  # |#####|   # |   # ", // 52 4
        "#####|#    |#### |    #|    #|#   #| ### ", // 53 5
        "  ## | #   |#    |#### |#   #|#   #| ### ", // 54 6
        "#####|    #|   # |  #  | #   |#    |#    ", // 55 7
        " ### |#   #|#   #| ### |#   #|#   #| ### ", // 56 8
        " ### |#   #|#   #| ####|    #|   # | ##  ", // 57 9
        "     |  #  |     |     |     |  #  |     ", // 58 :
        "     |  #  |     |     |  #  |  #  | #   ", // 59 ;
        "   ##|  ## | ##  |##   | ##  |  ## |   ##", // 60 <
        "     |     |#####|     |#####|     |     ", // 61 =
        "##   | ##  |  ## |   ##|  ## | ##  |##   ", // 62 >
        " ### |#   #|    #|   # |  #  |     |  #  ", // 63 ?
        " ### |#   #|#  ##|# # #|#  ##|#    | ####", // 64 @
        "  #  | # # |#   #|#   #|#####|#   #|#   #", // 65 A
        "#### |#   #|#   #|#### |#   #|#   #|#### ", // 66 B
        " ##  |#   #|#    |#    |#    |#   #| ##  ", // 67 C
        "#### |#   #|#   #|#   #|#   #|#   #|#### ", // 68 D
        "#####|#    |#    |#####|#    |#    |#####", // 69 E
        "#####|#    |#    |#####|#    |#    |#    ", // 70 F
        " ##  |#   #|#    |#  ##|#   #|#   #| ### ", // 71 G
        "#   #|#   #|#   #|#####|#   #|#   #|#   #", // 72 H
        " ### |  #  |  #  |  #  |  #  |  #  | ### ", // 73 I
        "   ##|    #|    #|    #|    #|#   #| ### ", // 74 J
        "#   #|#  # |# #  |##   |# #  |#  # |#   #", // 75 K
        "#    |#    |#    |#    |#    |#    |#####", // 76 L
        "#   #|## ##|# # #|#   #|#   #|#   #|#   #", // 77 M
        "#   #|##  #|# # #|#  ##|#   #|#   #|#   #", // 78 N
        " ### |#   #|#   #|#   #|#   #|#   #| ### ", // 79 O
        "#### |#   #|#   #|#### |#    |#    |#    ", // 80 P
        " ### |#   #|#   #|#   #|# # #|#  ##| ## #", // 81 Q
        "#### |#   #|#   #|#### |# #  |#  # |#   #", // 82 R
        " ### |#   #|#    | ### |    #|#   #| ### ", // 83 S
        "#####|  #  |  #  |  #  |  #  |  #  |  #  ", // 84 T
        "#   #|#   #|#   #|#   #|#   #|#   #| ### ", // 85 U
        "#   #|#   #|#   #|#   #|#   #| # # |  #  ", // 86 V
        "#   #|#   #|#   #|# # #|## ##|#   #|#   #", // 87 W
        "#   #|#   #| # # |  #  | # # |#   #|#   #", // 88 X
        "#   #|#   #| # # |  #  |  #  |  #  |  #  ", // 89 Y
        "#####|    #|   # |  #  | #   |#    |#####", // 90 Z
        "  ## |  #  |  #  |  #  |  #  |  #  |  ## ", // 91 [
        "#    |#    | #   |  #  |   # |    #|    #", // 92 backslash
        " ##  |   # |   # |   # |   # |   # | ##  ", // 93 ]
        "  #  | # # |     |     |     |     |     ", // 94 ^
        "     |     |     |     |     |     |#####", // 95 _
        " ##  |#   #|   ##| ####|#    |#   #| ### ", // 96 ` (actually using this for more chars)
    };
    
    private static final int CHAR_WIDTH = 5;
    private static final int CHAR_HEIGHT = 7;
    private static final int CHAR_SPACING = 1;
    
    public interface PixelRenderer {
        void setPixel(int x, int y, int color);
    }
    
    public static void drawString(PixelRenderer renderer, int baseX, int baseY, 
                                    String text, int color, int scale) {
        int x = baseX;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\n') {
                x = baseX;
                baseY += (CHAR_HEIGHT + CHAR_SPACING) * scale;
                continue;
            }
            if (c < 32 || c > 126) {
                x += (CHAR_WIDTH + CHAR_SPACING) * scale;
                continue;
            }
            int idx = c - 32;
            if (idx >= FONT_DATA.length) {
                x += (CHAR_WIDTH + CHAR_SPACING) * scale;
                continue;
            }
            String[] rows = FONT_DATA[idx].split("\\|");
            if (rows.length < 7) {
                x += (CHAR_WIDTH + CHAR_SPACING) * scale;
                continue;
            }
            for (int row = 0; row < 7; row++) {
                for (int col = 0; col < 5; col++) {
                    char pixel = rows[row].length() > col ? rows[row].charAt(col) : ' ';
                    if (pixel == '#') {
                        // Draw a filled rectangle for this pixel
                        int px = x + col * scale;
                        int py = baseY + row * scale;
                        renderer.setPixel(px, py, color);
                        if (scale > 1) {
                            renderer.setPixel(px + scale - 1, py, color);
                            renderer.setPixel(px, py + scale - 1, color);
                            renderer.setPixel(px + scale - 1, py + scale - 1, color);
                        }
                    }
                }
            }
            x += (CHAR_WIDTH + CHAR_SPACING) * scale;
        }
    }
    
    public static int getWidth(String text, int scale) {
        return text.length() * (CHAR_WIDTH + CHAR_SPACING) * scale;
    }
}
