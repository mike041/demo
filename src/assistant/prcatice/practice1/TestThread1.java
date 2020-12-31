package practice1;

public class TestThread1 {
    public static void main(String[] args) {
        new Thread(() -> {
            int seconds = 0;
            while (seconds<30) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("已经玩了LOL %d 秒%n", seconds++);
            }
        }).start();
    }

}
