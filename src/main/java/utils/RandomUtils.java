package utils;


import java.io.UnsupportedEncodingException;
import java.util.Random;

public class RandomUtils {
    public static String getNumber() {
        long timeStamp = System.currentTimeMillis();
        return String.valueOf(timeStamp);
    }

    public static String getNumber(int end) {
        long timeStamp = System.currentTimeMillis();
        return String.valueOf(timeStamp).substring(0, end);
    }

    public static String getNumber(int start, int end) {
        long timeStamp = System.currentTimeMillis();
        return String.valueOf(timeStamp).substring(start, end);
    }

    public static String getChar(int length) {
        Random random = new Random();
        String result = "";
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPWRDTUVECYZ";
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(52);
            result = result.concat(String.valueOf(chars.charAt(index)));
        }
        return result;
    }

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


    public static String getRandomString(int length) {
        StringBuffer ChineseString = new StringBuffer();
        for (int i = 0; i < length; i++) {
            ChineseString.append(RandomUtils.getRandomChar());
        }
        return ChineseString.toString();
    }

    public static void main(String[] args) {
        System.out.println(RandomUtils.getNumber());
    }
}
