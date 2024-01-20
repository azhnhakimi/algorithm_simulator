package utils;

import java.util.HashMap;
import java.util.Map;
import java.awt.Color;

public class CustomColor {

    static Map<Integer, Integer> colorMap = new HashMap<Integer, Integer>() {{
            put(0, 0xDC2127);
            put(1, 0xFF7400);
            put(2, 0x5E4DCD);
            put(3, 0x006600);
            put(4, 0x00C4FF);
            put(5, 0x51B749);
            put(6, 0xFFC100);
            put(7, 0xFF59C7);
            put(8, 0x800080);
            put(9, 0xDBADFF);
    }};
    
    public static int getColor(int processID){
        return colorMap.get(processID);
    }

    public static int getForegroundColor(int backgroundColor){

        Color bgColor = new Color(backgroundColor);

        // Calculate luminance of the background color
        double luminance = (0.299 * bgColor.getRed() +
                            0.587 * bgColor.getGreen() +
                            0.114 * bgColor.getBlue()) / 255;

        return (luminance > 0.5) ? Color.BLACK.getRGB() : Color.WHITE.getRGB();
    }
    
}
