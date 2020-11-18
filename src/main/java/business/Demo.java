package business;



public class Demo {


    public void test(String s) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(s);
            }
        });
        thread.start();
    }


    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            Demo demo = new Demo();
            demo.test("wo" + i);
        }
    }


}
