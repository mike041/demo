package practice1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Decryption {
    public static void main(String[] args) {
        Random random = new Random();
        List<String> passwords = new ArrayList<>();
        String password = String.valueOf(random.nextInt(1000));
        System.out.println("密码是：" + password);


        Thread ecryptionThread = new Thread() {
            @Override
            public void run() {
                boolean cnt = true;
                while (cnt) {
                    String number = String.valueOf(random.nextInt(1000));
                //    System.out.println("本次随机密码是" + number);

                    if (password.equals(number)) {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        cnt = false;
                        System.out.println("找到密码了" + number);
                    } else {
                        passwords.add(number);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }

            }
        };


        Thread daemonThread = new Thread() {
            @Override
            public void run() {
                while (true){
                    if (passwords.isEmpty()) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("密码可能是：" + passwords);
                        passwords.clear();
                    }
                }


            }
        };
        daemonThread.setPriority(Thread.MAX_PRIORITY);
        ecryptionThread.setPriority(Thread.MIN_PRIORITY);
        daemonThread.setDaemon(true);
        ecryptionThread.start();
        daemonThread.start();
    }
}
