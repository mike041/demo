package practice1;

public class bodongquan {
    public static void main(String[] args) {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    int x = 0;
                    for (int i = 0; i < 3; i++) {
                        System.out.println("波动拳第" + (i + 1) + "发");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    x++;
                    if (x == 1) {
                        try {
                            System.out.println("开始5秒的技能充能");
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        t1.start();

    }
}
