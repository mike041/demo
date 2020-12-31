package utils;


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

    public static void main(String[] args) {
        System.out.println(RandomUtils.getNumber());
    }
}
