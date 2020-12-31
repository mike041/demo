package practice1;

import java.lang.reflect.Field;

public class Hero6 {
    public String name;
    public float hp;

    public int damage;

    //回血
    public void recover() {
        hp = hp + 1;
    }

    //掉血
    public void hurt() {
        synchronized (this) {
            hp = hp - 1;

        }
    }

    public static synchronized void getName(Hero6 hero) {

    }

    public void attackHero(Hero h) {
        h.hp -= damage;
        System.out.format("%s 正在攻击 %s, %s的血变成了 %.0f%n", name, h.name, h.name, h.hp);
        if (h.isDead())
            System.out.println(h.name + "死了！");
    }

    public boolean isDead() {
        return 0 >= hp ? true : false;
    }
}
