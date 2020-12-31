package utils;

import java.io.UnsupportedEncodingException;
import java.util.Random;

public class ChineseChar {

    public static char getRandomChar() {
        String str = "";
        int hightPos; //
        int lowPos;
        Random random = new Random();
        hightPos = (176 + Math.abs(random.nextInt(39)));
        lowPos = (161 + Math.abs(random.nextInt(93)));
        byte[] b = new byte[2];
        b[0] = (Integer.valueOf(hightPos)).byteValue();
        b[1] = (Integer.valueOf(lowPos)).byteValue();
        try {
            str = new String(b, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("错误");
        }
        return str.charAt(0);
    }


    public static final String getRandomString(int length) {
        StringBuffer ChineseString = new StringBuffer();
        for (int i = 0; i < length; i++) {
            ChineseString.append(ChineseChar.getRandomChar());
        }
        return ChineseString.toString();
    }


    public static void main(String[] args) {
        for (int i = 0; i < 24; i++) {
            System.out.println(ChineseChar.getRandomString(10));

        }
    }
}
