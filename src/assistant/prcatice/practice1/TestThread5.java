package practice1;

public class TestThread5 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            int seconds = 0;
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("已经玩了LOL %d 秒%n", seconds++);
            }
        });
        t1.setDaemon(true);
        t1.start();
    }
}