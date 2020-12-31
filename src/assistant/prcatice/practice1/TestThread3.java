package practice1;

public class TestThread3 {
    public static void main(String[] args) {

        Hero3 gareen = new Hero3();
        gareen.name = "盖伦";
        gareen.hp = 616;
        gareen.damage = 1;

        Hero3 teemo = new Hero3();
        teemo.name = "提莫";
        teemo.hp = 300;
        teemo.damage = 1;

        Hero3 bh = new Hero3();
        bh.name = "赏金猎人";
        bh.hp = 500;
        bh.damage = 1;

        Hero3 leesin = new Hero3();
        leesin.name = "盲僧";
        leesin.hp = 455;
        leesin.damage = 1;

        Thread thread1 = new Thread() {
            @Override
            public void run() {
                while (!teemo.isDead()) {
                    gareen.attackHero(teemo);
                }
            }
        };
        thread1.setPriority(Thread.MAX_PRIORITY);
        thread1.start();

        Thread thread2 = new Thread(() -> {
            while (!leesin.isDead()) {
                bh.attackHero(leesin);
            }
        });
        thread1.setPriority(Thread.MIN_PRIORITY);

        thread2.start();
    }

}
