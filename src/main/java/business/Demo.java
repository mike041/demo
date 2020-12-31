package business;


public class Demo {

    int s1;
    static int s2 = 100;

    public Demo(int s1) {
        this.s1 = s1;
        System.out.println("构造器");
    }

    {
        System.out.println("普通代码块");
        s1 = s1 + 1;
        s2 = s2 + 1;

    }

    static {
        System.out.println("静态代码块");
        s2 = s2 + 1;
    }
    public void test1(){
        System.out.println("test1");
    }
    public static void test2(){
        System.out.println("test2");
    }

    public static void main(String[] args) {

        String builder="sadsdsadd%s";
        String th= builder.replace("%s","sasd");
        System.out.println(th);
    }


}
